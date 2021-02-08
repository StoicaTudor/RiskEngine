package mainPackage;

import java.util.HashMap;
import java.util.Map;

public class GameGraph {

	private HashMap<RegionName, GameRegion> graph = new HashMap<RegionName, GameRegion>();

	public GameGraph() {

		HashMap<Continent, Integer> continentsToConquer = new HashMap<Continent, Integer>();
		continentsToConquer.put(Continent.NORTH_AMERICA, 5);

		Mission mission = new Mission(continentsToConquer, null, 18);

		initMap();
		Position position = new Position(graph, Army.BLUE, Army.RED, 1, 50, null);
		Minimax minimax = new Minimax(3, Army.BLUE, Army.RED, mission);

		System.out.println(minimax.doMinimax(position, 3, Integer.MIN_VALUE, Integer.MIN_VALUE, Army.BLUE));

		for (Pair<RegionName, RegionName> currentDecision : position.bestNextPosition.scenarioThatLedToThisPosition) {
			System.out.println(currentDecision.getFirst() + "  ---->  " + currentDecision.getSecond());
		}
	}

	private void initMap() {

		GameInit gameInit = new GameInit();
		graph = gameInit.getMapGraph();
	}
}
