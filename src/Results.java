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
	
	public static void getMeanError() {
		double sum = 0;
		int i;
		for (i = 0; i < error.length; i++) {
			sum += error[i];
		}
		double mer = (double) (sum / (error.length - 2));
		System.out.println(mer);
	}
	
	
	public static double getYbar() {
		double sum = 0;
		int i;
		for (i = 0; i < targetData.length; i++) {
			sum += targetData[i][0];
		}
		double yBar = (double) (sum / targetData.length);
		return yBar;
	}
	
	public static double getSST() {
		double yBar = getYbar();
		double sum = 0;
		int i;
		for (i = 0; i < targetData.length; i++) {
			sum += Math.pow(targetData[i][0] - yBar, 2);
		}
		return sum;
	}
	
	public static double getSSR() {
		double yBar = getYbar();
		double ssr = 0;
		int i;
		for (i = 0; i < aiPredictions.length; i++) {
			ssr += Math.pow(aiPredictions[i] - yBar, 2);
		}
		return ssr;
	}
	
	public static double getRsquared() {
		double r2 = (double) (getSSR() / getSST());
		return r2;
	}
	
	public static void showStats() {
		System.out.println("The R^2 is equal to:\t" + getRsquared());
	}
	
}
