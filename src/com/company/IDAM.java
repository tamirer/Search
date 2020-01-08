package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class IDAM {
    public long numberOfExp;
    List<Node> nodesOpened;
    public int nodesDup;
    LinkedList<Node> openList;

    public Stack<Node> run(Node root) {
        numberOfExp = 0;
        nodesDup = 0;
        nodesOpened = new ArrayList<>();
        int bound = root.h;
        Stack<Node> path = new Stack<>();
        path.push(root);
        //openList.add(root);
        while (true) {
            /*path.clear();
            adjustPath(path, getBestOpen());*/
            int t = search(path, bound);
            if (t == -1)
                return path;
            else if (t == Integer.MAX_VALUE)
                return null;
            bound = t;
        }
    }

    private Node getBestOpen() {
        Node best = openList.peekFirst();
        for (Node n : openList){
            if(n.f < best.f)
                best = n;
        }
        return best;

    }

    private void adjustPath(Stack<Node> path, Node best) {
        if (best.parent != null) {
            adjustPath(path, best.parent);
        }
        path.push(best);
    }

    private int search(Stack<Node> path, int bound) {
        Node n = path.peek();
        if (nodesOpened.contains(n)) {
            nodesDup++;
        } else
            nodesOpened.add(n);
        int f = n.f;
        if (f > bound) return f;
        if (n.state.isGoal()) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        List<Node> succs = n.children.isEmpty() ? n.expand() : n.children;
        //openList.remove(n);
        //openList.addAll(succs);
        numberOfExp++;
        for (Node succ : succs) {
            if (!path.contains(succ)) {
                path.push(succ);
                int t = search(path, bound);
                if (t == -1) return -1;
                if (t < min) min = t;
                path.pop();
            }
        }
        return min;
    }

}
