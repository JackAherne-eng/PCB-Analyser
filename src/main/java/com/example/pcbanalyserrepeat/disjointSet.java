package com.example.pcbanalyserrepeat;

public class disjointSet {

    public void union(int[] array, int x, int y) {
        array[find(array, x)] = find(array, y);
    }

    private int find(int[] array, int y) {
        if (array[y] == 0){
            return y;
        }
        while (array[y] != 0){
            array[y] = array[array[y]];
            y = array[y];
    }
        return y;
    }
}
