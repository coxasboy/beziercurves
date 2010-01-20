package acabativa.grafico.drawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import acabativa.grafico.action.LineWalker;

public class LineGrowDrawer implements LineDrawer{

	private static final int SHAPE_SIZE = 6;
	
	private Point start;
	private Point end;
	LineWalker lineWalker = new LineWalker(5d);
	@SuppressWarnings("unused")
	private Class<?> defaultShape = Ellipse2D.class.getClass();
		
	public LineGrowDrawer(Point start, Point end) {
		super();
		if(start==null || end==null){
			  throw new IllegalArgumentException("Ponto nulo!");
		}	
		this.start = start;
		this.end = end;
	}
	
	public double getLineSize(){
		return lineWalker.getHipotenusa(start, end);
	}
	
	public void setPass(Double pass){
		lineWalker.setPass(pass);
	}
	
	public double getPass(){
		return lineWalker.getPass();
	}
		
	public LineGrowDrawer (LineDrawer drawerOne, LineDrawer drawerTwo, int ticker){
		this(drawerOne.getPoint(ticker), drawerTwo.getPoint(ticker));
	}
	
	public LineGrowDrawer (LineDrawer drawerOne, LineDrawer drawerTwo, double bezierCoeficient){
		this(drawerOne.getPoint(bezierCoeficient), drawerTwo.getPoint(bezierCoeficient));
	}
	
	public void draw(Graphics2D g2d, int ticker) throws IllegalArgumentException{
		
		Point point = getPoint(ticker);
		
		if(point!=null){
			g2d.draw(new Line2D.Double(
					new Point2D.Double(start.getX(), start.getY()),
					new Point2D.Double(point.getX(), point.getY())
			));	
			createAndDrawShape(g2d, start);
			createAndDrawShape(g2d, point);			
		}
	}
	
	public void draw(Graphics2D g2d, double bezierCoeficient) throws IllegalArgumentException{
		
		Point point = getPoint(bezierCoeficient);
		
		if(point!=null){
			g2d.draw(new Line2D.Double(
					new Point2D.Double(start.getX(), start.getY()),
					new Point2D.Double(point.getX(), point.getY())
			));		
			createAndDrawShape(g2d, start);
			createAndDrawShape(g2d, point);
		}
	}
	
	private void createAndDrawShape(Graphics2D g2d, Point point){
		Shape shape = new Ellipse2D.Double((int)point.getX()-(SHAPE_SIZE/2),(int)point.getY()-(SHAPE_SIZE/2),SHAPE_SIZE,SHAPE_SIZE);
		g2d.fill(shape);
	}
	
	public Shape getShape(int ticker){
		Point point = getPoint(ticker);
		return getShape(point);
	}
	
	public Shape getShape(double bezierCoeficient){
		Point point = getPoint(bezierCoeficient);
		return getShape(point);
	}
	
	private Shape getShape(Point point){
		return new Ellipse2D.Double(point.getX(), point.getY(), 1, 1);
	}
	
	public Point getPoint(int ticker){
		Point point = lineWalker.getPoint(start, end, ticker);
		return point;
	}
	
	public Point getPoint(double bezierCoeficient){
		Point point = lineWalker.getPoint(start, end, bezierCoeficient);
		return point;
	}
	
}
