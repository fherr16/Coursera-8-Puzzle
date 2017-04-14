import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  
  private int moves;
  private MinPQ<SearchNode> pq;
  SearchNode sn;
  boolean solvable;

  
  private class SearchNode {
    Board board;
    int moves;
    SearchNode previous;
  }
  
  public class SolutionIterator implements Iterator<Board> {
    public boolean hasNext() {
      return sn != null;
    }
    public Board next() {
      SearchNode temp = sn;
      sn = sn.previous;
      return temp.board;
    }
  }
  
  public class SolutionIterable implements Iterable<Board> {
    public Iterator<Board> iterator() {
      return new SolutionIterator();
    }  
  }
  
  public Solver(Board initial) {
    pq = new MinPQ<SearchNode>();
    sn = new SearchNode();
    sn.board = initial;
    sn.moves = 0;
    sn.previous = null;
    
    if(!isSolvable())
      solvable = false;
    else{
      sn = new SearchNode();
      pq = new MinPQ<SearchNode>();
      sn.board = initial;
      sn.moves = 0;
      sn.previous = null;
      pq.insert(sn);
      sn = pq.delMin();
      SearchNode temp;
      
      while (!sn.board.isGoal()) {
        for (Board board : sn.board.neighbors()) {
          temp = new SearchNode();
          temp.board = board;
          temp.moves = sn.moves + 1;
          temp.previous = sn;
          if (sn.previous != temp)
            pq.insert(temp);
        }
        sn = pq.delMin();
      }
    }
  }
  
  public boolean isSolvable() {
    return false;
  }
  
  public int moves() {
    if (!isSolvable()) return -1;
    else return moves;
  }
  
  public Iterable<Board> solution() {
    return new SolutionIterable();
  }
  
  public static void main(String[] args) {
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    Solver solver = new Solver(initial);

    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
  
}
