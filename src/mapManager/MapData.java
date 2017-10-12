package mapManager;

import java.util.ArrayList;

public class MapData {

	// List containing tile id's.
	private ArrayList<int[][]> mapData = new ArrayList<int[][]>();
	
	// Getter allowing external classes to access the list.
	public ArrayList<int[][]> getMapData() { return mapData; }
	
	// Allows new tile id's to be added to the list.
	public void addData(int[][] data) {
		
		mapData.add(data);
	}
	
	// Cleans up the list when the game closes down.
	public void cleanUp() { mapData.clear(); }
	
}