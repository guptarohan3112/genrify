import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MP3Data {
    private final File file;
    private final Metadata metadata = new Metadata();

    public MP3Data(File file) {
        this.file = file;
        try {
            InputStream input = new FileInputStream(file);
            ParseContext parseCtx = new ParseContext();
            Parser parser = new Mp3Parser();
            ContentHandler handler = new DefaultHandler();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
        } catch (
                TikaException | IOException | SAXException e) {
            e.printStackTrace();
        }
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

    private String getTitle(){
        return metadata.get("title");
    }

    private String getArtist(){
        return metadata.get("xmpDM:artist");
    }

    private String getComposer(){
        return metadata.get("xmpDM:composer");
    }

    private String getGenre(){
        return metadata.get("xmpDM:genre");
    }

    private String getAlbum(){
        return metadata.get("xmpDM:album");
    }

    public String getCat(Sort sort){
        return switch (sort) {
            case genre -> getGenre();
            case album -> getAlbum();
            case artist -> getArtist();
            case composer -> getComposer();
        };
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
