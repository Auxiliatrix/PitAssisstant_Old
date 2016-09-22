import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class InventoryLoader {

	public static String[] fileDump = new String[10000];
	public static int fileDumpPointer = 0;
	public static int locations = 0;
	// Number of General Locations
	public static String[] locationNames = new String[255];
	// Names of the General Locations
	public static int[] masterInventoryPointers = new int[255];
	// Contains General Location sizes
	public static String[][][] masterInventory = new String[255][10000][100];
	// Level 1: General Location
	// Level 2: Index
	// Level 2: Category { 0:Item || 1+:Description }
	// Level 3: Contents
	public static String[][] masterInventoryBorrow = new String[255][10000];
	public static int[][] masterInventoryDescriptionsPointer = new int[255][10000];
	
	public static String[] inventoryFile = new String[10000];
	public static int inventoryFilePointer = 0;
	
	public static boolean previousWasItem = false;
	
	protected static PAInterface GUI = new PAInterface();
	
	public static void main(String[] args) {
		
		run();

	}
	public static void run()
	{
		GUI.text("InventoryLoader called.");
		if(!loadedInventory())
		{
			if(createInventory())
			{
				GUI.text("Inventory created.");
			}
		}
		locations = 0;
		for( int f=0; f<255; f++ )
		{
			locationNames[f] = "";
			masterInventoryPointers[f] = 0;
			for( int g=0; g<10000; g++ )
			{
				for( int h=0; h<100; h++ )
				{
					masterInventory[f][g][h] = "";
				}
				masterInventoryDescriptionsPointer[f][g] = 0;
				inventoryFile[g] = "";
				masterInventoryBorrow[f][g] = "0";
			}
		}
		previousWasItem = false;
		inventoryFilePointer = 0;
		loadInventory();
		GUI.text("Inventory loaded.");
	}
	public static boolean loadedInventory()
	{
		boolean tf = false;
		String fileName = "inventory.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileReader.close();
            bufferedReader.close();
            tf = true;
        }
        catch(Exception e)
        {
        	tf = false;
        	GUI.text("Initial startup detected.");
        	GUI.text("Creating new inventory file.");
        }
		return tf;
	}
	public static boolean createInventory()
	{
		boolean tf = true;
		try {
        	FileWriter fw = new FileWriter("inventory.txt", true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter out = new PrintWriter(bw);
        	fw.close();
        	bw.close();
        	out.close();
		}
		catch(Exception E)
		{
			tf = false;
			GUI.text("Error creating inventory file.");
		}
		return tf;
	}
	public static void saveIventory()
	{
		String fileName = "inventory.txt";
		String line = null;
		try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {
            	if( line.startsWith("//") )
            	{
            		String ignore = line;
            	}
            	else if( line!=null )
				{
            		inventoryFile[inventoryFilePointer] = line;
            		inventoryFilePointer++;
				}
			} 
            fileReader.close();
            bufferedReader.close();
		}
		catch(Exception e)
		{
			GUI.text("I had an issue while trying to read from the inventory file.");
		}
	}
	public static void loadInventory()
	{
        String fileName = "inventory.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {
            	line = line.toLowerCase();
            	String item = "";
            	int length = line.length();
            	String sub = line.substring(1,length);
            	if( line.startsWith("//") )
            	{
            		String ignore = line;
            	}
            	else if( line.toLowerCase().startsWith("+") )
				{
					locations++;
					locationNames[locations-1] = sub;
					previousWasItem = false;
					GUI.text("Location \"" + sub + "\" added.");
				}
            	else if( line.toLowerCase().startsWith("-") )
            	{
            		if( !previousWasItem )
            		{
            			GUI.text("Warning: \"" + sub + "\" is a descriptor without a parent item." );
            			GUI.text("Setting \"" + sub + "\" as parent item.");
            			masterInventory[locations-1][masterInventoryPointers[locations-1]][0] = sub;
            			masterInventoryPointers[locations-1]++;
            			GUI.text("Item \"" + sub + "\" added under Location \"" + locationNames[locations-1] + "\"." );
            		}
            		else
            		{
            			masterInventory[locations-1][masterInventoryPointers[locations-1]][masterInventoryDescriptionsPointer[locations-1][masterInventoryPointers[locations-1]]+1] = sub;
            			masterInventoryDescriptionsPointer[locations-1][masterInventoryPointers[locations-1]]++;
            			GUI.text("Descriptor \"" + sub + "\" added under Item \"" + masterInventory[locations-1][masterInventoryPointers[locations-1]-1][0] + "\".");
            		}
            		previousWasItem = true;
            	}
            	else
            	{
            		masterInventory[locations-1][masterInventoryPointers[locations-1]][0] = line;
            		masterInventoryPointers[locations-1]++;
            		GUI.text("Item \"" + line + "\" added under Location \"" + locationNames[locations-1] + "\".");
            		previousWasItem = true;
            	}
            }
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            GUI.text("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            GUI.text("Error reading file '" + fileName + "'");
        }
	}
}
