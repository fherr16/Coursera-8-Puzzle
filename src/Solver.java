import java.util.Iterator;

public class Solver {
  
  private int moves;
  
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
