package simplilearn.farhadfaghihi.dao;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl implements FileDao {

    @Override
    public List<String> getAllFileNames(String currentDirectory) {
        return new ArrayList<>();
    }

    @Override
    public boolean addFile(String fileName, String fileContent) {
        return false;
    }

    @Override
    public boolean deleteFile(String fileName) {
        return false;
    }

    @Override
    public ArrayList<String> searchFiles(String fileName) {
        return null;
    }
}
