package com.company;
/* Created by InteliJ Idea

 *User: Nicholas Gaultney
 *Date: 2/24/2018
 *Time: 3:15 PM
 *Contact: nmgaultney@gmail.com
 */

import java.lang.reflect.Array;
import java.util.*;

public class Yahtzee {
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Die die3 = new Die();
    private Die die4 = new Die();
    private Die die5 = new Die();

    /*
    * Here, I intentionally use an ArrayList of arrays just to experiment with how
    * they work and familiarize myself with some things. Definetely not the best way to go...
    * but I definetely learned a few things.
     */
    private ArrayList<int[]> savedDice = new ArrayList<>();
    private ArrayList<Die> remainingDice = new ArrayList<>(Arrays.asList(die1, die2, die3, die4, die5));
    private HashMap<Integer, ArrayList<Die>> evaluationTable = new HashMap<>();

    public void play(){
        for (int i = 0; i < 3; i++) {
            this.rollDice();
            savedDice.add(this.evaluateDice());             // Adds the dieNumber and dice count to the savedDice list.
            printSavedDice();
        }
    }

    public void rollDice(){
        evaluationTable.clear();
        for(Die die : remainingDice){
            die.roll();
            evaluationTable.putIfAbsent(die.getValue(), new ArrayList<>());
            evaluationTable.get(die.getValue()).add(die);
            die.printValue();        // FIXME: Probably don't have to print here for this game
        }
        System.out.println();
    }

    private int[] evaluateDice(){
        int mostMatches = 0;
        int dieNumber = 7;

        for(Map.Entry<Integer, ArrayList<Die>> entry : evaluationTable.entrySet()){
            Integer key = entry.getKey();
            ArrayList<Die> values = entry.getValue();

            if ( values.size() > mostMatches ) {   //i.e. no repeats of saved values.

                // I begin to experiment with catch statements, so bare with me...
                try{
                    if( values.get(0).getValue() != savedDice.get(0)[0] ){      // Checks to see if current evaluated set has not already been saved on the first roll.
                        if( values.get(0).getValue() != savedDice.get(1)[0]){   // Does the same thing, but checks the second roll (if there was one)
                            mostMatches = values.size();                        // If both are true, saves the new values
                            dieNumber = values.get(0).getValue();
                        }
                    }
                } catch (Exception NullPointerException){   // Catches NullPointerException because if game is only on roll one or two,
                                                            //the previous conditions will point to a null in the array.
                    mostMatches = values.size();
                    dieNumber = values.get(0).getValue();   // Returns the die value of the selected dice
                }
            }
        }
        dieNumber = (dieNumber == 0) ? 7 : dieNumber;       // This is a cheaters way of handling a case of no possible move. Since my print loop
                                                //only iterates up to 6, the 7th index will never be printed out and therefore
                                                //is where I have decided to store the neusence.
        removeSavedDice(dieNumber);
        int[] a = new int[] {dieNumber, mostMatches};
        return a;
    }

    // Removes dice that have been saved from the remainingDice list
    private void removeSavedDice(int value){
        for(int i = 0; i < remainingDice.size(); i++){
            if(remainingDice.get(i).getValue() == value){   // Checks through each of the dice to see if they have been saved and need to be removed.
                remainingDice.remove(i);
                i--;    // i-- is to counter the fact that I am removing elements from the list as I traverse it.
            }
        }
    }

    public void printSavedDice(){
        // I initialize the ArrayList with 7 elements because the last one is exception storage for a '0'dice case
        ArrayList<Integer> dice = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0));

        System.out.print("Saved dice: ");

        for( int i =0; i < savedDice.size()-1; i++){        // size() - 1 because I don't want the last element
            System.out.print(savedDice.get(i)[0] + "  ");
            dice.set(savedDice.get(i)[0] - 1, (savedDice.get(i)[0] * savedDice.get(i)[1]));
        }
        System.out.print("\nSum of dice: ");
        for( int i = 0; i < dice.size(); i++){
            System.out.print(dice.get(i) + "  ");
        }
        System.out.println();
    }

}
