package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import common.Synchro;


public class Board extends JPanel  {
	private static final long serialVersionUID = 1L;
    private XOButton buttons[] = new XOButton[9];
	public Board(Synchro syn,JTextArea messages) {
		
		setFocusable(true);
		requestFocus();
		setBackground(Color.WHITE);
		this.setLayout(new GridLayout(3,3));

		for (int i = 0; i < 9; i++) {
			buttons[i] = new XOButton(i, syn,messages);
			this.add(buttons[i]);
		}
	}
	
	public void setButton(int button,char ch){
		buttons[button].setTheIcon(ch);
	}
}	