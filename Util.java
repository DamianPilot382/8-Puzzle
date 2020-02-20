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

        PriorityQueue<Puzzle> frontier = new PriorityQueue<>
        (new Comparator<Puzzle>() {
            public int compare(Puzzle a, Puzzle b){
                System.out.println("A: " + a.getEstimatedCost(heur));
                System.out.println(a);

                System.out.println("B: " + b.getEstimatedCost(heur));
                System.out.println(b);

                return a.getEstimatedCost(heur) - b.getEstimatedCost(heur);
            }
        });

        frontier.add(puzzle);

        HashSet<Puzzle> explored = new HashSet<>();

        int count = 1000;

        while(count-- > 0){
            
            if(frontier.isEmpty()) return null; //FAILURE

            Puzzle node = frontier.poll();

            if(node.checkGoalState())
                return node.getSteps(); //SOLUTION

            explored.add(node);

            Step[] steps = node.getPossibleSteps();

            for(Step step : steps){

                Puzzle child = node.move(step);


                //System.out.println(count);

                //System.out.println(child);

                if(!(explored.contains(child)))
                    frontier.add(child);

                //TODO
                //else if child is in frontier with higher cost
                    //replace that frontier node with child

            }

        }

        return null;

    }
}