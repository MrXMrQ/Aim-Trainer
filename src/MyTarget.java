import javax.swing.*;
import java.awt.*;

public class MyTarget extends JLabel {
    public MyTarget() {
        int box = (int)(Math.random() * (75-40) + 40);
        setBounds((int)(Math.random() * MainMenu.myFrame.getWidth()),(int)(Math.random() * MainMenu.myFrame.getHeight()),box,box);
        setBackground(Color.GRAY);
        setOpaque(true);
    }
}
