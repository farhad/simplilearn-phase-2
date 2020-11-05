package simplilearn.farhadfaghihi.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    public static String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }
}
