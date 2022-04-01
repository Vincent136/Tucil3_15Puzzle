import java.util.*;

public class Tree {
    public long id;
    public Puzzle node;
    public List<Tree> children = new LinkedList<Tree>();

    public Tree(Puzzle node, long counter) {
        this.id = counter;
        this.node = node;
    }

    public void addUp(long counter) {
        Puzzle puzzle = new Puzzle(node, "up");
        children.add(new Tree(puzzle, counter));
    }

    public void addRight (long counter) {
        Puzzle puzzle = new Puzzle(node, "right");
        children.add(new Tree(puzzle, counter));
    }

    public void addDown (long counter) {
        Puzzle puzzle = new Puzzle(node, "down");
        children.add(new Tree(puzzle, counter));
    }

    public void addLeft (long counter) {
        Puzzle puzzle = new Puzzle(node, "left");
        children.add(new Tree(puzzle, counter));
    }

    public int getMinCostIDX() {
        int min = children.get(0).node.getCost();
        System.out.println(children.get(0).node.getCost());
        System.out.println(children.get(0).node.prevCommand);
        int minIDX = 0;
        for (int i = 1; i < children.size(); i++) {
            System.out.println(children.get(i).node.getCost());
            System.out.println(children.get(i).node.prevCommand);
            if (children.get(i).node.getCost() < min) {
                min = children.get(i).node.getCost();
                minIDX = i;
            }
        }

        return minIDX;
    }
}
