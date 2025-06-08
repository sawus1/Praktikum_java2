import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Maze
{
	private char[][] maze;
	private List<Point> solution;
	
	public Maze(char[][] maze)
	{
		this.maze = maze;
		solution = new ArrayList<Point>();
	}
	
	public boolean canExit(Point p) 
	{
		int i = p.y;
		int j = p.x;
		int n = maze.length;
		if (i<0||j<0||i>=n||j>=n) return false; // outside
		if (maze[i][j]!=' ') return false; // wall or visited
		maze[i][j]='.'; // mark as visited
		solution.add(p);
		if (i==n-1&&j==n-1 // at the goal
		        || canExit(new Point(j, i+1)) || canExit(new Point(j+1, i))
		        || canExit(new Point(j, i-1)) || canExit(new Point(j-1, i))) {
		      System.out.println("("+j+","+i+")");
		return true;
		    }
		solution.clear();
		return false;
	}
	
	public List<Point> getSolution()
	{
		return solution;
	}
}