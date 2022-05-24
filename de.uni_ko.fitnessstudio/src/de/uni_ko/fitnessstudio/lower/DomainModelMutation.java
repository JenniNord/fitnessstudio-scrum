package de.uni_ko.fitnessstudio.lower;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.util.checking.Check;

import de.uni_ko.fitnessstudio.util.EngineFactory;

@SuppressWarnings("serial")
public class DomainModelMutation<S> implements MutationOperator<DomainModelSolution<S>> {
	
	Engine engine = EngineFactory.createEngine();
	Set<Rule> genRules;
	Set<Unit> fixedRules;
	
	private double mutationProbability;
	
	/** Constructor */
	public DomainModelMutation(Set<Rule> genRules, double probability) {
		this.genRules = genRules;
		this.fixedRules = new HashSet<>();
		
		this.mutationProbability = probability;
	}
	
	/** Constructor */
	public DomainModelMutation(Set<Rule> genRules, Set<Unit> fixedRules, double probability) {
		this.genRules = genRules;
		this.fixedRules = fixedRules;
		
		this.mutationProbability = probability;
	}
	
	@Override
	public DomainModelSolution<S> execute(DomainModelSolution<S> solution) {
		Check.isNotNull(solution);
		
		EGraph graph = new EGraphImpl((EObject) solution.getVariable(0));
		
		if (fixedRules.isEmpty()) {
			mutateWithGenRules(graph);
		} else {
			mutateWithFixedRules(graph);
		}
		
		graph.clear();
		return solution;
	}
	
	private void mutateWithFixedRules(EGraph graph) {
		for (Unit unit : fixedRules) {
			if (Math.random() < mutationProbability) {
				// Used by lower tier runner
				UnitApplication app = new UnitApplicationImpl(engine, graph, unit, null);
				app.execute(null);
			}
		}
	}
	
	private void mutateWithGenRules(EGraph graph) {
		for (Rule rule : genRules) {
			if (Math.random() < mutationProbability) {
				// Used by upper tier runner
				RuleApplication ruleApp = new RuleApplicationImpl(engine, graph, rule, null);
				ruleApp.execute(null);
			}
		}
	}

	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}


}
