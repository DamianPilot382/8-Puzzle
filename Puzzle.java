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

public class Puzzle {

    private int[] data;
    private int sideLength;
    private int spaceLoc;
    private LinkedList<Step> stepsFromOriginal;
    private int estimatedCost = -1;

    /**
     * Creates a puzzle with random numbers from a specified size.
     * @param sideLength Length of one side of the n x n puzzle.
     */
    public Puzzle(int sideLength){
        this.sideLength = sideLength;
        this.stepsFromOriginal = new LinkedList<Step>();
        data = randomShuffle();
    }

    /**
     * Creates a puzzle from a String of numbers, separated by a space.
     * @param str String to input
     * @throws IllegalArgumentException If string is not valid.
     */
    public Puzzle(String str) throws IllegalArgumentException {
        data = insertData(str);

        //If the numbers are not valid, throw an exception.
        if(!checkNumbers() || !checkParity(data))
            throw new IllegalArgumentException();

        this.stepsFromOriginal = new LinkedList<>();
    }

    /**
     * Creates a puzzle without checking for errors.
     * Good for creating a puzzle from a previous puzzle and for performance.
     * @param data number information for the puzzle.
     * @param sideLength Length of the sides of the n x n square.
     * @param steps Steps taken from original to get to this point.
     */
    public Puzzle(int[] data, int sideLen, int spaceLoc, List<Step> steps){
        this.data = data;
        this.sideLength = sideLen;
        this.stepsFromOriginal = (LinkedList<Step>) steps;
        this.spaceLoc = spaceLoc;
    }

    /**
     * Validates the data from the String and creates the data array.
     * @param str String with input to check.
     * @return int array with the data.
     * @throws IllegalArgumentException if the String isn't valid.
     */
    private int[] insertData(String str) throws IllegalArgumentException {

        //Get rid of whitespace and replace with underscores.
        str = str.trim().replaceAll("\\s+", "_");

        //Create a string array with the numbers.
        String[] temp = str.split("_");

        //Create a temp array.
        int[] arr = new int[temp.length];
        
        for(int i = 0; i < arr.length; i++){
            try{
                //Try to convert the string to a number.
                arr[i] = Integer.valueOf(temp[i]);

                //If the number currently added is 0, set the location
                //For the empty number.
                if(arr[i] == 0)
                    this.spaceLoc = i;

            }catch(NumberFormatException e){
                //If the number fails to convert, throw an exception.
                throw new IllegalArgumentException();
            }
        }

        return arr;
    }

    /**
     * Checks if the numbers provided are valid.
     * @return true if valid, false otherwise.
     */
    private boolean checkNumbers(){

        //Check that all continuous numbers are added.

        //Create a bitset
        BitSet set = new BitSet();

        //Loop through all the numbers in the array
        for(int i : data){

            //If the number is already in the bitset, it is a duplicate
            //and reject it.
            if(set.get(i))
                return false;
            else //Otherwise, add the number to the bitset.
                set.set(i, true);
        }

        //Loop through the bitset
        for(int i = 0; i < data.length; i++){
            //If a number is missing from the bitset, not all continuous
            //numbers are in the array, so reject.
            if(!set.get(i))
                return false;
        }

        //Get the length of the side
        this.sideLength = perfectSquare(data.length);
        
        //If the length of the side is not a perfect square, reject it.
        //Otherwise, accept the data.
        return sideLength != -1;
    }

    /**
     * Gets the integer root of num such that i * i = num.
     * @param num number to find the root for.
     * @return root of num. -1 if num is not a perfect square.
     */
    private int perfectSquare(int num){

        //if the number we are multiplying is greater than the
        //number / 2, then it is invalid.
        int end = (int)(num / 2);


        for(int i = 1; i < end; i++){
            //Multiply this number and check if it is the num provided.
            int square = i*i;
            if(square == num)
                //If it is, return it.
                return i;
        }

        //An integer root couldn't be found. Return -1 for error.
        return -1;
    }

