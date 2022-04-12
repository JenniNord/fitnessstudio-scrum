package de.uni_ko.fitnessstudio.util;

public class GAConfiguration {
	private int maxEvaluations;
	private int populationSize;
	private boolean printInfoAfterIteration;

	public GAConfiguration(int maxEvaluations, int populationSize) {
		super();
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;
		this.printInfoAfterIteration = false;
	}

	public GAConfiguration(int maxEvaluations, int populationSize, boolean printInfoAfterIteration) {
		super();
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;
		this.printInfoAfterIteration = printInfoAfterIteration;
	}

	public int getMaxEvaluations() {
		return maxEvaluations;
	}

	public void setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public boolean getPrintInfoAfterIteration() {
		return printInfoAfterIteration;
	}

	public void setPrintInfoAfterIteration(boolean printInfoAfterIteration) {
		this.printInfoAfterIteration = printInfoAfterIteration;
	}
}
