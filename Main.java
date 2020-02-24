import java.io.IOException;

/**
 * @author Damian Ugalde
 * @date 2020-02-23
 * @version 1.0
 *
 * Project 1
 * CS 4200 - Artificial Intelligence
 * California State Polytechnic University, Pomona
 * Computer Science Department
 *
 * Instructor: Dominick A. Atanasio
 *
 */
public class Main{

    public static void main(String[] args){
        runTests();
    }

    public static void runTests(){
        try{
            FileExport export = new FileExport("ans.csv");

            System.out.println("Running tests...");
            export.runTests();
            System.out.println("Completed");


        }catch(IOException e){
            System.out.println("Something went wrong when writing the file.");
        }
    }


    public static void testSearch(){

        Puzzle puzzle = new Puzzle();

        Heuristic heuristic = Heuristic.MANHATTAN;

        System.out.println(puzzle);

        Object[] searchReturn = Search.search(puzzle, false, heuristic);

        Step[] steps = (Step[]) searchReturn[0];

        
        if(steps == null) {
            System.out.println("No solution found.");
            System.exit(0);
        }

        System.out.println("Count: " + (int) searchReturn[1]);
        System.out.println("Cost: " + steps.length);



        Puzzle next = puzzle;

        for(Step i : steps){
            System.out.println(i);
            System.out.println(next);
            next = next.move(i);
        }

        System.out.println(next);

    }

}