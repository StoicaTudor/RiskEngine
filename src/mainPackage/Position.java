package mainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mainPackage.Evaluation.EvaluationCriterias;

public class Position {

	private Evaluation evaluation = new Evaluation();
	public HashMap<RegionName, GameRegion> graph; // make it private
	public ArrayList<GameRegion> gameRegionsOccupiedByPlayer = new ArrayList<GameRegion>();
	public final Army playerToAnalyze;// make it private
	public final Army enemyPlayer;// make it private
	private ArrayList<Position> childrenOfPosition = new ArrayList<>();
	private double probabilityToGetInThisPosition = 1;
	private final int minimumRiskToTake;

	public Position bestNextPosition;
	public final ArrayList<Pair<RegionName, RegionName>> scenarioThatLedToThisPosition;
	// this variable is used for extracting the best decision which must be made,
	// after the doMinimax() method has been called
	// doMinimax will modify its Position position parameter, by assigning a
	// Position bestNextPosition class variable to it
	// now, bestNextPosition variable will also have a class variable private final
	// ArrayList<Pair<RegionName, RegionName>> scenarioThatLedToThisPosition; ->
	// which is by the way this variable,
	// which will indicate to us what series of decisions (attacking scenario) has
	// led to this best position

	private Random rand = new Random();

	private ArrayList<ArrayList<Pair<RegionName, RegionName>>> attackingScenarios = new ArrayList<ArrayList<Pair<RegionName, RegionName>>>();

	public Position(HashMap<RegionName, GameRegion> graph, Army playerToAnalyze, Army enemyPlayer,
			double probabilityToGetInThisPosition, int minimumRiskToTake,
			ArrayList<Pair<RegionName, RegionName>> scenarioThatLedToThisPosition) {

		this.graph = graph;
		this.playerToAnalyze = playerToAnalyze;
		this.enemyPlayer = enemyPlayer;
		this.probabilityToGetInThisPosition = probabilityToGetInThisPosition;
		initGameRegionsOccupiedByPlayer();
		this.scenarioThatLedToThisPosition = scenarioThatLedToThisPosition;
		this.minimumRiskToTake = minimumRiskToTake;
	}

	private void initGameRegionsOccupiedByPlayer() {

		// iterate all graph regions
		for (GameRegion currentRegion : graph.values()) {
			// if the current region belongs to the player which is going to be analyzed
			if (currentRegion.occupyingArmy == playerToAnalyze) {
				gameRegionsOccupiedByPlayer.add(currentRegion);
				// add it in the array
			}
		}
		// System.out.println(this.playerToAnalyze + " has " +
		// this.gameRegionsOccupiedByPlayer.size() + " territories");
	}

	public double evaluatePosition(Mission mission) {
		/*
		 * kill a unit = 1; lose a unit = -1; need that continet = 3; eliminate threat
		 * against large neighbour enemy = 5; eliminate threat against own controlled
		 * continent = 4; left with 0 or 1 pieces = -oo; neighbour with a bigger enemy
		 * army = -5; letting back vulnerable pieces sourrounded by many enemies = -3;
		 */

		double evaluation = 0;
		int totalNrPlayerUnits = 0;

		for (GameRegion currentGameRegion : this.graph.values()) {

			if (this.graph.get(currentGameRegion.regionName).occupyingArmy == this.playerToAnalyze) {

				totalNrPlayerUnits += this.graph.get(currentGameRegion.regionName).nrUnits
						* this.evaluation.getEvaluationCriteria(EvaluationCriterias.HOLDING_1_UNIT);
				// this variable is used to check the total nr of units the AI controls

				evaluation += this.graph.get(currentGameRegion.regionName).nrUnits
						* this.evaluation.getEvaluationCriteria(EvaluationCriterias.ENEMY_HOLDING_1_UNIT);
				// add to evaluation the number of troops on that territory

				if (mission.getContinentsToConquer()
						.containsKey(this.graph.get(currentGameRegion.regionName).continent)) {
					evaluation += this.evaluation.getEvaluationCriteria(EvaluationCriterias.NEED_THAT_CONTINENT);
				} // if the territory is on a continent which AI is supossed to conquer

				// TODO - implement for
				// NO_BIG_THREATS_IN_NEIGHBOURHOOD
				// NO_BIG_THREATS_AT_BORDER
				// BIG_THREATS_IN_NEIGHBOURHOOD
			}
			// if territory is controlled by AI
			else {
				evaluation -= this.graph.get(currentGameRegion.regionName).nrUnits;
			}
			// if territory is controlled by an enemy

		} // iterate all territories on the map

		if (totalNrPlayerUnits <= 1) {
			evaluation = this.evaluation.getEvaluationCriteria(EvaluationCriterias.LEFT_WITH_0_UNITS);
			// evaluation += 0;
		}

		return evaluation;
	}

