package simplilearn.farhadfaghihi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {

    /**
     * Returns the content of a text file specified by path
     *
     * @param filePath -> absolute file path
     * @return
     * @throws IOException
     */
    public static String readFileContent(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder result = new StringBuilder(bufferedReader.readLine());
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line).append(System.lineSeparator());
        }
        bufferedReader.close();
        return result.toString();
    }

    /**
     * @return the current directory where the application started running
     */
    public static Path getCurrentWorkingDirectory() {
        return Paths.get(System.getProperty("user.dir"));
    }


    public static String getFileLastModifiedTime(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return attr.lastModifiedTime().toString();
    }

    public static String getSizeInKB(File file) {
        long lengthKB = file.length() / 1024;
        return lengthKB + " KB";
    }
}
