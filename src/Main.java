import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    public static void createFolder(String folder) {

        String nullCheck = folder.substring(folder.lastIndexOf('/') + 1).trim();
        if (folder.isEmpty() || nullCheck.equals("null")) {
            String root = folder.substring(0, folder.lastIndexOf('/'));
            folder = root + '/' + "undefined";
        }

        Path path = Paths.get(folder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Created directory: " + folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(String original, String output) throws IOException {
        File source = new File(original);
        File dest = new File(output);
        Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Please rerun with two arguments (read and output folder)");
            System.exit(1);
        }

        List<MP3Data> music = new ArrayList<>();
        /* first arg is unsorted folder */
        String folderLocation = args[0];
        /* second arg is output folder */
        String outputFolder = args[1];

        /* Read in all files from unsorted directory */
        List<File> filesInFolder = Files.walk(Paths.get(folderLocation))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        /* Get MP3 data for each file and print */
        for (File file : filesInFolder) {
            music.add(new MP3Data(file));
        }

        for (MP3Data mp3 : music) {
            System.out.println(mp3);
        }

        /* if output folder does not exist, create it */
        createFolder(outputFolder);

        /* First obtain list of genres and create folders for each one */
        Set<String> categories = new HashSet<>();

        for (MP3Data mp3 : music) {
            String genre;
            if (mp3.getGenre() == null) {
                categories.add("undefined");
            } else {
                categories.add(mp3.getGenre());
            }
        }

        for (String category : categories) {
            createFolder(outputFolder + '/' + category);
        }

        /* for each genre, traverse list of music and copy if matches */
        for (String category : categories) {
            for (MP3Data mp3 : music) {
                String genre;
                if (mp3.getGenre() == null) {
                    genre = "undefined";
                } else {
                    genre = mp3.getGenre();
                }
                if (genre.equals(category)) {
                    String origName = folderLocation + '/' + mp3.getFile().getName();
                    String outName = outputFolder + '/' + category + '/' + mp3.getFile().getName();
                    copyFile(origName, outName);
                }
            }
        }

    }
}
