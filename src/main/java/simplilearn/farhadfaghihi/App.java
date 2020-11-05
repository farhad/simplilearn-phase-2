package simplilearn.farhadfaghihi;

import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.dao.FileDaoImpl;
import simplilearn.farhadfaghihi.navigation.AppNavigation;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;

public class App {
    public static void main(String[] args) {

        try {
            FileDao fileDao = new FileDaoImpl();
            String currentWorkingDir = FileUtils.getCurrentWorkingDirectory();
            AppNavigation appNavigation = AppNavigation.getInstance(fileDao, currentWorkingDir);
            appNavigation.displayAppMenu();
        } catch (IOException e) {
            System.out.println("unexpected error -> application terminated");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
