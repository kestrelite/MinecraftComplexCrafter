package mccomplexcrafter;

import java.util.ArrayList;

public class Recipe {
    public ArrayList<String> items = new ArrayList<>();
    public ArrayList<Integer> quantity = new ArrayList<>();
    
    public boolean prepCraft = false;
    public boolean hasRecipe = false;
    public boolean basic = false;

    public float qtyOut = 1;
    public String machine = "";
    
    public void addItemRequirement(String item, int quantity) {
        hasRecipe = true;
        if(items.contains(item)) {
            int ind = items.indexOf(item);
            items.remove(ind);
            this.quantity.remove(ind);
        }
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
