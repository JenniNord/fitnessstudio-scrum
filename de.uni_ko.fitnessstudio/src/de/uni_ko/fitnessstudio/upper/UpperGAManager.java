package de.uni_ko.fitnessstudio.upper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import com.lagodiuk.Fitness;
import com.lagodiuk.GA;
import com.lagodiuk.GAIterationListener;
import com.lagodiuk.GAPopulation;
import com.lagodiuk.GAwithTimeout;

import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import de.uni_ko.fitnessstudio.lower.DomainModelProblem;
import de.uni_ko.fitnessstudio.lower.DomainModelSolution;
import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.util.GAConfiguration;
import de.uni_ko.fitnessstudio.util.ModelIO;

public class UpperGAManager<S> {

	private String prefix = "output_rules\\"
			+ new SimpleDateFormat("HH_mm_ss").format(Calendar.getInstance().getTime()).toString();

	private EObject inputModel;
	private EPackage metaModel;
	private DomainModelProblem<S> domainModelProblem;
	private Init<DomainModelSolution<S>> init;
	private DomainModelCrossover<DomainModelSolution<S>> domainModelCrossover;
	private ConstraintChecker constraintChecker;
	private GAConfiguration configurationUpper;
	private GAConfiguration configurationLower;
	private int timeoutSeconds;
	
	Map<String, Double> rulesWeight;
	
	long time = 0;

	private GA<RuleSet, Double> ga;

	public UpperGAManager(ConstraintChecker ruleSetChecker, EPackage metaModel, DomainModelProblem<S> problem, Init<DomainModelSolution<S>> init, DomainModelCrossover<DomainModelSolution<S>> crossover, 
			GAConfiguration configurationUpper, GAConfiguration configurationLower, EObject inputModel, int timeoutSeconds, Map<String, Double> rulesWeight) {
		this.constraintChecker = ruleSetChecker;
		this.metaModel = metaModel;
		this.domainModelProblem = problem;
		this.init = init;
		this.domainModelCrossover = crossover;
		this.configurationUpper = configurationUpper;
		this.configurationLower = configurationLower;
		this.inputModel = inputModel;
		this.timeoutSeconds = timeoutSeconds;
		this.rulesWeight = rulesWeight;
	}

	public double runGA() {
		time = System.currentTimeMillis();
		GAPopulation<RuleSet> population = RuleSetInit.create(configurationUpper.getPopulationSize(), metaModel,
				constraintChecker, rulesWeight);
		Fitness<RuleSet, Double> fitness = new RuleSetFitness<S>(inputModel, domainModelProblem, init, domainModelCrossover, configurationLower, constraintChecker);
		ga = new GAwithTimeout<RuleSet, Double>(population, fitness, timeoutSeconds);
		addListener(ga);
		ga.evolve(configurationUpper.getMaxEvaluations());
		RuleSet best = ga.getBest();
		double fitnessVal = ga.fitness(best);
		ga.getPopulation().trim(0);
		ga.clearCache();
		time = System.currentTimeMillis();
		if (best == null || !constraintChecker.satisfiesMutationConstraints(best.getGenRules()))
			return -10000.0;
		else
			return fitnessVal;
	}

	/**
	 * Listener for printing best chromosome after each iteration
	 */
	private void addListener(GA<RuleSet, Double> ga) {
		// just for pretty print
		System.out.println(String.format("%s\t%s\t%s\t%s", "iter", "fit", "chromosome", "seconds"));

		// Lets add listener, which prints best chromosome after each iteration
		ga.addIterationListener(new GAIterationListener<RuleSet, Double>() {

			@Override
			public void update(GA<RuleSet, Double> ga) {
				time = (System.currentTimeMillis() - time)/1000;
				RuleSet best = ga.getBest();
				double bestFit = ga.fitness(best);
				int iteration = ga.getIteration();
//				ga.getFitnessFunc().clearCache();

				// Listener prints best achieved solution
				System.out.println(String.format("%s\t%s\t%s\t%s", iteration, bestFit, best, time));
				ModelIO.saveProducedRuleSet(best.getGenRules(), iteration, bestFit, prefix);
				time = System.currentTimeMillis();
			}
		});
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
