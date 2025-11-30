package ru.yandex.practicum.gym;

public class Practicum {
    public static void main(String[] args) {
        Timetable timetable = new Timetable();

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coachVasilyev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachPetrov = new Coach("Петров", "Иван", "Алексеевич");     // Будет 1 занятие
        Coach coachSidorov = new Coach("Сидоров", "Алексей", "Петрович");



        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coachVasilyev,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession singleTrainingSession = new TrainingSession(groupAdult, coachVasilyev,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coachSidorov,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachVasilyev,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachVasilyev,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        System.out.println(timetable);

    }



}
