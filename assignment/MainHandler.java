package assignment;

import java.awt.*;
import javax.swing.*;

public class MainHandler extends JFrame {
	
	public static void main(String[] args) 
	{  
		MainPanel introPanel = new MainPanel();
		//EditorTasksPanel introPanel = new EditorTasksPanel();
		//AuthorTasksPanel introPanel = new AuthorTasksPanel();
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("JournalManager");
		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(introPanel, BorderLayout.CENTER);
		introPanel.addListeners(mainFrame);
		mainFrame.pack();
		mainFrame.setVisible(true);
	} 
	
	//protected
	
	public MainHandler(){
		
	}
}
