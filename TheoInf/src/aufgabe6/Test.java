package aufgabe6;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		boolean[] vars = { false, false, false };

		boolean[] list = { vars[0], vars[1], vars[2] };

		for (boolean b : list) {
			System.out.println(b);
		}

		System.out.println("-------------------");
		vars[1] = true;

		for (boolean b : list) {
			System.out.println(b);
		}
		System.out.println("-------------------");
		for (boolean b : vars) {
			System.out.println(b);
		}

//		List<ClauseVar> varList = new ArrayList<ClauseVar>();
//		ClauseVar v1 = new ClauseVar(false, false);
//		ClauseVar v2 = new ClauseVar(false, false);
//
//		varList.add(v1);
//		varList.add(v2);
//
//		List<ClauseVar> varList2 = new ArrayList<ClauseVar>();
//		varList2.add(varList.get(0));
//		varList2.add(varList.get(1));
//
//		v1.setIsPositive(true);
//		v2.setValue(true);
//		;
//
//		System.out.println("-------------------");
//		for (Variable v : varList) {
//			System.out.println(v);
//		}
//
//		System.out.println("-------------------");
//		for (Variable v : varList2) {
//			System.out.println(v);
//		}
		System.out.println("-------------------");
		System.out.println((false || true));

	}
}
