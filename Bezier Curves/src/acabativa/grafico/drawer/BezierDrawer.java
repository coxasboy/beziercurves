package acabativa.grafico.drawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BezierDrawer implements Drawer {

	List<Rectangle> path = new ArrayList<Rectangle>();
	List<LineDrawer> primitives = new ArrayList<LineDrawer>();
	List<List<LineDrawer>> listDrawers = new ArrayList<List<LineDrawer>>();
	Point [] points = null;

	int maxWidht = 0;
	int maxHeight = 0;
	
	public BezierDrawer(Point ... points){
		this.points = points;		
	}
	
	private void generateList(int ticker){
		listDrawers.clear();
		List<LineDrawer> list = loadPrimitives(points);
		calibratePrimitives();
		listDrawers.add(list);
		while(true){
			list = generateSubList(list, ticker);
			if(list==null || list.size()==0){
				break;
			}
			else{
				listDrawers.add(list);
			}
		}
	}
	
	private double getPassToThisGeneration(int generation){
		double passPrimitives = getBigger(primitives).getPass();
		for (int i = 0; i < generation; i++) {
			passPrimitives = passPrimitives/(1.5);
		}
		return passPrimitives;
	}
	
	private void calibratePrimitives(){
		LineDrawer smaller = getSmaller(primitives);
		for (LineDrawer lineDrawer : primitives) {
			lineDrawer.setPass(lineDrawer.getLineSize()/smaller.getLineSize());
		}
	}
	
	private LineDrawer getSmaller(List<LineDrawer> list){
		LineDrawer smaller = null;
		for (LineDrawer lineDrawer : list) {
			System.out.println("Item: " + lineDrawer.getLineSize());
			if(smaller==null || lineDrawer.getLineSize()<smaller.getLineSize()){
				smaller = lineDrawer;
				System.out.println("Smaller: " + smaller.getLineSize());
			}
		}
		return smaller;
	}
	
	private LineDrawer getBigger(List<LineDrawer> list){
		LineDrawer bigger = null;
		for (LineDrawer lineDrawer : list) {
			if(bigger==null || lineDrawer.getLineSize()>bigger.getLineSize()){
				bigger = lineDrawer;
			}
		}
		return bigger;
	}
	
	public List<Rectangle> getPath() {
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
	
	private List<LineDrawer> loadPrimitives(Point ... points){
		
		if(primitives==null || primitives.size()==0){
			primitives = new ArrayList<LineDrawer>();
			int numberOfLines = points.length - 1;
			for (int i = 0; i < numberOfLines; i++) {
				primitives.add(new LineDrawer(points[i], points[i+1]));
			}
		}
		
		System.out.println();
		return primitives;
	}

	@Override
	public void draw(Graphics2D g2d, int ticker)
			throws IllegalArgumentException {
		generateList(ticker);

		drawList(g2d, ticker);

		List<LineDrawer> lastList = (List<LineDrawer>) getLast(listDrawers);
		LineDrawer lastDrawer = (LineDrawer) getLast(lastList);
		
		path.add((Rectangle) lastDrawer.getShape(ticker));

		drawPath(g2d);
	}
	
	private Object getLast(List<? extends Object> list){
		return list.get(list.size()-1);
	}

	private void drawPath(Graphics2D g2d) {
		for (Rectangle rectangle : path) {
			g2d.draw(rectangle);
		}
	}

	private void drawList(Graphics2D g2d, int ticker) {
		for (List<LineDrawer> drawers : listDrawers) {
			for (LineDrawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, ticker);
			}
		}
	}

}
