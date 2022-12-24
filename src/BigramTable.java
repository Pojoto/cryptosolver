package src;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class BigramTable {
    
    private Matrix matrix;

    private char[] key;

    public BigramTable(){

        matrix = new Matrix(27, 27);

        key = new char[27];

    }

    public BigramTable(Matrix m){

        matrix = m;

        key = new char[]{
            
            ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

        };

    }

    public char[] getKey(){

        return key;

    }

    public Matrix geMatrix(){

        return matrix;

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

                builder.append(round(matrix.getArray()[i][j], 2));
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
