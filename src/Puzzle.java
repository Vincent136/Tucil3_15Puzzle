import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Puzzle {

    // atribut Puzzle
    public long id;
    public int[][] matrix = new int[4][4];
    private int row = 4;
    private int col = 4;
    public int row16;
    public int col16;
    private int cost;
    private int level;
    public String prevCommand;
    public Puzzle parent;
    public boolean isEmpty = false;

    // ctor Puzzle dengan input string filename dan counter (counter merupakan variable global untuk menghitung jumlah living node)
    public Puzzle(String filename, long counter){

        String content = null;
        try {
            content = Files.lines(Paths.get("../test/"+ filename))
                    .collect(Collectors.joining(System.lineSeparator()));
            String[] splitLine = content.split("\r\n");

            for (int i = 0; i < 4; i++) {
                String[] spaceSplit = splitLine[i].split(" ");
                for (int j = 0; j < 4; j++) {
                    int number =Integer.parseInt(spaceSplit[j]);
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
            this.id = counter;

        } catch (IOException e) {
            this.isEmpty = true;
        }
    }

    // ctor Puzzle dengan input puzzle parent dan command ("up","down","right","left") dan counter (counter merupakan variable global untuk menghitung jumlah living node)
    public Puzzle(Puzzle p, String command, long counter){
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
        this.parent = p;
        this.id = counter;
        this.level = p.level+1;
        int GX = getGX();
        this.cost = GX;
    }

    // fungsi untuk mengetahui apakah puzzle bisa diselesaikan parameter outputkurang merupakan list dari kurang(i)
    public boolean checkPossible(List<Integer> outputKurang) {
        boolean Possible = true;
        int sigma = 0;
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int numberNow = matrix[i][j];
                int k = i;
                int l = j + 1;
                int kurang = 0;
                while (k < row) {
                    if (l == col) {
                        l = 0;
                        k++;
                    }
                    if (k < row) {
                        if (matrix[k][l] < numberNow) {
                            kurang += 1;
                        }
                        l++;
                    }
                }
                outputKurang.set(numberNow-1, kurang);
                sigma += kurang;
            }
        }
        int X = (row16 + col16)%2;
        Possible = ((sigma + X)%2) == 0;
        outputKurang.set(16, sigma + X);
        return Possible;
    }

    // fungsi mengecek apakah kotak kosong bisa digerakan ke atas
    public boolean checkUp() {
        return row16 != 0 && !prevCommand.equals("down");
    }

    // fungsi mengecek apakah kotak kosong bisa digerakan ke kanan
    public boolean checkRight() {
        return col16 != 3 && !prevCommand.equals("left");
    }

    // fungsi mengecek apakah kotak kosong bisa digerakan ke bawah
    public boolean checkDown() {
        return row16 != 3 && !prevCommand.equals("up");
    }

    // fungsi mengecek apakah kotak kosong bisa digerakan ke kiri
    public boolean checkLeft() {
        return col16 != 0 && !prevCommand.equals("right");
    }

    // Fungsi untuk mengecek apakah bentuk matrix sudah pernah di check
    public boolean checkState(List<Puzzle> state) {
        for (Puzzle p : state) {
            boolean isSame = true;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (p.matrix[i][j] != this.matrix[i][j]) {
                        isSame = false;
                    }
                }
            }
            if (isSame) {
                return false;
            }
        }
        return true;
    }

    // fungsi untuk mendapatkan g(x) saat menentukan cost
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

    // fungsi untuk mengecek apakah matrix sudah merupakan goal
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

    // getter untuk mendapatkan cost dari puzzle
    public int getCost() {
        return this.cost;
    }

    // fungsi untuk print puzzle pada cli (tidak digunakan dalam GUI)
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
