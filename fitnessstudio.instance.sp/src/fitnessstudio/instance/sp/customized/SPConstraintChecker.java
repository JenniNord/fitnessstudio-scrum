package fitnessstudio.instance.sp.customized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.uni_ko.fitnessstudio.upper.ConstraintChecker;
import sp.model.sp.SPPackage;

public class SPConstraintChecker implements ConstraintChecker {
	
	private static EClass[] fC = { SPPackage.eINSTANCE.getPlan(), 
			SPPackage.eINSTANCE.getStakeholder(), 
			SPPackage.eINSTANCE.getBacklog()};
	private static Set<EClass> fixedClasses = new HashSet<EClass>(Arrays.asList(fC));

	public boolean satisfiesMutationConstraints(Collection<Rule> content) {
		for (Rule rule : content) {
			if (!satisfiesMutationConstraints(rule))
				return false;
		}

		return true;
	}

	public boolean satisfiesMutationConstraints(Rule rule) {
		if (creationOrDeletionViolatesConstraints(rule))
			return false;

		if (!satisfiesMutationConstraints(rule.getMultiRules())) {
			return false;
		}
		return true;
	}

	private static boolean creationOrDeletionViolatesConstraints(Rule rule) {
		Set<Node> deletionNodes = new HashSet<Node>(rule.getLhs().getNodes());
		Set<Node> creationNodes = new HashSet<Node>(rule.getRhs().getNodes());
		Map<Node, Node> preservedNodesLhs2Rhs = new HashMap<Node, Node>();
		Map<Node, Node> preservedNodesRhs2Lhs = new HashMap<Node, Node>();
		for (Mapping m : rule.getMappings()) {
			deletionNodes.remove(m.getOrigin());
			creationNodes.remove(m.getImage());
			preservedNodesLhs2Rhs.put(m.getOrigin(), m.getImage());
			preservedNodesRhs2Lhs.put(m.getImage(), m.getOrigin());
		}
		
		for (Node n : deletionNodes) {
			if (fixedClasses.contains(n.getType())) {
				System.out.println("Violates SPConstraintChecker");
				return true;
			}
		}
		for (Node n : creationNodes) {
			if (fixedClasses.contains(n.getType())) {
				System.out.println("Violates SPConstraintChecker");
				return true;
			}
		}
		
		// May not delete node other than WorkItems/Sprints and/or Sprints/Plan
		if (createOrDeleteEdgesViolateConstraints(deletionNodes, preservedNodesLhs2Rhs))
			return true;

		// May not create node other than WorkItems/Sprints and/or Sprints/Plan
		if (createOrDeleteEdgesViolateConstraints(creationNodes, preservedNodesRhs2Lhs))
			return true;

		return false;
	}
	
	private static boolean createOrDeleteEdgesViolateConstraints(Set<Node> nodes, Map<Node, Node> graph2graph) {
		// An edge is <<delete>> or <<create>>:
		// If its source and target nodes, x1 and x2, are
		// <<preserve>>, but the edge itself, e, has no
		// counterpart between the source and target node counterparts,
		// y1 and y2
		
		for (Node x1 : graph2graph.keySet()) {
			for (Edge e : x1.getOutgoing()) {
				Node x2 = e.getTarget();
				Node y1 = graph2graph.get(x1);
				Node y2 = graph2graph.get(x2);
				if (y1 != null && y2 != null && y1.getOutgoing(e.getType(), y2) == null)
					// Allowed edges: plan_sprint, sprint_wi, wi_sprint
					if (e.getType() != SPPackage.eINSTANCE.getSprint_CommittedItem() && 
						e.getType() != SPPackage.eINSTANCE.getWorkItem_IsPlannedFor() && 
						e.getType() != SPPackage.eINSTANCE.getPlan_Sprints()) {
						System.out.println("Violates SPConstraintChecker");
						return true;
					}
			}
		}
		
		return false;
	}

	public boolean satisfiesWellformednessConstraint(EObject model) {
		return true;
	}
	
}
