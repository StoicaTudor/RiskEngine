package mainPackage;

import java.util.HashMap;

public class Mission {

	private HashMap<Continent, Integer> continentsToConquer = null;
	private Army armyToDefeat = null;
	private final int nrTerritoriesToConquer;

	public Mission(HashMap<Continent, Integer> continentsToConquer, Army armyToDefeat, int nrTerritoriesToConquer) {
		this.continentsToConquer = continentsToConquer;
		this.armyToDefeat = armyToDefeat;
		this.nrTerritoriesToConquer = nrTerritoriesToConquer;
	}

	public HashMap<Continent, Integer> getContinentsToConquer() {
		return this.continentsToConquer;
	}

	public Army getArmyToDefeat() {
		return this.armyToDefeat;
	}

	public int getNrTerritoriesToConquer() {
		return this.nrTerritoriesToConquer;
	}

}
