package simplilearn.farhadfaghihi.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl implements FileDao {

    @Override
    public List<String> getAllFileNames(Path path) {
        File[] files = path.toFile().listFiles();

        if (files == null) {
            return new ArrayList<>();
        }

        ArrayList<String> fileNames = new ArrayList<>();
        for (File item : files) {
            fileNames.add(item.getName());
        }

        QuickSorter<String> quickSorter = new QuickSorter<>();
        return quickSorter.sort(fileNames);
    }

    @Override
    public boolean addFile(Path path, String fileContent) {
        File file = path.toFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileContent.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<String> searchFiles(Path path, String fileName) {
        List<String> allFiles = getAllFileNames(path);
        ArrayList<String> matchingFiles = new ArrayList<>();
        allFiles.forEach(name -> {
            if (name.toLowerCase().contains(fileName.toLowerCase())) {
                matchingFiles.add(name);
            }
        });

        return matchingFiles;
    }
}
