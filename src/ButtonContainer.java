import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonContainer extends JPanel implements ActionListener {
    private final JRadioButton genreButton, artistButton, albumButton, composerButton;
    private Sort sort;
    private final FileChooser fileChooser;
    private final FileChooser destChooser;

    public ButtonContainer(FileChooser fileChooser, FileChooser destChooser) {

        this.fileChooser = fileChooser;
        this.destChooser = destChooser;

        genreButton = new JRadioButton("Genre");
        genreButton.setToolTipText("Sorts based on genre");
        genreButton.setSelected(true);

        artistButton = new JRadioButton("Artist");
        artistButton.setToolTipText("Sorts based on artist");

        albumButton = new JRadioButton("Album");
        albumButton.setToolTipText("Sorts based on album");

        composerButton = new JRadioButton("Composer");
        composerButton.setToolTipText("Sorts based on composer");

        ButtonGroup group = new ButtonGroup();
        group.add(genreButton);
        group.add(artistButton);
        group.add(albumButton);
        group.add(composerButton);
        JButton select = new JButton("SELECT");
        select.addActionListener(this);

        genreButton.setActionCommand("genre");
        artistButton.setActionCommand("artist");
        albumButton.setActionCommand("album");
        composerButton.setActionCommand("composer");

        add(genreButton);
        add(artistButton);
        add(albumButton);
        add(composerButton);
        add(select);
        setBackground(Color.blue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (genreButton.isSelected()) sort = Sort.genre;
        if (artistButton.isSelected()) sort = Sort.artist;
        if (albumButton.isSelected()) sort = Sort.album;
        if (composerButton.isSelected()) sort = Sort.composer;

        try {
            Main.sortFolder(fileChooser.getDir(), destChooser.getDir(), sort);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
