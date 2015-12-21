package logic;
import common.Synchro;
import view.TicTacToeGUI;

public class Init {

	public static void main(String[] args) {
		Synchro syn = new Synchro();
		new TicTacToeGUI(syn);
	}

}
