import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class BotGUI extends JFrame{
	
	private JPanel panel;
	protected JTextField textBox;
	private JButton button;
	protected String currentSearch = "";
	protected JTextArea resultLabel;
	private JButton clearButton;
	
	public BotGUI(){
		super("HannahBot");
		
		this.setup();
	    this.createTextBox();
	    this.createButton();
	    this.createClearButton();
	    this.setResultLabel();
	    
	    setVisible(true);
	}
	
	public void setResultLabel(){
		this.resultLabel = new JTextArea();
		this.resultLabel.setEditable(false);
		getContentPane().add(this.resultLabel);

	}
	
	public void setup(){
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);        
        getContentPane().setBackground(Color.WHITE);
        Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("LogoWhite.bmp"));
		}catch (IOException e) {
			System.out.println("Corner Button Image Does Not Exist");
		}
        
        setIconImage(img);
        
	    setLayout(new FlowLayout());
	}
	
	public void createTextBox(){
		this.textBox = new JTextField(30);
		this.textBox.setHorizontalAlignment(JTextField.CENTER);
		this.textBox.setFont(new java.awt.Font("Arial",Font.BOLD, 25));
		this.textBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		this.textBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	String getValue = textBox.getText();
			    currentSearch = getValue;
			    textBox.setText("");
		    }
		});
	    add(this.textBox);
	}
	
	public void createButton(){
		this.button = new JButton();
		try {
			Image img = ImageIO.read(getClass().getResource("Search.bmp"));
			this.button.setIcon(new ImageIcon(img));
		}catch (IOException e) {
			System.out.println("Search Button Image Does Not Exist");
		}
		this.button.setBorder(null);
		  
		this.button.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
			      String getValue = textBox.getText();
			      currentSearch = getValue;
			      textBox.setText("");
			   }
			});

		add(this.button);
	}
	
	public void createClearButton(){
		this.clearButton = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("Clear.bmp"));
			this.clearButton.setIcon(new ImageIcon(img));
		}catch (IOException e) {
			System.out.println("Clear Button Image Does Not Exist");
		}
		
		this.clearButton.setBorder(null);
		
		this.clearButton.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
			      resultLabel.setText("");
			      displayResults("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=[Hannah Bot]=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				  displayResults("");
				  displayResults("   Hi, I'm HannahBot (v1.8). I can look for things, and tell you what's in our totes and boxes.");
				  displayResults("Hannah Bot (v1.8) Theoretically(TM) supports description-based queries and all sentence structures.");
				  displayResults("");
				  displayResults("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=(v1.8)=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				  displayResults("");
			   }
			});

		add(this.clearButton);
	}
	
	public String getSearch(){
		if(this.currentSearch == ""){
			return "";
		}else{
			String retString = this.currentSearch;
			this.currentSearch = "";
			return retString;
		}
	}	
	
	protected void displayResults(String results){
		this.resultLabel.append("\n" + results);
	}
	

}
