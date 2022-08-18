package com.example.pcbanalyserrepeat;

public class disjointSet {

    public void union(int[] array, int x, int y) {
        array[find(array, y)] = find(array, x);
    }

    public int find(int[] array, int y) {
        if (array[y] == 0){
            y = 0;
            return y;
        }
        while (array[y] != y){
            array[y] = array[array[y]];
            y = array[y];
    }
        return y;
    }
}
