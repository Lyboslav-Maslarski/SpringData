package _04_HospitalDatabase.core;

import java.util.Scanner;

public class EngineImp implements Engine {
    private final Scanner scanner;
    private final Controller controller;

    public EngineImp(Scanner scanner, Controller controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public void run() {
        String result = null;

        while (!("End").equals(result)) {

            result = processInput();

            System.out.println(result);
        }
    }

    private String processInput() {
        printOptions();

        int pickedOption = Integer.parseInt(scanner.nextLine());

        String result = null;

        switch (pickedOption) {
            case 1:
                result = controller.addPatient();
                break;
            case 2:
                result = controller.addVisitation();
                break;
            case 3:
                result = controller.addDiagnose();
                break;
            case 4:
                result = controller.addMedicament();
                break;
            case 5:
                result = controller.findPatient();
                break;
            case 6:
                result = controller.findVisitationByDate();
                break;
            case 0:
                result = controller.closeProgram();
                break;
            default:
                result = "No such option.";
        }
        return result;
    }

    private void printOptions() {
        System.out.println("1 - Insert a patient");
        System.out.println("2 - Insert a visitation");
        System.out.println("3 - Insert a diagnose");
        System.out.println("4 - Insert a medicament");
        System.out.println("5 - Find a patient");
        System.out.println("6 - Find a visitation by date");
        System.out.println("0 - End");
        System.out.println("Please enter the number of the command you want to execute.");
    }
}
