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
import java.net.URL;

public class PitAssistant {
	
	public static String[] fileDump;
	public static int fileDumpPointer;
	public static int locations;
	// Number of General Locations
	public static String[] locationNames;
	// Names of the General Locations
	public static int[] masterInventoryPointers;
	// Contains General Location sizes
	public static String[][][] masterInventory;
	// Level 1: General Location
	// Level 2: Index
	// Level 2: Category { 0:Item || 1+:Description }
	// Level 3: Contents
	public static String[][] masterInventoryBorrow;
	public static String[][] masterInventoryBB = new String[255][10000];
	public static int[][] masterInventoryDescriptionsPointer;
	
	public static String[] inventoryFile;
	public static int inventoryFilePointer;
	
	public static int[] found;
	
	// arrays and pointers??
	public static String[] nickName = new String[10000];
	public static int nickNamePointer = 0;
	public static String[] borrowFile = new String[10000];
	public static int borrowFilePointer = 0;
	public static String[] prefFile = new String[10000];
	public static int prefFilePointer = 0;
	public static String[] registerFile = new String[10000];
	public static int registerFilePointer = 0;
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
	public static String[] notes = new String[10000];
	public static int notesPointer = 0;
	public static int[] p = new int[8];
	public static String[] keywords = new String[10000];
	public static int keywordPointer = 0;
	// what the hell do these things do
	public static int TLLength = 42;
	public static int TALength = 20;
	public static int TBLength = 11;
	public static int TCLength = 18;
	public static int TDLength = 25;
	public static int TELength = 8;
	public static int CLength = 16;
	public static int ELength = 23;
	public static int PLength = 29;
	// storage, add to modular later
	public static String[][] ToolBox = new String[42][2];
	public static String[][] ToteA = new String[20][2];
	public static String[][] ToteB = new String[11][2];
	public static String[][] ToteC = new String[18][2];
	public static String[][] ToteD = new String[25][2];
	public static String[][] ToteE = new String[8][2];
	public static String[][] Crate = new String[16][2];
	// backup files
	public static String[][] ToolBoxBB = new String[42][2];
	public static String[][] ToteABB = new String[20][2];
	public static String[][] ToteBBB = new String[11][2];
	public static String[][] ToteCBB = new String[18][2];
	public static String[][] ToteDBB = new String[25][2];
	public static String[][] ToteEBB = new String[8][2];
	public static String[][] CrateBB = new String[16][2];
	// additional descriptor words
	public static String[] TLDesc = new String[42];
	public static String[] TADesc = new String[20];
	public static String[] TBDesc = new String[11];
	public static String[] TCDesc = new String[18];
	public static String[] TDDesc = new String[25];
	public static String[] TEDesc = new String[8];
	public static String[] CDesc = new String[16];
	// exclude from keywords
	public static String[] Exclusion = new String[23];
	public static String[] People = new String[29];
	// bunch of disorganized booleans
	public static boolean askHannah = false;
	public static boolean debugMode = false;
	public static boolean ziptie = false;
	public static boolean adminRestart = true;
	public static boolean on = true;
	public static boolean borrowCheck = true;
	public static boolean prefCheck = true;
	// preference items
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
	public static String MODE = "text";
	public static boolean exactCase = false;

	protected static PAInterface GUI = new PAInterface();
	URL loadGUI = PAInterface.class.getResource("load");
	URL inGUI = PAInterface.class.getResource("in");
	URL outGUI = PAInterface.class.getResource("out");
	
	protected static InventoryLoader inven = new InventoryLoader();
	URL loadInven = InventoryLoader.class.getResource("load");
	URL inInven = InventoryLoader.class.getResource("in");
	URL outInven = InventoryLoader.class.getResource("out");

	public static void main(String args[]) throws InterruptedException {
		/* [Organizer] [Main] [001] */
		initialize(true);
		while (on) {
			conductor();
		}
	}

	public static void initialize(boolean first) throws InterruptedException {
		/* [Organizer] [Load] [002] */
		GUI.flush();
		if (!loadedBorrow()) {
			if (createBorrow()) {
				resetBorrow();
			}
		}
		if (!loadedPref()) {
			if (createPref()) {
				resetPref();
			}
		}
		if (!loadedRegister()) {
			if (createRegister()) {

			}
		}
		inven.run();
		if (!started) {
			GUI.load(programName, userName, programColor, userColor);
			GUI.text("Loading libraries...");
			loadLibrary();
			GUI.text("Libraries loaded!");
			started = true;
		}
		inven.run();
		fileDump = inven.fileDump;
		fileDumpPointer = inven.fileDumpPointer;
		locations = inven.locations;
		locationNames = inven.locationNames;
		masterInventoryPointers = inven.masterInventoryPointers;
		masterInventory = inven.masterInventory;
		masterInventoryBorrow = inven.masterInventoryBorrow;
		masterInventoryDescriptionsPointer = inven.masterInventoryDescriptionsPointer;
		inventoryFile = inven.inventoryFile;
		inventoryFilePointer = inven.inventoryFilePointer;
		found = new int[locations+1];
		Thread.sleep(1500);
		if( !debugMode )
		{
			GUI.flush();
		}
		loadBorrow();
		loadPref();
		loadRegister();
		if (firstStartup) {
			tutorial();
		}
	}

	public static void tutorial() throws InterruptedException {
		System.out.println("Hello. My name is " + programName + ".");
		System.out
				.println("I will be giving you a brief overview of how I work in this tutorial.");
		System.out
				.println("My primary functionality is to help you locate items within, and help organize, your pit/area.");
		System.out
				.println("To import the items in your inventory, just let me know later.");
		System.out
				.println("Once you do, you can simply ask me to look for an item, and I will let you know where it is.");
		System.out.println("You can use whatever sentence structure you like.");
		System.out.println("- Look for an item");
		System.out.println("- Where is item?");
		System.out.println("- Whare di u put item pls");
		System.out.println("- item");
		System.out.println("- item??? can't find");
		System.out.println("And so on.");
		System.out
				.println("Another function I have is keeping track of borrowed items.");
		System.out
				.println("First, tell me who's giving who the items, or if we're borrowing or whatever.");
		System.out
				.println("Then, when prompted, tell me *exactly* what item is being lent/borrowed/whatever.");
		System.out.println("Finally, give me the other team's name/number.");
		System.out
				.println("If you mispell something or make a mistake, don't worry, you can just tell me to undo it.");
		System.out
				.println("Later, you can ask me what items have been borrowed or lent, or simply ask me for a list.");
		System.out
				.println("Don't worry. You can terminate this instance of me, but I'll still remember everything when you run me again.");
		System.out.println("I'm smart like that.");
		System.out
				.println("If you'd like, you can change my name by telling me you want to, or my text color, or yours.");
		System.out
				.println("Again, don't worry about closing me, or turning this computer off. I can remember things. Forever. For. Ever.");
		System.out
				.println("Finally, for some additional useful commands, just type 'help'!");
		System.out.println("That is the end of this tutorial.");
		prefWrite("started");
	}

