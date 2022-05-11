package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.uma.jmetal.problem.Problem;
import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.util.ModelIO;

import sp.model.sp.Plan;
import sp.model.sp.Sprint;
import sp.model.sp.SPFactory;
import sp.model.sp.WorkItem;

public class SPInit extends Init<SPSolution> {
	
	private Problem<SPSolution> problem;
	
	@Override
	public List<SPSolution> createInitialPopulation(int size, Problem<SPSolution> problem) {
		this.problem = problem;
		
		List<SPSolution> population = new ArrayList<>(size);
		
		//createEmptyPopulation(population, size);
	    
		// 1:1 Sprints:WorkItems
		//createRandomPopulation(population, size);
		createCompletePopulation(population, size);
		//createExtremesPopulation(population, size);
		//createRandomWithExtremesPopulation(population, size);
		
		// Blob Sprint, 1 Sprint:All WI
		//createRandomBlobPopulation(population, size);
		//createCompleteBlobPopulation(population, size);
		//createExtremesBlobPopulation(population, size);
		//createRandomWithExtremesBlobPopulation(population, size);
	    
	    return population;
	}
	
	private void createRandomPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			population.add(createRandomSolution());
	    }
	}
	
	private void createEmptyPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			population.add(problem.createSolution());
	    }
	}
	
	private void createCompletePopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			population.add(createCompleteSolution());
	    }
	}
	
	private void createExtremesPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			if (i % 2 == 0) {
				population.add(createCompleteSolution());
			} else {
				population.add(problem.createSolution());
			}
	    }
	}
	
	private void createRandomWithExtremesPopulation(List<SPSolution> population, int size) {
		population.add(problem.createSolution());
		
		for (int i = 1; i < size - 1; i++) {
			population.add(createRandomSolution());
	    }
		
		population.add(createCompleteSolution());
	}
	
	// some work items will get added to the plan with a new sprint
	private SPSolution createRandomSolution() {
		SPSolution solution = problem.createSolution();
		//Plan plan = solution.getVariable(0);
		
		for (int i = 0; i < solution.getVariable(0).getBacklog().getWorkitems().size(); i++) {
			if (Math.random() > 0.5) {
				// create sprint
				Sprint sprint = SPFactory.eINSTANCE.createSprint();
				
				// add work item to sprint
				WorkItem workitem = solution.getVariable(0).getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(sprint);
				sprint.getCommittedItem().add(workitem);
				
				// add sprint to plan
				solution.getVariable(0).getSprints().add(sprint);
			}
		}
		
		return solution;
	}
	
	// all work items gets added to the plan with one sprint each
	private SPSolution createCompleteSolution() {
		SPSolution solution = problem.createSolution();
		Plan plan = solution.getVariable(0);
		
		for (int i = 0; i < plan.getBacklog().getWorkitems().size(); i++) {
			// create sprint
			Sprint sprint = SPFactory.eINSTANCE.createSprint();
			
			// add work item to sprint
			WorkItem workitem = plan.getBacklog().getWorkitems().get(i);
			workitem.setIsPlannedFor(sprint);
			sprint.getCommittedItem().add(workitem);
			
			// add sprint to plan
			plan.getSprints().add(sprint);
		}
		
		return solution;
	}
	
	private void createRandomBlobPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			population.add(createRandomBlobSolution());
	    }
	}
	
	private void createCompleteBlobPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			population.add(createCompleteBlobSolution());
	    }
	}
	
	private void createExtremesBlobPopulation(List<SPSolution> population, int size) {
		for (int i = 0; i < size; i++) {
			if (i % 2 == 0) {
				population.add(createCompleteBlobSolution());
			} else {
				population.add(problem.createSolution());
			}
	    }
	}
	
	private void createRandomWithExtremesBlobPopulation(List<SPSolution> population, int size) {
		population.add(problem.createSolution());
		
		for (int i = 1; i < size - 1; i++) {
			population.add(createRandomBlobSolution());
	    }
		
		population.add(createCompleteBlobSolution());
	}
	
	// some work items will get added to one sprint belonging to the plan
	private SPSolution createRandomBlobSolution() {
		SPSolution solution = problem.createSolution();
		Plan plan = solution.getVariable(0);
		
		// create sprint
		Sprint sprint = SPFactory.eINSTANCE.createSprint();
		// add sprint to plan
		plan.getSprints().add(sprint);
		
		for (int i = 0; i < plan.getBacklog().getWorkitems().size(); i++) {
			if (Math.random() > 0.5) {
				// add work item to sprint
				WorkItem workitem = plan.getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(plan.getSprints().get(0));
				plan.getSprints().get(0).getCommittedItem().add(workitem);
			}
		}
		
		return solution;
	}
	
	// all work items gets added to the same sprint in the plan
	private SPSolution createCompleteBlobSolution() {
		SPSolution solution = problem.createSolution();
		Plan plan = solution.getVariable(0);
		
		// create sprint
		Sprint sprint = SPFactory.eINSTANCE.createSprint();
		// add sprint to plan
		plan.getSprints().add(sprint);
		
		for (int i = 0; i < plan.getBacklog().getWorkitems().size(); i++) {
			// add work item to sprint
			WorkItem workitem = plan.getBacklog().getWorkitems().get(i);
			workitem.setIsPlannedFor(plan.getSprints().get(0));
			plan.getSprints().get(0).getCommittedItem().add(workitem);
		}
		
		return solution;
	}
}
