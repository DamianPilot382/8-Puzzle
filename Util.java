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

public class Util{

    public static Step[] search(Puzzle puzzle, boolean isTree, Heuristic heur){

        int pathCost = 0;

        PriorityQueue<Puzzle> frontier = new PriorityQueue<>();

        frontier.add(puzzle);

        HashSet<Puzzle> explored = new HashSet<>();

        while(true){
            if(frontier.isEmpty()) return null; //FAILURE

            Puzzle node = frontier.poll();

            if(node.checkGoalState())
                return node.getSteps(); //SOLUTION

            explored.add(node);

            Step[] steps = node.getPossibleSteps();

            for(Step step : steps){
                Puzzle child = node.move(step);

                if(!explored.contains(child) && !frontier.contains(child))
                    frontier.add(child);
                
                //TODO
                //else if child is in frontier with higher cost
                    //replace that frontier node with child

            }

        }

    }
}