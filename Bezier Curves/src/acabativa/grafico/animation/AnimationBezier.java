package acabativa.grafico.animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import acabativa.grafico.drawer.BezierDrawer;
import acabativa.grafico.drawer.Drawer;
import acabativa.grafico.drawer.SceneryDrawer;

public class AnimationBezier extends JPanel implements ActionListener, MouseListener {

	public static final int MAX_WIDHT = 700;
	public static final int MAX_HEIGHT = 700;
	
	Timer timer;
	int ticker = 0;
	Drawer scenarieDrawer = new SceneryDrawer(MAX_WIDHT, MAX_HEIGHT);
	Drawer bezierLineDrawer = null;
	List<Rectangle> path = new ArrayList<Rectangle>();
	boolean running = true;
	boolean inFrame = true;
	
	public AnimationBezier() {
		getNewBezierInstance();
		
		timer = new Timer(75, this);
		timer.setInitialDelay(200);
		timer.start();		
	}
	
	private void getNewBezierInstance(){
		Random r = new Random();
		
		Point a = new Point(r.nextInt(350), 350+r.nextInt(350));
		Point b = new Point(r.nextInt(350), r.nextInt(350));
		Point c = new Point(350+r.nextInt(350), r.nextInt(350));
		Point d = new Point(350+r.nextInt(350), 350+r.nextInt(350));
		
		bezierLineDrawer = new BezierDrawer(a,b,c,d);
		
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
		for (Rectangle rectangle : path) {
			g2d.draw(rectangle);
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