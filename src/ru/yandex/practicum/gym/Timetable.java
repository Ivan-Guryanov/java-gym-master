package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsForDay =
                timetable.computeIfAbsent(day, k -> new TreeMap<>());

        ArrayList<TrainingSession> sessionsAtSpecificTime =
                sessionsForDay.computeIfAbsent(time, k -> new ArrayList<>());

        sessionsAtSpecificTime.add(trainingSession);
        //сохраняем занятие в расписании
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

        if (sessionsForDay != null) {
            return sessionsForDay.get(timeOfDay);
        }

        return null;
    }

    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> coachCounts = new HashMap<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> day : timetable.values()) {
            if (day == null) continue;
            for (ArrayList<TrainingSession> sessionsAtTime : day.values()) {
                for (TrainingSession session : sessionsAtTime) {
                    Coach coach = session.getCoach(); // Получаем тренера текущего занятия
                    coachCounts.computeIfAbsent(coach, k -> 0);
                    coachCounts.put(coach, coachCounts.get(coach) + 1);

                }
            }
        }
        return coachCounts;
    }

}