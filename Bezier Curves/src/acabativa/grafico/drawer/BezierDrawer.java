package acabativa.grafico.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import acabativa.music.Player;

public class BezierDrawer implements Drawer {
	
	private static final int MAX_TICKER = 100;

	List<Shape> path = new ArrayList<Shape>();
	List<Drawer> primitives = new ArrayList<Drawer>();
	List<List<Drawer>> listDrawers = new ArrayList<List<Drawer>>();
	Point [] points = null;
	List<Color> pallete = null;
	int lastNote = 0;
	int testCont = 0;
	
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
	Player player = null;
	
	public BezierDrawer(Point ... points){
		this.points = points;		
	}
	
	public BezierDrawer(Player player, Point ... points){
		this.points = points;
		this.player = player;
		player.endNotes();
	}
	
	private void generateList(double bezierCoeficient){
		listDrawers.clear();
		List<Drawer> list = loadPrimitives(points);
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
			
	public List<? extends Shape> getPath() {
		return path;
	}
	
	private List<Drawer> generateSubList(List<Drawer> drawers, double bezierCoeficient){
		int numberOfLines = drawers.size() - 1;
		List<Drawer> ret = new ArrayList<Drawer>();
		for (int i = 0; i < numberOfLines; i++) {
			LineDrawer newGeneration = new LinePointRunnerDrawer(
					(LineDrawer) drawers.get(i),
					(LineDrawer) drawers.get(i+1), bezierCoeficient);
			ret.add(newGeneration);
		}
		return ret;
	}
	
	private List<Drawer> loadPrimitives(Point ... points){
		
		if(primitives==null || primitives.size()==0){
			primitives = new ArrayList<Drawer>();
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

	@SuppressWarnings("unused")
	private void drawList(Graphics2D g2d, int ticker) {
		int cont = 0;
		for (List<Drawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont++%7));
			for (Drawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, ticker);
			}
		}
		g2d.setColor(Color.BLACK);
	}
	
	private void drawList(Graphics2D g2d, double bezierCoeficient) {
		int cont = 0;
		for (List<Drawer> drawers : listDrawers) {
			g2d.setColor(pallete.get(cont%pallete.size()));
			for (Drawer lineDrawer : drawers) {
				lineDrawer.draw(g2d, bezierCoeficient);
			}
			cont++;
		}
		if(player!=null && testCont%5==0){
			LineDrawer lineD = (LineDrawer) listDrawers.get(listDrawers.size()-1).get(0);
			Point p = lineD.getPoint(bezierCoeficient);
			player.stop(lastNote);
			lastNote = ((int)p.getX()/5+(int)p.getY()/5)/2;
			player.play(lastNote);
		}
		testCont++;
		g2d.setColor(Color.BLACK);
	}
	
	
	
}
