package simplilearn.farhadfaghihi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simplilearn.farhadfaghihi.dao.FileDao;
import simplilearn.farhadfaghihi.dao.FileDaoImpl;
import simplilearn.farhadfaghihi.model.FileObject;
import simplilearn.farhadfaghihi.model.OperationResult;
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
        OperationResult allFilesResult = fileDao.getAllFiles(PATH);
        List<FileObject> files = allFilesResult.getFileObjects();

        // Assert
        Assert.assertTrue(allFilesResult.isSuccessful());

        Assert.assertEquals(files.size(), 3);

        Assert.assertEquals(files.get(0).getNameAndExtension(), fileNames.get(0));
        Assert.assertEquals(files.get(1).getNameAndExtension(), fileNames.get(1));
        Assert.assertEquals(files.get(2).getNameAndExtension(), fileNames.get(2));
    }

    @Test
    public void givenPathWithNoFilesOrFolders_whenFileDaoGetAllFiles_thenEmptyList() throws IOException {
        // Arrange
        deleteSampleFiles();

        FileDao fileDao = new FileDaoImpl();

        // Act
        OperationResult allFilesResult = fileDao.getAllFiles(PATH);
        List<FileObject> files = allFilesResult.getFileObjects();

        // Assert
        Assert.assertTrue(allFilesResult.isSuccessful());

        Assert.assertEquals(files.size(), 0);
    }

    @Test
    public void givenPath_whenFileDaoGetAllFiles_thenFileNamesAreSorted() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        OperationResult allFilesResult = fileDao.getAllFiles(PATH);
        List<FileObject> files = allFilesResult.getFileObjects();

        // Assert
        Assert.assertTrue(allFilesResult.isSuccessful());

        Assert.assertEquals(files.size(), 3);

        Assert.assertEquals(files.get(0).getNameAndExtension(), fileNames.get(0));
        Assert.assertEquals(files.get(1).getNameAndExtension(), fileNames.get(1));
        Assert.assertEquals(files.get(2).getNameAndExtension(), fileNames.get(2));
    }

    @Test
    public void givenPath_whenFileDaoAddFile_thenFileAdded() throws IOException {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        OperationResult addFileResult = fileDao.addFile(FILE_TO_ADD, "test");

        // Assert
        Assert.assertTrue(addFileResult.isSuccessful());

        // Act
        String fileContent = FileUtils.readFileContent(FILE_TO_ADD.toString());

        // Assert
        Assert.assertEquals(fileContent, "test");

        // Act
        OperationResult getAllFilesResult = fileDao.getAllFiles(PATH);
        List<FileObject> files = getAllFilesResult.getFileObjects();

        // Assert
        Assert.assertTrue(getAllFilesResult.isSuccessful());

        Assert.assertEquals(files.size(), 4);

        Assert.assertEquals(files.get(0).getNameAndExtension(), fileNames.get(0));
        Assert.assertEquals(files.get(1).getNameAndExtension(), fileNames.get(1));
        Assert.assertEquals(files.get(2).getNameAndExtension(), fileNames.get(2));
        Assert.assertEquals(files.get(3).getNameAndExtension(), FILE_TO_ADD_NAME);
    }

    @Test
    public void givePath_whenFileDaoDeleteFile_thenFileDeleted() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();

        // Act
        OperationResult addFileResult = fileDao.addFile(FILE_TO_ADD, "test");

        // Assert
        Assert.assertTrue(addFileResult.isSuccessful());

        // Act
        OperationResult getAllFilesResult = fileDao.getAllFiles(PATH);
        List<FileObject> files = getAllFilesResult.getFileObjects();

        // Assert
        Assert.assertTrue(getAllFilesResult.isSuccessful());

        Assert.assertEquals(files.size(), 4);

        Assert.assertEquals(files.get(0).getNameAndExtension(), fileNames.get(0));
        Assert.assertEquals(files.get(1).getNameAndExtension(), fileNames.get(1));
        Assert.assertEquals(files.get(2).getNameAndExtension(), fileNames.get(2));
        Assert.assertEquals(files.get(3).getNameAndExtension(), FILE_TO_ADD_NAME);

        // Act
        OperationResult fileDeleteResult = fileDao.deleteFile(FILE_TO_DELETE);

        // Assert
        Assert.assertTrue(fileDeleteResult.isSuccessful());

        // Act
        OperationResult updatedGetAllFiles = fileDao.getAllFiles(PATH);
        List<FileObject> updatedFiles = updatedGetAllFiles.getFileObjects();

        // Assert
        Assert.assertTrue(updatedGetAllFiles.isSuccessful());

        Assert.assertEquals(updatedFiles.size(), 3);

        Assert.assertEquals(updatedFiles.get(0).getNameAndExtension(), fileNames.get(0));
        Assert.assertEquals(updatedFiles.get(1).getNameAndExtension(), fileNames.get(1));
        Assert.assertEquals(updatedFiles.get(2).getNameAndExtension(), fileNames.get(2));
    }

    @Test
    public void givenInvalidPath_whenFileDaoDeleteFile_thenOperationFails() {
        // Arrange
        FileDao fileDao = new FileDaoImpl();
        Path invalidPath = Paths.get(PATH.toString() + "/invalid_file.txt");

        // Act
        OperationResult fileDeleteResult = fileDao.deleteFile(invalidPath);

        // Assert
        Assert.assertFalse(fileDeleteResult.isSuccessful());
    }

    @Test
    public void givenPathAndFileName_whenFileDaoSearchFiles_thenFilesAreFound() {
        // Arrange
        String criteriaXyz = "xyz";
        String criteriaA = "a";
        String criteriaB = "B";

        FileDao fileDao = new FileDaoImpl();

        // Act
        OperationResult searchResultXyz = fileDao.searchFiles(PATH, criteriaXyz);
        List<FileObject> criteriaXyzResult = searchResultXyz.getFileObjects();

        // Assert
        Assert.assertTrue(searchResultXyz.isSuccessful());

        Assert.assertEquals(criteriaXyzResult.size(), 1);
        Assert.assertEquals(criteriaXyzResult.get(0).getNameAndExtension(), SAMPLE_FILE_NAME_THREE);

        // Act
        OperationResult searchResultA = fileDao.searchFiles(PATH, criteriaA);
        List<FileObject> criteriaAResult = searchResultA.getFileObjects();

        // Assert
        Assert.assertTrue(searchResultA.isSuccessful());

        Assert.assertEquals(criteriaAResult.size(), 3);

        Assert.assertEquals(criteriaAResult.get(0).getNameAndExtension(), SAMPLE_FILE_NAME_ONE);
        Assert.assertEquals(criteriaAResult.get(1).getNameAndExtension(), SAMPLE_FILE_NAME_TWO);
        Assert.assertEquals(criteriaAResult.get(2).getNameAndExtension(), SAMPLE_FILE_NAME_THREE);

        // Act
        OperationResult searchResultB = fileDao.searchFiles(PATH, criteriaB);
        List<FileObject> criteriaBResult = searchResultB.getFileObjects();

        // Assert
        Assert.assertTrue(searchResultB.isSuccessful());

        Assert.assertEquals(criteriaBResult.size(), 1);
        Assert.assertEquals(criteriaBResult.get(0).getNameAndExtension(), SAMPLE_FILE_NAME_TWO);
    }
}
