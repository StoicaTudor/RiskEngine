package mainPackage;

import java.util.ArrayList;

public class Main {

	public Main() {

		Menu menu = new Menu();
		menu.getNrPlayersSelected();
		menu.getPlayersColors();
		menu.getAIColors();
		menu.getDifficulty();
		// to implement

		Probabilities probabilities = new Probabilities();
		probabilities.readProbabilitiesFromFile();

		ArrayList<Army> enemyArmies = new ArrayList<>();
		enemyArmies.add(Army.RED);
		enemyArmies.add(Army.YELLOW);
		
		GameGraph gameGraph = new GameGraph(Army.BLUE, enemyArmies);
	}

	public static void main(String[] args) {

		new Main();
	}
}
