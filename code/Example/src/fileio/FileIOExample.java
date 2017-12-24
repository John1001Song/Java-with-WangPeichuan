package fileio;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


/** Demonstrates file input / output in Java */
public class FileIOExample {
    public static void main(String[] args) {

        readUsingBufferedReader("dir/lyrics.txt");
        // readUsingNewBufferedReader("dir/lyrics.txt");

        //readUsingStreams("dir/lyrics.txt");
        // readWrite("dir/lyrics.txt", "dir/output.txt");
    }

    /** Use Files.newBufferedReader to read every line from the given file
     *  and print it to the console.
     *  You need to know the encoding of the file you are reading (UTF-8 in this case).
     */
    public static void readUsingNewBufferedReader(String filename) {
        Path path = Paths.get(filename);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /** Wrap InputStreamReader in a BufferedReader.
     *  Read lines from the file and output each line to the console.
     *  Note that you can also pass FileReader to the BufferedReader, but FileReader does not allow to specify Charset.*/
    public static void readUsingBufferedReader(String filename) {
        try (BufferedReader  reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch(IOException e) {
             System.out.println(e);
        }


    }

    /** Use Java 8 streams to read from the file.
     *  Will talk more about Java 8 features at the end of the semester. */
   public static void readUsingStreams(String fileName) {
       Path path = Paths.get(fileName);
        try (Stream<String> stream = Files.lines(path)) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Read from one file and write to another */
    public static void readWrite(String inputFile, String outputFile) {
        Path inputPath = Paths.get(inputFile);
        Path outputPath = Paths.get(outputFile);

        try (PrintWriter pw = new PrintWriter(outputPath.toString()); BufferedReader reader = Files.newBufferedReader(inputPath, Charset.forName("UTF-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                pw.println(line);
            }
            pw.flush();
        } catch (IOException e) {
            System.out.println(e);
        }

    }



}
