package fitnessstudio.instance.sp.runners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import sp.model.sp.*;
import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import de.uni_ko.fitnessstudio.lower.DomainModelProblem;
import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.upper.UpperGAManager;
import de.uni_ko.fitnessstudio.util.GAConfiguration;
import de.uni_ko.fitnessstudio.util.ModelIO;
import fitnessstudio.instance.sp.customized.SPConstraintChecker;
import fitnessstudio.instance.sp.customized.SPCrossover;
import fitnessstudio.instance.sp.customized.SPInit;
import fitnessstudio.instance.sp.customized.SPProblem;

@SuppressWarnings("all")
public class UpperTierRunner {
	private static String INPUT_MODEL_ID = "A";
	private static String INPUT_MODEL = "input\\" + INPUT_MODEL_ID + ".xmi";
	private static String OUTPUT_PREFIX = "output_rules\\" + INPUT_MODEL_ID + "\\"
			+ new SimpleDateFormat("HH_mm_ss").format(Calendar.getInstance().getTime()).toString() + "\\";

	private static int UPPER_TIER_ITERATIONS = 15; //30?
	private static int UPPER_TIER_POPULATION_SIZE = 60;
	private static int LOWER_TIER_MAX_EVALUATIONS = 200;
	private static int LOWER_TIER_POPULATION_SIZE = 8;
	private static int RUNS = 1; //5; 30
	private static int TIMEOUT = 180;
	
	private static final Map<String, Double> RULES_WEIGHT = Map.of(
			"createPreserveEdgesWithNode", 0.2,
			"createPreserveEdge", 0.2,
			"createCrOrDelNodeWithContainmentEdge", 0.2, // changed
			"createCrOrDelEdge", 0.9,
			"createPreserveNodeWithIncomingEdge", 0.2,
			"createPreserveNodeWithOutgoingEdge", 0.2,
			"addNAC", 0.2,
			"addNAC2", 0.2,
			"CreateMultiRuleWithMappedPreserveNode", 0.2
		);
	
	private static GAConfiguration configurationUpper = new GAConfiguration(UPPER_TIER_ITERATIONS, UPPER_TIER_POPULATION_SIZE, true);
	private static GAConfiguration configurationLower = new GAConfiguration(LOWER_TIER_MAX_EVALUATIONS, LOWER_TIER_POPULATION_SIZE, false);

	public static void main(String[] args) {

		for (int i = 1; i <= RUNS; i++) {
			System.gc();
			System.out.println("============");
			System.out.println("Run " + i);
			System.out.println("============");

			long start = System.currentTimeMillis();

			SPPackage.eINSTANCE.eClass();
			EPackage metaModel = SPPackage.eINSTANCE;
			DomainModelProblem domainModelProblem = new SPProblem(INPUT_MODEL_ID);
			Init init = new SPInit();
			DomainModelCrossover domainModelCrossover = new SPCrossover(0.9);
			
			SPConstraintChecker mutationConstraintChecker = new SPConstraintChecker();
			EObject inputModel = ModelIO.loadModel(INPUT_MODEL);
			
			UpperGAManager manager = new UpperGAManager(mutationConstraintChecker, metaModel, domainModelProblem, init, domainModelCrossover,
					configurationUpper, configurationLower, inputModel, TIMEOUT, RULES_WEIGHT);
			manager.setPrefix(OUTPUT_PREFIX + "\\"+i);
			double result = manager.runGA();

			long end = System.currentTimeMillis();
			long time = end - start;
			createLogEntry(i, result, time);
		}
	}

	private static void createLogEntry(int i, double result, long time) {
		String line = i + " \t " + time + " \t " + result + "\n";
		String path = OUTPUT_PREFIX + "log.txt";

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
			bw.write(line);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
