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

        Puzzle puzzle = new Puzzle(3);

        Heuristic heuristic = Heuristic.MANHATTAN;

        Step[] steps = Util.search(puzzle, true, heuristic);

        if(steps == null){
            System.out.println("No solution found.");
            System.exit(0);
        }

        Puzzle next = puzzle;

        for(Step i : steps){
            System.out.println(i);
            System.out.println(next);
            next = next.move(i);
        }

        System.out.println(next);

    }

}