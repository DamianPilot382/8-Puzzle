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
import java.util.*;

public class Search {

    /**
     * Search algorithm to look for the solution to a puzzle.
     * This algorithm implements A* searching for both graph and tree search.
     * @param puzzle Puzzle to be searched.
     * @param useExplored Set to true if using an explored set (graph search), false otherwise (tree search).
     * @param heur heuristic to be used
     * @return Steps needed to solve the puzzle. Will return null if no solution found.
     */
    public static Object[] search(Puzzle puzzle, boolean useExplored, Heuristic heur){

        //Used to order which puzzle solution to explore next.
        PriorityQueue<Puzzle> frontier = new PriorityQueue<>(new PuzzleComparator(heur));

        //Add the first puzzle to the frontier.
        frontier.offer(puzzle);

        //Initialize the explored set
        HashSet<Puzzle> explored = new HashSet<>();

        int nodeCount = 1;

        while(true){
            
            //If the frontier is empty, a solution wasn't found.
            //This shouldn't happen if the puzzle is a valid one.
            if(frontier.isEmpty())
                return null; //FAILURE
            
            //Get the next puzzle.
            Puzzle node = frontier.poll();
            nodeCount++;

            //If this puzzle is the goal state, return the steps taken.
            if(node.checkGoalState())
                return new Object[]{node.getSteps(), nodeCount}; //SOLUTION


            //Add the puzzle to the explored set if using graph search.
            if(useExplored)
                explored.add(node);

            //Get the possible steps for this node.
            Step[] steps = node.getPossibleSteps();

            //Loop through every possible state
            for(Step step : steps){

                //Get a new puzzle with this move
                Puzzle child = node.move(step);

                //Add this puzzle to the frontier if not using the explored set OR
                //Or if using the explored, add it to the frontier if it isn't in the explored set.
                if(!useExplored || (useExplored && !(explored.contains(child)))){
                    frontier.add(child);

                }

            }

        }

    }

}

/**
 * PuzzleComparator is used to compare puzzles for the PriorityQueue.
 */
class PuzzleComparator implements Comparator<Puzzle> {


    //Heuristic used to calculate the estimated cost.
    Heuristic heuristic;

    /**
     * Creates a new puzzle comparator.
     * @param heuristic The heuristic to use.
     */
    public PuzzleComparator(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    /**
     * Compares the two puzzles.
     * @param a first puzzle to compare
     * @param b second puzzle to compare
     * 
     * @return positive number if a has a higher estimated cost. Negative otherwise. Zero if equal.
     */
    @Override
    public int compare(Puzzle a, Puzzle b){
            return a.getEstimatedCost(heuristic) - b.getEstimatedCost(heuristic);
    }
}