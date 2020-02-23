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
import java.io.FileWriter;
import java.io.IOException;

public class FileExport {

    public static final int COMMA_AMOUNT = 32;

    private FileWriter writer;
    private Tester tester;

    public FileExport(String fileName, int testSize) throws IOException {
        this.writer = new FileWriter(fileName);
        this.tester = new Tester(testSize);
    }

    public void runTests() throws IOException {

        this.addHeaders();

        String next = tester.getNextPuzzle();
        
        while(tester.hasNext()){

            try{
                Puzzle p = new Puzzle(next);

                writer.append(next + ",");
                this.addSearch(p, true, Heuristic.MANHATTAN);
                
                writer.append(next + ",");
                this.addSearch(p, true, Heuristic.MANHATTAN);

                writer.append(next + ",");
                this.addSearch(p, false, Heuristic.HAMMING);
                
                writer.append(next + ",");
                this.addSearch(p, true, Heuristic.MANHATTAN);

                System.gc();

            }catch(IllegalArgumentException e){
                //writer.append("FAILURE");
                //this.fillCommas(COMMA_AMOUNT - 2);

            }
            
            next = tester.getNextPuzzle();
            writer.flush();
            
        }

        writer.flush();
        writer.close();
    }

    private void addSearch(Puzzle p, boolean useExplored, Heuristic heur) throws IOException {

        long time = System.nanoTime();
        Object[] searchReturn = Search.search(p, useExplored, heur);
        time = System.nanoTime() - time;

        Step[] steps = (Step[]) searchReturn[0];

        if(steps == null) {
            System.out.println("No solution found.");
            System.exit(0);
        }

        //writer.append("SUCCESS,");
        writer.append(useExplored+",");
        writer.append(heur+",");
        writer.append(steps.length+",");
        writer.append(searchReturn[1]+",");
        writer.append(time+",\n");

        //this.fillSteps(steps);

        //this.fillCommas(COMMA_AMOUNT - 25 - steps.length);
    }

    private void fillSteps(Step[] steps) throws IOException {
        for(Step i : steps){
            writer.append(i+",");
        }
    }

    private void addHeaders() throws IOException {
        writer.append("Puzzle,");
        //writer.append("Result,");
        writer.append("Explored,");
        writer.append("Heuristic,");
        writer.append("Depth,");
        writer.append("Count,");
        writer.append("Time,\n");
        //writer.append("Steps,\n");
    }

    private void fillCommas(int count) throws IOException{
        for(int i = 1; i <= count; i++){
            writer.append(",");
        }

        writer.append("\n");
    }
}