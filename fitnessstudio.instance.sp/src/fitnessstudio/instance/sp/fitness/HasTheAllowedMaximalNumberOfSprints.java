package fitnessstudio.instance.sp.fitness;

import sp.model.sp.*;

/**
 * https://github.com/mde-optimiser/mde_optimiser
 */
public class HasTheAllowedMaximalNumberOfSprints {

  public double computeFitness(Plan plan) {

    double totalEffort =
        plan.getBacklog().getWorkitems().stream()
            .mapToDouble(workItem -> workItem.getEffort())
            .sum();

    // TODO: remove "magic number"?
    int maximumVelocity = 25;

    double desiredSprints = totalEffort / maximumVelocity;

    if (desiredSprints - (int) desiredSprints > 0.5d) {
      desiredSprints = Math.ceil(desiredSprints);
    } else {
      desiredSprints = Math.floor(desiredSprints);
    }

    long nonEmptySprints =
        plan.getSprints().stream().filter(sprint -> sprint.getCommittedItem().size() > 0).count();

    // If we have less than the minimum number of desired sprints
    // Negative numbers trigger constraint violations in JMetal
    if (nonEmptySprints > desiredSprints) {
		System.out.println("Counted sprints: " + nonEmptySprints);
	    System.out.println("Counted maximal desired sprints: " + desiredSprints);
      return (desiredSprints - nonEmptySprints);
    }

    return 0;
  }

  public String getName() {
    return "Has the allowed maximal number of sprints";
  }
}
