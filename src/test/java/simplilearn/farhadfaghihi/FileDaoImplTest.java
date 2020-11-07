package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.dao.FileDaoImpl;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImplTest {
    private static final Path PATH = Paths.get("src/test/resources");

    private static final String SAMPLE_FILE_NAME_ONE = "a_file_for_search.txt";
    private static final String SAMPLE_FILE_NAME_TWO = "b_file_for_search.txt";
    private static final String SAMPLE_FILE_NAME_THREE = "c_file_for_search_xyz.txt";

    private static final String FILE_TO_ADD_NAME = "d_new_file.txt";
    private static final Path FILE_TO_ADD = Paths.get(PATH.toString() + "/" + FILE_TO_ADD_NAME);
    private static final Path FILE_TO_DELETE = Paths.get(PATH.toString() + "/" + FILE_TO_ADD_NAME);

    private final ArrayList<String> fileNames = new ArrayList<>();

    @Before
    public void before() throws IOException {
        addSampleFiles();

        fileNames.add(SAMPLE_FILE_NAME_ONE);
        fileNames.add(SAMPLE_FILE_NAME_TWO);
        fileNames.add(SAMPLE_FILE_NAME_THREE);
    }

    @After
    public void after() throws IOException {
        fileNames.clear();
        deleteSampleFiles();
    }

    private void addSampleFiles() throws IOException {
        Path sampleFileOne = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_ONE);
        Files.createFile(sampleFileOne);

        Path sampleFileTwo = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_TWO);
        Files.createFile(sampleFileTwo);

        Path sampleFileThree = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_THREE);
        Files.createFile(sampleFileThree);
    }

    private void deleteSampleFiles() throws IOException {
        Path sampleFileOne = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_ONE);
        Files.deleteIfExists(sampleFileOne);
        Path sampleFileTwo = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_TWO);
        Files.deleteIfExists(sampleFileTwo);
        Path sampleFileThree = Paths.get(PATH.toString() + "/" + SAMPLE_FILE_NAME_THREE);
        Files.deleteIfExists(sampleFileThree);

        Files.deleteIfExists(FILE_TO_ADD);
    }

    @Test
    public void givenPath_whenFileDaoGetAllFiles_thenFileNames() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        List<String> files = fileDao.getAllFileNames(PATH);

        // Arrange
        Assert.assertEquals(files.size(), 3);
        Assert.assertEquals(files.get(0), fileNames.get(0));
        Assert.assertEquals(files.get(1), fileNames.get(1));
        Assert.assertEquals(files.get(2), fileNames.get(2));
    }

    @Test
    public void givenPath_whenFileDaoGetAllFiles_thenFileNamesAreSorted() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        List<String> files = fileDao.getAllFileNames(PATH);

        // Arrange
        Assert.assertEquals(files.size(), 3);
        Assert.assertEquals(files.get(0), fileNames.get(0));
        Assert.assertEquals(files.get(1), fileNames.get(1));
        Assert.assertEquals(files.get(2), fileNames.get(2));
    }

    @Test
    public void givenPath_whenFileDaoAddFile_thenFileAdded() throws IOException {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        boolean result = fileDao.addFile(FILE_TO_ADD, "test");

        // Assert
        Assert.assertTrue(result);

        // Act
        String fileContent = FileUtils.readFileContent(FILE_TO_ADD.toString());

        // Assert
        Assert.assertEquals(fileContent, "test");

        // Act
        List<String> files = fileDao.getAllFileNames(PATH);

        // Assert
        Assert.assertEquals(files.size(), 4);
        Assert.assertEquals(files.get(0), fileNames.get(0));
        Assert.assertEquals(files.get(1), fileNames.get(1));
        Assert.assertEquals(files.get(2), fileNames.get(2));
        Assert.assertEquals(files.get(3), FILE_TO_ADD_NAME);
    }

    @Test
    public void givePath_whenFileDaoDeleteFile_thenFileDeleted() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        boolean fileAdded = fileDao.addFile(FILE_TO_ADD, "test");

        // Assert
        Assert.assertTrue(fileAdded);

        // Act
        List<String> files = fileDao.getAllFileNames(PATH);

        // Assert
        Assert.assertEquals(files.size(), 4);
        Assert.assertEquals(files.get(0), fileNames.get(0));
        Assert.assertEquals(files.get(1), fileNames.get(1));
        Assert.assertEquals(files.get(2), fileNames.get(2));
        Assert.assertEquals(files.get(3), FILE_TO_ADD_NAME);

        // Act
        boolean fileDeleted = fileDao.deleteFile(FILE_TO_DELETE);

        // Assert
        Assert.assertTrue(fileDeleted);

        // Act
        List<String> updatedFiles = fileDao.getAllFileNames(PATH);

        // Assert
        Assert.assertEquals(updatedFiles.size(), 3);
        Assert.assertEquals(updatedFiles.get(0), fileNames.get(0));
        Assert.assertEquals(updatedFiles.get(1), fileNames.get(1));
        Assert.assertEquals(updatedFiles.get(2), fileNames.get(2));
    }

    @Test
    public void givenPathAndFileName_whenFileDaoSearchFiles_thenFilesAreFound() {
        // Arrange
        String nameCriteriaXyz = "xyz";
        String nameCriteriaA = "a";
        String nameCriteriaB = "B";

        FileDao fileDao = new FileDaoImpl();

        // Act
        List<String> criteriaXyzResult = fileDao.searchFiles(PATH, nameCriteriaXyz);

        // Assert
        Assert.assertEquals(criteriaXyzResult.size(), 1);
        Assert.assertEquals(criteriaXyzResult.get(0), SAMPLE_FILE_NAME_THREE);

        // Act
        List<String> criteriaAResult = fileDao.searchFiles(PATH, nameCriteriaA);

        // Assert
        Assert.assertEquals(criteriaAResult.size(), 3);
        Assert.assertEquals(criteriaAResult.get(0), SAMPLE_FILE_NAME_ONE);
        Assert.assertEquals(criteriaAResult.get(1), SAMPLE_FILE_NAME_TWO);
        Assert.assertEquals(criteriaAResult.get(2), SAMPLE_FILE_NAME_THREE);

        // Act
        List<String> criteriaBResult = fileDao.searchFiles(PATH, nameCriteriaB);

        // Assert
        Assert.assertEquals(criteriaBResult.size(), 1);
        Assert.assertEquals(criteriaBResult.get(0), SAMPLE_FILE_NAME_TWO);
    }
}
