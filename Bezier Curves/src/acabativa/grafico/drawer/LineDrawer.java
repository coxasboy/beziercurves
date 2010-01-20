package acabativa.grafico.drawer;

import java.awt.Point;
import java.awt.Shape;

public interface LineDrawer extends Drawer{

	
	public Shape getShape(int ticker);
	
	public Shape getShape(double bezierCoeficient);
	
	public Point getPoint(int ticker);
	
	public Point getPoint(double bezierCoeficient);
	
	public double getLineSize();
	
	public boolean existDraw(int ticker);
	
}
