package de.uni_ko.fitnessstudio.lower;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIMeasures;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.lab.visualization.plot.PlotFront;
import org.uma.jmetal.lab.visualization.plot.impl.PlotSmile;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.qualityindicator.impl.NormalizedHypervolume;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.measure.MeasureManager;
import org.uma.jmetal.util.measure.impl.BasicMeasure;
import org.uma.jmetal.util.measure.impl.DurationMeasure;
import org.uma.jmetal.util.point.PointSolution;

import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.nsga.InitNSGAIIBuilder;
import de.uni_ko.fitnessstudio.util.GAConfiguration;

public class LowerNSGAIIManager<S> extends AbstractAlgorithmRunner {
	DomainModelProblem<S> problem;
	Init<DomainModelSolution<S>> init;
	Algorithm<List<DomainModelSolution<S>>> algorithm;
	DomainModelCrossover<DomainModelSolution<S>> crossover;
	DomainModelMutation<S> mutation;
	SelectionOperator<List<DomainModelSolution<S>>, DomainModelSolution<S>> selection;
	GAConfiguration configuration;
	
	long computingTime = -1;
	String referenceParetoFront;
	String prefix = "";
	
	public LowerNSGAIIManager(DomainModelProblem<S> problem, Init<DomainModelSolution<S>> init,
			DomainModelCrossover<DomainModelSolution<S>> crossover, DomainModelMutation<S> mutation, GAConfiguration configuration) {
		this.problem = problem;
		this.init = init;
		this.crossover = crossover;
		this.mutation = mutation;
		this.selection = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());
		this.configuration = configuration;
		this.referenceParetoFront  = "resource\\ref" + problem.INPUT_MODEL_ID + ".csv";
	}
	
	public void runNSGAII() throws JMetalException, InterruptedException, FileNotFoundException {
		algorithm = 
				new InitNSGAIIBuilder<>(problem, crossover, mutation, configuration.getPopulationSize(), init)
					.setSelectionOperator(selection)
					.setMaxEvaluations(configuration.getMaxEvaluations())
					.build();
				/* new NSGAIIBuilder<>(problem, crossover, mutation, configuration.getPopulationSize())
	            	.setSelectionOperator(selection)
	            	.setMaxEvaluations(configuration.getMaxEvaluations())
	            	.setVariant(NSGAIIBuilder.NSGAIIVariant.Measures)
	            	.build(); */

		((NSGAIIMeasures<DomainModelSolution<S>>) algorithm).setReferenceFront(new ArrayFront(referenceParetoFront));
	    AlgorithmRunner algorithmRunner = 
	    		new AlgorithmRunner.Executor(algorithm)
	            	.execute();
	    
	    /* Measure management */
	   MeasureManager measureManager = ((NSGAIIMeasures<DomainModelSolution<S>>)algorithm).getMeasureManager() ;
	    
	    DurationMeasure currentComputingTime =
	            (DurationMeasure) measureManager.<Long>getPullMeasure("currentExecutionTime");

        BasicMeasure<Double> hypervolumeMeasure =
                (BasicMeasure<Double>) measureManager.<Double>getPushMeasure("hypervolume");

        /* End of measure management */
	    
	    List<DomainModelSolution<S>> population = algorithm.getResult();
        
	    computingTime = algorithmRunner.getComputingTime();
	    
	    printFinalDomainModelSolutionSet(population);
	    /*
	    
	    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
	
	    printFinalSolutionSet(population);
	    if (!referenceParetoFront.equals("")) {
	      printQualityIndicators(population, referenceParetoFront);
	    }
*
	    PlotFront plot = new PlotSmile(new ArrayFront(population).getMatrix()) ;
	    plot.plot(); */
	}
	
	public List<DomainModelSolution<S>> getResult() {
		return algorithm.getResult();
	}
	
	public long getComputingTime() {
		return computingTime;
	}
	
	public double getHypervolume() throws FileNotFoundException {
		Front referenceFront = new ArrayFront(referenceParetoFront);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(algorithm.getResult())) ;
	    List<PointSolution> normalizedPopulation = FrontUtils
	        .convertFrontToSolutionList(normalizedFront) ;
	    
	    return new NormalizedHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
	}
	
	public double getSpread() throws FileNotFoundException {
		Front referenceFront = new ArrayFront(referenceParetoFront);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(algorithm.getResult())) ;
	    List<PointSolution> normalizedPopulation = FrontUtils
	        .convertFrontToSolutionList(normalizedFront) ;
		
		return new Spread<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
	}
	
	private void printFinalDomainModelSolutionSet(List<DomainModelSolution<S>> population) {
		new SolutionListOutput(population)
        	.setFunFileOutputContext(new DefaultFileOutputContext(prefix + "FUN.csv", ","))
        	.print();
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
