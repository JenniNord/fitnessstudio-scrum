package fitnessstudio.instance.sp.fitness;

import sp.model.sp.*;

/**
 * https://github.com/mde-optimiser/mde_optimiser
 */
public class HasNoUnassignedWorkItems {

  public double computeFitness(Plan plan) {

    long unassignedWorkItems =
        plan.getBacklog().getWorkitems().stream()
	        .filter(wi -> wi.getIsPlannedFor() == null)
	        .count();

    System.out.println("Unassigned backlog work items: " + unassignedWorkItems);

    // Negative numbers trigger constraint violations in JMetal
    return unassignedWorkItems * -1;
  }

  public String getName() {
    return "Mimise unassigned work items";
  }
}
