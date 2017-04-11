
public class Board {
  
  private int[][] board;
  private int n;
  
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
    return outOfPlace;
  }
  
  public int manhattan() {
    int manhattanSum = 0;
    return manhattanSum;
  }
  
  public boolean isGoal() {
    return false;
  }
  
  public Board twin() {
    Board twin = new Board(board);
    return twin;
  }
  
  public boolean equals(Board y) {
    return false;
  }
  
  public Iterable<Board> neighbors() {
    
  }
  
  public String toString() {
    return "Fix";
  }

}
