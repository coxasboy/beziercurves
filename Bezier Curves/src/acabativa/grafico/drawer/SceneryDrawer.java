package acabativa.grafico.drawer;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class SceneryDrawer implements Drawer{
	int maxWidht = 0;
	int maxHeight = 0;
		
	public SceneryDrawer(int maxWidht, int maxHeight) {
		super();
		this.maxWidht = maxWidht;
		this.maxHeight = maxHeight;
	}

	public void draw(Graphics2D g2d, int ticker){
		Font font = new Font("Dialog", Font.PLAIN, 20);
		
		g2d.setFont(font);
		g2d.drawString("Tempo: " + ticker, maxWidht+5, 30 );
		
		g2d.draw(new Line2D.Double(0,maxWidht,maxHeight,maxHeight));
		g2d.draw(new Line2D.Double(maxWidht,0,maxHeight,maxHeight));
		g2d.draw(new Line2D.Double(0,0,maxHeight,0));
		g2d.draw(new Line2D.Double(0,0,0,maxHeight));
		g2d.draw(new Rectangle(maxWidht/2,maxHeight/2,1,1));
	}

	@Override
	public void draw(Graphics2D g2d, double bezierCoeficient)
			throws IllegalArgumentException {
		draw(g2d,(int)bezierCoeficient);
	}

}
