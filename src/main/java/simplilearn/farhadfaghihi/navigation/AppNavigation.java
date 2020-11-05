package simplilearn.farhadfaghihi.navigation;

import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static simplilearn.farhadfaghihi.utils.Consts.*;

/**
 * Main Menu Navigation Class
 */
public class AppNavigation {

    private final Scanner scanner = new Scanner(System.in);
    private int lastSelectedOption = -1;
    private List<Integer> validOptions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));

    private FileDao fileDao;
    private String currentDirectory;
    private static AppNavigation instance;

    protected AppNavigation(FileDao fileDao, String currentDirectory) {
        this.fileDao = fileDao;
        this.currentDirectory = currentDirectory;
    }

    public static AppNavigation getInstance(FileDao fileDao, String currentDirectory) {
        if (instance == null)
            instance = new AppNavigation(fileDao, currentDirectory);
        return instance;
    }

    public void displayAppMenu() throws IOException {
        if (lastSelectedOption == -1) {
            displayWelcomeMessage();
        }

        displayMainMenu();

        readMenuOptionFromConsole();
    }

    private void readMenuOptionFromConsole() throws IOException {
        while (lastSelectedOption != 0) {
            String token = scanner.next();
            if (isValidOption(token)) {
                lastSelectedOption = Integer.parseInt(token);
                switch (lastSelectedOption) {
                    case 0: {
                        displayFarewellMessageAndExit();
                        break;
                    }

                    case 1: {
                        List<String> allFiles = fileDao.getAllFileNames(currentDirectory);
                        printFileNamesToConsole(allFiles);
                        break;
                    }

                    case 2: {
                        displaySearchFilesMenu();
                        break;
                    }

                    case 3: {
                        displayAddNewFileMenu();
                        break;
                    }

                    case 4: {
                        displayDeleteFileMenu();
                        break;
                    }

                    default: {

                    }
                }
            } else {
                displayInvalidOptionMessage();
            }
        }
    }

    protected void displayWelcomeMessage() throws IOException {
        printFileToConsole(WELCOME_MESSAGE_FILE_PATH);
    }

    protected void displayMainMenu() throws IOException {
        printFileToConsole(MAIN_MENU_FILE_PATH);
    }

    protected void displayFarewellMessageAndExit() throws IOException {
        printFileToConsole(FAREWELL_MESSAGE_FILE_PATH);
        System.exit(0);
    }

    protected void displaySearchFilesMenu() throws IOException {
        printFileToConsole(SEARCH_MENU_FILE_PATH);
    }

    protected void displayAddNewFileMenu() throws IOException {
        printFileToConsole(ADD_MENU_FIRST_FILE_PATH);
    }

    protected void displayDeleteFileMenu() throws IOException {
        printFileToConsole(DELETE_MENU_FILE_PATH);
    }

    protected void displayInvalidOptionMessage() throws IOException {
        printFileToConsole(INVALID_OPTION_MESSAGE_FILE_PATH);
    }

    private void printFileToConsole(String fileName) throws IOException {
        String mainMenu = FileUtils.readFileContent(fileName);
        System.out.println(mainMenu);
    }

    private void printFileNamesToConsole(List<String> fileNames) {
        // todo : show them in table format using formatted text
        // todo : show additional information about a file
        fileNames.forEach(System.out::println);
    }

    private boolean isValidOption(String token) {
        if (token == null)
            return false;

        try {
            int option = Integer.parseInt(token);
            return validOptions.contains(option);
        } catch (Exception exception) {
            return false;
        }
    }

}
