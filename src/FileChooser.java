import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser extends JPanel implements ActionListener {

    JButton chooseButton;
    JTextArea log;
    JFileChooser fc;
    File dir;

    public FileChooser(String label) {
        super(new BorderLayout());

        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooseButton = new JButton("choose this directory... (" + label + ")");

        chooseButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(chooseButton);

        add(panel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(FileChooser.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            dir = fc.getSelectedFile();
            log.append("Opening: " + dir.getName() + ".\n");
        } else {
            log.append("Open command cancelled by user.\n");
        }
        log.setCaretPosition(log.getDocument().getLength());
    }

    public String getDir() {
        return dir.getName();
    }
}
