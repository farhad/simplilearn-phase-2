package simplilearn.farhadfaghihi.dao;

import java.nio.file.Path;
import java.util.List;

public interface FileDao {
    List<String> getAllFileNames(Path path);

    boolean addFile(Path path, String fileContent);

    boolean deleteFile(Path path);

    List<String> searchFiles(Path path, String fileName);
}
