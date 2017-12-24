package fileio;
import java.nio.file.*;
import java.io.*;

public class DirectoryListingExample {
    public static void main(String[] args) {
        printFilesInFolder("dir");
    }

    /** Prints the names of all files in a given folder. Does *not* go into subfolders */
    public static void printFilesInFolder(String directory) {
        Path path = Paths.get(directory);
        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path)) {
            for (Path file: filesList) {
                // process the file
                System.out.println(file);
            }
        }
       catch (IOException e) {
           System.out.println("Could not print the contents of the following folder: " + directory);
       }

    }

}
