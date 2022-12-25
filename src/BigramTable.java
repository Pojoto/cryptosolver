package src;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class BigramTable {
    
    private double[][] matrix;

    private char[] key;

    public BigramTable(){

        matrix = new double[27][27];

        key = new char[27];

    }

    public BigramTable(double[][] matrix){

        this.matrix = matrix;

        key = new char[]{
            
            ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

        };

    }

    public char[] getKey(){

        return key;

    }

    public double[][] getMatrix(){

        return matrix;

    }

    public void swap(int i, int j){

        swapRows(i, j);
        swapCols(i, j);

        char temp = key[i];
        key[i] = key[j];
        key[j] = temp;

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

        builder.append(" ");

        for(int i = 0; i < key.length; i++){

            builder.append(key[i]);
            builder.append("    ");


        }

        builder.append("\n");

        for(int i = 0; i < key.length; i++){

            builder.append(key[i]);
            builder.append(" ");

            for(int j = 0; j < key.length; j++){

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
