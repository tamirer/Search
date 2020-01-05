package com.company;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        //State s = new State(1,2,3,7,8,4,5,6,9,10,11,15,0,12,13,14);
        //State s = new State(14,2,1,7,6,0,5,4,11,9,12,3,8,13,10,15);
        //System.out.println(s.isSolvable());
        IDA ida = new IDA();
        IDAM idam = new IDAM();
        RBFS rbfs = new RBFS(0);
        RBFS rbfs_e1 = new RBFS(4);
        long idaTime = 0,idamTime = 0, rbfsTime = 0, rbfse1Time = 0;
        long idaExp = 0, rbfsExp = 0, rbfse1Exp = 0;
        long idaDup = 0, rbfsDup = 0, rbfse1Dup = 0;
        long idaLen = 0, rbfsLen = 0, rbfse1Len = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter("test.csv", true))) {
            writeToCSV(writer, "Limit",
                    "Total time IDA","Total time IDAM", "Total time RBFS", "Total time RBFSe",
                    "Average Time IDA","Average Time IDAM", "Average Time RBFS", "Average Time RBFSe",
                    "Total expansions IDA", "Total expansions RBFS", "Total expansions RBFSe",
                    "Average expansions IDA", "Average expansions RBFS", "Average expansions RBFSe",
                    "Total dups IDA", "Total dups RBFS", "Total dups RBFSe",
                    "Average dups IDA", "Average dups RBFS", "Average dups RBFSe",
                    "Average Solution length IDA", "Average Solution length RBFS", "Average Solution length IDA");
            for (int limit = 100; limit <= 10000; limit += 100) {

                for (int i = limit - 100; i < limit; i++) {
                    State s = new State();
                    print(i,s);
                    Node root = createRoot(s);

                    long start = System.currentTimeMillis();
                    Stack<Node> d = ida.run(root);
                    idaTime += System.currentTimeMillis() - start;
                    idaLen += d.size();

                    root = createRoot(s);
                    start = System.currentTimeMillis();
                    d = idam.run(root);
                    idamTime += System.currentTimeMillis() - start;

                    root = createRoot(s);
                    start = System.currentTimeMillis();
                    d = rbfs.run(root);
                    rbfsTime += System.currentTimeMillis() - start;
                    rbfsLen += d.size();

                    root = createRoot(s);
                    start = System.currentTimeMillis();
                    d = rbfs_e1.run(root);
                    rbfse1Time += System.currentTimeMillis() - start;
                    rbfse1Len += d.size();

                    idaExp += ida.numberOfExp;
                    rbfsExp += rbfs.numberOfExp;
                    rbfse1Exp += rbfs_e1.numberOfExp;
                    idaDup += ida.nodesDup;
                    rbfsDup += rbfs.nodesDup;
                    rbfse1Dup += rbfs_e1.nodesDup;
                    //System.out.println(i + ">ida len: " + idaLen + " rbfs: " + rbfsLen + " rbfse: " + rbfse1Len);
                }

                writeToCSV(writer, limit,
                        idaTime,idamTime, rbfsTime, rbfse1Time,
                        (idaTime / limit),(idamTime / limit),  (rbfsTime / limit), (rbfse1Time / limit),
                        idaExp, rbfsExp, rbfse1Exp,
                        (idaExp / limit), (rbfsExp / limit), (rbfse1Exp / limit),
                        idaDup, rbfsDup, rbfse1Dup,
                        (idaDup / limit), (rbfsDup / limit), (rbfse1Dup / limit),
                        (idaLen / limit), (rbfsLen / limit), (rbfse1Len / limit)
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static Object[] reverse(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    public static Node createRoot(State s) {
        return new Node(null, s, s.calcH(), 0);
    }

    public static void print(int j, State s) {
        String st = "";
        for (int i:s.board) {
            st+=i+" ";
        }
        System.out.println(j + ">" + st);
    }

    public static void writeToCSV(PrintWriter writer, Object... data){
        StringBuilder sb = new StringBuilder();
        for (Object d : data) {
            sb.append(d);
            sb.append(",");
        }
        sb.append("\n");
        writer.write(sb.toString());
    }
}
