package Interface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ShowRule extends JFrame{//not use
	private String thisAccount;
	ShowRule(String account){
		super(account);
		setSize(400,300);
		
		JTextArea myJTextArea = new JTextArea();
		JScrollPane myJScollPane = new JScrollPane(myJTextArea);
		
		add(myJScollPane);
		
		JButton back = new JButton("back");
	}
}
