import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.ArrayUtils;

@SuppressWarnings("serial")
public class Browser extends BrowserPane {


	public static void main(String args[]) throws Exception {

		Browser.createFolder(); 								//upon first launch, the directory is created
		Browser.createFile("History"); 								//text files to save the browser info are then 
		Browser.createFile("Bookmarks");							//created in the directory
		Browser.createFile("Home");
		Browser file = new Browser();								//the browser constructor is called 
		file.frameHandler();									//the framehandler is then added to the constructed Browser
		loadHomepage();										//homepage is then loaded ready for the user
	}

	public Browser() throws Exception {
																				
		historyArray = history.toArray(new String[history.size()]);				// ArrayList must be converted to an Array for JComboBox
		bookmarksArray = bookmarks.toArray(new String[bookmarks.size()]);			//ArrayList is used initially as you can add and remover components
		display.setEditable(false);								//without this the HTML code is visible
		addressBar.setEditable(true);								//allows the user to enter a desired URL
		displayBookmarks();									//fetches the users saved bookmarks and adds them to the designated comboBox
	}

	public static void createFolder() {
		String dirPath = "C:\\Conquistador Browser";						//a path for where the directory is to be created
		File folder = new File(dirPath);							//the directory is initialised and given the path 
		try {
			if (folder.exists()) {								//if the folder already exists, another will not be created
				return;
			} else {
				folder.mkdir();								//if the folder is n
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(display,
					"Could not create directory at: " + folder.getPath());		//user will be alerted if the directory cannot be created
		}
	}

	public static void createFile(String s) {							//this method allows me to create specified .txt files 
		File tmp = new File("C:\\Conquistador Browser", s + ".txt");				//File tmp is given a path and accepts string s as the file name
													//+ .txt to make it a text file
		try {
			if (tmp.exists()) {
				return;									//if the file is already there, no action will be taken
			} else {
				tmp.createNewFile();							//else the file will be created
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(display,
					"Could not create file at: " + s +".txt");			//user will be alerted if the file cannot be created
		}
	}

	public static void loadHomepage() {
		String tempHP = null;									//a string to save the read file .toString()
		java.util.List<String> stringList = null;						//a list to save the read file to
		Path homePath = new File("C:\\Conquistador Browser\\Home.txt")				//path is set the the Home.txt file
				.toPath();
		try {											// try/catch statement to read History.txt and save it to arrayList
			stringList = Files.readAllLines(homePath);					//Home.txt is read and saved to a list
			tempHP = stringList.toString();							//list is then converted to a string
			
			if (tempHP.contains("[]")) {							//if the list is empty the default homepage is set
				setDefaultHP();								//setDefultHP() method is called
				
			} else{										//else the pre-existing homepage is set
			hp = tempHP.substring(1, tempHP.length() - 1);					//a substring is made to remove the square brackets
			hpBar.setText(hp);								//a text field is set to show the user what their current homepage is
			addressBar.setSelectedItem(hp);							//the addressBar is set the the homepage
			loadData(hp);									//homepage is loaded
			}
		} catch (IOException e) {
				e.printStackTrace();
			JOptionPane.showMessageDialog(display,						//user is alerted if the homepage cannot be found
					"Homepage could not be found");
		}
	}

	static void updateHistory() {

		Path historyPath = new File("C:\\Conquistador Browser\\History.txt")			// a path is written to History.txt
				.toPath();
		java.util.List<String> stringList = null;						// a new list is initialised, to store pre-existing history
	
		try {											// try/catch statement to read History.txt and save it to an arrayList
			stringList = Files.readAllLines(historyPath);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(display,						//user is alerted if history could not be updated
					"History could not be updated");
		}
		String[] currentHistory = stringList.toArray(new String[stringList			// arrayList is converted to a simple array
				.size()]);
		String[] allHistory = (String[]) ArrayUtils.addAll(currentHistory,			// the extracted history is then added to the running history
				history.toArray(new String[history.size()]));
	
		try {											// a try catch statement for writing the new combined history to History.txt
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"C:\\Conquistador Browser\\History.txt", false));
			for (int i = 0; i < allHistory.length; i++) {
				String x = allHistory[i];
				writer.write(x);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(display,						//user is alerted if history could not be updated
					"History could not be updated");
		}
	}

	static void updateBookmarks() {												
		String[] allBookmarks = bookmarks.toArray(new String[					//bookmarks list is converted to a string array
		        bookmarks.size()]);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(			//writer is given the path to Bookmarks.txt
					"C:\\Conquistador Browser\\Bookmarks.txt", false));
			for (int i = 0; i < allBookmarks.length; i++) {					//for loop to write all values to Bookmarks.txt
				String x = allBookmarks[i];
				writer.write(x);
				writer.newLine();
			}
			writer.flush();
			writer.close();
			JOptionPane.showMessageDialog(more, "Bookmark added");				//user is alerted that their bookmark has been added
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(more,
					"Bookmarks could not be updated");				//user is alerted if their bookmark is not added
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	static void displayBookmarks() {								//method loads saved bookmarks to display in designated JComboBox

		Path path = new File("C:\\Conquistador Browser\\Bookmarks.txt")				// a path is written to Bookmarks.txt	
				.toPath();
		java.util.List<String> stringList = null;						// a new list is initialised, to store pre-existing Bookmarks
		try {											// try/catch statement to read Bookmarks.txt and save it to an arrayList
			stringList = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(display,
					"Bookmarks could not be updated");
		}
		// arrayList is converted to a simple array
		String[] currentBookmarks = stringList.toArray(new String[				//list is converted into simple array
		        stringList.size()]);

		for (int i = 0; i < currentBookmarks.length; i++) {					//the simple array is then added back into the live bookmarks list
			bookmarks.add(currentBookmarks[i]);
		}
		bookmarkBar.setModel(new DefaultComboBoxModel(bookmarks.toArray()));			//live list is then modelled to the JComboBox
	}
	
	static void setDefaultHP(){ 									//method sets default homepage upon first launch
		hp = "http://google.com";								//hp is given a string
		hpBar.setText(hp);									//the string is then set in the homepage textfield
		addressBar.setSelectedItem(hp);								//addressBar is set to display the homepage
		loadData(hp);										//homepage is loaded
		
		try {											//try/catch to write the homepage to Home.txt
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"C:\\Conquistador Browser\\Home.txt", false));
			writer.write(hp);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(more,
					"Homepage could not be updated");				//used is alerted if homepage cannot be set
		}
	}
}
