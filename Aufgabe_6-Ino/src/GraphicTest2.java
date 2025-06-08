
import java.awt.Container;

import javax.swing.JFrame;

public class GraphicTest2 extends JFrame {

	private static final long serialVersionUID = 1L;

	Container c; Graphic g;
	
	public GraphicTest2() {
	    c = getContentPane();
	    g = new Graphic(300, 300);
	    c.add(g);
	  }
	
	public void animate() {
		for (int i=0; i<200; i++) {
			g.fillRect(i,100,100,100);
			g.redraw();
			g.sleep(100);
			g.clear();
		}
	}
	
	public static void main(String[] args) {
		GraphicTest2 fenster = new GraphicTest2();
	    fenster.setTitle("Graphic");
	    fenster.pack();
	    fenster.setVisible(true);
	    fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		fenster.animate();
	}
	
}
