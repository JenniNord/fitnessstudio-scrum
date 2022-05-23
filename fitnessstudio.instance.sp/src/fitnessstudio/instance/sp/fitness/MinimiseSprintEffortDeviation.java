package fitnessstudio.instance.sp.fitness;

import java.util.stream.DoubleStream;
import sp.model.sp.*;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

/**
 * https://github.com/mde-optimiser/mde_optimiser
 */
public class MinimiseSprintEffortDeviation {

  public double computeFitness(Plan plan) {

    double[] fitness =
        plan.getSprints().stream()
            .map(
                sprint ->
                    sprint.getCommittedItem().stream()
                        .flatMapToDouble(x -> DoubleStream.of(x.getEffort()))
                        .sum())
            .mapToDouble(Double::doubleValue)
            .toArray();

    double effortStandardDeviation = new StandardDeviation().evaluate(fitness);

    // System.out.println("Sprint effort distribution: " + fitness);
    //System.out.println("Sprint effort standard deviation: " + effortStandardDeviation);
    
    /*if (Double.isNaN(effortStandardDeviation)) {
    	System.out.println("Sprints: " + plan.getSprints().size());
    }*/

    return effortStandardDeviation;
  }

  public String getName() {
    return "Maximise average sprint effort";
  }
}
