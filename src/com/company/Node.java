package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    public Node parent;
    public State state;
    public int h;
    public int g;
    public int f;
    public int F;
    public List<Node> children;

    public Node(Node parent, State state,int h, int g){
        this.parent = parent;
        this.state = state;
        this.h = h;
        this.g = g;
        this.f = g+h;
        this.F = f;
        this.children = new ArrayList<>();
    }

    public List<Node> expand() {
        List<Node> res = new ArrayList<>();
        State s = state;
        State up = s.applyMove(State.move.UP);
        if (up != null)
            res.add(new Node(this, up, up.calcH(), g + 1));

        State down = s.applyMove(State.move.DOWN);
        if (down != null)
            res.add(new Node(this, down, down.calcH(), g + 1));

        State left = s.applyMove(State.move.LEFT);
        if (left != null)
            res.add(new Node(this, left, left.calcH(), g + 1));

        State right = s.applyMove(State.move.RIGHT);
        if (right != null)
            res.add(new Node(this, right, right.calcH(), g + 1));
        return res;
    }

    @Override
    public String toString(){
        return "blank: " + state.getBlankIndex();
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Node))
            return false;
        Node otherNode = (Node)other;

        /*for (int i = 0; i < state.board.length; i++) {
            if(state.board[i] != otherNode.state.board[i])
                return false;
        }*/
        return Arrays.equals(state.board,otherNode.state.board);
    }
}
