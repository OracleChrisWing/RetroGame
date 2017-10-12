package mapManager;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MapLoader {

	// The location of the Maps folder.
	private final String RES = "res\\Maps\\";
	private String filename;
	
	// Stores the width and height of the map (in terms of tiles).
	private int mapWidth; private int mapHeight;
	
	// Strones the width and height of the map (in terms of pixels).
	private int mapWidthPixels; private int mapHeightPixels;
	// Stores the width and height of the tiles (in terms of pixels).
	private int tileWidth; private int tileHeight;
	
	// Stores the width and height of the spritesheet (in terms of tiles).
	private int sheetWidth; private int sheetHeight;
	
	private String spritesheetSource = "";
	
	private MapData mapData;
	
	public MapLoader(MapData mapData, String filename) {
		
		this.mapData = mapData;
		this.filename = filename;
	}
	
	// Loads the tile map into memory.
	public void loadMap(){
		
		try {
			
			// Before we can start parsing the document, we must first load it and structure it as an XML doc. 
			// From there we can read it like any other XML doc.
			
			File mapFile = new File(RES + filename + ".tmx");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(mapFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nMap = doc.getElementsByTagName("map");
			Node mapNode = nMap.item(0);
			
			if(mapNode.getNodeType() == Node.ELEMENT_NODE) {
				
				// Contains attribute information within the 'map' element.
				Element attribs = (Element) mapNode;
				
				// Gets the map dimensions.
				mapWidth = Integer.parseInt(attribs.getAttribute("width"));
				mapHeight = Integer.parseInt(attribs.getAttribute("height"));
				
				// Gets the tile dimensions.
				tileWidth = Integer.parseInt(attribs.getAttribute("tilewidth"));
				tileHeight = Integer.parseInt(attribs.getAttribute("tileheight"));
				
				mapWidthPixels = mapWidth * tileWidth;
				mapHeightPixels = mapHeight * tileHeight;
			}
			
			NodeList nImage = doc.getElementsByTagName("tileset");
			Node imageNode = nImage.item(0);
			
			if(imageNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element attribs = (Element)imageNode;
				
				spritesheetSource = attribs.getAttribute("source");
			}
			
			NodeList nLayers = doc.getElementsByTagName("layer");
			
			for(int i = 0; i < nLayers.getLength(); i++) {
				
				Node layerNode = nLayers.item(i);
				
				if(layerNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element data = (Element)layerNode;
					
					String mapData = data.getElementsByTagName("data").item(0).getTextContent();
					
					String[] rawData = mapData.split(",");
					int[][] processedData = processRawData(rawData, mapWidth, mapHeight);
					
					this.mapData.addData(processedData);
				}
			}
			
		}catch(Exception e) { e.printStackTrace(); }
	}
	
	// This processes the raw data from the .tmx file into an easier format to later read when selecting what tile to render.
	private int[][] processRawData(String[] rawData, int width, int height){
		
		int[][] data = new int[width][height];
		
		int counter = 0;
		
		for(int y = 0; y < height; y++) {
			
			for(int x = 0; x < width; x++) {
				
				String rd = rawData[counter];
				rd = rd.trim();
				
				data[x][y] = Integer.parseInt(rd);
				counter ++;
			}
		}
		
		return data;
	}

	// Assortment of getters and setters.
	
	public int getMapWidth() { return mapWidth; }

	public int getMapHeight() { return mapHeight; }
	
	public int getMapWidthPixels() { return mapWidthPixels; }
	
	public int getMapHeightPixels() { return mapHeightPixels; }

	public int getTileWidth() { return tileWidth; }

	public int getTileHeight() { return tileHeight; }

	public int getSheetWidth() { return sheetWidth; }

	public int getSheetHeight() { return sheetHeight; }

	public String getSpritesheetSource() { return spritesheetSource; }
}
