
package src;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

public class Solver{

    private BigramTable trainingTable;

    public static char[] alphabet = {

        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

    };



    public Solver(File trainingFile) throws FileNotFoundException{ 

        trainingTable = bigramize(trainingFile);

    }

    private String decipher(File cipherFile, HashMap<Character, Character> key) throws FileNotFoundException{

        StringBuilder builder = new StringBuilder();
        
        Scanner lineReader = new Scanner(cipherFile);

        while(lineReader.hasNextLine()){

            String line = lineReader.nextLine();

            for(int i = 0; i < line.length(); i++){

                char token = Character.toLowerCase(line.charAt(i));

                builder.append(key.get(token));

            }
            
        }

        lineReader.close();

        return builder.toString();

    }


    public String solve(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate(cipherTable);

        for(int i = 0; i < alphabet.length; i++){

            for(int j = i + 1; j < alphabet.length; j++){

                count++;

                cipherTable.swap(i, j);

                double currEvaluation = evaluate(cipherTable);    


                if(currEvaluation < minEvaluation){

                    minEvaluation = currEvaluation;
                    i = 0;
                    break;

                } else if(currEvaluation > minEvaluation){

                    cipherTable.swap(i, j);

                } else {

                    System.out.println("WOW");

                }


            }


        }

        System.out.println("FINISHED!!");

        System.out.println(minEvaluation);

        System.out.println(count);

        return decipher(cipherFile, cipherTable.getKey());

    }


    public double evaluate(BigramTable cipherTable) throws FileNotFoundException{

        double sum = 0;

        double[][] cipherArray = cipherTable.getMatrix();

        double[][] trainingArray = trainingTable.getMatrix();

        for(int i = 0; i < 27; i++){

            for(int j = 0; j < 27; j++){

                double difference = Math.abs(trainingArray[i][j] - cipherArray[i][j]);

                //System.out.println(Math.log(difference));

                sum += difference;//Math.log(difference);

            }

        }

        return sum;

    }

    public BigramTable bigramize(File file) throws FileNotFoundException{

        Map<String, Integer> bigramMap = defaultMap();

        int count = 0;

        Scanner lineReader = new Scanner(file);

        while(lineReader.hasNextLine()){

            String line = lineReader.nextLine();

            for(int i = 0; i < line.length() - 1; i++){

                char char1 = line.charAt(i);

                char char2 = line.charAt(i + 1);

                String bigram = ("" + char1 + char2).toLowerCase();

                bigramMap.put(bigram, bigramMap.get(bigram) + 1);

                count++;

            }
            
        }

        lineReader.close();

        int index = 0;

        String[] keys = bigramMap.keySet().toArray(new String[0]); 

        double[][] freq = new double[27][27];

        for(int i = 0; i < freq.length; i++){

            for(int j = 0; j < freq[i].length; j++){

                freq[i][j] = bigramMap.get(keys[index]) / (double) count;

                index++;

            }

        }

        BigramTable table = new BigramTable(freq);

        return table;

        //plan is to preload a treemap of all bigrams possible, each with default 0 frequency. 
        //then we analyze text, looping through each bigram and updating the corresponding tree map entry values
        //, the bigram frequency. 
        //after all the characters in the text are analyzed, the hash map can be mapped to a 2d array consisting of the 
        //frequencies of the bigrams in order. any bigrams not found in text will have the default 0 frequency from the hash map
        //now the 2d array can be made a matrix and then made a bigram table object. 
        //note that bigram frequencies must be divided by total count of bigrams in the text.

    }


    //function for creating a returning a default treemap consisting of all the possible bigrams as keys and 0 as a frequency value. 
    public static Map<String, Integer> defaultMap(){

        TreeMap<String, Integer> map = new TreeMap<>();

        for(int i = 0; i < alphabet.length; i++){

            for(int j = 0; j < alphabet.length; j++){

                map.put("" + alphabet[i] + alphabet[j], 0);

            }

        }

        return map;

    }









    public static String[] ngrams(String text, int n){

        HashMap<String, Integer> ngramMap = new HashMap<String, Integer>();

        for(int i = 0; i < text.length() - n + 1; i++){

            String ngram = "";

            for(int j = 0; j < n; j++){

                ngram = ngram + text.charAt(i + j);

            }

            ngram = ngram.toLowerCase();
 
            //if the map contains the ngram already, increment frequency. else, assign a new key/value in the map of freq 1
            ngramMap.put(ngram, ngramMap.containsKey(ngram) ? ngramMap.get(ngram) + 1 : 1);

        }

        Map<String, Integer> ngramMap2 = MapUtil.sortByValue(ngramMap);

        Set<String> ngramSet = ngramMap2.keySet();

        String[] ngrams = ngramSet.toArray(new String[ngramSet.size()]);

        //Arrays.sort(ngrams);


        return ngrams;

    }







    

    public class MapUtil {

        public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

            List<Entry<K, V>> list = new ArrayList<>(map.entrySet());

            list.sort(Entry.<K, V>comparingByValue().reversed());
    
            Map<K, V> result = new LinkedHashMap<>();

            for (Entry<K, V> entry : list) {

                result.put(entry.getKey(), entry.getValue());

            }
    
            return result;
        }
        
    }
    



}

