import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
  
  private int moves;
  private MinPQ<SearchNode> pq;
  
  private class SearchNode {
    Board board;
    int moves;
    SearchNode previous;
  }
  
  public class SolutionIterator<Board> implements Iterator<Board> {
    public boolean hasNext() {
      return false;
    }
    public Board next() {
      return null;
    }
  }
  
  public class SolutionIterable<Board> implements Iterable<Board> {
    public Iterator<Board> iterator() {
      return new SolutionIterator<Board>();
    }
  }
  
  public Solver(Board initial) {
    pq = new MinPQ<SearchNode>();
    SearchNode sn = new SearchNode();
    sn.board = initial;
    sn.moves = 0;
    sn.previous = null;
    
    pq.insert(sn);
    SearchNode removed = pq.delMin();
    SearchNode temp;
    
    while (!removed.board.isGoal()) {
      for (Board board : removed.board.neighbors()) {
        temp = new SearchNode();
        temp.board = board;
        temp.moves = removed.moves + 1;
        temp.previous = removed;
        
        pq.insert(temp);
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
    return new SolutionIterable<Board>();
  }
  
  public static void main(String[] args) {

  }
  
}
