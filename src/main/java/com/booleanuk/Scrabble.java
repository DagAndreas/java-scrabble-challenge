package com.booleanuk;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.HashMap;

public class Scrabble {
    public String word;


    public Scrabble(String word) {
        this.word = word;
    }

//    public int score() {
//        int score = 0;
//
//        for(int i = 0; i < word.length(); i++){
//            char c = word.charAt(i);
//            String s = String.valueOf(c);
//            System.out.println(s.toUpperCase());
//            System.out.println(getValue(s));
//            score = score + getValue(s);
//        }
//
//        return score;
//    }


    public int score() {
        ArrayList<String> convertedList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            String s = String.valueOf(c);


            int score = getValue(s);
            if (score != 0){
                convertedList.add(String.valueOf(score));
            }
            else {
                convertedList.add(s);
            }
        }

        return calculateScoreRecursion(convertedList, 1, 0);
    }

    public int calculateScoreRecursion(ArrayList<String> word, int multiplier, int sum){
        if (word.size() == 0){
            return sum;
        }
        String firstChar = word.removeFirst();
        switch (firstChar){
            case "{":
                return calculateScoreRecursion(word, multiplier*2, sum);
            case "}":
                return calculateScoreRecursion(word, multiplier/2, sum);
            case "[":
                return calculateScoreRecursion(word, multiplier * 3, sum);
            case "]":
                return calculateScoreRecursion(word, multiplier/3, sum);
            default:
                int value = Integer.valueOf(firstChar);
                value = value * multiplier;
                int newSum = sum + value;
                return calculateScoreRecursion(word, multiplier, newSum);
        }
    }



    public int getValue(String c){
        int letterValue;
        System.out.println(c);
        switch (c.toUpperCase()){
            case "A", "E", "I", "O", "U", "L", "N", "R", "S", "T"-> letterValue = 1;
            case "D", "G" -> letterValue = 2;
            case "B", "C", "M", "P" -> letterValue = 3;
            case "F", "H", "V", "W", "Y" -> letterValue = 4;
            case "K" -> letterValue = 5;
            case "J", "X" -> letterValue = 8;
            case "Q", "Z" -> letterValue = 10;
            default -> letterValue = 0;
        }
        return letterValue;
    }
}
