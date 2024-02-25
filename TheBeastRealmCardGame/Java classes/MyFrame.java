

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	MyPanel myPanel=new MyPanel();
	
	
	MyFrame(){
		
		//myPanel.setBounds(0, 0, myPanel.getWidth(), myPanel.getHeight());
		this.add(myPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
		
	}
	
	
}
