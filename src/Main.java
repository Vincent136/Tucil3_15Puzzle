import java.util.*;

public class Main {

    public static void BranchnBound(String filename) {
        long counter = 1;
        Puzzle p = new Puzzle(filename, counter);
        if (p.checkPossible()) {
            if (p.checkGoal()) {
                System.out.println("Udah Bener!");
            } else {
                PrioQueue PQ = new PrioQueue();
                List<Puzzle> state = new ArrayList<Puzzle>();

                PQ.enQueue(p);
    
                boolean notFound = true;
    
                while (notFound) {
                    Puzzle check = PQ.deQueue();
                    state.add(check);
                    if (check.checkGoal()) {
                        notFound = false;
                        printSolution(check);
                    } else {
                        if (check.checkUp()) {
                            counter ++;
                            Puzzle child = new Puzzle(check, "up", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkRight()) {
                            counter ++;
                            Puzzle child = new Puzzle(check, "right", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkDown()) {
                            counter ++;
                            Puzzle child = new Puzzle(check, "down", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                        if (check.checkLeft()) {
                            counter ++;
                            Puzzle child = new Puzzle(check, "left", counter);
                            if (child.checkState(state)) {
                                PQ.enQueue(child);
                            } else {
                                counter--;
                            }
                        }
                    }
                }
            }
            
        } else {
            System.out.println("Not Possible!");
        }
    }

    public static void printSolution(Puzzle check) {
        if (check.parent == null) {
            System.out.println(check.id);
            check.printPuzzle();
        } else {
            printSolution(check.parent);
            System.out.println(check.id);
            check.printPuzzle();
        }
    }

    public static void main(String[] args){
        BranchnBound("test2.txt");
    }
}
