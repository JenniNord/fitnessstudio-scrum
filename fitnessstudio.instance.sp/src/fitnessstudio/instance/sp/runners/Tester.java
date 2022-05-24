package fitnessstudio.instance.sp.runners;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.NormalizedHypervolume;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

public class Tester {
	public static void main(String[] args) {
		
		try {
			String referenceParetoFront = "resource\\refA.csv";
			String referenceParetoFront2 = "resource\\refB.csv";
	
			Front referenceFront = new ArrayFront(referenceParetoFront);
		    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront);
		    
		    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront);
		    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(referenceParetoFront2));
		    List<PointSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
		    
		    Double res = new NormalizedHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
		    System.out.println("Hypervolume: " + res);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
