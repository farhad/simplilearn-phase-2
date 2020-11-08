package simplilearn.farhadfaghihi.navigation;

import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.model.FileObject;
import simplilearn.farhadfaghihi.model.OperationResult;
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

    private final List<Integer> validOptions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
    private final Scanner scanner = new Scanner(System.in);
    private final FileDao fileDao;
    private final Path currentDirectory;

    private int lastSelectedOption = -1;

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
                        OperationResult allFilesResult = fileDao.getAllFiles(currentDirectory);
                        printFileNamesToConsole(allFilesResult.getFileObjects());
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
                    OperationResult searchResult = fileDao.searchFiles(currentDirectory, token);
                    if (searchResult.isSuccessful()) {
                        System.out.println("Results found: " + searchResult.getFileObjects().size());
                        printFileNamesToConsole(searchResult.getFileObjects());
                    } else {
                        System.out.println(searchResult.getMessage());
                    }
                } else if (lastSelectedOption == 3) {
                    Path path = Paths.get(currentDirectory.toString() + "/" + token);
                    OperationResult operationResult = fileDao.addFile(path, "");
                    System.out.println(operationResult.getMessage());

                } else if (lastSelectedOption == 4) {
                    Path path = Paths.get(currentDirectory.toString() + "/" + token);
                    OperationResult operationResult = fileDao.deleteFile(path);
                    System.out.println(operationResult.getMessage());

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

    private void printFileNamesToConsole(List<FileObject> fileNames) {
        fileNames.forEach(fileObject -> {
            System.out.println(fileObject.getNameAndExtension());
        });
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
