package src;
public class BigramTable {
    
    private Matrix matrix;

    private char[] key;

    
    public BigramTable(){

        matrix = new Matrix(27, 27);

        key = new char[27];



    }

    public BigramTable(Matrix m){

        matrix = m;

        key = solver.alphabet;

        // key = new char[]{
            
        //     ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        //     'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

        // };



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

    public static double[][] trainingTable(){

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
