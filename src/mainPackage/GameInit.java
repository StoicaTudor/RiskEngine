package mainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInit {

	private HashMap<RegionName, GameRegion> graph = new HashMap<RegionName, GameRegion>();
	// the graph

	///////////////////////////////////////////////////////////////////////// initializing
	///////////////////////////////////////////////////////////////////////// Game
	///////////////////////////////////////////////////////////////////////// Regions
	GameRegion ALASKA = new GameRegion(RegionName.ALASKA, Continent.NORTH_AMERICA);
	GameRegion NORD_WEST_TERITORY = new GameRegion(RegionName.NORD_WEST_TERITORY, Continent.NORTH_AMERICA);
	GameRegion VANCOUVER = new GameRegion(RegionName.VANCOUVER, Continent.NORTH_AMERICA);
	GameRegion WINNIPEG = new GameRegion(RegionName.WINNIPEG, Continent.NORTH_AMERICA);
	GameRegion QUEBEC = new GameRegion(RegionName.QUEBEC, Continent.NORTH_AMERICA);
	GameRegion WEST_UNITED_STATES = new GameRegion(RegionName.WEST_UNITED_STATES, Continent.NORTH_AMERICA);
	GameRegion CENTRAL_AMERICA = new GameRegion(RegionName.CENTRAL_AMERICA, Continent.NORTH_AMERICA);
	GameRegion GREENLAND = new GameRegion(RegionName.GREENLAND, Continent.NORTH_AMERICA);
	GameRegion EAST_UNITED_STATES = new GameRegion(RegionName.EAST_UNITED_STATES, Continent.NORTH_AMERICA);

	GameRegion BRASIL = new GameRegion(RegionName.BRASIL, Continent.SOUTH_AMERICA);
	GameRegion PERU = new GameRegion(RegionName.PERU, Continent.SOUTH_AMERICA);
	GameRegion ARGENTINA = new GameRegion(RegionName.ARGENTINA, Continent.SOUTH_AMERICA);
	GameRegion COLUMBIA = new GameRegion(RegionName.COLUMBIA, Continent.SOUTH_AMERICA);

	GameRegion NORTH_AFRICA = new GameRegion(RegionName.NORTH_AFRICA, Continent.AFRICA);
	GameRegion EGIPT = new GameRegion(RegionName.EGIPT, Continent.AFRICA);
	GameRegion SAHARA = new GameRegion(RegionName.SAHARA, Continent.AFRICA);
	GameRegion CONGO = new GameRegion(RegionName.CONGO, Continent.AFRICA);
	GameRegion EAST_AFRICA = new GameRegion(RegionName.EAST_AFRICA, Continent.AFRICA);
	GameRegion SOUTH_AFRICA = new GameRegion(RegionName.SOUTH_AFRICA, Continent.AFRICA);

	GameRegion INDONESIA = new GameRegion(RegionName.INDONESIA, Continent.AUSTRALIA);
	GameRegion PAPUA = new GameRegion(RegionName.PAPUA, Continent.AUSTRALIA);
	GameRegion WEST_AUSTRALIA = new GameRegion(RegionName.WEST_AUSTRALIA, Continent.AUSTRALIA);
	GameRegion QUEENSLAND = new GameRegion(RegionName.QUEENSLAND, Continent.AUSTRALIA);

	GameRegion WEST_EUROPE = new GameRegion(RegionName.WEST_EUROPE, Continent.EUROPE);
	GameRegion CENTRAL_EUROPE = new GameRegion(RegionName.CENTRAL_EUROPE, Continent.EUROPE);
	GameRegion SCANDINAVIA = new GameRegion(RegionName.SCANDINAVIA, Continent.EUROPE);
	GameRegion EAST_EUROPE = new GameRegion(RegionName.EAST_EUROPE, Continent.EUROPE);
	GameRegion EUROPEAN_RUSSIA = new GameRegion(RegionName.EUROPEAN_RUSSIA, Continent.EUROPE);

	GameRegion MIDDLE_EAST = new GameRegion(RegionName.MIDDLE_EAST, Continent.ASIA);
	GameRegion IRAN = new GameRegion(RegionName.IRAN, Continent.ASIA);
	GameRegion CENTRAL_RUSSIA = new GameRegion(RegionName.CENTRAL_RUSSIA, Continent.ASIA);
	GameRegion SIBERIA = new GameRegion(RegionName.SIBERIA, Continent.ASIA);
	GameRegion MONGOLIA = new GameRegion(RegionName.MONGOLIA, Continent.ASIA);
	GameRegion BAIKAL = new GameRegion(RegionName.BAIKAL, Continent.ASIA);
	GameRegion CHINA = new GameRegion(RegionName.CHINA, Continent.ASIA);
	GameRegion INDIA = new GameRegion(RegionName.INDIA, Continent.ASIA);
	GameRegion INDO_CHINA = new GameRegion(RegionName.INDO_CHINA, Continent.ASIA);
	GameRegion JAPAN = new GameRegion(RegionName.JAPAN, Continent.ASIA);
	GameRegion KAMCEATKA = new GameRegion(RegionName.KAMCEATKA, Continent.ASIA);
	///////////////////////////////////////////////////////////////////////// initializing
	///////////////////////////////////////////////////////////////////////// Game
	///////////////////////////////////////////////////////////////////////// Regions

	public void initNeighbours() {

		List ALASKA_NEIGHBOURS = new ArrayList<RegionName>();
		// ALASKA_NEIGHBOURS.add(KAMCEATKA.regionName);
		ALASKA_NEIGHBOURS.add(VANCOUVER.regionName);
		ALASKA_NEIGHBOURS.add(NORD_WEST_TERITORY.regionName);

		List NORD_WEST_TERITORY_NEIGHBOURS = new ArrayList<RegionName>();
		NORD_WEST_TERITORY_NEIGHBOURS.add(ALASKA.regionName);
		NORD_WEST_TERITORY_NEIGHBOURS.add(VANCOUVER.regionName);
		NORD_WEST_TERITORY_NEIGHBOURS.add(WINNIPEG.regionName);
		NORD_WEST_TERITORY_NEIGHBOURS.add(GREENLAND.regionName);

		List VANCOUVER_NEIGHBOURS = new ArrayList<RegionName>();
		VANCOUVER_NEIGHBOURS.add(ALASKA.regionName);
		VANCOUVER_NEIGHBOURS.add(NORD_WEST_TERITORY.regionName);
		VANCOUVER_NEIGHBOURS.add(WINNIPEG.regionName);
		VANCOUVER_NEIGHBOURS.add(WEST_UNITED_STATES.regionName);

		List WINNIPEG_NEIGHBOURS = new ArrayList<RegionName>();
		WINNIPEG_NEIGHBOURS.add(VANCOUVER.regionName);
		WINNIPEG_NEIGHBOURS.add(NORD_WEST_TERITORY.regionName);
		WINNIPEG_NEIGHBOURS.add(EAST_UNITED_STATES.regionName);
		WINNIPEG_NEIGHBOURS.add(WEST_UNITED_STATES.regionName);
		WINNIPEG_NEIGHBOURS.add(QUEBEC.regionName);

		List WEST_UNITED_STATES_NEIGHBOURS = new ArrayList<RegionName>();
		WEST_UNITED_STATES_NEIGHBOURS.add(VANCOUVER.regionName);
		WEST_UNITED_STATES_NEIGHBOURS.add(WINNIPEG.regionName);
		WEST_UNITED_STATES_NEIGHBOURS.add(EAST_UNITED_STATES.regionName);
		WEST_UNITED_STATES_NEIGHBOURS.add(CENTRAL_AMERICA.regionName);

		List EAST_UNITED_STATES_NEIGHBOURS = new ArrayList<RegionName>();
		EAST_UNITED_STATES_NEIGHBOURS.add(WEST_UNITED_STATES.regionName);
		EAST_UNITED_STATES_NEIGHBOURS.add(WINNIPEG.regionName);
		EAST_UNITED_STATES_NEIGHBOURS.add(CENTRAL_AMERICA.regionName);
		EAST_UNITED_STATES_NEIGHBOURS.add(QUEBEC.regionName);

		List QUEBEC_NEIGHBOURS = new ArrayList<RegionName>();
		QUEBEC_NEIGHBOURS.add(GREENLAND.regionName);
		QUEBEC_NEIGHBOURS.add(EAST_UNITED_STATES.regionName);
		QUEBEC_NEIGHBOURS.add(WINNIPEG.regionName);

		List GREENLAND_NEIGHBOURS = new ArrayList<RegionName>();
		GREENLAND_NEIGHBOURS.add(QUEBEC.regionName);
		GREENLAND_NEIGHBOURS.add(NORD_WEST_TERITORY.regionName);
//		WEST_UNITED_STATES_NEIGHBOURS.add(WEST_EUROPE.regionName);
//		WEST_UNITED_STATES_NEIGHBOURS.add(SCANDINAVIA.regionName);

		List CENTRAL_AMERICA_NEIGHBOURS = new ArrayList<RegionName>();
		// CENTRAL_AMERICA_NEIGHBOURS.add(COLUMBIA.regionName);
		CENTRAL_AMERICA_NEIGHBOURS.add(EAST_UNITED_STATES.regionName);
		CENTRAL_AMERICA_NEIGHBOURS.add(WEST_UNITED_STATES.regionName);

		ALASKA.neighbours = ALASKA_NEIGHBOURS;
		NORD_WEST_TERITORY.neighbours = NORD_WEST_TERITORY_NEIGHBOURS;
		GREENLAND.neighbours = GREENLAND_NEIGHBOURS;
		QUEBEC.neighbours = QUEBEC_NEIGHBOURS;
		EAST_UNITED_STATES.neighbours = EAST_UNITED_STATES_NEIGHBOURS;
		WEST_UNITED_STATES.neighbours = WEST_UNITED_STATES_NEIGHBOURS;
		CENTRAL_AMERICA.neighbours = CENTRAL_AMERICA_NEIGHBOURS;
		WINNIPEG.neighbours = WINNIPEG_NEIGHBOURS;
		VANCOUVER.neighbours = VANCOUVER_NEIGHBOURS;

		System.gc();
	}

	private void initGraph() {

		graph.put(RegionName.ALASKA, ALASKA);
		graph.put(RegionName.NORD_WEST_TERITORY, NORD_WEST_TERITORY);
		graph.put(RegionName.GREENLAND, GREENLAND);
		graph.put(RegionName.VANCOUVER, VANCOUVER);
		graph.put(RegionName.WINNIPEG, WINNIPEG);
		graph.put(RegionName.QUEBEC, QUEBEC);
		graph.put(RegionName.WEST_UNITED_STATES, WEST_UNITED_STATES);
		graph.put(RegionName.EAST_UNITED_STATES, EAST_UNITED_STATES);
		graph.put(RegionName.CENTRAL_AMERICA, CENTRAL_AMERICA);
	}

	private void setArmies() {

		graph.get(RegionName.ALASKA).occupyingArmy = Army.RED;
		graph.get(RegionName.ALASKA).nrUnits = 5;

		graph.get(RegionName.NORD_WEST_TERITORY).occupyingArmy = Army.BLUE;
		graph.get(RegionName.NORD_WEST_TERITORY).nrUnits = 10;

		graph.get(RegionName.VANCOUVER).occupyingArmy = Army.YELLOW;
		graph.get(RegionName.VANCOUVER).nrUnits = 3;

		graph.get(RegionName.GREENLAND).occupyingArmy = Army.BLUE;
		graph.get(RegionName.GREENLAND).nrUnits = 2;

		graph.get(RegionName.QUEBEC).occupyingArmy = Army.YELLOW;
		graph.get(RegionName.QUEBEC).nrUnits = 2;

		graph.get(RegionName.CENTRAL_AMERICA).occupyingArmy = Army.YELLOW;
		graph.get(RegionName.CENTRAL_AMERICA).nrUnits = 10;

		graph.get(RegionName.WEST_UNITED_STATES).occupyingArmy = Army.BLUE;
		graph.get(RegionName.WEST_UNITED_STATES).nrUnits = 3;

		graph.get(RegionName.EAST_UNITED_STATES).occupyingArmy = Army.RED;
		graph.get(RegionName.EAST_UNITED_STATES).nrUnits = 4;
	}

	public HashMap<RegionName, GameRegion> getGraph() {
		return this.graph;
	}

	public GameInit() {
		initNeighbours();
		initGraph();
		setArmies();
	}

}
