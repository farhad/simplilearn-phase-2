package simplilearn.farhadfaghihi.navigation;

import org.codehaus.plexus.component.configurator.converters.basic.FileConverter;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.util.Scanner;

import static simplilearn.farhadfaghihi.utils.Consts.*;

/**
 * Main Menu Navigation Class
 */
public class AppNavigation {

    private static AppNavigation instance;
    private int lastSelectedOption = -1;


    protected AppNavigation() {

    }

    public static AppNavigation getInstance() {
        if (instance == null)
            instance = new AppNavigation();
        return instance;
    }

    public void displayAppMenu() throws IOException {
        if (lastSelectedOption == -1) {
            displayWelcomeMessage();
        }

        displayMainMenu();

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        switch (option) {
            case 0: {
                displayFarewellMessageAndExit();
            }
        }
    }

    protected void displayWelcomeMessage() throws IOException {
        String welcomeMessage = FileUtils.readFileContent(WELCOME_MESSAGE_FILE_PATH);
        System.out.println(welcomeMessage);
    }

    protected void displayMainMenu() throws IOException {
        String mainMenu = FileUtils.readFileContent(MAIN_MENU_FILE_PATH);
        System.out.println(mainMenu);
    }

    protected void displayFarewellMessageAndExit() throws IOException {
        String farewell = FileUtils.readFileContent(FAREWELL_MESSAGE_FILE_PATH);
        System.out.println(farewell);
        System.exit(0);
    }
}
