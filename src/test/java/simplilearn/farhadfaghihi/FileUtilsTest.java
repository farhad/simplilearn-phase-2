package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;

public class FileUtilsTest {

    private static String FILE_PATH = "src/main/resources/welcome_message.txt";

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void givenFilePath_whenUsingReadFileContent_thenFileData() throws IOException {
        // Arrange + Act
        String content = FileUtils.readFileContent(FILE_PATH);

        // Assert
        Assert.assertTrue(content.contains("Phase 1 Assignment project"));
    }
}
