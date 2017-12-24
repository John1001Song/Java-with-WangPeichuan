package exceptions;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/** Demonstrates how to use try with resources in Java. Shows that we can have several catch blocks after try.
 *  More specific exception should be caught before the more general one.
 *  I encourage you to edit the code: remove try/catch from readUsingNewBufferedReader,
 *  add a throws clause to the signature of readUsingNewBufferedReader, and catch IOException in the main method.
 */
public class CheckedExceptionExample {
    public static void main(String[] args) {
        readUsingNewBufferedReader("dir/lyrics.txt");

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
            System.out.println("IOException"); //Could not read from the file: " + filename);
        } catch (Exception e) {
            System.out.println("Exception"); //Some exception occured : " + e);

        }
    }

}
