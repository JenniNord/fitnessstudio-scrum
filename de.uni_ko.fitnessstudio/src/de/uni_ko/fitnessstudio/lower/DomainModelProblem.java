package de.uni_ko.fitnessstudio.lower;

import org.uma.jmetal.problem.Problem;

@SuppressWarnings("serial")
public abstract class DomainModelProblem<S> implements Problem<DomainModelSolution<S>> {
	private int numberOfVariables;
	private int numberOfObjectives;
	private int numberOfConstraints;
	
	public final String INPUT_MODEL_ID;

	public DomainModelProblem(int variables, int objectives, int constraints, String inputModelId) {
		this.numberOfVariables = variables;
		this.numberOfObjectives = objectives;
		this.numberOfConstraints = constraints;
		
		this.INPUT_MODEL_ID = inputModelId;
	}
	
	@Override
	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

	@Override
	public int getNumberOfConstraints() {
		return numberOfConstraints;
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
