package mainPackage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Game {

	private ArrayList<Army> enemyArmies = new ArrayList<Army>();
	// a list with enemy armies, in case I will ever use it
	private Army aiArmy;
	// AI's army
	private HashMap<Army, Army> armiesOrder;
	// I use a hashMap, since I want to call armiesOrder.get(this.turn) -> in order
	// to retrieve the next player's turn
	private Army turn;
	// current turn in the game

	private HashMap<RegionName, GameRegion> graph = new HashMap<RegionName, GameRegion>();
	private Utility utility = new Utility();

	private GameFrontEnd gameFrontEnd = new GameFrontEnd();
	// object which is used to draw the frame and its elements accordingly

	public Game(Army aiArmy, ArrayList<Army> enemyArmies, HashMap<Army, Army> armiesOrder,
			HashMap<RegionName, GameRegion> graph, Army startingArmy) {

		this.aiArmy = aiArmy;
		this.enemyArmies = enemyArmies;
		this.armiesOrder = armiesOrder;
		this.graph = graph;
		this.turn = startingArmy;

		gameFrontEnd.initializeFrame(this.turn, utility.getGraphHashMapClone(this.graph), Army.BLUE);
		// calling the frame initializer method

		GameEvent gameEvent;

		// !!!!! check if, after an attack in which the attacker transfered units, it
		// can further attack from the conquered territory
		// IT MUST NOT
		// ANSWER: it does and it can; fix this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// am ramas aici + un improvement de salvat in variabile, nu lasat sa tot graph.get
		while (true) { // do this until the game finishes

			while (gameFrontEnd.getGameEvent() == null && gameFrontEnd.frameIsEnabled()) {

				gameEvent = gameFrontEnd.getGameEvent();
				System.out.print("");

			} // while gameEvent is null, wait for a not null value && while the frame is
				// enabled (next turn hasn't been pressed)

			if (!gameFrontEnd.frameIsEnabled()) {
				// if we exited the loop because next turn has been pressed

				this.turn = armiesOrder.get(this.turn);
				// switch to the next turn

			} else if (gameFrontEnd.getGameEvent() != null) {
				// if we exited the loop because ENTER was pressed (an attack has been launched)

				gameEvent = gameFrontEnd.getGameEvent();
				// we collect the gameEvent object(attacker, defender, territories, dice values,
				// along with the resulting lost units)

				graph.get(gameEvent.attackingRegion).nrUnits -= gameEvent.attackerLosses;
				graph.get(gameEvent.defendingRegion).nrUnits -= gameEvent.defenderLosses;
				// at the end of each fight, substract from each army the nr of units it has
				// lost

				if (graph.get(gameEvent.attackingRegion).nrUnits <= 0) {
					// if the attacker has remained with no units, the region becomes a PEASANTS one
					// and it will have just 1 unit on it, by default

					graph.get(gameEvent.attackingRegion).occupyingArmy = Army.PEASANTS;
					graph.get(gameEvent.attackingRegion).nrUnits = 1;
				}

				if (graph.get(gameEvent.defendingRegion).nrUnits <= 0) {
					// if the defender has remained with no units, the region becomes a PEASANTS one
					// and it will have just 1 unit on it, by default

					graph.get(gameEvent.defendingRegion).occupyingArmy = Army.PEASANTS;
					graph.get(gameEvent.defendingRegion).nrUnits = 1;

					if (graph.get(gameEvent.attackingRegion).occupyingArmy != Army.PEASANTS
							&& graph.get(gameEvent.attackingRegion).nrUnits > 1) {
						// if the attacking army did not turn up to be peasants && has more than 1 unit
						// (i.e., if it can move units from the attacking territory to the conquered one
						// (defending one))

						graph.get(gameEvent.defendingRegion).occupyingArmy = graph
								.get(gameEvent.attackingRegion).occupyingArmy;
						// the defending territory becomes the occupyingArmy

						if (graph.get(gameEvent.attackingRegion).nrUnits == 2) {
							// if there are just 2 units on the territory, we do not require the user to
							// input the nr of units

							graph.get(gameEvent.defendingRegion).nrUnits = 1;
							graph.get(gameEvent.attackingRegion).nrUnits -= 1;
							// it moves just 1 unit (mandatory)
						} else {

							String inputAnswer = JOptionPane
									.showInputDialog(new StringBuilder("How many units shall go to ")
											.append(gameEvent.defendingRegion.toString()).append("? (1 .... ")
											.append(graph.get(gameEvent.attackingRegion).nrUnits - 1).append(')')
											.toString());
							// require the user to input the number of units it wants to transfer (at least
							// 1)

							if (inputAnswer == null) {
								// if the user has pressed X or CANCEL
								graph.get(gameEvent.defendingRegion).nrUnits = 1;
								graph.get(gameEvent.attackingRegion).nrUnits -= 1;
							} else {

								// analyse the user input
								try {

									int inputAnswerInt = Integer.parseInt(inputAnswer);

									if (inputAnswerInt < graph.get(gameEvent.attackingRegion).nrUnits) {
										// put maximum
										graph.get(gameEvent.defendingRegion).nrUnits = graph
												.get(gameEvent.attackingRegion).nrUnits - 1;

										graph.get(gameEvent.attackingRegion).nrUnits = 1;

										// improvement !!! -> place values in separate variables, such that the program
										// shall not be required to graph.get every time -> time consuming
									} else if (inputAnswerInt < 0) {
										// if the user places negative values, we just transfer 1 unit

										graph.get(gameEvent.defendingRegion).nrUnits = 1;
										graph.get(gameEvent.attackingRegion).nrUnits -= 1;
									} else {
										// if the user places a normal value
										graph.get(gameEvent.defendingRegion).nrUnits = inputAnswerInt;
										graph.get(gameEvent.attackingRegion).nrUnits -= inputAnswerInt;
									}

								} catch (NumberFormatException ex) {
									// if NumberFormatException is thrown, it means that user has entered a non
									// valid input; so transfer 1 unit
									graph.get(gameEvent.defendingRegion).nrUnits = 1;
									graph.get(gameEvent.attackingRegion).nrUnits -= 1;
								}
							}
						}
					}
				}

				// if it reaches this point, we then it means that gameEvent is not null
				gameFrontEnd.setGameEventToNull();
				// so set it to null
			}

			gameFrontEnd.initializeFrame(this.turn, utility.getGraphHashMapClone(this.graph), Army.BLUE);
			System.gc();
		}
	}
}
