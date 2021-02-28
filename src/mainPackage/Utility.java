package mainPackage;

import java.util.HashMap;

public class Utility {

	public HashMap<RegionName, GameRegion> getGraphHashMapClone(HashMap<RegionName, GameRegion> originalHashMap) {

		HashMap<RegionName, GameRegion> clonedHashMap = new HashMap<RegionName, GameRegion>();

		for (HashMap.Entry<RegionName, GameRegion> entry : originalHashMap.entrySet()) {
			clonedHashMap.put(entry.getKey(), new GameRegion(entry.getValue().regionName, entry.getValue().continent,
					entry.getValue().neighbours, entry.getValue().occupyingArmy, entry.getValue().nrUnits));
		}

		return clonedHashMap;
	}
}
