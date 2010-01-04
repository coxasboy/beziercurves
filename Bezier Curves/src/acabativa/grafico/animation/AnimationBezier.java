package acabativa.grafico.animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import acabativa.grafico.drawer.BezierDrawer;
import acabativa.grafico.drawer.Drawer;
import acabativa.grafico.drawer.SceneryDrawer;

public class AnimationBezier extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static final int MAX_WIDHT = 700;
	public static final int MAX_HEIGHT = 700;
	
	Timer timer;
	int ticker = 0;
	Drawer scenarieDrawer = new SceneryDrawer(MAX_WIDHT, MAX_HEIGHT);
	Drawer bezierLineDrawer = null;
	List<Shape> path = new ArrayList<Shape>();
	boolean running = true;
	boolean inFrame = true;
	
	public AnimationBezier() {
		getNewBezierInstance();
		
		timer = new Timer(40, this);
		timer.setInitialDelay(200);
		timer.start();		
	}
	
	private void getNewBezierInstance(){
		Random r = new Random();
		List<Point> points = new ArrayList<Point>();
		
		points.add(new Point(r.nextInt(350), 350+r.nextInt(350)));
		points.add(new Point(r.nextInt(350), r.nextInt(350)));
		points.add(new Point(350+r.nextInt(350), r.nextInt(350)));
		points.add(new Point(350+r.nextInt(350), 350+r.nextInt(350)));
		points.add(new Point(350+r.nextInt(350), 350+r.nextInt(350)));		
		
		Collections.shuffle(points);
		
		bezierLineDrawer = new BezierDrawer(
				points.get(0),
				points.get(1),
				points.get(2),
				points.get(3),
				points.get(4)
				);
		
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		try{
			scenarieDrawer.draw(g2d, ticker);
			bezierLineDrawer.draw(g2d, ticker);
		}
		catch (Exception e) {
			e.printStackTrace();
			path.addAll(((BezierDrawer)bezierLineDrawer).getPath());
			getNewBezierInstance();
			ticker = 0;
			
		}
		
		drawPath(g2d);		
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
		JFrame frame = new JFrame("Curva de Bezier");
		AnimationBezier animation = new AnimationBezier();
		frame.add(animation);
		frame.addMouseListener(animation);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(850, 750);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
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

}