package src;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class BigramTable {
    
    private Matrix matrix;

    private char[] key;

    private double[][] trainingTable;

    private final File TRAINING_FILE = new File("C:\\Users\\joshu\\Documents\\GitHub\\cryptosolver\\src\\training.txt");

    
    public BigramTable(){

        matrix = new Matrix(27, 27);

        key = new char[27];

        trainingTable(TRAINING_TEXT_PATH);



    }

    public BigramTable(Matrix m){

        matrix = m;

        key = solver.alphabet;

        // key = new char[]{
            
        //     ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        //     'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

        // };

        trainingTable(TRAINING_TEXT_PATH);



    }

    public char[] getKey(){

        return key;

    }

    public Matrix geMatrix(){

        return matrix;

    }

    public double evaluate(){

        double sum = 0;

        double[][] trainingTable = trainingTable();

        for(int i = 0; i < 27; i++){

            for(int j = 0; j < 27; j++){

                double difference = Math.abs(trainingTable[i][j] - matrix.getArray()[i][j]);

                sum += Math.log(difference);

            }

        }

        return sum;

    }

    public static double[][] trainingTable(File file){

        Map<String, Integer> bigramMap = defaultMap();

        int count = 0;

        Scanner lineReader = new Scanner(file);

        while(lineReader.hasNextLine()){

            String line = lineReader.nextLine();

            for(int i = 0; i < line.length() - 1; i++){

                char char1 = line.charAt(i);

                char char2 = line.charAt(i + 1);

                String bigram = ("" + char1 + char2).toLowerCase();

                System.out.println(bigram);

                bigramMap.put(bigram, bigramMap.get(bigram) + 1);

                count++;

            }
            
        }

        int index = 0;

        String[] keys = bigramMap.keySet().toArray(new String[0]); 

        double[][] freq = new double[27][27];

        for(int i = 0; i < freq.length; i++){

            for(int j = 0; j < freq[i].length; j++){

                freq[i][j] = bigramMap.get(keys[index]) / (double) count;

                index++;

            }

        }

        


        double[][] trainingTable = new double[27][27];

        for(int i = 0; i < trainingTable.length; i++){

            for(int j = 0; j < trainingTable[i].length; j++){

                //load the default training bigram values into this 2d array

            }

        }

        return trainingTable;


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

    private static double round(double value, int precision) {

        int scale = (int)Math.pow(10, precision);

        return (double)Math.round(value * scale) / scale;

    }


}
