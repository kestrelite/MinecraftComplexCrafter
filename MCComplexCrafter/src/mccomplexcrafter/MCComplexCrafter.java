package mccomplexcrafter;

import java.util.HashMap;
import java.util.Scanner;

public class MCComplexCrafter {
    public static HashMap<String, Recipe> itemList = new HashMap<>();
    public static Scanner s = new Scanner(System.in);
    
    public static String[] getNextInput() {
        System.out.print("> ");
        return s.nextLine().split(" ");
    }
    
    public static void main(String[] args) {
        System.out.println("MCComplexCrafter console!");
        
        boolean exit = false;
        
        while(exit == false) {
            String[] input = getNextInput();
            switch(input[0]) {
                case "exit": case "e":
                    exit = true;
                    break;
                case "additem": case "a":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(itemList.containsKey(input[1])) {System.out.println("Item already exists."); break;}
                    if(input.length > 2) {System.out.println("Too many arguments - do not include spaces in item IDs."); break;}
                    itemList.put(input[1], new Recipe());
                    System.out.println("Item \"" + input[1] + "\" has been added.");
                    break;
                case "hasitem": case "h":
                    if(itemList.containsKey(input[1])) System.out.println("Item exists.");
                    else System.out.println("Item does not exist.");
                    break;
                case "delitem": case "d":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(!itemList.containsKey(input[1])) {System.out.println("Item does not exist."); break;}
                    itemList.remove(input[1]);
                    System.out.println("Item \"" + input[1] + "\" removed.");
                    break;
                case "listitems": case "l":
                    System.out.println(itemList.keySet().toString());
                    break;
                default:
                    System.out.println("Command not recognized: " + input[0]);
            }
        }
    }
}
