import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AimTargets extends MouseAdapter {
    ArrayList<MyTarget> myTargetArrayList = new ArrayList<>();
    int index = 0;
    int score = 0;
    Thread randomTargetsThread;
    Thread targetRemoverThread;
    Thread timerThread;

    public AimTargets() {
        MainMenu.myFrame.setLayout(null);

        timerThread = new Thread(this::timer);
        timerThread.start();

        randomTargetsThread = new Thread(this::randomTargets);
        randomTargetsThread.start();

        targetRemoverThread = new Thread(this::removeTarget);
        targetRemoverThread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < myTargetArrayList.size(); i++) {
            if (e.getSource() == myTargetArrayList.get(i)) {
                myTargetArrayList.get(i).setBackground(null);
                score += 10;
            }
        }
    }

    public void randomTargets() {
        while (randomTargetsThread.isAlive() && !timerThread.isInterrupted()) {
            MyTarget myTarget = new MyTarget();
            myTarget.addMouseListener(this);
            myTargetArrayList.add(myTarget);
            index++;
            MainMenu.myFrame.add(myTargetArrayList.get(index - 1));
            SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);
            try {
                Thread.sleep(AimTrainerWindow.difficulty);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeTarget() {
        while (targetRemoverThread.isAlive() && !timerThread.isInterrupted()) {
            if (myTargetArrayList.size() > 4) {
                MainMenu.myFrame.remove(myTargetArrayList.get(0));
                myTargetArrayList.remove(0);
                index--;
                try {
                    Thread.sleep(AimTrainerWindow.difficulty);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void timer() {
        while (timerThread.isAlive() && !timerThread.isInterrupted()) {
            if(AimTrainerWindow.timer == 0) {
                timerThread.interrupt();
                System.out.println(score);
                MainMenu.myFrame.getContentPane().removeAll();
                MainMenu.myFrame.repaint();
                SwingUtilities.updateComponentTreeUI(MainMenu.myFrame);

            } else {
                AimTrainerWindow.timer--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}