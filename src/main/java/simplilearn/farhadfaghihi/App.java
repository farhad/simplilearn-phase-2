package simplilearn.farhadfaghihi;

import simplilearn.farhadfaghihi.navigation.AppNavigation;

import java.io.IOException;

public class App {
    public static void main(String[] args) {

        try {
            AppNavigation appNavigation = AppNavigation.getInstance();
            appNavigation.displayAppMenu();
        } catch (IOException e) {
            System.out.println("unexpected error -> application terminated");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
