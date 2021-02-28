package mainPackage;

import java.util.List;

public class GameRegion {

	public final RegionName regionName;
	public final Continent continent;
	public List<RegionName> neighbours = null;
	public Army occupyingArmy = Army.PEASANTS;
	public int nrUnits = 1;

	public GameRegion(RegionName regionName, Continent continent) {

		this.regionName = regionName;
		this.continent = continent;
	}

	public GameRegion(RegionName regionName, Continent continent, List<RegionName> neighbours, Army occupyingArmy,
			int nrUnits) {

		this.regionName = regionName;
		this.continent = continent;
		this.neighbours = neighbours;
		this.occupyingArmy = occupyingArmy;
		this.nrUnits = nrUnits;
	}
}
