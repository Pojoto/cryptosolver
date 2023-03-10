
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
import java.util.Currency;

public class Solver{

    private BigramTable trainingTable;
    private HashMap<String, Double> trigramMap;

    public static char[] alphabet = {

        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

    };

    // public static char[] alphabet = {

    //     'a', 'b', 'c', 'd'

    // };



    public Solver(File trainingFile) throws FileNotFoundException{ 

        trainingTable = bigramize(trainingFile);
        trigramMap = trigramize(trainingFile);

    }

    private String decipher(File cipherFile, HashMap<Character, Character> key) throws FileNotFoundException{

        StringBuilder builder = new StringBuilder();
        
        Scanner lineReader = new Scanner(cipherFile);

        while(lineReader.hasNextLine()){

            String line = lineReader.nextLine();

            if(line.charAt(line.length() - 1) != ' '){

                line = line + " ";

            }

            for(int i = 0; i < line.length(); i++){

                char token = Character.toLowerCase(line.charAt(i));

                builder.append(key.get(token));

            }
            
        }

        lineReader.close();

        return builder.toString();

    }

    //2-depth best neighbor hill climbing
    public String solve5(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate(cipherTable);
        
        char min1 = alphabet[0];
        char min2 = alphabet[1];
        char min3 = alphabet[0];
        char min4 = alphabet[1];

        boolean resume = true;

        System.out.println("START: " + minEvaluation);
        System.out.println(evaluate2(cipherFile, cipherTable));

        System.out.println(cipherTable.getKey().toString());

        while(resume){
            
            double startEvaluation = minEvaluation;

            for(int i = 0; i < alphabet.length; i++){

                for(int j = i + 1; j < alphabet.length; j++){

                    count++;

                    char plaintext1 = alphabet[i];

                    char plaintext2 = alphabet[j];
    
                    cipherTable.swap(plaintext1, plaintext2);

                    for(int x = 0; x < alphabet.length; x++){

                        for(int z = x; z < alphabet.length; z++){

                            char plaintext3 = alphabet[x];

                            char plaintext4 = alphabet[z];
            
                            cipherTable.swap(plaintext3, plaintext4);

                            double currEvaluation = evaluate(cipherTable);

                            if(currEvaluation < minEvaluation){

                                minEvaluation = currEvaluation;
        
                                min1 = plaintext1;
                                min2 = plaintext2;
                                min3 = plaintext3;
                                min4 = plaintext4;
        
                                // System.out.println("SWAP: " + i + " + " + j);
        
                                // System.out.println(minEvaluation);
        
                                // System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");
        
                            }

                            cipherTable.swap(plaintext3, plaintext4);

                        }

                    }

                    cipherTable.swap(plaintext1, plaintext2);


                }

            }

            if(startEvaluation == minEvaluation){

                resume = false;

            } else {

                cipherTable.swap(min1, min2);
                cipherTable.swap(min3, min4);

                System.out.println("SWAP: " + cipherTable.getKey().get(min1) + " + " + cipherTable.getKey().get(min2));

                System.out.println(minEvaluation);

                System.out.println(evaluate2(cipherFile, cipherTable));

                System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

            }

            //count++;

        }

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(evaluate2(cipherFile, cipherTable));

        System.out.println(cipherTable.getKey().toString());

        System.out.println(count);

        return decipher(cipherFile, cipherTable.getKey());

    }


    //simulated annealing
    public String solve0(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate(cipherTable);

        double smallest = minEvaluation;
        
        char min1 = alphabet[0];
        char min2 = alphabet[1];

        boolean resume = true;

        System.out.println("START: " + minEvaluation);

        System.out.println(cipherTable.getKey().toString());

        double temperature = 2;

        while(temperature > 1){

            count++;

            int random1 = (int)(Math.random() * 27);
            int random2 = (int)(Math.random() * 27);

            char plaintext1 = alphabet[random1];

            char plaintext2 = alphabet[random2];

            cipherTable.swap(plaintext1, plaintext2);

            double currEvaluation = evaluate(cipherTable);

            double difference = minEvaluation - currEvaluation;

            //System.out.println(currEvaluation);

            if(currEvaluation < smallest){

                smallest = currEvaluation;

            }

            if(currEvaluation < minEvaluation){

                minEvaluation = currEvaluation;

            } else if(Math.pow(Math.E, -(difference / temperature)) < Math.random()){

                minEvaluation = currEvaluation;

            } else {

                cipherTable.swap(plaintext1, plaintext2);

            }

            temperature = temperature - 0.00001;//* .99999999;


            // if(startEvaluation == minEvaluation){

            //     resume = false;

            // } else {

            //     cipherTable.swap(min1, min2);

            //     System.out.println("SWAP: " + cipherTable.getKey().get(min1) + " + " + cipherTable.getKey().get(min2));

            //     System.out.println(minEvaluation);

            //     System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

            // }

            //count++;

        }

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(cipherTable.getKey().toString());

        System.out.println(count);

        System.out.println(smallest);

        return decipher(cipherFile, cipherTable.getKey());

    }



