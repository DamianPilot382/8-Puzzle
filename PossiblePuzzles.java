import java.io.FileWriter;
import java.io.IOException;

public class PossiblePuzzles{

    public static void getPossibleSteps() throws IOException {
        FileWriter writer = new FileWriter("puzzles.txt");
        
        Testerer t = new Testerer();

        while(t.hasNext()){
            t.getNextPuzzle();
            writer.append(t.getStringPuzzle() + "\n");

            System.out.println(t.getStringPuzzle());

        }

        writer.flush();

        writer.close();

        System.out.println("Done!");

    }

    public static void main(String[] args){
        try{
            getPossibleSteps();
        }catch(IOException e){
            System.out.println("Shiba power");
        }
    }
}