import java.util.Scanner;
import java.io.*;

public class HannahBot {

	public static String[] borrowedItem = new String[10000];
	public static String[] borrowedTeam = new String[10000];
	public static int borrowedPointer = 0;
	public static String[] lentItem = new String[10000];
	public static int[][] lentLoc = new int[10000][2];
	public static String[] lentTeam = new String[10000];
	public static int lentPointer = 0;
	public static String[] borrowedItemBU = new String[10000];
	public static String[] borrowedTeamBU = new String[10000];
	public static int borrowedPointerBU = 0;
	public static String[] lentItemBU = new String[10000];
	public static int[][] lentLocBU = new int[10000][2];
	public static String[] lentTeamBU = new String[10000];
	public static int lentPointerBU = 0;
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
	public static String[][] ToolBox = new String[42][2];
	public static String[][] ToteA = new String[20][2];
	public static String[][] ToteB = new String[11][2];
	public static String[][] ToteC = new String[18][2];
	public static String[][] ToteD = new String[25][2];
	public static String[][] ToteE = new String[8][2];
	public static String[][] Crate = new String[16][2];
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
	public static boolean adminRestart = true;
	public static boolean on = true;
	public static boolean borrowCheck = true;
	public static void main(String args[]) throws InterruptedException
	{
		/* [Organizer] [Main] */
		initialize();
		while( on )
		{
			conductor();
		}
	}
	public static void initialize()
	{
		/* [Organizer] [Load] */
		loadLibrary();
		if( !loaded() )
		{
			if(createFile() )
			{
				resetBorrow();
			}
		}
		loadBorrow();
		greet();
	}
	public static boolean createFile()
	{
		/* [Startup] [Load] [Borrow] */
		try
		{
        	FileWriter fw = new FileWriter("borrow.txt", true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter out = new PrintWriter(bw);
        	fw.close();
        	bw.close();
        	out.close();
		}
		catch( Exception E )
		{
			System.out.println("Error writing.");
		}
		return true;
	}
	public static boolean loaded()
	{
		/* [Startup] [Load] [Borrow] */
		boolean tf = false;
		String fileName = "borrow.txt";
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
        	System.out.println("Initial startup detected.");
        	System.out.println("Creating new file.");
        }
		return tf;
	}
	public static void greet()
	{
		/* [Startup] [Text] [Print] [Info] */
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=[Hannah Bot]=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println();
		System.out.println("   Hi, I'm HannahBot (v1.9). I can look for things, and tell you what's in our totes and boxes.");
		System.out.println("Hannah Bot (v1.9) Theoretically(TM) supports description-based queries and all sentence structures.");
		System.out.println("Hannah Bot (v1.9) Theoretically(TM) can now read and keep track of borrowed items from a file.");
		System.out.println();
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v1.9)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println();
		System.out.println("How may I help you?");
	}
	public static void conductor() throws InterruptedException
	{
		/* [Organizer] [Main] */
		reset();
		String input = input();
		boolean normal = caser(input);
		if( normal )
		{
			parse(input);
			search();
			output();
		}
		if( on )
		{
			menu();
		}
		else if( !on && borrowCheck )
		{
			System.out.println("Goodbye.");
		}
	}
	public static void reset()
	{
		/* [Cleanup] [Pointer] [Borrow] */
		resultPointer = 0;
		keywordPointer = 0;
		for( int f=0; f<printedPointer; f++ )
		{
			printed[f] = "";
		}
		printedPointer = 1;
		ziptie = false;
	}
	public static boolean caser(String input) throws InterruptedException
	{
		/* [Organizer] [Text] [Process] */
		boolean normal = false;
		boolean skip = false;
		boolean ignoreExclaim = false;
		String data = input.toLowerCase();
		if( data.contains("bye") )
		{
			shutDown();
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
			System.out.println("(v1.8) ::  Critical Bug Fix.");
			System.out.println("(v1.9) ::  Added ability to read and keep track of borrowed items from a file.");
			skip = true;
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
		if( data.contains("todo") || data.contains("to-do") )
		{
			System.out.println("1. Borrowed Item Tracking");
			System.out.println("2. Memory Modification");
			System.out.println("3. Emoji Support");
			System.out.println("4. Find repeat error");
			System.out.println("5. Consolidate pointers");
			System.out.println("6. Save reponses to a text file for easy translation for international teams.");
			System.out.println("7. Fix the ridiculously buggy borrow stuff");
			System.out.println("8. Add debug tags to all functions.");
			System.out.println("9. Organize cases [Standalone, Priority, Easter Egg, Repeatable]");
			skip = true;
		}
		if( data.contains("git") )
		{
			System.out.println("Go away, Ryan.");
			skip = true;
		}
		if( data.contains("cls") )
		{
			System.out.println("Cls? What's that? I'm supposed to be based off of a human being, Pranav.");
			skip = true;
		}
		if( (data.contains("reload") || data.contains("restore")) && data.contains("borrow") )
		{
			restoreBorrow();
			System.out.println("I've successfully restored the borrow file.");
			skip = true;
		}
		else if( (data.contains("clear") || data.contains("reset")) && data.contains("borrow") )
		{
			resetBorrow();
			System.out.println("I've cleared all memories of what we've borrowed.");
			System.out.println("I've saved a backup of it, though, so just let me know if you want to restore it.");
			skip = true;
		}
		else if( data.contains("borrow") || data.contains("lend") || data.contains("lent") )
		{
			data = " " + data + " ";
			if( data.contains("tell") || data.contains("list") || data.contains("print") )
			{
				listBorrow();
			}
			else if( data.contains("check") && !data.contains("out") )
			{
				listBorrow();
			}
			else if( (data.contains(" we ") && data.contains("borrow")) || (data.contains(" us ") && data.contains("lent")) )
			{
				borrow("in");
			}
			else if( (data.contains(" we ") && data.contains("lent")) || (data.contains(" us ") && data.contains("borrow")) )
			{
				borrow("out");
			}
			else if( data.contains("borrow") || data.contains("lend") || data.contains("lent") )
			{
				borrow("null");
			}
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
			System.out.println("Debug mode enabled.");
			skip = true;
		}
		else if( data.contains("debug") && data.contains(" off") )
		{
			debugMode = false;
			System.out.println("Debug mode disabled.");
			skip = true;
		}
		else if( data.contains("debug") && data.contains("toggle") )
		{
			debugMode = !debugMode;
			if( debugMode )
			{
				System.out.println("Debug mode enabled.");
			}
			else
			{
				System.out.println("Debug mode disabled.");
			}
			skip = true;
		}
		if( data.startsWith("hi!") || data.startsWith("hi ") || data.startsWith("hello") || data.startsWith("greetings") )
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
		if( data.contains("backpack") )
		{
			System.out.println("If you're looking for a backpack, I would ask Pranav.");
			skip = true;
		}
		if( data.contains("memes") )
		{
			System.out.println("Stop looking for memes.");
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
		/* [Data] [Text] [Process] */
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
	public static void menu()
	{
		/* [Cleanup] [Text] [Print] */
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
				if(antiRepeat(ToolBox[results[f][1]][0]))
				{
					if( ToolBox[results[f][1]][1] == "0" )
					{
						System.out.println(ToolBox[results[f][1]][0]);
					}
					if( ToolBox[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToolBox[results[f][1]][0] + " was lent to team " + ToolBox[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[1] > 0 )
		{
			System.out.println("In Tote A, we should theoretically have the following items:");
			for( int f=p[0]; f<p[0]+p[1]; f++ )
			{
				if(antiRepeat(ToteA[results[f][1]][0]))
				{
					if( ToteA[results[f][1]][1] == "0" )
					{
						System.out.println(ToteA[results[f][1]][0]);
					}
					if( ToteA[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToteA[results[f][1]][0] + " was lent to team " + ToteA[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[2] > 0 )
		{
			System.out.println("In Tote B, we should theoretically have the following items:");
			for( int f=p[0]+p[1]; f<p[0]+p[1]+p[2]; f++ )
			{
				if(antiRepeat(ToteB[results[f][1]][0]))
				{
					if( ToteB[results[f][1]][1] == "0" )
					{
						System.out.println(ToteB[results[f][1]][0]);
					}
					if( ToteB[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToteB[results[f][1]][0] + " was lent to team " + ToteB[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[3] > 0 )
		{
			System.out.println("In Tote C, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]; f<p[0]+p[1]+p[2]+p[3]; f++ )
			{
				if(antiRepeat(ToteC[results[f][1]][0]))
				{
					if( ToteC[results[f][1]][1] == "0" )
					{
						System.out.println(ToteC[results[f][1]][0]);
					}
					if( ToteC[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToteC[results[f][1]][0] + " was lent to team " + ToteC[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[4] > 0 )
		{
			System.out.println("In Tote D, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]; f<p[0]+p[1]+p[2]+p[3]+p[4]; f++ )
			{
				if(antiRepeat(ToteD[results[f][1]][0]))
				{
					if( ToteD[results[f][1]][1] == "0" )
					{
						System.out.println(ToteD[results[f][1]][0]);
					}
					if( ToteD[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToteD[results[f][1]][0] + " was lent to team " + ToteD[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[5] > 0 )
		{
			System.out.println("In Tote E, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f++ )
			{
				if(antiRepeat(ToteE[results[f][1]][0]))
				{
					if( ToteE[results[f][1]][1] == "0" )
					{
						System.out.println(ToteE[results[f][1]][0]);
					}
					if( ToteE[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + ToteE[results[f][1]][0] + " was lent to team " + ToteE[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( p[6] > 0 )
		{
			System.out.println("In the Crate, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]; f++ )
			{
				if(antiRepeat(Crate[results[f][1]][0]))
				{
					if( Crate[results[f][1]][1] == "0" )
					{
						System.out.println(Crate[results[f][1]][0]);
					}
					if( ToolBox[results[f][1]][1] != "0" )
					{
						System.out.println("* The " + Crate[results[f][1]][0] + " was lent to team " + Crate[results[f][1]][1] + ".");
					}
				}
			}
			System.out.println("");
		}
		if( ziptie )
		{
			System.out.println("There's also a whole bunch in Trinity's hair.");
		}
	}
	public static String input()
	{
		/* [IO] [Text] [Input] */
		Scanner sc = new Scanner(System.in);
		boolean repeat = true;
		String query = "";
		while( repeat )
		{
			repeat = false;
			query = sc.nextLine();
			if( query == "" )
			{
				repeat = true;
				System.out.println("I'm going to need something more specific.");
			}
		}
		return query;
	}
	public static void search()
	{
		/* [Organizer] [Text] [Process] */
		checkToolBox();
		checkToteA();
		checkToteB();
		checkToteC();
		checkToteD();
		checkToteE();
		checkCrate();
	}
	public static boolean antiRepeat(String check)
	{
		/* [Support] [Text] [Pointer] */
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
	public static void checkToolBox()
	{
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
		/* [Search] [Data] [Memory] */
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
	public static void borrow(String inout)
	{
		/* [Organizer] [Borrow] [IO] */
		if( debugMode )
		{
			System.out.println("borrow() called with input + '" + inout + "'.");
		}
		boolean check = false;
		boolean repeat = true;
		boolean in = false;
		boolean out = false;
		if( inout.equals("in") )
		{
			in = true;
		}
		if( inout.equals("out") )
		{
			out = true;
		}
		if( inout.equals("null") )
		{
			check = true;
			System.out.println("Do you want to record what we borrowed, or what was borrowed from us?");
		}
		String IO = "";
		String Item = "";
		String Team = "";
		if( check )
			{
			while( repeat )
			{
				String input = input();
				String data = input.toLowerCase();
				data = " " + data + " ";
				if( (data.contains(" we ") && data.contains("borrow")) || (data.contains(" us ") && data.contains("lent")) )
				{
					in = true;
				}
				if( (data.contains(" we ") && data.contains("lent")) || (data.contains(" us ") && data.contains("borrow")) )
				{
					out = true;
				}
				repeat = false;
				if( in && out )
				{
					System.out.println("You're going to have to pick one or the other.");	
					repeat = true;
				}
			}
		}
		boolean go = false;
		if( in )
		{
			IO = "B";
			System.out.println("What item did we borrow?");
			Item = input();
			System.out.println("Which team did we borrow from?");
			Team = input();
			go = true;
		}
		if( out )
		{
			IO = "L";
			System.out.println("What item did we lend?");
			Item = input();
			Item = Item.toLowerCase();
			repeat = true;
			System.out.println("Which team did we lend to?");
			Team = input();
			int tb = ToolBoxCB(Item);
			int a = ToteACB(Item);
			int b = ToteBCB(Item);
			int c = ToteCCB(Item);
			int d = ToteDCB(Item);
			int e = ToteECB(Item);
			int crate = CrateCB(Item);
			if( tb+a+b+c+d+e+crate != 0 )
			{
				go = true;
			}
		}
		if(go)
		{
			borrowWrite(IO + "~" + Item + "~" + Team + "~");
			loadBorrow();
		}
		else if( !go )
		{
			System.out.println("I dont think we have '" + Item + "'.");
		}
	}
	public static void loadBorrow()
	{
		/* [Startup] [Borrow] [IO] */
		if( debugMode )
		{
			System.out.println("loadBorrow() called");
		}
        String fileName = "borrow.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {
            	String item = "";
            	if( line.startsWith("//") )
            	{
            		String ignore = line;
            	}
            	else if( line.toLowerCase().equals("b") )
				{
            		boolean good = true;
            		line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try
					{
						line = bufferedReader.readLine();
						tempTeam = line;
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						borrowedItem[borrowedPointer] = tempString;
						borrowedTeam[borrowedPointer] = tempTeam;
						borrowedPointer++;
					}
				}
				else if( line.toLowerCase().equals("l") )
				{
					boolean good = true;
					line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try
					{
						line = bufferedReader.readLine();
						tempTeam = line;
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						item = tempString.toLowerCase();
						int tb = ToolBoxCB(item);
						int a = ToteACB(item);
						int b = ToteBCB(item);
						int c = ToteCCB(item);
						int d = ToteDCB(item);
						int e = ToteECB(item);
						int crate = CrateCB(item);
						if( tb+a+b+c+d+e+crate != 0 )
						{
							if( tb != 0 )
							{
								lentLoc[lentPointer][0] = 0;
								lentLoc[lentPointer][1] = tb;
								ToolBox[tb][1] = tempTeam;
							}
							if( a != 0 )
							{
								lentLoc[lentPointer][0] = 1;
								lentLoc[lentPointer][1] = a;
								ToteA[a][1] = tempTeam;
							}
							if( b != 0 )
							{
								lentLoc[lentPointer][0] = 2;
								lentLoc[lentPointer][1] = b;
								ToteB[b][1] = tempTeam;
							}
							if( c != 0 )
							{
								lentLoc[lentPointer][0] = 3;
								lentLoc[lentPointer][1] = c;
								ToteC[c][1] = tempTeam;
							}
							if( d != 0 )
							{
								lentLoc[lentPointer][0] = 4;
								lentLoc[lentPointer][1] = d;
								ToteD[d][1] = tempTeam;
							}
							if( e != 0 )
							{
								lentLoc[lentPointer][0] = 5;
								lentLoc[lentPointer][1] = e;
								ToteE[e][1] = tempTeam;
							}
							if( crate != 0 )
							{
								lentLoc[lentPointer][0] = 6;
								lentLoc[lentPointer][1] = crate;
								Crate[crate][1] = tempTeam;
							}
							lentItem[lentPointer] = tempString;
							lentTeam[lentPointer] = tempTeam;
							lentPointer++;
						}
						else
						{
							System.out.println("I don't think we have '" + item + "'.");
							System.out.println("You may want to check the borrow file, or reset it.");
							break;
						}
					}
				}
            }   
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }
	}
	public static void write(String string)
	{
		/* [IO] [Borrow] */
		if( debugMode )
		{
			System.out.println("write() called with input '" + string + "'.");
		}
		try{

            FileWriter fstream = new FileWriter("borrow.txt",true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(string);
            fbw.newLine();
            fbw.close();
        }catch (Exception e) {
            System.out.println("Couldn't print to the file.");
        }

    }
	public static void borrowWrite(String writer)
	{
		/* [Organizer] [Borrow] [IO] */
		if( debugMode )
		{
			System.out.println("borrowWrite() called with input '" + writer + "'.");
		}
		int end = writer.indexOf("~");
		String BL = writer.substring(0,end);
		String temp = writer.substring(end+1,writer.length());
		end = temp.indexOf("~");
		String item = temp.substring(0,end);
		temp = temp.substring(end+1,temp.length());
		end = temp.indexOf("~");
		String team = temp.substring(0,end);
		temp = temp.substring(end+1,temp.length());
		write(BL);
		write(item);
		write(team);
	}
	public static void clearBorrow()
	{
		/* [Cleanup] [Borrow] [Memory] */
		if( debugMode )
		{
			System.out.println("clearBorrow() called");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("borrow.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("You can't reset something I don't have yet.");
		}
	}
	public static void resetBorrow()
	{
		if( debugMode )
		{
			System.out.println("resetBorrow() called");
		}
		loadToolBox();
		loadToteA();
		loadToteB();
		loadToteC();
		loadToteD();
		loadToteE();
		loadCrate();
		clearBorrow();
		// back up text file contents!
		borrowedItemBU = borrowedItem;
		borrowedTeamBU = borrowedTeam;
		borrowedPointerBU = borrowedPointer;
		lentItemBU = lentItem;
		lentLocBU = lentLoc;
		lentTeamBU = lentTeam;
		lentPointerBU = lentPointer;
		write("// adminRestart = false");
		write("// This is the file where the borrowed items are stored.");
		write("// Line 1: B/L (Borrowed/Lent)");
		write("// Line 2: Item Name (Exact)");
		write("// Line 3: Team No.");
		borrowedPointer = 0;
		lentPointer = 0;
		for( int f=0; f<10000; f++ )
		{
			borrowedItem[f] = "";
			borrowedTeam[f] = "";
			lentItem[f] = "";
			lentLoc[f][0] = 0;
			lentLoc[f][1] = 0;
			lentTeam[f] = "";
		}
	}
	public static void restoreBorrow()
	{
		/* [Cleanup] [Borrow] [Memory] */
		if( debugMode )
		{
			System.out.println("restoreBorrow() called");
		}
		// remember to add back the file stuff
		borrowedItem = borrowedItemBU;
		borrowedTeam = borrowedTeamBU;
		borrowedPointer = borrowedPointerBU;
		lentItem = lentItemBU;
		lentLoc = lentLocBU;
		lentTeam = lentTeamBU;
		lentPointer = lentPointerBU;
	}
	public static void listBorrow()
	{
		/* [Borrow] [Text] [Print] [Info] */
		String fileName = "borrow.txt";
        // We borrowed 'a box' from 000.
		// We lent 'rivet gun' to 000.
		String piece0 = "We ";
		String piece1 = "";
		String piece2 = "";
		String piece3 = "";
		String piece4 = "";
		String piece5 = ".";
		String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {
            	String item = "";
            	if( line.startsWith("//") )
            	{
            		String ignore = line;
            	}
            	else if( line.toLowerCase().equals("b") )
				{
            		boolean good = true;
            		line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try
					{
						line = bufferedReader.readLine();
						tempTeam = line;
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						piece1 = "borrowed '";
						piece2 = tempString;
						piece3 = "' from ";
						piece4 = tempTeam;
						System.out.println(piece0+piece1+piece2+piece3+piece4+piece5);
					}
				}
				else if( line.toLowerCase().equals("l") )
				{
					boolean good = true;
					line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try
					{
						line = bufferedReader.readLine();
						tempTeam = line;
					}
					catch(Exception e)
					{
						System.out.println("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						piece1 = "lent '";
						piece2 = tempString;
						piece3 = "' to ";
						piece4 = tempTeam;
						System.out.println(piece0+piece1+piece2+piece3+piece4+piece5);
					}
				}
			} 
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
	public static void loadLibrary()
	{
		/* [Startup] [Organizer] [Load] [Memory] */
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
		/* [Load] [Memory] */
		ToolBox[0][0] = "Precision Screwdriver Set";
		ToolBox[1][0] = "Goo-gone";
		ToolBox[2][0] = "Files";
		ToolBox[3][0] = "Erwin Clamps";
		ToolBox[4][0] = "Metal Clamps";
		ToolBox[5][0] = "Flashlight";
		ToolBox[6][0] = "Craftsman's square";
		ToolBox[7][0] = "Flathead";
		ToolBox[8][0] = "Phillips";
		ToolBox[9][0] = "Icepick";
		ToolBox[10][0] = "Utility";
		ToolBox[11][0] = "Deburring tool";
		ToolBox[12][0] = "Paper ruler";
		ToolBox[13][0] = "Loctite";
		ToolBox[14][0] = "Bent Needlenose (Red & Black, orange)";
		ToolBox[15][0] = "Wire Crusher (orange)";
		ToolBox[16][0] = "Needlenose (orange)";
		ToolBox[17][0] = "Drill Bit row thing";
		ToolBox[18][0] = "Big Needlenose (Grey)";
		ToolBox[19][0] = "Box Cutter";
		ToolBox[20][0] = "Wire Stripper/Crimper";
		ToolBox[21][0] = "Snub-Nose Pliers";
		ToolBox[22][0] = "Needlenose (green)";
		ToolBox[23][0] = "Stab-Stab";
		ToolBox[24][0] = "Chain Breaker";
		ToolBox[25][0] = "SAE Bit Row";
		ToolBox[26][0] = "Snub-Nose Pliers (orange, black-red)";
		ToolBox[27][0] = "SAE Hex Keys x2";
		ToolBox[28][0] = "Wire Stripper";
		ToolBox[29][0] = "Snips";
		ToolBox[30][0] = "Blue Vise Grip";
		ToolBox[31][0] = "Mallet";
		ToolBox[32][0] = "Tiny snips";
		ToolBox[33][0] = "Hammer";
		ToolBox[34][0] = "Ruler";
		ToolBox[35][0] = "Measuring Tape";
		ToolBox[36][0] = "Robo-Grip";
		ToolBox[37][0] = "Tiny Tape Measure";
		ToolBox[38][0] = "Aviation Snips";
		ToolBox[39][0] = "Wago";
		ToolBox[40][0] = "Scissors";
		for( int f=0; f<41; f++ )
		{
			ToolBox[f][1] = "0";
		}
	}
	public static void loadToteA()
	{
		/* [Load] [Memory] */
		ToteA[0][0] = "Rivet Box";
		ToteA[1][0] = "Pneumatics Hardware Box";
		ToteA[2][0] = "Zip Tie Box";
		ToteA[3][0] = "Encoders";
		ToteA[4][0] = "Pressure Tape";
		ToteA[5][0] = "Hack Saw";
		ToteA[6][0] = "PWM Crimp Tool";
		ToteA[7][0] = "Pneumatic Wheels";
		ToteA[8][0] = "Grease";
		ToteA[9][0] = "craftsman drill bit set";
		ToteA[10][0] = "rivet gun";
		ToteA[11][0] = "Tape Box";
		ToteA[12][0] = "Gorilla Tape";
		ToteA[13][0] = "Clear Packing tape";
		ToteA[14][0] = "Electrical Tape, Red";
		ToteA[15][0] = "Electrical Tape, Black";
		ToteA[16][0] = "Painter's Tape, Blue";
		ToteA[17][0] = "Crazy glue ";
		ToteA[18][0] = "Black Rachet Set";
		ToteA[19][0] = "Drills + chargers";	
		for( int f=0; f<20; f++ )
		{
			ToteA[f][1] = "0";
		}
	}
	public static void loadToteB()
	{
		/* [Load] [Memory] */
		ToteB[0][0] = "Polycord Box (+tread, radio, camera)";
		ToteB[1][0] = "Solder Box (+chain)";
		ToteB[2][0] = "Vex Pro Box (versas/ringlight/victor)";
		ToteB[3][0] = "SAE Tap Set";
		ToteB[4][0] = "Spacer Box";
		ToteB[5][0] = "Warrior Set";
		ToteB[6][0] = "Box End Rachets";
		ToteB[7][0] = "Compressed Air";
		ToteB[8][0] = "Tap Fluid";
		ToteB[9][0] = "Caliper";
		ToteB[10][0] = "Blue Rachet Set";
		for( int f=0; f<11; f++ )
		{
			ToteB[f][1] = "0";
		}
	}
	public static void loadToteC()
	{
		/* [Load] [Memory] */
		ToteC[0][0] = "Hardstop (orange)";
		ToteC[1][0] = "Gears Stuff Box";
		ToteC[2][0] = "Mcmaster box";
		ToteC[3][0] = "Red Battery Wire";
		ToteC[4][0] = "Black Battery Wire";
		ToteC[5][0] = "Pickup hardware bag(small)";
		ToteC[6][0] = "Wire box";
		ToteC[7][0] = "Victors";
		ToteC[8][0] = "Pneumatic Tubing Bag";
		ToteC[9][0] = "Versas";
		ToteC[10][0] = "Long Pistons";
		ToteC[11][0] = "Air Tanks";
		ToteC[12][0] = "Random Sheet Metal ";
		ToteC[13][0] = "Sponsor panels";
		ToteC[14][0] = "Breakers";
		ToteC[15][0] = "Pneumatic Tubing Blue and Orange";
		ToteC[16][0] = "Optical Sensor";
		ToteC[17][0] = "Pickup hardware bag(big)";
		for( int f=0; f<18; f++ )
		{
			ToteC[f][1] = "0";
		}
	}
	public static void loadToteD()
	{
		/* [Load] [Memory] */
		ToteD[0][0] = "Pit Bag";
		ToteD[1][0] = "White Board";
		ToteD[2][0] = "Staple Bag";
		ToteD[3][0] = "Staple Gun";
		ToteD[4][0] = "Paint brushes+paint";
		ToteD[5][0] = "Red Fabric";
		ToteD[6][0] = "Blue Fabric";
		ToteD[7][0] = "Bumper Bolts + Nuts Bag";
		ToteD[8][0] = "Standard";
		ToteD[9][0] = "Power Strips ";
		ToteD[10][0] = "Extension cords";
		ToteD[11][0] = "Mutimeter";
		ToteD[12][0] = "vise bit";
		ToteD[13][0] = "Radio power cord";
		ToteD[14][0] = "Ethernet";
		ToteD[15][0] = "Ball";
		ToteD[16][0] = "Crowbar";
		ToteD[17][0] = "Crate Screws(box)";
		ToteD[18][0] = "Pool noodle";
		ToteD[19][0] = "1x1 tubing";
		ToteD[20][0] = "Gorilla Tape";
		ToteD[21][0] = "Velcro";
		ToteD[22][0] = "Pneumatics plate";
		ToteD[23][0] = "2 CIMs Box";
		ToteD[24][0] = "4 Dry Erase Markers";
		for( int f=0; f<25; f++ )
		{
			ToteD[f][1] = "0";
		}
	}
	public static void loadToteE()
	{
		/* [Load] [Memory] */
		ToteE[0][0] = "Chain Box";
		ToteE[1][0] = "Plates Stuff Box";
		ToteE[2][0] = "Clear Screw Box";
		ToteE[3][0] = "Mantis hardware bag";
		ToteE[4][0] = "Crappy Drill Bit Set";
		ToteE[5][0] = "Electrical Stuff box";
		ToteE[6][0] = "Anderson Box";
		ToteE[7][0] = "Rag Bag";
		for( int f=0; f<8; f++ )
		{
			ToteE[f][1] = "0";
		}
	}
	public static void loadCrate()
	{
		/* [Load] [Memory] */
		Crate[0][0] = "long hex stock (3)";
		Crate[1][0] = "standard stand";
		Crate[2][0] = "vise";
		Crate[3][0] = "tarp ";
		Crate[4][0] = "white shelves";
		Crate[5][0] = "EZ Up";
		Crate[6][0] = "robot";
		Crate[7][0] = "Bumpers";
		Crate[8][0] = "banner";
		Crate[9][0] = "Blue Banner";
		Crate[10][0] = "sheet metal";
		Crate[11][0] = "big shelves";
		Crate[12][0] = "Shop vac";
		Crate[13][0] = "push cart (1)";
		Crate[14][0] = "Orange Safety Kit";
		Crate[15][0] = "knee pads";
		for( int f=0; f<16; f++ )
		{
			Crate[f][1] = "0";
		}
	}
	public static void loadExclusion()
	{
		/* [Load] [Memory] */
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
		/* [Load] [Memory] */
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
		/* [Load] [Memory] */
		TLDesc[0] = "Precision Screwdrivers Sets screws ";
		TLDesc[1] = "Goo-gone goo gone removers";
		TLDesc[2] = "Files";
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
		/* [Load] [Memory] */
		TADesc[0] = "Rivets Boxes";
		TADesc[1] = "Pneumatics Hardware Boxes";
		TADesc[2] = "Zip Tie Boxes ziptie zipties";
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
		/* [Load] [Memory] */
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
		/* [Load] [Memory] */
		TCDesc[0] = "Hardstops (orange)";
		TCDesc[1] = "Gears Stuff Boxes";
		TCDesc[2] = "Mcmaster boxes";
		TCDesc[3] = "Red Battery batteries Wires electrical cables cords";
		TCDesc[4] = "Black Battery Wires batteries electrical cables cords";
		TCDesc[5] = "Pickup hardware bags(small)";
		TCDesc[6] = "Wires cables electrical cords boxes";
		TCDesc[7] = "Victors motors";
		TCDesc[8] = "Pneumatics Tubing Bags";
		TCDesc[9] = "Versas";
		TCDesc[10] = "Long Pistons pneumatics";
		TCDesc[11] = "Air Tanks pneumatics";
		TCDesc[12] = "Random Sheets Metal scrap";
		TCDesc[13] = "Sponsor panels";
		TCDesc[14] = "Breakers electrical";
		TCDesc[15] = "Pneumatics Tubing Blue and Orange";
		TCDesc[16] = "Optical Sensors";
		TCDesc[17] = "Pickup hardware bags(big)";
	}
	public static void loadTDDesc()
	{
		/* [Load] [Memory] */
		TDDesc[0] = "Pit Bags";
		TDDesc[1] = "White Boards";
		TDDesc[2] = "Staples Bags";
		TDDesc[3] = "Staples Guns";
		TDDesc[4] = "Paint brushes+paint";
		TDDesc[5] = "Red Fabrics";
		TDDesc[6] = "Blue Fabrics";
		TDDesc[7] = "Bumpers Bolts + Nuts Bags";
		TDDesc[8] = "Standards flags";
		TDDesc[9] = "Power Strips electrical";
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
		/* [Load] [Memory] */
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
		/* [Load] [Memory] */
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
	public static int ToolBoxCB(String item)
	{
		/* [Search]*/
		int loc = 0;
		for( int f=0; f<41; f++ )
		{
			if( item.equals(ToolBox[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int ToteACB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<20; f++ )
		{
			if( item.equals(ToteA[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int ToteBCB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<11; f++ )
		{
			if( item.equals(ToteB[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int ToteCCB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<18; f++ )
		{
			if( item.equals(ToteC[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int ToteDCB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<25; f++ )
		{
			if( item.equals(ToteD[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int ToteECB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<8; f++ )
		{
			if( item.equals(ToteE[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static int CrateCB(String item)
	{
		/* [Search] */
		int loc = 0;
		for( int f=0; f<16; f++ )
		{
			if( item.equals(Crate[f][0].toLowerCase()) )
			{
				loc = f;
				break;
			}
		}
		return loc;
	}
	public static void listToolBox()
	{
		/* [Text] [Print] [Info] */
		System.out.println("The Toolbox contains the following:");
		for( int f=0; f<41; f++ )
		{
			if( ToolBox[f][1] == "0" )
			{
				System.out.println(ToolBox[f][0]);
			}
			if( ToolBox[f][1] != "0" )
			{
				System.out.println("The " + ToolBox[f][0] + " was lent to team " + ToolBox[f][1] + ".");
			}
		}
		System.out.println();
	}
	public static void listToteA()
	{
		/* [Text] [Print] [Info] */
		System.out.println("Tote A contains the following:");
		for( int f=0; f<20; f++ )
		{
			if( ToteA[f][1] == "0" )
			{
				System.out.println(ToteA[f][0]);
			}
			if( ToteA[f][1] != "0" )
			{
				System.out.println("* The " + ToteA[f][0] + " was lent to team " + ToteA[f][1] + ".");
			}		
		}
		System.out.println();
	}
	public static void listToteB()
	{
		/* [Text] [Print] [Info] */
		System.out.println("Tote B contains the following:");
		for( int f=0; f<11; f++ )
		{
			if( ToteB[f][1] == "0" )
			{
				System.out.println(ToteB[f][0]);
			}
			if( ToteB[f][1] != "0" )
			{
				System.out.println("* The " + ToteB[f][0] + " was lent to team " + ToteB[f][1] + ".");
			}		}
		System.out.println();
	}
	public static void listToteC()
	{
		/* [Text] [Print] [Info] */
		System.out.println("Tote C contains the following:");
		for( int f=0; f<18; f++ )
		{
			if( ToteC[f][1] == "0" )
			{
				System.out.println(ToteC[f][0]);
			}
			if( ToteC[f][1] != "0" )
			{
				System.out.println("* The " + ToteC[f][0] + " was lent to team " + ToteC[f][1] + ".");
			}		}
		System.out.println();
	}
	public static void listToteD()
	{
		/* [Text] [Print] [Info] */
		System.out.println("Tote D contains the following:");
		for( int f=0; f<25; f++ )
		{
			if( ToteD[f][1] == "0" )
			{
				System.out.println(ToteD[f][0]);
			}
			if( ToteD[f][1] != "0" )
			{
				System.out.println("* The " + ToteD[f][0] + " was lent to team " + ToteD[f][1] + ".");
			}		}
		System.out.println();
	}
	public static void listToteE()
	{
		/* [Text] [Print] [Info] */
		System.out.println("Tote E contains the following:");
		for( int f=0; f<8; f++ )
		{
			if( ToteE[f][1] == "0" )
			{
				System.out.println(ToteE[f][0]);
			}
			if( ToteE[f][1] != "0" )
			{
				System.out.println("* The " + ToteE[f][0] + " was lent to team " + ToteE[f][1] + ".");
			}		}
		System.out.println();
	}
	public static void listCrate()
	{
		/* [Text] [Print] [Info] */
		System.out.println("The Crate contains the following:");
		for( int f=0; f<16; f++ )
		{
			if( Crate[f][1] == "0" )
			{
				System.out.println(Crate[f][0]);
			}
			if( Crate[f][1] != "0" )
			{
				System.out.println("* The " + Crate[f][0] + " was lent to team " + Crate[f][1] + ".");
			}		}
		System.out.println();
	}
	public static void shutDown()
	{
		/* [Cleanup] [Terminate] */
		write("close");
		on = false;
	}
}
