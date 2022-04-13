package fitnessstudio.instance.sp.customized;

import java.util.HashMap;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

import de.uni_ko.fitnessstudio.lower.DomainModelSolution;
import de.uni_ko.fitnessstudio.util.ModelIO;
import sp.model.sp.Plan;
import sp.model.sp.SPFactory;
import sp.model.sp.Sprint;
import sp.model.sp.WorkItem;

@SuppressWarnings("serial")
public class SPSolution extends DomainModelSolution<Plan> {
	
	private RandomGenerator<Double> solutionRandomGenerator;
	
	/** Constructor */
	protected SPSolution(int numberOfVariables, int numberOfObjectives, int numberOfConstraints, String inputModelId) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints);

		this.solutionRandomGenerator = () -> JMetalRandom.getInstance().nextDouble();
		
		String path = "input\\" + inputModelId + ".xmi";
		// Plan solution = createRandomSolution(path);
		// Plan solution = createExtremeSolution(path);
		Plan solution = createEmptySolution(path);
		
		super.setVariable(0, solution);
	}
	
	/** Constructor */
	public SPSolution(SPSolution solution) {
		super(solution.getNumberOfVariables(), solution.getNumberOfObjectives(), solution.getNumberOfConstraints());
		
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			Plan deepCopy = (Plan) EcoreUtil.copy(solution.getVariable(i));;
			setVariable(i, deepCopy);
	    }

	    for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
	    	setObjective(i, solution.getObjective(i)) ;
	    }

	    for (int i = 0; i < solution.getNumberOfConstraints(); i++) {
	    	setConstraint(i, solution.getConstraint(i));
	    }

	    attributes = new HashMap<Object, Object>(solution.attributes) ;
	}

	// Commented out
	// some work items will get added to the plan with a new sprint & some plans will be empty
	private Plan createExtremeSolution(String path) {
		Plan inputModel = (Plan) ModelIO.loadModel(path);
		
		if (Math.random() > 0.5) {
			for (int i = 0; i < inputModel.getBacklog().getWorkitems().size(); i++) {
				// create sprint
				Sprint sprint = SPFactory.eINSTANCE.createSprint();
				
				// add work item to sprint
				WorkItem workitem = inputModel.getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(sprint);
				sprint.getCommittedItem().add(workitem);
				
				// add sprint to plan
				inputModel.getSprints().add(sprint);
			}
		}
		
		return inputModel;
	}
	
	// Commented out
	// some work items will get added to the plan with a new sprint
	private Plan createRandomSolution(String path) {
		Plan inputModel = (Plan) ModelIO.loadModel(path);
		for (int i = 0; i < inputModel.getBacklog().getWorkitems().size(); i++) {
			if (solutionRandomGenerator.getRandomValue() > 0.5) {
				// create sprint
				Sprint sprint = SPFactory.eINSTANCE.createSprint();
				
				// add work item to sprint
				WorkItem workitem = inputModel.getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(sprint);
				sprint.getCommittedItem().add(workitem);
				
				// add sprint to plan
				inputModel.getSprints().add(sprint);
			}
		}
		
		return inputModel;
	}
	
	// Commented out
	// all work items gets added to the plan with one sprint each
	private Plan createCompleteSolution(String path) {
		Plan inputModel = (Plan) ModelIO.loadModel(path);
		
		for (int i = 0; i < inputModel.getBacklog().getWorkitems().size(); i++) {
			// create sprint
			Sprint sprint = SPFactory.eINSTANCE.createSprint();
			
			// add work item to sprint
			WorkItem workitem = inputModel.getBacklog().getWorkitems().get(i);
			workitem.setIsPlannedFor(sprint);
			sprint.getCommittedItem().add(workitem);
			
			// add sprint to plan
			inputModel.getSprints().add(sprint);
		}
		
		return inputModel;
	}
	
	private Plan createEmptySolution(String path) {
		Plan inputModel = (Plan) ModelIO.loadModel(path);
		
		return inputModel;
	}
	
	@Override
	public Solution<Plan> copy() {
		return new SPSolution(this);
	}

}
