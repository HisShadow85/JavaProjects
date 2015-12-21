package ai;

import common.Synchro;

public class Minimax {
	//Minimax with Alpha-beta Pruning
	synchronized public static byte minimax(byte player, byte alpha, byte beta,Synchro syn) { 
		
		switch (syn.getCurrStateNoUpdate(player < 0 ? 'O' : 'X')) {
		case playerOneWon:
			return -100;
		case playerTwoWon:
			return 100;
		case tie:
			return 0;
		default:
		}
		byte score;
		for (int i = 0; i < 9; i++) {
			if (syn.getBoardSymbol(i) == '\0') {
				syn.setBoardSymbol(i, player < 0 ? 'X' : 'O');
				score = minimax((byte) -player, alpha, beta,syn);
				if (player > 0) {// is cpu
					if (score > alpha)
						alpha = score;
				} else {// is human
					if (score < beta)
						beta = score;
				}
                
				syn.setBoardSymbol(i, '\0');
				if (alpha >= beta){
					break; // beta cut-off
				}
								
			}
		}
		return player > 0 ? alpha : beta;
	}
}
