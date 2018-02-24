package com.company;
/* Created by InteliJ Idea

 *User: Nicholas Gaultney
 *Date: 2/24/2018
 *Time: 2:57 PM
 *Contact: nmgaultney@gmail.com
 */

public class Die {
    private int value;

    public void roll(){
        // Math.random returns a value between 0.0 and 0.9. For that reason, multiply by 6 to get decimal between
        //0 and 5. Add 1 to get 1 to 6
        value = (int)(Math.random() * 6 + 1);
    }

    public int getValue(){
        return value;
    }

    public void printValue(){
        System.out.println(value);
    }
}
