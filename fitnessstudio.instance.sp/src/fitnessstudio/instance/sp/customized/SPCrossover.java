package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.Iterator;
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
	    
	    //crossOver(parents, child1);
	    //crossOver(parents, child2);
	    
	    int backlog = parents.get(0).getVariable(0).getBacklog().getWorkitems().size();

	    // Single Point Crossover
	    int pivot = generator.nextInt(backlog);
	    if (!(parents.get(0).getVariable(0).getSprints().isEmpty() && 
	    		parents.get(1).getVariable(0).getSprints().isEmpty())) {
	    	if (!(parents.get(0).getVariable(0).getSprints().get(0).getCommittedItem().isEmpty() && 
		    		parents.get(1).getVariable(0).getSprints().get(0).getCommittedItem().isEmpty())) {
			    doCrossover(parents.get(0).getVariable(0), parents.get(1).getVariable(0), child1.getVariable(0), pivot);
			    doCrossover(parents.get(1).getVariable(0), parents.get(0).getVariable(0), child2.getVariable(0), pivot);
	    	}
	    }
	    
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
	
	public WorkItem createWorkItem(WorkItem w) {
		WorkItem copy = SPFactory.eINSTANCE.createWorkItem();
		copy.setEffort(w.getEffort());
		copy.setImportance(w.getImportance());
		copy.setStakeholder(w.getStakeholder());
		return copy;
	}
	
	private void moveSelectedSprint(Plan dominant, Plan recessive, Plan child) {
		// Remove all sprints without work items from dominant parent
		dominant.getSprints().removeAll(
				dominant.getSprints().stream().filter(s -> s.getCommittedItem().isEmpty()).collect(Collectors.toSet()));
		
		// If dominant parent has sprints (with work items)
		if (!dominant.getSprints().isEmpty()) {
			// Select random sprint from dominant parent
			Sprint selected = selectSprint(dominant);
			// Create new sprint for child
			Sprint selected_ = SPFactory.eINSTANCE.createSprint();
			// Add new sprint to child
			child.getSprints().add(selected_);
			
			// Select all committed work items in selected sprint
			//EList<WorkItem> committed = selected.getCommittedItem();
			
			// Add work items from selected sprint to new child sprint
			// For each work item in child backlog
			for (WorkItem w : child.getBacklog().getWorkitems()) {
				for (WorkItem wi : selected.getCommittedItem()) {
					// if the work item exists in the committed items (to the selected sprint)
					if (workItemEquals(w, wi)) {
						// add work item to new sprint
						w.setIsPlannedFor(selected_);
						selected_.getCommittedItem().add(w);
					}
					
					for (WorkItem wii : recessive.getBacklog().getWorkitems()) {
						// if the work item exists in the committed items (to the selected sprint)
						if (workItemEquals(wi, wii)) {
							// remove work item from its sprint
							Sprint s = w.getIsPlannedFor();
							wii.setIsPlannedFor(null);
							s.getCommittedItem().remove(wii);
						}
					}
				}	
			}
			// Remove selected sprint work items from recessive parent sprints
			// For each work item in the recessive parents backlog
			/*for (WorkItem w : recessive.getBacklog().getWorkitems()) {
				
				//Concurrent modification exception here!
				for (WorkItem wi : selected.getCommittedItem()) {
					// if the work item exists in the committed items (to the selected sprint)
					if (workItemEquals(w, wi)) {
						// remove work item from its sprint
						//Sprint s = w.getIsPlannedFor();
						w.setIsPlannedFor(null);
						//s.getCommittedItem().remove(w);
					}
				}
			}*/
			// Remove selected sprint from dominant parent
			dominant.getSprints().remove(selected);
		}
		// Remove empty sprints from recessive parent
		recessive.getSprints().removeAll(
				recessive.getSprints().stream().filter(s -> s.getCommittedItem().isEmpty()).collect(Collectors.toSet()));
	}

	private Sprint selectSprint(Plan plan) {
		return plan.getSprints().get((int) (Math.random() * plan.getSprints().size()));
	}
	
	// Moves a sprint from parent to child
	// Create a sprint in child & move work items from parent sprint to child sprint?
	public void doCrossover(Plan parent1, Plan parent2, Plan child, int pivot) {
		// Clear clear the first sprint from child
		child.getSprints().get(0).getCommittedItem().clear();
		
		int n = 0;
		for (WorkItem workitem : child.getBacklog().getWorkitems()) {
			// Set child work item as unplanned
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
	
	// TODO: move this somewhere?
	private boolean workItemEquals(WorkItem wiA, WorkItem wiB) {
		return ((wiA.getEffort() == wiB.getEffort()) && 
				(wiA.getImportance() == wiB.getImportance()) && 
				(wiA.getStakeholder() == wiB.getStakeholder()));
	}
}