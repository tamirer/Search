package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IDA {
    public long numberOfExp;
    List<Node> nodesOpened;
    public int nodesDup;

    public Stack<Node> run(Node root) {
        numberOfExp = 0;
        nodesDup = 0;
        nodesOpened = new ArrayList<>();
        int bound = root.h;
        Stack<Node> path = new Stack<>();
        path.push(root);
        while (true) {
            int t = search(path, bound);
            if (t == -1)
                return path;
            else if (t == Integer.MAX_VALUE)
                return null;
            bound = t;
        }
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
        List<Node> succs = n.expand();
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