	public void computeChildrenOfPosition() {

		this.backtrackAttackingScenarios();
		// first backtrack attacking scenarios

		for (ArrayList<Pair<RegionName, RegionName>> currentAttackingScenario : this.attackingScenarios) {
			// for every attacking scenario we have backtracked

			HashMap<RegionName, GameRegion> newGraph = (HashMap<RegionName, GameRegion>) this.graph.clone();
			// copy the graph, since we will create an object of type Position, which
			// signifies a hipotetical next scenario, for this currentAttackingScenario

			ArrayList<GameRegion> newGameRegionsOccupiedByPlayer = (ArrayList<GameRegion>) this.gameRegionsOccupiedByPlayer
					.clone();
			// copy the array, since we will create an object of type Position, which
			// signifies a hipotetical next scenario, for this currentAttackingScenario

			int probabilityToGetInThisPosition = 1;

			for (Pair<RegionName, RegionName> currentAttack : currentAttackingScenario) {
				// for every attack from the attacking scenario

				// if the attack is from A to A, it means that it is a PAS attack, which means
				// that nothing will happen
				if (currentAttack.getFirst() == currentAttack.getSecond()) {
					// so we continue the loop
					continue;
				}

				// check if the probability of victory is bigger than the risk the AI is willing
				// to take
				if (Probabilities.probabilitiesMatrix[newGraph.get(currentAttack.getFirst()).nrUnits][newGraph
						.get(currentAttack.getSecond()).nrUnits] >= this.minimumRiskToTake) {
					// always suppose that the AI wins, since this is the reason why it engaged in
					// the attack

					probabilityToGetInThisPosition *= Probabilities.probabilitiesMatrix[newGraph
							.get(currentAttack.getFirst()).nrUnits][newGraph.get(currentAttack.getSecond()).nrUnits];
					int remainingTroops = (int) (newGraph.get(currentAttack.getFirst()).nrUnits
							* Probabilities.probabilitiesMatrix[newGraph.get(currentAttack.getFirst()).nrUnits][newGraph
									.get(currentAttack.getSecond()).nrUnits]
							/ 100);

					if (remainingTroops == 0) {
						remainingTroops = 1;
					}
					// the remaining nr of troops is the initial nr of troops * probability of
					// winning -> this is the expected value of remaining troops
					// TODO - implement a formula which computes this number, but in accordance with
					// this.minimumRiskToTake

					// now, there are 2 situations:
					// 1. the attacking army has just 1 unit left and this means that the conquered
					// territory will become a peasant one
					// 2. the attacking army has more than 1 unit left and 1. won't apply here
					if (remainingTroops == 1) {

						newGraph.get(currentAttack.getSecond()).occupyingArmy = Army.PEASANTS;
						newGraph.get(currentAttack.getSecond()).nrUnits = 1;
						continue;
					}

					newGraph.get(currentAttack.getSecond()).occupyingArmy = this.playerToAnalyze;
					this.gameRegionsOccupiedByPlayer.add(graph.get(currentAttack.getSecond())); // maybe we don't need
																								// this

					newGraph.get(currentAttack.getFirst()).nrUnits = rand.nextInt(remainingTroops - 1) + 1;
					// at least 1 remains in the Heimatland
					newGraph.get(currentAttack.getSecond()).nrUnits = remainingTroops
							- newGraph.get(currentAttack.getFirst()).nrUnits;
					// the rest of them go in the newly conquered territory

					// for now, let's just move a random nr of units from the attacking territory to
					// the newly conquered territory
					// TODO - implement a clever algorithm of moving troops from the attacking
					// territory to the newly conquered territory
				}
			}

			this.childrenOfPosition.add(new Position(newGraph, enemyPlayer, playerToAnalyze,
					probabilityToGetInThisPosition, minimumRiskToTake, currentAttackingScenario));
			// finally, add the newly created position in the childrenOfPosition array
			// notice that enemyPlayer and playerToAnalyze parameters are reversed
			// this because, in the minimax algorithm, I must compute, at each step,
			// evaluation and childrenPositions for the next player
			// ex Blue, then Red, then Blue again, then Red etc
			// currentAttackingScenario parameter is used to indicate which series of events
			// (attacking scenario) has been used to get in this position
		}
	}

