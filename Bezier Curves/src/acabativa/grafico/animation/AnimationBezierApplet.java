package acabativa.grafico.animation;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import acabativa.grafico.drawer.BezierDrawer;
import acabativa.grafico.drawer.Drawer;
import acabativa.grafico.drawer.SceneryDrawer;

public class AnimationBezierApplet extends JApplet implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	private void createGUI() {
		AnimationBezier animation = new AnimationBezier();
		this.setSize(AnimationBezier.MAX_WIDHT, AnimationBezier.MAX_HEIGHT);
		setContentPane(animation);
	}

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't successfully complete");
		}
	}

}
