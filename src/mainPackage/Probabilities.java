package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Probabilities {

	public static final double[][] probabilitiesMatrix = new double[32][32];
	// this is the probability matrix which will be used when assessing the risk for
	// attacking a certain territory
	// probabilitiesMatrix[x][y] signifies the probability of x troops to defeat y
	// troops

	public Probabilities() {

	}

	public void readProbabilitiesFromFile() {

		File myObj = new File("Utilities/probabilities");
		Scanner myReader;
		try {
			myReader = new Scanner(myObj);
			for (int i = 1; i <= 20; i++) {
				for (int j = 1; j <= 20; j++) {
					probabilitiesMatrix[i][j] = Double.parseDouble(myReader.next().replace('%', ' '));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printProbabilities() {
		System.out.println("-------------------------------------------------------");

		for (int i = 1; i <= 20; i++) {
			for (int j = 1; j <= 20; j++) {
				System.out.print(probabilitiesMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
