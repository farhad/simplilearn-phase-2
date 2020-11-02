package simplilearn.farhadfaghihi.navigation;

import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.util.Scanner;

import static simplilearn.farhadfaghihi.utils.Consts.MAIN_MENU_FILE_PATH;
import static simplilearn.farhadfaghihi.utils.Consts.WELCOME_MESSAGE_FILE_PATH;

/**
 * Main Menu Navigation Class
 */
public class AppNavigation {

    private static AppNavigation instance;
    private int lastSelectedOption = -1;

    private AppNavigation() {

    }

    public static AppNavigation getInstance() {
        if (instance == null)
            instance = new AppNavigation();
        return instance;
    }

    public void displayAppMenu() throws IOException {
        StringBuilder mainMenu = new StringBuilder();
        if (lastSelectedOption == -1) {
            mainMenu.append(FileUtils.readFileContent(WELCOME_MESSAGE_FILE_PATH));
        }

        mainMenu.append(FileUtils.readFileContent(MAIN_MENU_FILE_PATH));
        System.out.println(mainMenu.toString());
    }
}
