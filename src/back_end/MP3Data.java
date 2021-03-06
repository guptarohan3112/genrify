package back_end;

import back_end.Data;
import org.apache.tika.parser.mp3.Mp3Parser;

import java.io.File;

public class MP3Data extends Data {

    public MP3Data(File file) {
        super(file, new Mp3Parser());
    }

    public File getFile() {
        return this.file;
    }

    @SuppressWarnings("unused")
    public void printMeta() {
        // List all metadata, used for debugging
        String[] metadataNames = metadata.names();
        for (String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
    }

    public String getTitle(){
        return metadata.get("title");
    }

    public String getArtist(){
        return metadata.get("xmpDM:artist");
    }

    public String getComposer(){
        return metadata.get("xmpDM:composer");
    }

    public String getGenre(){
        return metadata.get("xmpDM:genre");
    }

    public String getAlbum(){
        return metadata.get("xmpDM:album");
    }

    public String getCat(Sort sort){
        switch (sort) {
            case genre : return getGenre();
            case artist : return getArtist();
            case composer : return getComposer();
            case album : return getAlbum();
            default: return "undefined";
        }
    }

    @Override
    public String toString() {
        // Retrieve the necessary info from metadata
        // Names - title, xmpDM:artist etc. - mentioned below may differ based
        return "\n----------------------------------------------" +
                "\nTitle: " + getTitle() +
                "\nArtists: " + getArtist() +
                "\nComposer: " + getComposer() +
                "\nGenre: " + getGenre() +
                "\nAlbum: " + getAlbum();
    }
}
