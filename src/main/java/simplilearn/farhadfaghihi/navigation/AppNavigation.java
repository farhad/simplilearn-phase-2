package simplilearn.farhadfaghihi.navigation;

import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.model.FileObject;
import simplilearn.farhadfaghihi.model.OperationResult;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static simplilearn.farhadfaghihi.utils.Consts.*;

/**
 * Main Menu Navigation Class
 */
public class AppNavigation {

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

        runMainMenu();
    }

    private void runMainMenu() throws IOException {
        displayMainMenu();

        Scanner scanner = new Scanner(System.in);
        while (lastSelectedOption != 0) {
            String token = scanner.next();
            if (isNumericToken(token)) {
                processMainMenuInput(token);
            } else {
                processSubMenuInput(token);
            }
        }

        scanner.close();
        displayFarewellMessageAndExit();
    }

    private void processMainMenuInput(String token) throws IOException {
        int tokenInt = Integer.parseInt(token);
        switch (tokenInt) {
            case 1: {
                OperationResult allFilesResult = fileDao.getAllFiles(currentDirectory);
                printFileObjectsToConsole(allFilesResult.getFileObjects());

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
            default: {
                if (tokenInt != 0)
                    displayInvalidOptionMessage();
                break;
            }
        }

        lastSelectedOption = tokenInt;
    }

    private void processSubMenuInput(String token) throws IOException {
        if (lastSelectedOption == 2) {
            OperationResult searchResult = fileDao.searchFiles(currentDirectory, token);
            if (searchResult.isSuccessful()) {
                System.out.println("Results found: " + searchResult.getFileObjects().size());
                printFileObjectsToConsole(searchResult.getFileObjects());
            } else {
                System.out.println(searchResult.getMessage());
            }
        } else if (lastSelectedOption == 3) {
            Path path = Paths.get(currentDirectory.toString() + "/" + token);
            OperationResult operationResult = fileDao.addFile(path, "");
            System.out.println(operationResult.getMessage() + System.lineSeparator());

        } else if (lastSelectedOption == 4) {
            Path path = Paths.get(currentDirectory.toString() + "/" + token);
            OperationResult operationResult = fileDao.deleteFile(path);
            System.out.println(operationResult.getMessage() + System.lineSeparator());

        } else {
            displayInvalidOptionMessage();
        }

        displayMainMenu();
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

    private void printFileObjectsToConsole(List<FileObject> fileNames) {
        fileNames.forEach(fileObject -> {
            String template = "%1$-32s %2$-16s %3$-16s %n";
            System.out.printf(template, fileObject.getNameAndExtension(), fileObject.getType(), fileObject.getTotalSize());
        });
        System.out.println();
    }

    private boolean isNumericToken(String token) {
        if (token == null)
            return false;
        try {
            Integer.parseInt(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}
