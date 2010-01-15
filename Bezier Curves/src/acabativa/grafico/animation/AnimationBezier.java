package acabativa.grafico.animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import acabativa.grafico.drawer.BezierDrawer;
import acabativa.grafico.drawer.Drawer;
import acabativa.grafico.drawer.SceneryDrawer;

public class AnimationBezier extends JPanel implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int MAX_WIDHT = 500;
	public static final int MAX_HEIGHT = 500;
	
	Timer timer;
	int ticker = 0;
	Drawer scenarieDrawer = new SceneryDrawer(MAX_WIDHT, MAX_HEIGHT);
	Drawer bezierLineDrawer = null;
	List<Shape> path = new ArrayList<Shape>();
	boolean running = true;
	boolean inFrame = true;
	
	int timeFrame = 30;
	int quadrant = 1;
	int numberOfPoints = 6;
	
	public AnimationBezier() {
		getNewBezierInstance();
		
		timer = new Timer(getTimeToWait(), this);
		timer.setInitialDelay(200);
		timer.start();		
	}
	
	private int getTimeToWait(){
		return 1000/timeFrame;
	}
	
	private void getNewBezierInstance(){
		List<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i < numberOfPoints; i++) {
			points.add(getNewPoint());
		}
		
		Collections.shuffle(points);
		Point[] pointsArray = new Point[points.size()];  
		bezierLineDrawer = new BezierDrawer(points.toArray(pointsArray));
	}
	
	private void resetSpeed(){
		timer.stop();
		timer = null;
		timer = new Timer(getTimeToWait(), this);
		timer.setInitialDelay(200);
		timer.start();	
	}
	
	private Point getNewPoint(){
		Random r = new Random();
		int x = getQuadrantX(quadrant)+ r.nextInt(MAX_WIDHT/2);
		int y = getQuadrantY(quadrant)+ r.nextInt(MAX_HEIGHT/2);
		Point ret = new Point(x, y);
		quadrant++;
		if(quadrant>4){
			quadrant = 0;
		}
		return ret;
	}
	
	private int getQuadrantX(int quadrant){
		switch (quadrant) {
		case 1:
			return 0;
		case 2:
			return MAX_WIDHT/2;
		case 3:
			return 0;
		case 4:
			return MAX_WIDHT/2;
		}
		return 0;
	}
	
	private int getQuadrantY(int quadrant){
		switch (quadrant) {
		case 1:
			return 0;
		case 2:
			return 0;
		case 3:
			return MAX_HEIGHT/2;
		case 4:
			return MAX_HEIGHT/2;
		}
		return 0;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		try{
			if(ticker==101){
				refresh();
			}
			scenarieDrawer.draw(g2d, ticker);
			bezierLineDrawer.draw(g2d, ticker);
		}
		catch (Exception e) {
			e.printStackTrace();
			refresh();
		}
		drawPath(g2d);		
	}	
	
	private void refresh(){
		path.addAll(((BezierDrawer)bezierLineDrawer).getPath());
		getNewBezierInstance();
		ticker = 0;
	}

	public void drawPath(Graphics2D g2d) {
		for (Shape shape : path) {
			g2d.draw(shape);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(running){
			ticker += 1;
			repaint();
		}
	}

	public static void main(String[] args) {
		JFrame frame = getJFrame();
		frame.setVisible(true);		
	}
	
	public static JFrame getJFrame(){
		JFrame frame = new JFrame("Curva de Bezier");
		AnimationBezier animation = new AnimationBezier();
		frame.add(animation);
		frame.addMouseListener(animation);
		frame.addKeyListener(animation);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAX_WIDHT+150, MAX_HEIGHT+37);
		frame.setLocationRelativeTo(null);
		return frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(inFrame){
			running = !running;
			System.out.println(running?"GO!":"STOP!");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		inFrame = true;
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		inFrame = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == ('+')){
			numberOfPoints = numberOfPoints + 1;
			System.out.println("Numero de pontos: " + numberOfPoints);
		}
		if(e.getKeyChar() == ('-')){
			numberOfPoints = numberOfPoints - 1;
			System.out.println("Numero de pontos: " + numberOfPoints);
		}	
		if(e.getKeyChar() == (' ')){
			path.clear();
			System.out.println("Limpando curvas");
		}	
		if(e.getKeyChar() == ('x')){
			running = true;
			System.out.println(running?"GO!":"STOP!");
		}	
		if(e.getKeyChar() == ('c')){
			running = false;
			System.out.println(running?"GO!":"STOP!");
		}
		if(e.getKeyChar() == ('v')){
			timeFrame = timeFrame + 10;
			resetSpeed();
			System.out.println("Nova velocidade: " + timeFrame);
		}
		if(e.getKeyChar() == ('z')){
			timeFrame = timeFrame - 10;
			resetSpeed();
			System.out.println("Nova velocidade: " + timeFrame);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}