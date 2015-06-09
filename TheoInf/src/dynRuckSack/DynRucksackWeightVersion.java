package dynRuckSack;

import java.util.ArrayList;

public class DynRucksackWeightVersion {

	static ArrayList<Item> itemList = getItemList();

	public static ArrayList<Item> getItemList() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		Item i0 = new Item("Nothing", 0 ,0);
		Item i1 = new Item("Essen", 23, 15);
		Item i2 = new Item("Zelt", 33, 23);
		Item i3 = new Item("Getreanke", 11, 15);
		Item i4 = new Item("Pullover", 35, 33);
		Item i5 = new Item("MP3-Player", 11, 32);

		itemList.add(i0);
		itemList.add(i1);
		itemList.add(i2);
		itemList.add(i3);
		itemList.add(i4);
		itemList.add(i5);
		
		return itemList;
	}
	
	public static void multiplyWeight(ArrayList<Item> itemList, int factor) {
		for(Item item : itemList) {
			item.setWeight(item.getWeight()*factor);
		}
	}
	
	public static ArrayList<Item> getItemList2() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		Item i0 = new Item("Nothing", 0 ,0);
		Item i1 = new Item("1", 5, 2);
		Item i2 = new Item("2", 3, 4);
		Item i3 = new Item("3", 1, 1);
		Item i4 = new Item("4", 2, 3);
		Item i5 = new Item("5", 1, 2);
		Item i6 = new Item("6", 7, 3);
		Item i7 = new Item("7", 4, 7);
		Item i8 = new Item("8", 3, 3);
		
		itemList.add(i0);
		itemList.add(i1);
		itemList.add(i2);
		itemList.add(i3);
		itemList.add(i4);
		itemList.add(i5);
		itemList.add(i6);
		itemList.add(i7);
		itemList.add(i8);
		
		return itemList;
	}
	
	public static int getValue(int i) {
		return itemList.get(i).getValue();
	}
	
	public static int getWeight(int i) {
		return itemList.get(i).getWeight();
	}
	
	public static int opt(int i, int w) {
		if(w <= 0) return 0;
		if(i <= 0) return 0;
		if(w < getWeight(i)) {
			return opt(i-1, w);
		} else {
			return Math.max(opt(i-1, w), getValue(i) + opt(i-1, w-getWeight(i)));
		}
	}
	// n = number of items
	// W = rucksack total weight
	public static int rucksack(int n, int W) {
		for(int i = 1; i < n; i++) {
			for(int j = 1; j < W; j++) {
				opt(i, j);
			}
		}
		return opt(n, W);
	}
	
	public static void main(String[] args) {
		int rucksackCapacity = 65;
		
		System.out.println(rucksack(itemList.size() -1, rucksackCapacity));
	}
}
