package mainPackage;

// class which will be passed from the front end (user's input) to the Game class, which will coordinate the game (game's driver)
public class GameEvent {

	public RegionName attackingRegion;
	public Army attackingArmy;

	public RegionName defendingRegion;
	public Army defendingArmy;

	private int[] attackingDice;
	private int[] defendingDice;

	public int attackerLosses = 0;
	public int defenderLosses = 0;
	// variables which indicate how many units will each army lose

	public GameEvent(RegionName attackingRegion, Army attackingArmy, RegionName defendingRegion, Army defendingArmy,
			int[] attackingDice, int[] defendingDice) {

		this.attackingRegion = attackingRegion;
		this.attackingArmy = attackingArmy;

		this.defendingRegion = defendingRegion;
		this.defendingArmy = defendingArmy;

		this.attackingDice = sortDescending(attackingDice);
		this.defendingDice = sortDescending(defendingDice);

		for (int i = 0; i < this.defendingDice.length; i++) {

			if (attackingDice[i] > defendingDice[i]) {
				defenderLosses++;
				// if the attacker die is bigger than the defender's, the defender looses a unit
			} else if (attackingDice[i] < defendingDice[i]) {
				attackerLosses++;
				// if the defender die is bigger than the attacker's, the attacker looses a unit
			} else {
				defenderLosses++;
				attackerLosses++;
				// else, both lose 1 unit
			}
		}
	}

	private int[] sortDescending(int[] array) {
		// a simple bubble sort function for sorting the dices
		// it does not require efficiency, since it aims to sort 1, 2 or 3 elements

		if (array.length == 1) {
			return array;
		}

		boolean sorted = false;

		while (!sorted) {

			sorted = true; // suppose the array is sorted, at the start of the loop

			for (int i = 0; i < array.length - 1; i++) {

				if (array[i] < array[i + 1]) {

					int aux = array[i];
					array[i] = array[i + 1];
					array[i + 1] = aux;

					sorted = false;
				}
			}
		}

		return array;
	}
}
