package de.uni_ko.fitnessstudio.nsga;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;

public class InitNSGAIIBuilder<S extends Solution<?>> extends NSGAIIBuilder<S> {
	private Init<S> initialization;
	
	public InitNSGAIIBuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
			int populationSize, Init<S> initialization) {
		super(problem, crossoverOperator, mutationOperator, populationSize);
		
		this.initialization = initialization;
	}
	
	@Override
	public NSGAII<S> build() {
	    NSGAII<S> algorithm = new InitNSGAII<S>(
	    		getProblem(), getMaxIterations(), getPopulationSize(), matingPoolSize, offspringPopulationSize, getCrossoverOperator(), 
	    		getMutationOperator(), getSelectionOperator(), new DominanceComparator<>(), super.getSolutionListEvaluator(), initialization
			);

	    return algorithm ;
	}

}
