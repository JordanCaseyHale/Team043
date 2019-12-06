package assignment;

import java.awt.*;
import javax.swing.*;

public class MainHandler extends JFrame {
	
	public static void main(String[] args) 
	{  
		MainPanel introPanel = new MainPanel();
		//EditorTasksPanel introPanel = new EditorTasksPanel("TestEditor@aol.com");
		//AuthorTasksPanel introPanel = new AuthorTasksPanel();
		//ReviewerTasksPanel introPanel = new ReviewerTasksPanel("");
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("JournalManager");
		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(introPanel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		introPanel.addListeners(mainFrame);
		mainFrame.pack();
		mainFrame.setVisible(true);
	} 
	
	//protected
	
	public static void logout(JFrame parent) {
		parent.getContentPane().removeAll();
		MainPanel nextPanel = new MainPanel();
		nextPanel.addListeners(parent);
		parent.getContentPane().add(nextPanel);
		parent.revalidate(); 
		parent.repaint();
	}
}
