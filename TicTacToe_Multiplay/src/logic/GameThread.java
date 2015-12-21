package logic;

import javax.swing.JTextArea;

import common.Synchro;
import view.Board;

public abstract class GameThread extends Thread {
	Synchro syn;
	Board board;
	JTextArea messages;
	protected byte counter;
	
	protected volatile  boolean threadSuspended ;
	
	public GameThread(Synchro syn,Board board,JTextArea messages){
		this.board = board;
		this.syn = syn;
		this.messages = messages;
	}
	

	public void terminate(){
		this.threadSuspended = true;
	}
}