	public ArrayList<Position> getChildrenOfPosition() {
		return this.childrenOfPosition;
	}

	public void backtrackAttackingScenarios() {

		ArrayList<Pair<RegionName, RegionName>> scenario = new ArrayList<Pair<RegionName, RegionName>>();

		backtrackingUtil(0, scenario);

//		int scenarioIndex = 0;
//
//		for (ArrayList<Pair<RegionName, RegionName>> currentScenario : attackingScenarios) {
//
//			System.out.println("Scenario " + scenarioIndex++);
//
//			for (Pair<RegionName, RegionName> command : currentScenario) {
//				System.out.println(command.getFirst() + " ---->" + command.getSecond());
//			}
//			System.out.println("----------");
//		}
//		System.out.println("-----------------------------------------------------------------------------");
	}

	/*
	 * private void backtrackingUtil(int index, ArrayList<Pair<RegionName,
	 * RegionName>> scenario) is used to compute all the available attacking
	 * scenarios for a specific moment in the game; it is used in the minimax
	 * algorithm, in the beginning of the function, whenever I want to compute all
	 * the children of Position position
	 */

	private void backtrackingUtil(int index, ArrayList<Pair<RegionName, RegionName>> scenario) {

		ArrayList<Pair<RegionName, RegionName>> scenarioCopy = (ArrayList<Pair<RegionName, RegionName>>) scenario
				.clone();

		if (index == gameRegionsOccupiedByPlayer.size()) {

			this.attackingScenarios.add(scenarioCopy);
			// add in the main scenario array the current configuration

			return;
		}

		int loopIndex = 0;

		for (RegionName neighbourRegionName : gameRegionsOccupiedByPlayer.get(index).neighbours) {

			loopIndex++;

			// if we can attack it AND if the attacking army has at least 2 units (you
			// cannot attack with 1 unit, those are the rules)
			if (graph.get(neighbourRegionName).occupyingArmy != this.playerToAnalyze
					&& graph.get(gameRegionsOccupiedByPlayer.get(index).regionName).nrUnits > 1) {

				if (index < gameRegionsOccupiedByPlayer.size()) {
					scenarioCopy.add(new Pair<RegionName, RegionName>(gameRegionsOccupiedByPlayer.get(index).regionName,
							neighbourRegionName));
					backtrackingUtil(index + 1, scenarioCopy);
					scenarioCopy.remove(scenarioCopy.size() - 1);
				}
			}

			if (loopIndex == gameRegionsOccupiedByPlayer.get(index).neighbours.size()) {
				// we artificially consider the PAS scenario, for the territory we are attacking
				// from
				// we do this at the end of this for loop
				scenarioCopy.add(new Pair<RegionName, RegionName>(gameRegionsOccupiedByPlayer.get(index).regionName,
						gameRegionsOccupiedByPlayer.get(index).regionName));
				backtrackingUtil(index + 1, scenarioCopy);
				scenarioCopy.remove(scenarioCopy.size() - 1);
			}
		}
	}
}
