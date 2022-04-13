package fitnessstudio.instance.sp.runners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.NormalizedHypervolume;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

public class SpreadCalculator {
	
	private static Double getSpread(String paretoFrontFile, String populationFile) throws FileNotFoundException {
		Front referenceFront = new ArrayFront(paretoFrontFile);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(populationFile)) ;
	    List<PointSolution> normalizedPopulation = FrontUtils
	        .convertFrontToSolutionList(normalizedFront) ;
		
		return new Spread<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
	}
	
	private static Double getHypervolume(String paretoFrontFile, String populationFile) throws FileNotFoundException {
		Front referenceFront = new ArrayFront(paretoFrontFile);
	    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

	    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
	    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(populationFile)) ;
	    List<PointSolution> normalizedPopulation = FrontUtils
	        .convertFrontToSolutionList(normalizedFront) ;
		
		return new NormalizedHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
	}
	
	private static void createLogEntry(String line, double spread, final String OUTPUT_PREFIX) {
		String lineWithSpread = line + " \t " + spread + "\n";
		String path = OUTPUT_PREFIX + "logWithSpread.txt";
	
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
			bw.write(lineWithSpread);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<String> initMethods = Arrays.asList("complete", "empty", "extremes", "rand+x", "random");
		List<String> models = Arrays.asList("A", "B", "C", "D", "E");
		
		for (String initMethod : initMethods) {
			for (String model : models) {
				final String OUTPUT_PREFIX = "output_models\\" + model + "\\" + initMethod + "-fixedXORgen\\"; // either -fixed or -fixedXORgen
				String paretoFrontFile = "resource\\ref" + model + ".csv";
				
				try (BufferedReader br = new BufferedReader(new FileReader(OUTPUT_PREFIX + "log.txt"))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				    	String i = line.split(" ")[0];
						String populationFile = OUTPUT_PREFIX + i + "FUN.csv"; 
						Double spread = getSpread(paretoFrontFile, populationFile);
						
						// Safety check, is calculated hypervolume same as hypervolume from log.txt
						Double hypervolumeLog = Double.valueOf(line.substring(line.lastIndexOf(" ")+1));
						Double hypervolumeCalc = getHypervolume(paretoFrontFile, populationFile);
						
						if (Math.abs(hypervolumeLog - hypervolumeCalc) > 0.0000000001) {
							System.out.println("Log: "+hypervolumeLog+", Calc: "+hypervolumeCalc);
						}
						
						createLogEntry(line, spread, OUTPUT_PREFIX);
				    }
				}
			}
		}
		System.out.println("Execution success");
	}
}
