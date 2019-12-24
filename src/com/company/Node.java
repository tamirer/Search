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

    @Override
    public String toString(){
        return "blank: " + state.getBlankIndex();
    }
}
