import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import javax.swing.JFrame;

public class GraphicMaze extends JFrame {
	private static final long serialVersionUID = 1L;
	Container c; Graphic g;
	private static final char[][] maze =
		  {{' ','X',' ','X',' ',' '},
		   {' ','X',' ',' ',' ','X'},
		   {' ',' ','X','X',' ','X'},
		   {'X',' ',' ',' ',' ','X'},
		   {' ',' ',' ','X',' ','X'},
		   {'X','X',' ',' ',' ',' '}};
	
	public GraphicMaze()
	{
		c = getContentPane();
		g = new Graphic(600, 600);
		c.add(g);
	}
	
	public void showElements() {
		for(int i = 0; i <= 600; i += 100) {
			g.drawLine(0, i, 600, i);
			g.drawLine(i, 0, i, 600);
		}
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				int x = j * 100;
				int y = i * 100;
				char c = maze[i][j];
				switch(c) {
				case '.':
					g.setColor(Color.blue);
					g.fillOval(x, y, 100, 100);
					break;
				case 'X':
					g.setColor(Color.black);
					g.fillRect(x, y, 100, 100);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		
				   
		Maze testmaze = new Maze(maze);
		try {
			if(testmaze.canExit(new Point(0,0)))
			{
				for(Point p : testmaze.getSolution())
				{
					System.out.println("Point: " + p.x + " " + p.y);
				}
			}
			else throw new UnsolvableMazeException();
		}
		catch(UnsolvableMazeException ex)
		{
			System.out.println(ex.getMessage());
			System.exit(-1);
		}	
		GraphicMaze fenster = new GraphicMaze();
		fenster.setTitle("Graphic");
	    fenster.pack();
	    fenster.setVisible(true);
	    fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    fenster.showElements();
	}

}
