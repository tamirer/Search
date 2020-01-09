package com.company;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    int id;
    int x;
    int y;
    List<GraphNode> neighbours;

    public GraphNode(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(GraphNode other){
        neighbours.add(other);
    }

}
