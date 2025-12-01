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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Расписание на неделю:\n");

        List<DayOfWeek> sortedDays = new ArrayList<>(timetable.keySet());
        Collections.sort(sortedDays);

        for (DayOfWeek day : sortedDays) {

            result.append("\n- ").append(day.toString()).append(" -\n");

            TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsForDay = timetable.get(day);
            for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> timeEntry : sessionsForDay.entrySet()) {
                TimeOfDay time = timeEntry.getKey();
                ArrayList<TrainingSession> sessions = timeEntry.getValue();

                result.append(time.toString()).append(":\n");

                for (TrainingSession session : sessions) {
                    result.append("    - ").append(session.toString()).append("\n");
                }
            }
        }

        return result.toString();
    }

}