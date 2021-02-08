package mainPackage;

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
		
		GameGraph gameGraph = new GameGraph();
		// gameGraph.initDummyGraph();
	}

	public static void main(String[] args) {

		new Main();
	}
}
