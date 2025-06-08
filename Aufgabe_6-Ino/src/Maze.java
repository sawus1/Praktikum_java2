import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Maze {

	char[][] maze;
	List<Point> solution = new ArrayList<Point>();

	public Maze(char[][] maze) {
		this.maze = maze;
	}

	public boolean canExit(int i, int j) {

		int n = maze.length;

		// ausserhalb?
		if (i < 0 || j < 0 || i >= n || j >= n)
			return false;

		// Mauer?
		if (maze[i][j] != ' ')
			return false;

		maze[i][j] = '.';

		if (i == n - 1 && j == n - 1 || canExit(i + 1, j) || canExit(i, j + 1) || canExit(i - 1, j)
				|| canExit(i, j - 1)) {
			System.out.println("(" + j + ", " + i + ")");
			Point p = new Point(i,j);
			solution.add(p);
			System.out.println(p);
			
			maze[i][j] = '+';
			return true;
		}

		return false;

	}

	public void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++)
				System.out.print(maze[i][j] + " ");
			System.out.println();
		}
	}

	public List<Point> getSolution() {
		canExit(0,0);
		return solution;

	}
	
	public int size() {
		return maze.length;
	}
	
	public List<Point> getMaze(){
		List<Point> borders = new ArrayList<Point>();
		for(int row = 0; row < size(); row++) {
			for(int col = 0; col < size();col++) {
				if(maze[row][col] == 'X')
					borders.add(new Point(row,col));
			}
		}
		return borders;
	}

	public static void main(String[] args) {

		char[][] maze = { { ' ', 'X', ' ', 'X', ' ', ' ' }, { ' ', 'X', ' ', ' ', ' ', 'X' },
				{ ' ', ' ', 'X', 'X', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', 'X' }, { ' ', ' ', ' ', 'X', ' ', 'X' },
				{ 'X', 'X', ' ', ' ', ' ', ' ' } };

		Maze mymaze = new Maze(maze);
		mymaze.canExit(0, 0);
		mymaze.printMaze();
	}

}
