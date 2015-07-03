package aufgabe6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.DeepCopy;

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

	public void buildVarList(int numberOfVars) {
		varList = new ArrayList<Variable>();
		varList.add(null);
		for (int i = 1; i <= numberOfVars; i++) {
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
					buildVarList(getNumberOfVariables(line));
				} else if (!isComment(line)) {
					Clause clause = new Clause();
					String[] lineSplit = line.split(" ");
					for (int i = 0; i < lineSplit.length; i++) {
						if (!lineSplit[i].equals("0")) {
							int index = Integer.parseInt(lineSplit[i]);
							boolean isPositive = true;
							if (index < 0) {
								isPositive = false;
							}
							clause.add(new ClauseVar(isPositive, varList
									.get(Math.abs(index))));
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

	public double getExpectationValue(List<Clause> term, Variable var) {
		double result = 0;
		for (Clause clause : term) {
			if (clause.isTrue()) {
				result = result + 1;
			} else {
				boolean isRemoved = clause.removeVar(var);
				result = result + clause.getExpectationValue();
				// System.out.println(clause.getExpectationValue());
				if (isRemoved)
					clause.undoRemove();
			}
		}
		return result;
	}

	public void removeVarFromClause(List<Clause> term, Variable var) {
		for (Clause clause : term) {
			if (!clause.isTrue()) {
				if (clause.removeVar(var)) {
//					System.out.println("removing var from clause");
				}
			}
		}
	}

	public List<Clause> cloneList(List<Clause> term) {
		List<Clause> clone = new ArrayList<Clause>();
		for (Clause c : term) {
			clone.add(new Clause(c));
		}
		return clone;
	}

	public void derandA(List<Clause> term) {
		double w0;
		double w1;
		for (int i = 1; i < varList.size(); i++) {
			Variable var = varList.get(i);

			varList.get(i).setValue(0);
			w0 = getExpectationValue(term, var);
			varList.get(i).setValue(1);
			w1 = getExpectationValue(term, var);
			System.out.println("w0 " + w0 + " | w1 " + w1);
			if (w0 <= w1) {
				varList.get(i).setValue(1);
				removeVarFromClause(term, var);
			} else {
				varList.get(i).setValue(0);
				removeVarFromClause(term, var);
			}
		}
	}

	public void derandAndRand(List<Clause> term) {
		
		List<Clause> termCopy = (List<Clause>) DeepCopy.copy(term);
		
		double w0;
		double w1;
		for (int i = 1; i < varList.size(); i++) {
			Variable var = varList.get(i);

			varList.get(i).setValue(0);
			w0 = getExpectationValue(termCopy, var);
			varList.get(i).setValue(1);
			w1 = getExpectationValue(termCopy, var);
//			System.out.println("w0 " + w0 + " | w1 " + w1);
			if (w0 < w1) {
				varList.get(i).setValue(1);
				removeVarFromClause(termCopy, var);
			} else {
				varList.get(i).setValue(0);
				removeVarFromClause(termCopy, var);
			}
			if (w0 == w1) {
				if (Math.random() < 0.5) {
					varList.get(i).setValue(1);
					removeVarFromClause(termCopy, var);
				} else {
					varList.get(i).setValue(0);
					removeVarFromClause(termCopy, var);
				}
			}
		}
	}

	public static void printNewMax(int oldMax, int newMax) {
		if (newMax > oldMax) {
			oldMax = newMax;
			System.out.println(oldMax);
		}
	}

	public static void main(String[] args) {
		File file = new File("target" + File.separator
				+ "aim-50-1_6-yes1-1.cnf");
		DimacsReader reader = new DimacsReader();
		reader.readFile(file);
		SATSolver solver = new SATSolver();

		System.out.println("--------------------------------------");

		int counter = 0;
//		System.out.println("--------------------------------------");
//		System.out.println("--- Start random algorithm A ---");
//		for (int i = 0; i < 1000000000; i++) {
//			reader.algorithmA();
//			if (solver.isTermTrue(reader.getTerm())) {
//				System.out.println("true!");
//			}
//			solver.getMax(reader.getTerm());
//			int max = solver.getMaxSastified();
//			if (max > counter) {
//				counter = max;
//				System.out.println("Run: " + i + " : " + max);
//			}
//		}

		System.out.println("--------------------------------------");
		System.out.println("--- Start derandAndRand -------");
		int run = 0;
		while (!solver.isTermTrue(reader.getTerm())) {
			run++;
			reader.derandAndRand(reader.getTerm());
			solver.getMax(reader.getTerm());
			int max = solver.getMaxSastified();
			if (max > counter) {
				counter = max;
				System.out.println("Run: " + run + " : " + max);
			}
		}

		System.out.println("--------------------------------------");
		System.out.println("The Term is " + solver.isTermTrue(reader.getTerm()));

		// System.out.println("----- VariablesList :");
		// for (Variable var : reader.varList) {
		// System.out.println(var);
		// }
	}
}
