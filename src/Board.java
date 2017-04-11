import java.util.Iterator;

public class Board {
  
  private int[][] board;
  private final int n;
  
  public class BoardIterator implements Iterator<Board> {

    public boolean hasNext() {
      return false;
    }

    public Board next() {
      return null;
    }
  }
  
  public class BoardIterable implements Iterable<Board> {
    public Iterator<Board> iterator() {
      return new BoardIterator();
    }  
  }
  
  public Board(int[][] blocks) {
    n = blocks.length;
    board = new int[n][n];
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        board[row][col] = blocks[row][col];
  }
  
  public int dimension() {
    return n;
  }
  
  public int hamming() {
    int outOfPlace = 0;
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        if (board[row][col] != 0 && board[row][col] != (row*n) + col + 1) outOfPlace++;
    return outOfPlace;
  }
  
  public int manhattan() {
    int manhattanSum = 0;
    int goalRow, goalCol;
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        if (board[row][col] != 0 && board[row][col] != (row*n) + col + 1) {
          if (board[row][col] % n == 0) {
            goalRow = (board[row][col] / n) - 1;
            goalCol = (board[row][col] - 1) - (goalRow * n);
            manhattanSum += Math.abs(goalRow - row);
            manhattanSum += Math.abs(goalCol - row);
          }
          else {
            goalRow = (board[row][col] / n);
            goalCol = (board[row][col] - 1) - (goalRow * n);
            manhattanSum += Math.abs(goalRow - row);
            manhattanSum += Math.abs(goalCol - row);
          }
        }
    return manhattanSum;
  }
  
  public boolean isGoal() {
    return hamming() == 0;
  }
  
  public Board twin() {
    Board twin = new Board(board);
    
    
    
    return twin;
  }
  
  public boolean equals(Board y) {
    return false;
  }
  
  public Iterable<Board> neighbors() {
    return new BoardIterable();
  }
  
  public String toString() {
    return "Fix";
  }

}
