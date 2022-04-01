import java.util.*;

public class PrioQueue {
    public List<Tree> liveNode = new LinkedList<Tree>(); 

    public void enQueue(Tree puzzle) {
        if (liveNode.isEmpty()) {
            liveNode.add(puzzle);
        } else {
            int i = 0;
            boolean found = false;
            for (Tree elmt : liveNode) {
                if (elmt.node.getCost() > puzzle.node.getCost()) {
                    found = true;
                    break;
                } else {
                    i++;
                }
            }
            if (found) {
                liveNode.add(i, puzzle);
            } else {
                liveNode.add(puzzle);
            }
        }
    }

    public Tree deQueue() {
        Tree out = liveNode.get(0);
        liveNode.remove(0);
        return out;
    }
}
