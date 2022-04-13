package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.util.checking.Check;

import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import sp.model.sp.Plan;
import sp.model.sp.Sprint;
import sp.model.sp.WorkItem;


@SuppressWarnings("serial")
public class SPCrossover implements DomainModelCrossover<SPSolution> {

	private double crossoverProbability;
	private Random generator = new Random();
	
	/** Constructor */
	public SPCrossover(double probability) {
		this.crossoverProbability = probability;
	}
	
	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}

	@Override
	public List<SPSolution> execute(List<SPSolution> parents) {
		Check.isNotNull(parents);
	    Check.that(parents.size() == 2, "There must be 2 parents instead of " + parents.size());
	    
		List<SPSolution> offspring = new ArrayList<>(2);
		SPSolution child1 = (SPSolution) parents.get(0).copy();
		SPSolution child2 = (SPSolution) parents.get(1).copy();
		
		offspring.add(child1);
	    offspring.add(child2);
		
	    if (Math.random() > crossoverProbability)
	    	return offspring;
	    
	    int backlogItems = parents.get(0).getVariable(0).getBacklog().getWorkitems().size();

	    // Single Point Crossover
	    int pivot = generator.nextInt(backlogItems);
	    doCrossover(parents.get(0).getVariable(0), parents.get(1).getVariable(0), child1.getVariable(0), pivot);
	    doCrossover(parents.get(1).getVariable(0), parents.get(0).getVariable(0), child2.getVariable(0), pivot);
	    
	    return offspring;
	}
	
	public void doCrossover(Plan parent1, Plan parent2, Plan child, int pivot) {
		child.getSprints().get(0).getCommittedItem().clear();
		
		int n = 0;
		for (WorkItem workitem : child.getBacklog().getWorkitems()) {
			workitem.setIsPlannedFor(null);
			
			if (n < pivot) {
				for (WorkItem p1_workitem : parent1.getSprints().get(0).getCommittedItem()) {
					if (workItemEquals(workitem, p1_workitem)) {
						workitem.setIsPlannedFor(child.getSprints().get(0));
						child.getSprints().get(0).getCommittedItem().add(workitem);
						break;
					}
				}
			} else {
				for (WorkItem p2_workitem : parent2.getSprints().get(0).getCommittedItem()) {
					if (workItemEquals(workitem, p2_workitem)) {
						workitem.setIsPlannedFor(child.getSprints().get(0));
						child.getSprints().get(0).getCommittedItem().add(workitem);
						break;
					}
				}
			}
			n++;
		}
	}
	
	// TODO: move this somewhere? fix it
	private boolean workItemEquals(WorkItem wiA, WorkItem wiB) {
		return (wiA.getEffort() == wiB.getEffort() 
				&& wiA.getImportance() == wiB.getImportance() 
				&& wiA.getStakeholder() == wiB.getStakeholder());
	}
}