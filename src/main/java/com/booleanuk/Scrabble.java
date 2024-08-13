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
        System.out.println("conv list = " +convertedList);

        word = word.toUpperCase();
        // check for legal characters. All legal characters are:
        String legalCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ{}[]";
        for (int i = 0; i < word.length(); i++) {
            String c = String.valueOf(word.charAt(i));
            if (!legalCharacters.contains(c)){
                System.out.println("Found illegal character" + c );
                return 0;
            }
        }




        // check for paired brackets
        boolean invalidMultipliers = containsValidMultipliers(word);
        System.out.println("Invalid multipliers:" + invalidMultipliers);
        if (invalidMultipliers) return 0;

        return calculateScoreRecursion(convertedList, 1, 0);
    }


    public boolean CheckFrontbackPair(String word){
        char first_c = word.charAt(0);
        if (first_c == '{'){
            char third_char = word.charAt(2);
            if (third_char == '}'){
                return false;
            }
            char last_char = word.charAt(word.length()-1);
            char last_char_pair = word.charAt(word.length()-3);

            if (last_char == '}' && last_char_pair != '{'){
                return true;
            }
            return false;
        }

     return false;
    }

    public boolean containsValidMultipliers(String word) {
        try{

            boolean is_paired_front_back = false;
            System.out.println("Hei");
            char first_c = word.charAt(0);


            if (first_c == '{' || first_c == '[') {
                //should be third or last.
                char third_c = word.charAt(2);


                char last_c = word.charAt(word.length() - 1);

                if (third_c == last_c && first_c == last_c) {
                    System.out.println("third == last");
                    return true;
                }

                if (first_c != third_c){
                    if (first_c != last_c){
                        System.out.println("Neither first or last is " + first_c);
                        return true;
                    }
                }

                if (first_c == last_c){
                    is_paired_front_back = true;
                }
            }




            else if (first_c == '}' || first_c == ']') return true;
            int iterlength = word.length()-1;
            if(is_paired_front_back) iterlength--;
            for (int i = 1; i < iterlength; i++){
                char c = word.charAt(i);
                System.out.println("Checking " + c);
                if (c == '{'){
                    System.out.println("Found {");
                    char next_semibrack = word.charAt(i+2);
                    System.out.println("Next char " + next_semibrack);
                    if (next_semibrack != '}'){
                        System.out.println("Didn't find pairing }");
                        return true;
                    }
                }
                else if (c == '[') {
                    System.out.println("Found [");
                    char next_brack = word.charAt(i + 2);
                    if (next_brack != ']') {
                        System.out.println("Didn't find pairing ]");
                        return true;
                    }
                }
                else if (c == '}'){
                    System.out.println("Found }");
                    char prev_semibrack = word.charAt(i-2);
                    if (prev_semibrack != '{'){
                        return true;
                    }
                }
                else if (c == ']'){
                        System.out.println("Found ]");
                    char prev_brack = word.charAt(i-2);
                    if(prev_brack != '['){
                        return true;
                    }
                }



            }

            return false;

        } catch (Exception e){
            System.out.println(e);
            return true;
        }
    }

    public int calculateScoreRecursion(ArrayList<String> word, int multiplier, int sum){
        if (word.isEmpty()){
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
                int value = Integer.parseInt(firstChar);
                value = value * multiplier;
                int newSum = sum + value;
                System.out.println("returning default " + newSum);
                return calculateScoreRecursion(word, multiplier, newSum);
        }
    }



    public int getValue(String c){
        int letterValue;
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
        System.out.println(c + ": " + letterValue);
        return letterValue;
    }
}
