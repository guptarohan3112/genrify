import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main{

    public static String[] fixCat(String category){
        category = category.replaceAll("\\\\", "&");
        category = category.replaceAll(":", "_");
        return category.split("/");
    }


    public static String fixPath(String dir) {
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

        List<Data> store = new ArrayList<>();

        /* Read in all files from unsorted directory */
        List<File> filesInFolder = Files.walk(Paths.get(folderLocation))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        /* Get MP3 data for each file and print */
        for (File file : filesInFolder) {
            store.add(new MP3Data(file));
        }

        for (Data data : store) {
            System.out.println(data);
        }

        /* if output folder does not exist, create it */
        createFolder(outputFolder);

        /* First obtain set categories and create folders for each one */
        Set<String> categories = new HashSet<>();

        for (Data data : store) {
            if (data.getCat(sort) == null || data.getCat(sort).isEmpty()) {
                categories.add("undefined");
            } else {
                String category = data.getCat(sort);
                Collections.addAll(categories, fixCat(category));
            }
        }

        for (String category : categories) {
            createFolder(outputFolder + '/' + category);
        }

        /* for each category, traverse list of store and copy if matches */
        for (String category : categories) {

            for (Data data : store) {
                /* allows for splitting by '/' character */
                Set<String> cats = new HashSet<>();
                String thisCategory;

                if (data.getCat(sort) == null) {
                    thisCategory = "undefined";
                    cats.add(thisCategory);
                } else {
                    thisCategory = data.getCat(sort);
                    Collections.addAll(cats, fixCat(thisCategory));
                }


                for (String cat : cats) {
                    if (cat.equals(category)) {
                        String fileName = data.getFile().getName();
                        String origName = folderLocation + '/' + fileName;
                        String outName = outputFolder + '/' + category + '/' + fileName;
                        copyFile(origName, outName);
                    }
                }
            }
        }
    }
}
