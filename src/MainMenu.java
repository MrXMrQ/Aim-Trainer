import javax.swing.*;
import java.awt.*;

public class MainMenu {
    //Frame
    MyFrame myFrame;

    //Panel
    JPanel panelNORTH;
    JPanel panelCENTER;

    //Label
    JLabel labelHeadline;

    public MainMenu() {
        mainMenuWindow();
    }

    public void mainMenuWindow() {
        myFrame = new MyFrame();
        myFrame.setLayout(new BorderLayout());

        panelNORTH = new JPanel(new FlowLayout());

        labelHeadline = new JLabel("Select the difficulty", SwingUtilities.CENTER);
        labelHeadline.setFont(new Font("Inter", Font.BOLD, 50));
        panelNORTH.add(labelHeadline);
        panelNORTH.setPreferredSize(new Dimension(0, 200));


        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(30);

        panelCENTER = new JPanel(flowLayout);

        JButton noobButton = new JButton("Noob");
        noobButton.setPreferredSize(new Dimension(100, 25));
        noobButton.addActionListener(e -> {

        });
        panelCENTER.add(noobButton);

        JButton easyButton = new JButton("Easy");
        easyButton.setPreferredSize(new Dimension(100, 25));
        panelCENTER.add(easyButton);

        JButton normalButton = new JButton("Normal");
        normalButton.setPreferredSize(new Dimension(100, 25));
        panelCENTER.add(normalButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setPreferredSize(new Dimension(100, 25));
        panelCENTER.add(hardButton);


        myFrame.add(panelNORTH, BorderLayout.NORTH);
        myFrame.add(panelCENTER, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }
}
