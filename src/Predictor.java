
/**
 * 
 */

/**
 * @author dimitrispapakyriakopoylos
 *
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Predictor {

	static double[][] covidData;
	static double[][] targetData;
	static double maxNewCases;
	static double maxTotalCases;
	static double maxReproductionRate;
	static double maxNewTests;
	static double maxTotalTests;
	static double maxTestsPerCase;

	static double nextDayPrediction;

	private JFrame frame;
	private AiPanel panel;
	private JLabel nextDay;

	public void processData(int x) throws IOException { // reads MS Excel Data
		FileInputStream fis = new FileInputStream(new File("/Users/dimitrispapakyriakopoylos/Documents/greece.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(x);
		covidData = new double[sheet.getLastRowNum() - 1][7];
		int i;
		System.out.println("Processing data...");
		for (i = 1; i <= sheet.getLastRowNum() - 1; i++) {
			covidData[i - 1][0] = (double) ReadCellDataN(i, 2);
			covidData[i - 1][1] = (double) ReadCellDataN(i, 3);
			covidData[i - 1][2] = (double) ReadCellDataN(i, 4);
			covidData[i - 1][3] = (double) ReadCellDataN(i, 5);
			covidData[i - 1][4] = (double) ReadCellDataN(i, 6);
			covidData[i - 1][5] = (double) ReadCellDataN(i, 7);
			covidData[i - 1][6] = (double) ReadCellDataN(i, 8);

		}
		targetData = new double[sheet.getLastRowNum() - 1][1];
		for (i = 2; i <= sheet.getLastRowNum(); i++) {
			targetData[i - 2][0] = (double) ReadCellDataN(i, 3);
		}
		System.out.println("Data have been processed!");
		System.out.println("Finding maxes...");
		findMax();
		for (i = 1; i <= sheet.getLastRowNum() - 1; i++) {
			covidData[i - 1][0] /= maxTotalCases;
			covidData[i - 1][1] /= maxNewCases;
			covidData[i - 1][2] /= maxReproductionRate;
			covidData[i - 1][3] /= maxNewTests;
			covidData[i - 1][4] /= maxTotalTests;
			covidData[i - 1][6] /= maxTestsPerCase;

		}
		for (i = 2; i <= sheet.getLastRowNum(); i++) {
			targetData[i - 2][0] = (double) ReadCellDataN(i, 3) / maxNewCases;
		}
		System.out.println("Data process complete!");
		wb.close();

	}

	public void findMax() throws IOException { // Finds the max number of cases and max number of cumulative cases in 14
												// days
												// and stores them in max1 and max2

		FileInputStream fis = new FileInputStream(new File("/Users/dimitrispapakyriakopoylos/Documents/greece.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		wb.close();
		maxNewCases = ReadCellDataN(1, 3);
		maxTotalCases = ReadCellDataN(1, 2);
		maxReproductionRate = ReadCellDataN(1, 4);
		maxNewTests = ReadCellDataN(1, 5);
		maxTotalTests = ReadCellDataN(1, 6);
		maxTestsPerCase = ReadCellDataN(1, 8);
		int i, j;
		double[] temp = new double[covidData.length];
		for (i = 0; i < 7; i++) {
			if (i != 7) {
				for (j = 0; j < covidData.length; j++) {
					temp[j] = covidData[j][i];
				}
				Arrays.sort(temp);
				switch (i) {

				case 0:
					maxTotalCases = temp[temp.length - 1];
					break;
				case 1:
					maxNewCases = temp[temp.length - 1];
					break;
				case 2:
					maxReproductionRate = temp[temp.length - 1];
					break;
				case 3:
					maxNewTests = temp[temp.length - 1];
					break;
				case 4:
					maxTotalTests = temp[temp.length - 1];
					break;
				case 6:
					maxTestsPerCase = temp[temp.length - 1];
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Predictor obj = new Predictor();
		try {
			obj.processData(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Training Neural Network...");
		NeuralNetwork ai = new NeuralNetwork(7, 16, 1);
		ai.fit(covidData, targetData, 5000000);
		System.out.println("Training was completed!");
		int i;
		double[] error = new double[covidData.length];
		double[] w = new double[covidData.length];
		for (i = 0; i < covidData.length; i++) {
			List<Double> output = ai.predict(covidData[i]);
			w[i] = output.get(0) * maxNewCases;
			targetData[i][0] *= maxNewCases;
			error[i] = (w[i] / targetData[i][0]) - 1;
		}
		covidData[covidData.length - 1][1] = targetData[covidData.length - 1][0];
		List<Double> output = ai.predict(covidData[covidData.length - 1]);
		nextDayPrediction = output.get(0) * maxNewCases;
		Results.setResults(w, targetData, error, maxNewCases);
		obj.makePage();

	}

	public void makePage() {

		// GUI frame and panel creation
		frame = new JFrame();
		frame.setSize(800, 800);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program ends when we close the window
		frame.setTitle("Covid-19 Prediction AI version 1 @2020");
		panel = new AiPanel();
		frame.add(panel);

		// Set the frame to be appeared always in the center of the screen, regardless
		// monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height - 900);

		nextDay = new JLabel("Prediction for tomorrow: " + Double.toString(nextDayPrediction));
		nextDay.setFont(new Font("Arial", Font.PLAIN, 14));
		nextDay.setForeground(Color.BLACK);
		nextDay.setBounds(500, 30, 250, 20);
		panel.add(nextDay);

		frame.setVisible(true);

	}

	public double ReadCellDataN(int vRow, int vColumn) throws IOException { // Numerical
		double value = 0; // variable for storing the cell value
		FileInputStream fis = new FileInputStream(new File("/Users/dimitrispapakyriakopoylos/Documents/greece.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(vRow); // returns the logical row
		Cell cell = row.getCell(vColumn); // getting the cell representing the given column
		value = cell.getNumericCellValue(); // getting cell value
		wb.close();
		return value; // returns the cell value
	}

}

