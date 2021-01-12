/**
 * 
 */

/**
 * @author dimitrispapakyriakopoylos
 *
 */
public class Results {

	
	static double max;
	static double[] aiPredictions;
	static double[][] targetData;
	static double[] error;
	
	
	public static void setResults(double[] aiPred, double[][] targetD, double[] er, double m) {
		aiPredictions = aiPred;
		targetData = targetD;
		error = er;
		max = m;
	}


	/**
	 * @return the aiPredictions
	 */
	public static double getAiPredictions(int i) {
		return aiPredictions[i];
	}


	/**
	 * @return the targetData
	 */
	public static double getTargetData(int i, int j) {
		return targetData[i][j];
	}


	/**
	 * @return the error
	 */
	public static double getError(int i) {
		return error[i];
	}
	
	
	
}
