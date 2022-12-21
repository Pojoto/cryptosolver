package src;
public class BigramTable {
    
    private Matrix matrix;

    private char[] key;

    
    public BigramTable(){

        matrix = new Matrix(27, 27);

        key = new char[27];



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


}
