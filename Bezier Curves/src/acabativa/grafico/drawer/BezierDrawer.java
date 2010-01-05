package acabativa.grafico.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class BezierDrawer implements Drawer {
	
	private static final int MAX_TICKER = 100;

	List<Shape> path = new ArrayList<Shape>();
	List<LineDrawer> primitives = new ArrayList<LineDrawer>();
	List<List<LineDrawer>> listDrawers = new ArrayList<List<LineDrawer>>();
	Point [] points = null;
	List<Color> pallete = null;
	
	{
		pallete = new ArrayList<Color>();
		pallete.add(Color.GREEN);
		pallete.add(Color.CYAN);
		pallete.add(Color.BLUE);
		pallete.add(Color.RED);
		pallete.add(Color.MAGENTA);
		pallete.add(Color.BLACK);
		pallete.add(Color.ORANGE);
	}

	int maxWidht = 0;
	int maxHeight = 0;
	
	public BezierDrawer(Point ... points){
		this.points = points;		
	}
	
	private void generateList(double bezierCoeficient){
		listDrawers.clear();
		List<LineDrawer> list = loadPrimitives(points);
		listDrawers.add(list);
		while(true){
			list = generateSubList(list, bezierCoeficient);
			if(list==null || list.size()==0){
				break;
			}
			else{
				listDrawers.add(list);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private List<LineDrawer> getUniqueList(List<List<LineDrawer>> listOfLists){
		List<LineDrawer> ret = new ArrayList<LineDrawer>();
		for (List<LineDrawer> list : listOfLists) {
			ret.addAll(list);
		}
		return ret;
	}
	
	private void calibrate(List<LineDrawer> drawers){
		LineDrawer smaller = getSmaller(drawers);
		for (LineDrawer lineDrawer : drawers) {
			lineDrawer.setPass(lineDrawer.getLineSize()/smaller.getLineSize());
		}
	}
	
	private LineDrawer getSmaller(List<LineDrawer> list){
		LineDrawer smaller = null;
		for (LineDrawer lineDrawer : list) {
			if(smaller==null || lineDrawer.getLineSize()<smaller.getLineSize()){
				smaller = lineDrawer;
			}
		}
		return smaller;
	}
	
	@SuppressWarnings("unused")
	private LineDrawer getBigger(List<LineDrawer> list){
		LineDrawer bigger = null;
		for (LineDrawer lineDrawer : list) {
			if(bigger==null || lineDrawer.getLineSize()>bigger.getLineSize()){
				bigger = lineDrawer;
			}
		}
		return bigger;
	}
	
	public List<? extends Shape> getPath() {
		return path;
	}
	
	
			
	private List<LineDrawer> generateSubList(List<LineDrawer> drawers, int ticker){
		int numberOfLines = drawers.size() - 1;
		List<LineDrawer> ret = new ArrayList<LineDrawer>();
		for (int i = 0; i < numberOfLines; i++) {
			LineDrawer newGeneration = new LineDrawer(
					(LineDrawer) drawers.get(i),
					(LineDrawer) drawers.get(i+1), ticker);
			ret.add(newGeneration);
		}
		return ret;
	}
	
	private List<LineDrawer> generateSubList(List<LineDrawer> drawers, double bezierCoeficient){
		int numberOfLines = drawers.size() - 1;
		List<LineDrawer> ret = new ArrayList<LineDrawer>();
		for (int i = 0; i < numberOfLines; i++) {
			LineDrawer newGeneration = new LineDrawer(
					(LineDrawer) drawers.get(i),
					(LineDrawer) drawers.get(i+1), bezierCoeficient);
			ret.add(newGeneration);
		}
		return ret;
	}
	
	private List<LineDrawer> loadPrimitives(Point ... points){
		
		if(primitives==null || primitives.size()==0){
			primitives = new ArrayList<LineDrawer>();
			int numberOfLines = points.length - 1;
			for (int i = 0; i < numberOfLines; i++) {
				primitives.add(new LineDrawer(points[i], points[i+1]));
			}
		}
		return primitives;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void draw(Graphics2D g2d, int ticker)
			throws IllegalArgumentException {
		draw(g2d,((double)ticker/(double)MAX_TICKER));
		
//		generateList(ticker);
//
//		drawList(g2d, ticker);
//
//		List<LineDrawer> lastList = (List<LineDrawer>) getLast(listDrawers);
//		LineDrawer lastDrawer = (LineDrawer) getLast(lastList);
//		
//		path.add(lastDrawer.getShape(ticker));
//
//		drawPath(g2d);
	}
	
	public void draw(Graphics2D g2d, double bezierCoeficient) throws IllegalArgumentException {
		generateList(bezierCoeficient);
		
		drawList(g2d, bezierCoeficient);
		
		List<LineDrawer> lastList = (List<LineDrawer>) getLast(listDrawers);
		LineDrawer lastDrawer = (LineDrawer) getLast(lastList);
		
		path.add(lastDrawer.getShape(bezierCoeficient));
		
		drawPath(g2d);
	}
	
	private Object getLast(List<? extends Object> list){
		return list.get(list.size()-1);
	}

	private void drawPath(Graphics2D g2d) {
		for (Shape shape : path) {
			g2d.draw(shape);
		}
	}

	private void drawList(Graphics2D g2d, int ticker) {
		int cont = 0;
		for (List<LineDrawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont++%7));
			for (LineDrawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, ticker);
			}
		}
		g2d.setColor(Color.BLACK);
	}
	
	private void drawList(Graphics2D g2d, double bezierCoeficient) {
		int cont = 0;
		for (List<LineDrawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont++%pallete.size()));
			for (LineDrawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, bezierCoeficient);
			}
		}
		g2d.setColor(Color.BLACK);
	}

}
