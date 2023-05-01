package _04_HospitalDatabase.core;

public interface Controller {
    String addPatient();

    String addVisitation();

    String addDiagnose();

    String addMedicament();

    String findPatient();

    String findVisitationByDate();

    String closeProgram();
}
