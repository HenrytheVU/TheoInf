package dynRuckSack;

import java.util.ArrayList;

public class DynRucksackBook {

	static ArrayList<Item> itemList = getItemList();
	static int alpha = 0;
	static int rucksackCapacity = 65;
	static int numberOfItems = itemList.size();

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

	public static int getTotalValue() {
		int result = 0;
		for (Item item : getItemList()) {
			result = +item.getValue();
		}
		;
		return result;
	}

	public static int getValue(int i) {
		return itemList.get(i).getValue();
	}

	public static int getWeight(int i) {
		return itemList.get(i).getWeight();
	}
	
	public static int opt(int j, int a) {
		if(a <= 0) return 0;
		if((a >= 1) &&(j <= 0)) return Integer.MAX_VALUE;
//		if(a > getValue(j)) {
//			return opt(j-1, a); 
//			}
		return Math.min(opt(j - 1 ,a - getValue(j)) + getWeight(j), opt(j - 1, a));
	}
	
	
	// n = number of items
	// w = total rucksack weight
	public static int rucksack(int n, int W) {
		int a = 0;
		do {
			System.out.println("a : " + a);
			a++;
			for(int j = 1; j < n; j++) {
				opt(j, a);
				System.out.println("opt(j, a) ("+j+", "+ a +") = " + opt(n, a));
			}
			System.out.println("opt(n, a) ("+n+", "+ a +") = " + opt(n, a));
		} while (W > opt(n, a));
		System.out.println("1opt(n, a) ("+n+", "+ a +") = " + opt(n, a));
		return a - 1;
	}
	
	public static void main(String[] args) {
		int rucksackCapacity = 65;
		System.out.println("result : " + rucksack(itemList.size() - 1, rucksackCapacity));
	}
}
