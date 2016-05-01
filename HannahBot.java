import java.util.Scanner;
import java.io.*;

public class HannahBot {

	public static String[] borrowedItem = new String[10000];
	public static int[] borrowedTeam = new int[10000];
	public static int borrowedPointer = 0;
	public static String[] lentItem = new String[10000];
	public static int[] lentTeam = new int[10000];
	public static int lentPointer = 0;
	public static int[][] results = new int[10000][2];
	public static String[] exact = new String[10000];
	public static int[] exactLocation = new int[10000];
	public static int exactPointer = 0;
	public static String[] printed = new String[10000];
	public static int printedPointer = 1;
	public static int resultPointer = 0;
	public static int[] p = new int[7];
	public static String[] keywords = new String[10000];
	public static int keywordPointer = 0;
	public static int TLLength = 42;
	public static int TALength = 20;
	public static int TBLength = 11;
	public static int TCLength = 18;
	public static int TDLength = 25;
	public static int TELength = 8;
	public static int CLength = 16;
	public static int ELength = 23;
	public static int PLength = 29;
	public static String[] ToolBox = new String[42];
	public static String[] ToteA = new String[20];
	public static String[] ToteB = new String[11];
	public static String[] ToteC = new String[18];
	public static String[] ToteD = new String[25];
	public static String[] ToteE = new String[8];
	public static String[] Crate = new String[16];
	public static String[] TLDesc = new String[42];
	public static String[] TADesc = new String[20];
	public static String[] TBDesc = new String[11];
	public static String[] TCDesc = new String[18];
	public static String[] TDDesc = new String[25];
	public static String[] TEDesc = new String[8];
	public static String[] CDesc = new String[16];
	public static String[] Exclusion = new String[23];
	public static String[] People = new String[29];
	public static boolean askHannah = false;
	public static boolean debugMode = false;
	public static boolean ziptie = false;
	public static void main(String args[]) throws InterruptedException
	{
		initialize();
		while( true )
		{
			conductor();
		}
	}
	public static void initialize()
	{
		loadLibrary();
		loadBorrow();
		greet();
	}
	public static void greet()
	{
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=[Hannah Bot]=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println();
		System.out.println("   Hi, I'm HannahBot (v1.8). I can look for things, and tell you what's in our totes and boxes.");
		System.out.println("Hannah Bot (v1.8) Theoretically(TM) supports description-based queries and all sentence structures.");
		System.out.println();
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v1.8)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println();
		System.out.println("How may I help you?");
	}
	public static void conductor() throws InterruptedException
	{
		reset();
		String input = input();
		boolean normal = caser(input);
		if( normal )
		{
			parse(input);
			search();
			output();
		}
		menu();
	}
	public static boolean caser(String input) throws InterruptedException
	{
		boolean normal = false;
		boolean skip = false;
		boolean ignoreExclaim = false;
		String data = input.toLowerCase();
		if( input.equals("") )
		{
			System.out.println("I'm going to need something more specific.");
			skip = true;
		}
		if( data.contains("changelog") )
		{
			System.out.println("(v1.0) ::  Basic search function for totes.");
			System.out.println("(v1.1) ::  Added some more search functinality.");
			System.out.println("(v1.2) ::  Added ability to list things in totes.");
			System.out.println("(v1.3) ::  Added support for sentence structures.");
			System.out.println("(v1.4) ::  Added Easter Eggs and bug fixes.");
			System.out.println("(v1.5) ::  Incorporated description-based search.");
			System.out.println("(v1.6) ::  Bug fixes. Added some additional commands and Easter Eggs.");
			System.out.println("(v1.7) ::  Improved Search Algorithm. Bug Fixes. Consolidated Memory Arrays.");
			skip = true;
			System.out.println("(v1.8) ::  Critical Bug Fix.");
		}
		if( data.contains("help") && !data.contains("find") || data.contains("help") && !data.contains("look") )
		{
			System.out.println("I can look for things by name or by description, theoretically.");
			System.out.println("I can also list things in the totes.");
			System.out.println("Say 'flush' to clear the output thingy.");
			System.out.println("Say 'changelog' to view the changelog.");
			System.out.println("You can also toggle debug mode by telling me to.");
			skip = true;
		}
		if( data.contains("flush") )
		{
			for( int f=0; f<50; f++ )
			{
				System.out.println();
			}
			skip = true;
		}
		if( data.contains("cls") )
		{
			System.out.println("Cls? What's that? I'm supposed to be based off of a human being, Pranav.");
			skip = true;
		}
		if( data.contains("three") || data.contains("3") )
		{
			if( data.contains("law") )
			{
				System.out.println("I'm offended.");
				skip = true;
			}
		}
		if( data.contains("debug") && data.contains(" on") )
		{
			debugMode = true;
			skip = true;
		}
		else if( data.contains("debug") && data.contains(" off") )
		{
			debugMode = false;
			skip = true;
		}
		else if( data.contains("debug") && data.contains("toggle") )
		{
			debugMode = !debugMode;
		}
		if( data.startsWith("hi!") || data.startsWith("hi") || data.startsWith("hi?") || data.startsWith("hello") || data.startsWith("greetings") )
		{
			System.out.println("Hi.");
			Thread.sleep(500);
			ignoreExclaim = true;
			skip = true;
		}
		if( data.contains("ziptie") && data.contains("dream") )
		{
			System.out.println("Allow me to refer you to our robot.");
		}
		if( data.contains("door") || data.contains("hinge") )
		{
			System.out.println("Really? Again?");
			skip = true;
		}
		if( data.contains("replacement") && data.contains("hannah") )
		{
			System.out.println("I'm not Hannah's replacement. Hannah is a wonderful and unique human being. I am a computer program.");
			skip = true;
		}
		if( input.endsWith("!") && !ignoreExclaim  )
		{
			System.out.println("Hey, no need to yell.");
			Thread.sleep(500);
		}
		if( input.endsWith("!") && data.contains("please") )
		{
			System.out.println("Fine.");
			Thread.sleep(500);
		}
		if( data.contains("backpack") )
		{
			System.out.println("If you're looking for a backpack, I would ask Pranav.");
			skip = true;
		}
		if( !skip )
		{
			boolean checkPerson = false;
			for( int f=0; f<29; f++ )
			{
				if( data.contains(People[f]) && data.contains("where") )
				{
					checkPerson = true;
					break;
				}
			}
			if( checkPerson )
			{
				System.out.println("I'm a tote organizer. Go ask Rayna or something.");
			}
			else if( data.contains("where") && data.contains("hannah") && askHannah )
			{
				System.out.println("I'm right her- Oh.");
				Thread.sleep(500);
				System.out.println("You meant *that* Hannah.");
				askHannah = true;
			}
			else if( data.contains("where") && data.contains("hannah") && !askHannah )
			{
				System.out.println("Haven't you already tried looking for me?");
			}
			else if( data.contains("love") )
			{
				System.out.println("If you're looking for love, you'll have to look elsewhere.");
			}
			else if( data.contains("toolbox") )
			{
				listToolBox();
			}
			else if( data.contains("tote a") )
			{
				listToteA();
			}
			else if( data.contains("tote b") )
			{
				listToteB();
			}
			else if( data.contains("tote c") )
			{
				listToteC();
			}
			else if( data.contains("tote d") )
			{
				listToteD();
			}
			else if( data.contains("tote e") )
			{
				listToteE();
			}
			else if( data.contains("crate") )
			{
				listCrate();
			}
			else if( data.contains("inventory") || data.contains("list") || data.contains(" all") || data.contains("everything") )
			{
				listToolBox();
				listToteA();
				listToteB();
				listToteC();
				listToteD();
				listToteE();
				listCrate();
			}
			else
			{
				normal = true;
				if( data.contains("ziptie") || data.contains("zip tie") )
				{
					ziptie = true;
				}
			}
		}
		return normal;
	}
	public static void parse(String input)
	{
		String data = input.toLowerCase();
		while( data.endsWith(" ") )
		{
			data = data.substring(0,data.length()-1);
		}
		if( data.endsWith(".") || data.endsWith("?") || data.endsWith("!") )
		{
			data = data.substring(0,data.length()-1);
		}
		while( data.endsWith(" ") )
		{
			data = data.substring(0,data.length()-1);
		}
		data = data + " ";
		while( data.contains(" ") )
		{
			int end = data.indexOf(" ");
			String keyword = data.substring(0,end);
			String temp = data.substring(end+1,data.length());
			boolean pass = true;
			for( int f=0; f<23; f++ )
			{
				if(keyword.equals(Exclusion[f]))
				{
					pass = false;
					break;
				}
			}
			if( keyword.length() < 3 )
			{
				pass = false;
			}
			if( pass )
			{
				if( debugMode )
				{
					System.out.println(keyword);
				}
				keywords[keywordPointer] = keyword;
				keywordPointer++;
			}
			data = temp;
		}
	}
	public static void reset()
	{
		resultPointer = 0;
		keywordPointer = 0;
		for( int f=0; f<printedPointer; f++ )
		{
			printed[f] = "";
		}
		printedPointer = 1;
		ziptie = false;
	}
	public static void menu()
	{
		System.out.println("How else may I help you?");
	}
	public static void output()
	{
		if( resultPointer == 0 )
		{
			System.out.println("Sorry, I couldn't find what you were looking for. Maybe you meant something else?");
		}
		else
		{
			System.out.println("Okay, here's what I found:");
		}
		System.out.println("");
		if( p[0] > 0 )
		{
			System.out.println("In the Toolbox, we should theoretically have the following items:");
			for( int f=0; f<p[0]; f++ )
			{
				if(antiRepeat(ToolBox[results[f][1]]))
				{
					System.out.println(ToolBox[results[f][1]]);
				}
			}
			System.out.println("");
		}
		if( p[1] > 0 )
		{
			System.out.println("In Tote A, we should theoretically have the following items:");
			for( int f=p[0]; f<p[0]+p[1]; f++ )
			{
				if(antiRepeat(ToteA[results[f][1]]))
				{
					System.out.println(ToteA[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( p[2] > 0 )
		{
			System.out.println("In Tote B, we should theoretically have the following items:");
			for( int f=p[0]+p[1]; f<p[0]+p[1]+p[2]; f++ )
			{
				if(antiRepeat(ToteB[results[f][1]]))
				{
					System.out.println(ToteB[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( p[3] > 0 )
		{
			System.out.println("In Tote C, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]; f<p[0]+p[1]+p[2]+p[3]; f++ )
			{
				if(antiRepeat(ToteC[results[f][1]]))
				{
					System.out.println(ToteC[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( p[4] > 0 )
		{
			System.out.println("In Tote D, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]; f<p[0]+p[1]+p[2]+p[3]+p[4]; f++ )
			{
				if(antiRepeat(ToteD[results[f][1]]))
				{
					System.out.println(ToteD[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( p[5] > 0 )
		{
			System.out.println("In Tote E, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f++ )
			{
				if(antiRepeat(ToteE[results[f][1]]))
				{
					System.out.println(ToteE[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( p[6] > 0 )
		{
			System.out.println("In the Crate, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]; f++ )
			{
				if(antiRepeat(Crate[results[f][1]]))
				{
					System.out.println(Crate[results[f][1]]);
				}			}
			System.out.println("");
		}
		if( ziptie )
		{
			System.out.println("There's also a whole bunch in Trinity's hair.");
		}
	}
	public static String input()
	{
		Scanner sc = new Scanner(System.in);
		String query = sc.nextLine();
		return query;
	}
	public static void search()
	{
		checkToolBox();
		checkToteA();
		checkToteB();
		checkToteC();
		checkToteD();
		checkToteE();
		checkCrate();
	}
	public static void checkToolBox()
	{
		p[0] = 0;
		for( int f=0; f<41; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TLDesc[f].toLowerCase()) || TLDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[0]++;
					resultPointer++;
					results[resultPointer-1][0] = 0;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkToteA()
	{
		p[1] = 0;
		for( int f=0; f<20; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TADesc[f].toLowerCase()) || TADesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[1]++;
					resultPointer++;
					results[resultPointer-1][0] = 1;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkToteB()
	{
		p[2] = 0;
		for( int f=0; f<11; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TBDesc[f].toLowerCase()) || TBDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[2]++;
					resultPointer++;
					results[resultPointer-1][0] = 2;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkToteC()
	{
		p[3] = 0;
		for( int f=0; f<18; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TCDesc[f].toLowerCase()) || TCDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[3]++;
					resultPointer++;
					results[resultPointer-1][0] = 3;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkToteD()
	{
		p[4] = 0;
		for( int f=0; f<25; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TDDesc[f].toLowerCase()) || TDDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[4]++;
					resultPointer++;
					results[resultPointer-1][0] = 4;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkToteE()
	{
		p[5] = 0;
		for( int f=0; f<8; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(TEDesc[f].toLowerCase()) || TEDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[5]++;
					resultPointer++;
					results[resultPointer-1][0] = 5;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void checkCrate()
	{
		p[6] = 0;
		for( int f=0; f<16; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(CDesc[f].toLowerCase()) || CDesc[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[6]++;
					resultPointer++;
					results[resultPointer-1][0] = 6;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void loadLibrary()
	{
		loadToolBox();
		loadToteA();
		loadToteB();
		loadToteC();
		loadToteD();
		loadToteE();
		loadCrate();
		loadTLDesc();
		loadTADesc();
		loadTBDesc();
		loadTCDesc();
		loadTDDesc();
		loadTEDesc();
		loadCDesc();
		loadExclusion();
		loadPeople();
	}
	public static void loadToolBox()
	{
		ToolBox[0] = "Precision Screwdriver Set";
		ToolBox[1] = "Goo-gone";
		ToolBox[2] = "Files";
		ToolBox[3] = "Erwin Clamps";
		ToolBox[4] = "Metal Clamps";
		ToolBox[5] = "Flashlight";
		ToolBox[6] = "Craftsman's square";
		ToolBox[7] = "Flathead";
		ToolBox[8] = "Phillips";
		ToolBox[9] = "Icepick";
		ToolBox[10] = "Utility";
		ToolBox[11] = "Deburring tool";
		ToolBox[12] = "Paper ruler";
		ToolBox[13] = "Loctite";
		ToolBox[14] = "Bent Needlenose (Red & Black, orange)";
		ToolBox[15] = "Wire Crusher (orange)";
		ToolBox[16] = "Needlenose (orange)";
		ToolBox[17] = "Drill Bit row thing";
		ToolBox[18] = "Big Needlenose (Grey)";
		ToolBox[19] = "Box Cutter";
		ToolBox[20] = "Wire Stripper/Crimper";
		ToolBox[21] = "Snub-Nose Pliers";
		ToolBox[22] = "Needlenose (green)";
		ToolBox[23] = "Stab-Stab";
		ToolBox[24] = "Chain Breaker";
		ToolBox[25] = "SAE Bit Row";
		ToolBox[26] = "Snub-Nose Pliers (orange, black-red)";
		ToolBox[27] = "SAE Hex Keys x2";
		ToolBox[28] = "Wire Stripper";
		ToolBox[29] = "Snips";
		ToolBox[30] = "Blue Vise Grip";
		ToolBox[31] = "Mallet";
		ToolBox[32] = "Tiny snips";
		ToolBox[33] = "Hammer";
		ToolBox[34] = "Ruler";
		ToolBox[35] = "Measuring Tape";
		ToolBox[36] = "Robo-Grip";
		ToolBox[37] = "Tiny Tape Measure";
		ToolBox[38] = "Aviation Snips";
		ToolBox[39] = "Wago";
		ToolBox[40] = "Scissors";
	}
	public static void loadToteA()
	{
		ToteA[0] = "Rivet Box";
		ToteA[1] = "Pneumatics Hardware Box";
		ToteA[2] = "Zip Tie Box";
		ToteA[3] = "Encoders";
		ToteA[4] = "Pressure Tape";
		ToteA[5] = "Hack Saw";
		ToteA[6] = "PWM Crimp Tool";
		ToteA[7] = "Pneumatic Wheels";
		ToteA[8] = "Grease";
		ToteA[9] = "craftsman drill bit set";
		ToteA[10] = "rivet gun";
		ToteA[11] = "Tape Box";
		ToteA[12] = "Gorilla Tape";
		ToteA[13] = "Clear Packing tape";
		ToteA[14] = "Electrical Tape, Red";
		ToteA[15] = "Electrical Tape, Black";
		ToteA[16] = "Painter's Tape, Blue";
		ToteA[17] = "Crazy glue ";
		ToteA[18] = "Black Rachet Set";
		ToteA[19] = "Drills + chargers";	}
	public static void loadToteB()
	{
		ToteB[0] = "Polycord Box (+tread, radio, camera)";
		ToteB[1] = "Solder Box (+chain)";
		ToteB[2] = "Vex Pro Box (versas/ringlight/victor)";
		ToteB[3] = "SAE Tap Set";
		ToteB[4] = "Spacer Box";
		ToteB[5] = "Warrior Set";
		ToteB[6] = "Box End Rachets";
		ToteB[7] = "Compressed Air";
		ToteB[8] = "Tap Fluid";
		ToteB[9] = "Caliper";
		ToteB[10] = "Blue Rachet Set";
	}
	public static void loadToteC()
	{
		ToteC[0] = "Hardstop (orange)";
		ToteC[1] = "Gears Stuff Box";
		ToteC[2] = "Mcmaster box";
		ToteC[3] = "Red Battery Wire";
		ToteC[4] = "Black Battery Wire";
		ToteC[5] = "Pickup hardware bag(small)";
		ToteC[6] = "Wire box";
		ToteC[7] = "Victors";
		ToteC[8] = "Pneumatic Tubing Bag";
		ToteC[9] = "Versas";
		ToteC[10] = "Long Pistons";
		ToteC[11] = "Air Tanks";
		ToteC[12] = "Random Sheet Metal ";
		ToteC[13] = "Sponsor panels";
		ToteC[14] = "Breakers";
		ToteC[15] = "Pneumatic Tubing Blue and Orange";
		ToteC[16] = "Optical Sensor";
		ToteC[17] = "Pickup hardware bag(big)";
	}
	public static void loadToteD()
	{
		ToteD[0] = "Pit Bag";
		ToteD[1] = "White Board";
		ToteD[2] = "Staple Bag";
		ToteD[3] = "Staple Gun";
		ToteD[4] = "Paint brushes+paint";
		ToteD[5] = "Red Fabric";
		ToteD[6] = "Blue Fabric";
		ToteD[7] = "Bumper Bolts + Nuts Bag";
		ToteD[8] = "Standard";
		ToteD[9] = "Power Strips ";
		ToteD[10] = "Extension cords";
		ToteD[11] = "Mutimeter";
		ToteD[12] = "vise bit";
		ToteD[13] = "Radio power cord";
		ToteD[14] = "Ethernet";
		ToteD[15] = "Ball";
		ToteD[16] = "Crowbar";
		ToteD[17] = "Crate Screws(box)";
		ToteD[18] = "Pool noodle";
		ToteD[19] = "1x1 tubing";
		ToteD[20] = "Gorilla Tape";
		ToteD[21] = "Velcro";
		ToteD[22] = "Pneumatics plate";
		ToteD[23] = "2 CIMs Box";
		ToteD[24] = "4 Dry Erase Markers";
	}
	public static void loadToteE()
	{
		ToteE[0] = "Chain Box";
		ToteE[1] = "Plates Stuff Box";
		ToteE[2] = "Clear Screw Box";
		ToteE[3] = "Mantis hardware bag";
		ToteE[4] = "Crappy Drill Bit Set";
		ToteE[5] = "Electrical Stuff box";
		ToteE[6] = "Anderson Box";
		ToteE[7] = "Rag Bag";
	}
	public static void loadCrate()
	{
		Crate[0] = "long hex stock (3)";
		Crate[1] = "standard stand";
		Crate[2] = "vise";
		Crate[3] = "tarp ";
		Crate[4] = "white shelves";
		Crate[5] = "EZ Up";
		Crate[6] = "robot";
		Crate[7] = "Bumpers";
		Crate[8] = "banner";
		Crate[9] = "Blue Banner";
		Crate[10] = "sheet metal";
		Crate[11] = "big shelves";
		Crate[12] = "Shop vac";
		Crate[13] = "push cart (1)";
		Crate[14] = "Orange Safety Kit";
		Crate[15] = "knee pads";
	}
	public static void loadExclusion()
	{
		Exclusion[0] = "and";
		Exclusion[1] = "or";
		Exclusion[2] = "where";
		Exclusion[3] = "what";
		Exclusion[4] = "which";
		Exclusion[5] = "i";
		Exclusion[6] = "am";
		Exclusion[7] = "look";
		Exclusion[8] = "search";
		Exclusion[9] = "for";
		Exclusion[10] = "a";
		Exclusion[11] = "the";
		Exclusion[12] = "an";
		Exclusion[13] = "are";
		Exclusion[14] = "is";
		Exclusion[15] = "in";
		Exclusion[16] = "inside";
		Exclusion[17] = "at";
		Exclusion[18] = "you";
		Exclusion[19] = "find";
		Exclusion[20] = "me";
		Exclusion[21] = "tell";
		Exclusion[22] = "could";
	}
	public static void loadPeople()
	{
		People[0] = "justin";
		People[1] = "pranav";
		People[2] = "aanya";
		People[3] = "anya";
		People[4] = "thuy";
		People[5] = "evan";
		People[6] = "rayna";
		People[7] = "antoni";
		People[8] = "kunal";
		People[9] = "canoe";
		People[10] = "hayley";
		People[11] = "arrington";
		People[12] = "tibbs";
		People[13] = "jessica";
		People[14] = "raymond";
		People[15] = "alex";
		People[16] = "robert";
		People[17] = "nathan";
		People[18] = "trinity";
		People[19] = "reyna";
		People[20] = "rohan";
		People[21] = "russel";
		People[22] = "matt";
		People[23] = "matthew";
		People[24] = "ryan";
		People[25] = "joey";
		People[26] = "kovalik";
		People[27] = "joseph";
		People[28] = "zain";
	}
	public static void loadTLDesc()
	{
		TLDesc[0] = "Precision Screwdrivers Sets screws ";
		TLDesc[1] = "Goo-gone goo gone removers";
		TLDesc[2] = "Files papers documents";
		TLDesc[3] = "Erwin Clamps";
		TLDesc[4] = "Metal Clamps";
		TLDesc[5] = "Flashlights";
		TLDesc[6] = "Craftsman's square";
		TLDesc[7] = "Flathead screwdrivers screws";
		TLDesc[8] = "Phillips screwdriver screws";
		TLDesc[9] = "Icepicks pokey little";
		TLDesc[10] = "Utility utilities";
		TLDesc[11] = "Deburring tool ";
		TLDesc[12] = "Paper rulers measures";
		TLDesc[13] = "Loctites lock tight";
		TLDesc[14] = "Bent Needlenoses (Red & Black, orange)";
		TLDesc[15] = "Wire Crushers (orange)";
		TLDesc[16] = "Needlenoses (orange)";
		TLDesc[17] = "Drills Bits rows thing";
		TLDesc[18] = "Big Needlenoses (Grey)";
		TLDesc[19] = "Boxes Cutters scissors knife knives";
		TLDesc[20] = "Wires Strippers/Crimpers";
		TLDesc[21] = "Snub-Noses Pliers snubs noses";
		TLDesc[22] = "Needlenoses (green)";
		TLDesc[23] = "Stab-Stab stabs stab";
		TLDesc[24] = "Chains Breakers";
		TLDesc[25] = "SAE Bits Rows";
		TLDesc[26] = "Snub-Nose Pliers (orange, black-red)";
		TLDesc[27] = "SAE Hex Keys x2";
		TLDesc[28] = "Wires Strippers";
		TLDesc[29] = "Snips";
		TLDesc[30] = "Blue Vise Grips";
		TLDesc[31] = "Mallets hammers";
		TLDesc[32] = "Tiny snips";
		TLDesc[33] = "Hammers";
		TLDesc[34] = "Rulers measures";
		TLDesc[35] = "Measuring Tape";
		TLDesc[36] = "Robo-Grips";
		TLDesc[37] = "Tiny Tape Measures rulers";
		TLDesc[38] = "Aviation Snips";
		TLDesc[39] = "Wago tools screwdrivers";
		TLDesc[40] = "Scissors";
	}
	public static void loadTADesc()
	{
		TADesc[0] = "Rivets Boxes";
		TADesc[1] = "Pneumatics Hardware Boxes";
		TADesc[2] = "Zip Tie Boxes ziptie";
		TADesc[3] = "Encoders";
		TADesc[4] = "Pressure Tapes";
		TADesc[5] = "Hack Saws";
		TADesc[6] = "PWM Crimp Tools";
		TADesc[7] = "Pneumatic Wheels";
		TADesc[8] = "Greases";
		TADesc[9] = "craftsman drill bits sets";
		TADesc[10] = "rivet guns";
		TADesc[11] = "Tape Boxes";
		TADesc[12] = "Gorilla Tapes";
		TADesc[13] = "Clear Packing tapes";
		TADesc[14] = "Electrical Tapes, Red";
		TADesc[15] = "Electrical Tapes, Black";
		TADesc[16] = "Painter's Tapes, Blue";
		TADesc[17] = "Crazy glues";
		TADesc[18] = "Black Rachets Sets";
		TADesc[19] = "Drills + chargers";	}
	public static void loadTBDesc()
	{
		TBDesc[0] = "Polycords Boxes (+treads, radios, cameras)";
		TBDesc[1] = "Solder Boxes (+chains)";
		TBDesc[2] = "Vex Pro Boxes (versas/ringlights/victors) vexpros";
		TBDesc[3] = "SAE Tap Sets";
		TBDesc[4] = "Spacer Boxes";
		TBDesc[5] = "Warrior Sets";
		TBDesc[6] = "Box End Rachets";
		TBDesc[7] = "Compressed Air compress";
		TBDesc[8] = "Tap Fluids liquids";
		TBDesc[9] = "Calipers";
		TBDesc[10] = "Blue Rachets Sets";
	}
	public static void loadTCDesc()
	{
		TCDesc[0] = "Hardstops (orange)";
		TCDesc[1] = "Gears Stuff Boxes";
		TCDesc[2] = "Mcmaster boxes";
		TCDesc[3] = "Red Battery batteries Wires electric cables cords";
		TCDesc[4] = "Black Battery Wires batteries electric cables cords";
		TCDesc[5] = "Pickup hardware bags(small)";
		TCDesc[6] = "Wires cables electric cords boxes ";
		TCDesc[7] = "Victors motors";
		TCDesc[8] = "Pneumatics Tubing Bags";
		TCDesc[9] = "Versas";
		TCDesc[10] = "Long Pistons pneumatics";
		TCDesc[11] = "Air Tanks pneumatics";
		TCDesc[12] = "Random Sheets Metal scrap";
		TCDesc[13] = "Sponsor panels";
		TCDesc[14] = "Breakers electric";
		TCDesc[15] = "Pneumatics Tubing Blue and Orange";
		TCDesc[16] = "Optical Sensors";
		TCDesc[17] = "Pickup hardware bags(big)";
	}
	public static void loadTDDesc()
	{
		TDDesc[0] = "Pit Bags";
		TDDesc[1] = "White Boards";
		TDDesc[2] = "Staples Bags";
		TDDesc[3] = "Staples Guns";
		TDDesc[4] = "Paint brushes+paint";
		TDDesc[5] = "Red Fabrics";
		TDDesc[6] = "Blue Fabrics";
		TDDesc[7] = "Bumpers Bolts + Nuts Bags";
		TDDesc[8] = "Standards flags";
		TDDesc[9] = "Power Strips electric";
		TDDesc[10] = "Extension cords power";
		TDDesc[11] = "Multimeters";
		TDDesc[12] = "vise bits";
		TDDesc[13] = "Radio power cords cables wires";
		TDDesc[14] = "Ethernet cables wires";
		TDDesc[15] = "Balls";
		TDDesc[16] = "Crowbars";
		TDDesc[17] = "CDesc Screws(box)";
		TDDesc[18] = "Pool noodles";
		TDDesc[19] = "1x1 tubing";
		TDDesc[20] = "Gorilla Tape";
		TDDesc[21] = "Velcro";
		TDDesc[22] = "Pneumatics plates";
		TDDesc[23] = "2 CIMs Boxes";
		TDDesc[24] = "4 Dry Erase Markers";
	}
	public static void loadTEDesc()
	{
		TEDesc[0] = "Chain Boxes";
		TEDesc[1] = "Plates Stuff Boxes";
		TEDesc[2] = "Clear Screw Boxes";
		TEDesc[3] = "Mantis hardware bags";
		TEDesc[4] = "Crappy Drills Bits Sets";
		TEDesc[5] = "Electrical Stuff boxes";
		TEDesc[6] = "Anderson Boxes";
		TEDesc[7] = "Rags Bags";
	}
	public static void loadCDesc()
	{
		CDesc[0] = "long hex stocks (3)";
		CDesc[1] = "standard stands";
		CDesc[2] = "vises";
		CDesc[3] = "tarps";
		CDesc[4] = "white shelves";
		CDesc[5] = "EZ Ups";
		CDesc[6] = "robots";
		CDesc[7] = "Bumpers";
		CDesc[8] = "banners";
		CDesc[9] = "Blue Banners chairmans";
		CDesc[10] = "sheets metal";
		CDesc[11] = "big shelves";
		CDesc[12] = "Shop vacs";
		CDesc[13] = "push carts (1)";
		CDesc[14] = "Orange Safety Kits";
		CDesc[15] = "knee pads";
	}
	public static void listToolBox()
	{
		System.out.println("The Toolbox contains the following:");
		for( int f=0; f<41; f++ )
		{
			System.out.println(ToolBox[f]);
		}
		System.out.println();
	}
	public static void listToteA()
	{
		System.out.println("Tote A contains the following:");
		for( int f=0; f<20; f++ )
		{
			System.out.println(ToteA[f]);
		}
		System.out.println();
	}
	public static void listToteB()
	{
		System.out.println("Tote B contains the following:");
		for( int f=0; f<11; f++ )
		{
			System.out.println(ToteB[f]);
		}
		System.out.println();
	}
	public static void listToteC()
	{
		System.out.println("Tote C contains the following:");
		for( int f=0; f<18; f++ )
		{
			System.out.println(ToteC[f]);
		}
		System.out.println();
	}
	public static void listToteD()
	{
		System.out.println("Tote D contains the following:");
		for( int f=0; f<25; f++ )
		{
			System.out.println(ToteD[f]);
		}
		System.out.println();
	}
	public static void listToteE()
	{
		System.out.println("Tote E contains the following:");
		for( int f=0; f<8; f++ )
		{
			System.out.println(ToteE[f]);
		}
		System.out.println();
	}
	public static void listCrate()
	{
		System.out.println("The Crate contains the following:");
		for( int f=0; f<16; f++ )
		{
			System.out.println(Crate[f]);
		}
		System.out.println();
	}
	public static boolean antiRepeat(String check)
	{
		boolean b = true;
		for( int f=0; f<printedPointer; f++ )
		{
			if(check.equals(printed[f]))
			{
				b = false;
			}
		}
		if( b )
		{
			printed[printedPointer-1] = check;
			printedPointer++;
		}
		return b;
	}
	public static void borrow()
	{
		System.out.println("Do you want to check what we borrowed, or what was borrowed from us?");
		boolean repeat = true;
		boolean in = false;
		boolean out = false;
		while( repeat )
		{
			repeat = false;
			String input = input();
			String data = input.toLowerCase();
			if( data.contains("we") && data.contains("borrowed") || data.contains("us") && data.contains("lent") )
			{
				in = true;
			}
			if( data.contains("we") && data.contains("lent") || data.contains("us") && data.contains("borrowed") )
			{
				out = true;
			}
			if( in && out )
			{
				System.out.println("You're going to have to pick one or the other.");	
				repeat = true;
			}
		}
		if( in )
		{
			
		}
		if( out )
		{
			
		}
	}
	public static void loadBorrow()
	{
		try
		{
			Scanner fin = new Scanner(new File("C:\borrow.txt"));
			while( fin.hasNextLine() )
			{
				String ignore1 = fin.nextLine();
				String ignore2 = fin.nextLine();
				String ignore3 = fin.nextLine();
				String ignore4 = fin.nextLine();
				String type = fin.nextLine();
				if( type.toLowerCase().equals("b") )
				{
					borrowedItem[borrowedPointer] = fin.nextLine();
					try
					{
						borrowedTeam[borrowedPointer] = fin.nextInt();
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						break;
					}
					borrowedPointer++;
				}
				else if( type.toLowerCase().equals("l") )
				{
					lentItem[lentPointer] = fin.nextLine();
					try
					{
					lentTeam[lentPointer] = fin.nextInt();
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						break;
					}
					lentPointer++;
				}
				else
				{
					System.out.println("That's not the correct syntax.");
					break;
				}
			}
		}
		catch( Exception E1 )
		{
			System.out.println("That's weird... I can't read any of this.");
		}
	}
}
