import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
  
  private int[][] board;
  private final int n;
  
  private class BoardIterator implements Iterator<Board> {
    
    private int row, col, count;
    private int[][] boardClone;
    
    public BoardIterator() {
      
      if (board == null)
        throw new NoSuchElementException();
      
      findZeroLoop:
      for (row = 0; row < n; row++)
        for (col = 0; col < n; col++)
          if (board[row][col] == 0) break findZeroLoop;
            
      count = 4;
    }

    public boolean hasNext() {  
      if (count == 2 && row == n - 1 && col == 0) return false;
      else if (count == 1 && col == 0) return false;
      else return count > 0;
    }

    public Board next() {
              
      if (board == null)
        throw new NoSuchElementException();
      
      boardClone = new int[n][n];
      
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          boardClone[i][j] = board[i][j];
            
      if (count == 4) {
        try {
          boardClone[row][col] = boardClone[row - 1][col];
          boardClone[row - 1][col] = 0;
        } catch (ArrayIndexOutOfBoundsException ex) { count--; }
      }
      if (count == 3) {
        try {
          boardClone[row][col] = boardClone[row][col + 1];
          boardClone[row][col + 1] = 0;
        } catch (ArrayIndexOutOfBoundsException ex) { count--; }
      }
      if (count == 2) {
        try {
          boardClone[row][col] = boardClone[row + 1][col];
          boardClone[row + 1][col] = 0;
        } catch (ArrayIndexOutOfBoundsException ex) { count--; }
      }
      if (count == 1) {
        try {
          boardClone[row][col] = boardClone[row][col - 1];
          boardClone[row][col - 1] = 0;
        } catch (ArrayIndexOutOfBoundsException ex) { count--; }
      }
      count--;
      return new Board(boardClone);
    }
  }
  
  private class BoardIterable implements Iterable<Board> {
    public Iterator<Board> iterator() {
      return new BoardIterator();
    }  
  }
  
  public Board(int[][] blocks) {
    
    if (blocks == null)
      throw new NullPointerException();
    
    n = blocks.length;
    board = new int[n][n];
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++) board[row][col] = blocks[row][col];
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
            manhattanSum += Math.abs(goalCol - col);
          }
          else {
            goalRow = (board[row][col] / n);
            goalCol = (board[row][col] - 1) - (goalRow * n);
            manhattanSum += Math.abs(goalRow - row);
            manhattanSum += Math.abs(goalCol - col);
          }
        }
    return manhattanSum;
  }
  
  public boolean isGoal() {
    return hamming() == 0;
  }
  
  public Board twin() {
    int[][] boardClone = new int[n][n];
    
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        boardClone[i][j] = board[i][j];
    
    int temp, temp2;
    int tempRow = StdRandom.uniform(n);
    int tempCol = StdRandom.uniform(n); 
    while (boardClone[tempRow][tempCol] == 0) {
      tempRow = StdRandom.uniform(n);
      tempCol = StdRandom.uniform(n);
    }
    temp = boardClone[tempRow][tempCol];
    
    int temp2Row = StdRandom.uniform(n);
    int temp2Col = StdRandom.uniform(n); 
    while (boardClone[temp2Row][temp2Col] == 0 || boardClone[temp2Row][temp2Col] == temp) {
      temp2Row = StdRandom.uniform(n);
      temp2Col = StdRandom.uniform(n);
    }
    temp2 = boardClone[temp2Row][temp2Col];
    
    boardClone[tempRow][tempCol] = temp2;
    boardClone[temp2Row][temp2Col] = temp;
    
    return new Board(boardClone);
  }
  
  public boolean equals(Object y) {
    if (y == null) return false;
    return toString().equals(y.toString());
  }
  
  public Iterable<Board> neighbors() {
    return new BoardIterable();
  }
  
  public String toString() {
    StringBuffer representation = new StringBuffer();
    representation.append(n + "\n");
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (board[row][col] < 10)
          representation.append(" " + board[row][col] + " ");
        else
          representation.append(board[row][col]+ " ");
      }
      representation.append("\n");
    }
    return representation.toString();
  }
}
