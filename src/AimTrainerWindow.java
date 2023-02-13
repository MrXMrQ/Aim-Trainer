import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AimTrainerWindow extends MouseAdapter {

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
    Thread updateThread;

    //Other
    public static int difficulty;
    boolean countDownAfterEnterFinished = false;
    boolean countDownTargets = true;
    public static int timer;
    ArrayList<JPanel> targetList = new ArrayList<>();
    int currentElement = 0;
    int score = 0;
    int missed;
    int totalClicks;

    public AimTrainerWindow() {
        aimThread = new Thread(this::aimTrainerWindow);
        aimThread.start();

        countDownEnterThread = new Thread(this::countDownEnter);

        countDownAimTrainerThread = new Thread(this::countDownAimTrainer);

        targetsForAimTrainerThread = new Thread(this::targetsForAimTrainer);

        updateThread = new Thread(this::update);
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
                targetsForAimTrainerThread.start();
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

    public void targetsForAimTrainer() {
        MainMenu.myFrame.dispose();
        targetWindow = new MyFrame();
        targetWindow.setLayout(null);
        countDownAimTrainerThread.start();

        while (countDownTargets) {
            try {
                if (targetList.size() == 5) {
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

    public void countDownAimTrainer() {
        updateThread.start();
        targetWindow.setTitle(timer + " seconds, score: " + score + " , clicks missed: " + (totalClicks - missed) + " , total clicks: " + totalClicks);

        while (timer >= 0) {
            try {
                targetWindow.setTitle(timer + " seconds, score: " + score + " , clicks missed: " + (totalClicks - missed) + " , total clicks: " + totalClicks);
                timer--;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (timer <= 0) {
                countDownTargets = false;
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
        target.addMouseListener(this);
        targetList.add(target);

        for (int i = 0; i < targetList.size(); i++) {
            if (targetList.indexOf(i) == target.getX()) {
                targetList.remove(target);
                targetGen();
            } else {
                return targetList.get(currentElement);
            }
        }
        return null;
    }

    public void update() {
        while (updateThread.isAlive()) {
            SwingUtilities.updateComponentTreeUI(targetWindow);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < targetList.size(); i++) {
            if (e.getSource() == targetList.get(i)) {
                score+=10;
                totalClicks++;
                targetList.get(i).setBackground(null);
            } else {
                missed++;
                totalClicks++;
            }
        }
    }
}