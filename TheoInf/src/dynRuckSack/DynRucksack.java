package dynRuckSack;

import java.util.ArrayList;
import java.util.Collections;

public class DynRucksack {
	
	public static void multiplyValueBy(ArrayList<Item> itemList, int factor) {
		for(Item item : itemList) {
			item.setValue(item.getValue() * factor);
		}
	}
	

    public static void main(String[] args) {
    	
    	int counter = 0;
    	
    	ArrayList<Item> itemList = new ArrayList<Item>();
    	Item i1 = new Item("Essen", 23, 15);
    	Item i2 = new Item("Zelt", 33, 23);
    	Item i3 = new Item("Getreanke", 11, 15);
    	Item i4 = new Item("Pullover", 35, 33);
    	Item i5 = new Item("MP3-Player", 11, 32);
    	
    	itemList.add(i1);
    	itemList.add(i2);
    	itemList.add(i3);
    	itemList.add(i4);
    	itemList.add(i5);
    	
//    	multiplyValueBy(itemList, 10000);

        int numberOfItems = itemList.size();   // number of items
        int maxWeight = 65;   // maximum weight of the rucksack

        int[] value = new int[numberOfItems+1];
        int[] weight = new int[numberOfItems+1];

        // generate random instance, items 1..N
//        for (int n = 1; n <= N; n++) {
//            value[n] = (int) (Math.random() * 1000);
//            weight[n] = (int) (Math.random() * maxWeight);
//        }
        
        for(int i = 0; i < itemList.size(); i++) {
        	Item item = itemList.get(i);
        	value[i+1] = item.getValue();
        	weight[i+1] = item.getWeight();
        }

        // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] opt = new int[numberOfItems+1][maxWeight+1];
        boolean[][] sol = new boolean[numberOfItems+1][maxWeight+1];

        for (int n = 1; n <= numberOfItems; n++) {
            for (int w = 1; w <= maxWeight; w++) {
            	counter++;
                // don't take item n
                int option1 = opt[n-1][w];

                // take item n
                int option2 = Integer.MIN_VALUE;
                if (weight[n] <= w) option2 = value[n] + opt[n-1][w-weight[n]];

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        // determine which items to take
        boolean[] take = new boolean[numberOfItems+1];
        for (int n = numberOfItems, w = maxWeight; n > 0; n--) {
            if (sol[n][w]) { take[n] = true;  w = w - weight[n]; }
            else           { take[n] = false;                    }
        }

        // print results
        System.out.println("[ITEM]" + "\t\t" + "[WEIGHT]" + "\t" + "[VALUE]" + "\t\t" + "[TAKE]");
        for (int n = 1; n <= numberOfItems; n++) {
        	Item item = itemList.get(n-1);
            System.out.println(item.getName().substring(0, 4) + "\t\t" + weight[n] + "\t\t" +value[n] + "\t\t" + take[n]);
        }
        System.out.println("Number of Steps: " + counter);
    }
}
