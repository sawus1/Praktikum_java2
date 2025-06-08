import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;

public class GraphicMaze extends JFrame {

	private static final long serialVersionUID = 1L;
	Container gm;
	Graphic mazeDrawing;
	Maze maze;
	List<Point> solution;

	private final static int size = 100;
	private final static int offset = 10;

	public void drawMaze() {
		for (int row = 0; row < maze.size(); row++) {
			for (int col = 0; col < maze.size(); col++) {
				mazeDrawing.setColor(Color.BLACK);
				if (maze.getMaze().contains(new Point(row, col)))
					mazeDrawing.fillRect(col * size, row * size, size, size);
				else
					mazeDrawing.drawRect(col * size, row * size, size, size);

			}
		}
		drawSolution();
		mazeDrawing.redraw();
	}

	public void drawSolution() {
		for (int row = 0; row < maze.size(); row++) {
			for (int col = 0; col < maze.size(); col++) {
				mazeDrawing.setColor(Color.BLUE);
				if (maze.getSolution().contains(new Point(row, col)))
					mazeDrawing.fillOval(col * size + offset, row * size + offset, size - 2 * offset,
							size - 2 * offset);
			}
		}

	}

	public GraphicMaze(Maze m) {
		gm = getContentPane();
		maze = m;
		solution = maze.getSolution();
		mazeDrawing = new Graphic(maze.size() * size, maze.size() * size);

		gm.add(mazeDrawing);

	}

	public static void main(String[] args) {

		char[][] maze = { { ' ', 'X', ' ', 'X', ' ', ' ' }, { ' ', 'X', ' ', ' ', ' ', 'X' },
				{ ' ', ' ', 'X', 'X', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', 'X' }, { ' ', ' ', ' ', 'X', ' ', 'X' },
				{ 'X', 'X', ' ', ' ', ' ', ' ' } };

		Maze mymaze = new Maze(maze);
		mymaze.canExit(0, 0);
		mymaze.printMaze();

		GraphicMaze gm = new GraphicMaze(mymaze);
		gm.setTitle("Graphic Maze");
		gm.setSize(mymaze.size() * size, mymaze.size() * size);
		gm.setVisible(true);
		gm.pack();
		gm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gm.drawMaze();

	}

}
