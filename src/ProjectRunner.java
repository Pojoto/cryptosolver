package src;

import java.io.File;
import java.io.FileNotFoundException;

public class ProjectRunner {
    

    public static void main(String[] args) throws FileNotFoundException{

        File training = new File("src\\training.txt");

        Solver solver = new Solver(training);

        File ciphertext = new File("src\\text.txt");

        solver.evaluate(ciphertext);

    }


}
