package fitnessstudio.instance.sp.customized;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
	    
	    List<SPSolution> copied_parents1 = new ArrayList<SPSolution>();
	    List<SPSolution> copied_parents2 = new ArrayList<SPSolution>();
	    SPSolution parent1 = (SPSolution) parents.get(0).copy();
		SPSolution parent2 = (SPSolution) parents.get(1).copy();
		SPSolution parent3 = (SPSolution) parents.get(0).copy();
		SPSolution parent4 = (SPSolution) parents.get(1).copy();
		copied_parents1.add(parent1);
		copied_parents1.add(parent2);
		copied_parents2.add(parent3);
		copied_parents2.add(parent4);
	    
		List<SPSolution> offspring = new ArrayList<>(2);
		SPSolution child1 = (SPSolution) parents.get(0).copy();
		SPSolution child2 = (SPSolution) parents.get(1).copy();
		
		offspring.add(child1);
	    offspring.add(child2);
		
	    if (Math.random() > crossoverProbability)
	    	return offspring;
	    
	    crossOver(copied_parents1, offspring.get(0));
	    crossOver(copied_parents2, offspring.get(1));

	   /* int backlog = parents.get(0).getVariable(0).getBacklog().getWorkitems().size();

	    // Single Point Crossover
	    int pivot = generator.nextInt(backlog);
	    if (!parents.get(0).getVariable(0).getSprints().isEmpty() && 
	    		!parents.get(1).getVariable(0).getSprints().isEmpty()) {
	    		if (!(parents.get(0).getVariable(0).getSprints().get(0).getCommittedItem().isEmpty() && 
			    		parents.get(1).getVariable(0).getSprints().get(0).getCommittedItem().isEmpty())) {
	    			doCrossover(parents.get(0).getVariable(0), parents.get(1).getVariable(0), child1.getVariable(0), pivot);
	    			doCrossover(parents.get(1).getVariable(0), parents.get(0).getVariable(0), child2.getVariable(0), pivot);
	    		}
	    }*/
	    
	    /*System.out.println("Child 1 wi: " + 
	    offspring.get(0).getVariable(0).getBacklog().getWorkitems().stream()
	    .filter(w -> w.getIsPlannedFor() == null).collect(Collectors.toList()).size());
	    System.out.println("Child 2 wi: " + 
	    offspring.get(1).getVariable(0).getBacklog().getWorkitems().stream()
	    .filter(w -> w.getIsPlannedFor() == null).collect(Collectors.toList()).size());*/
	    /*System.out.println("Parent 0 sprints: " + parents.get(0).getVariable(0).getSprints().size());
	    System.out.println("Parent 0 sprints: " + parents.get(1).getVariable(0).getSprints().size());
	    System.out.println("Parent 0 copy sprints: " + copied_parents1.get(0).getVariable(0).getSprints().size());
	    System.out.println("Parent 1 copy sprints: " + copied_parents1.get(1).getVariable(0).getSprints().size());
	    System.out.println("Child 0 sprints: " + offspring.get(0).getVariable(0).getSprints().size());*/
	    //System.out.println("Child 2 sprints: " + offspring.get(1).getVariable(0).getSprints().size());
	    //System.out.println("Child 1 sprints: " + offspring.get(0).getVariable(0).getBacklog().getWorkitems().size());
	    //System.out.println("Child 2 sprints: " + offspring.get(1).getVariable(0).getBacklog().getWorkitems().size());
	    return offspring;
	}
	
	private void crossOver(List<SPSolution> parents, SPSolution child) {
		child.getVariable(0).getSprints().clear();
	    child.getVariable(0).getBacklog().getWorkitems().stream().forEach(w -> w.setIsPlannedFor(null));
	    
		boolean turn = false;
		// while both parents have sprints
	    while (!(parents.get(0).getVariable(0).getSprints().isEmpty() && parents.get(1).getVariable(0).getSprints().isEmpty())) {
	    	// if turn & parent 0 has sprints
	    	if (turn && !parents.get(0).getVariable(0).getSprints().isEmpty()) {
	    		moveSelectedSprint(parents.get(0).getVariable(0), parents.get(1).getVariable(0), child.getVariable(0));
	    		
	    		// if !turn & parents 1 has sprints
	    	} else if (!turn && !parents.get(1).getVariable(0).getSprints().isEmpty()) {
	    		moveSelectedSprint(parents.get(1).getVariable(0), parents.get(0).getVariable(0), child.getVariable(0));
	    	}
	    	turn = !turn;
	    }
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
			// Add new sprint to child plan
			child.getSprints().add(selected_);
			
			// Get backlogs
			EList<WorkItem> backlog = dominant.getBacklog().getWorkitems();
			EList<WorkItem> recessive_backlog = recessive.getBacklog().getWorkitems();
			EList<WorkItem> child_backlog = child.getBacklog().getWorkitems();
			
			// Loop through dominant backlog
			for (int i = 0; i < backlog.size(); i++) {
				// If the work item is in the committed items in the selected sprint
				if (selected.getCommittedItem().contains(backlog.get(i))) {
					// Add to new sprint in child
					WorkItem wi = child_backlog.get(i);
					wi.setIsPlannedFor(selected_);
					selected_.getCommittedItem().add(wi);
					
					// Remove work item from recessive sprints
					WorkItem rwi = recessive_backlog.get(i);
					Sprint s = rwi.getIsPlannedFor();
					if (s != null) {
						rwi.setIsPlannedFor(null);
						s.getCommittedItem().remove(rwi);
					}
				}
			}
			
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

	public void doCrossover(Plan parent1, Plan parent2, Plan child, int pivot) {
		// Clear the child's first sprint from work items
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
	
	private boolean workItemEquals(WorkItem wiA, WorkItem wiB) {
		return ((wiA.getEffort() == wiB.getEffort()) && 
				(wiA.getImportance() == wiB.getImportance()));
	}
}