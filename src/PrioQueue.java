import java.util.*;

public class PrioQueue {
    public List<Puzzle> liveNode = new LinkedList<Puzzle>(); 

    public void enQueue(Puzzle puzzle) {
        if (liveNode.isEmpty()) {
            liveNode.add(puzzle);
        } else {
            int i = 0;
            boolean found = false;
            for (Puzzle elmt : liveNode) {
                if (elmt.getCost() > puzzle.getCost()) {
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

    public Puzzle deQueue() {
        Puzzle out = liveNode.get(0);
        liveNode.remove(0);
        return out;
    }
}
