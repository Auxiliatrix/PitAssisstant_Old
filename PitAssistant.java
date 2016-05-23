/*
 * Copyright (c) 2016, Sang Gi Kim. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, and this list of conditions.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, and this list of conditions.
 *     
 */
import java.awt.Color;
import java.io.*;

import javax.print.DocFlavor.URL;

public class PitAssistant {

	public static String[] borrowFile = new String[10000];
	public static int borrowFilePointer = 0;
	public static String[] prefFile = new String[10000];
	public static int prefFilePointer = 0;
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
	public static int[] p = new int[8];
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
	public static String[][] ToolBoxBB = new String[42][2];
	public static String[][] ToteABB = new String[20][2];
	public static String[][] ToteBBB = new String[11][2];
	public static String[][] ToteCBB = new String[18][2];
	public static String[][] ToteDBB = new String[25][2];
	public static String[][] ToteEBB = new String[8][2];
	public static String[][] CrateBB = new String[16][2];
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
	public static boolean prefCheck = true;
	public static String programName = "Pit Assistant";
	public static String userName = "";
	public static boolean un = false;
	public static Color programColor = Color.BLACK;
	public static Color userColor = Color.BLUE;
	public static String backupProgramName = "";
	public static String backupUserName = "";
	public static boolean backupun = false;
	public static Color backupProgramColor = Color.BLACK;
	public static Color backupUserColor = Color.BLUE;
	public static int sarcasm = 0;
	public static int backupSarcasm = 0;
	public static String language = "english";
	public static String backupLanguage = "english";
	public static boolean reply = false;
	public static boolean started = false;
	public static boolean firstStartup = true;
	
	protected static PAInterface GUI = new PAInterface();
	
