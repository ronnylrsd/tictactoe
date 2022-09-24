package jogadores;

import java.util.LinkedList;

public class Avenger extends Jogador{
	
	private int tamanhoIdealTabuleiro = 3;
    private int vazio = -1;

    public Avenger(String nome) {
        super(nome);
        
    }
    
    @Override
    public int[] jogar(int[][] board) {
        int[] jogada = new int [1];
        return jogada;
    }



    private int minMax(int[][] tabuleiro, int tamanho, boolean turno) {
        int pontuacao = validaMovimento(tabuleiro);

        // TO DO check more sizes of the boad to limit the depth of the search
        // Terminal case to the recursion

        // start
        // limits the recursion by number of runs
        if (tabuleiro.length <= tamanhoIdealTabuleiro) {
            // limit the recursion to 8 times
            if (tamanho >= 8) {
                return pontuacao;
            }
        } else {
            // limit the recursion to idealBoardSize times
            if (tamanho >= 3) {
                return pontuacao;
            }
        }

        // terminal cases with win or lose condition(end of the game)
        if (pontuacao == -1 || pontuacao == 1) {
            return pontuacao;
        }
        // end

        // check if there still moves avalible before try another move
        if (!jogadaLivre(tabuleiro)) {
            return 0;
        }

        // check turn
        //
        // my turn
        if (turno) {
            int melhorJogada = -1;
            

            for (int i = 0; i < tabuleiro.length; i++) {
                for (int j = 0; j < tabuleiro.length; j++) {
                    // check if position is empty
                    if (tabuleiro[i][j] == -1) {
                        // play on tha position to check the result
                        tabuleiro[i][j] = this.getSimbolo();

                        // try to find the best value to MAX player (me) and change the turn
                        melhorJogada = Math.max(melhorJogada, minMax(tabuleiro, tamanho + 1, !turno));

                        // undo last move to preserv the board
                        tabuleiro[i][j] = vazio;
                    }
                }
            }
            return melhorJogada;
        }

        // opposite Turn
        else {
            int melhorJogada = 1;
            

            for (int line = 0; line < tabuleiro.length; line++) {
                for (int colunm = 0; colunm < tabuleiro.length; colunm++) {

                    if (tabuleiro[line][colunm] == -1) { // check if position is empty
                        // play on tha position to check the result
                        tabuleiro[line][colunm] = simboloOposto();

                        // try to find the worst value to MIN player and change the turn
                        melhorJogada = Math.min(melhorJogada, minMax(tabuleiro, tamanho + 1, !turno));

                        // undo last move to preserv the board
                        tabuleiro[line][colunm] = vazio;
                    }
                }
            }
            return melhorJogada;
        }
    }

    private boolean jogadaLivre(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    return true;
                }
            }
        }

        return false;
    }

    private int validaMovimento(int[][] board) {
        
        int line = 0;
        int colunm = 0;
        int boardSize = board.length; // used to compare with win condition
        int winCondition = 0;
        int loseCondition = 0;
        int oppositeSymbol = simboloOposto();

        // check terminal cases
        // start
        //
        // check lose paths(line)
        for (line = 0; line < boardSize; line++) {
            for (colunm = 0; colunm < boardSize; colunm++) {
                if (board[line][colunm] == oppositeSymbol) {
                    loseCondition++;
                }

            }
            if (loseCondition == boardSize) {
                return -1; // Detect as lose path for the next opposite move (line)
            }
            loseCondition = 0;
        }

        // check lose paths (colunm)
        for (colunm = 0; colunm < boardSize; colunm++) {
            for (line = 0; line < boardSize; line++) {
                if (board[line][colunm] == oppositeSymbol) {
                    loseCondition++;
                }

            }
            if (loseCondition == boardSize) {
                return -1; // Detect as lose path for the next opposite move (colunm)
            }
            loseCondition = 0;
        }

        //TO DO: review later
        // check lose paths (left diagonal)
        for (colunm = 0; colunm < (boardSize - (board.length - 1)); colunm++) {
			for (line = 0; line < (board.length - (board.length - 1)); line++) {
				while (board[line][colunm] == oppositeSymbol) {
					line++;
					colunm++;
					loseCondition++;
					if (loseCondition == board.length) {
						return -1;
					}
				}
				loseCondition = 0;
			}
		}
        //TO DO: review later
        // check lose paths (right diagonal)
        for (colunm = boardSize-1; colunm > boardSize - 2; colunm-- ) {
			for (line = 0; line < (board.length - (board.length - 1)); line++) {
				while (board[line][colunm] == oppositeSymbol) {
					line++;
					colunm--;
					loseCondition++;
					if (loseCondition == board.length) {
						return -1;
					}
				}
				loseCondition = 0;
			}
		}
        //
        // check win paths (line)
        for (line = 0; line < boardSize; line++) {
            for (colunm = 0; colunm < boardSize; colunm++) {
                if (board[line][colunm] == this.getSimbolo()) {
                    winCondition++;
                }

            }
            if (winCondition == boardSize) {
                return 1; // Detect as win path for the next move (line)
            }
            winCondition = 0;
        }

        // check win paths (colunm)
        for (colunm = 0; colunm < boardSize; colunm++) {
            for (line = 0; line < boardSize; line++) {
                if (board[line][colunm] == this.getSimbolo()) {
                    winCondition++;
                }

            }
            if (winCondition == boardSize) {
                return 1; // Detect as win path for the next move (colunm)
            }
            winCondition = 0;
        }

        // check win paths (left diagonal)
        for (line = 0; line < boardSize; line++) { // TO DO: replace this line to 'line = 0'
            for (colunm = 0; colunm < boardSize; colunm++) {
                if (board[line][colunm] == this.getSimbolo()) {
                    winCondition++;
                }
                if (line < boardSize) {
                    // go to next line if not last
                    line++;
                }

            }
            if (winCondition == boardSize) {
                return 1; // Detect as win path for the next move (left diagonal)
            }
            winCondition = 0;
        }

        // check win paths (right diagonal)
        for (line = 0; line < boardSize; line++) {
            for (colunm = 2; colunm > boardSize; colunm--) {
                if (board[line][colunm] == this.getSimbolo()) {
                    winCondition++;
                }
                if (line < boardSize) {
                    // go to next line if not last
                    line++;
                }

            }
            if (winCondition == boardSize) {
                return 1; // Detect as win path for the next move (right diagonal)
            }
            winCondition = 0;
        }

        // end

        // if the next move don't result in a win or loss return 0
        return 0;
    }

    private int simboloOposto() {
        if (this.getSimbolo() == 1) {
            return 0;
        } else {
            return 1;
        }

    }

}
    
