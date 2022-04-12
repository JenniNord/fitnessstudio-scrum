package de.uni_ko.fitnessstudio.nsga;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIMeasures;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.solution.Solution;

@SuppressWarnings("serial")
public class InitNSGAII<S extends Solution<?>> extends NSGAIIMeasures<S> {
	
	private Init<S> initialization;
	
	/** Constructor */
	public InitNSGAII(Problem<S> problem, int maxIterations, int populationSize, int matingPoolSize,
			int offspringPopulationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
			SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator,
			SolutionListEvaluator<S> evaluator, Init<S> initialization) {
		super(problem, maxIterations, populationSize, matingPoolSize, offspringPopulationSize, crossoverOperator,
				mutationOperator, selectionOperator, dominanceComparator, evaluator);
		
		this.initialization = initialization;
	}

	@Override
	protected List<S> createInitialPopulation() {
		return initialization.createInitialPopulation(maxPopulationSize, problem);
	}

}
