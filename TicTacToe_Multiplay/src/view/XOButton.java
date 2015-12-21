package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

import common.Synchro;

public class XOButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	ImageIcon X, O;
	Synchro syn;
	byte id = 0;
	JTextArea messages;
	

	public XOButton(int id, Synchro syn,JTextArea messages) {
		this.syn = syn;
		this.id = (byte) id;
		this.messages = messages;
		X = new ImageIcon(this.getClass().getResource("/redX.png"));
		O = new ImageIcon(this.getClass().getResource("/blueCircle.png"));
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (syn.isGame() && syn.getBoardSymbol(id) == '\0') {
			if (syn.isHotSeat()) {
				if (syn.isPlayerOneTurn()) {
					setIcon(X);
					syn.setBoardSymbol(id, 'X');
					syn.updateState('X');
					messages.setText("Player Two turn");
				} else {
					setIcon(O);
					syn.setBoardSymbol(id, 'O');
					syn.updateState('O');
					messages.setText("Player One turn");
				}
				
				switch (syn.getGameWinStatus()) {
				case playerOneWon:
					//System.out.println("Player One Wins");
					messages.setText("Player One Wins");
					syn.setHotSeat(false);
					syn.setGame(false);
					break;
				case playerTwoWon:
					//System.out.println("Player Two wins");
					messages.setText("Player Two Wins");
					syn.setHotSeat(false);
					syn.setGame(false);
					break;
				case tie:
					//System.out.println("Game Tie");
					messages.setText("Game Tie");
					syn.setHotSeat(false);
					syn.setGame(false);
					break;
				default:
					syn.switchPlayers();
					break;
				}
							
			} else if (syn.isPlayerOneTurn()) {
				setIcon(X);
				syn.setBoardSymbol(id, 'X');
				//syn.updateState('X');
				syn.setPlayerOneTurn(false);
			}else{
				return;
			}					
		}
	}

	public void setTheIcon(char ch) {
		switch (ch) {
		case 0:
			setIcon(null);
			break;
		case 'X':
			setIcon(X);
			break;
		case 'O':
			setIcon(O);
			break;
		default:
			return;
		}
	}

}