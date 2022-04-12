package de.uni_ko.fitnessstudio.nsga;

import java.util.List;

import org.uma.jmetal.problem.Problem;

public abstract class Init<S> {
	
	public abstract List<S> createInitialPopulation(int size, Problem<S> problem);
}
