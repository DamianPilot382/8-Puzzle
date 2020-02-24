import java.util.Arrays;

public class Testerer{
    
    private byte[] puzzle;
    private boolean done;
    private byte spaceLoc;

    public Testerer(){
        puzzle = new byte[9];
        done = false;
        this.spaceLoc = 0;
    }

    private boolean checkDuplicates(){
        for(byte i = 0; i < puzzle.length; i++){
            for(byte j = (byte)(i+1); j < puzzle.length; j++){
                if(puzzle[j] == puzzle[i] || puzzle[i] == 9 || puzzle[j] == 9)
                    return false;
                if(puzzle[i] == 0)
                    this.spaceLoc = i;
            }
        }

        return true;
    }

    public byte getSpaceLoc(){
        return this.spaceLoc;
    }

    private void incrementPuzzle(byte n){

        while(n > 0){
            
            if(puzzle[n] != 9){
                puzzle[n]++;
                break;
            }

            puzzle[n] = 0;
            n--;
        }

        if(n == 0)
            done = true;
    }

    public boolean hasNext(){
        return !done;
    }

    private void incrementPuzzle(){
        this.incrementPuzzle((byte)(puzzle.length - 1));
    }

    public byte[] getNextPuzzle(){
        do{
            this.incrementPuzzle();
        }while(!this.checkDuplicates());

        return Arrays.copyOf(puzzle, puzzle.length);
    }

    public String getStringPuzzle(){
        StringBuilder builder = new StringBuilder();
        for(byte i : puzzle){
            builder.append(i+" ");
        }

        return builder.toString().trim();
    }

    public static void main(String[] args){
        Testerer t = new Testerer();
        while(t.hasNext()){
            t.getNextPuzzle();
            System.out.println(t.getStringPuzzle());
        }
    }

}