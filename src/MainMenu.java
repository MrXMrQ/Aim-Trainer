import javax.swing.*;
import java.awt.*;

public class MainMenu {
    //Frame
    public static MyFrame myFrame;

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
            remover();
            AimTrainerWindow.difficulty = 3000;
            new AimTrainerWindow();
        });
        panelCENTER.add(noobButton);

        JButton easyButton = new JButton("Easy");
        easyButton.setPreferredSize(new Dimension(100, 25));
        easyButton.addActionListener(e -> {
            remover();
            AimTrainerWindow.difficulty = 2000;
            new AimTrainerWindow();
        });
        panelCENTER.add(easyButton);

        JButton normalButton = new JButton("Normal");
        normalButton.setPreferredSize(new Dimension(100, 25));
        normalButton.addActionListener(e -> {
            remover();
            AimTrainerWindow.difficulty = 1000;
            new AimTrainerWindow();
        });
        panelCENTER.add(normalButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setPreferredSize(new Dimension(100, 25));
        hardButton.addActionListener(e -> {
            remover();
            AimTrainerWindow.difficulty = 500;
            new AimTrainerWindow();
        });
        panelCENTER.add(hardButton);


        myFrame.add(panelNORTH, BorderLayout.NORTH);
        myFrame.add(panelCENTER, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }

    public void remover() {
        myFrame.getContentPane().removeAll();
        myFrame.repaint();
        SwingUtilities.updateComponentTreeUI(myFrame);
        myFrame.requestFocus();
    }
}
