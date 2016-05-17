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

public class PAInterface extends JPanel implements ActionListener {
	protected JTextField searchBar;
    protected static JTextArea console;
    protected JScrollPane scroll;
    protected static String input = "";

    public PAInterface() {
        super(new GridBagLayout());
 
        console = new JTextArea(5, 20);
        console.setFont(new java.awt.Font("Courier New",Font.BOLD,18));
        console.setEditable(false);
        console.setLineWrap(true);
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
	protected void load(String name) {
        JFrame frame = new JFrame(name);
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
		console.append(output + "\n");
		console.setCaretPosition(console.getDocument().getLength());
	}
	@Override
    public void actionPerformed(ActionEvent event) {
    	String getValue = searchBar.getText();
	    input = getValue;
	    searchBar.setText("");
    }
	protected void flush()
	{
		console.setText("");
		out("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-[Pit Assistant]-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		out("");
		out("  Hi, I'm Pit Assistant (v3.2). I can look for things, and tell you what's in our totes and boxes.");
		out("Pit Assisstant (v3.2) Theoretically(TM) supports description-based queries and all sentence structures.");
		out("         Pit Assistant (v3.2) Theoretically(TM) keeps track of borrowed items from a file.");
		out("");
		out("  =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v3.2)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		out("");
	}
}
