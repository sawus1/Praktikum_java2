import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class Graphic extends JPanel {

	private static final long serialVersionUID = 1L;
	
	List<GraphicOperation> list = new LinkedList<>();
	int width, height;

	public Graphic(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for (GraphicOperation op: list)
			op.draw(g);
    } 

	public void drawLine(int x1, int y1, int x2, int y2) {
		list.add(new DrawLineOperation(x1, y1, x2, y2));
	}

	public void setColor(Color color) {
		list.add(new SetColorOperation(color));
	}

	public void redraw() {
		repaint();		
	}

	public void drawRect(int x1, int y1, int width, int height) {
		list.add(new DrawRectOperation(x1, y1, width, height));
	}

	public void drawOval(int x1, int y1, int width, int height) {
		list.add(new DrawOvalOperation(x1, y1, width, height));
	}

	public void fillRect(int x1, int y1, int width, int height) {
		list.add(new FillRectOperation(x1, y1, width, height));
	}

	public void fillOval(int x1, int y1, int width, int height) {
		list.add(new FillOvalOperation(x1, y1, width, height));	
	}

	public void clear() {
		list = new ArrayList<>();
	}

	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public interface GraphicOperation {
		void draw(Graphics g);
	}
	
	class SetColorOperation implements GraphicOperation {
		Color c;
		SetColorOperation(Color c) {
			this.c = c;
		}
		public void draw(Graphics g) {
			g.setColor(c);
		}
	}
	
	class DrawLineOperation implements GraphicOperation {
		int x1,y1,x2,y2;
		
		DrawLineOperation(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		public void draw(Graphics g) {
			g.drawLine(x1,y1,x2,y2);
		}
	}
	
	class DrawRectOperation implements GraphicOperation {
		int x1,y1,width,height;
		
		DrawRectOperation(int x1, int y1, int width, int height) {
			this.x1 = x1;
			this.y1 = y1;
			this.width = width;
			this.height = height;
		}
		public void draw(Graphics g) {
			g.drawRect(x1,y1,width,height);
			//System.out.format("DrawRect: %d %d %d %d\n", x1, y1, x2, y2);
		}
	}
	
	class FillRectOperation implements GraphicOperation {
		int x1,y1,width,height;
		
		FillRectOperation(int x1, int y1, int width, int height) {
			this.x1 = x1;
			this.y1 = y1;
			this.width = width;
			this.height = height;
		}
		public void draw(Graphics g) {
			g.fillRect(x1,y1,width,height);
			//System.out.format("FillRect: %d %d %d %d\n", x1, y1, x2, y2);
		}
	}
	
	class DrawOvalOperation implements GraphicOperation {
		int x1,y1,width,height;
		
		DrawOvalOperation(int x1, int y1, int width, int height) {
			this.x1 = x1;
			this.y1 = y1;
			this.width = width;
			this.height = height;
		}
		public void draw(Graphics g) {
			g.drawOval(x1,y1,width,height);
		}
	}
	
	class FillOvalOperation implements GraphicOperation {
		int x1,y1,width,height;
		
		FillOvalOperation(int x1, int y1, int width, int height) {
			this.x1 = x1;
			this.y1 = y1;
			this.width = width;
			this.height = height;
		}
		public void draw(Graphics g) {
			g.fillOval(x1,y1,width,height);
		}
	}
	
}
