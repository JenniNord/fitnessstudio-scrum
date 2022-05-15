package fitnessstudio.instance.sp.runners;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.uma.jmetal.util.JMetalException;

import sp.model.sp.*;
import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import de.uni_ko.fitnessstudio.lower.DomainModelMutation;
import de.uni_ko.fitnessstudio.lower.DomainModelProblem;
import de.uni_ko.fitnessstudio.lower.LowerNSGAIIManager;

import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.util.GAConfiguration;
import de.uni_ko.fitnessstudio.util.ModelIO;
import fitnessstudio.instance.sp.customized.SPConstraintChecker;
import fitnessstudio.instance.sp.customized.SPCrossover;
import fitnessstudio.instance.sp.customized.SPInit;
import fitnessstudio.instance.sp.customized.SPProblem;

@SuppressWarnings("all")
public class LowerTierRunnerWithFixed {
	private static String MUTATION_RULES_DIRECTORY = "transformation\\fixed";

	private static int RUNS = 1;//30;//
	private static int MAX_EVALUATIONS = 150000;//500;5000;150000;//
	private static int POPULATION_SIZE = 200;//40;//
	
	private static GAConfiguration configuration = new GAConfiguration(MAX_EVALUATIONS, POPULATION_SIZE, true);

	public static void main(String[] args) throws JMetalException, InterruptedException, FileNotFoundException {
		SPPackage.eINSTANCE.eClass();
		
		List<String> models = Arrays.asList("A", "B", "C", "D", "E");
		for (String model : models) {
			runWithModel(model);
		}
	}
	
	private static void runWithModel(final String INPUT_MODEL_ID) throws JMetalException, InterruptedException, FileNotFoundException {
		final String OUTPUT_PREFIX = "output_models\\" +INPUT_MODEL_ID + "\\" + new SimpleDateFormat("HH_mm_ss").format(Calendar.getInstance().getTime()).toString() + "\\";
		System.out.println("============");
		
		try {
		    Path path = Paths.get(OUTPUT_PREFIX);

		    Files.createDirectories(path);

		  } catch (IOException e) {
		    System.err.println("Failed to create directory!" + e.getMessage());
		  }
		
		for (int i = 1; i <= RUNS; i++) {
			System.out.println("============");
			System.out.println("Run "+i);
			System.out.println("============");
			
			DomainModelProblem problem = new SPProblem(INPUT_MODEL_ID);
			Init init = new SPInit();
			DomainModelCrossover crossover = new SPCrossover(0.9); //.8//.9
			DomainModelMutation mutation = new DomainModelMutation(getGenRules(), getFixedRules(), 0.6); //.3//.6
			
			LowerNSGAIIManager gaManager = new LowerNSGAIIManager(problem, init, crossover, mutation, configuration);
			gaManager.setPrefix(OUTPUT_PREFIX + i);
			gaManager.runNSGAII();
			
			System.out.print("Pareto time");
			
			// Models pareto front
			gaManager.getResult();

			long computingTime = gaManager.getComputingTime();
			
			// For each run: log i + hypervolume + runtime
			//System.out.println(i + "\t" + gaManager.getHypervolume() + "\t" + computingTime);
			//createLogEntry(i, gaManager.getHypervolume(), gaManager.getSpread(), computingTime, OUTPUT_PREFIX);
		}
	}

	private static void createLogEntry(int i, double hypervolume, double spread, long time, final String OUTPUT_PREFIX) {
		String line = i+ " \t " + time + " \t " + hypervolume + " \t " + spread +"\n";
		String path = OUTPUT_PREFIX + "log.txt";
	
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
			bw.write(line);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Set<Rule> getFixedMutationRules() {
		ModelIO.registerPackage();

		Path fixedRulesDirectory = Paths.get(MUTATION_RULES_DIRECTORY);
		Path firstHenshinFile = null;
		try {
			firstHenshinFile = Files.list(fixedRulesDirectory).filter(x -> x.toString().endsWith(".henshin"))
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HenshinResourceSet resSet = new HenshinResourceSet();
		Resource res1 = resSet.getResource(URI.createURI(firstHenshinFile.toString()), true);
		System.out.println(firstHenshinFile.toString());
		Module mod1 = (Module) res1.getContents().get(0);
		Set<Rule> mutationRules = new HashSet<Rule>();
		for (Unit u : mod1.getUnits()) {
			if (u instanceof Rule)
				mutationRules.add(((Rule) u));
		}
		return mutationRules;
	}
	
	private static Set<Unit> getFixedRules() {
		HenshinResourceSet resSet = new HenshinResourceSet();
		
		Resource resource = resSet.getResource(URI.createURI("..\\de.uni_ko.fitnessstudio\\transformation\\genetic\\mutation.henshin"), true);
		Module module = (Module) resource.getContents().get(0);
		
		Set<Unit> fixedRules = new HashSet<Unit>(module.getUnits());
		
		// Remove all SubUnits from fixedRules
		for (Unit unit : module.getUnits()) {
			List<Unit> subUnits = unit.getSubUnits(true);
			fixedRules.removeAll(subUnits);
		}
		
		return fixedRules;
	}
	
	public static Set<Rule> getGenRules() {
		ModelIO.registerPackage();

		Path fixedRulesDirectory = Paths.get(MUTATION_RULES_DIRECTORY);
		Path firstHenshinFile = null;
		try {
			firstHenshinFile = Files.list(fixedRulesDirectory).filter(x -> x.toString().endsWith(".henshin"))
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HenshinResourceSet resSet = new HenshinResourceSet();
		Resource res1 = resSet.getResource(URI.createURI(firstHenshinFile.toString()), true);
		System.out.println(firstHenshinFile.toString());
		Module mod1 = (Module) res1.getContents().get(0);
		Set<Rule> mutationRules = new HashSet<Rule>();
		for (Unit u : mod1.getUnits()) {
			if (u instanceof Rule)
				mutationRules.add(((Rule) u));
		}
		return mutationRules;
	}
	

}
