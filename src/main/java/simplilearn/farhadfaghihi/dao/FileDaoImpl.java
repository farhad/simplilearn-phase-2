package simplilearn.farhadfaghihi.dao;

import simplilearn.farhadfaghihi.model.FileObject;
import simplilearn.farhadfaghihi.model.OperationResult;
import simplilearn.farhadfaghihi.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl implements FileDao {

    private static final String MESSAGE_SUCCESS = "operation successful";
    private static final String MESSAGE_FAILED = "operation failed";

    @Override
    public OperationResult getAllFiles(Path path) {
        File[] files = path.toFile().listFiles();

        if (files == null) {
            return new OperationResult(MESSAGE_SUCCESS, true, new ArrayList<>());
        }

        try {
            ArrayList<FileObject> fileObjects = new ArrayList<>();
            for (File item : files) {
                String nameAndExtension = item.getName();
                String lastModifiedTime = FileUtils.getFileLastModifiedTime(item);
                String size = FileUtils.getSizeInKB(item);
                FileObject fileObject = new FileObject(nameAndExtension, lastModifiedTime, size, item.isDirectory());
                fileObjects.add(fileObject);
            }

            QuickSorter<FileObject> quickSorter = new QuickSorter<>();
            List<FileObject> sortedList = quickSorter.sort(fileObjects);
            return new OperationResult(MESSAGE_SUCCESS, true, sortedList);

        } catch (Exception exception) {
            String message = MESSAGE_FAILED + " -> " + exception.getCause();
            return new OperationResult(message, false, new ArrayList<>());
        }
    }

    @Override
    public OperationResult addFile(Path path, String fileContent) {
        File file = path.toFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileContent.getBytes());
            return new OperationResult(MESSAGE_SUCCESS, true, new ArrayList<>());
        } catch (IOException exception) {
            String message = MESSAGE_FAILED + " -> " + exception.getCause();
            return new OperationResult(message, false, new ArrayList<>());
        }
    }

    @Override
    public OperationResult deleteFile(Path path) {
        try {
            Files.delete(path);
            return new OperationResult(MESSAGE_SUCCESS, true, new ArrayList<>());
        } catch (Exception exception) {
            String message = MESSAGE_FAILED + " -> " + exception.getCause();
            return new OperationResult(message, false, new ArrayList<>());
        }
    }

    @Override
    public OperationResult searchFiles(Path path, String fileName) {
        OperationResult allFilesResult = getAllFiles(path);

        if (allFilesResult.isSuccessful()) {
            ArrayList<FileObject> matchingFiles = new ArrayList<>();
            allFilesResult.getFileObjects().forEach(fileObj -> {
                if (fileObj.getNameAndExtension().contains(fileName.toLowerCase())) {
                    matchingFiles.add(fileObj);
                }
            });

            return new OperationResult(MESSAGE_FAILED, true, matchingFiles);
        } else {
            return new OperationResult(MESSAGE_FAILED, false, new ArrayList<>());
        }
    }
}
