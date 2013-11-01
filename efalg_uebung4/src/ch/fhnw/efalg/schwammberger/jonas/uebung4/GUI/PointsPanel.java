package ch.fhnw.efalg.schwammberger.jonas.uebung4.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import ch.fhnw.efalg.schwammberger.jonas.uebung4.Line;

public class PointsPanel extends JPanel {
	private List<Point> points;
	private Point[] rectangle;

	public PointsPanel() {
		super();
		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	}

	public List<Point> getPoints() {
		return this.points;
	}

	public void setRectangle(Line[] rectangle) {

	}

	public void clear() {
		this.points.clear();
		this.rectangle = null;
	}

}
