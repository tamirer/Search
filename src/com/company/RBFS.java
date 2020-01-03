package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RBFS {

    public long numberOfExp;
    List<Node> nodesOpened;
    public int nodesDup;
    int epsilon = 0;

    public RBFS(int eps){
        epsilon = eps;
    }

    public Stack<Node> run(Node n) {
        numberOfExp = 0;
        nodesDup = 0;
        nodesOpened = new ArrayList<>();
        Stack<Node> path = new Stack<>();
        RBFSHelper(n, Integer.MAX_VALUE-epsilon, path);
        Node current = path.peek();
        while (current.parent != null) {
            current = current.parent;
            path.push(current);
        }
        return path;
    }

    private int RBFSHelper(Node n, int B, Stack<Node> stack) {
        if (nodesOpened.contains(n)) {
            nodesDup++;
        } else
            nodesOpened.add(n);
        if (n.state.isGoal()) {
            stack.push(n);
            return -1;
        }
        List<Node> C = n.expand();
        numberOfExp++;
        if (C.isEmpty())
            return Integer.MAX_VALUE;
        for (Node child : C) {
            if (n.f < n.F)
                child.F = Math.max(n.F, child.f);
            else
                child.F = child.f;
        }
        Node[] temp = bestF(C);
        Node n1 = temp[0];
        Node n2 = temp[1];
        while (n1.F <= B+epsilon && n1.F < Integer.MAX_VALUE) {
            n1.F = RBFSHelper(n1, Math.min(B, n2.F), stack);
            if (n1.F == -1)
                return -1;
            temp = bestF(C);
            n1 = temp[0];
            n2 = temp[1];
        }
        return n1.F;
    }

    private Node[] bestF(List<Node> c) {
        Node best1 = null, best2 = null;
        for (Node n : c) {
            if (best1 == null || n.F < best1.F) {
                best2 = best1;
                best1 = n;
            } else if (best2 == null || n.F < best2.F) {
                best2 = n;
            }
        }
        return new Node[]{best1, best2};
    }
}
