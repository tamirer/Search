package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IDA {

    int numberOfNodes;


    public Stack<Node> idaStar(Node root) {
        int bound = root.h;
        Stack<Node> path = new Stack<>();
        path.push(root);
        int i = 0;
        while (true) {
            System.out.println("bound is " + bound + ", i = " + i);
            int t = search(path, bound);
            if (t == -1)
                return path;
            else if (t == Integer.MAX_VALUE)
                return null;
            bound = t;
            i++;
        }
    }

    private int search(Stack<Node> path, int bound) {
        Node n = path.peek();
        int f = n.f;
        if (f > bound) return f;
        if (isGoal(n))
            return -1;
        int min = Integer.MAX_VALUE;
        for (Node succ : expand(n)) {
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

    private List<Node> expand(Node n) {
        List<Node> res = new ArrayList<>();
        State s = n.state;
        State up = s.applyMove(State.move.UP);
        if (up != null)
            res.add(new Node(n, up, up.calcH(), n.g + 1));

        State down = s.applyMove(State.move.DOWN);
        if (down != null)
            res.add(new Node(n, down, down.calcH(), n.g + 1));

        State left = s.applyMove(State.move.LEFT);
        if (left != null)
            res.add(new Node(n, left, left.calcH(), n.g + 1));

        State right = s.applyMove(State.move.RIGHT);
        if (right != null)
            res.add(new Node(n, right, right.calcH(), n.g + 1));
        return res;
    }

    private boolean isGoal(Node n) {
        for (int i = 0; i < State.size* State.size-1; i++) {
            if (n.state.board[i] != i+1)
                return false;
        }
        return true;
    }
}
