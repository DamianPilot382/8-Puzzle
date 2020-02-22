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
        while(true){
            testSearch();
        }
    }

    public static void testSearch(){

        Puzzle puzzle = new Puzzle(3);

        Heuristic heuristic = Heuristic.MANHATTAN;

        System.out.println(puzzle);

        Object[] searchReturn = Search.search(puzzle, true, heuristic);

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