
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;

public class solver{

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        String ciphertext = input.nextLine();

        System.out.println(Arrays.toString(ngrams(ciphertext, 3)));

    }


    public static String[] ngrams(String text, int n){

        HashMap<String, Integer> ngramMap = new HashMap<String, Integer>();

        for(int i = 0; i < text.length() - n + 1; i++){

            String ngram = "";

            for(int j = 0; j < n; j++){

                ngram = ngram + text.charAt(i + j);

            }
 
            //if the map contains the ngram already, increment frequency. else, assign a new key/value in the map of freq 1
            ngramMap.put(ngram, ngramMap.containsKey(ngram) ? ngramMap.get(ngram) + 1 : 1);

        }

        Set<String> ngramSet = ngramMap.keySet();

        String[] ngrams = ngramSet.toArray(new String[ngramSet.size()]);

        Arrays.sort(ngrams);


        return ngrams;

    }



}
