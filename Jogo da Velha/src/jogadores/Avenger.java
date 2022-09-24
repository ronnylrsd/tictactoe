package jogadores;

public class Avenger extends Jogador {

    private int tamanhoIdealTabuleiro = 3;
    private int vazio = -1;

    public Avenger(String nome) {
        super(nome);

    }
    private int[] jogada(int[][] board, int i) {
        int melhorJogada = -1;
        int tamanhoTabuleiro = board.length;
        int[] jogada = new int[i];

        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                if (board[linha][coluna] == -1) {
                    board[linha][coluna] = this.getSimbolo();
                    int tryMove = minMax(board, 0, false);
                    board[linha][coluna] = vazio;
                    if (tryMove > melhorJogada) {
                        jogada[0] = linha;
                        jogada[1] = coluna;
                        melhorJogada = tryMove;
                    }
                }
            }
        }
        return jogada;
    }

    @Override
    public int[] jogar(int[][] board) {
        int[] jogada = jogada(board, 2);
        return jogada;
    }

    private int minMax(int[][] tabuleiro, int tamanho, boolean turno) {
        int pontuacao = validaMovimento(tabuleiro);
        if (tabuleiro.length <= tamanhoIdealTabuleiro) {
            if (tamanho >= 8) {
                return pontuacao;
            }
        } else {

            if (tamanho >= 3) {
                return pontuacao;
            }
        }
        if (pontuacao == -1 || pontuacao == 1) {
            return pontuacao;
        }
        if (!jogadaLivre(tabuleiro)) {
            return 0;
        }
        if (turno) {
            int melhorJogada = -1;
            for (int i = 0; i < tabuleiro.length; i++) {
                for (int j = 0; j < tabuleiro.length; j++) {
                    if (tabuleiro[i][j] == -1) {
                        tabuleiro[i][j] = this.getSimbolo();
                        melhorJogada = Math.max(melhorJogada, minMax(tabuleiro, tamanho + 1, !turno));
                        tabuleiro[i][j] = vazio;
                    }
                }
            }
            return melhorJogada;
        }
        else {
            int melhorJogada = 1;

            for (int linha = 0; linha < tabuleiro.length; linha++) {
                for (int coluna = 0; coluna < tabuleiro.length; coluna++) {
                    if (tabuleiro[linha][coluna] == -1) {
                        tabuleiro[linha][coluna] = simboloOposto();
                        melhorJogada = Math.min(melhorJogada, minMax(tabuleiro, tamanho + 1, !turno));
                        tabuleiro[linha][coluna] = vazio;
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

        int linha = 0;
        int coluna = 0;
        int condicaoDeVitoria = 0;
        int loseCondition = 0;
        int oppositeSymbol = simboloOposto();

        for (linha = 0; linha < board.length; linha++) {
            for (coluna = 0; coluna < board.length; coluna++) {
                if (board[linha][coluna] == oppositeSymbol) {
                    loseCondition++;
                }

            }
            if (loseCondition == board.length) {
                return -1;
            }
            loseCondition = 0;
        }

        for (coluna = 0; coluna < board.length; coluna++) {
            for (linha = 0; linha < board.length; linha++) {
                if (board[linha][coluna] == oppositeSymbol) {
                    loseCondition++;
                }

            }
            if (loseCondition == board.length) {
                return -1;
            }
            loseCondition = 0;
        }

        for (coluna = 0; coluna < (board.length - (board.length - 1)); coluna++) {
            for (linha = 0; linha < (board.length - (board.length - 1)); linha++) {
                while (board[linha][coluna] == oppositeSymbol) {
                    linha++;
                    coluna++;
                    loseCondition++;
                    if (loseCondition == board.length) {
                        return -1;
                    }
                }
                loseCondition = 0;
            }
        }

        for (coluna = board.length - 1; coluna > board.length - 2; coluna--) {
            for (linha = 0; linha < (board.length - (board.length - 1)); linha++) {
                while (board[linha][coluna] == oppositeSymbol) {
                    linha++;
                    coluna--;
                    loseCondition++;
                    if (loseCondition == board.length) {
                        return -1;
                    }
                }
                loseCondition = 0;
            }
        }

        for (linha = 0; linha < board.length; linha++) {
            for (coluna = 0; coluna < board.length; coluna++) {
                if (board[linha][coluna] == this.getSimbolo()) {
                    condicaoDeVitoria++;
                }

            }
            if (condicaoDeVitoria == board.length) {
                return 1;
            }
            condicaoDeVitoria = 0;
        }

        for (coluna = 0; coluna < board.length; coluna++) {
            for (linha = 0; linha < board.length; linha++) {
                if (board[linha][coluna] == this.getSimbolo()) {
                    condicaoDeVitoria++;
                }

            }
            if (condicaoDeVitoria == board.length) {
                return 1;
            }
            condicaoDeVitoria = 0;
        }

        for (linha = 0; linha < board.length; linha++) {
            for (coluna = 0; coluna < board.length; coluna++) {
                if (board[linha][coluna] == this.getSimbolo()) {
                    condicaoDeVitoria++;
                }
                if (linha < board.length) {

                    linha++;
                }

            }
            if (condicaoDeVitoria == board.length) {
                return 1;
            }
            condicaoDeVitoria = 0;
        }

        for (linha = 0; linha < board.length; linha++) {
            for (coluna = 2; coluna > board.length; coluna--) {
                if (board[linha][coluna] == this.getSimbolo()) {
                    condicaoDeVitoria++;
                }
                if (linha < board.length) {

                    linha++;
                }

            }
            if (condicaoDeVitoria == board.length) {
                return 1;
            }
            condicaoDeVitoria = 0;
        }

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
