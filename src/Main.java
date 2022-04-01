import java.util.*;

public class Main {

    public static void BranchnBound(Tree searchTree, long counter) {
        if (searchTree.node.checkGoal()) {
            System.out.println("Udah Bener!");
        } else {
            PrioQueue PQ = new PrioQueue();
            if (searchTree.node.checkUp()) {
                counter ++;
                searchTree.addUp(counter);
            }
            if (searchTree.node.checkRight()) {
                counter ++;
                searchTree.addRight(counter);
            }
            if (searchTree.node.checkDown()) {
                counter ++;
                searchTree.addDown(counter);
            }
            if (searchTree.node.checkLeft()) {
                counter ++;
                searchTree.addLeft(counter);
            }

            for (Tree child : searchTree.children) {
                PQ.enQueue(child);
            }

            while (!PQ.liveNode.isEmpty()) {
                Tree check = PQ.deQueue();
            }
        }
    }
    public static void main(String[] args){
        Puzzle p = new Puzzle("test2.txt");
        if (p.checkPossible()) {
            long counter = 1;
            System.out.println(counter);
            p.printPuzzle();
            Tree searchTree = new Tree(p, counter);
            BranchnBound(searchTree, counter);
        } else {
            System.out.println("Not Possible!");
        }
    }
}
