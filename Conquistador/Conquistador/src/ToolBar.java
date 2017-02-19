import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToolBar extends Browser {


	public ToolBar() throws Exception {
		super();
	}

	static void addComponentsToFrame(Container pane) throws Exception {  		//method is used to hold information on how the 
																				//JEditorPane should be structured
		//all components off the tool bar are added
		pane.add(addressBar);
		pane.add(scrollBar);
		pane.add(btnGo);
		pane.add(btnHome);
		pane.add(btnRefresh);
		pane.add(btnBack);
		pane.add(btnForward);
		pane.add(btnMore);

		//Components are given their placement of the pane as well as their size
		addressBar.setBounds(161, 11, 630, 27);
		scrollBar.setBounds(1, 50, 1016, 530);

		// buttons
		//Components are given their placement of the pane as well as their size
		btnBack.setBounds(11, 12, 47, 25);
		btnRefresh.setBounds(59, 12, 47, 25);
		btnForward.setBounds(107, 12, 47, 25);
		btnGo.setBounds(797, 12, 67, 25);
		btnHome.setBounds(865, 12, 67, 25);
		btnMore.setBounds(933, 12, 67, 25);

		//the text for each button can be changed, this allows for a clearer display of text to suit the button
		btnBack.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		btnRefresh.setFont(new Font("TimesRoman", Font.BOLD, 12));
		btnForward.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		btnMore.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		btnGo.setFont(new Font("TimesRoman", Font.BOLD, 20));
		btnHome.setFont(new Font("TimesRoman", Font.BOLD, 20));

		BrowserPane.actionListenerCalls();										//actionListeners method is called and added
		BrowserPane.moreActionListenerCalls();									//moreActionListeners method is called and added
		toolTipText();															//buttons are given their own labels
	}

	static void addComponentsToMoreFrame(Container more)
			throws Exception {
		//method contains components and placements/size for the more features frame
		JLabel Label1 = new JLabel("Set a new Home Page:");
		JLabel Label2 = new JLabel("History:");
		JLabel Label3 = new JLabel("Bookmarks:");
		JPanel Panel1 = new JPanel();
		JPanel Panel2 = new JPanel();

		more.add(Label1);
		more.add(Label2);
		more.add(Label3);
		more.add(Panel1);
		more.add(Panel2);
		more.add(hpBar);
		more.add(btnSetHP);
		more.add(btnViewHis);
		more.add(btnDelHis);
		more.add(bookmarkBar);
		more.add(btnViewBook);
		more.add(btnAddBook);

		// Home Page
		Label1.setBounds(7, 9, 300, 26);
		hpBar.setBounds(7, 35, 300, 27);
		btnSetHP.setBounds(308, 35, 70, 26);
		Panel1.setBounds(0, 90, 400, 1);
		Panel1.setBackground(Color.gray);

		// History
		Label2.setBounds(7, 100, 300, 26);
		btnViewHis.setBounds(70, 125, 125, 36);
		btnDelHis.setBounds(205, 125, 125, 36);
		Panel2.setBounds(0, 189, 400, 1);
		Panel2.setBackground(Color.gray);

		// Favourites
		Label3.setBounds(7, 199, 300, 26);
		bookmarkBar.setBounds(7, 225, 369, 26);
		btnViewBook.setBounds(70, 265, 125, 36);
		btnAddBook.setBounds(205, 265, 125, 36);
	}
	
	private static void toolTipText() {											
		//this method allows for additional text to be displayed if the user hovers the cursor over a button
		btnHome.setToolTipText("Home");
		btnRefresh.setToolTipText("Refresh");
		btnGo.setToolTipText("Go!");
		btnBack.setToolTipText("Back");
		btnForward.setToolTipText("Forward");
		btnMore.setToolTipText("Additional features");
		btnAddBook.setToolTipText("Adds the current website to bookmarks");
		hpBar.setToolTipText("Enter a valid URL to set as a homepage");
	}

}
