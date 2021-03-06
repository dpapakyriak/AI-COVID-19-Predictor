
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
	static double maxTotalVacc;
	static double maxPeopleVaccinated;
	static double maxPeopleFullyVaccinated;
	static double maxNewVaccinations;
	static double maxStringencyIndex;

	static double nextDayPrediction;

	private JFrame frame;
	private AiPanel panel;
	private JLabel blue;
	private JLabel orange;
	

	public void processData(int x) throws IOException { // reads MS Excel Data
		FileInputStream fis = new FileInputStream(new File("/Users/dimitrispapakyriakopoylos/Documents/greece.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(x);
		covidData = new double[sheet.getLastRowNum() - 1][12];
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
			covidData[i - 1][7] = (double) ReadCellDataN(i, 9);
			covidData[i - 1][8] = (double) ReadCellDataN(i, 10);
			covidData[i - 1][9] = (double) ReadCellDataN(i, 11);
			covidData[i - 1][10] = (double) ReadCellDataN(i, 12);
			covidData[i - 1][11] = (double) ReadCellDataN(i, 13);

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
			covidData[i - 1][7] /= maxTotalVacc;
			covidData[i - 1][8] /= maxPeopleVaccinated;
			covidData[i - 1][9] /= maxPeopleFullyVaccinated;
			covidData[i - 1][10] /= maxNewVaccinations;
			covidData[i - 1][11] /= maxStringencyIndex;

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
		maxTotalVacc = ReadCellDataN(1, 9);
		maxPeopleVaccinated = ReadCellDataN(1, 10);
		maxPeopleFullyVaccinated = ReadCellDataN(1, 11);
		maxNewVaccinations = ReadCellDataN(1, 12);
		maxStringencyIndex = ReadCellDataN(1, 13);
		int i, j;
		double[] temp = new double[covidData.length];
		for (i = 0; i < 12; i++) {
			if (i != 5) {
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
				case 7:
						maxTotalVacc = temp[temp.length - 1];
						break;
				case 8:
					maxPeopleVaccinated = temp[temp.length - 1];
						break;
				case 9:
					maxPeopleFullyVaccinated = temp[temp.length - 1];
						break;
				case 10:
					maxNewVaccinations = temp[temp.length - 1];
						break;
				case 11:
					maxStringencyIndex = temp[temp.length - 1];
						break;
				}
			}
		}
		System.out.println("Max: " + maxNewCases);
		System.out.println("Maxes were found!");
	}

	public static void main(String[] args) {
		Predictor obj = new Predictor();
		try {
			obj.processData(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Training Neural Network...");
		NeuralNetwork ai = new NeuralNetwork(12, 16, 1); 	// Create the A.N.N.
		ai.fit(covidData, targetData, 5000000); 	//Train the A.N.N.
		System.out.println("Training was completed!");
		int i;
		double[] errors = new double[covidData.length];
		double[] w = new double[covidData.length];
		for (i = 0; i < covidData.length; i++) {
			List<Double> output = ai.predict(covidData[i]);
			w[i] = output.get(0) * maxNewCases;
			targetData[i][0] *= maxNewCases;
			errors[i] = Math.pow((w[i] - targetData[i][0]), 2);
		}
		Results.setResults(w, targetData, errors, maxNewCases);
		Results.showStats();
		obj.makePage();

	}

	public void makePage() {

		// GUI frame and panel creation
		frame = new JFrame();
		frame.setBounds(400, 0, 1000, 1000);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program ends when we close the window
		frame.setTitle("Covid-19 Prediction AI version 1 @2020");
		panel = new AiPanel();
		
		blue = new JLabel("Blue: Artificial Neural Network's predictions");
		blue.setBounds(130, 800, 300, 40);
		frame.add(blue);
		
		orange = new JLabel("Orange: Real Data");
		orange.setBounds(130, 850, 300, 40);
		frame.add(orange);
		
		frame.add(panel);

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

