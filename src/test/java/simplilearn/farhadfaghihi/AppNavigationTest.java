package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.navigation.AppNavigation;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.*;

import static simplilearn.farhadfaghihi.utils.Consts.*;

/**
 * because we need to write test for protected and private methods in [AppNavigation] class,
 * we need to extend the class.
 */
public class AppNavigationTest extends AppNavigation {

    public AppNavigationTest() {
        getInstance();
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void before() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void after() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    public void givenAppNavigation_whenCalledDisplayWelcomeMessage_thenWelcomeMessage() throws IOException {
        // Arrange
        String welcomeMessage = FileUtils.readFileContent(WELCOME_MESSAGE_FILE_PATH);

        // Act
        displayWelcomeMessage();

        // Assert
        Assert.assertTrue(outContent.toString().contains(welcomeMessage));
    }

    @Test
    public void givenAppNavigation_whenCallingDisplayMainMenu_thenMainMenu() throws IOException {
        // Arrange
        String mainMenu = FileUtils.readFileContent(MAIN_MENU_FILE_PATH);

        // Act
        displayMainMenu();

        // Assert
        Assert.assertTrue(outContent.toString().contains(mainMenu));
    }
}
