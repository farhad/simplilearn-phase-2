package simplilearn.farhadfaghihi.navigation;

import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final FileDao fileDao;
    private final Path currentDirectory;

    private int lastSelectedOption = -1;
    private List<Integer> validOptions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));

    private static AppNavigation instance;

    protected AppNavigation(FileDao fileDao, Path currentDirectory) {
        this.fileDao = fileDao;
        this.currentDirectory = currentDirectory;
    }

    public static AppNavigation getInstance(FileDao fileDao, Path currentDirectory) {
        if (instance == null)
            instance = new AppNavigation(fileDao, currentDirectory);
        return instance;
    }

    public void start() throws IOException {
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
                int tokenInt = Integer.parseInt(token);
                switch (tokenInt) {
                    case 0: {
                        if (lastSelectedOption == -1)
                            displayFarewellMessageAndExit();
                        else
                            displayMainMenu();

                        break;
                    }

                    case 1: {
                        List<String> allFiles = fileDao.getAllFileNames(currentDirectory);
                        printFileNamesToConsole(allFiles);
                        displayMainMenu();
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
                }
                lastSelectedOption = tokenInt;
            } else {
                if (lastSelectedOption == 2) {
                    List<String> searchResult = fileDao.searchFiles(currentDirectory, token);
                    System.out.println("Results found: " + searchResult.size());
                    printFileNamesToConsole(searchResult);

                } else if (lastSelectedOption == 3) {
                    Path path = Paths.get(currentDirectory.toString() + "/" + token);
                    fileDao.addFile(path, "");
                    System.out.println("file added successfully");

                } else if (lastSelectedOption == 4) {
                    Path path = Paths.get(currentDirectory.toString() + "/" + token);
                    boolean result = fileDao.deleteFile(path);
                    if (result) {
                        System.out.println("file deleted successfully");
                    } else {
                        System.out.println("file deletion failed");
                    }

                } else {
                    displayInvalidOptionMessage();
                }

                displayMainMenu();
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
        fileNames.forEach(System.out::println);
        System.out.println();
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
