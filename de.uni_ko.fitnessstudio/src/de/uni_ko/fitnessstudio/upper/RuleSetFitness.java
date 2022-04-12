package de.uni_ko.fitnessstudio.upper;

import java.io.FileNotFoundException;

import org.eclipse.emf.ecore.EObject;
import org.uma.jmetal.util.JMetalException;

import com.lagodiuk.Fitness;

import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import de.uni_ko.fitnessstudio.lower.DomainModelMutation;
import de.uni_ko.fitnessstudio.lower.DomainModelProblem;
import de.uni_ko.fitnessstudio.lower.DomainModelSolution;
import de.uni_ko.fitnessstudio.lower.LowerNSGAIIManager;
import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.util.GAConfiguration;

/**
 * Fitness function, which calculates difference two chromosomes.
 */
public class RuleSetFitness<S> implements Fitness<RuleSet, Double> {

	// Map<RuleSet, Double> cache = new HashMap<RuleSet, Double>();
	EObject model;
	DomainModelProblem<S> domainModelProblem;
	Init<DomainModelSolution<S>> init;
	DomainModelCrossover<DomainModelSolution<S>> domainModelCrossover;
	
	GAConfiguration configurationLower;
	
	// TODO: Might let LowerNSGAIIManager use constraintChecker
	private ConstraintChecker constraintChecker;
	
	public RuleSetFitness(EObject model, DomainModelProblem<S> problem, Init<DomainModelSolution<S>> init, DomainModelCrossover<DomainModelSolution<S>> crossover, 
			GAConfiguration configurationLower, ConstraintChecker constraintChecker) {
		this.model = model;
		this.domainModelProblem = problem;
		this.init = init;
		this.domainModelCrossover = crossover;
		this.configurationLower = configurationLower;
		this.constraintChecker = constraintChecker;
	}

	@Override
	public Double calculate(RuleSet chromosome) {
		if (chromosome.getConstraintChecker().satisfiesMutationConstraints(chromosome.getGenRules())) {
			DomainModelMutation<S> domainModelMutation = new DomainModelMutation<S>(chromosome.getGenRules(), 0.6);
			return getResultWithTimeout(domainModelMutation);
		} else {
			return -10000.0;
		}
	}

	private Double getResultWithTimeout(DomainModelMutation<S> domainModelMutation) {
		LowerNSGAIIManager<S> manager = new LowerNSGAIIManager<S>(domainModelProblem, init, domainModelCrossover, domainModelMutation, configurationLower);
		try {
			manager.runNSGAII();
			
			return -manager.getHypervolume();
		} catch (JMetalException | FileNotFoundException | InterruptedException e1) {
			return -10000.0;
		}
	}

}