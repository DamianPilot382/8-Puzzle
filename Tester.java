public class Tester{
    
    private int[] puzzle;
    private boolean done;

    public Tester(int sideLength){
        puzzle = new int[]{0, 1, 2, 3, 5, 4, 7, 6, 8};//new int[sideLength * sideLength];
        done = false;
    }

    private boolean checkDuplicates(){
        for(int i = 0; i < puzzle.length; i++){
            for(int j = i+1; j < puzzle.length; j++){
                if(puzzle[j] == puzzle[i])
                    return false;
            }
        }

        return true;
    }

    private void incrementPuzzle(int n){

        if(puzzle[n] != 9)
            puzzle[n]++;
        else{
            if(n != 0){
                puzzle[n] = 0;
                incrementPuzzle(n-1);
            }
            else
                done = true;
        }

    }

    public boolean hasNext(){
        return !done;
    }

    private void incrementPuzzle(){
        this.incrementPuzzle(puzzle.length - 1);
    }

    public String getNextPuzzle(){
        StringBuilder builder = new StringBuilder(puzzle.length);

        for(int i : puzzle){
            builder.append(i + " ");
        }

        do{
            incrementPuzzle();
        }while(!checkDuplicates());

        return builder.toString().trim();
    }

}