import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class BrowserPane extends JFrame {

	//bookmark and history array/lists are initialised 
	static ArrayList<String> history = new ArrayList<String>();
	static ArrayList<String> bookmarks = new ArrayList<String>();
	static String[] historyArray;
	static String[] bookmarksArray;

	//swing components are initialised 
	protected static JEditorPane display = new JEditorPane();				//display shows the HTML code / websites
	protected static JScrollPane scrollBar = new JScrollPane(display);			//adds a scrollPane to display
	protected static JComboBox<String> addressBar = new JComboBox<String>();		//a JComboBox is used as the addressBar
	protected static JComboBox<String> bookmarkBar = new JComboBox<String>();		//bookmarks are displayed within a JComboBox	
	protected static JTextField hpBar = new JTextField();
	protected static JFrame more = new JFrame();						//new frame is created to hold additional features

	//Strings to accept uni-codes are initialised
	public static String REFRESH = "\u21bb";
	public static String BACK = "\u25c0";
	public static String FORWARD = "\u25b6";
	public static String MORE = "\u22EF";
	public static String GO = "\u21B5";
	public static String HOME = "\u2302";
	//homepage string is initialised 
	public static String hp;

	//JButtons are created, with the corresponding uni-code strings attached 
	protected static JButton btnGo = new JButton(GO);
	protected static JButton btnHome = new JButton(HOME);
	protected static JButton btnBack = new JButton(BACK);
	protected static JButton btnForward = new JButton(FORWARD);
	protected static JButton btnRefresh = new JButton(REFRESH);
	protected static JButton btnMore = new JButton(MORE);
	
	//more Jbuttons are initialised for the more features frame
	protected static JButton btnViewHis = new JButton("View / Edit");
	protected static JButton btnDelHis = new JButton("Delete");
	protected static JButton btnViewBook = new JButton("View / Edit");
	protected static JButton btnAddBook = new JButton("Add");
	protected static JButton btnSetHP = new JButton("Set");

	public BrowserPane() throws Exception {
		super();
	}

	public void frameHandler() throws Exception {					//all fram settings are held in this method

		setTitle("Conquistador");						//title is set			
		setSize(1024, 608);							//frame size is set
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//close function is set
		setVisible(true);							//frame is made visible
		setResizable(false);							//frame is not resizable
		setLayout(null);							//frame layout is set to null
		setLocationRelativeTo(null);						//when the browser is launched it shows in 
																				//the centre of the users screen
		
		ToolBar.addComponentsToFrame(getContentPane());				//components are called from the tool bar class
																				//and added to the frame
		
		addWindowListener(new WindowAdapter() {					//a new action listener is created for actions carried out
																				//when the browser is exited
			public void windowClosing(WindowEvent e) {	
				Browser.updateHistory();				//the history update method is called
				e.getWindow().dispose();
			}
		});
	}

	public static void moreHandler() throws Exception {					//this method handles the settings for the more features frame

		more.setTitle("More");								//title is set
		more.setSize(390, 350);								//size is set
		more.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				//close action is set, does not exit out of entire browser
		more.setVisible(true);								//more is made visible 
		more.setResizable(false);							//more frame is not resizable
		more.setLayout(null);								//layout set to null
		more.setLocationRelativeTo(null);						//more features frame opens in centre of the screen
		ToolBar.addComponentsToMoreFrame(more);						//more features components are added
	}

	static void actionListenerCalls() throws Exception {					//method stores main frames action listeners

		addressBar.addActionListener(new ActionListener() {				//allows the user to go to specified URL
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {

				String newSelection = (String) addressBar.getSelectedItem();	//represents text entry to the combobox as a new String
				if (history.contains(newSelection)) {				//if history already contains the String, it will not be doubled
					history.remove(newSelection);
				}
				history.add(newSelection);					//users desired URL is added to the history
				addressBar.setModel(new DefaultComboBoxModel(			//remodels the JComboBox, converting the history list to an array
						history.toArray()));
				addressBar.setSelectedItem(newSelection);			//sets the addressBar to state the desired website
				loadData(newSelection);						//loads the desired website
			}
		});

		display.addHyperlinkListener(new HyperlinkListener() {				//adds a hyperlink listener to the display
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {	
					String newSelection = e.getURL().toString();		//represent the hyperlink as a new String
					
					if (history.contains(newSelection)) {			//if history already contains the String, it will not be doubled
						history.remove(newSelection);
					}
					history.add(newSelection);				//adds the hyperlink to website history
					addressBar.setModel(new DefaultComboBoxModel(history	//remodels the JComboBox with the new website added
							.toArray()));
					addressBar.setSelectedItem(newSelection);		//sets the addressBar to state the new web address
					loadData(newSelection);					//hyperlink web address is then loaded
				}
			}
		});

		btnHome.addActionListener(new ActionListener() {				//loads the user determined homepage
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {				//if history already contains the website, it will not be doubled
				if (history.contains(hp)) {										
					history.remove(hp);
				}
				history.add(hp);						//homepage added to history
				addressBar.setModel(new DefaultComboBoxModel(			// remodels the JComboBox
						history.toArray()));
				addressBar.setSelectedItem(hp);					//homepage has been loaded from file and set as string hp,
												//addressBar is set to display homepage
				loadData(hp);							//homepage is loaded
			}
		});

		btnGo.addActionListener(new ActionListener() {					//sets the go button to load text entered in the addressBar
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {

				String newSelection = (String) addressBar.getSelectedItem();	//represent text entry from addressBar as a new String
				if (history.contains(newSelection)) {				//if history already contains the String, it will not be doubled
					history.remove(newSelection);
				}
				history.add(newSelection);					//adds text entry to history
				addressBar.setModel(new DefaultComboBoxModel(			//remodels the JComboBox with the new text entry
						history.toArray()));
				addressBar.setSelectedItem(newSelection);			//sets the addressBar to state the text entry
												//this line isn't always necessary, but makes sure the user 
												//entry is kept in the addressBar
				
				loadData(newSelection);						//loads the text entry
			}
		});

		btnRefresh.addActionListener(new ActionListener() {				//reloads a website
			public void actionPerformed(ActionEvent e) {		
				loadData(history.get(history.size() -1));			//fetches last history entry and loads it 
			}
		});

		btnBack.addActionListener(new ActionListener() {				//allows user to cycle backwards through their history
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {
				try {
					String newSelection = history.get(history.size() - 2);	//gets previous website and sets it to a String
					loadData(newSelection);					//string is then loaded 
					String s = history.get(history.size() - 1);		//gets starting website and sets it to String s
					history.add(0, s);					//String s is added to history with an array position of 0
					history.remove(history.size() - 1);			//starting website is removed, as it is now in position 0
					addressBar.setModel(new DefaultComboBoxModel(history	//addressBar is remodelled to show this change 
							.toArray()));
					addressBar.setSelectedItem(newSelection);		//previous website is displayed in addressBar
				} catch (Exception a) {
					return;							//try/catch statement used incase there is no previous history
												//to load, in which cased nothing happens
				}
			}
		});

		btnForward.addActionListener(new ActionListener() {				//allows user to cycle forward through their history
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {			
				try {
					String newSelection = history.get(0);			//as the back button sets first website to position 0
												//the forward button fetches it and sets it as a string
					loadData(newSelection);					//website is then loaded
					String s = history.get(0);				//string at position 0 is set to string s
					history.add(s);						//string s is added to history
					history.remove(0);					//position 0 removed as it is now at the bottom of the list
					addressBar.setModel(new DefaultComboBoxModel(history	//remodels the combobox
							.toArray()));
					addressBar.setSelectedItem(newSelection);		//sets addressBar to display forwarded site
				} catch (Exception a) {
					return;							//if there is nothing to forward to then no actions take place
				}
			}
		});

		btnMore.addActionListener(new ActionListener() {				//opens the more fetures fram
			public void actionPerformed(ActionEvent e) {
				try {
					moreHandler();						//more features frame handler is called
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	static void moreActionListenerCalls() throws Exception {						//method contains acctionListeners for the componets of the more frame

		btnDelHis.addActionListener(new ActionListener() {						//deletes all saved history
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null, "Are you sure?",			//JOptionPane asks the user is they want to delete the history
						"Delete History", JOptionPane.YES_NO_OPTION) 
						== JOptionPane.YES_OPTION) {
					try {
						String currentSite = (String) addressBar			//a record of the current site is held in a string
								.getSelectedItem();
						BufferedWriter writer = new BufferedWriter(			//writer is given a path to the History file
								new FileWriter(
										"C:\\Conquistador Browser\\History.txt",
										false));

						writer.write("");						//the text file is overwritten to be blank
						writer.flush();
						writer.close();
						history.removeAll(history);					//all active history is removed
						addressBar.setSelectedItem(currentSite);			//the record of the current site is the added back to the addressBar
						addressBar.setModel(new DefaultComboBoxModel(history    	//addressBar is remodelled
								.toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(display,				//if history.txt is not found, user is alerted
								"History could not be deleted");
					}
				} else {
					return;									//if user clicks no, no action is carried out
				}
			}
		});

		btnViewHis.addActionListener(new ActionListener() {						//launches Hitory.txt for the user to view/edit
			public void actionPerformed(ActionEvent e) {

				Runtime rt = Runtime.getRuntime();						//creates a new runtime event
				String file = "C:\\Conquistador Browser\\History.txt";				//path to Histor.txt is set
				try {
					rt.exec("notepad " + file);						//if found the file is launched with notepad
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(more,
							"Could not find History.txt in directory: " + file);	//if not found, user is alerted
					e1.printStackTrace();
				}
			}
		});

		btnViewBook.addActionListener(new ActionListener() {						//launches Bookmarks.txt for the user to view/edit
			public void actionPerformed(ActionEvent e) {
				Runtime rt = Runtime.getRuntime();						//a new runtime event is created
				String file = "C:\\Conquistador Browser\\Bookmarks.txt";			//path to Bookmarks.txt is given
				try {
					rt.exec("notepad " + file);						//bookmarks.txt is launched using notepad
														//a space is required after notepad
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(display,
							"Could not find Bookmarks.txt in directory: " 
									+ file);				//if Bookmarks.txt is not found, user is alerted
					e1.printStackTrace();
				} 
			}
		});

		btnSetHP.addActionListener(new ActionListener() {						//allows user to set a desired homepage
			public void actionPerformed(ActionEvent e) {
				hp = hpBar.getText();								//string hp is set to equal a user entry in designated textfield
				if (hp.isEmpty()) {								//if the entry is blank then the user is alerted
					JOptionPane.showMessageDialog(more,
							"Please enter a valid URL");
					return;
				}
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(		//a path is set to Home.txt
							"C:\\Conquistador Browser\\Home.txt", false));
					writer.write(hp);							//string hp is written to Home.txt for future use
					writer.newLine();
					writer.flush();
					writer.close();
					JOptionPane
							.showMessageDialog(more, "Homepage updated!");		//user is informed that the change was successful
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(more,					//user is alerted if change is not successful
							"Homepage could not be updated");
				}
			}
		});

		hpBar.addActionListener(new ActionListener() {							//this action listener is exactly the same as btnSetHp action listner
														//it adds the same functionality to pressing enter while 
														//typing in the text field hpBar
			public void actionPerformed(ActionEvent e) {
				hp = hpBar.getText();
				if (hp.isEmpty()) {
					JOptionPane.showMessageDialog(more,
							"Please enter a valid URL");
					return;
				}
				try {
					System.out
							.println("Attempting To Save Array Contents To File...");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							"C:\\Conquistador Browser\\Home.txt", false));
					writer.write(hp);
					writer.newLine();
					writer.flush();
					writer.close();
					JOptionPane
							.showMessageDialog(more, "Homepage updated!");
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(more,
							"Homepage could not be updated");
				}
			}
		});

		btnAddBook.addActionListener(new ActionListener() {						//allows user to add bookmarks
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				String newSelection = (String) addressBar.getSelectedItem();			//represents text entry as a new String, newSelection
				if (bookmarks.contains(newSelection)) {						//if history already contains the String, it will not be doubled
					bookmarks.remove(newSelection);
				}
				bookmarks.add(newSelection);							//newSelection is added to bookmarks list
				bookmarkBar.setModel(new DefaultComboBoxModel(bookmarks				//remodels the JComboBox, with newSelection added
						.toArray()));
				bookmarkBar.setSelectedItem(newSelection);					//sets bookmarkBar to display newSelection
				Browser.updateBookmarks();							//calls Browser.updateBookmarks() method to rewrite the new bookmark
														//to Bookmarks.txt
			}
		});

		bookmarkBar.addActionListener(new ActionListener() {						//allows user to load selected bookmark
			public void actionPerformed(ActionEvent e) {
				String newSelection = (String) bookmarkBar.getSelectedItem();			//represent bookmark selection entry as a new String
				addressBar.setSelectedItem(newSelection);					//addressBar is set to the bookmark selection
				loadData(newSelection);								//bookmark selection is loaded
			}
		});

	}

	protected static void loadData(String text) {								//loads html to display pane
		try {
				display.setPage(text);								//display will be set to the corresponding wedsite
														//determined by the String text 
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(display, "Oops, URL not fround");				//if the website is not found, the user is informed
			return;
		}
	}

}
