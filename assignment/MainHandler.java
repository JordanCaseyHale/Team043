package assignment;

import java.awt.*;
import javax.swing.*;

public class MainHandler {

	public static void main(String[] args) 
	{  
		//MainPanel introPanel = new MainPanel();
		WriterPanel introPanel = new WriterPanel();
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("JournalManager");
		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(introPanel, BorderLayout.CENTER);
		introPanel.addListeners(mainFrame);
		mainFrame.pack();
		mainFrame.setVisible(true);
	} 

}