    /**
     * Checks if the given data has an even number of inversions.
     * @return True if even inversions, false otherwise.
     */
    private boolean checkParity(int[] arr){

        //Boolean flipper for inversion. If the boolean is false,
        //then there is an unequal amount of inversions.
        boolean inverted = true;

        //Loop through the array
        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){

                if(arr[i] == 0 || arr[j] == 0)
                    continue;

                //If the data at data[j] position belongs below data[i]
                //Flip the inverter.
                if(arr[i] > arr[j])
                    inverted = !inverted;
            }
        }

        return inverted;
    }

    /**
     * Gets the steps that are available based on the position of the
     * empty number.
     * @return Steps available
     */
    public Step[] getPossibleSteps(){

        ArrayList<Step> arr = new ArrayList<>(4);

        //Get the row and col of the empty num.
        int row = spaceLoc / sideLength;
        int col = spaceLoc % sideLength;

        //ROW
        if(row > 0)
            arr.add(Step.UP);
        if(row + 1 < sideLength)
            arr.add(Step.DOWN);
        
        //COL
        if(col > 0)
            arr.add(Step.LEFT);
        if(col + 1 < sideLength)
            arr.add(Step.RIGHT);

        return arr.toArray(new Step[arr.size()]);

    }

    /**
     * Based on the puzzle configuration and the movement specified,
     * move the piece without altering this puzzle and return a new Puzzle
     * object with the move performed.
     * @param step Movement to perform.
     * @return a clone of this Puzzle object with the movement performed.
     */
    @SuppressWarnings("unchecked")
    public Puzzle move(Step step){

        //Copy this puzzle data to a new array for the new puzzle
        int[] temp = Arrays.copyOf(data, data.length);
        int newSpaceLoc = 0;

        //Based on the movement taken, update the new puzzle data.
        switch(step){
            case UP:
                newSpaceLoc = spaceLoc - sideLength;
                break;
            case DOWN:
                newSpaceLoc = spaceLoc + sideLength;
                break;
            case LEFT:
                newSpaceLoc = spaceLoc - 1;
                break;
            case RIGHT:
                newSpaceLoc = spaceLoc + 1;
                break;
        };

        swap(temp, spaceLoc, newSpaceLoc);

        //Copy the steps taken to get to this puzzle for the new puzzle.
        LinkedList<Step> newStep = (LinkedList<Step>) stepsFromOriginal.clone();

        //Add the move that was just performed.
        newStep.add(step);

        //Create and return the new puzzle object.
        return new Puzzle(temp, sideLength, newSpaceLoc, newStep);
    }

    /**
     * Helper method to swap two values in an array
     * @param arr Array to perform the swap in.
     * @param a first index to have the data swapped.
     * @param b seocnd index to have the data swapped.
     */
    private void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Checks if this puzzle has reached the goal state.
     * @return True if at the goal state, false otherwise.
     */
    public boolean checkGoalState(){

        for(int i = 0; i < data.length; i++){

            //If a number is not in the right order, this is not the goal state.
            if(data[i] != i)
                return false;
        }

        //Goal state found, return true.
        return true;
    }

    /**
     * Heuristic that returns the amount of tiles that are not in the
     * position needed for a solution, excluding the empty number.
     * @return Hamming distance for this puzzle configuration.
     */
    public int getHammingDistance(){

        int count = 0;

        for(int i = 0; i < data.length; i++){

            //If the number is 0, ignore.
            if(data[i] == 0)
                continue;
            
            //If the number is where it should be, add one to the count.
            if(data[i] != i)
                count++;
        }

        return count;
    }

    /**
     * Heuristic that determines the distance a number in the puzzle is from
     * where it should be for a solution, excluding the empty number.
     * @return Manhattan distance for this puzzle configuration.
     */
    public int getManhattanDistance(){
        int count = 0;

        for(int i = 0; i < data.length; i++){

            //If the number is zero, ignore it.
            if(data[i] == 0)
                continue;

            //If the number is not in the correct position
            if(data[i] != i){
                //Calculate the distance to the correct position
                //and add it to the running total.
                int delta = Math.abs(i - data[i]);
                count += delta % sideLength + delta / sideLength;
            }
        }

        return count;
    }

    /**
     * Gets the steps taken.
     * @return Array of the steps taken to get to this puzzle state.
     */
    public Step[] getSteps(){
        return stepsFromOriginal.toArray(new Step[stepsFromOriginal.size()]);
    }

    /**
     * Gets the length of one side of the n x n square puzzle.
     * @return length of one side.
     */
    public int getSideLength(){
        return this.sideLength;
    }

    /**
     * Gets the heuristic value based on the specified heuristic calculation.
     * @param heuristic Heuristic calculation to be used.
     * @return Heuristic value
     */
    public int getHeuristic(Heuristic heuristic){
        switch(heuristic){
            case HAMMING:
                return this.getHammingDistance();
            case MANHATTAN:
                return this.getManhattanDistance();
            default:
                return 0;
        }
    }

    /**
     * Gets the amount of steps taken from the original puzzle to
     * get to this puzzle configuration.
     * @return Step count
     */
    public int getStepCount(){
        return this.stepsFromOriginal.size();
    }

    /**
     * Gets the data for this puzzle.
     * @return number data.
     */
    protected int[] getData(){
        return this.data;
    }

    /**
     * Gets the estimated cost based on the specified heuristic.
     * @param heuristic Heuristic to use
     * @return Estimated cost
     */
    public int getEstimatedCost(Heuristic heuristic){
        if(estimatedCost < 0)
            estimatedCost = this.getHeuristic(heuristic) + this.getStepCount();
        return estimatedCost;
    }

    /**
     * Checks if two puzzles are equal by checking their number data.
     * @param other The other puzzle to compare to
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other){

        //System.out.println("EQUAL CHECKED");

        //Check if the other object is a puzzle. If not, return false.
        if(other instanceof Puzzle){

            //Get the data for the other puzzle.
            int[] otherData = ((Puzzle) other).getData();
            
            //If sizes don't match, they're not equal.
            if(otherData.length != this.data.length)
                return false;

            //Loop through the data.
            for(int i = 0; i < data.length; i++){
                
                //If a mismatch is found, they are not equal.
                if(data[i] != otherData[i])
                    return false;
            }
            
        }else return false;

        //No mismatches found, puzzles are equal.
        return true;
    }

    /**
     * Gets a string representation of the puzzle for printing to the console.
     * @return String representation of this puzzle.
     */
    @Override
    public String toString(){

        //Create a string builder for beter String creation.
        StringBuilder str = new StringBuilder(data.length * 4);

        str.append(spaceLoc + "\n");

        //Loop through the data
        for(int i = 1; i <= data.length; i++){

            //Add a border
            str.append("| ");

            //If this is the empty number, add a space instead of a number.
            if(data[i-1] == 0)
                str.append("  | ");
            else
                //Add the number plus a border
                str.append(data[i-1] + " | ");

            //If the side length is reached, create a new line.
            if(i % sideLength == 0)
                str.append("\n");

        }

        return str.toString();
    }

    /**
     * Overrides the hashcode so that it is equivalent to the array of data.
     * This is helpful for comparing this object in a HashMap.
     */
    @Override
    public int hashCode(){

        //Add all the numbers from the data to a String.
        StringBuilder builder = new StringBuilder(data.length);
        for(int i : data){
            builder.append(i);
        }

        return Integer.parseInt(builder.toString());
    }

    /**
     * Generates a random shuffle for a puzzle, then checks that is valid.
     * @return data containing a valid random shuffle.
     */
    private int[] randomShuffle(){

        //Calculate the size once so you only have to multiply once.
        int size = sideLength * sideLength;

        //Create an ArrayList and an array.
        ArrayList<Integer> arr = new ArrayList<>(size);
        int[] temp = new int[size];

        //Add all the numbers from 0 to size to the ArrayList.
        for(int i = 0; i < size; i++){
            arr.add(i);
        }

        do{
            //Shuffle the ArrayList
            Collections.shuffle(arr);

            //Add all the numbers from the ArrayList to the array
            for(int i = 0; i < size; i++){
                temp[i] = arr.get(i);

                //Find the location of the empty number
                if(temp[i] == 0)
                    this.spaceLoc = i;
            }

            //Keep repeating while the numbers are not valid.
        }while(!this.checkParity(temp));

        return temp;
    }

}