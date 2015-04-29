package itemtree;

import java.util.ArrayList;
import java.util.HashMap;
import mccomplexcrafter.MCComplexCrafter;
import mccomplexcrafter.Recipe;

public class ItemNode {
    private static final HashMap<String, Recipe> itemList = MCComplexCrafter.itemList;
    
    public static HashMap<String, Float> bottomAgg = new HashMap<>();
    public static HashMap<String, Float> fullAgg = new HashMap<>();
    public static float aggQuantity = 1;
    
    public ArrayList<ItemNode> subNodes = new ArrayList<>();
    public String name;
    public Recipe recipe;

    public static void clearStatics() {
        bottomAgg = new HashMap<>();
        fullAgg = new HashMap<>();
        aggQuantity = 1;
    }
    
    public ItemNode(String item) {
        if(!itemList.containsKey(item)) throw new Error("A node was created for a nonexistent item: " + item);
        name = item;
        recipe = itemList.get(item);
        
        float currFullQ = (fullAgg.containsKey(name) ? fullAgg.get(name) : 0);
        fullAgg.put(name, aggQuantity + currFullQ);
        if(!recipe.hasRecipe) {
            float currQ = (bottomAgg.containsKey(name) ? bottomAgg.get(name) : 0);
            bottomAgg.put(name, aggQuantity + currQ);
            return;
        } 
        
        for(String i : recipe.items) {            
            if(!itemList.containsKey(i)) itemList.put(i, new Recipe());
            //System.out.println(name + " requires " + i);
            float aggScalar = ((float)recipe.quantity.get(recipe.items.indexOf(i))) / recipe.qtyOut;
            aggQuantity *= aggScalar;
            subNodes.add(new ItemNode(i));
            aggQuantity /= aggScalar;
        }
    }

    public void printFullAgg() {
        printFullAgg(0);
    }
    
    private void printFullAgg(int tabs) {
        if(!recipe.hasRecipe) return;
        for(int i = 0; i < recipe.items.size(); i++) {
            for(int j = 0; j < tabs; j++) System.out.print("|\t");
            float qtyNeeded = aggQuantity * ((float)recipe.quantity.get(i))/recipe.qtyOut;
            System.out.println(Math.ceil(qtyNeeded) + " of " + recipe.items.get(i));
            aggQuantity *= recipe.quantity.get(i);
            subNodes.get(i).printFullAgg(tabs+1);
            aggQuantity /= recipe.quantity.get(i);
        }
    }
    
    public void printFull() {
        printFull(0);
    }
    
    private void printFull(int tabs) {
        if(!recipe.hasRecipe) return;
        for(int i = 0; i < recipe.items.size(); i++) {
            for(int j = 0; j < tabs; j++) System.out.print("|\t");
            float qtyNeeded = ((float)recipe.quantity.get(i))/recipe.qtyOut;
            System.out.println(Math.ceil(qtyNeeded) + " of " + recipe.items.get(i) +
                    (recipe.machine != "" ? " ("+recipe.machine+")" : ""));
            subNodes.get(i).printFull(tabs+1);
        }
    }
    
    public ArrayList<String> getFilterBase() {
        ArrayList<String> agg = new ArrayList<>();
        if(!recipe.hasRecipe) {
            agg.add(name);
            return agg;
        }
        
        for(ItemNode item : subNodes) {
            if(item.recipe.basic) continue;
            ArrayList<String> subAgg = item.getBase();
            for(String s : subAgg)
                if(!agg.contains(s)) agg.add(s);
        }
        
        return agg;        
    }
    
    public ArrayList<String> getBase() {
        ArrayList<String> agg = new ArrayList<>();
        if(!recipe.hasRecipe) {
            agg.add(name);
            return agg;
        }
        
        for(ItemNode item : subNodes) {
            ArrayList<String> subAgg = item.getBase();
            for(String s : subAgg)
                if(!agg.contains(s)) agg.add(s);
        }
        
        return agg;
    }
    
    public ArrayList<String> getMachinesUsed() {
        ArrayList<String> ret = new ArrayList<>(); 
        if(!ret.contains(recipe.machine) && !recipe.machine.equals("")) ret.add(recipe.machine);
        
        for(ItemNode item : subNodes) {
            ArrayList<String> subAgg = item.getMachinesUsed();
            for(String s : subAgg)
                if(!ret.contains(s)) ret.add(s);
        }
        
        return ret;
    }
    
    public ArrayList<ItemNode> stripBottom() {
        ArrayList<ItemNode> ret = new ArrayList<>();
        
        if(subNodes.isEmpty()) {ret.add(this); return ret;}
        
        ArrayList<ItemNode> newSubNodes = new ArrayList<>();
        
        for(ItemNode node : subNodes) {
            ArrayList<ItemNode> obj = node.stripBottom();
            for(ItemNode n : obj) if(!ret.contains(n)) ret.add(n);
            if(!obj.contains(node)) newSubNodes.add(node);
        }
        
        subNodes = newSubNodes;
        return ret;
    }
}
