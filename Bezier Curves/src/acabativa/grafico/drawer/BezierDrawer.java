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
	List<LinePointRunnerDrawer> primitives = new ArrayList<LinePointRunnerDrawer>();
	List<List<LinePointRunnerDrawer>> listDrawers = new ArrayList<List<LinePointRunnerDrawer>>();
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
		List<LinePointRunnerDrawer> list = loadPrimitives(points);
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
	private List<LinePointRunnerDrawer> getUniqueList(List<List<LinePointRunnerDrawer>> listOfLists){
		List<LinePointRunnerDrawer> ret = new ArrayList<LinePointRunnerDrawer>();
		for (List<LinePointRunnerDrawer> list : listOfLists) {
			ret.addAll(list);
		}
		return ret;
	}
	
	@SuppressWarnings("unused")
	private void calibrate(List<LinePointRunnerDrawer> drawers){
		LinePointRunnerDrawer smaller = getSmaller(drawers);
		for (LinePointRunnerDrawer lineDrawer : drawers) {
			lineDrawer.setPass(lineDrawer.getLineSize()/smaller.getLineSize());
		}
	}
	
	private LinePointRunnerDrawer getSmaller(List<LinePointRunnerDrawer> list){
		LinePointRunnerDrawer smaller = null;
		for (LinePointRunnerDrawer lineDrawer : list) {
			if(smaller==null || lineDrawer.getLineSize()<smaller.getLineSize()){
				smaller = lineDrawer;
			}
		}
		return smaller;
	}
	
	@SuppressWarnings("unused")
	private LinePointRunnerDrawer getBigger(List<LinePointRunnerDrawer> list){
		LinePointRunnerDrawer bigger = null;
		for (LinePointRunnerDrawer lineDrawer : list) {
			if(bigger==null || lineDrawer.getLineSize()>bigger.getLineSize()){
				bigger = lineDrawer;
			}
		}
		return bigger;
	}
	
	public List<? extends Shape> getPath() {
		return path;
	}
	
	
			
//	private List<LineDrawer> generateSubList(List<LineDrawer> drawers, int ticker){
//		int numberOfLines = drawers.size() - 1;
//		List<LineDrawer> ret = new ArrayList<LineDrawer>();
//		for (int i = 0; i < numberOfLines; i++) {
//			LineDrawer newGeneration = new LineDrawer(
//					(LineDrawer) drawers.get(i),
//					(LineDrawer) drawers.get(i+1), ticker);
//			ret.add(newGeneration);
//		}
//		return ret;
//	}
	
	private List<LinePointRunnerDrawer> generateSubList(List<LinePointRunnerDrawer> drawers, double bezierCoeficient){
		int numberOfLines = drawers.size() - 1;
		List<LinePointRunnerDrawer> ret = new ArrayList<LinePointRunnerDrawer>();
		for (int i = 0; i < numberOfLines; i++) {
			LinePointRunnerDrawer newGeneration = new LinePointRunnerDrawer(
					(LinePointRunnerDrawer) drawers.get(i),
					(LinePointRunnerDrawer) drawers.get(i+1), bezierCoeficient);
			ret.add(newGeneration);
		}
		return ret;
	}
	
	private List<LinePointRunnerDrawer> loadPrimitives(Point ... points){
		
		if(primitives==null || primitives.size()==0){
			primitives = new ArrayList<LinePointRunnerDrawer>();
			int numberOfLines = points.length - 1;
			for (int i = 0; i < numberOfLines; i++) {
				primitives.add(new LinePointRunnerDrawer(points[i], points[i+1]));
			}
		}
		return primitives;
	}


	@Override
	public void draw(Graphics2D g2d, int ticker)
			throws IllegalArgumentException {
		draw(g2d,((double)ticker/(double)MAX_TICKER));
	}
	
	@SuppressWarnings("unchecked")
	public void draw(Graphics2D g2d, double bezierCoeficient) throws IllegalArgumentException {
		generateList(bezierCoeficient);
		
		drawList(g2d, bezierCoeficient);
		
		List<LinePointRunnerDrawer> lastList = (List<LinePointRunnerDrawer>) getLast(listDrawers);
		LinePointRunnerDrawer lastDrawer = (LinePointRunnerDrawer) getLast(lastList);
		
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

	@SuppressWarnings("unused")
	private void drawList(Graphics2D g2d, int ticker) {
		int cont = 0;
		for (List<LinePointRunnerDrawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont++%7));
			for (LinePointRunnerDrawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, ticker);
			}
		}
		g2d.setColor(Color.BLACK);
	}
	
	private void drawList(Graphics2D g2d, double bezierCoeficient) {
		int cont = 0;
		for (List<LinePointRunnerDrawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont++%pallete.size()));
			for (LinePointRunnerDrawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, bezierCoeficient);
			}
		}
		g2d.setColor(Color.BLACK);
	}

}
