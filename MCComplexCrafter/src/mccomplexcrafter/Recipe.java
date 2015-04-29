package mccomplexcrafter;

import java.util.ArrayList;

public class Recipe {
    public ArrayList<String> items = new ArrayList<>();
    public ArrayList<Integer> quantity = new ArrayList<>();
    
    public boolean prepCraft = false;
    public boolean hasRecipe = false;
    
    public void addItemRequirement(String item, int quantity) {
        hasRecipe = true;
        items.add(item);
        this.quantity.add(quantity);
    }
    
    @Override
    public String toString() {
        String ret = "";
        for(int i = 0; i < items.size(); i++)
            ret += items.get(i) + ":" + quantity.get(i) + ", ";
        return ret.substring(0, ret.length()-2);
    }
}
