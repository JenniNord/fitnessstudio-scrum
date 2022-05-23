package fitnessstudio.instance.sp.fitness;

import sp.model.sp.*;

/**
 * https://github.com/mde-optimiser/mde_optimiser
 * 
 * Added getMaximumVelocity
 * Moved getDesiredSprints to a method
 */
public class HasTheAllowedMaximalNumberOfSprints {
	
	private int maximumVelocity = 25;
	
	public int getMaximumVelocity() {
		return maximumVelocity;
	}
	
	public double getDesiredSprints(Plan plan) {
		double totalEffort =
			plan.getBacklog().getWorkitems().stream()
			    .mapToDouble(workItem -> workItem.getEffort())
			    .sum();
		
		return totalEffort / maximumVelocity;
	}

	public double computeFitness(Plan plan) {
		
		double desiredSprints = getDesiredSprints(plan);
		
		if (desiredSprints - (int) desiredSprints > 0.5d) {
			desiredSprints = Math.ceil(desiredSprints);
		} else {
			desiredSprints = Math.floor(desiredSprints);
		}
		
		long nonEmptySprints = plan.getSprints().stream().filter(sprint -> sprint.getCommittedItem().size() > 0).count();
		
		// If we have less than the minimum number of desired sprints
		// Negative numbers trigger constraint violations in JMetal
		if (nonEmptySprints > desiredSprints) {
			//System.out.println("Counted sprints: " + nonEmptySprints);
			//System.out.println("Counted maximal desired sprints: " + desiredSprints);
			return (desiredSprints - nonEmptySprints);
		}
		
		return 0;
	}

	public String getName() {
		return "Has the allowed maximal number of sprints";
	}
}
