package acabativa.grafico.drawer;

import java.awt.Graphics2D;

public interface Drawer {

	
	public void draw(Graphics2D g2d, int ticker) throws IllegalArgumentException;
}
