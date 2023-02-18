import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AimTrainerWindow extends MouseAdapter {
    //Panel
    JPanel panelNORTH;
    JPanel panelCENTER;

    //Label
    JLabel labelPressEnter;
    JLabel countDown;

    //Thread
    Thread aimThread;
    Thread countDownEnterThread;

    //Other
    public static int difficulty;
    boolean countDownAfterEnterFinished = false;
    public static int timer;

    public AimTrainerWindow() {
        aimThread = new Thread(this::aimTrainerWindow);
        aimThread.start();

        countDownEnterThread = new Thread(this::countDownEnter);
    }

    public void aimTrainerWindow() {
        panelNORTH = new JPanel(new FlowLayout());
        panelNORTH.setBackground(new Color(0, 0, 0, 125));

        labelPressEnter = new JLabel("Pleas type Enter", SwingUtilities.CENTER);
        labelPressEnter.setFont(new Font("Inter", Font.BOLD, 50));
        labelPressEnter.setForeground(Color.CYAN);
        panelNORTH.add(labelPressEnter);

        panelCENTER = new JPanel();
        panelCENTER.setBackground(new Color(0, 0, 0, 125));
        MainMenu.myFrame.add(panelCENTER, BorderLayout.CENTER);

        MainMenu.myFrame.add(panelNORTH, BorderLayout.NORTH);
        SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);

        MainMenu.myFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    panelNORTH.removeAll();
                    panelNORTH.setBackground(new Color(0, 0, 0, 0));
                    panelCENTER.setBackground(new Color(0, 0, 0, 0));
                    SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                    MainMenu.myFrame.removeKeyListener(this);
                    countDownEnterThread.start();
                }
            }
        });

        while (aimThread.isAlive()) {
            if (countDownAfterEnterFinished) {
                countDownAfterEnterFinished = false;
                aimThread.interrupt();
                countDownEnterThread.interrupt();
                new AimTargets();

            }
        }
    }

    public void countDownEnter() {
        countDown = new JLabel("3");
        countDown.setFont(new Font("Inter", Font.BOLD, 100));
        panelNORTH.add(countDown);
        SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);

        for (int i = 3; i >= 0; i--) {
            try {
                countDown.setText(i + "");
                SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                Thread.sleep(1000);
                if (i == 0) {
                    panelNORTH.removeAll();
                    SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                    countDownAfterEnterFinished = true;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}