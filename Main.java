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
        testSearch();
    }

    public static void testSearch(){
        Puzzle puzzle = new Puzzle("1 2 3 4 0 5 6 7 8");

        Heuristic heuristic = Heuristic.NONE;

        System.out.println(puzzle);

        Step[] steps = Util.search(puzzle, true, heuristic);

        for(Step step : steps){
            System.out.println(step);
        }

    }

    public static void testText(){
        Puzzle puzzle = new Puzzle("1 2 3 4 0 5 6 7 8");

        System.out.println(puzzle.spaceLoc);

        System.out.println(puzzle);
        Step[] arr = puzzle.getPossibleSteps();

        for(Step i : arr){
            System.out.println(i);
            System.out.println(puzzle.move(i));
        }
    }

}