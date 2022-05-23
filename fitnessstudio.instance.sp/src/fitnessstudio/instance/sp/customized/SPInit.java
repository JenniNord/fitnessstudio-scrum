package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.uma.jmetal.problem.Problem;
import de.uni_ko.fitnessstudio.nsga.Init;
import de.uni_ko.fitnessstudio.util.ModelIO;
import fitnessstudio.instance.sp.fitness.HasTheAllowedMaximalNumberOfSprints;
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
		
		//createEmptyPopulation(population, size); //no
		createRandomPopulation(population, size);
		//createCompletePopulation(population, size); //no
		//createExtremesPopulation(population, size); //no
		//createRandomWithExtremesPopulation(population, size); //no
	    
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
	
	// some work items will be added to a sprint 
	private SPSolution createRandomSolution() {
		SPSolution solution = problem.createSolution();
		int sprintVelocity = 0;
		int max_velocity = new HasTheAllowedMaximalNumberOfSprints().getMaximumVelocity();
		Sprint sprint = SPFactory.eINSTANCE.createSprint();
		solution.getVariable(0).getSprints().add(sprint);
		
		// for every work item in the backlog
		for (int i = 0; i < solution.getVariable(0).getBacklog().getWorkitems().size(); i++) {
			if (Math.random() > 0.5) {
				// if the sprint isn't full
				if (sprintVelocity < max_velocity) {
					// add work item to sprint
					WorkItem workitem = solution.getVariable(0).getBacklog().getWorkitems().get(i);
					workitem.setIsPlannedFor(sprint);
					sprint.getCommittedItem().add(workitem);
					// keep track of sprint effort
					sprintVelocity += workitem.getEffort();
				} else {
					sprint = SPFactory.eINSTANCE.createSprint();
					// add sprint to plan
					solution.getVariable(0).getSprints().add(sprint);
					// add work item to sprint
					WorkItem workitem = solution.getVariable(0).getBacklog().getWorkitems().get(i);
					workitem.setIsPlannedFor(sprint);
					sprint.getCommittedItem().add(workitem);
					// reset/set sprint velocity
					sprintVelocity = workitem.getEffort();
				}
			}
		}
		
		// remove sprint if it's empty
		if (sprint.getCommittedItem().isEmpty()) {
			solution.getVariable(0).getSprints().remove(sprint);
		}
		
		return solution;
	}
	
	// all work items gets added to the plan in sprints
	// a new sprint is created when the max velocity is reached
	private SPSolution createCompleteSolution() {
		SPSolution solution = problem.createSolution();
		int sprintVelocity = 0;
		int max_velocity = new HasTheAllowedMaximalNumberOfSprints().getMaximumVelocity();
		Sprint sprint = SPFactory.eINSTANCE.createSprint();
		solution.getVariable(0).getSprints().add(sprint);
		
		// for every work item in the backlog
		for (int i = 0; i < solution.getVariable(0).getBacklog().getWorkitems().size(); i++) {
			// if the sprint isn't full
			if (sprintVelocity < max_velocity) {
				// add work item to sprint
				WorkItem workitem = solution.getVariable(0).getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(sprint);
				sprint.getCommittedItem().add(workitem);
				// keep track of sprint effort
				sprintVelocity += workitem.getEffort();
			} else {
				sprint = SPFactory.eINSTANCE.createSprint();
				// add sprint to plan
				solution.getVariable(0).getSprints().add(sprint);
				// add work item to sprint
				WorkItem workitem = solution.getVariable(0).getBacklog().getWorkitems().get(i);
				workitem.setIsPlannedFor(sprint);
				sprint.getCommittedItem().add(workitem);
				// reset/set sprint velocity
				sprintVelocity = workitem.getEffort();
			}
		}
		
		// remove sprint if it's empty
		if (sprint.getCommittedItem().isEmpty()) {
			solution.getVariable(0).getSprints().remove(sprint);
		}
		
		return solution;
	}
}