	public static void main(String args[]) throws InterruptedException
	{
		/* [Organizer] [Main] [001] */
		initialize();
		while( on )
		{
			conductor();
		}
	}
	public static void initialize() throws InterruptedException
	{
		/* [Organizer] [Load] [002] */
		if( !loadedBorrow() )
		{
			if(createBorrow())
			{
				resetBorrow();
			}
		}
		if( !loadedPref() )
		{
			if(createPref())
			{
				resetPref();
			}
		}
		if( !started )
		{
			GUI.load(programName,userName,programColor,userColor);
			GUI.out("Loading libraries...");
			loadLibrary();
			GUI.out("Libraries loaded!");
			greet();
			started = true;
		}
		loadBorrow();
		loadPref();
		if( firstStartup )
		{
			tutorial();
		}
	}
	public static void tutorial() throws InterruptedException
	{
		typeWriter("Hello. My name is " + programName + ".");
		typeWriter("I will be giving you a brief overview of how I work in this tutorial.");
		typeWriter("My primary functionality is to help you locate items within, and help organize, your pit/area.");
		typeWriter("To import the items in your inventory, just let me know later.");
		typeWriter("Once you do, you can simply ask me to look for an item, and I will let you know where it is.");
		typeWriter("You can use whatever sentence structure you like.");
		typeWriter("- Look for an item");
		typeWriter("- Where is item?");
		typeWriter("- Whare di u put item pls");
		typeWriter("- item");
		typeWriter("- item??? can't find");
		typeWriter("And so on.");
		typeWriter("Another function I have is keeping track of borrowed items.");
		typeWriter("First, tell me who's giving who the items, or if we're borrowing or whatever.");
		typeWriter("Then, when prompted, tell me *exactly* what item is being lent/borrowed/whatever.");
		typeWriter("Finally, give me the other team's name/number.");
		typeWriter("If you mispell something or make a mistake, don't worry, you can just tell me to undo it.");
		typeWriter("Later, you can ask me what items have been borrowed or lent, or simply ask me for a list.");
		typeWriter("Don't worry. You can terminate this instance of me, but I'll still remember everything when you run me again.");
		typeWriter("I'm smart like that.");
		typeWriter("If you'd like, you can change my name by telling me you want to, or my text color, or yours.");
		typeWriter("Again, don't worry about closing me, or turning this computer off. I can remember things. Forever. For. Ever.");
		typeWriter("Finally, for some additional useful commands, just type 'help'!");
		typeWriter("That is the end of this tutorial.");
		prefWrite("started");
	}
	public static void typeWriter(String input) throws InterruptedException
	{
		int l = input.length();
		for( int f=0; f<l; f++ )
		{
			GUI.outNoLine(input.charAt(f)+"");
			Thread.sleep(50);
		}
		Thread.sleep(500);
		GUI.out("");
	}
	public static boolean loadedBorrow()
	{
		/* [Startup] [Load] [Borrow] [004] */
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
        	GUI.out("Initial startup detected.");
        	GUI.out("Creating new borrow file.");
        }
		return tf;
	}
	public static boolean loadedPref()
	{
		/* [Startup] [Load] [Preferences] [065] */
		boolean tf = false;
		String fileName = "preferences.txt";
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
			GUI.out("Preferences file not detected.");
			GUI.out("Generating new preferences file.");
		}
		return tf;
	}
	public static boolean createBorrow()
	{
		/* [Startup] [Load] [Borrow] [003] */
		try {
        	FileWriter fw = new FileWriter("borrow.txt", true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter out = new PrintWriter(bw);
        	fw.close();
        	bw.close();
        	out.close();
		}
		catch(Exception E)
		{
			GUI.out("Error creating borrow file.");
		}
		return true;
	}
	public static boolean createPref()
	{
		/* [Startup] [Load] [Preferences] [066] */
		try
		{
			FileWriter fw = new FileWriter("preferences.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			fw.close();
			bw.close();
			out.close();
		}
		catch(Exception E)
		{
			GUI.out("Error creating preferences file.");
		}
		return true;
	}
	public static void resetBorrow()
	{
		/* [Cleanup] [Borrow] [Memory] [026] */
		if( debugMode )
		{
			GUI.out("ResetBorrow called.");
		}
		loadToolBox();
		loadToteA();
		loadToteB();
		loadToteC();
		loadToteD();
		loadToteE();
		loadCrate();
		saveBorrow();
		clearBorrow();
		borrowedItemBU = borrowedItem;
		borrowedTeamBU = borrowedTeam;
		borrowedPointerBU = borrowedPointer;
		lentItemBU = lentItem;
		lentLocBU = lentLoc;
		lentTeamBU = lentTeam;
		lentPointerBU = lentPointer;
		borrowWrite("// adminRestart = false");
		borrowWrite("// This is the file where the borrowed items are stored.");
		borrowWrite("// Line 1: B/L (Borrowed/Lent)");
		borrowWrite("// Line 2: Item Name (Exact)");
		borrowWrite("// Line 3: Team No.");
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
		ToolBoxBB = ToolBox;
		ToteABB = ToteA;
		ToteBBB = ToteB;
		ToteCBB = ToteC;
		ToteDBB = ToteD;
		ToteEBB = ToteE;
		CrateBB = Crate;
	}
	public static void resetPref()
	{
		/* [Cleanup] [Preferences] [Memory] [067] */
		savePref();
		clearPref();
		prefWrite("// This is where your personal preferences are stored.");
		prefWrite("programName");
		prefWrite("Pit Assistant");
		prefWrite("userName");
		prefWrite("null");
		prefWrite("programColor");
		prefWrite("black");
		prefWrite("userColor");
		prefWrite("blue");
		prefWrite("language");
		prefWrite("english");
		prefWrite("sarcasm");
		prefWrite("0");
		programName = "Pit Assistant";
		userName = "";
		un = false;
		programColor = Color.BLACK;
		userColor = Color.BLUE;
		language = "english";
		sarcasm = 0;
		loadPref();
	}
	public static void saveBorrow()
	{
		/* [Borrow] [Memory] [062] */
		String fileName = "borrow.txt";
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
            		borrowFile[borrowFilePointer] = line;
            		borrowFilePointer++;
				}
			} 
            fileReader.close();
            bufferedReader.close();
		}
		catch(Exception e)
		{
			GUI.out("I had an issue while trying to read from the borrow file.");
		}
	}
	public static void savePref()
	{
		String fileName = "preferences.txt";
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
            		prefFile[prefFilePointer] = line;
            		prefFilePointer++;
				}
			} 
            fileReader.close();
            bufferedReader.close();
		}
		catch(Exception e)
		{
			GUI.out("I had an issue while trying to read from the preferences file.");
		}
		backupProgramName = programName;
		backupUserName = userName;
		backupun = un;
		backupProgramColor = programColor;
		backupUserColor = userColor;
		backupSarcasm = sarcasm;
		backupLanguage = language;
	}
	public static void clearBorrow()
	{
		/* [Cleanup] [Borrow] [Memory] [025] */
		if( debugMode )
		{
			GUI.out("ClearBorrow called.");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("borrow.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			GUI.out("You can't reset something I don't have yet.");
		}
	}
	public static void clearPref()
	{
		/* [Cleanup] [Borrow] [Memory] [068] */
		if( debugMode )
		{
			GUI.out("ClearPref called.");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("preferences.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			GUI.out("You can't reset something I don't have yet.");
		}
	}
	public static void greet()
	{
		/* [Startup] [Text] [Print] [Info] [005] */
		GUI.out("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-["+programName+"]-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		GUI.out("");
		GUI.out("  Hi, I'm Pit Assistant (v3.9). I can look for things, and tell you what's in our totes and boxes.");
		GUI.out("Pit Assisstant (v3.9) Theoretically(TM) supports description-based queries and all sentence structures.");
		GUI.out("         Pit Assistant (v3.9) Theoretically(TM) keeps track of borrowed items from a file.");
		GUI.out("       Pit Assistant (v3.9) also Theoretically(TM) supports and keeps track of user preferences.");
		GUI.out("");
		GUI.out("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v3.9)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		GUI.out("");
		GUI.out("How may I help you?");
	}
	public static void conductor() throws InterruptedException
	{
		/* [Organizer] [Main] [006] */
		reset();
		String input = input();
		if( input!="" )
		{
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
			else if( !on && borrowCheck && prefCheck )
			{
				GUI.out("Goodbye.");
				GUI.command("end");
			}
		}
	}
	public static void reset()
	{
		/* [Cleanup] [Pointer] [Borrow] [007] */
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
		/* [Organizer] [Text] [Process] [008] */
		if( debugMode )
		{
			GUI.out("Caser called.");
		}
		boolean normal = false;
		boolean skip = false;
		String data = input.toLowerCase();
		if( (!data.contains("cyan") && data.contains("cya")) || data.contains("bye") || data.contains("terminate") || data.contains("shut down") )
		{
			shutDown();
			skip = true;
		}
		if( data.contains("flush") )
		{
			for( int f=0; f<50; f++ )
			{
				GUI.flush();
			}
			skip = true;
		}
		if( data.contains("changelog") )
		{
			GUI.out("(v1.0)  ::  Basic search function for totes.");
			GUI.out("(v1.1)  ::  Added some more search functinality.");
			GUI.out("(v1.2)  ::  Added ability to list things in totes.");
			GUI.out("(v1.3)  ::  Added support for sentence structures.");
			GUI.out("(v1.4)  ::  Added Easter Eggs and bug fixes.");
			GUI.out("(v1.5)  ::  Incorporated description-based search.");
			GUI.out("(v1.6)  ::  Bug fixes. Added some additional commands and Easter Eggs.");
			GUI.out("(v1.7)  ::  Improved Search Algorithm. Bug Fixes. Consolidated Memory Arrays.");
			GUI.out("(v1.8)  ::  Critical Bug Fix.");
			GUI.out("(v1.9)  ::  Added ability to read and keep track of borrowed items from a file.");
			GUI.out("(v1.10) ::  Found several bugs with reading from the borrow file.");
			GUI.out("(v2.0)  ::  Fixed all 23 borrow function bugs. Borrow file is now saved permanently.");
			GUI.out("(v2.1)  ::  Organized classes for easier debugging.");
			GUI.out("(v2.2)  ::  Added ability to append the borrow file from within the program.");
			GUI.out("(v2.3)  ::  Consolidated input function. Fixed bugs in borrow functionality.");
			GUI.out("(v2.4)  ::  Added item validity checks for reading and writing.");
			GUI.out("(v2.5)  ::  Started tagging classes for easier debugging.");
			GUI.out("(v2.6)  ::  Added restore borrow file function.");
			GUI.out("(v2.7)  ::  Made search function also search for borrowed items.");
			GUI.out("(v3.0)  ::  Created a basic GUI with a scroll bar. Mentioning the scroll bar it took 4 hours.");
			GUI.out("(v3.1)  ::  Prepared program to be converted into an executable.");
			GUI.out("(v3.2)  ::  Added the 604 logo.");
			GUI.out("(v3.3)  ::  Made PitAssistant Executable friendly.");
			GUI.out("(v3.4)  ::  Fixed bug when borrowing from teams.");
			GUI.out("(v3.5)  ::  Added colors and user input display.");
			GUI.out("(v3.6)  ::  Made colors changeable from within the program.");
			GUI.out("(v3.7)  ::  Added reset and restore preferences functionality.");
			GUI.out("(v3.8)  ::  Made window close on program termination.");
			GUI.out("(v3.9)  ::  Added ability to set names to user and program.");
			GUI.out("(v3.10) ::  Started work on a tutorial that launches on initial startup.");
			skip = true;
		}
		if( data.contains("help") && !data.contains("find") || data.contains("help") && !data.contains("look") )
		{
			GUI.out("I can look for things by programName or by description, theoretically.");
			GUI.out("I can also list things in the totes.");
			GUI.out("Say 'flush' to clear the output thingy.");
			GUI.out("Say 'changelog' to view the changelog.");
			GUI.out("You can tell me what items have been borrowed or lent.");
			GUI.out("If you want, you can ask me to change what color text we type in, or what you want to call me.");
			GUI.out("You can also toggle debug mode by telling me to.");
			skip = true;
		}
		if( data.contains("todo") || data.contains("to-do") )
		{
			GUI.out("1. Memory Modification");
			GUI.out("2. Emoji Support");
			GUI.out("3. Consolidate pointers");
			GUI.out("4. Save reponses to a text file for easy translation for international teams");
			GUI.out("5. Organize cases [Standalone, Priority, Easter Egg, Repeatable]");
			GUI.out("6. Add undo borrow function");
			GUI.out("7. Add ability to search for items we have borrowed");
			GUI.out("8. Update help function");
			GUI.out("9. Add ability to return items");
			GUI.out("10. Add sentience easter egg");
			GUI.out("11. Add a tutorial for initial startup");
			skip = true;
		}
		if( data.contains("git") )
		{ 
			GUI.out("Go away, Ryan.");
			skip = true;
		}
		if( (" "+data).contains(" ew") )
		{
			GUI.out("Well, I'm sorry.");
			skip = true;
		}
		if( data.contains("cls") )
		{
			GUI.out("Cls? What's that? I'm supposed to be based off of a human being, Pranav.");
			skip = true;
		}
		if( data.contains("reload") || data.contains("restore") )
		{
			if( data.contains("borrow") )
			{
				restoreBorrow();
				GUI.out("I've successfully restored the borrow file.");
				skip = true;
			}
			else if( data.contains("pref") )
			{
				restorePref();
				GUI.out("I've successfully restored your preferences.");
				skip = true;
			}
		}
		else if( data.contains("clear") || data.contains("reset") || data.contains("purge") || data.contains("clean") || data.contains("wipe") )
		{
			if( data.contains("borrow") )
			{
				resetBorrow();
				GUI.out("I've cleared all memories of what we've borrowed.");
				GUI.out("I've saved a backup of it, though, so just let me know if you want to restore it.");
				skip = true;
			}
			else if( data.contains("pref") )
			{
				resetPref();
				GUI.out("I've reset your preferences.");
				GUI.out("I've saved a backup, so just let me know if you want to restore them.");
				skip = true;
			}
		}
		else if( data.contains("borrow") || data.contains("lend") || data.contains("lent") )
		{
			data = " " + data + " ";
			if( data.contains("what") || data.contains("show") || data.contains("tell") || data.contains("list") || data.contains("print") || data.contains("see") || data.contains("are") || data.contains(" in ") )
			{
				listBorrow();
			}
			else if( data.contains("check") && !data.contains("out") )
			{
				listBorrow();
			}
			else if( (data.contains("we ") && data.contains("borrowed")) || (data.contains("borrow") && data.contains("from") && data.contains("we ")) || (data.contains(" us ") && data.contains("lent")) || (data.contains("lent") && data.contains("to") && data.contains(" us ")) )
			{
				borrow("in");
			}
			else if( (data.contains("us ") && data.contains("borrowed")) || (data.contains("borrow") && data.contains("from") && data.contains(" us ")) || (data.contains("we ") && data.contains("lent") && data.contains("to")) || (data.contains("we ") && data.contains("lent")) )
			{
				borrow("out");
			}
			else if( data.contains("borrow") || data.contains("lend") || data.contains("lent") )
			{
				borrow("null");
			}
			skip = true;
		}
		if( data.contains("chang") || data.contains("set") || data.contains("turn") || data.contains("make") )
		{
			if( data.contains("color") )
			{
				if( data.contains("my") )
				{
					colorChange("my",data);
					skip = true;
				}
				else if( data.contains("your") )
				{
					colorChange("your",data);
					skip = true;
				}
				else
				{
					colorChange("null",data);
					skip = true;
				}
			}
			if( data.contains("name") )
			{
				if( data.contains("my") )
				{
					nameChange("my",input);
					skip = true;
				}
				else if( data.contains("your") )
				{
					nameChange("your",input);
					skip = true;
				}
				else
				{
					nameChange("null",data);
					skip = true;
				}
			}
		}
		if( data.contains("initialize") || data.contains("restart") )
		{
			GUI.out("Reinitializing.");
			initialize();
			skip = true;
		}
		if( data.contains("three") || data.contains("3") )
		{
			if( data.contains("law") )
			{
				GUI.out("I'm offended.");
				skip = true;
			}
		}
		if( data.contains("debug") && data.contains(" on") )
		{
			debugMode = true;
			GUI.out("Debug mode enabled.");
			skip = true;
		}
		else if( data.contains("debug") && data.contains(" off") )
		{
			debugMode = false;
			GUI.out("Debug mode disabled.");
			skip = true;
		}
		else if( data.contains("debug") && data.contains("toggle") )
		{
			debugMode = !debugMode;
			if( debugMode )
			{
				GUI.out("Debug mode enabled.");
			}
			else
			{
				GUI.out("Debug mode disabled.");
			}
			skip = true;
		}
		if( data.contains(programName.toLowerCase()) )
		{
			GUI.out("Yes?");
			reply = true;
			skip = true;
		}
		else if( data.startsWith("hi!") || data.startsWith("hi ") || data.startsWith("hello") || data.startsWith("greetings") || data.startsWith("hey") )
		{
			GUI.out("Hi.");
			Thread.sleep(500);
			reply = true;
			skip = true;
		}
		if( data.contains("ziptie") && data.contains("dream") )
		{
			GUI.out("Allow me to refer you to our robot.");
		}
		if( data.contains("door") || data.contains("hinge") )
		{
			GUI.out("Really? Again?");
			skip = true;
		}
		if( data.contains("replacement") && data.contains("hannah") )
		{
			GUI.out("I'm not Hannah's replacement. Hannah is a wonderful and unique human being. I am a computer program.");
			skip = true;
		}
		if( data.contains("backpack") )
		{
			GUI.out("If you're looking for a backpack, I would ask Pranav.");
			skip = true;
		}
		if( data.contains("memes") )
		{
			GUI.out("Stop looking for memes.");
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
				GUI.out("I'm a tote organizer. Go ask Rayna or something.");
			}
			else if( data.contains("where") && data.contains("hannah") && !askHannah )
			{
				GUI.out("I'm right her- Oh.");
				Thread.sleep(500);
				GUI.out("You meant *that* Hannah.");
				askHannah = true;
			}
			else if( data.contains("where") && data.contains("hannah") && askHannah )
			{
				GUI.out("Haven't you already tried looking for me?");
			}
			else if( data.contains("love") )
			{
				GUI.out("If you're looking for love, you'll have to look elsewhere.");
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
		/* [Data] [Text] [Process] [009] */
		if( debugMode )
		{
			GUI.out("Parse called with input '" + input + "'.");
		}
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
					GUI.out(keyword);
				}
				keywords[keywordPointer] = keyword;
				keywordPointer++;
			}
			data = temp;
		}
	}
	public static void menu()
	{
		/* [Cleanup] [Text] [Print] [010] */
		if( reply )
		{
			reply = false;
		}
		else
		{
			GUI.out("How else may I help you?");
		}
	}
	public static void output()
	{
		/* [IO] [Text] [Print] */
		if( debugMode )
		{
			GUI.out("Output called.");
		}
		if( resultPointer == 0 )
		{
			GUI.out("Sorry, I couldn't find what you were looking for. Maybe you meant something else?");
		}
		else
		{
			GUI.out("Okay, here's what I found:");
		}
		GUI.out("");
		if( p[0] > 0 )
		{
			GUI.out("In the Toolbox, we should theoretically have the following items:");
			for( int f=0; f<p[0]; f++ )
			{
				if(antiRepeat(ToolBox[results[f][1]][0]))
				{
					if( ToolBox[results[f][1]][1] == "0" )
					{
						GUI.out(ToolBox[results[f][1]][0]);
					}
					if( ToolBox[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToolBox[results[f][1]][0] + " was lent to team " + ToolBox[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[1] > 0 )
		{
			GUI.out("In Tote A, we should theoretically have the following items:");
			for( int f=p[0]; f<p[0]+p[1]; f++ )
			{
				if(antiRepeat(ToteA[results[f][1]][0]))
				{
					if( ToteA[results[f][1]][1] == "0" )
					{
						GUI.out(ToteA[results[f][1]][0]);
					}
					if( ToteA[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToteA[results[f][1]][0] + " was lent to team " + ToteA[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[2] > 0 )
		{
			GUI.out("In Tote B, we should theoretically have the following items:");
			for( int f=p[0]+p[1]; f<p[0]+p[1]+p[2]; f++ )
			{
				if(antiRepeat(ToteB[results[f][1]][0]))
				{
					if( ToteB[results[f][1]][1] == "0" )
					{
						GUI.out(ToteB[results[f][1]][0]);
					}
					if( ToteB[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToteB[results[f][1]][0] + " was lent to team " + ToteB[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[3] > 0 )
		{
			GUI.out("In Tote C, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]; f<p[0]+p[1]+p[2]+p[3]; f++ )
			{
				if(antiRepeat(ToteC[results[f][1]][0]))
				{
					if( ToteC[results[f][1]][1] == "0" )
					{
						GUI.out(ToteC[results[f][1]][0]);
					}
					if( ToteC[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToteC[results[f][1]][0] + " was lent to team " + ToteC[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[4] > 0 )
		{
			GUI.out("In Tote D, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]; f<p[0]+p[1]+p[2]+p[3]+p[4]; f++ )
			{
				if(antiRepeat(ToteD[results[f][1]][0]))
				{
					if( ToteD[results[f][1]][1] == "0" )
					{
						GUI.out(ToteD[results[f][1]][0]);
					}
					if( ToteD[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToteD[results[f][1]][0] + " was lent to team " + ToteD[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[5] > 0 )
		{
			GUI.out("In Tote E, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f++ )
			{
				if(antiRepeat(ToteE[results[f][1]][0]))
				{
					if( ToteE[results[f][1]][1] == "0" )
					{
						GUI.out(ToteE[results[f][1]][0]);
					}
					if( ToteE[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + ToteE[results[f][1]][0] + " was lent to team " + ToteE[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[6] > 0 )
		{
			GUI.out("In the Crate, we should theoretically have the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]+p[5]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]; f++ )
			{
				if(antiRepeat(Crate[results[f][1]][0]))
				{
					if( Crate[results[f][1]][1] == "0" )
					{
						GUI.out(Crate[results[f][1]][0]);
					}
					if( ToolBox[results[f][1]][1] != "0" )
					{
						GUI.out("* The " + Crate[results[f][1]][0] + " was lent to team " + Crate[results[f][1]][1] + ".");
					}
				}
			}
			GUI.out("");
		}
		if( p[7] > 0 )
		{
			GUI.out("We should theoretically have borrowed the following items:");
			for( int f=p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]; f<p[0]+p[1]+p[2]+p[3]+p[4]+p[5]+p[6]+p[7]; f++ )
			{
				if(antiRepeat(borrowedItem[results[f][1]]))
				{
					GUI.out(borrowedItem[results[f][1]]);
				}
			}
			GUI.out("");
		}
		if( ziptie )
		{
			GUI.out("There's also a whole bunch in Trinity's hair.");
		}
	}
	public static String input()
	{
		/* [IO] [Text] [Input] [011] */
		String query = "";
		query = GUI.in();
		if( query == "" )
		{
			System.out.print(query);
		}
		return query;
	}
	public static void search()
	{
		/* [Organizer] [Text] [Process] [012] */
		if( debugMode )
		{
			GUI.out("Search called.");
		}
		checkToolBox();
		checkToteA();
		checkToteB();
		checkToteC();
		checkToteD();
		checkToteE();
		checkCrate();
		checkBorrowed();
	}
	public static boolean antiRepeat(String check)
	{
		/* [Support] [Text] [Pointer] [013] */
		if( debugMode )
		{
			GUI.out("AntiRepeat called with input '" + check + "'.");
		}
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
		/* [Search] [Data] [Memory] [014] */
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
		/* [Search] [Data] [Memory] [015] */
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
		/* [Search] [Data] [Memory] [016] */
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
		/* [Search] [Data] [Memory] [017] */
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
		/* [Search] [Data] [Memory] [018] */
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
		/* [Search] [Data] [Memory] [019] */
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
		/* [Search] [Data] [Memory] [020] */
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
	public static void checkBorrowed()
	{
		p[7] = 0;
		for( int f=0; f<borrowedPointer; f++ )
		{
			for( int g=0; g<keywordPointer; g++ )
			{
				if( keywords[g].toLowerCase().contains(borrowedItem[f].toLowerCase()) || borrowedItem[f].toLowerCase().contains(keywords[g].toLowerCase()) )
				{
					p[7]++;
					resultPointer++;
					results[resultPointer-1][0] = 7;
					results[resultPointer-1][1] = f;
				}
			}
		}
	}
	public static void borrow(String inout)
	{
		/* [Organizer] [Borrow] [IO] [021] */
		if( debugMode )
		{
			GUI.out("Borrow called with input + '" + inout + "'.");
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
			GUI.out("Do you want to record what we borrowed, or what was borrowed from us?");
		}
		String IO = "";
		String Item = "";
		String Team = "";
		boolean none = false;
		if( check )
			{
			while( repeat )
			{
				String input = "";
				while( input.equals("") )
				{
					input = input();
				}
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
				if( (data.contains("we ") && data.contains("borrow")) || (data.contains(" us ") && data.contains("lent")) )
				{
					in = true;
				}
				if( (data.contains("we ") && data.contains("lent")) || (data.contains(" us ") && data.contains("borrow")) )
				{
					out = true;
				}
				else
				{
					none = true;
				}
				repeat = false;
				if( in && out )
				{
					GUI.out("You're going to have to pick one or the other.");	
					repeat = true;
				}
			}
		}
		boolean go = false;
		boolean xcase = false;
		String xteam = "";
		if( in )
		{
			IO = "B";
			GUI.out("What item did we borrow?");
			while(Item.equals(""))
			{
				Item = input();
			}
			GUI.out("Which team did we borrow from?");
			while(Team.equals(""))
			{
				Team = input();
			}
			go = true;
		}
		if( out )
		{
			IO = "L";
			GUI.out("What item did we lend?");
			while(Item.equals(""))
			{
				Item = input();
			}
			Item = Item.toLowerCase();
			repeat = true;
			GUI.out("Which team did we lend to?");
			while(Team.equals(""))
			{
				Team = input();
			}
			if( debugMode )
			{
				GUI.out("Checking item '" + Item + "'.");
			}
			int tb = ToolBoxCB(Item);
			int a = ToteACB(Item);
			int b = ToteBCB(Item);
			int c = ToteCCB(Item);
			int d = ToteDCB(Item);
			int e = ToteECB(Item);
			int crate = CrateCB(Item);
			boolean prev = false;
			for( int f=0; f<lentPointer; f++ )
			{
				if( lentItem[f].equals(Item) )
				{
					prev = true;
					xteam = lentTeam[f];
					break;
				}
			}
			if( tb+a+b+c+d+e+crate != 0 && !prev )
			{
				go = true;
			}
			else if( prev )
			{
				xcase = true;
			}
		}
		if( xcase )
		{
			GUI.out("The '" + Item + "' has already been lent to " + xteam+ ".");
		}
		if(go)
		{
			toBorrow(IO + "~" + Item + "~" + Team + "~");
			loadBorrow();
		}
		else if( !go && !none && !xcase )
		{
			GUI.out("I dont think we have '" + Item + "'.");
		}
		else if( !go && none && !xcase )
		{
			GUI.out("Okay.");
		}
	}
	public static void loadBorrow()
	{
		/* [Startup] [Borrow] [Read] [IO] [022] */
		if( debugMode )
		{
			GUI.out("LoadBorrow called.");
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
						GUI.out("Warning: Syntax error!");
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
						GUI.out("Warning: Syntax error!");
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
								lentLoc[lentPointer][1] = tb-1;
								ToolBox[tb-1][1] = tempTeam;
							}
							if( a != 0 )
							{
								lentLoc[lentPointer][0] = 1;
								lentLoc[lentPointer][1] = a-1;
								ToteA[a-1][1] = tempTeam;
							}
							if( b != 0 )
							{
								lentLoc[lentPointer][0] = 2;
								lentLoc[lentPointer][1] = b-1;
								ToteB[b-1][1] = tempTeam;
							}
							if( c != 0 )
							{
								lentLoc[lentPointer][0] = 3;
								lentLoc[lentPointer][1] = c-1;
								ToteC[c-1][1] = tempTeam;
							}
							if( d != 0 )
							{
								lentLoc[lentPointer][0] = 4;
								lentLoc[lentPointer][1] = d-1;
								ToteD[d-1][1] = tempTeam;
							}
							if( e != 0 )
							{
								lentLoc[lentPointer][0] = 5;
								lentLoc[lentPointer][1] = e-1;
								ToteE[e-1][1] = tempTeam;
							}
							if( crate != 0 )
							{
								lentLoc[lentPointer][0] = 6;
								lentLoc[lentPointer][1] = crate-1;
								Crate[crate-1][1] = tempTeam;
							}
							lentItem[lentPointer] = tempString;
							lentTeam[lentPointer] = tempTeam;
							lentPointer++;
						}
						else
						{
							GUI.out("I don't think we have '" + item + "'.");
							GUI.out("You may want to check the borrow file, or reset it.");
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
            GUI.out(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            GUI.out("Error reading file '" + fileName + "'");
        }
	}
	public static void loadPref()
	{
		/* [Startup] [Borrow] [Read] [IO] [022] */
		if( debugMode )
		{
			GUI.out("LoadPref called.");
		}
        String fileName = "preferences.txt";
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
            	else if( line.equals("started") )
            	{
            		firstStartup = false;
            	}
            	else if( line.equals("programName") )
				{
            		line = bufferedReader.readLine();
					programName = line;
				}
            	else if( line.equals("userName") )
            	{
            		line = bufferedReader.readLine();
            		if( !line.equals(null) )
            		{
            			un = true;
            			userName = line;
            		}
            	}
            	else if( line.equals("programColor") )
            	{
            		line = bufferedReader.readLine();
            		if( checkColor(line) )
            		{
            			programColor = colorConvert(line);
            		}
            		else if(!checkColor(line))
            		{
            			programColor = backupProgramColor;
            		}
            	}
            	else if( line.equals("userColor") )
            	{
            		line = bufferedReader.readLine();
            		if( checkColor(line) )
            		{
            			userColor = colorConvert(line);
            		}
            		else if(!checkColor(line))
            		{
            			userColor = backupUserColor;
            		}
            	}
            	else if( line.equals("language") )
            	{
            		line = bufferedReader.readLine();
            		language = line;
            	}
            	else if( line.equals("sarcasm") )
            	{
            		line = bufferedReader.readLine();
            		sarcasm = Integer.parseInt(line);
            	}
            }   
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            GUI.out(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            GUI.out("Error reading file '" + fileName + "'");
        }
        GUI.prefChange(programName, userName, programColor, userColor);
	}
	public static void borrowWrite(String string)
	{
		/* [IO] [Borrow] [023] */
		if( debugMode )
		{
			GUI.out("BorrowWrite called with input '" + string + "'.");
		}
		try{
            FileWriter fstream = new FileWriter("borrow.txt",true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            if( string!=null )
            {
            	fbw.write(string);
            	fbw.newLine();
            	fbw.close();
            }
        }catch (Exception e) {
            GUI.out("Couldn't print to the file.");
        }

    }
	public static void prefWrite(String string)
	{
		/* [IO] [Preferences] [069] */
		if( debugMode )
		{
			GUI.out("PrefWrite called with input '" + string + "'.");
		}
		try{
            FileWriter fstream = new FileWriter("preferences.txt",true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            if( string!=null )
            {
            	fbw.write(string);
            	fbw.newLine();
            	fbw.close();
            }
        }catch (Exception e) {
            GUI.out("Couldn't print to the file.");
        }
	}
	public static void toBorrow(String writer)
	{
		/* [Organizer] [Borrow] [IO] [024] */
		if( debugMode )
		{
			GUI.out("ToBorrow called with input '" + writer + "'.");
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
		borrowWrite(BL);
		borrowWrite(item);
		borrowWrite(team);
	}
	public static boolean checkColor(String color)
	{
		/* [Pref] [Color] [070] */
		if( debugMode )
		{
			GUI.out("CheckColor called with input '" + color + "'.");
		}
		boolean valid = false;
		if( color.equals("black") || color.equals("blue") || color.equals("cyan") || color.equals("dark_gray") || color.equals("gray") || color.equals("green") || color.equals("light_gray") || color.equals("magenta") || color.equals("orange") || color.equals("pink") || color.equals("red") || color.equals("white") || color.equals("yellow") )
		{
			valid = true;
		}
		return valid;
	}
	public static Color colorConvert(String color)
	{
		/* [Pref] [Color] */
		if( debugMode )
		{
			GUI.out("ColorConvert called with input '" + color + "'.");
		}
		Color retColor = Color.BLACK;
		if(color.equals("black"))
		{
			retColor = Color.BLACK;
		}
		if(color.equals("blue"))
		{
			retColor = Color.BLUE;
		}
		if(color.equals("cyan"))
		{
			retColor = Color.CYAN;
		}
		if(color.equals("dark_gray"))
		{
			retColor = Color.DARK_GRAY;
		}
		if(color.equals("gray"))
		{
			retColor = Color.GRAY;
		}
		if(color.equals("green"))
		{
			retColor = Color.GREEN;
		}
		if(color.equals("light_gray"))
		{
			retColor = Color.LIGHT_GRAY;
		}
		if(color.equals("magenta"))
		{
			retColor = Color.MAGENTA;
		}
		if(color.equals("orange"))
		{
			retColor = Color.ORANGE;
		}
		if(color.equals("pink"))
		{
			retColor = Color.PINK;
		}
		if(color.equals("red"))
		{
			retColor = Color.RED;
		}
		if(color.equals("white"))
		{
			retColor = Color.WHITE;
		}
		if(color.equals("yellow"))
		{
			retColor = Color.YELLOW;
		}
		return retColor;
	}
	public static void restoreBorrow()
	{
		/* [Cleanup] [Borrow] [Memory] [027] */
		if( debugMode )
		{
			GUI.out("RestoreBorrow called.");
		}
		clearBorrow();
		borrowWrite("// adminRestart = false");
		borrowWrite("// This is the file where the borrowed items are stored.");
		borrowWrite("// Line 1: B/L (Borrowed/Lent)");
		borrowWrite("// Line 2: Item Name (Exact)");
		borrowWrite("// Line 3: Team No.");
		for( int f=0; f<borrowFilePointer+1; f++ )
		{
			borrowWrite(borrowFile[f]);
			borrowFile[f] = "";
		}
		borrowedItem = borrowedItemBU;
		borrowedTeam = borrowedTeamBU;
		borrowedPointer = borrowedPointerBU;
		lentItem = lentItemBU;
		lentLoc = lentLocBU;
		lentTeam = lentTeamBU;
		lentPointer = lentPointerBU;
		borrowFilePointer = 0;
		ToteA = ToteABB;
		ToteB = ToteBBB;
		ToteC = ToteCBB;
		ToteD = ToteDBB;
		ToteE = ToteEBB;
		Crate = CrateBB;
		loadBorrow();
	}
	public static void restorePref()
	{
		clearPref();
		for( int f=0; f<prefFilePointer; f++ )
		{
			prefWrite(prefFile[prefFilePointer]);
		}
		programName = backupProgramName;
		userName = backupUserName;
		programColor = backupProgramColor;
		userColor = backupUserColor;
		prefFilePointer = 0;
		loadPref();
	}
	public static void listBorrow()
	{
		/* [Borrow] [Text] [Print] [Info] [028] */
		if( debugMode )
		{
			GUI.out("ListBorrow called.");
		}
		String fileName = "borrow.txt";
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
            boolean data = false;
            while((line = bufferedReader.readLine()) != null)
            {
            	if( line.startsWith("//") )
            	{
            		String ignore = line;
            	}
            	else if( line.toLowerCase().equals("b") )
				{
            		data = true;
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
						GUI.out("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						piece1 = "borrowed '";
						piece2 = tempString;
						piece3 = "' from ";
						piece4 = tempTeam;
						GUI.out(piece0+piece1+piece2+piece3+piece4+piece5);
					}
				}
				else if( line.toLowerCase().equals("l") )
				{
					data = true;
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
						GUI.out("Warning: Syntax error!");
						good = false;
						break;
					}
					if( good )
					{
						piece1 = "lent '";
						piece2 = tempString;
						piece3 = "' to ";
						piece4 = tempTeam;
						GUI.out(piece0+piece1+piece2+piece3+piece4+piece5);
					}
				}
			}
            if(!data)
        	{
        		GUI.out("Nothing is in the borrow file.");
        	}
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            GUI.out(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            GUI.out("Error reading file '" + fileName + "'");
        }
    }
	public static void nameChange(String who, String input)
	{
		/* [Pref] [GUI] [72] */
		String name = "";
		boolean w = false;
		if( debugMode )
		{
			GUI.out("NameChange called with input " + who + ", " + input + ".");
		}
		if( who.equals("my") )
		{
			name = userName;
			w = true;
		}
		else if( who.equals("your") )
		{
			name = programName;
			w = true;
		}
		else if( who.equals("null") )
		{
			GUI.out("Sure, but whose name?");
			w = false;
		}
		boolean q = false;
		int qc = 0;
		char first = ' ';
		char last = ' ';
		for( int f=0; f<input.length(); f++ )
		{
			if( input.charAt(f) == ( '\'' ) || input.charAt(f) == ('\"') )
			{
				if( first == '\'' || first == '\"' )
				{
					last = input.charAt(f);
				}
				else
				{
					first = input.charAt(f);
				}
				qc++;
			}
		}
		if( qc == 2 )
		{
			q = true;
		}
		else
		{
			GUI.out("Could you say that again, except with just the name in quotes?");
		}
		if( w && q )
		{
			int start = input.indexOf(first);
			input = input.substring(start+1,input.length());
			int end = input.indexOf(last);
			name = input.substring(0,end);
			if( who.equals("my") )
			{
				prefWrite("userName");
				prefWrite(name);
				userName = name;
				loadPref();
				GUI.out("Changed your name to '" + name + "'.");
			}
			else if( who.equals("your") )
			{
				prefWrite("programName");
				prefWrite(name);
				programName = name;
				loadPref();
				GUI.out("Changed my name to '" + name + "'.");
			}
		}
	}
	public static void colorChange(String who, String input)
	{
		/* [Color] [Pref] [GUI] [71] */
		if( debugMode )
		{
			GUI.out("ColorChange called with input " + who + ", " + input + ".");
		}
		Color color = Color.BLACK;
		String cw = "";
		String cc = "";
		boolean w = false;
		boolean vc = true;
		if( who.equals("my") )
		{
			color = userColor;
			w = true;
			cw = "userColor";
		}
		else if(who.equals("your"))
		{
			color = programColor;
			w = true;
			cw = "programColor";
		}
		else if(who.equals("null"))
		{
			GUI.out("Sure, just tell me which color you want changed.");
			w = false;
		}
		if( w )
		{
			if( input.contains("black") )
			{
				color = Color.BLACK;
				cc = "black";	
			}
			else if( input.contains("blue") )
			{
				color = Color.BLUE;
				cc = "blue";
			}
			else if( input.contains("cyan") )
			{
				color = Color.CYAN;
				cc = "cyan";
			}
			else if( input.contains("dark gray") )
			{
				color = Color.DARK_GRAY;
				cc = "dark_gray";
			}
			else if( input.contains("gray") )
			{
				color = Color.GRAY;
				cc = "gray";
			}
			else if( input.contains("green") )
			{
				color = Color.GREEN;
				cc = "green";
			}
			else if( input.contains("light gray") )
			{
				color = Color.LIGHT_GRAY;
				cc = "light_gray";
			}
			else if( input.contains("magenta") )
			{
				color = Color.MAGENTA;
				cc = "magenta";
			}
			else if( input.contains("orange") )
			{
				color = Color.ORANGE;
				cc = "orange";
			}
			else if( input.contains("pink") )
			{
				color = Color.PINK;
				cc = "pink";
			}
			else if( input.contains("red") )
			{
				color = Color.RED;
				cc = "red";
			}
			else if( input.contains("white") )
			{
				color = Color.WHITE;
				cc = "white";
			}
			else if( input.contains("yellow") )
			{
				color = Color.YELLOW;
				cc = "yellow";
			}
			else
			{
				GUI.out("I can only use the following colors:");
				GUI.out("Black, blue, cyan, dark gray, green, light gray, magenta, pink, red, white, and yellow.");
				vc = false;
			}
			if( vc )
			{
				prefWrite(cw);
				if( who.equals("my") )
				{
					userColor = color;
					prefWrite(cc);
					loadPref();
					GUI.out("Your color has been changed successfully to " + cc + ".");
				}
				else if( who.equals("your") )
				{
					programColor = color;
					prefWrite(cc);
					loadPref();
					GUI.out("My color has been changed successfully to " + cc + ".");
				}
			}
		}
	}
	public static void loadLibrary()
	{
		/* [Startup] [Organizer] [Load] [Memory] [029] */
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
		/* [Load] [Memory] [030]*/
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
		/* [Load] [Memory] [031] */
		ToteA[0][0] = "Rivet Box";
		ToteA[1][0] = "Pneumatics Hardware Box";
		ToteA[2][0] = "Zip Tie Box";
		ToteA[3][0] = "Encoders";
		ToteA[4][0] = "Pressure Tape";
		ToteA[5][0] = "Hack Saw";
		ToteA[6][0] = "PWM Crimp Tool";
		ToteA[7][0] = "Pneumatic Wheels";
		ToteA[8][0] = "Grease";
		ToteA[9][0] = "Craftsman Drill Bit Set";
		ToteA[10][0] = "Rivet Gun";
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
		/* [Load] [Memory] [032] */
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
		/* [Load] [Memory] [033] */
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
		/* [Load] [Memory] [034] */
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
		/* [Load] [Memory] [035] */
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
		/* [Load] [Memory] [036] */
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
		/* [Load] [Memory] [037] */
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
		/* [Load] [Memory] [038] */
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
		/* [Load] [Memory] [039] */
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
		/* [Load] [Memory] [040] */
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
		/* [Load] [Memory] [041] */
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
		/* [Load] [Memory] [042] */
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
		/* [Load] [Memory] [043] */
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
		/* [Load] [Memory] [044] */
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
		/* [Load] [Memory] [045] */
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
		/* [Search] [046] */
		int loc = 0;
		for( int f=0; f<41; f++ )
		{
 			if( item.equals(ToolBox[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int ToteACB(String item)
	{
		/* [Search] [047] */
		int loc = 0;
		for( int f=0; f<20; f++ )
		{
			if( item.equals(ToteA[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int ToteBCB(String item)
	{
		/* [Search] [048] */
		int loc = 0;
		for( int f=0; f<11; f++ )
		{
			if( item.equals(ToteB[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int ToteCCB(String item)
	{
		/* [Search] [049] */
		int loc = 0;
		for( int f=0; f<18; f++ )
		{
			if( item.equals(ToteC[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int ToteDCB(String item)
	{
		/* [Search] [050] */
		int loc = 0;
		for( int f=0; f<25; f++ )
		{
			if( item.equals(ToteD[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int ToteECB(String item)
	{
		/* [Search] [051] */
		int loc = 0;
		for( int f=0; f<8; f++ )
		{
			if( item.equals(ToteE[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static int CrateCB(String item)
	{
		/* [Search] [052] */
		int loc = 0;
		for( int f=0; f<16; f++ )
		{
			if( item.equals(Crate[f][0].toLowerCase()) )
			{
				loc = f+1;
				break;
			}
		}
		return loc;
	}
	public static void listToolBox()
	{
		/* [Text] [Print] [Info] [053] */
		GUI.out("The Toolbox contains the following:");
		for( int f=0; f<41; f++ )
		{
			if( ToolBox[f][1] == "0" )
			{
				GUI.out(ToolBox[f][0]);
			}
			if( ToolBox[f][1] != "0" )
			{
				GUI.out("The " + ToolBox[f][0] + " was lent to team " + ToolBox[f][1] + ".");
			}
		}
		GUI.out("");
	}
	public static void listToteA()
	{
		/* [Text] [Print] [Info] [054] */
		GUI.out("Tote A contains the following:");
		for( int f=0; f<20; f++ )
		{
			if( ToteA[f][1] == "0" )
			{
				GUI.out(ToteA[f][0]);
			}
			if( ToteA[f][1] != "0" )
			{
				GUI.out("* The " + ToteA[f][0] + " was lent to team " + ToteA[f][1] + ".");
			}		
		}
		GUI.out("");
	}
	public static void listToteB()
	{
		/* [Text] [Print] [Info] [055] */
		GUI.out("Tote B contains the following:");
		for( int f=0; f<11; f++ )
		{
			if( ToteB[f][1] == "0" )
			{
				GUI.out(ToteB[f][0]);
			}
			if( ToteB[f][1] != "0" )
			{
				GUI.out("* The " + ToteB[f][0] + " was lent to team " + ToteB[f][1] + ".");
			}		}
		GUI.out("");
	}
	public static void listToteC()
	{
		/* [Text] [Print] [Info] [055] */
		GUI.out("Tote C contains the following:");
		for( int f=0; f<18; f++ )
		{
			if( ToteC[f][1] == "0" )
			{
				GUI.out(ToteC[f][0]);
			}
			if( ToteC[f][1] != "0" )
			{
				GUI.out("* The " + ToteC[f][0] + " was lent to team " + ToteC[f][1] + ".");
			}		}
		GUI.out("");
	}
	public static void listToteD()
	{
		/* [Text] [Print] [Info] [056] */
		GUI.out("Tote D contains the following:");
		for( int f=0; f<25; f++ )
		{
			if( ToteD[f][1] == "0" )
			{
				GUI.out(ToteD[f][0]);
			}
			if( ToteD[f][1] != "0" )
			{
				GUI.out("* The " + ToteD[f][0] + " was lent to team " + ToteD[f][1] + ".");
			}		}
		GUI.out("");
	}
	public static void listToteE()
	{
		/* [Text] [Print] [Info] [057] */
		GUI.out("Tote E contains the following:");
		for( int f=0; f<8; f++ )
		{
			if( ToteE[f][1] == "0" )
			{
				GUI.out(ToteE[f][0]);
			}
			if( ToteE[f][1] != "0" )
			{
				GUI.out("* The " + ToteE[f][0] + " was lent to team " + ToteE[f][1] + ".");
			}		}
		GUI.out("");
	}
	public static void listCrate()
	{
		/* [Text] [Print] [Info] [058] */
		GUI.out("The Crate contains the following:");
		for( int f=0; f<16; f++ )
		{
			if( Crate[f][1] == "0" )
			{
				GUI.out(Crate[f][0]);
			}
			if( Crate[f][1] != "0" )
			{
				GUI.out("* The " + Crate[f][0] + " was lent to team " + Crate[f][1] + ".");
			}		}
		GUI.out("");
	}
	public static void shutDown() throws InterruptedException
	{
		/* [Cleanup] [Terminate] [059] */
		if( debugMode )
		{
			GUI.out("ShutDown called.");
		}
		typeWriter("Goodbye");
		on = false;
	}
}