	public static void typeWriter(String input) throws InterruptedException {
		int l = input.length();
		for (int f = 0; f < l; f++) {
			GUI.textNoLine(input.charAt(f) + "");
			Thread.sleep(50);
		}
		Thread.sleep(500);
		GUI.text("");
	}
	public static boolean loadedBorrow() {
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
		} catch (Exception e) {
			tf = false;
			GUI.text("Initial startup detected.");
			GUI.text("Creating new borrow file.");
		}
		return tf;
	}

	public static boolean loadedPref() {
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
		} catch (Exception e) {
			tf = false;
			GUI.text("Preferences file not detected.");
			GUI.text("Generating new preferences file.");
		}
		return tf;
	}

	public static boolean loadedRegister() {
		/* [Startup] [Load] [Register] */
		boolean tf = false;
		String fileName = "register.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			fileReader.close();
			bufferedReader.close();
			tf = true;
		} catch (Exception e) {
			tf = false;
			GUI.text("Register File not detected.");
			GUI.text("Creating new register file.");
		}
		return tf;
	}

	public static boolean createBorrow() {
		/* [Startup] [Load] [Borrow] [003] */
		try {
			FileWriter fw = new FileWriter("borrow.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			fw.close();
			bw.close();
			out.close();
		} catch (Exception E) {
			GUI.text("Error creating borrow file.");
		}
		return true;
	}

	public static boolean createRegister() {
		/* [Startup] [Load] [Register] */
		try {
			FileWriter fw = new FileWriter("register.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			fw.close();
			bw.close();
			out.close();
		} catch (Exception E) {
			GUI.text("Error creating register file.");
		}
		return true;
	}

	public static boolean createPref() {
		/* [Startup] [Load] [Preferences] [066] */
		try {
			FileWriter fw = new FileWriter("preferences.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			fw.close();
			bw.close();
			out.close();
		} catch (Exception E) {
			GUI.text("Error creating preferences file.");
		}
		return true;
	}

	public static void resetBorrow() {
		/* [Cleanup] [Borrow] [Memory] [026] */
		if (debugMode) {
			GUI.text("ResetBorrow called.");
		}
		for( int f=0; f<locations; f++ )
		{
			for( int g=0; g<masterInventoryPointers[f]; g++ )
			{
				masterInventoryBorrow[f][g] = "0";
			}
		}
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
		for (int f = 0; f < 10000; f++) {
			borrowedItem[f] = "";
			borrowedTeam[f] = "";
			lentItem[f] = "";
			lentLoc[f][0] = 0;
			lentLoc[f][1] = 0;
			lentTeam[f] = "";
		}
		masterInventoryBB = masterInventoryBorrow;
	}

	public static void resetPref() {
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
		prefWrite("started");
		programName = "Pit Assistant";
		userName = "";
		un = false;
		programColor = Color.BLACK;
		userColor = Color.BLUE;
		language = "english";
		sarcasm = 0;
		loadPref();
	}

	public static void saveBorrow() {
		/* [Borrow] [Memory] [062] */
		String fileName = "borrow.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line != null) {
					borrowFile[borrowFilePointer] = line;
					borrowFilePointer++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			GUI.text("I had an issue while trying to read from the borrow file.");
		}
	}

	public static void savePref() {
		String fileName = "preferences.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line != null) {
					prefFile[prefFilePointer] = line;
					prefFilePointer++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			GUI.text("I had an issue while trying to read from the preferences file.");
		}
		backupProgramName = programName;
		backupUserName = userName;
		backupun = un;
		backupProgramColor = programColor;
		backupUserColor = userColor;
		backupSarcasm = sarcasm;
		backupLanguage = language;
	}

	public static void saveRegister() {
		String fileName = "register.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line != null) {
					registerFile[registerFilePointer] = line;
					registerFilePointer++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			GUI.text("I had an issue while trying to read from the register file.");
		}
	}

	public static void clearBorrow() {
		/* [Cleanup] [Borrow] [Memory] [025] */
		if (debugMode) {
			GUI.text("ClearBorrow called.");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("borrow.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			GUI.text("You can't reset something I don't have yet.");
		}
	}

	public static void clearPref() {
		/* [Cleanup] [Borrow] [Memory] [068] */
		if (debugMode) {
			GUI.text("ClearPref called.");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("preferences.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			GUI.text("You can't reset something I don't have yet.");
		}
	}

	public static void greet() {
		/* [Startup] [Text] [Print] [Info] [005] */
		GUI.text("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-[" + programName
				+ "]-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		GUI.text("");
		GUI.text("  Hi, I'm Pit Assistant (v5.1). I can look for things, and tell you what's in our totes and boxes.");
		GUI.text("Pit Assisstant (v5.1) Theoretically(TM) supports description-based queries and all sentence structures.");
		GUI.text("         Pit Assistant (v5.1) Theoretically(TM) keeps track of borrowed items from a file.");
		GUI.text("       Pit Assistant (v5.1) also Theoretically(TM) supports and keeps track of user preferences.");
		GUI.text("");
		GUI.text("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v5.1)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		GUI.text("");
		GUI.text("How may I help you?");
	}

	public static void conductor() throws InterruptedException {
		/* [Organizer] [Main] [006] */
		reset();
		String input = input();
		if (input != "") {
			boolean normal = cmd(input);
			if (normal) {
				parse(input);
				if (!exactCase) {
					recursiveSearch();
				}
				recursiveOutput();
			}
			if (on) {
				menu();
			} else if (!on && borrowCheck && prefCheck) {
				GUI.command("end");
			}
		}
	}

	public static void reset() {
		/* [Cleanup] [Pointer] [Borrow] [007] */
		resultPointer = 0;
		keywordPointer = 0;
		for (int f = 0; f < printedPointer; f++) {
			printed[f] = "";
		}
		printedPointer = 1;
		ziptie = false;
	}

	public static boolean cmd(String input) throws InterruptedException {
		if( debugMode )
		{
			GUI.text("Cmd called with input " + input + ".");
		}
		boolean normal = false;
		String data = input.toLowerCase();
		if( data.startsWith("cmd: ") )
		{
			String parsed = data.substring(5,data.length());
			command(parsed);
		}
		else
		{
			normal = caser(input);
		}
		return normal;
	}
	public static void command(String input)
	{
		String sub = "";
		int le = input.length();
		if( debugMode )
		{
			GUI.text("Command called with input " + input  + ".");
		}
		if( input.startsWith("ls ") )
		{
			
		}
		else if( input.startsWith("get ") )
		{
			sub = input.substring(4,le);
			if( debugMode )
			{
				GUI.text("get");
				GUI.text(sub);
			}
			if( sub.equals("locations") )
			{
				GUI.text(locations+"");
			}
		}
	}
	public static boolean caser(String input) throws InterruptedException {
		/* [Organizer] [Text] [Process] [008] */
		if (debugMode) {
			GUI.text("Caser called.");
		}
		boolean normal = false;
		boolean skip = false;
		String data = input.toLowerCase();
		if ((!data.contains("cyan") && data.contains("cya"))
				|| data.contains("bye") || data.contains("terminate")
				|| data.contains("shut down") || data.contains("shutdown") || data.contains("done here")) {
			shutDown();
			skip = true;
		}
		if (data.contains("flush")) {
			for (int f = 0; f < 50; f++) {
				GUI.flush();
			}
			skip = true;
		}
		if (data.contains("changelog")) {
			GUI.out("Here is the changelog:");
			GUI.text("(v1.0)  ::  Basic search function for totes.");
			GUI.text("(v1.1)  ::  Added some more search functinality.");
			GUI.text("(v1.2)  ::  Added ability to list things in totes.");
			GUI.text("(v1.3)  ::  Added support for sentence structures.");
			GUI.text("(v1.4)  ::  Added Easter Eggs and bug fixes.");
			GUI.text("(v1.5)  ::  Incorporated description-based search.");
			GUI.text("(v1.6)  ::  Bug fixes. Added some additional commands and Easter Eggs.");
			GUI.text("(v1.7)  ::  Improved Search Algorithm. Bug Fixes. Consolidated Memory Arrays.");
			GUI.text("(v1.8)  ::  Critical Bug Fix.");
			GUI.text("(v1.9)  ::  Added ability to read and keep track of borrowed items from a file.");
			GUI.text("(v1.10) ::  Found several bugs with reading from the borrow file.");
			GUI.text("(v2.0)  ::  Fixed all 23 borrow function bugs. Borrow file is now saved permanently.");
			GUI.text("(v2.1)  ::  Organized classes for easier debugging.");
			GUI.text("(v2.2)  ::  Added ability to append the borrow file from within the program.");
			GUI.text("(v2.3)  ::  Consolidated input function. Fixed bugs in borrow functionality.");
			GUI.text("(v2.4)  ::  Added item validity checks for reading and writing.");
			GUI.text("(v2.5)  ::  Started tagging classes for easier debugging.");
			GUI.text("(v2.6)  ::  Added restore borrow file function.");
			GUI.text("(v2.7)  ::  Made search function also search for borrowed items.");
			GUI.text("(v3.0)  ::  Created a basic GUI with a scroll bar. Mentioning the scroll bar it took 4 hours.");
			GUI.text("(v3.1)  ::  Prepared program to be converted into an executable.");
			GUI.text("(v3.2)  ::  Added the 604 logo.");
			GUI.text("(v3.3)  ::  Made PitAssistant Executable friendly.");
			GUI.text("(v3.4)  ::  Fixed bug when borrowing from teams.");
			GUI.text("(v3.5)  ::  Added colors and user input display.");
			GUI.text("(v3.6)  ::  Made colors changeable from within the program.");
			GUI.text("(v3.7)  ::  Added reset and restore preferences functionality.");
			GUI.text("(v3.8)  ::  Made window close on program termination.");
			GUI.text("(v3.9)  ::  Added ability to set names to user and program.");
			GUI.text("(v3.10) ::  Started work on a tutorial that launches on initial startup.");
			GUI.text("(v3.11) ::  Did some stuff with name parsing.");
			GUI.text("(v4.0)  ::  Added Voice Synthesis!");
			GUI.text("(v4.1)  ::  Minor text fixes");
			GUI.text("(v4.2)  ::  Added registry mode.");
			GUI.text("(v4.3)  ::  Added Exact Search cases!!");
			GUI.text("(v4.4)  ::  Added escape to register mode");
			GUI.text("(v4.5)  ::  Prepared InventoryLoader for new modular inventory implementation");
			GUI.text("(v4.6)  ::  Setup new recursive search algorithm for modular inventory implementation");
			GUI.text("(v4.7)  ::  Finished modular inventory setup. Beginning the long and arduous process of debugging.");
			GUI.text("(v5.0)  ::  Debugging complete (and way ahead of schedule). Modular Inventory is now up and running.");
			GUI.text("(v5.1)  ::  Made PitAssistant executable again.");
			skip = true;
		}
		if (data.contains("help") && !data.contains("find")
				|| data.contains("help") && !data.contains("look")) {
			GUI.text("I can look for things by name or by description, theoretically.");
			GUI.text("I can also list things in the totes.");
			GUI.text("Say 'flush' to clear the output thingy.");
			GUI.text("Say 'changelog' to view the changelog.");
			GUI.text("You can tell me what items have been borrowed or lent.");
			GUI.text("If you want, you can ask me to change what color text we type in, or what you want to call me.");
			GUI.text("You can also toggle debug mode by telling me to.");
			skip = true;
		}
		if (data.contains("todo") || data.contains("to-do")) {
			GUI.text("1.  BUG FIXES:");
			GUI.text("   a) CRITICAL MEMORY LEAK");
			GUI.text("");
			GUI.text("2.  Emoji Support");
			GUI.text("3.  *Consolidate pointers");
			GUI.text("4.  Save reponses to a text file for easy translation for international teams");
			GUI.text("5.  Organize cases [Standalone, Priority, Easter Egg, Repeatable]");
			GUI.text("6.  *Add undo borrow function");
			GUI.text("7.  Add sentience easter egg");
			GUI.text("8.  Add a pager maybe?");
			GUI.text("9.  Work on social communications");
			GUI.text("10. Make header stay same size regardless of name");
			GUI.text("11. Make note taking independent from borrow file");
			skip = true;
		}
		if (data.contains("thank")) {
			if (data.contains("thanks") || data.contains("you")) {
				GUI.out("You're welcome.");
				skip = true;
			} else if (data.contains("goodness") || data.contains("god")) {
				GUI.out("Yay?");
				skip = true;
			}
		}
		if (data.contains("ugh")) {
			GUI.out("Sorry.");
			skip = true;
		}
		if ((data + " ").contains("oops")) {
			GUI.out("Uh oh.");
			skip = true;
		}
		if (data.contains("git")) {
			GUI.out("Go away, Ryan.");
			skip = true;
		}
		if ((" " + data).contains(" ew")) {
			GUI.out("Well, I'm sorry.");
			skip = true;
		}
		if (data.contains("cls")) {
			GUI.out("Cls? What's that? I'm supposed to be based off of a human being, Pranav.");
			skip = true;
		}
		if (data.contains("reload") || data.contains("restore")) {
			if (data.contains("borrow")) {
				restoreBorrow();
				GUI.out("I've successfully restored the borrow file.");
				skip = true;
			} else if (data.contains("pref")) {
				restorePref();
				GUI.out("I've successfully restored your preferences.");
				skip = true;
			}
		} else if (data.contains("clear") || data.contains("reset")
				|| data.contains("purge") || data.contains("clean")
				|| data.contains("wipe")) {
			if (data.contains("borrow")) {
				resetBorrow();
				GUI.out("I've cleared all memories of what we've borrowed.");
				GUI.out("I've saved a backup of it, though, so just let me know if you want to restore it.");
				skip = true;
			} else if (data.contains("pref")) {
				resetPref();
				GUI.out("I've reset your preferences.");
				GUI.out("I've saved a backup, so just let me know if you want to restore them.");
				skip = true;
			}
		} else if (data.contains("borrow") || data.contains("lend")
				|| data.contains("lent")) {
			data = " " + data + " ";
			if (data.contains("what") || data.contains("show")
					|| data.contains("tell") || data.contains("list")
					|| data.contains("print") || data.contains("see")
					|| data.contains("are") || data.contains(" in ")) {
				listBorrow();
			} else if (data.contains("check") && !data.contains("out")) {
				listBorrow();
			} else if ((data.contains("we ") && data.contains("borrowed"))
					|| (data.contains("borrow") && data.contains("from") && data
							.contains("we "))
					|| (data.contains(" us ") && data.contains("lent"))
					|| (data.contains("lent") && data.contains("to") && data
							.contains(" us "))) {
				borrow("in");
			} else if ((data.contains("us ") && data.contains("borrowed"))
					|| (data.contains("borrow") && data.contains("from") && data
							.contains(" us "))
					|| (data.contains("we ") && data.contains("lent") && data
							.contains("to"))
					|| (data.contains("we ") && data.contains("lent"))) {
				borrow("out");
			} else if (data.contains("borrow") || data.contains("lend")
					|| data.contains("lent")) {
				borrow("null");
			}
			skip = true;
		}
		if (data.contains("note")) {
			if (data.contains("what") || data.contains("show")
					|| data.contains("tell") || data.contains("list")
					|| data.contains("print") || data.contains("see")) {
				printNotes();
				skip = true;
			}
		} else if ((data.contains("make") && data.contains("note"))
				|| data.contains("write") || data.contains("record")) {
			takeNote(data);
			skip = true;
		}
		boolean name = false;
		for (int f = 0; f < nickNamePointer; f++) {
			if (data.contains(nickName[f])) {
				name = true;
				break;
			}
		}
		if (data.contains("chang") || data.contains("set")
				|| data.contains("turn") || data.contains("make")) {
			if (data.contains("color")) {
				if (data.contains("my")) {
					colorChange("my", data);
					skip = true;
				} else if (data.contains("your")) {
					colorChange("your", data);
					skip = true;
				} else {
					colorChange("null", data);
					skip = true;
				}
			}
			if (data.contains("name")) {
				if (data.contains("my")) {
					nameChange("my", input);
					skip = true;
				} else if (data.contains("your")) {
					nameChange("your", input);
					skip = true;
				} else {
					nameChange("null", data);
					skip = true;
				}
			}
		} else if (name) {
			GUI.out("Yes?");
			reply = true;
			skip = true;
		} else if (data.startsWith("hi!") || data.startsWith("hi ")
				|| data.startsWith("hello") || data.startsWith("greetings")
				|| data.startsWith("hey")) {
			GUI.out("Hi.");
			Thread.sleep(500);
			reply = true;
			skip = true;
		}
		if (data.contains("initialize") || data.contains("restart")) {
			GUI.out("Reinitializing.");
			GUI.flush();
			initialize(false);
			skip = true;
		}
		if (data.contains("pneumatic fluid")) {
			GUI.out("You just have to keep looking for it. You'll find it eventually.");
			skip = true;
		}
		if (data.contains("three") || data.contains("3")) {
			if (data.contains("law")) {
				GUI.out("I'm offended.");
				skip = true;
			}
		}
		if (data.contains("register") && data.contains("print")) {
			printRegister();
			skip = true;
		} else if ((data.contains("mode") || data.contains("want to"))
				&& (data.contains("register") || data.contains("registry") || data
						.contains("registration"))) {
			GUI.out("Engaging Registery Mode.");
			register();
			skip = true;
		}
		if (data.contains("toggle")
				&& (data.contains("voice") || data.contains("text") || data
						.contains("output"))) {
			if (MODE.equals("text")) {
				MODE = "voice";
				GUI.out("Speech synthesizer enabled.");
				skip = true;
			} else if (MODE.equals("voice")) {
				MODE = "text";
				GUI.out("Speech synthesizer disabled.");
				skip = true;
			}
		} else if (data.contains("talk")
				|| ((data.contains("voice") && data.contains("use")))
				|| data.contains("speak to me")) {
			MODE = "voice";
			GUI.out("Speech synthesizer enabled.");
			skip = true;
		} else if (data.contains("shut up")
				|| (data.contains("text") && data.contains("use"))) {
			MODE = "text";
			GUI.out("Speech synthesizer disabled.");
			skip = true;
		}
		if (data.contains("debug") && data.contains(" on")) {
			debugMode = true;
			GUI.out("Debug mode enabled.");
			skip = true;
		} else if (data.contains("debug") && data.contains(" enable")) {
			debugMode = true;
			GUI.out("Debug mode enabled.");
			skip = true;
		} else if (data.contains("debug") && data.contains(" off")) {
			debugMode = false;
			GUI.out("Debug mode disabled.");
			skip = true;
		}else if (data.contains("debug") && data.contains(" disable")) {
			debugMode = false;
			GUI.out("Debug mode disabled.");
			skip = true;
		} else if (data.contains("debug") && data.contains("toggle")) {
			debugMode = !debugMode;
			if (debugMode) {
				GUI.out("Debug mode enabled.");
			} else {
				GUI.out("Debug mode disabled.");
			}
			skip = true;
		}
		if (data.contains("ziptie") && data.contains("dream")) {
			GUI.out("Allow me to refer you to our robot.");
		}
		if (data.contains("door") || data.contains("hinge")) {
			GUI.out("Really? Again?");
			skip = true;
		}
		if (data.contains("replacement") && data.contains("hannah")) {
			GUI.out("I'm not Hannah's replacement. Hannah is a wonderful and unique human being. I am a computer program.");
			skip = true;
		}
		if (data.contains("backpack")) {
			GUI.out("If you're looking for a backpack, I would ask Pranav.");
			skip = true;
		}
		if (data.contains("memes")) {
			GUI.out("Stop looking for memes.");
			skip = true;
		}
		if (!skip) {
			boolean checkPerson = false;
			for (int f = 0; f < 29; f++) {
				if (data.contains(People[f]) && data.contains("where")) {
					checkPerson = true;
					break;
				}
			}
			if (checkPerson) {
				GUI.out("I'm a tote organizer. Go ask Rayna or something.");
			} else if (data.contains("where") && data.contains("hannah")
					&& !askHannah) {
				GUI.out("I'm right her- Oh.");
				Thread.sleep(500);
				GUI.out("You meant *that* Hannah.");
				askHannah = true;
			} else if (data.contains("where") && data.contains("hannah")
					&& askHannah) {
				GUI.out("Haven't you already tried looking for me?");
			} else if (data.contains("love")) {
				GUI.out("If you're looking for love, you'll have to look elsewhere.");
			}
			else
			{
				for( int f=0; f<locations; f++ )
				{
					if( data.contains(locationNames[f].toLowerCase()) )
					{
						recursiveList(f);
						skip = true;
					}
				}
			}
			if (data.contains("inventory") || data.contains("list")
					|| data.contains(" all") || data.contains("everything")) {
				recursiveList(-1);
				skip = true;
			} else {
				normal = true;
				if (data.contains("ziptie") || data.contains("zip tie")) {
					ziptie = true;
				}
			}
		}
		return normal;
	}

	public static void recursiveList(int which)
	{
		if( which == -1 )
		{
			for( int f=0; f<locations; f++ )
			{
				GUI.text(locationNames[f]);
			}
		}
		else
		{
			for( int f=0; f<masterInventoryPointers[which]; f++ )
			{
				GUI.text(masterInventory[which][f][0]);
			}
		}
	}
	public static void parse(String input) {
		/* [Data] [Text] [Process] [009] */
		if (debugMode) {
			GUI.out("Parse called with input '" + input + "'.");
		}
		String data = input.toLowerCase();
		while (data.endsWith(" ")) {
			data = data.substring(0, data.length() - 1);
		}
		if (data.endsWith(".") || data.endsWith("?") || data.endsWith("!")) {
			data = data.substring(0, data.length() - 1);
		}
		while (data.endsWith(" ")) {
			data = data.substring(0, data.length() - 1);
		}
		if (data.contains("\"") && !data.contains("name")) {
			recursiveExactSearch(parseExact(data));
			exactCase = true;
		} else {
			exactCase = false;
			data = data + " ";
			while (data.contains(" ")) {
				int end = data.indexOf(" ");
				String keyword = data.substring(0, end);
				String temp = data.substring(end + 1, data.length());
				boolean pass = true;
				for (int f = 0; f < 23; f++) {
					if (keyword.equals(Exclusion[f])) {
						pass = false;
						break;
					}
				}
				if (keyword.length() < 3) {
					pass = false;
				}
				if (pass) {
					if (debugMode) {
						GUI.text(keyword);
					}
					keywords[keywordPointer] = keyword;
					keywordPointer++;
				}
				data = temp;
			}
		}
	}

	public static String parseExact(String data) {
		boolean q = false;
		int qc = 0;
		char first = ' ';
		char last = ' ';
		for (int f = 0; f < data.length(); f++) {
			if (data.charAt(f) == ('\'') || data.charAt(f) == ('\"')) {
				if (first == '\'' || first == '\"') {
					last = data.charAt(f);
				} else {
					first = data.charAt(f);
				}
				qc++;
			}
		}
		if (qc == 2) {
			q = true;
		} else {
			GUI.out("If you want to conduct an exact search, try again with the item in quotes.");
		}
		if (q) {
			int start = data.indexOf(first);
			data = data.substring(start + 1, data.length());
			int end = data.indexOf(last);
			data = data.substring(0, end);
		}
		return data;
	}

	public static void menu() {
		/* [Cleanup] [Text] [Print] [010] */
		if (reply) {
			reply = false;
		} else {
			GUI.out("How else may I help you?");
		}
	}

	public static void recursiveOutput()
	{
		if( debugMode )
		{
			GUI.text("RecursiveOutput called.");
		}
		if( resultPointer == 0 && !exactCase )
		{
			GUI.out("Sorry, I couldn't find what you were looking for.");
		}
		else if( !exactCase )
		{
			GUI.out("Okay, here's what I found:");
		}
		GUI.out("");
		if( exactCase )
		{
			for( int f=0; f<locations; f++ )
			{
				if( found[f] > 0 )
				{
					if( antiRepeat(masterInventory[f][results[0][1]][0]) )
					{
						GUI.out("It will be in " + locationNames[f] + ".");
					}
					if( masterInventoryBorrow[f][results[0][1]] != "0" )
					{
						GUI.out("* The " + masterInventory[f][results[0][1]][0] + " was lent to team " + masterInventoryBorrow[f][results[0][1]] + ".");
					}
				}
			}
			if( found[locations] > 0 )
			{
				if( antiRepeat(borrowedItem[results[0][1]]) )
				{
					GUI.out("We should have borrowed it.");
				}
			}
		}
		else
		{
			for( int f=0; f<locations; f++ )
			{
				if( found[f] > 0 )
				{
					GUI.out("In the " + locationNames[f] + ", we should theoretically have the following items:");
					int totalSoFar = 0;
					for( int g=0; g<f; g++ )
					{
						totalSoFar += found[g];
					}
					for( int g=totalSoFar; g<totalSoFar+found[f]; g++ )
					{
						if( antiRepeat(masterInventory[f][results[g][1]][0]) )
						{
							if( masterInventoryBorrow[f][results[g][1]] == "0" )
							{
								GUI.out(masterInventory[f][results[g][1]][0]);
							}
							else
							{
								GUI.out("* The " + masterInventory[f][results[g][1]][0] + " was lent to team " + masterInventoryBorrow[f][results[g][1]] + ".");
							}
						}
					}
					GUI.out("");
				}
			}
			if (found[locations] > 0) {
				GUI.out("We should theoretically have borrowed the following items:");
				int totalWithoutBorrow = 0;
				for( int f=0; f<locations-1; f++ )
				{
					totalWithoutBorrow += found[f];
				}
				for (int f = totalWithoutBorrow; f < totalWithoutBorrow + found[locations]; f++) {
					if (antiRepeat(borrowedItem[results[f][1]])) {
						GUI.out(borrowedItem[results[f][1]]);
					}
				}
				GUI.out("");
			}
		}
	}
	/*
	public static void output() {
		if (debugMode) {
			GUI.text("Output called.");
		}
		if (resultPointer == 0 && !exactCase) {
			GUI.out("Sorry, I couldn't find what you were looking for. Maybe you meant something else?");
		} else {
			if (!exactCase) {
				GUI.out("Okay, here's what I found:");
			}
		}
		GUI.out("");
		if (exactCase) {
			if (p[0] > 0) {
				if (antiRepeat(ToolBox[results[0][1]][0])) {
					if (ToolBox[results[0][1]][1] == "0") {
						GUI.out("It will be in the ToolBox.");
					}
					if (ToolBox[results[0][1]][1] != "0") {
						GUI.out("* The " + ToolBox[results[0][1]][0]
								+ " was lent to team "
								+ ToolBox[results[0][1]][1] + ".");
					}
				}
			} else if (p[1] > 0) {
				if (antiRepeat(ToteA[results[0][1]][0])) {
					if (ToteA[results[0][1]][1] == "0") {
						GUI.out("It will be in Tote A.");
					}
					if (ToteA[results[0][1]][1] != "0") {
						GUI.out("* The " + ToteA[results[0][1]][0]
								+ " was lent to team "
								+ ToteA[results[0][1]][1] + ".");
					}
				}
			} else if (p[2] > 0) {
				if (antiRepeat(ToteB[results[0][1]][0])) {
					if (ToteB[results[0][1]][1] == "0") {
						GUI.out("It will be in Tote B.");
					}
					if (ToteB[results[0][1]][1] != "0") {
						GUI.out("* The " + ToteB[results[0][1]][0]
								+ " was lent to team "
								+ ToteB[results[0][1]][1] + ".");
					}
				}
			} else if (p[3] > 0) {
				if (antiRepeat(ToteC[results[0][1]][0])) {
					if (ToteC[results[0][1]][1] == "0") {
						GUI.out("It will be in Tote C.");
					}
					if (ToteC[results[0][1]][1] != "0") {
						GUI.out("* The " + ToteC[results[0][1]][0]
								+ " was lent to team "
								+ ToteC[results[0][1]][1] + ".");
					}
				}
			} else if (p[4] > 0) {
				if (antiRepeat(ToteD[results[0][1]][0])) {
					if (ToteD[results[0][1]][1] == "0") {
						GUI.out("It will be in Tote D.");
					}
					if (ToteD[results[0][1]][1] != "0") {
						GUI.out("* The " + ToteD[results[0][1]][0]
								+ " was lent to team "
								+ ToteD[results[0][1]][1] + ".");
					}
				}
			} else if (p[5] > 0) {
				if (antiRepeat(ToteE[results[0][1]][0])) {
					if (ToteE[results[0][1]][1] == "0") {
						GUI.out("It will be in Tote E.");
					}
					if (ToteE[results[0][1]][1] != "0") {
						GUI.out("* The " + ToteE[results[0][1]][0]
								+ " was lent to team "
								+ ToteE[results[0][1]][1] + ".");
					}
				}
			} else if (p[6] > 0) {
				if (antiRepeat(Crate[results[0][1]][0])) {
					if (Crate[results[0][1]][1] == "0") {
						GUI.out("It will be in the Crate.");
					}
					if (Crate[results[0][1]][1] != "0") {
						GUI.out("* The " + Crate[results[0][1]][0]
								+ " was lent to team "
								+ Crate[results[0][1]][1] + ".");
					}
				}
			} else if (p[7] > 0) {
				if (antiRepeat(borrowedItem[results[0][1]])) {
					GUI.out("We should have borrowed it.");
				}
			}
			GUI.out("");
		} else {
			if (p[0] > 0) {
				GUI.out("In the Toolbox, we should theoretically have the following items:");
				for (int f = 0; f < p[0]; f++) {
					if (antiRepeat(ToolBox[results[f][1]][0])) {
						if (ToolBox[results[f][1]][1] == "0") {
							GUI.out(ToolBox[results[f][1]][0]);
						}
						if (ToolBox[results[f][1]][1] != "0") {
							GUI.out("* The " + ToolBox[results[f][1]][0]
									+ " was lent to team "
									+ ToolBox[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[1] > 0) {
				GUI.out("In Tote A, we should theoretically have the following items:");
				for (int f = p[0]; f < p[0] + p[1]; f++) {
					if (antiRepeat(ToteA[results[f][1]][0])) {
						if (ToteA[results[f][1]][1] == "0") {
							GUI.out(ToteA[results[f][1]][0]);
						}
						if (ToteA[results[f][1]][1] != "0") {
							GUI.out("* The " + ToteA[results[f][1]][0]
									+ " was lent to team "
									+ ToteA[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[2] > 0) {
				GUI.out("In Tote B, we should theoretically have the following items:");
				for (int f = p[0] + p[1]; f < p[0] + p[1] + p[2]; f++) {
					if (antiRepeat(ToteB[results[f][1]][0])) {
						if (ToteB[results[f][1]][1] == "0") {
							GUI.out(ToteB[results[f][1]][0]);
						}
						if (ToteB[results[f][1]][1] != "0") {
							GUI.out("* The " + ToteB[results[f][1]][0]
									+ " was lent to team "
									+ ToteB[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[3] > 0) {
				GUI.out("In Tote C, we should theoretically have the following items:");
				for (int f = p[0] + p[1] + p[2]; f < p[0] + p[1] + p[2] + p[3]; f++) {
					if (antiRepeat(ToteC[results[f][1]][0])) {
						if (ToteC[results[f][1]][1] == "0") {
							GUI.out(ToteC[results[f][1]][0]);
						}
						if (ToteC[results[f][1]][1] != "0") {
							GUI.out("* The " + ToteC[results[f][1]][0]
									+ " was lent to team "
									+ ToteC[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[4] > 0) {
				GUI.out("In Tote D, we should theoretically have the following items:");
				for (int f = p[0] + p[1] + p[2] + p[3]; f < p[0] + p[1] + p[2]
						+ p[3] + p[4]; f++) {
					if (antiRepeat(ToteD[results[f][1]][0])) {
						if (ToteD[results[f][1]][1] == "0") {
							GUI.out(ToteD[results[f][1]][0]);
						}
						if (ToteD[results[f][1]][1] != "0") {
							GUI.out("* The " + ToteD[results[f][1]][0]
									+ " was lent to team "
									+ ToteD[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[5] > 0) {
				GUI.out("In Tote E, we should theoretically have the following items:");
				for (int f = p[0] + p[1] + p[2] + p[3] + p[4]; f < p[0] + p[1]
						+ p[2] + p[3] + p[4] + p[5]; f++) {
					if (antiRepeat(ToteE[results[f][1]][0])) {
						if (ToteE[results[f][1]][1] == "0") {
							GUI.out(ToteE[results[f][1]][0]);
						}
						if (ToteE[results[f][1]][1] != "0") {
							GUI.out("* The " + ToteE[results[f][1]][0]
									+ " was lent to team "
									+ ToteE[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[6] > 0) {
				GUI.out("In the Crate, we should theoretically have the following items:");
				for (int f = p[0] + p[1] + p[2] + p[3] + p[4] + p[5]; f < p[0]
						+ p[1] + p[2] + p[3] + p[4] + p[5] + p[6]; f++) {
					if (antiRepeat(Crate[results[f][1]][0])) {
						if (Crate[results[f][1]][1] == "0") {
							GUI.out(Crate[results[f][1]][0]);
						}
						if (ToolBox[results[f][1]][1] != "0") {
							GUI.out("* The " + Crate[results[f][1]][0]
									+ " was lent to team "
									+ Crate[results[f][1]][1] + ".");
						}
					}
				}
				GUI.out("");
			}
			if (p[7] > 0) {
				GUI.out("We should theoretically have borrowed the following items:");
				for (int f = p[0] + p[1] + p[2] + p[3] + p[4] + p[5] + p[6]; f < p[0]
						+ p[1] + p[2] + p[3] + p[4] + p[5] + p[6] + p[7]; f++) {
					if (antiRepeat(borrowedItem[results[f][1]])) {
						GUI.out(borrowedItem[results[f][1]]);
					}
				}
				GUI.out("");
			}
			if (ziptie) {
				GUI.out("There's also a whole bunch in Trinity's hair.");
			}
		}
	}
	*/
	public static String input() {
		/* [IO] [Text] [Input] [011] */
		String query = "";
		query = GUI.in();
		if (query == "") {
			System.out.print(query);
		}
		return query;
	}

	public static void register() {
		boolean b = false;
		while (true && !b) {
			GUI.out("Type the word next to begin.");
			String next = "";
			while (next.equals("")) {
				next = input();
			}
			if (next.equals("next")) {
				break;
			}
			if (next.equals("done") || next.equals("cancel")
					|| next.equals("nevermind")) {
				b = true;
			}
		}
		while (true && !b) {
			GUI.out("Hello. What is your name?");
			String name = "";
			while (name.equals("")) {
				name = input();
			}
			if (name.equals("done") || name.equals("cancel")
					|| name.equals("nevermind")) {
				b = true;
			}
			String email = "";
			if (!b) {
				GUI.out("Please enter your email.");
			}
			while (true && !b) {
				email = "";
				while (email.equals("")) {
					email = input();
				}
				if (email.equals("done") || email.equals("cancel")
						|| email.equals("nevermind")) {
					b = true;
					break;
				} else if (!email.contains("@") || !email.contains(".")) {
					GUI.out("Please enter a valid email.");
				} else {
					break;
				}
			}
			String grade = "";
			if (!b) {
				GUI.out("What grade are you in?");
			}
			while (grade.equals("") && !b) {
				grade = input();
			}
			if (grade.equals("done") || grade.equals("cancel")
					|| grade.equals("nevermind")) {
				b = true;
			}
			if (!b) {
				GUI.out("Welcome to Programming, " + name + ".");
				registerWrite(name);
				registerWrite(email);
				registerWrite(grade);
				registerFile[registerFilePointer] = name;
				registerFilePointer++;
				registerFile[registerFilePointer] = email;
				registerFilePointer++;
				registerFile[registerFilePointer] = grade;
				registerFilePointer++;
				String command = "";
				while (command.equals("")) {
					command = input();
				}
				if (command.equals("done") || command.equals("finished")) {
					break;
				} else if (command.equals("next")) {
					GUI.out("Next.");
				}
			}
		}
		GUI.out("Disengaging Register Mode.");
	}

	public static void printRegister() {
		for (int f = 0; f < registerFilePointer; f += 3) {
			GUI.text("Name: " + registerFile[f]);
			GUI.text("Email: " + registerFile[f + 1]);
			if (registerFile[f + 2] != null) {
				if (registerFile[f + 2].equalsIgnoreCase("freshman")) {
					registerFile[f + 2] = "9";
				} else if (registerFile[f + 2].equalsIgnoreCase("sophomore")) {
					registerFile[f + 2] = "10";
				} else if (registerFile[f + 2].equalsIgnoreCase("junior")) {
					registerFile[f + 2] = "11";
				} else if (registerFile[f + 2].equalsIgnoreCase("senior")) {
					registerFile[f + 2] = "12";
				}
			}
			GUI.text("Grade: " + registerFile[f + 2]);
		}
	}

	/*
	public static void search() {
		if (debugMode) {
			GUI.text("Search called.");
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
	*/
	public static void recursiveSearch()
	{
		if( debugMode )
		{
			GUI.text("RecursiveSearch called.");
		}
		for( int f=0; f<locations+1; f++ )
		{
			found[f] = 0;
		}
		for( int f=0; f<locations; f++ )
		{
			if( debugMode )
			{
				GUI.text("Searching in location " + locationNames[f] + ".");
			}
			found[f] = 0;
			for( int g=0; g<masterInventoryPointers[f]; g++ )
			{
				boolean tf = false;
				int adjust = g;
				if( debugMode )
				{
					GUI.text("Testing against " + masterInventory[f][g][0]);
				}
				for( int h=0; h<keywordPointer; h++ )
				{
					for( int i=0; i<=masterInventoryDescriptionsPointer[f][g]; i++  )
					{
						if (keywords[h].toLowerCase().contains(masterInventory[f][g][i].toLowerCase())
								|| masterInventory[f][g][i].toLowerCase().contains(
										keywords[h].toLowerCase())) {
							if( debugMode )
							{
								GUI.text("Match found: " + masterInventory[f][g][i]);
								if( i > 0 )
								{
									adjust = g-1;
								}
								GUI.text("Location: " + locationNames[f] + ":" + adjust);
							}
							if( i > 0 )
							{
								adjust = g-1;
							}
							tf = true;
						}
					}
				}
				if(tf)
				{
					found[f]++;
					resultPointer++;
					results[resultPointer - 1][0] = f;
					results[resultPointer - 1][1] = adjust;
				}
			}
		}
		checkBorrowedR();
	}
	public static void recursiveExactSearch(String term) {
		if( debugMode )
		{
			GUI.text("RecursiveExactSearch called.");
		}
		for( int f=0; f<locations+1; f++ )
		{
			found[f] = 0;
		}
		for( int f=0; f<locations; f++ )
		{
			for( int g=0; g<masterInventoryPointers[f]; g++ )
			{
				for( int h=0; h<=masterInventoryDescriptionsPointer[f][g]; h++ )
				{
					if( term.toLowerCase().equals(masterInventory[f][g][h].toLowerCase()) )
					{
						found[f]++;
						resultPointer++;
						results[resultPointer-1][0] = f;
						results[resultPointer-1][1] = g;
					}
				}
			}
		}
	}
	public static void checkBorrowedR() {
		found[locations] = 0;
		for (int f = 0; f < borrowedPointer; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(
						borrowedItem[f].toLowerCase())
						|| borrowedItem[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					found[locations]++;
					resultPointer++;
					results[resultPointer - 1][0] = locations;
					results[resultPointer - 1][1] = f;
				} else if ((keywords[g].toLowerCase() + "s")
						.contains(borrowedItem[f].toLowerCase())
						|| (borrowedItem[f] + "s").contains(keywords[g]
								.toLowerCase())) {
					found[locations]++;
					resultPointer++;
					results[resultPointer - 1][0] = locations;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactBorrowedR(String term) {
		found[locations] = 0;
		for (int f = 0; f < borrowedPointer; f++) {
			if (term.toLowerCase().equals(borrowedItem[f].toLowerCase())) {
				found[locations]++;
				resultPointer++;
				results[resultPointer - 1][0] = locations;
				results[resultPointer - 1][1] = f;
			}
		}
	}
	/*
	public static void exactSearch(String term) {
		if (debugMode) {
			GUI.text("ExactSearch called with term " + term + ".");
		}
		exactToolBox(term);
		exactToteA(term);
		exactToteB(term);
		exactToteC(term);
		exactToteD(term);
		exactToteE(term);
		exactCrate(term);
		exactBorrowed(term);
	}
	*/
	public static boolean antiRepeat(String check) {
		/* [Support] [Text] [Pointer] [013] */
		if (debugMode) {
			GUI.out("AntiRepeat called with input '" + check + "'.");
		}
		boolean b = true;
		for (int f = 0; f < printedPointer; f++) {
			if (check.equals(printed[f])) {
				b = false;
			}
		}
		if (b) {
			printed[printedPointer - 1] = check;
			printedPointer++;
		}
		return b;
	}
	/*
	public static void checkToolBox() {
		p[0] = 0;
		for (int f = 0; f < 41; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TLDesc[f].toLowerCase())
						|| TLDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[0]++;
					resultPointer++;
					results[resultPointer - 1][0] = 0;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToolBox(String term) {
		p[0] = 0;
		for (int f = 0; f < 41; f++) {
			if (term.toLowerCase().equals(ToolBox[f][0].toLowerCase())) {
				p[0]++;
				resultPointer++;
				results[resultPointer - 1][0] = 0;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkToteA() {
		p[1] = 0;
		for (int f = 0; f < 20; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TADesc[f].toLowerCase())
						|| TADesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[1]++;
					resultPointer++;
					results[resultPointer - 1][0] = 1;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToteA(String term) {
		p[1] = 0;
		for (int f = 0; f < 20; f++) {
			if (term.toLowerCase().equals(ToteA[f][0].toLowerCase())) {
				p[1]++;
				resultPointer++;
				results[resultPointer - 1][0] = 1;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkToteB() {
		p[2] = 0;
		for (int f = 0; f < 11; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TBDesc[f].toLowerCase())
						|| TBDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[2]++;
					resultPointer++;
					results[resultPointer - 1][0] = 2;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToteB(String term) {
		p[2] = 0;
		for (int f = 0; f < 11; f++) {
			if (term.toLowerCase().equals(ToteB[f][0].toLowerCase())) {
				p[2]++;
				resultPointer++;
				results[resultPointer - 1][0] = 2;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkToteC() {
		p[3] = 0;
		for (int f = 0; f < 18; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TCDesc[f].toLowerCase())
						|| TCDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[3]++;
					resultPointer++;
					results[resultPointer - 1][0] = 3;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToteC(String term) {
		p[3] = 0;
		for (int f = 0; f < 18; f++) {
			if (term.toLowerCase().equals(ToteC[f][0].toLowerCase())) {
				p[3]++;
				resultPointer++;
				results[resultPointer - 1][0] = 3;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkToteD() {
		p[4] = 0;
		for (int f = 0; f < 25; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TDDesc[f].toLowerCase())
						|| TDDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[4]++;
					resultPointer++;
					results[resultPointer - 1][0] = 4;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToteD(String term) {
		p[4] = 0;
		for (int f = 0; f < 20; f++) {
			if (term.toLowerCase().equals(ToteD[f][0].toLowerCase())) {
				p[4]++;
				resultPointer++;
				results[resultPointer - 1][0] = 4;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkToteE() {
		p[5] = 0;
		for (int f = 0; f < 8; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(TEDesc[f].toLowerCase())
						|| TEDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[5]++;
					resultPointer++;
					results[resultPointer - 1][0] = 5;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactToteE(String term) {
		p[5] = 0;
		for (int f = 0; f < 8; f++) {
			if (term.toLowerCase().equals(ToteE[f][0].toLowerCase())) {
				p[5]++;
				resultPointer++;
				results[resultPointer - 1][0] = 5;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkCrate() {
		p[6] = 0;
		for (int f = 0; f < 16; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(CDesc[f].toLowerCase())
						|| CDesc[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[6]++;
					resultPointer++;
					results[resultPointer - 1][0] = 6;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactCrate(String term) {
		p[6] = 0;
		for (int f = 0; f < 16; f++) {
			if (term.toLowerCase().equals(Crate[f][0].toLowerCase())) {
				p[6]++;
				resultPointer++;
				results[resultPointer - 1][0] = 6;
				results[resultPointer - 1][1] = f;
			}
		}
	}

	public static void checkBorrowed() {
		p[7] = 0;
		for (int f = 0; f < borrowedPointer; f++) {
			for (int g = 0; g < keywordPointer; g++) {
				if (keywords[g].toLowerCase().contains(
						borrowedItem[f].toLowerCase())
						|| borrowedItem[f].toLowerCase().contains(
								keywords[g].toLowerCase())) {
					p[7]++;
					resultPointer++;
					results[resultPointer - 1][0] = 7;
					results[resultPointer - 1][1] = f;
				} else if ((keywords[g].toLowerCase() + "s")
						.contains(borrowedItem[f].toLowerCase())
						|| (borrowedItem[f] + "s").contains(keywords[g]
								.toLowerCase())) {
					p[7]++;
					resultPointer++;
					results[resultPointer - 1][0] = 7;
					results[resultPointer - 1][1] = f;
				}
			}
		}
	}

	public static void exactBorrowed(String term) {
		p[7] = 0;
		for (int f = 0; f < borrowedPointer; f++) {
			if (term.toLowerCase().equals(borrowedItem[f].toLowerCase())) {
				p[7]++;
				resultPointer++;
				results[resultPointer - 1][0] = 7;
				results[resultPointer - 1][1] = f;
			}
		}
	}
	*/
	public static void borrow(String inout) {
		/* [Organizer] [Borrow] [IO] [021] */
		if (debugMode) {
			GUI.out("Borrow called with input + '" + inout + "'.");
		}
		boolean check = false;
		boolean repeat = true;
		boolean in = false;
		boolean out = false;
		if (inout.equals("in")) {
			in = true;
		}
		if (inout.equals("out")) {
			out = true;
		}
		if (inout.equals("null")) {
			check = true;
			GUI.out("Do you want to record what we borrowed, or what was borrowed from us?");
		}
		String IO = "";
		String Item = "";
		String Team = "";
		boolean none = false;
		if (check) {
			while (repeat) {
				String input = "";
				while (input.equals("")) {
					input = input();
				}
				String data = input.toLowerCase();
				while (data.endsWith(" ")) {
					data = data.substring(0, data.length() - 1);
				}
				if (data.endsWith(".") || data.endsWith("?")
						|| data.endsWith("!")) {
					data = data.substring(0, data.length() - 1);
				}
				while (data.endsWith(" ")) {
					data = data.substring(0, data.length() - 1);
				}
				data = data + " ";
				if ((data.contains("we ") && data.contains("borrow"))
						|| (data.contains(" us ") && data.contains("lent"))) {
					in = true;
				}
				if ((data.contains("we ") && data.contains("lent"))
						|| (data.contains(" us ") && data.contains("borrow"))) {
					out = true;
				} else {
					none = true;
				}
				repeat = false;
				if (in && out) {
					GUI.out("You're going to have to pick one or the other.");
					repeat = true;
				}
			}
		}
		boolean go = false;
		boolean xcase = false;
		String xteam = "";
		if (in) {
			IO = "B";
			GUI.out("What item did we borrow?");
			while (Item.equals("")) {
				Item = input();
			}
			GUI.out("Which team did we borrow from?");
			while (Team.equals("")) {
				Team = input();
			}
			go = true;
		}
		if (out) {
			IO = "L";
			GUI.out("What item did we lend?");
			while (Item.equals("")) {
				Item = input();
			}
			Item = Item.toLowerCase();
			repeat = true;
			GUI.out("Which team did we lend to?");
			while (Team.equals("")) {
				Team = input();
			}
			if (debugMode) {
				GUI.out("Checking item '" + Item + "'.");
			}
			int[] loc = checkBorrow(Item);
			boolean prev = false;
			for (int f = 0; f < lentPointer; f++) {
				if (lentItem[f].equals(Item)) {
					prev = true;
					xteam = lentTeam[f];
					break;
				}
			}
			int total = 0;
			for( int f=0; f<locations; f++ )
			{
				total = total + loc[f];
			}
			if (total != 0 && !prev) {
				go = true;
			} else if (prev) {
				xcase = true;
			}
		}
		if (xcase) {
			GUI.out("The '" + Item + "' has already been lent to " + xteam
					+ ".");
		}
		if (go) {
			toBorrow(IO + "~" + Item + "~" + Team + "~");
			loadBorrow();
		} else if (!go && !none && !xcase) {
			GUI.out("I dont think we have '" + Item + "'.");
		} else if (!go && none && !xcase) {
			GUI.out("Okay.");
		}
	}

	public static void loadBorrow() {
		/* [Startup] [Borrow] [Read] [IO] [022] */
		if (debugMode) {
			GUI.out("LoadBorrow called.");
		}
		notesPointer = 0;
		String fileName = "borrow.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				String item = "";
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line.toLowerCase().equals("b")) {
					boolean good = true;
					line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try {
						line = bufferedReader.readLine();
						tempTeam = line;
					} catch (Exception e) {
						GUI.text("Warning: Syntax error!");
						good = false;
						break;
					}
					if (good) {
						borrowedItem[borrowedPointer] = tempString;
						borrowedTeam[borrowedPointer] = tempTeam;
						borrowedPointer++;
					}
				} else if (line.toLowerCase().equals("l")) {
					boolean good = true;
					line = bufferedReader.readLine();
					if( debugMode )
					{
						GUI.text("We have lent " + line + ".");
					}
					String tempString = line;
					String tempTeam = "0";
					try {
						line = bufferedReader.readLine();
						tempTeam = line;
					} catch (Exception e) {
						GUI.out("Warning: Syntax error!");
						good = false;
						break;
					}
					if (good) {
						GUI.text("It was lent to " + tempTeam + ".");
						item = tempString.toLowerCase();
						GUI.text("Parsing item as " + item + ".");
						int loc[] = checkBorrow(item);
						int total = 0;
						for( int f=0; f<locations; f++ )
						{
							total = total+loc[f];
						}
						if (total != 0) {
							for( int f=0; f<locations; f++ )
							{
								if( loc[f] != 0 )
								{
									if( debugMode )
									{
										GUI.text("The item was in " + locationNames[f] + ".");
										GUI.text("The index was " + (loc[f]-1) + ".");
									}
									lentLoc[lentPointer][0] = f;
									lentLoc[lentPointer][1] = loc[f]-1;
									masterInventoryBorrow[f][loc[f]-1] = tempTeam;
								}
							}
							lentItem[lentPointer] = tempString;
							lentTeam[lentPointer] = tempTeam;
							lentPointer++;
						} else {
							GUI.out("I don't think we have '" + item + "'.");
							GUI.out("You may want to check the borrow file, or reset it.");
							break;
						}
					}
				} else if (line.toLowerCase().equals("n")) {
					String note = bufferedReader.readLine();
					notes[notesPointer] = note;
					notesPointer++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			GUI.text("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			GUI.text("Error reading file '" + fileName + "'");
		}
	}

	public static void loadPref() {
		/* [Startup] [Borrow] [Read] [IO] [022] */
		if (debugMode) {
			GUI.text("LoadPref called.");
		}
		String fileName = "preferences.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line.equals("started")) {
					firstStartup = false;
				} else if (line.equals("programName")) {
					line = bufferedReader.readLine();
					programName = line;
				} else if (line.equals("userName")) {
					line = bufferedReader.readLine();
					if (!line.equals(null)) {
						un = true;
						userName = line;
					}
				} else if (line.equals("programColor")) {
					line = bufferedReader.readLine();
					if (checkColor(line)) {
						programColor = colorConvert(line);
					} else if (!checkColor(line)) {
						programColor = backupProgramColor;
					}
				} else if (line.equals("userColor")) {
					line = bufferedReader.readLine();
					if (checkColor(line)) {
						userColor = colorConvert(line);
					} else if (!checkColor(line)) {
						userColor = backupUserColor;
					}
				} else if (line.equals("language")) {
					line = bufferedReader.readLine();
					language = line;
				} else if (line.equals("sarcasm")) {
					line = bufferedReader.readLine();
					sarcasm = Integer.parseInt(line);
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			GUI.text("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			GUI.text("Error reading file '" + fileName + "'");
		}
		GUI.prefChange(programName, userName, programColor, userColor);
		parseName(programName);
	}

	public static void loadRegister() {
		/* [Startup] [Register] [Read] [IO] [022] */
		if (debugMode) {
			GUI.text("LoadRegister called.");
		}
		String fileName = "register.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else {
					registerFile[registerFilePointer] = line;
					registerFilePointer++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			GUI.text("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			GUI.text("Error reading file '" + fileName + "'");
		}
	}

	public static void borrowWrite(String string) {
		/* [IO] [Borrow] [023] */
		if (debugMode) {
			GUI.text("BorrowWrite called with input '" + string + "'.");
		}
		try {
			FileWriter fstream = new FileWriter("borrow.txt", true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if (string != null) {
				fbw.write(string);
				fbw.newLine();
				fbw.close();
			}
		} catch (Exception e) {
			GUI.text("Couldn't print to the file.");
		}

	}

	public static void takeNote(String string) {
		if (debugMode) {
			GUI.text("TakeNote called with input '" + string + "'.");
		}
		boolean cont = true;
		while (cont) {
			int c = 0;
			int firstInstance = 0;
			int secondInstance = 0;
			for (int f = 0; f < string.length(); f++) {
				if (string.charAt(f) == '"' || string.charAt(f) == '\'') {
					if (c == 0) {
						firstInstance = f;
					} else {
						secondInstance = f;
					}
					c++;
				}
			}
			if (c == 2) {
				String sub = string
						.substring(firstInstance + 1, secondInstance);
				borrowWrite("n");
				borrowWrite(sub);
				loadBorrow();
				cont = false;
			} else if (c != 2) {
				GUI.out("Please put the note in quotations.");
				string = "";
				while (string.equals("")) {
					string = input();
				}
				if (string.equals("done") || string.equals("cancel")
						|| string.equals("nevermind")) {
					cont = false;
				}
			}
		}
	}

	public static void registerWrite(String string) {
		/* [IO] [Register] [023] */
		if (debugMode) {
			GUI.text("RegisterWrite called with input '" + string + "'.");
		}
		try {
			FileWriter fstream = new FileWriter("register.txt", true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if (string != null) {
				fbw.write(string);
				fbw.newLine();
				fbw.close();
			}
		} catch (Exception e) {
			GUI.text("Couldn't print to the file.");
		}

	}

	public static void prefWrite(String string) {
		/* [IO] [Preferences] [069] */
		if (debugMode) {
			GUI.text("PrefWrite called with input '" + string + "'.");
		}
		try {
			FileWriter fstream = new FileWriter("preferences.txt", true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if (string != null) {
				fbw.write(string);
				fbw.newLine();
				fbw.close();
			}
		} catch (Exception e) {
			GUI.text("Couldn't print to the file.");
		}
	}

	public static void toBorrow(String writer) {
		/* [Organizer] [Borrow] [IO] [024] */
		if (debugMode) {
			GUI.text("ToBorrow called with input '" + writer + "'.");
		}
		int end = writer.indexOf("~");
		String BL = writer.substring(0, end);
		String temp = writer.substring(end + 1, writer.length());
		end = temp.indexOf("~");
		String item = temp.substring(0, end);
		temp = temp.substring(end + 1, temp.length());
		end = temp.indexOf("~");
		String team = temp.substring(0, end);
		temp = temp.substring(end + 1, temp.length());
		borrowWrite(BL);
		borrowWrite(item);
		borrowWrite(team);
	}

	public static boolean checkColor(String color) {
		/* [Pref] [Color] [070] */
		if (debugMode) {
			GUI.text("CheckColor called with input '" + color + "'.");
		}
		boolean valid = false;
		if (color.equals("black") || color.equals("blue")
				|| color.equals("cyan") || color.equals("dark_gray")
				|| color.equals("gray") || color.equals("green")
				|| color.equals("light_gray") || color.equals("magenta")
				|| color.equals("orange") || color.equals("pink")
				|| color.equals("red") || color.equals("white")
				|| color.equals("yellow")) {
			valid = true;
		}
		return valid;
	}

	public static Color colorConvert(String color) {
		/* [Pref] [Color] */
		if (debugMode) {
			GUI.text("ColorConvert called with input '" + color + "'.");
		}
		Color retColor = Color.BLACK;
		if (color.equals("black")) {
			retColor = Color.BLACK;
		}
		if (color.equals("blue")) {
			retColor = Color.BLUE;
		}
		if (color.equals("cyan")) {
			retColor = Color.CYAN;
		}
		if (color.equals("dark_gray")) {
			retColor = Color.DARK_GRAY;
		}
		if (color.equals("gray")) {
			retColor = Color.GRAY;
		}
		if (color.equals("green")) {
			retColor = Color.GREEN;
		}
		if (color.equals("light_gray")) {
			retColor = Color.LIGHT_GRAY;
		}
		if (color.equals("magenta")) {
			retColor = Color.MAGENTA;
		}
		if (color.equals("orange")) {
			retColor = Color.ORANGE;
		}
		if (color.equals("pink")) {
			retColor = Color.PINK;
		}
		if (color.equals("red")) {
			retColor = Color.RED;
		}
		if (color.equals("white")) {
			retColor = Color.WHITE;
		}
		if (color.equals("yellow")) {
			retColor = Color.YELLOW;
		}
		return retColor;
	}

	public static void restoreBorrow() {
		/* [Cleanup] [Borrow] [Memory] [027] */
		if (debugMode) {
			GUI.text("RestoreBorrow called.");
		}
		clearBorrow();
		borrowWrite("// adminRestart = false");
		borrowWrite("// This is the file where the borrowed items are stored.");
		borrowWrite("// Line 1: B/L (Borrowed/Lent)");
		borrowWrite("// Line 2: Item Name (Exact)");
		borrowWrite("// Line 3: Team No.");
		for (int f = 0; f < borrowFilePointer + 1; f++) {
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
		masterInventoryBorrow = masterInventoryBB;
		loadBorrow();
	}

	public static void restorePref() {
		clearPref();
		for (int f = 0; f < prefFilePointer; f++) {
			prefWrite(prefFile[prefFilePointer]);
		}
		programName = backupProgramName;
		userName = backupUserName;
		programColor = backupProgramColor;
		userColor = backupUserColor;
		prefFilePointer = 0;
		loadPref();
	}

	public static void listBorrow() {
		/* [Borrow] [Text] [Print] [Info] [028] */
		if (debugMode) {
			GUI.text("ListBorrow called.");
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
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("//")) {
					String ignore = line;
				} else if (line.toLowerCase().equals("b")) {
					data = true;
					boolean good = true;
					line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try {
						line = bufferedReader.readLine();
						tempTeam = line;
					} catch (Exception e) {
						GUI.text("Warning: Syntax error!");
						good = false;
						break;
					}
					if (good) {
						piece1 = "borrowed '";
						piece2 = tempString;
						piece3 = "' from ";
						piece4 = tempTeam;
						GUI.out(piece0 + piece1 + piece2 + piece3 + piece4
								+ piece5);
					}
				} else if (line.toLowerCase().equals("l")) {
					data = true;
					boolean good = true;
					line = bufferedReader.readLine();
					String tempString = line;
					String tempTeam = "0";
					try {
						line = bufferedReader.readLine();
						tempTeam = line;
					} catch (Exception e) {
						GUI.text("Warning: Syntax error!");
						good = false;
						break;
					}
					if (good) {
						piece1 = "lent '";
						piece2 = tempString;
						piece3 = "' to ";
						piece4 = tempTeam;
						GUI.out(piece0 + piece1 + piece2 + piece3 + piece4
								+ piece5);
					}
				}
			}
			if (!data) {
				GUI.out("Nothing is in the borrow file.");
			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			GUI.text("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			GUI.text("Error reading file '" + fileName + "'");
		}
	}

	public static void printNotes() {
		for (int f = 0; f < notesPointer; f++) {
			GUI.out(notes[f]);
		}
	}

	public static void nameChange(String who, String input) {
		/* [Pref] [GUI] [72] */
		String name = "";
		boolean w = false;
		if (debugMode) {
			GUI.text("NameChange called with input " + who + ", " + input + ".");
		}
		if (who.equals("my")) {
			name = userName;
			w = true;
		} else if (who.equals("your")) {
			name = programName;
			w = true;
		} else if (who.equals("null")) {
			GUI.out("Sure, but whose name?");
			w = false;
		}
		boolean q = false;
		int qc = 0;
		char first = ' ';
		char last = ' ';
		for (int f = 0; f < input.length(); f++) {
			if (input.charAt(f) == ('\'') || input.charAt(f) == ('\"')) {
				if (first == '\'' || first == '\"') {
					last = input.charAt(f);
				} else {
					first = input.charAt(f);
				}
				qc++;
			}
		}
		if (qc == 2) {
			q = true;
		} else {
			GUI.out("Could you say that again, except with just the name in quotes?");
		}
		if (w && q) {
			int start = input.indexOf(first);
			input = input.substring(start + 1, input.length());
			int end = input.indexOf(last);
			name = input.substring(0, end);
			if (who.equals("my")) {
				prefWrite("userName");
				prefWrite(name);
				userName = name;
				loadPref();
				GUI.out("Changed your name to '" + name + "'.");
			} else if (who.equals("your")) {
				prefWrite("programName");
				prefWrite(name);
				programName = name;
				loadPref();
				GUI.out("Changed my name to '" + name + "'.");
			}
		}
	}

	public static void colorChange(String who, String input) {
		/* [Color] [Pref] [GUI] [71] */
		if (debugMode) {
			GUI.text("ColorChange called with input " + who + ", " + input
					+ ".");
		}
		Color color = Color.BLACK;
		String cw = "";
		String cc = "";
		boolean w = false;
		boolean vc = true;
		if (who.equals("my")) {
			color = userColor;
			w = true;
			cw = "userColor";
		} else if (who.equals("your")) {
			color = programColor;
			w = true;
			cw = "programColor";
		} else if (who.equals("null")) {
			GUI.out("Sure, just tell me which color you want changed.");
			w = false;
		}
		if (w) {
			if (input.contains("black")) {
				color = Color.BLACK;
				cc = "black";
			} else if (input.contains("blue")) {
				color = Color.BLUE;
				cc = "blue";
			} else if (input.contains("cyan")) {
				color = Color.CYAN;
				cc = "cyan";
			} else if (input.contains("dark gray")) {
				color = Color.DARK_GRAY;
				cc = "dark_gray";
			} else if (input.contains("gray")) {
				color = Color.GRAY;
				cc = "gray";
			} else if (input.contains("green")) {
				color = Color.GREEN;
				cc = "green";
			} else if (input.contains("light gray")) {
				color = Color.LIGHT_GRAY;
				cc = "light_gray";
			} else if (input.contains("magenta")) {
				color = Color.MAGENTA;
				cc = "magenta";
			} else if (input.contains("orange")) {
				color = Color.ORANGE;
				cc = "orange";
			} else if (input.contains("pink")) {
				color = Color.PINK;
				cc = "pink";
			} else if (input.contains("red")) {
				color = Color.RED;
				cc = "red";
			} else if (input.contains("white")) {
				color = Color.WHITE;
				cc = "white";
			} else if (input.contains("yellow")) {
				color = Color.YELLOW;
				cc = "yellow";
			} else {
				GUI.out("I can only use the following colors:");
				GUI.out("Black, blue, cyan, dark gray, green, light gray, magenta, pink, red, white, and yellow.");
				vc = false;
			}
			if (vc) {
				prefWrite(cw);
				if (who.equals("my")) {
					userColor = color;
					prefWrite(cc);
					loadPref();
					GUI.out("Your color has been changed successfully to " + cc
							+ ".");
				} else if (who.equals("your")) {
					programColor = color;
					prefWrite(cc);
					loadPref();
					GUI.out("My color has been changed successfully to " + cc
							+ ".");
				}
			}
		}
	}

	public static void loadLibrary() {
		/*loadToolBox();
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
		loadCDesc();*/
		loadExclusion();
		loadPeople();
	}

	/*
	public static void loadToolBox() {
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
		for (int f = 0; f < 41; f++) {
			ToolBox[f][1] = "0";
		}
	}

	public static void loadToteA() {
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
		for (int f = 0; f < 20; f++) {
			ToteA[f][1] = "0";
		}
	}

	public static void loadToteB() {
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
		for (int f = 0; f < 11; f++) {
			ToteB[f][1] = "0";
		}
	}

	public static void loadToteC() {
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
		for (int f = 0; f < 18; f++) {
			ToteC[f][1] = "0";
		}
	}

	public static void loadToteD() {
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
		for (int f = 0; f < 25; f++) {
			ToteD[f][1] = "0";
		}
	}

	public static void loadToteE() {
		ToteE[0][0] = "Chain Box";
		ToteE[1][0] = "Plates Stuff Box";
		ToteE[2][0] = "Clear Screw Box";
		ToteE[3][0] = "Mantis hardware bag";
		ToteE[4][0] = "Crappy Drill Bit Set";
		ToteE[5][0] = "Electrical Stuff box";
		ToteE[6][0] = "Anderson Box";
		ToteE[7][0] = "Rag Bag";
		for (int f = 0; f < 8; f++) {
			ToteE[f][1] = "0";
		}
	}

	public static void loadCrate() {
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
		for (int f = 0; f < 16; f++) {
			Crate[f][1] = "0";
		}
	}
	 */
	public static void loadExclusion() {
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

	public static void loadPeople() {
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
	
	/*
	public static void loadTLDesc() {
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

	public static void loadTADesc() {
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
		TADesc[19] = "Drills + chargers";
	}

	public static void loadTBDesc() {
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

	public static void loadTCDesc() {
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

	public static void loadTDDesc() {
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

	public static void loadTEDesc() {
		TEDesc[0] = "Chain Boxes";
		TEDesc[1] = "Plates Stuff Boxes";
		TEDesc[2] = "Clear Screw Boxes";
		TEDesc[3] = "Mantis hardware bags";
		TEDesc[4] = "Crappy Drills Bits Sets";
		TEDesc[5] = "Electrical Stuff boxes";
		TEDesc[6] = "Anderson Boxes";
		TEDesc[7] = "Rags Bags";
	}

	public static void loadCDesc() {
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
	*/
	public static int[] checkBorrow(String item)
	{
		int[] loc = new int[locations];
		for( int f=0; f<locations-1; f++ )
		{
			for( int g=0; g<masterInventoryPointers[f]; g++ )
			{
				if( item.equals(masterInventory[f][g][0]) )
				{
					loc[f] = g+1;
					break;
				}
			}
		}
		return loc;
	}
	/*
	public static int ToolBoxCB(String item) {
		int loc = 0;
		for (int f = 0; f < 41; f++) {
			if (item.equals(ToolBox[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int ToteACB(String item) {
		int loc = 0;
		for (int f = 0; f < 20; f++) {
			if (item.equals(ToteA[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int ToteBCB(String item) {
		int loc = 0;
		for (int f = 0; f < 11; f++) {
			if (item.equals(ToteB[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int ToteCCB(String item) {
		int loc = 0;
		for (int f = 0; f < 18; f++) {
			if (item.equals(ToteC[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int ToteDCB(String item) {
		int loc = 0;
		for (int f = 0; f < 25; f++) {
			if (item.equals(ToteD[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int ToteECB(String item) {
		int loc = 0;
		for (int f = 0; f < 8; f++) {
			if (item.equals(ToteE[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}

	public static int CrateCB(String item) {
		int loc = 0;
		for (int f = 0; f < 16; f++) {
			if (item.equals(Crate[f][0].toLowerCase())) {
				loc = f + 1;
				break;
			}
		}
		return loc;
	}
	*/
	/*
	public static void listToolBox() {
		GUI.out("The Toolbox contains the following:");
		for (int f = 0; f < 41; f++) {
			if (ToolBox[f][1] == "0") {
				GUI.text(ToolBox[f][0]);
			}
			if (ToolBox[f][1] != "0") {
				GUI.text("The " + ToolBox[f][0] + " was lent to team "
						+ ToolBox[f][1] + ".");
			}
		}
		GUI.out("");
	}

	public static void listToteA() {
		GUI.out("Tote A contains the following:");
		for (int f = 0; f < 20; f++) {
			if (ToteA[f][1] == "0") {
				GUI.text(ToteA[f][0]);
			}
			if (ToteA[f][1] != "0") {
				GUI.text("* The " + ToteA[f][0] + " was lent to team "
						+ ToteA[f][1] + ".");
			}
		}
		GUI.text("");
	}

	public static void listToteB() {
		GUI.out("Tote B contains the following:");
		for (int f = 0; f < 11; f++) {
			if (ToteB[f][1] == "0") {
				GUI.text(ToteB[f][0]);
			}
			if (ToteB[f][1] != "0") {
				GUI.text("* The " + ToteB[f][0] + " was lent to team "
						+ ToteB[f][1] + ".");
			}
		}
		GUI.text("");
	}

	public static void listToteC() {
		GUI.out("Tote C contains the following:");
		for (int f = 0; f < 18; f++) {
			if (ToteC[f][1] == "0") {
				GUI.text(ToteC[f][0]);
			}
			if (ToteC[f][1] != "0") {
				GUI.text("* The " + ToteC[f][0] + " was lent to team "
						+ ToteC[f][1] + ".");
			}
		}
		GUI.text("");
	}

	public static void listToteD() {
		GUI.out("Tote D contains the following:");
		for (int f = 0; f < 25; f++) {
			if (ToteD[f][1] == "0") {
				GUI.text(ToteD[f][0]);
			}
			if (ToteD[f][1] != "0") {
				GUI.text("* The " + ToteD[f][0] + " was lent to team "
						+ ToteD[f][1] + ".");
			}
		}
		GUI.text("");
	}

	public static void listToteE() {
		GUI.out("Tote E contains the following:");
		for (int f = 0; f < 8; f++) {
			if (ToteE[f][1] == "0") {
				GUI.text(ToteE[f][0]);
			}
			if (ToteE[f][1] != "0") {
				GUI.text("* The " + ToteE[f][0] + " was lent to team "
						+ ToteE[f][1] + ".");
			}
		}
		GUI.text("");
	}

	public static void listCrate() {
		GUI.out("The Crate contains the following:");
		for (int f = 0; f < 16; f++) {
			if (Crate[f][1] == "0") {
				GUI.text(Crate[f][0]);
			}
			if (Crate[f][1] != "0") {
				GUI.text("* The " + Crate[f][0] + " was lent to team "
						+ Crate[f][1] + ".");
			}
		}
		GUI.text("");
	}
	*/
	public static void shutDown() throws InterruptedException {
		/* [Cleanup] [Terminate] [059] */
		if (debugMode) {
			GUI.text("ShutDown called.");
		}
		typeWriter("Goodbye");
		on = false;
	}

	public static void parseName(String name) {
		/* [Data] [Text] [Process] [073] */
		nickNamePointer = 0;
		for (int f = 0; f < nickNamePointer; f++) {
			nickName[f] = null;
		}
		if (debugMode) {
			GUI.text("ParseName called with input '" + name + "'.");
		}
		String data = name.toLowerCase();
		while (data.endsWith(" ")) {
			data = data.substring(0, data.length() - 1);
		}
		if (data.endsWith(".") || data.endsWith("?") || data.endsWith("!")) {
			data = data.substring(0, data.length() - 1);
		}
		while (data.endsWith(" ")) {
			data = data.substring(0, data.length() - 1);
		}
		data = data + " ";
		while (data.contains(" ")) {
			int end = data.indexOf(" ");
			String nick = data.substring(0, end);
			String temp = data.substring(end + 1, data.length());
			if (debugMode) {
				GUI.out(nick);
			}
			nickName[nickNamePointer] = nick;
			nickNamePointer++;
			data = temp;
		}
	}
}
