import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class GraphicTest extends JFrame {

	private static final long serialVersionUID = 1L;

	Container c; Graphic g;
	
	public GraphicTest() {
	    c = getContentPane();
	    g = new Graphic(300, 300);
	    c.add(g);
	  }
	
	public void showElements() {
		g.drawLine(0,0,100,100);
		g.setColor(Color.BLUE);
		g.drawRect(100,0,100,100);
		g.setColor(Color.GREEN);
		g.drawOval(200,0,100,100);
		g.fillRect(0,100,100,100);
		g.setColor(Color.RED);
		g.fillOval(100,100,100,100);
		g.redraw();
	}
	
	public static void main(String[] args) {		
		GraphicTest fenster = new GraphicTest();
	    fenster.setTitle("Graphic");
	    fenster.pack();
	    fenster.setVisible(true);
	    fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    fenster.showElements();
	}
}