package acabativa.grafico.drawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import acabativa.grafico.action.LineWalker;

public class LineDrawer implements Drawer{

	private Point start;
	private Point end;
	LineWalker lineWalker = new LineWalker(1d);
	private Class<?> defaultShape = Rectangle.class.getClass();
		
	public LineDrawer(Point start, Point end) {
		super();
		if(start==null || end==null){
			  throw new IllegalArgumentException("Ponto nulo!");
		}	
		this.start = start;
		this.end = end;
	}
	
	public double getLineSize(){
		return Math.sqrt(Math.pow(start.getX(), 2) + Math.pow(end.getY(), 2));
	}
	
	public void setPass(Double pass){
		System.out.println("settingPass to: " + pass);
		lineWalker.setPass(pass);
	}
		
	public LineDrawer (LineDrawer drawerOne, LineDrawer drawerTwo, int ticker){
		this(drawerOne.getPoint(ticker), drawerTwo.getPoint(ticker));
	}
	
	public void draw(Graphics2D g2d, int ticker) throws IllegalArgumentException{
		
		Point point = getPoint(ticker);
		
		if(point!=null){
			g2d.draw(new Line2D.Double(
					new Point2D.Double(start.getX(), start.getY()),
					new Point2D.Double(end.getX(), end.getY())
			));			
		 	g2d.draw(new Rectangle((int)point.getX(),(int)point.getY(),5,5));
		}
	}
	
	public Shape getShape(int ticker){
		Point point = getPoint(ticker);
		return getShape(point);
	}
	
	private Shape getShape(Point point){
		if(defaultShape.equals(Rectangle.class.getClass())){
			return new Rectangle((int)point.getX(),(int)point.getY(),5,5);
		}
		else{
			return null;
		}
	}
	
	public Point getPoint(int ticker){
		Point point = lineWalker.getPoint(start, end, ticker);
		return point;
	}
	
}
