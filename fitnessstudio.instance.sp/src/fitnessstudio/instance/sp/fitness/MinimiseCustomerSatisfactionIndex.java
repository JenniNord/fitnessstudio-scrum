package fitnessstudio.instance.sp.fitness;

import java.util.stream.DoubleStream;
import sp.model.sp.*;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.eclipse.emf.common.util.EList;

/**
 * https://github.com/mde-optimiser/mde_optimiser
 */
public class MinimiseCustomerSatisfactionIndex {

  public double computeFitness(Plan plan) {

	EList<Sprint> sprints = plan.getSprints();
    StandardDeviation standardDeviationCalculator = new StandardDeviation();

    double[] stakeholderImportanceSprintDeviation =
        plan.getStakeholders().stream()
            .map(
                stakeholder -> {
                  double[] effortAccrossSprints =
                      sprints.stream()
                          .map(
                              sprint ->
                                  sprint.getCommittedItem().stream()
                                      .filter(item -> item.getStakeholder().equals(stakeholder))
                                      .flatMapToDouble(
                                          item -> DoubleStream.of(item.getImportance()))
                                      .sum())
                          .mapToDouble(Double::doubleValue)
                          .toArray();

                  return standardDeviationCalculator.evaluate(effortAccrossSprints);
                })
            .mapToDouble(Double::doubleValue)
            .toArray();

    double importanceStandardDeviation = standardDeviationCalculator.evaluate(stakeholderImportanceSprintDeviation);

    // System.out.println("Sprint stakeholder importance distribution: " + stakeholderImportanceSprintDeviation);
    //System.out.println("Sprint Customer Satisfaction Index: " + importanceStandardDeviation);
    
    /*if (Double.isNaN(importanceStandardDeviation)) {
    	System.out.println("Sprints: " + plan.getSprints().size());
    }*/

    return importanceStandardDeviation;
  }

  public String getName() {

    return "Minimise Customer Satisfaction Index";
  }
}
