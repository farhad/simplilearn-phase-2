package simplilearn.farhadfaghihi.dao;

import simplilearn.farhadfaghihi.model.OperationResult;

import java.nio.file.Path;

public interface FileDao {
    OperationResult getAllFiles(Path path);

    OperationResult addFile(Path path, String fileContent);

    OperationResult deleteFile(Path path);

    OperationResult searchFiles(Path path, String fileName);
}
