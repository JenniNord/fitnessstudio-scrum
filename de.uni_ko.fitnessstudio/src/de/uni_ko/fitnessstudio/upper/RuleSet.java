package de.uni_ko.fitnessstudio.upper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

import com.lagodiuk.Chromosome;


/**
 * Encapsulates a rule set as an individual during evolution.
 * @author strueber
 *
 */
public class RuleSet implements Chromosome<RuleSet> {
	Set<Unit> fixedRules;
	Set<Rule> genRules;
	ConstraintChecker checker;
	EPackage metaModel;
	
	private Map<String, Double> rulesWeight;

	public RuleSet(Set<Rule> genRules, Set<Unit> fixedRules, EPackage metaModel, ConstraintChecker checker, Map<String, Double> rulesWeight) {
		super();
		this.genRules = genRules;
		this.fixedRules = fixedRules;
		this.metaModel = metaModel;
		this.checker = checker;
		this.rulesWeight = rulesWeight;
	}

	public RuleSet(Rule rule, EPackage metaModel, ConstraintChecker checker, Map<String, Double> rulesWeight) {
		super();
		this.metaModel = metaModel;
		this.genRules = new HashSet<Rule>();
		this.checker = checker;
		this.rulesWeight = rulesWeight;
		genRules.add(rule);
	}


	@Override
	public RuleSet mutate() {
		return RuleSetMutator.mutate(this, metaModel);
	}
	
	public static EObject loadModel(String model) {
		ResourceSet resSet = new HenshinResourceSet();
		Resource resource = resSet.getResource(URI.createURI(model), true);
		if(resource == null) {
			System.err.println("Model can not be loaded!");
			return null;
		}
		return resource.getContents().get(0);
	}
	public static void registerPackage() {
//		ArchitectureCRAPackage.eINSTANCE.eClass();
		HenshinPackage.eINSTANCE.eClass();
		   
	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());
	}

	public Set<Unit> getAllRules() {
		Set<Unit> allRules = new HashSet<>(getGenRules());
		allRules.addAll(fixedRules);
		
		return allRules;
	}
	
	public Set<Unit> getFixedRules() {
		return fixedRules;
	}
	
	public Set<Rule> getGenRules() {
		return genRules;
	}
	
	@Override
	public String toString() {
		return genRules.toString();
	}

	@Override
	public List<RuleSet> crossover(RuleSet anotherChromosome) {
		return RuleSetCrossover.crossover(this, anotherChromosome);
	}


	public ConstraintChecker getConstraintChecker() {
		return checker;
	}

	public void setChecker(ConstraintChecker checker) {
		this.checker = checker;
	}

	public void setFixedRules(Set<Unit> fixedRules) {
		this.fixedRules = fixedRules;
	}
	
	public void setGenRules(Set<Rule> genRules) {
		this.genRules = genRules;
	}

	public EPackage getMetaModel() {
		return metaModel;
	}
	
	public Map<String, Double> getRulesWeight() {
		return rulesWeight;
	}
}
