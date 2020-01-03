package com.company;

public class Main {

    public static void main(String[] args) {
        //State s = new State(1,2,3,7,8,4,5,6,9,10,11,15,0,12,13,14);
        //State s = new State(6,13,7,10,8,9,11,0,15,2,12,5,14,3,1,4);
        //System.out.println(s.isSolvable());
        IDA ida = new IDA();
        RBFS rbfs = new RBFS(0);
        RBFS rbfs_e1 = new RBFS(4);
        long idaTime = 0, rbfsTime = 0, rbfse1Time = 0;
        long idaExp = 0, rbfsExp = 0,rbfse1Exp = 0;
        long idaDup =0,rbfsDup=0, rbfse1Dup=0;
        int limit = 500;
        for (int i = 0; i < limit; i++) {
            Node root = createRoot(new State());
            long start = System.currentTimeMillis();
            int idaLen = ida.run(root).size();
            idaTime += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            int rbfsLen = rbfs.run(root).size();
            rbfsTime += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            int rbfse1Len = rbfs_e1.run(root).size();
            rbfse1Time += System.currentTimeMillis() - start;

            idaExp += ida.numberOfExp;
            rbfsExp += rbfs.numberOfExp;
            rbfse1Exp += rbfs_e1.numberOfExp;
            idaDup+= ida.nodesDup;
            rbfsDup+= rbfs.nodesDup;
            rbfse1Dup+= rbfs_e1.nodesDup;
            //System.out.println(i + ">ida len: " + idaLen + " rbfs: " + rbfsLen + " rbfse: " + rbfse1Len);
        }
        System.out.println("Ida total time: " + idaTime + " rbfs total time: " + rbfsTime + " rbfse1 total :" + rbfse1Time);
        System.out.println("Ida average time: " + (idaTime/limit) + " rbfs average time: " + (rbfsTime/limit) + " rbfse1 average :" + (rbfse1Time/limit));
        System.out.println("Ida total expansions: " + idaExp + " rbfs total expansions: " + rbfsExp + " rbfse1 total :" + rbfse1Exp);
        System.out.println("Ida average expansions: " + (idaExp/limit) + " rbfs average expansions: " + (rbfsExp/limit) + " rbfse1 average :" + (rbfse1Exp/limit));
        System.out.println("Ida total dups: " + idaDup + " rbfs total dups: " + rbfsDup + " rbfse1 total :" + rbfse1Dup);
        System.out.println("Ida average dups: " + (idaDup/limit) + " rbfs average dups: " + (rbfsDup/limit) + " rbfse1 average :" + (rbfse1Dup/limit));
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


}
