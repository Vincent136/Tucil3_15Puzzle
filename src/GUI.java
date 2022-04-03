import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {
    // attribut object yang digunakan pada GUI
    JFrame frame = new JFrame("Puzzle15GUI");
    JButton s1 = new JButton("");
    JButton s2 = new JButton("");
    JButton s3 = new JButton("");
    JButton s4 = new JButton("");
    JButton s5 = new JButton("");
    JButton s6 = new JButton("");
    JButton s7 = new JButton("");
    JButton s8 = new JButton("");
    JButton s9 = new JButton("");
    JButton s10 = new JButton("");
    JButton s11 = new JButton("");
    JButton s12 = new JButton("");
    JButton s13 = new JButton("");
    JButton s14 = new JButton("");
    JButton s15 = new JButton("");
    JButton s16 = new JButton("");

    JButton StartBtn =  new JButton("Start");
    JButton zeroButton;
    JButton prevButton;
    JButton nextButton;
    JButton lastButton;
    JButton playButton;

    JTextArea kurang = new JTextArea();
    JTextField FileName = new JTextField(10);

    JLabel label = new JLabel("Insert file name: ");
    JLabel Step = new JLabel();
    JLabel TotalBranch = new JLabel();
    JLabel TimeElapsed = new JLabel();

    JPanel PuzzlePanel = new JPanel();
    JPanel InputPanel = new JPanel();
    JPanel MainPanel = new JPanel();
    JPanel LeftPanel = new JPanel();
    JPanel RightPanel = new JPanel();
    JPanel PrevNextPanel = new JPanel();
    JPanel InfoPanel = new JPanel();

    java.util.List<Puzzle> output;
    java.util.List<Integer> outputKurang;
    int idxNow = 0;

    // ctor GUI untuk menyipakan objek objek dan menampilkan window GUI ke layar
    public GUI() {
        MainPanel.setLayout(new GridLayout(0,3));
        LeftPanel.setLayout(new GridLayout(5,0));
        InputPanel.add(label);
        InputPanel.add(FileName);
        InputPanel.add(StartBtn);

        LeftPanel.add(new JPanel());
        LeftPanel.add(new JPanel());
        LeftPanel.add(InputPanel);

        zeroButton = new JButton( new AbstractAction("<< prev") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                idxNow = 0;
                Step.setText("Step: " + idxNow + " / " + (output.size()-1));
                refreshPuzzle();
            }
        });

        prevButton = new JButton( new AbstractAction("prev") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (idxNow != 0) {
                    idxNow--;
                    Step.setText("Step: " + idxNow + " / " + (output.size()-1));
                }
                refreshPuzzle();
            }
        });

        nextButton = new JButton( new AbstractAction("next") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (idxNow != output.size()-1) {
                    idxNow++;
                    Step.setText("Step: " + idxNow + " / " + (output.size()-1));
                }
                refreshPuzzle();
            }
        });

        lastButton = new JButton( new AbstractAction("next >>") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                idxNow = output.size()-1;
                Step.setText("Step: " + idxNow + " / " + (output.size()-1));
                refreshPuzzle();
            }
        });

        playButton = new JButton( new AbstractAction("play") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                new Thread(new Runnable() {
                    public void run() {
                        zeroButton.setEnabled(false);
                        prevButton.setEnabled(false);
                        nextButton.setEnabled(false);
                        lastButton.setEnabled(false);
                        playButton.setEnabled(false);

                        for (int i = idxNow + 1; i < output.size(); i++) {
                            idxNow = i;

                            // Runs inside of the Swing UI thread
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    refreshPuzzle();
                                    Step.setText("Step: " + idxNow + " / " + (output.size()-1));
                                }
                            });

                            try {
                                java.lang.Thread.sleep(100);
                            }
                            catch(Exception e) {

                            }
                        }
                        idxNow = output.size() -1;
                        zeroButton.setEnabled(true);
                        prevButton.setEnabled(true);
                        nextButton.setEnabled(true);
                        lastButton.setEnabled(true);
                        playButton.setEnabled(true);
                    }
                }).start();
            }

        });

        PrevNextPanel.add(zeroButton);
        PrevNextPanel.add(prevButton);
        PrevNextPanel.add(nextButton);
        PrevNextPanel.add(lastButton);
        PrevNextPanel.add(playButton);

        LeftPanel.add(PrevNextPanel);

        InfoPanel.add(Step);
        InfoPanel.add(TotalBranch);
        InfoPanel.add(TimeElapsed);

        LeftPanel.add(InfoPanel);

        PuzzlePanel.setLayout(new GridLayout(4,4,3,3));
        PuzzlePanel.add(s1);
        PuzzlePanel.add(s2);
        PuzzlePanel.add(s3);
        PuzzlePanel.add(s4);
        PuzzlePanel.add(s5);
        PuzzlePanel.add(s6);
        PuzzlePanel.add(s7);
        PuzzlePanel.add(s8);
        PuzzlePanel.add(s9);
        PuzzlePanel.add(s10);
        PuzzlePanel.add(s11);
        PuzzlePanel.add(s12);
        PuzzlePanel.add(s13);
        PuzzlePanel.add(s14);
        PuzzlePanel.add(s15);
        PuzzlePanel.add(s16);

        RightPanel.add(kurang);

        MainPanel.add(LeftPanel);
        MainPanel.add(PuzzlePanel);
        MainPanel.add(RightPanel);

        frame.add(MainPanel);

        zeroButton.setEnabled(false);
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        lastButton.setEnabled(false);
        playButton.setEnabled(false);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1250, 500);
        frame.setVisible(true);

        StartBtn.addActionListener(this);
    }

    // fungsi utama ketika tombol start dijalankan
    @Override
    public void actionPerformed(ActionEvent e) {
        BranchnBound(FileName.getText());
    }

    // algoritma Branch n Bound untuk menyelesaikan puzzle
    public void BranchnBound(String filename) {
        output =  new java.util.ArrayList<Puzzle>();
        Integer[] integers = new Integer[17];
        Arrays.fill(integers, 0);
        outputKurang = Arrays.asList(integers);

        clearPuzzle();

        kurang.setText(null);
        TimeElapsed.setText(null);
        Step.setText(null);
        TotalBranch.setText(null);

        idxNow = 0;
        long counter = 1;
        Puzzle p = new Puzzle(filename, counter);

        long start = System.currentTimeMillis();

        if(!p.isEmpty) {
            if (p.checkPossible(outputKurang)) {
                PrioQueue PQ = new PrioQueue();
                java.util.List<Puzzle> state = new ArrayList<Puzzle>();

                PQ.enQueue(p);

                boolean notFound = true;

                while (notFound) {
                    Puzzle check = PQ.deQueue();
                    state.add(check);
                    if (check.checkGoal()) {
                        notFound = false;
                        getSolution(check);
                    } else {
                        if (check.checkUp()) {
                            counter++;
                            Puzzle child = new Puzzle(check, "up", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkRight()) {
                            counter++;
                            Puzzle child = new Puzzle(check, "right", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkDown()) {
                            counter++;
                            Puzzle child = new Puzzle(check, "down", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkLeft()) {
                            counter++;
                            Puzzle child = new Puzzle(check, "left", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                    }
                }

                zeroButton.setEnabled(true);
                prevButton.setEnabled(true);
                nextButton.setEnabled(true);
                lastButton.setEnabled(true);
                playButton.setEnabled(true);
                TotalBranch.setText("Total branch: " + counter);
                Step.setText("Step: " + idxNow + " / " + (output.size() - 1) + "");
                refreshPuzzle();

            } else {
                output.add(p);

                zeroButton.setEnabled(false);
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
                lastButton.setEnabled(false);
                playButton.setEnabled(false);

                Step.setText("Puzzle is unresolvable!");
                TotalBranch.setText(null);
                refreshPuzzle();
            }
            for (int i = 0; i < 16; i++) {
                kurang.append("Kurang(" + (i+1) + ") = " + outputKurang.get(i) + "\r\n");
            }
            kurang.append("\r\n");
            kurang.append("Total Kurang + X = " + outputKurang.get(16));

            TimeElapsed.setText("Time Elapsed: " + (System.currentTimeMillis() - start) + " ms");
        } else {
            zeroButton.setEnabled(false);
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            lastButton.setEnabled(false);
            playButton.setEnabled(false);

            Step.setText("file is not exist!");
            TotalBranch.setText(null);
        }
    }

    // fungsi untuk menambahkan solusi pada list output (digunakan pada fungsi BranchnBound)
    public void getSolution(Puzzle check) {
        if (check.parent == null) {
            output.add(check);
        } else {
            getSolution(check.parent);
            output.add(check);
        }
    }

    // fungsi untuk mereset tampilan puzzle
    public void clearPuzzle() {
        s1.setText("");
        s1.setBackground(Color.white);
        s2.setText("");
        s2.setBackground(Color.white);
        s3.setText("");
        s3.setBackground(Color.white);
        s4.setText("");
        s4.setBackground(Color.white);
        s5.setText("");
        s5.setBackground(Color.white);
        s6.setText("");
        s6.setBackground(Color.white);
        s7.setText("");
        s7.setBackground(Color.white);
        s8.setText("");
        s8.setBackground(Color.white);
        s9.setText("");
        s9.setBackground(Color.white);
        s10.setText("");
        s10.setBackground(Color.white);
        s11.setText("");
        s11.setBackground(Color.white);
        s12.setText("");
        s12.setBackground(Color.white);
        s13.setText("");
        s13.setBackground(Color.white);
        s14.setText("");
        s14.setBackground(Color.white);
        s15.setText("");
        s15.setBackground(Color.white);
        s16.setText("");
        s16.setBackground(Color.white);
    }

    // fungsi untuk refresh tampilan puzzle sesuai dengan step
    public void refreshPuzzle() {
        refreshSlot(s1, output.get(idxNow).matrix[0][0]);
        refreshSlot(s2, output.get(idxNow).matrix[0][1]);
        refreshSlot(s3, output.get(idxNow).matrix[0][2]);
        refreshSlot(s4, output.get(idxNow).matrix[0][3]);
        refreshSlot(s5, output.get(idxNow).matrix[1][0]);
        refreshSlot(s6, output.get(idxNow).matrix[1][1]);
        refreshSlot(s7, output.get(idxNow).matrix[1][2]);
        refreshSlot(s8, output.get(idxNow).matrix[1][3]);
        refreshSlot(s9, output.get(idxNow).matrix[2][0]);
        refreshSlot(s10, output.get(idxNow).matrix[2][1]);
        refreshSlot(s11, output.get(idxNow).matrix[2][2]);
        refreshSlot(s12, output.get(idxNow).matrix[2][3]);
        refreshSlot(s13, output.get(idxNow).matrix[3][0]);
        refreshSlot(s14, output.get(idxNow).matrix[3][1]);
        refreshSlot(s15, output.get(idxNow).matrix[3][2]);
        refreshSlot(s16, output.get(idxNow).matrix[3][3]);
    }

    // fungsi pembantu pada refresh puzzle
    public void refreshSlot(JButton btn,int input) {
        if (input != 16) {
            btn.setText(Integer.toString(input));
            btn.setBackground(Color.gray);
        } else {
            btn.setText("");
            btn.setBackground(Color.white);
        }
    }

    // fungsi main yang dijalankan ketika program di run
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
