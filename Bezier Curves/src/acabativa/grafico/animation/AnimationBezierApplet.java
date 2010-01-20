package acabativa.grafico.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class AnimationBezierApplet extends JApplet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
