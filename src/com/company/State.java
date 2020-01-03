package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class State {
    public enum move {UP, DOWN, LEFT, RIGHT}

    public static int size = 3;

    public int[] board;

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

    }

    public State(int... input) {
        this.board = input.clone();
    }

    public State applyMove(move m) {
        switch (m) {
            case UP:
                int blankIndex = getBlankIndex();
                if (getRow(blankIndex) == 0)
                    return null;
                int[] newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex - size];
                newBoard[blankIndex - size] = 0;
                return new State(newBoard);

            case DOWN:
                blankIndex = getBlankIndex();
                if (getRow(blankIndex) == size - 1)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex + size];
                newBoard[blankIndex + size] = 0;
                return new State(newBoard);
            case LEFT:
                blankIndex = getBlankIndex();
                if (getCol(blankIndex) == 0)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex - 1];
                newBoard[blankIndex - 1] = 0;
                return new State(newBoard);
            case RIGHT:
                blankIndex = getBlankIndex();
                if (getCol(blankIndex) == size - 1)
                    return null;
                newBoard = board.clone();
                newBoard[blankIndex] = newBoard[blankIndex + 1];
                newBoard[blankIndex + 1] = 0;
                return new State(newBoard);
        }
        return null;
    }

    private int getCol(int i) {
        return i % size;
    }

    public boolean isSolvable() {
        int inversions = getInversions();
        int i = getBlankIndex();
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
        for (int i = 0; i < State.size* State.size-1; i++) {
            if (board[i] != i+1)
                return false;
        }
        return true;
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
        return res;
    }
}
