package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.uma.jmetal.util.checking.Check;

import de.uni_ko.fitnessstudio.lower.DomainModelCrossover;
import sp.model.sp.Plan;
import sp.model.sp.SPFactory;
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
	    
	    crossOver(parents, child1);
	    crossOver(parents, child2);
	    
	    return offspring;
	}
	
	private void crossOver(List<SPSolution> parents, SPSolution child) {
		child.getVariable(0).getSprints().clear();
	    child.getVariable(0).getBacklog().getWorkitems().stream().forEach(w -> w.setIsPlannedFor(null));
	    
		boolean turn = false;
		
	    while (!(parents.get(0).getVariable(0).getSprints().isEmpty() && parents.get(1).getVariable(0).getSprints().isEmpty())) {
	    	if (turn && !parents.get(0).getVariable(0).getSprints().isEmpty()) {
	    		moveSelectedSprint(parents.get(0).getVariable(0), parents.get(1).getVariable(0), child.getVariable(0));
	    	} else if (!turn && !parents.get(1).getVariable(0).getSprints().isEmpty()) {
	    		moveSelectedSprint(parents.get(1).getVariable(0), parents.get(0).getVariable(0), child.getVariable(0));
	    	}
	    	turn = !turn;
	    }
	}
	
	private void moveSelectedSprint(Plan dominant, Plan recessive, Plan child) {
		dominant.getSprints().removeAll(
				dominant.getSprints().stream().filter(c -> c.getCommittedItem().isEmpty()).collect(Collectors.toSet()));
		if (!dominant.getSprints().isEmpty()) {
			Sprint selected = selectSprint(dominant);
			Sprint selected_ = SPFactory.eINSTANCE.createSprint();
			child.getSprints().add(selected_);
			
			EList<WorkItem> committed = selected.getCommittedItem();
			
			// Remove all features from the recessive class
			for (WorkItem w : child.getBacklog().getWorkitems()) {
				for (WorkItem wi : committed) {
					if (workItemEquals(w, wi)) {
						w.setIsPlannedFor(selected_);
					}
				}	
			}
			for (WorkItem w : recessive.getBacklog().getWorkitems()) {
				for (WorkItem wi : committed) {
					if (workItemEquals(w, wi)) {
						w.setIsPlannedFor(null);
					}
				}
			}
			dominant.getSprints().remove(selected);
		}
		recessive.getSprints().removeAll(
				recessive.getSprints().stream().filter(c -> c.getCommittedItem().isEmpty()).collect(Collectors.toSet()));
	}

	private Sprint selectSprint(Plan model) {
		return model.getSprints().get((int) (Math.random() * model.getSprints().size()));
	}
	
	// Moves a sprint from parent to child
	// Create a sprint in child & move work items from parent sprint to child sprint?
	public void doCrossover(Plan parent1, Plan parent2, Plan child, int pivot) {
		// Clear sprints from child
		child.getSprints().clear();
		
		int n = 0;
		for (WorkItem workitem : child.getBacklog().getWorkitems()) {
			// Set child work item as unplanned
			workitem.setIsPlannedFor(null);
			
			if (n < pivot) {
				if (parent1.getSprints().size() > 0) {
					for (WorkItem p1_workitem : parent1.getSprints().get(0).getCommittedItem()) {
						if (workItemEquals(workitem, p1_workitem)) {
							workitem.setIsPlannedFor(child.getSprints().get(0));
							child.getSprints().get(0).getCommittedItem().add(workitem);
							break;
						}
					}
				}
			} else {
				if (parent2.getSprints().size() > 0) {
					for (WorkItem p2_workitem : parent2.getSprints().get(0).getCommittedItem()) {
						if (workItemEquals(workitem, p2_workitem)) {
							workitem.setIsPlannedFor(child.getSprints().get(0));
							child.getSprints().get(0).getCommittedItem().add(workitem);
							break;
						}
					}
				}
			}
			n++;
		}
	}
	
	// TODO: move this somewhere?
	private boolean workItemEquals(WorkItem wiA, WorkItem wiB) {
		return ((wiA.getEffort() == wiB.getEffort()) && 
				(wiA.getImportance() == wiB.getImportance()) && 
				(wiA.getStakeholder() == wiB.getStakeholder()));
	}
}