package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.utils.Consts;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;

public class FileUtilsTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void givenFilePath_whenUsingReadFileContent_thenFileData() throws IOException {
        // Arrange + Act
        String content = FileUtils.readFileContent(Consts.WELCOME_MESSAGE_FILE_PATH);

        // Assert
        Assert.assertTrue(content.contains("Phase 1 Assignment project"));
    }
}
