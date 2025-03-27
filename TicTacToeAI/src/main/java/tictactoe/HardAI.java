package tictactoe;

class HardAI implements Player {
  private final Square playingAs;
  private final Square opponentPlayingAs;
  private final Table table;

  HardAI(Square playingAs, Table table) {
    this.playingAs = playingAs;
    opponentPlayingAs = playingAs == Square.X ? Square.O : Square.X;
    this.table = table;
  }

  @Override
  public void move() {
    System.out.println("Making move level \"hard\"");
    int bestMove = -1;
    int bestScore = Integer.MIN_VALUE;
    for (int i : table.availableCells()) {
      table.set(i, playingAs);
      int score = minimax(table, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
      if (score == 1) return;
      table.set(i, Square.EMPTY);
      if (score > bestScore) {
        bestMove = i;
        bestScore = score;
      }
    }
    table.set(bestMove, playingAs);
  }

  private int minimax(Table table, int alpha, int beta, boolean maximizingPlayer) {
    if (table.hasThreeInARow(playingAs)) return 1;
    if (table.hasThreeInARow(opponentPlayingAs)) return -1;
    if (table.isFull()) return 0;

    if (maximizingPlayer) {
      int maxScore = Integer.MIN_VALUE;
      for (int i : table.availableCells()) {
        table.set(i, playingAs);
        maxScore = Math.max(maxScore, minimax(table, alpha, beta, false));
        table.set(i, Square.EMPTY);
        if (maxScore > beta) break;
        alpha = Math.max(alpha, maxScore);
      }
      return maxScore;
    } else {
      int minScore = Integer.MAX_VALUE;
      for (int i : table.availableCells()) {
        table.set(i, opponentPlayingAs);
        minScore = Math.min(minScore, minimax(table, alpha, beta, true));
        table.set(i, Square.EMPTY);
        if (minScore < alpha) break;
        beta = Math.min(beta, minScore);
      }
      return minScore;
    }
  }
}
