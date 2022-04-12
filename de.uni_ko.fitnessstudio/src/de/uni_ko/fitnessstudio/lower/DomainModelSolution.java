package de.uni_ko.fitnessstudio.lower;

import org.uma.jmetal.solution.AbstractSolution;
import org.uma.jmetal.solution.Solution;

@SuppressWarnings("serial")
public abstract class DomainModelSolution<S> extends AbstractSolution<S> implements Solution<S> {

	protected DomainModelSolution(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints);
		// TODO Auto-generated constructor stub
	}
}
