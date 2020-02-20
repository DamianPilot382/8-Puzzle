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

    private byte[] data;
    private byte sideLength;
    public byte spaceLoc;
    private LinkedList<Step> stepsFromOriginal;

    /**
     * Creates a puzzle from a String of numbers, separated by a space.
     * @param str String to input
     * @throws IllegalArgumentException If string is not valid.
     */
    public Puzzle(String str) throws IllegalArgumentException {
        data = insertData(str);

        //If the numbers are not valid, throw an exception.
        if(!checkNumbers() && !checkParity())
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
    public Puzzle(byte[] data, byte sideLength, LinkedList<Step> steps){
        this.data = data;
        this.sideLength = sideLength;
        this.stepsFromOriginal = steps;
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
    public byte getSideLength(){
        return this.sideLength;
    }

    /**
     * Heuristic that returns the amount of tiles that are not in the
     * position needed for a solution, excluding the empty number.
     * @return Hamming distance for this puzzle configuration.
     */
    public int getHammingDistance(){

        byte count = 0;

        for(int i = 0; i < data.length; i++){

            //If the number is 0, ignore.
            if(data[i] == 0)
                continue;
            
            //If the number is where it should be, add one to the count.
            if(data[i] == i)
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
        byte count = 0;

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
     * Validates the data from the String and creates the data array.
     * @param str String with input to check.
     * @return byte array with the data.
     * @throws IllegalArgumentException if the String isn't valid.
     */
    private byte[] insertData(String str) throws IllegalArgumentException {

        //Get rid of whitespace and replace with underscores.
        str = str.trim().replaceAll("\\s+", "_");

        //Create a string array with the numbers.
        String[] temp = str.split("_");

        //Create a temp array.
        byte[] arr = new byte[temp.length];
        
        for(int i = 0; i < arr.length; i++){
            try{
                //Try to convert the string to a number.
                arr[i] = Byte.valueOf(temp[i]);

                //If the number currently added is 0, set the location
                //For the empty number.
                if(arr[i] == 0)
                    this.spaceLoc = (byte) i;

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
    private byte perfectSquare(int num){

        //if the number we are multiplying is greater than the
        //number / 2, then it is invalid.
        byte end = (byte)(num / 2);


        for(byte i = 1; i < end; i++){
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
    private boolean checkParity(){

        //Boolean flipper for inversion. If the boolean is false,
        //then there is an unequal amount of inversions.
        boolean inverted = true;

        //Loop through the array
        for(int i = 0; i < data.length; i++){
            for(int j = i + 1; j < data.length; j++){

                //If the data at data[j] position belongs below data[i]
                //Flip the inverter.
                if(data[i] > data[j])
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
    public Puzzle move(Step step){

        //Copy this puzzle data to a new array for the new puzzle
        byte[] temp = Arrays.copyOf(data, data.length);

        //Based on the movement taken, update the new puzzle data.
        switch(step){
            case UP:
                swap(temp, spaceLoc, spaceLoc - sideLength);
                break;
            case DOWN:
                swap(temp, spaceLoc, spaceLoc + sideLength);
                break;
            case LEFT:
                swap(temp, spaceLoc, spaceLoc - 1);
                break;
            case RIGHT:
                swap(temp, spaceLoc, spaceLoc + 1);
                break;
        };

        //Copy the steps taken to get to this puzzle for the new puzzle.
        LinkedList<Step> newSteps = (LinkedList) stepsFromOriginal.clone();

        //Add the move that was just performed.
        newSteps.add(step);

        //Create and return the new puzzle object.
        return new Puzzle(temp, sideLength, newSteps);
    }

    /**
     * Helper method to swap two values in an array
     * @param arr Array to perform the swap in.
     * @param a first index to have the data swapped.
     * @param b seocnd index to have the data swapped.
     */
    private void swap(byte[] arr, int a, int b){
        byte temp = arr[a];
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
     * Gets a string representation of the puzzle for printing to the console.
     * @return String representation of this puzzle.
     */
    @Override
    public String toString(){

        //Create a string builder for beter String creation.
        StringBuilder str = new StringBuilder(data.length * 4);

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

}