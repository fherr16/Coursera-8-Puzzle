import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  
  private int moves;
  private SearchNode solution, twin, temp;
  private boolean solvable;

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int moves;
    private SearchNode previous;
    
    public int compareTo(SearchNode other) {
      if ((this.board.manhattan() + this.moves) > (other.board.manhattan() + other.moves)) return +1;
      else return -1;
    }
  }
  
  private class SolutionIterator implements Iterator<Board> {
    private int count;
    private SearchNode transverse = new SearchNode();
    
    public SolutionIterator() {
      count = solution.moves;
    }
    
    public boolean hasNext() {
      return count >= 0;
    }
    public Board next() {
      if (solution == null) throw new NoSuchElementException();
      
      int counter = 0;
      transverse.board = solution.board;
      transverse.moves = solution.moves;
      transverse.previous = solution.previous;
      
      while (counter < count) {
        transverse = transverse.previous;
        counter++;
      }
      count--;
      return transverse.board;
    }
  }
  
  private class SolutionIterable implements Iterable<Board> {
    public Iterator<Board> iterator() {
      return new SolutionIterator();
    }  
  }
  
  public Solver(Board initial) {
    
    if (initial == null)
      throw new NullPointerException();
        
    if (initial.isGoal()) {
      solvable = true;
      moves = 0;
      solution = new SearchNode();
      solution.board = initial;
      solution.moves = 0;
      solution.previous = null;
    }
    else {
      MinPQ<SearchNode> pqSolution = new MinPQ<SearchNode>();
      MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
      
      boolean initialFlag = false;
      boolean twinFlag = false;

      solution = new SearchNode();
      twin = new SearchNode();
      temp = new SearchNode();
      
      solution.board = initial;
      solution.moves = 0;
      solution.previous = null;
      pqSolution.insert(solution);
      
      twin.board = initial.twin();
      twin.moves = 0;
      twin.previous = null;
      pqTwin.insert(twin);
            
      while (!initialFlag && !twinFlag) {
        
        solution = new SearchNode();
        solution = pqSolution.delMin();
        twin = new SearchNode();
        twin = pqTwin.delMin();
        
        if (solution.board.isGoal())
          initialFlag = true;
        else {
          for (Board board : solution.board.neighbors()) {
            if (solution.previous == null || !board.equals(solution.previous.board)) {
              temp = new SearchNode();
              temp.board = board;
              temp.moves = solution.moves+1;
              temp.previous = solution;
              pqSolution.insert(temp); 
            }
          } 
        }
        
        if (twin.board.isGoal())
          twinFlag = true;
        else {
          for (Board x : twin.board.neighbors()) {
            if (twin.previous == null || !x.equals(twin.previous.board)) {
              temp = new SearchNode();
              temp.board = x;
              temp.moves = twin.moves+1;
              temp.previous = twin;
              pqTwin.insert(temp); 
            }
          } 
        }
      }
      
      if (twinFlag && !initialFlag)
        solvable = false;
      else {
        solvable = true;
        moves = solution.moves; 
      }
    }
  }
  
  public boolean isSolvable() {
    return solvable;
  }
  
  public int moves() {
    if (!isSolvable()) return -1;
    else return moves;
  }
  
  public Iterable<Board> solution() {
    if (!isSolvable())
      return null;
    return new SolutionIterable();
  }
  
  public static void main(String[] args) {

    // create initial board from file
//    In in = new In("/Users/fabianherrera/Documents/CourseraTests/8 Puzzle/puzzle25.txt");
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}
