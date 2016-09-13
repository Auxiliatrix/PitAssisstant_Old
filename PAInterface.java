import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PAInterface extends JPanel implements ActionListener {
	protected JFrame frame;
	protected JTextField searchBar;
    protected static JTextPane console;
    protected JScrollPane scroll;
    protected static String input = "";
    public static Color programColor = Color.BLACK;
    public static Color userColor = Color.BLUE;
    public static String programName = "";
    public static String userName = "";
    protected Style style;

    public PAInterface() {
        super(new GridBagLayout());
		voce.SpeechInterface.init("../../../lib", true, false, "", "");
        console = new JTextPane();
        console.setFont(new java.awt.Font("Courier New",Font.BOLD,18));
        console.setEditable(false);
        style = console.addStyle("Style",null);
        StyleConstants.setForeground(style, programColor);
        scroll = new JScrollPane(console);
        
        searchBar = new JTextField(45);
        searchBar.setFont(new java.awt.Font("Calibri",Font.BOLD, 25));
        searchBar.addActionListener(this);
 
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(searchBar, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scroll, c);
    }
	protected void load(String program, String user, Color pac, Color uc)
	{
        frame = new JFrame(program);
        programName = program;
        userName = user;
        programColor = pac;
        userColor = uc;
    	StyleConstants.setForeground(style, programColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PAInterface());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(1150,700);
        frame.getContentPane().setBackground(Color.WHITE);
        Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("604logo.bmp"));
		}catch (IOException e) {
			System.out.println("Logo picture not found!");
		}
        frame.setIconImage(img);
    }
	protected void prefChange(String program, String user, Color pac, Color uc)
	{
		programName = program;
		userName = user;
		programColor = pac;
		userColor = uc;
    	StyleConstants.setForeground(style, programColor);
	}
	public String in()
	{
		if(input == "")
		{
			return "";
		}
		else
		{
			String tempString = input;
			input = "";
			return tempString;
		}
	}
	protected void out(String output)
	{
		String mode = PitAssistant.MODE;
		if( mode.equals("text") )
		{
			text(output);
		}
		else if( mode.equals("voice") )
		{
			voice(output);
		}
	}
	protected void text(String output)
	{
		Document doc = console.getDocument();
		try {
			doc.insertString(doc.getLength(), output+"\n", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		console.setCaretPosition(console.getDocument().getLength());
	}
	protected void voice(String output)
	{
		Document doc = console.getDocument();
		try {
			doc.insertString(doc.getLength(), output+"\n", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		console.setCaretPosition(console.getDocument().getLength());
		voce.SpeechInterface.synthesize(output);
	}
	protected void textNoLine(String output)
	{
		Document doc = console.getDocument();
		try {
			doc.insertString(doc.getLength(), output, style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		console.setCaretPosition(console.getDocument().getLength());
	}
	@Override
    public void actionPerformed(ActionEvent event) {
    	String getValue = searchBar.getText();
    	StyleConstants.setForeground(style, userColor);
    	text(getValue);
    	StyleConstants.setForeground(style, programColor);
	    input = getValue;
	    searchBar.setText("");
    }
	protected void flush()
	{
		console.setText("");
		text("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-[" + programName + "]-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		text("");
		text("  Hi, I'm Pit Assistant (v4.2). I can look for things, and tell you what's in our totes and boxes.");
		text("Pit Assisstant (v4.2) Theoretically(TM) supports description-based queries and all sentence structures.");
		text("         Pit Assistant (v4.2) Theoretically(TM) keeps track of borrowed items from a file.");
		text("       Pit Assistant (v4.2) also Theoretically(TM) supports and keeps track of user preferences.");
		text("");
		text("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v4.2)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		text("");
	}
	protected void command(String command)
	{
		if( command.equals("end") )
		{
			voce.SpeechInterface.destroy();
			frame.dispose();
		}
	}
}
