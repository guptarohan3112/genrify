import com.google.protobuf.AbstractParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class Data {
    protected final File file;
    protected Metadata metadata = new Metadata();

    public Data(File file, Parser parser) {
        this.file = file;
        try {
            InputStream input = new FileInputStream(file);
            ParseContext parseCtx = new ParseContext();
            ContentHandler handler = new DefaultHandler();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
        } catch (
                TikaException | IOException |
                        SAXException e) {
            e.printStackTrace();
        }
    }

    public abstract File getFile();

    public abstract void printMeta();

    public abstract String getTitle();
    public abstract String getComposer();
    public abstract String getGenre();
    public abstract String getAlbum();
    public abstract String getCat(Sort sort);

}


