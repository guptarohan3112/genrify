import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MP3Data {
    File file;
    InputStream input;
    ContentHandler handler = new DefaultHandler();
    Metadata metadata = new Metadata();
    Parser parser = new Mp3Parser();
    ParseContext parseCtx = new ParseContext();

    public MP3Data(File file) {
        this.file = file;
        try {
            input = new FileInputStream(file);
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

    public void printMeta() {
        // List all metadata
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
