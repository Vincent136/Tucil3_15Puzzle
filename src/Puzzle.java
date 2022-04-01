import java.io.*;
import java.util.Scanner;

public class Puzzle {
    public int[][] matrix = new int[4][4];
    private int row = 4;
    private int col = 4;
    public int row16;
    public int col16;
    private int cost;
    private int level;
    public String prevCommand;
    
    public Puzzle(String filename){
        try {
            File file = new File(filename);

            Scanner input = new Scanner(file);

            for (int i = 0; i < row; i++) {
                String line = input.nextLine();
                String[] arrLine = line.split(" ");
                for (int j = 0; j < arrLine.length; j++) {
                    int number =Integer.parseInt(arrLine[j]);
                    if (number == 16) {
                        this.row16 = i;
                        this.col16 = j;
                    }
                    this.matrix[i][j] = number;
                }
            }

            this.level = 0;
            int GX = getGX();
            this.cost = level + GX;

            this.prevCommand = "-";

            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Puzzle(Puzzle p, String command){
        if (command.equals("up")) {
            for(int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    this.matrix[i][j] = p.matrix[i][j];
                }
            }
            this.row16 = p.row16;
            this.col16 = p.col16;

            int temp = this.matrix[row16][col16];
            this.matrix[row16][col16] = this.matrix[row16-1][col16];
            this.matrix[row16-1][col16] = temp;

            this.row16--;
            this.prevCommand = "up";

        } else if (command.equals("right")) {
            for(int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    this.matrix[i][j] = p.matrix[i][j];
                }
            }
            this.row16 = p.row16;
            this.col16 = p.col16;

            int temp = this.matrix[row16][col16];
            this.matrix[row16][col16] = this.matrix[row16][col16+1];
            this.matrix[row16][col16+1] = temp;

            this.col16++;
            this.prevCommand = "right";

        } else if (command.equals("down")) {
            for(int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    this.matrix[i][j] = p.matrix[i][j];
                }
            }
            this.row16 = p.row16;
            this.col16 = p.col16;

            int temp = this.matrix[row16][col16];
            this.matrix[row16][col16] = this.matrix[row16+1][col16];
            this.matrix[row16+1][col16] = temp;

            this.row16++;
            this.prevCommand = "down";

        } else if (command.equals("left")) {
            for(int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    this.matrix[i][j] = p.matrix[i][j];
                }
            }
            this.row16 = p.row16;
            this.col16 = p.col16;

            int temp = this.matrix[row16][col16];
            this.matrix[row16][col16] = this.matrix[row16][col16-1];
            this.matrix[row16][col16-1] = temp;

            this.col16--;
            this.prevCommand = "left";
        }
        this.level = p.level+1;
        int GX = getGX();
        this.cost = this.level + GX;
    }

    public boolean checkPossible() {
        boolean Possible = true;
        int sigma = 0;
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int numberNow = matrix[i][j];
                int k = i;
                int l = j + 1;
                while (k < row) {
                    if (l == col) {
                        l = 0;
                        k++;
                    } 
                    if (k < row) {
                        if (matrix[k][l] < numberNow) {
                            sigma += 1;
                        }
                        l++;
                    }
                }
            }
        }
        int X = (row16 + col16)%2;      
        Possible = ((sigma + X)%2) == 0;
        return Possible;
    }

    public boolean checkUp() {
        return row16 != 0 && !prevCommand.equals("down");
    }

    public boolean checkRight() {
        return col16 != 3 && !prevCommand.equals("left");
    }

    public boolean checkDown() {
        return row16 != 3 && !prevCommand.equals("up");
    }

    public boolean checkLeft() {
        return col16 != 0 && !prevCommand.equals("right");
    }

    public int getGX() {
        int GX = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != 16) {
                    if (matrix[i][j] != i*4 + j + 1) {
                        GX++;
                    } 
                }
            }   
        }
        return GX;
    }

    public boolean checkGoal() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != i*4 + j + 1) {
                    return false;
                }
            }   
        }
        return true;
    }

    public int getCost() {
        return this.cost;
    }

    public void printPuzzle() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != 16) {
                    System.out.print(matrix[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }   
            System.out.println();
        }
    }
}