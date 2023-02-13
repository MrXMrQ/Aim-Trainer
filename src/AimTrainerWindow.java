import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AimTrainerWindow {

    MyFrame targetWindow;
    //Panel
    JPanel panelNORTH;
    JPanel panelCENTER;

    //Label
    JLabel labelPressEnter;
    JLabel countDown;

    //Thread
    Thread aimThread;
    Thread countDownEnterThread;
    Thread countDownAimTrainerThread;
    Thread targetsForAimTrainerThread;

    //Other
    public static int difficulty;
    boolean countDownAfterEnterFinished = false;
    boolean countDownTargets = true;
    public static int timer;
    ArrayList<JPanel> targetList = new ArrayList<>();
    int currentElement = 0;

    public AimTrainerWindow() {
        aimThread = new Thread(this::aimTrainerWindow);
        aimThread.start();

        countDownEnterThread = new Thread(this::countDownEnter);

        countDownAimTrainerThread = new Thread(this::countDownAimTrainer);

        targetsForAimTrainerThread = new Thread(this::targetsForAimTrainer);
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
                countDownAimTrainerThread.start();
                countDownAfterEnterFinished = false;
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

    public void countDownAimTrainer() {
        JLabel labelTimer = new JLabel(timer + "  seconds");
        labelTimer.setFont(new Font("Inter", Font.BOLD, 50));
        panelNORTH.add(labelTimer);
        targetsForAimTrainerThread.start();
        while (timer >= 0) {
            try {
                labelTimer.setText(timer + " seconds");
                timer--;
                SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (timer <= 0) {
                countDownTargets = false;
            }
        }
    }

    public void targetsForAimTrainer() {
        MainMenu.myFrame.dispose();
        targetWindow = new MyFrame();
        targetWindow.setLayout(null);

        while (countDownTargets) {
            try {
                if(targetList.size() == 5) {
                    targetWindow.remove(targetList.get(0));
                    targetList.remove(0);
                    currentElement--;
                    SwingUtilities.updateComponentTreeUI(targetWindow);
                } else {
                    targetWindow.add(targetGen());
                    currentElement++;
                    SwingUtilities.updateComponentTreeUI(targetWindow);
                    Thread.sleep(difficulty);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JPanel targetGen() {
        int randomX = (int) (Math.random() * 780);
        int randomY = (int) (Math.random() * 480);
        int randomBox = (int) (Math.random() * (100 - 50)) + 50;

        JPanel target = new JPanel();
        target.setBounds(randomX, randomY, randomBox, randomBox);
        target.setBackground(Color.BLACK);

        targetList.add(target);

        return targetList.get(currentElement);
    }
}