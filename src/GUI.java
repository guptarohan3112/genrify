import javax.swing.*;
import java.awt.*;

class GUI{
    public static void main(String args[]){
        JFrame frame = new JFrame("Genrify");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,350);
        FileChooser fileChooser = new FileChooser("Source files");
        FileChooser destChooser = new FileChooser("Destination folder");
        ButtonContainer buttonPanel = new ButtonContainer(fileChooser, destChooser);

        frame.getContentPane().add(fileChooser, BorderLayout.NORTH);
        frame.getContentPane().add(destChooser, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}