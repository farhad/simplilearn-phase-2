package simplilearn.farhadfaghihi.dao;

import java.util.ArrayList;
import java.util.List;

public interface FileDao {
    List<String> getAllFileNames(String currentDirectory);

    boolean addFile(String fileName, String fileContent);

    boolean deleteFile(String fileName);

    List<String> searchFiles(String fileName);
}
