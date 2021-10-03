import javax.swing.*;
import java.awt.*;

public class Simulation {
    int[][] population;
    JButton[][] board;
    JButton evolveButton;
    JButton stopButton;
    boolean isOn;
    JFrame frame;
    JPanel panel;
    /**
     * Each cell has an initial state:
     * live (represented by a 1) or dead (represented by a 0).
     * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following
     * four rules (taken from the Wikipedia article <a href="https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life">
     *     Game of Life</a>):
     * <br>
     *      1. Any live cell with fewer than two live neighbors dies as if caused by under-population. <br>
     *      2. Any live cell with two or three live neighbors lives on to the next generation. <br>
     *      3. Any live cell with more than three live neighbors dies, as if by over-population. <br>
     *      4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction. <br>
     * The next state is created by applying the above algorithms simultaneously to every cell in the current state,
     * where births and deaths occur simultaneously.**/

    public Simulation()
    {
        population=new int[100][56];
        board=new JButton[population.length][population[0].length];
        frame=new JFrame("Conway's Game of Life");
        frame.setSize(1515,930);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        panel=new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(null);
        evolveButton=new JButton("Start Simulation!");
        evolveButton.setBounds(540,840,200,50);
        stopButton=new JButton("Stop Simulation!");
        stopButton.setBounds(800,840,200,50);
        stopButton.setBackground(Color.white);
        panel.add(evolveButton);
        panel.add(stopButton);
        stopButton.addActionListener(e -> {isOn=false; frame.dispose();});
        evolveButton.addActionListener(e -> {
                evolveContinuously();
        });
        stopButton.setBorderPainted(false);
        evolveButton.setBorderPainted(true);
        evolveButton.setBackground(Color.white);
        initializeButtons();
        frame.add(panel);
        isOn=true;
        frame.setVisible(true);

    }
    private void initializeButtons()
    {
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board[0].length;j++) {
                board[i][j] = new JButton();
                int finalI=i, finalJ=j;
                board[finalI][finalJ].setBounds(finalI*15,finalJ*15,15,15);
                panel.add(board[finalI][finalJ]);
                board[i][j].setBackground(Color.lightGray);
                board[i][j].addActionListener(e -> {
                    board[finalI][finalJ].setBackground(new Color(69, 222, 207));
                    population[finalI][finalJ]=1;
                });
            }
    }
    private void evolveContinuously()
    {
        Thread thread=new Thread(() -> {
            while(isOn) {
                evolve();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }
    private void evolve() {
       // System.out.println("HELO");
        int m = population.length, n= population[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int sum = 0;
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (k >= 0 && k < m && l >= 0 && l < n) {
                                if (population[k][l] == 2) // Tagged as 2 means originally it was a 0.
                                    sum += 0;
                                else if (population[k][l] == 3) //Tagged as 3 means it was originally a 1.
                                    sum++;
                                else
                                    sum += population[k][l];
                            }
                        }
                    }
                    sum -= population[i][j];
                    if (population[i][j] != 0 && (sum <= 1 || sum >= 4))
                        population[i][j] = 3;
                    if (population[i][j] == 0 && sum == 3)
                        population[i][j] = 2;
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (population[i][j] == 2) {
                        population[i][j] = 1;
                        board[i][j].setBackground(new Color(69, 222, 207));
                    } else if (population[i][j] == 3) {
                        population[i][j] = 0;
                        board[i][j].setBackground(Color.lightGray);
                    }
                }
            }

    }

    public static void main(String[] args) {
        new Simulation();
    }

}
