import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * A custom FileHandler Class is used for reading files line by line.
 */
abstract class CustomFileHandler {

  /**
   * Reads the file and returns a list of String that
   * each String element is a line in the file.
   *
   * @param fileName the name of the file to be read in
   * @return List of String of the content of the file
   * @throws IOException if file is not found
   */
  static List<String> readFile(
      final String fileName) throws IOException {
    File file = new File(fileName.trim());
    List<String> lines = new ArrayList<>();
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(
            new FileInputStream(file)));
    String line = reader.readLine();
    while (line != null) {
      lines.add(line);
      line = reader.readLine();
    }
    reader.close();
    return lines;
  }

  /**
   * Writes the content to the file line by line.
   *
   * @param fileName the name path of the output file
   * @param content the name of the file to be read in
   * @throws IOException if file is not found
   */
  static void writeFile(final String fileName,
      final ArrayList<String> content)
      throws IOException {
    File file = new File(fileName.trim());
    FileOutputStream fos = new FileOutputStream(file);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

    for (String line : content) {
      bw.write(line);
      bw.newLine();
    }
    bw.close();
  }

  /**
   * Saves the Store to a file.
   *
   * @param fileName the name path of the output file
   * @param config the Store to be saved
   * @throws IOException if file is not found
   */
  static void saveConfiguration(final String fileName, final Configuration config)
      throws IOException {
    File file = new File(fileName.trim());
    FileOutputStream fos = new FileOutputStream(file);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(config);
  }

  /**
   * Reads the Store from a file.
   *
   * @param fileName the name path of the input file
   * @return the StoreConfiguration that has been loaded from a file
   * @throws IOException if file is not found
   * @throws ClassNotFoundException if no Store class is found
   */
  static Configuration readConfiguration(final String fileName)
      throws IOException, ClassNotFoundException {
    File file = new File(fileName.trim());
    FileInputStream fis = new FileInputStream(file);
    ObjectInputStream ois = new ObjectInputStream(fis);

    Configuration config = ((Configuration) ois.readObject());
    return config;
  }

  static void setOnlineStatus() throws IOException {
    writeFile("Status", new ArrayList<String>(Arrays.asList("1")));
  }

  static void setOfflineStatus() throws IOException {
    writeFile("Status", new ArrayList<String>(Arrays.asList("0")));
  }
}

