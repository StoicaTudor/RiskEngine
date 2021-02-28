package mainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameGraph {

	private HashMap<RegionName, GameRegion> graph = new HashMap<RegionName, GameRegion>();
	private Utility utility = new Utility();

	private Army myArmy;
	private ArrayList<Army> enemyArmies = new ArrayList<Army>();

	private ArrayList<ArrayList<Pair<RegionName, RegionName>>> scenariosMatrix = new ArrayList<ArrayList<Pair<RegionName, RegionName>>>();

	public GameGraph(Army myArmy, ArrayList<Army> enemyArmies) {

		this.myArmy = myArmy;
		this.enemyArmies = enemyArmies;

		initMap();

		computeTheBestScenario();

		HashMap<Army, Army> armiesOrder = new HashMap<Army, Army>();
		armiesOrder.put(Army.BLUE, Army.RED);
		armiesOrder.put(Army.RED, Army.YELLOW);
		armiesOrder.put(Army.YELLOW, Army.BLUE);

		new Game(myArmy, enemyArmies, armiesOrder, graph, Army.BLUE);

		System.gc();
	}

	private void computeTheBestScenario() {

		HashMap<Continent, Integer> continentsToConquer = new HashMap<Continent, Integer>();
		continentsToConquer.put(Continent.NORTH_AMERICA, 5);

		Mission mission = new Mission(continentsToConquer, null, 18);

		for (Army currentEnemyArmy : enemyArmies) {
			attackFromTo(myArmy, currentEnemyArmy, mission);
		}

		Pair<RegionName, RegionName> bestAttackingScenario = computeTheMostCommonScenario();
		System.out.println(bestAttackingScenario.getFirst() + " ----> " + bestAttackingScenario.getSecond());
	}

	private Pair<RegionName, RegionName> computeTheMostCommonScenario() {

		int[] frequenceArray = new int[graph.size() * graph.size()];
		Pair<RegionName, RegionName> bestAttackingScenario = null;
		int maximum = -1;

		for (int i = 0; i < graph.size() * graph.size(); i++) {
			frequenceArray[i] = 0;
		} // initialize the array with -1

		for (ArrayList<Pair<RegionName, RegionName>> currentScenariosList : scenariosMatrix) {

			for (Pair<RegionName, RegionName> currentScenarioAttack : currentScenariosList) {

				RegionName from = currentScenarioAttack.getFirst();
				RegionName to = currentScenarioAttack.getSecond();

				frequenceArray[currentScenarioAttack.hashCode() % frequenceArray.length]++;

				if (frequenceArray[currentScenarioAttack.hashCode() % frequenceArray.length] > maximum) {

					maximum = frequenceArray[currentScenarioAttack.hashCode() % frequenceArray.length];
					bestAttackingScenario = currentScenarioAttack;
				}
			}
		}

		System.gc();
		return bestAttackingScenario;
	}

	private void attackFromTo(Army attackingArmy, Army defendingArmy, Mission mission) {

		// printGraph(this.graph);

		HashMap<RegionName, GameRegion> clonedGraph = utility.getGraphHashMapClone(graph);

		Position position = new Position(clonedGraph, attackingArmy, defendingArmy, 1, 50, null);
		Minimax minimax = new Minimax(3, attackingArmy, defendingArmy, mission);

		System.out.println(minimax.doMinimax(position, 3, Integer.MIN_VALUE, Integer.MIN_VALUE, attackingArmy));

		for (Pair<RegionName, RegionName> currentDecision : position.bestNextPosition.scenarioThatLedToThisPosition) {

			System.out.println(currentDecision.getFirst() + "  ---->  " + currentDecision.getSecond());
		}
		System.out.println(position.gameRegionsOccupiedByPlayer.size());
		System.out.println("--------------------------------");

		scenariosMatrix.add(position.bestNextPosition.scenarioThatLedToThisPosition);

		System.gc();
	}

	private void printGraph(HashMap<RegionName, GameRegion> graphClone) {

		for (GameRegion currentRegion : graphClone.values()) {
			System.out.println(
					currentRegion.regionName + " -----> " + currentRegion.occupyingArmy + " " + currentRegion.nrUnits);
		}
		System.out.println();
	}

	private void initMap() {

		GameInit gameInit = new GameInit();
		graph = gameInit.getGraph();
	}
}
