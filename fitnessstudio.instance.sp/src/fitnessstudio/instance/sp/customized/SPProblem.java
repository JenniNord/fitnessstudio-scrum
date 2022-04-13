package fitnessstudio.instance.sp.customized;

import org.uma.jmetal.util.checking.Check;

import de.uni_ko.fitnessstudio.lower.DomainModelProblem;
import de.uni_ko.fitnessstudio.lower.DomainModelSolution;
import fitnessstudio.instance.sp.fitness.MinimiseSprintEffortDeviation;
import fitnessstudio.instance.sp.fitness.MinimiseCustomerSatisfactionIndex;
import fitnessstudio.instance.sp.fitness.HasNoUnassignedWorkItems;
import fitnessstudio.instance.sp.fitness.HasTheAllowedMaximalNumberOfSprints;
import sp.model.sp.Plan;

@SuppressWarnings("serial")
public class SPProblem extends DomainModelProblem<Plan> {
	// Changed these
	private final MinimiseCustomerSatisfactionIndex satisfaction;
	private final MinimiseSprintEffortDeviation effort;
	private final HasNoUnassignedWorkItems assigned;
	private final HasTheAllowedMaximalNumberOfSprints sprints;
	
	public SPProblem(String inputModelId) {
		// nrVariables, nrObjectives, nrConstraints
		super(1, 2, 2, inputModelId); // added constraints
		
		this.satisfaction = new MinimiseCustomerSatisfactionIndex();
		this.effort = new MinimiseSprintEffortDeviation();
		this.assigned = new HasNoUnassignedWorkItems();
		this.sprints = new HasTheAllowedMaximalNumberOfSprints();
	}

	@Override
	public void evaluate(DomainModelSolution<Plan> solution) {
		Check.isNotNull(solution);
		// TODO: get rid of magic numbers
	    Check.that(getNumberOfObjectives() == 2, "There must be 2 objectives instead of " + getNumberOfObjectives());
	    Check.that(getNumberOfConstraints() == 2, "There must be 2 constraints instead of " + getNumberOfConstraints());
	    
	    // negative/positive? changed these
		solution.setObjective(0, satisfaction.computeFitness(solution.getVariable(0)));
		solution.setObjective(1, effort.computeFitness(solution.getVariable(0)));
		
		// TODO: add NumberOfViolatedConstraints?
		solution.setConstraint(0, -assigned.computeFitness(solution.getVariable(0)));
		solution.setConstraint(1, sprints.computeFitness(solution.getVariable(0)));
	}

	@Override
	public DomainModelSolution<Plan> createSolution() {
		// added number of constraints
		return new SPSolution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints(), INPUT_MODEL_ID);
	}
}
