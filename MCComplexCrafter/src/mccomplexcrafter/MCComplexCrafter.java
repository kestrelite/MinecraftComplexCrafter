package mccomplexcrafter;

import java.util.HashMap;
import java.util.Scanner;

public class MCComplexCrafter {
    public static HashMap<String, Recipe> itemList = new HashMap<>();
    public static Scanner s = new Scanner(System.in);
    
    public static String[] getNextInput(String prefix) {
        System.out.print(prefix + "> ");
        return s.nextLine().split(" ");
    }
    
    public static void main(String[] args) {
        System.out.println("MCComplexCrafter console!");
        
        boolean exit = false;
        
        while(exit == false) {
            String[] input = getNextInput("Main");
            switch(input[0]) {
                case "exit": case "q":
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
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
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
                case "resetrecipe": case "r":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(!itemList.containsKey(input[1])) {System.out.println("Item does not exist."); break;}
                    itemList.put(input[1], new Recipe());
                    System.out.println("Item \"" + input[1] + "\" reset.");
                    break;
                case "editrecipe": case "e":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(!itemList.containsKey(input[1])) {System.out.println("Item does not exist."); break;}
                    System.out.println("Editing recipe for item \"" + input[1] + "\"...");
                    Recipe r = recpieEditor(itemList.get(input[1]));
                    itemList.put(input[1], r);
                    System.out.println("Recipe for \"" + input[1] + "\" has been updated.");
                    break;
                case "showrecipe": case "s":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(!itemList.containsKey(input[1])) {System.out.println("Item does not exist."); break;}
                    if(!itemList.get(input[1]).hasRecipe) {System.out.println("This item has no recipe."); break;}
                    System.out.println(itemList.get(input[1]));
                    break;
                default:
                    System.out.println("Command not recognized: " + input[0]);
            }
        }
    }

    private static Recipe recpieEditor(Recipe r) {
        boolean exit = false;
        while(exit == false) {
            String[] input = getNextInput("Recipe");
            switch(input[0]) {
                case "exit": case "q":
                    exit = true; break;
                case "editreq": case "e":
                case "addreq": case "a":
                    if(input.length < 3) {System.out.println("Not enough arguments."); break;}
                    if(input.length > 3) {System.out.println("Too many arguments."); break;}
                    if(!input[2].matches("\\d+")) {System.out.println("Quantity must be a number."); break;}
                    if(!itemList.containsKey(input[1])) {System.out.println("WARN: Item \"" + input[1] + "\" does not (yet) exist.");}
                    System.out.println("Requiring " + input[2] + " of \"" + input[1] + "\".");
                    r.addItemRequirement(input[1], Integer.parseInt(input[2]));
                    break;
                case "delreq": case "d":
                    if(input.length < 2) {System.out.println("Not enough arguments."); break;}
                    if(input.length > 2) {System.out.println("Too many arguments."); break;}
                    if(!r.items.contains(input[1])) {System.out.println("That item does not exist."); break;}
                    r.quantity.remove(r.items.indexOf(input[1]));
                    r.items.remove(input[1]);
                    System.out.println("Item requirement for \"" + input[1] + "\" deleted.");
                    break;
                case "listreqs": case "l":
                    if(!r.hasRecipe) {System.out.println("No recipe requirements exist."); break;}
                    System.out.println(r); break;
                case "toggleprep": case "p":
                    System.out.println("Crafting preparation set to " + !r.prepCraft);
                    r.prepCraft = !r.prepCraft;
                    break;
                default:
                    System.out.println("Command not recognized: " + input[0]);
            }
        }
        return r;
    }
}
