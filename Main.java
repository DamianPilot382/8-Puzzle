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
        testText();
    }

    public static void testSearch(){
        //Puzzle puzzle = new Puzzle("1 2 3 4 0 5 6 7 8");
        Puzzle puzzle = new Puzzle("6 8 2 5 4 3 1 7 0");

        Heuristic heuristic = Heuristic.HAMMING;

        System.out.println(puzzle);

        Step[] steps = Util.search(puzzle, true, heuristic);

        for(Step step : steps){
            System.out.println(step);
        }

    }

    public static void testText(){
        //Puzzle puzzle = new Puzzle("1 2 3 4 0 5 6 7 8");

        Puzzle puzzle = new Puzzle("6 8 2 5 4 3 1 7 0");

        Heuristic heuristic = Heuristic.HAMMING;

        int count = 3;
        Puzzle next = puzzle;

        

        // while(count-- > 0){

        //     System.out.println(next.getEstimatedCost(heuristic));

        //     System.out.println(next);
        //     Step[] arr = next.getPossibleSteps();

        //     for(Step i : arr){
        //         System.out.println(i);
        //         System.out.println(next.move(i));
        //         next = next.move(i);
        //     }
        // }
    }

}