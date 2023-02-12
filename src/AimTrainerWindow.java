import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AimTrainerWindow{
    //Panel
    JPanel panelNORTH;

    //Label
    JLabel labelPressEnter;
    JLabel countDown;

    //Thread
    Thread aimThread;
    Thread countDownThread;

    //Other
    public static  int difficulty;
    boolean countDownFinished = false;

    public AimTrainerWindow() {
        aimThread = new Thread(this::aimTrainerWindow);
        aimThread.start();

        countDownThread = new Thread(this::countDown);

    }

    public void aimTrainerWindow() {
        panelNORTH = new JPanel(new FlowLayout());
        panelNORTH.setBackground(new Color(0,0,0,125));

        labelPressEnter = new JLabel("Pleas type Enter", SwingUtilities.CENTER);
        labelPressEnter.setFont(new Font("Inter", Font.BOLD, 50));
        labelPressEnter.setForeground(Color.CYAN);
        panelNORTH.add(labelPressEnter);

        MainMenu.myFrame.add(panelNORTH);
        SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);

        MainMenu.myFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    panelNORTH.removeAll();
                    panelNORTH.setBackground(new Color(0,0,0,0));
                    SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                    MainMenu.myFrame.removeKeyListener(this);
                    countDownThread.start();
                }
            }
        });
    }

    public void countDown() {
        countDown = new JLabel("3");
        countDown.setFont(new Font("Inter", Font.BOLD,100));
        panelNORTH.add(countDown);
        SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);

        for (int i = 3; i >= 0; i--) {
            try {
                System.out.println(i);
                countDown.setText(i + "");
                SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                Thread.sleep(1000);
                if(i == 0) {
                    panelNORTH.removeAll();
                    SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                    countDownFinished = true;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
