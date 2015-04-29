package mccomplexcrafter;

import java.util.ArrayList;

public class Recipe {
    public ArrayList<String> items = new ArrayList<>();
    public ArrayList<Integer> quantity = new ArrayList<>();
    
    public boolean slowCraft = false;
    public boolean hasRecipe = false;
    
    public void addItemRequirement(String item, int quantity) {
        hasRecipe = true;
        items.add(item);
        this.quantity.add(quantity);
    }
}
