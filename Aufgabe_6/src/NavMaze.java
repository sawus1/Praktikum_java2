import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NavMaze extends JFrame {
	private static final long serialVersionUID = 1L;
	Container c; Graphic g;
	JButton b1, b2;
	int navPos = 0;
	private static final char[][] maze =
		  {{' ','X',' ','X',' ',' '},
		   {' ','X',' ',' ',' ','X'},
		   {' ',' ','X','X',' ','X'},
		   {'X',' ',' ',' ',' ','X'},
		   {' ',' ',' ','X',' ','X'},
		   {'X','X',' ',' ',' ',' '}};
	private static List<Point> solution;
	public NavMaze()
	{
		c = getContentPane();
		g = new Graphic(600, 600);
		b1 = new JButton("Zurück");
		b2 = new JButton("Vor");
		JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(b1);
	    buttonPanel.add(b2);

	    c.setLayout(new BorderLayout());
	    c.add(g, BorderLayout.CENTER);
	    c.add(buttonPanel, BorderLayout.SOUTH);
	    
	    ActionListener vorL = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(navPos + 1 < solution.size())
				{
					navPos++;
					System.out.println(navPos);
					showElements();
				}
			}
		};
		
		ActionListener zurL = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(navPos > 0)
				{
					navPos--;
					System.out.println(navPos);
					showElements();
				}
			}
		};
		
		b1.addActionListener(zurL);
		b2.addActionListener(vorL);
		
	}
	
	public void showElements() {
		g.clear();
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
				if(c == 'X')
				{
					g.setColor(Color.black);
					g.fillRect(x, y, 100, 100);
				}
			}
		}
		Point p = solution.get(navPos);
		int px = p.x * 100;
		int py = p.y * 100;
		g.setColor(Color.blue);
		System.out.println("draw oval" + px + " " + py);
		g.fillOval(px,py,100,100);
		g.redraw();
	}
	
	public static void main(String[] args) {
		
		
				   
		Maze testmaze = new Maze(maze);
		try {
			if(testmaze.canExit(new Point(0,0)))
			{
				solution = testmaze.getSolution();
				for(Point p : solution)
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
		NavMaze fenster = new NavMaze();
		fenster.setTitle("Graphic");
	    fenster.pack();
	    fenster.setVisible(true);
	    fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    fenster.showElements();
	}
	
	

}
