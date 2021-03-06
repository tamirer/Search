package com.company;

import java.util.*;

public class State {

    //TILE PUZZLE

    /*public enum move {UP, DOWN, LEFT, RIGHT}

    public static int size = 4;
    public static int[] goal4 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
    public static int[] goal3 = {1,2,3,4,5,6,7,8,0};
    public static boolean linearConflict=false;

    public int[] board;
    public int blankIndex;
    public int[] goal;

    public State() {
        while(true) {
            List<Integer> rand = new ArrayList<>();
            for (int i = 0; i < size * size; i++) {
                rand.add(i);
            }
            Collections.shuffle(rand);
            board = new int[size * size];
            for (int i = 0; i < board.length; i++) {
                board[i] = rand.get(i);
            }
            if (isSolvable())
                break;
        }
        blankIndex=getBlankIndex();
        goal=size==4? goal4:goal3;
    }

    public State(int moves){
        board = goal4.clone();
        blankIndex=getBlankIndex();
        Random r = new Random();
        int l=0;
        for (int i = 0; i < moves; i++) {
            int movei = r.nextInt(4);
            move m = move.values()[movei];
            State s = applyMove(m);
            if(s!=null){
                board = s.board;
                blankIndex=s.blankIndex;
                l++;
            }
        }
        System.out.println(l);
        goal=size==4? goal4:goal3;
    }

    public State(int blank,int... input) {
        this.board = input.clone();
        blankIndex=blank;
        goal=size==4? goal4:goal3;
    }

    public State applyMove(move m) {
        switch (m) {
            case UP:
                if (getRow(blankIndex) == 0)
                    return null;
                int[] newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex - size];
                newBoard[blankIndex - size] = 0;
                return new State(blankIndex - size,newBoard);

            case DOWN:
                if (getRow(blankIndex) == size - 1)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex + size];
                newBoard[blankIndex + size] = 0;
                return new State(blankIndex + size,newBoard);
            case LEFT:
                if (getCol(blankIndex) == 0)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex - 1];
                newBoard[blankIndex - 1] = 0;
                return new State(blankIndex - 1,newBoard);
            case RIGHT:
                if (getCol(blankIndex) == size - 1)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex + 1];
                newBoard[blankIndex + 1] = 0;
                return new State(blankIndex + 1,newBoard);
        }
        return null;
    }

    private int getCol(int i) {
        return i % size;
    }

    public boolean isSolvable() {
        int inversions = getInversions();
        int i = blankIndex;
        if (size % 2 == 0) {
            if ((size - getRow(i)) % 2 == 0) {
                return inversions % 2 == 1;
            } else
                return inversions % 2 == 0;
        } else return inversions % 2 == 0;
    }

    public int getBlankIndex() {
        int i;
        for (i = 0; i < size*size; i++) {
            if (board[i] == 0) {
                break;
            }
        }
        return i;
    }

    private int getRow(int i) {
        return (i / size);
    }

    public boolean isGoal() {
        /*for (int i = 0; i < State.size* State.size-1; i++) {
            if (board[i] != i+1)
                return false;
        }
        return true;
        return Arrays.equals(board,goal);
    }

    private int getInversions() {
        int res = 0;
        for (int i = 0; i < size*size; i++) {
            for (int j = i + 1; j < size*size; j++) {
                if (board[i] > board[j] && board[i] != 0 && board[j] != 0)
                    res++;
            }
        }
        return res;
    }

    public int calcH() {
        int res = 0;
        for (int i = 0; i < size*size; i++) {
            int num = board[i];
            if (num == 0)
                continue;
            int row = getRow(i);
            int col = getCol(i);
            int expectedRow = getRow(num-1);
            int expectedCol = getCol(num-1);
            res += Math.abs(row - expectedRow) + Math.abs(col - expectedCol);
        }
        if(linearConflict)
            return res+calcH2();
        else
            return res;
    }

    public int calcH2() {
        int lc=0;
        for (int i = 0; i < size; i++) {
            // rows
            for(int ri=i*size;ri<i*size+size;ri++){
                if(board[ri]!=0&&(board[ri]-1)/size==ri/size)
                    for(int j=ri+1;j<i*size+size; j++){
                        if(board[j]!=0&&(board[j]-1)/size==j/size && board[j]<board[ri])
                            lc++;
                    }
            }

            // cols
            for(int ci=i;ci<size*size;ci+=size){
                if(board[ci]!=0&&(board[ci]-1)%size==i%size)
                    for(int j=ci+size;j<size*size; j+=size){
                        if(board[j]!=0&&(board[j]-1)%size==j%size && board[j]<board[ci])
                            lc++;
                    }
            }
        }
        return lc;
    }*/

    List<GraphEdge> edges;
    List<GraphNode> nodes;
    GraphNode loc;
    GraphNode dest;
    public int totalCost;

    //generate random graph
    public State() {
        totalCost = 0;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        int numOfNodes = Math.max((int) (Math.random() * (60)),10);
        int numOfEdges = (int)(numOfNodes*1.5);
        generateGraph(numOfNodes, numOfEdges);
        int i = (int) (Math.random() * (numOfNodes-1));
        int j = (int) (Math.random() * (numOfNodes-1));
        loc = nodes.get(i);
        dest = nodes.get(j);
    }

    public State(State state, GraphNode newLoc, int cost) {
        this.edges = state.edges;
        this.nodes = state.nodes;
        this.dest = state.dest;
        this.loc = newLoc;
        this.totalCost = cost;
    }

    private void generateGraph(int numOfNodes, int numOfEdges) {
        int nodeId = 0, edgeId = 0;
        LinkedList<GraphNode> initNodes = new LinkedList<>();
        for (int i = 0; i < numOfNodes; i++, nodeId++) {
            int x = (int) (Math.random() * 1000);
            int y = (int) (Math.random() * 1000);
            GraphNode node = new GraphNode(nodeId, x, y);
            nodes.add(node);
            initNodes.add(node);
        }
        GraphNode src = initNodes.pop();
        while (!initNodes.isEmpty()) {
            GraphNode dst = initNodes.pop();
            int weight = (int) (Math.random() * 1000);
            GraphEdge edge = new GraphEdge(edgeId++,src.id,dst.id ,weight);
            edges.add(edge);
            src.addNeighbour(dst);
            dst.addNeighbour(src);
            src = dst;
            numOfEdges--;
        }
        while (numOfEdges > 0){
            int i = (int) (Math.random() * (numOfNodes-1));
            int j = (int) (Math.random() * (numOfNodes-1));
            if(i == j)
                continue;
            int weight = (int) (Math.random() * 1000);
            GraphEdge edge = new GraphEdge(edgeId++,i,j ,weight);
            edges.add(edge);
            nodes.get(i).addNeighbour(nodes.get(j));
            nodes.get(j).addNeighbour(nodes.get(i));
            numOfEdges--;
        }
    }

    //pitagoras between loc and dest
    public int calcH() {
        return (int) Math.sqrt(Math.pow(loc.x - dest.x, 2) + Math.pow(loc.y - dest.y, 2));
    }

    public State applyMove(GraphNode newLoc) {
        if (!loc.neighbours.contains(newLoc))
            return null;
        return new State(this,newLoc,totalCost + getEdge(loc, newLoc).weight);
    }

    private GraphEdge getEdge(GraphNode loc, GraphNode newLoc) {
        for (GraphEdge e : edges) {
            if ((e.n1 == loc.id && e.n2 == newLoc.id) ||
                    (e.n1 == newLoc.id && e.n2 == loc.id))
                return e;
        }
        return null;
    }

    public boolean isGoal() {
        return loc.id == dest.id;
    }

}
