package aufgabe6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DimacsReader {

	public List<Variable> varList;

	protected List<Clause> term;
	
	public DimacsReader() {

	}
	
	public List<Clause> getTerm() {
		return term;
	}
	
	public boolean isComment(String line) {
		return line.startsWith("c");
	}

	public boolean isHeadline(String line) {
		return line.startsWith("p");
	}

	public int getNumberOfVariables(String line) {
		return Integer.parseInt(line.split(" ")[2]);
	}

	public int getNumberOfClauses(String line) {
		return Integer.parseInt(line.split(" ")[3]);
	}
	
	public void buildVarList(int numberOfVars){
		varList = new ArrayList<Variable>();
		varList.add(null);
		for(int i = 1; i <= numberOfVars; i++) {
			varList.add(new Variable());
		}
		System.out.println(varList.size());
	}
	
	public void readFile(File file) {
		term = new ArrayList<Clause>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (isHeadline(line)) {
					buildVarList(getNumberOfClauses(line));
				} else if(!isComment(line)) {
					Clause clause = new Clause();
					String[] lineSplit = line.split(" ");
					for (int i = 0; i < lineSplit.length; i++) {
						if (!lineSplit[i].equals("0")) {
							int index = Integer.parseInt(lineSplit[i]);
							boolean isPositive = true;
							if(index < 0) {
								isPositive = false;
							}
							clause.add(new ClauseVar(isPositive, varList.get(Math.abs(index))));
						}
					}
					term.add(clause);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void algorithmA() {
		for (int i = 1; i < varList.size(); i++) {
			if (Math.random() < 0.5) {
				varList.get(i).setValue(1);
			} else {
				varList.get(i).setValue(0);
			}
		}
	}
	
	public double getExpectationValue(List<Clause> term) {
		double result = 0;
		for(Clause clause : term) {
			if(clause.isTrue()) {
				result = result + 1;
				System.out.println(1);
			} else {
				result = result + clause.getExpectationValue();
				System.out.println(clause.getExpectationValue());
			}
		}
		return result;
	}
	
	public void derandA(List<Clause> term){
		double w0;
		double w1;
		for(int i = 1; i < varList.size(); i++) {
			varList.get(i).setValue(0);
			w0 = getExpectationValue(term);
			varList.get(i).setValue(1);
			w1 = getExpectationValue(term);
			System.out.println("w0 " + w0 + " | w1 "+ w1 );
			if(w0 <= w1) {
				varList.get(i).setValue(1);
			} else {
				varList.get(i).setValue(0);
			}
		}
	}
	
	public static void main(String[] args) {
		File file = new File("target"+File.separator+"aim-50-1_6-no-3.cnf");
		DimacsReader reader = new DimacsReader();
		reader.readFile(file);
		for(Clause clause : reader.getTerm()) {
			System.out.println(clause);
		}
		
		System.out.println("--------------------------------------");

//		reader.algorithmA();
//		
//		System.out.println("--------------------------------------");
//		for(Clause clause : reader.getTerm()) {
//			System.out.println(clause);
//		}
		
		
		SATSolver solver = new SATSolver();
		System.out.println(solver.isTermTrue(reader.getTerm()));
		
//		System.out.println("--------------------------------------");
//		System.out.println(reader.getExpectationValue(reader.getTerm()));
		System.out.println("--- Start derandA -------");
		reader.derandA(reader.getTerm());
		System.out.println(solver.isTermTrue(reader.getTerm()));
		
		for(Clause clause : reader.getTerm()) {
			System.out.println(clause);
		}
	}
}
