package com.company;

import java.util.Arrays;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
		//State s = new State(1,2,3,7,8,4,5,6,9,10,11,15,0,12,13,14);
		//State s = new State(6,13,7,10,8,9,11,0,15,2,12,5,14,3,1,4);
		//System.out.println(s.isSolvable());
	    IDA ida = new IDA();
	    Node root = createRoot(new State());
	    Stack<Node> path = ida.idaStar(root);
	    System.out.println(Arrays.toString(path.toArray()));
    }

    public static Node createRoot(State s) {
        return new Node(null, s, s.calcH(), 0);
    }


}
