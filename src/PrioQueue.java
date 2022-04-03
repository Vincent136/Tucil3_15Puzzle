import java.util.*;

// kelas prioqueue digunakan untuk menyimpan living node pada pencarian branch & bound
public class PrioQueue {
    // attribute
    public List<Puzzle> liveNode = new LinkedList<Puzzle>();

    // fungsi untuk memasukan puzzle ke dalam Prioqueue sesuai dengan prioritas (cost terkecil akan masuk pada index terendah)
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

    // fungsi untuk mengambil puzzle pada index terendah dari prioqueue
    public Puzzle deQueue() {
        Puzzle out = liveNode.get(0);
        liveNode.remove(0);
        return out;
    }
}