    //simulated annealing using trigram evaluation
    public String solve4(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate2(cipherFile, cipherTable);

        double smallest = minEvaluation;
        
        char min1 = alphabet[0];
        char min2 = alphabet[1];

        boolean resume = true;

        System.out.println("START: " + minEvaluation);

        System.out.println(cipherTable.getKey().toString());

        double temperature = 2;

        while(temperature > 1){

            count++;

            int random1 = (int)(Math.random() * 27);
            int random2 = (int)(Math.random() * 27);

            char plaintext1 = alphabet[random1];

            char plaintext2 = alphabet[random2];

            cipherTable.swap(plaintext1, plaintext2);

            double currEvaluation = evaluate2(cipherFile, cipherTable);

            double difference = minEvaluation - currEvaluation;

            //System.out.println(currEvaluation);

            if(currEvaluation > smallest){

                smallest = currEvaluation;

            }

            if(currEvaluation > minEvaluation){

                minEvaluation = currEvaluation;

            } else if(Math.pow(Math.E, -(difference / temperature)) < Math.random()){

                minEvaluation = currEvaluation;

            } else {

                cipherTable.swap(plaintext1, plaintext2);

            }

            temperature = temperature - 0.00001;//* .99999999;


            // if(startEvaluation == minEvaluation){

            //     resume = false;

            // } else {

            //     cipherTable.swap(min1, min2);

            //     System.out.println("SWAP: " + cipherTable.getKey().get(min1) + " + " + cipherTable.getKey().get(min2));

            //     System.out.println(minEvaluation);

            //     System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

            // }

            //count++;

        }

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(cipherTable.getKey().toString());

        System.out.println(count);

        System.out.println(smallest);

        return decipher(cipherFile, cipherTable.getKey());

    }



    //best neighbor hill climbing
    public String solve1(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate(cipherTable);
        
        char min1 = alphabet[0];
        char min2 = alphabet[1];

        boolean resume = true;

        System.out.println("START: " + minEvaluation);
        System.out.println(evaluate2(cipherFile, cipherTable));

        System.out.println(cipherTable.getKey().toString());

        while(resume){
            
            double startEvaluation = minEvaluation;

            for(int i = 0; i < alphabet.length; i++){

                for(int j = i + 1; j < alphabet.length; j++){

                    count++;

                    char plaintext1 = alphabet[i];

                    char plaintext2 = alphabet[j];
    
                    cipherTable.swap(plaintext1, plaintext2);

                    double currEvaluation = evaluate(cipherTable);

                    if(currEvaluation < minEvaluation){

                        minEvaluation = currEvaluation;

                        min1 = plaintext1;
                        min2 = plaintext2;

                        // System.out.println("SWAP: " + i + " + " + j);

                        // System.out.println(minEvaluation);

                        // System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

                    }

                    cipherTable.swap(plaintext1, plaintext2);


                }

            }

            if(startEvaluation == minEvaluation){

                resume = false;

            } else {

                cipherTable.swap(min1, min2);

                System.out.println("SWAP: " + cipherTable.getKey().get(min1) + " + " + cipherTable.getKey().get(min2));

                System.out.println(minEvaluation);

                System.out.println(evaluate2(cipherFile, cipherTable));

                System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

            }

            //count++;

        }

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(evaluate2(cipherFile, cipherTable));

        System.out.println(cipherTable.getKey().toString());

        System.out.println(count);

        return decipher(cipherFile, cipherTable.getKey());

    }


    //best neighbor trigram analysis
    public String solve3(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate2(cipherFile, cipherTable);
        
        char min1 = alphabet[0];
        char min2 = alphabet[1];

        boolean resume = true;

        System.out.println("START: " + minEvaluation);
        System.out.println(evaluate(cipherTable));

        System.out.println(cipherTable.getKey().toString());

        while(resume){
            
            double startEvaluation = minEvaluation;

            for(int i = 0; i < alphabet.length; i++){

                for(int j = i + 1; j < alphabet.length; j++){

                    count++;

                    char plaintext1 = alphabet[i];

                    char plaintext2 = alphabet[j];
    
                    cipherTable.swap(plaintext1, plaintext2);

                    double currEvaluation = evaluate2(cipherFile, cipherTable);

                    if(currEvaluation > minEvaluation){

                        minEvaluation = currEvaluation;

                        min1 = plaintext1;
                        min2 = plaintext2;

                        // System.out.println("SWAP: " + i + " + " + j);

                        // System.out.println(minEvaluation);

                        // System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

                    }

                    cipherTable.swap(plaintext1, plaintext2);


                }

            }

            if(startEvaluation == minEvaluation){

                resume = false;

            } else {

                cipherTable.swap(min1, min2);

                System.out.println("SWAP: " + cipherTable.getKey().get(min1) + " + " + cipherTable.getKey().get(min2));

                System.out.println(minEvaluation);

                System.out.println(evaluate(cipherTable));

                System.out.println(decipher(cipherFile, cipherTable.getKey()) + "\n");

            }

            //count++;

        }

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(evaluate(cipherTable));

        System.out.println(cipherTable.getKey().toString());

        System.out.println(count);

        return decipher(cipherFile, cipherTable.getKey());

    }


