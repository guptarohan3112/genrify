import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {


        public static String fixPath(String dir){
            dir = dir.replaceAll(" ", "_");
            dir = dir.replaceAll("<", "_");
            dir = dir.replaceAll(">", "_");
            return dir;
        }

    public static void createFolder(String folder) {
        String nullCheck = folder.substring(folder.lastIndexOf('/') + 1).trim();
        if (folder.isEmpty() || nullCheck.equals("null")) {
            String root = folder.substring(0, folder.lastIndexOf('/'));
            folder = root + '/' + "undefined";
        }

        folder = fixPath(folder);
        Path path = Paths.get(folder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("\nCreated directory: " + folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(String original, String output) throws IOException {
        File source = new File(original);
        output = fixPath(output);
        File dest = new File(output);
        Files.copy(source.toPath(), dest.toPath(), REPLACE_EXISTING);
    }

    public static void sortFolder(String folderLocation, String outputFolder, Sort sort) throws IOException {

        List<MP3Data> music = new ArrayList<>();

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

        /* First obtain set categories and create folders for each one */
        Set<String> categories = new HashSet<>();

        for (MP3Data mp3 : music) {
            if (mp3.getCat(sort) == null || mp3.getCat(sort).isEmpty()) {
                categories.add("undefined");
            } else {
                String category = mp3.getCat(sort);
                category = category.replaceAll("/", "&");
                category = category.replaceAll("\\\\", "&");
                category = category.replaceAll(":", "_");
                categories.add(category);
            }
        }

        for (String category : categories) {
            createFolder(outputFolder + '/' + category);
        }

        /* for each category, traverse list of music and copy if matches */
        for (String category : categories) {
            for (int i = 0; i < music.size(); i++) {
                String thisCategory;
                if (music.get(i).getCat(sort) == null) {
                    thisCategory = "undefined";
                } else {
                    thisCategory = music.get(i).getCat(sort);
                    thisCategory = thisCategory.replaceAll("/", "&");
                    thisCategory = thisCategory.replaceAll("\\\\", "&");
                    thisCategory = thisCategory.replaceAll(":", "_");

                }
                if (thisCategory.equals(category)) {
                    String fileName = music.get(i).getFile().getName();
                    String origName = folderLocation + '/' + fileName;
                    String outName = outputFolder + '/' + category + '/' + fileName;
                    copyFile(origName, outName);
                    music.remove(i);
                }
            }
        }
    }
}
