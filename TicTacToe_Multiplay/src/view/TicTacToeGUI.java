package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import common.Synchro;

public class TicTacToeGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 506;
	private final int HEIGHT = 600;
    
	public JPanel menu;
	public JPanel board;
	JTextArea messages;
	
	public TicTacToeGUI(Synchro syn) {		      
		this.setTitle("Tic-Tac-Toe");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setFont(new Font("Verdana", Font.BOLD, 32));
		messages.setSize(506, 50);
		board = new Board(syn,messages);
		menu = new Menu(syn,(Board) board,messages);
		
		container.add(menu,BorderLayout.NORTH);
		container.add(board,BorderLayout.CENTER);
		container.add(messages, BorderLayout.SOUTH);
		this.setResizable(true);
		this.setVisible(true);
	}	
}
