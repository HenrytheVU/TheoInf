package aufgabe1;

import java.util.Random;

public class Util {
	static final Random rand = new Random();
	
	public static String[] getEdge(String line) {
		String[] lineSplit = line.split(" ");
		String fromVertex = lineSplit[1];
		String toVertex = lineSplit[2];
		String weight = null;
		if(lineSplit.length == 4) {
			weight = lineSplit[3];
		}
		String[] result = {fromVertex, toVertex, weight};
		return result;
	}
	
	public static String getVertexName(String line) {
		return line.split(" ")[1];
	}

	public static int randInt(int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
