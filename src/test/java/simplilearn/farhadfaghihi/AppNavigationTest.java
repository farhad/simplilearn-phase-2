package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.navigation.AppNavigation;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static simplilearn.farhadfaghihi.utils.Consts.MAIN_MENU_FILE_PATH;
import static simplilearn.farhadfaghihi.utils.Consts.WELCOME_MESSAGE_FILE_PATH;

public class AppNavigationTest {

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
    public void givenAppNavigation_whenCalledDisplayMenu_thenMainMenu() throws IOException {
        // Arrange
        String welcomeMessage = FileUtils.readFileContent(WELCOME_MESSAGE_FILE_PATH);
        String mainMenuMessage = FileUtils.readFileContent(MAIN_MENU_FILE_PATH);
        String expectedOutput = welcomeMessage + mainMenuMessage + System.lineSeparator();

        // Act
        AppNavigation appNavigation = AppNavigation.getInstance();
        appNavigation.displayAppMenu();

        // Assert
        Assert.assertEquals(expectedOutput, outContent.toString());
    }
}
