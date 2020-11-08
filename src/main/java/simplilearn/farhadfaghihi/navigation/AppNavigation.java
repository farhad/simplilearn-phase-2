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
                processSubMenuInput(token, scanner);
            }
        }

        scanner.close();
        displayFarewellMessageAndExit();
    }

    private void processMainMenuInput(String token) throws IOException {
        int tokenInt = Integer.parseInt(token);
        switch (tokenInt) {
            case 0: {
                if (lastSelectedOption == 9)
                    displayFarewellMessageAndExit();
                break;
            }
            case 1: {
                lastSelectedOption = tokenInt;
                OperationResult allFilesResult = fileDao.getAllFiles(currentDirectory);
                printFileObjectsToConsole(allFilesResult.getFileObjects());

                displayContinueOrReturnOption();
                break;
            }
            case 2: {
                lastSelectedOption = tokenInt;
                displaySearchFilesMenu();
                break;
            }
            case 3: {
                lastSelectedOption = tokenInt;
                displayAddNewFileMenu();
                break;
            }
            case 4: {
                lastSelectedOption = tokenInt;
                displayDeleteFileMenu();
                break;
            }

            case 9: {
                lastSelectedOption = tokenInt;
                displayMainMenu();
                break;
            }

            default: {
                if (tokenInt != 0)
                    displayInvalidOptionMessage();
                break;
            }
        }


    }

    private void processSubMenuInput(String token, Scanner scanner) throws IOException {
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

        displayContinueOrReturnOption();
    }

    protected void displayWelcomeMessage() throws IOException {
        printFileToConsole(WELCOME_MESSAGE_PATH);
    }

    protected void displayMainMenu() throws IOException {
        printFileToConsole(MAIN_MENU_PATH);
    }

    protected void displayFarewellMessageAndExit() throws IOException {
        printFileToConsole(FAREWELL_MESSAGE_PATH);
        System.exit(0);
    }

    protected void displayContinueOrReturnOption() {
        switch (lastSelectedOption) {
            case 1: {
                System.out.println("Press 9 to return to the main menu:");
                break;
            }

            case 2: {
                System.out.println("Enter a file name to search again or Press 9 to return to the main menu:");
                break;
            }

            case 3: {
                System.out.println("Enter a file name to add again or Press 9 to return to the main menu:");
                break;
            }

            case 4: {
                System.out.println("Enter a file name to delete again or Press 9 to return to the main menu:");
                break;
            }
        }

    }

    protected void displaySearchFilesMenu() throws IOException {
        printFileToConsole(SEARCH_MENU_PATH);
    }

    protected void displayAddNewFileMenu() throws IOException {
        printFileToConsole(ADD_MENU_FIRST_PATH);
    }

    protected void displayDeleteFileMenu() throws IOException {
        printFileToConsole(DELETE_MENU_PATH);
    }

    protected void displayInvalidOptionMessage() throws IOException {
        printFileToConsole(INVALID_OPTION_MESSAGE_FILE);
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