    //better neighbor hill climbing
    public String solve2(File cipherFile) throws FileNotFoundException{

        int count = 0;

        BigramTable cipherTable = bigramize(cipherFile);

        double minEvaluation = evaluate(cipherTable);

        System.out.println("START: " + minEvaluation);

        // System.out.println(cipherTable.toString() + "\n");

        for(int i = 0; i < alphabet.length; i++){

            for(int j = i + 1; j < alphabet.length; j++){

                count++;

                char plaintext1 = alphabet[i];

                char plaintext2 = alphabet[j];

                cipherTable.swap(plaintext1, plaintext2);

                double currEvaluation = evaluate(cipherTable); 
                
                // System.out.println(count);
                // System.out.println(cipherTable.getKey().toString());
                // System.out.println(currEvaluation);
                // System.out.println(cipherTable.toString() + "\n");

                if(currEvaluation < minEvaluation){

                    minEvaluation = currEvaluation;
                    i = 0;

                    // System.out.println("^SWAPPED\n");

                    break;

                } else if(currEvaluation > minEvaluation){

                    cipherTable.swap(plaintext1, plaintext2);

                } else {

                    //the evaluations are equal
                    //System.out.println("I: " + plaintext1 + " J: " + plaintext2);

                }


            }


        }

        System.out.println(cipherTable.getKey().toString());

        System.out.println("FINAL: " + minEvaluation);

        System.out.println(count);

        // System.out.println(trainingTable.toString());

        // System.out.println(cipherTable.toString());

        return decipher(cipherFile, cipherTable.getKey());

    }

    public double evaluate(BigramTable cipherTable) throws FileNotFoundException{

        double sum = 0;

        double[][] cipherArray = cipherTable.getMatrix();

        double[][] trainingArray = trainingTable.getMatrix();

        for(int i = 0; i < alphabet.length; i++){

            for(int j = 0; j < alphabet.length; j++){

                double difference = Math.abs(trainingArray[i][j] - cipherArray[i][j]);

                //System.out.println(Math.log(difference));

                sum += difference;//Math.log(difference);

            }

        }

        return sum;

    }

    public double evaluate2(File cipherFile, BigramTable cipherTable) throws FileNotFoundException{

        String text = decipher(cipherFile, cipherTable.getKey());

        double sum = 0;

        for(int i = 0; i < text.length() - 2; i++){

            char char1 = text.charAt(i);
            char char2 = text.charAt(i + 1);
            char char3 = text.charAt(i + 2);

            String trigram = "" + char1 + char2 + char3;

            double result = trigramMap.containsKey(trigram) ? trigramMap.get(trigram) : 0;

            sum = sum + result;

        }

        return sum;

    }

    private boolean alphabetContains(char c){

        for(int i = 0; i < alphabet.length; i++){

            if(alphabet[i] == c){

                return true;

            }

        }

        return false;

    }

    public HashMap<String, Double> trigramize(File file) throws FileNotFoundException{

        HashMap<String, Double> trigramMap = new HashMap<String, Double>();

        int count = 0;

        Scanner lineReader = new Scanner(file);

        while(lineReader.hasNextLine()){

            String line = " " + lineReader.nextLine();

            if(line.charAt(line.length() - 1) != ' '){

                line = line + " ";

            }

            count += line.length();

        }

        count -= 2;

        lineReader.close();

        Scanner lineReader2 = new Scanner(file);

        while(lineReader2.hasNextLine()){

            String line = " " + lineReader2.nextLine();

            if(line.charAt(line.length() - 1) != ' '){

                line = line + " ";

            }

            for(int i = 0; i < line.length() - 2; i++){

                char char1 = line.charAt(i);
                char char2 = line.charAt(i + 1);
                char char3 = line.charAt(i + 2);

                String trigram = ("" + char1 + char2 + char3).toLowerCase();

                double unit = 1.0 / count;

                trigramMap.put(trigram, trigramMap.containsKey(trigram) ? trigramMap.get(trigram) + unit : unit);


            }

        }

        lineReader2.close();

        return trigramMap;

    }

    public BigramTable bigramize(File file) throws FileNotFoundException{

        Map<String, Integer> bigramMap = defaultMap();

        int count = 0;

        Scanner lineReader = new Scanner(file);

        while(lineReader.hasNextLine()){

            String line = " " + lineReader.nextLine();

            if(line.charAt(line.length() - 1) != ' '){

                line = line + " ";

            }

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

        double[][] freq = new double[alphabet.length][alphabet.length];

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

