package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertNotNull(mondaySessions);
        Assertions.assertEquals(1, mondaySessions.size());
        ArrayList<TrainingSession> sessionsList = mondaySessions.get(new TimeOfDay(13, 0));
        Assertions.assertNotNull(sessionsList);
        Assertions.assertEquals(1, sessionsList.size());
        //Проверить, что за понедельник вернулось одно занятие

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(tuesdaySessions);
        //Проверить, что за вторник не вернулось занятий


    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertNotNull(mondaySessions);
        Assertions.assertEquals(1, mondaySessions.size());
        ArrayList<TrainingSession> sessionsList = mondaySessions.get(new TimeOfDay(13, 0));
        Assertions.assertNotNull(sessionsList);
        Assertions.assertEquals(1, sessionsList.size());
        // Проверить, что за понедельник вернулось одно занятие

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertNotNull(thursdaySessions);
        Assertions.assertEquals(2, thursdaySessions.size());
        Iterator<TimeOfDay> timeIterator = thursdaySessions.keySet().iterator();

        TimeOfDay firstTime = timeIterator.next();
        TimeOfDay secondTime = timeIterator.next();

        Assertions.assertEquals(new TimeOfDay(13, 0), firstTime);
        Assertions.assertEquals(new TimeOfDay(20, 0), secondTime);
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(tuesdaySessions);
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondayAdultrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(mondayAdultrainingSession);

        ArrayList<TrainingSession> sessionsAt1300 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        Assertions.assertNotNull(sessionsAt1300);
        Assertions.assertEquals(2, sessionsAt1300.size());
        //Проверить, что за понедельник в 13:00 вернулось одно занятие (проверил 2 занятия)


        ArrayList<TrainingSession> sessionsAt1400 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)
        );

        Assertions.assertNull(sessionsAt1400);
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetCountByCoaches_MultipleCoachesAndSessions() {
        Timetable timetable = new Timetable();

        Coach coachVasilyev = new Coach("Васильев", "Николай", "Сергеевич"); // Будет 3 занятия
        Coach coachPetrov = new Coach("Петров", "Иван", "Алексеевич");     // Будет 1 занятие
        Coach coachSidorov = new Coach("Сидоров", "Алексей", "Петрович");   // Будет 0 занятий

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachVasilyev,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0)));

        HashMap<Coach, Integer> counts = timetable.getCountByCoaches();

        Assertions.assertEquals(2, counts.size());

        Integer vasilyevCount = counts.get(coachVasilyev);
        Assertions.assertNotNull(vasilyevCount);
        Assertions.assertEquals(3, vasilyevCount);

        Integer petrovCount = counts.get(coachPetrov);
        Assertions.assertNotNull(petrovCount);
        Assertions.assertEquals(1, petrovCount);

        Integer sidorovCount = counts.get(coachSidorov);
        Assertions.assertNull(sidorovCount);
    }

    @Test
    void testGetCountByCoaches_EmptyTimetable() {
        Timetable timetable = new Timetable();

        HashMap<Coach, Integer> counts = timetable.getCountByCoaches();

        Assertions.assertNotNull(counts);
        Assertions.assertTrue(counts.isEmpty());
    }

}
