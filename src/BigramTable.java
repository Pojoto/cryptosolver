package src;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BigramTable {
    
    private double[][] matrix;

    private HashMap<Character, Character> key;

    public BigramTable(){

        matrix = new double[Solver.alphabet.length][Solver.alphabet.length];

        key = new HashMap<Character, Character>();

    }

    public BigramTable(double[][] matrix){

        this.matrix = matrix;

        key = new HashMap<Character, Character>();

        for(int i = 0; i < Solver.alphabet.length; i++){

            key.put(Solver.alphabet[i], Solver.alphabet[i]);


        }

    }

    public HashMap<Character, Character> getKey(){

        return key;

    }

    public double[][] getMatrix(){

        return matrix;

    }

    public void swap(int i, int j){

        swapRows(i, j);
        swapCols(i, j);

        char temp = key.get(Solver.alphabet[i]);
        key.put(Solver.alphabet[i], key.get(Solver.alphabet[j]));
        key.put(Solver.alphabet[j], temp);

    }

    private void swapRows(int i, int j) {

        double[] temp = matrix[i];

        matrix[i] = matrix[j];

        matrix[j] = temp;

    }

    private void swapCols(int i, int j){

        for(int z = 0; z < matrix.length; z++){

            double temp = matrix[z][i];

            matrix[z][i] = matrix[z][j];
    
            matrix[z][j] = temp;

        }

    }

    public String toString(){

        StringBuilder builder = new StringBuilder();

        Character[] keyArray = key.keySet().toArray(new Character[0]);

        builder.append(" ");

        for(int i = 0; i < keyArray.length; i++){

            builder.append(keyArray[i]);
            builder.append("    ");


        }

        builder.append("\n");

        for(int i = 0; i < keyArray.length; i++){

            builder.append(keyArray[i]);
            builder.append(" ");

            for(int j = 0; j < keyArray.length; j++){

                builder.append(round(matrix[i][j], 2));
                builder.append(" ");

            }

            builder.append("\n");

        }

        return builder.toString();

    }

    private double round(double value, int precision) {

        int scale = (int)Math.pow(10, precision);

        return (double)Math.round(value * scale) / scale;

    }


}
