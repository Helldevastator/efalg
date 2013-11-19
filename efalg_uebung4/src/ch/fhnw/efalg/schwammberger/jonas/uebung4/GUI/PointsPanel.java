package ch.fhnw.efalg.schwammberger.jonas.uebung4.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PointsPanel extends JPanel {
	private List<Point> points;
	private Point[] rectangle;

	public PointsPanel() {
		super();
		points = new ArrayList<>(50);
		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickedEvent(e);
			}
		});

	}

	private void mouseClickedEvent(MouseEvent e) {
		points.add(new Point(e.getX(), e.getY()));
		super.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//draw points
		for (Point p : points) {
			g.drawRect(p.x, p.y, 1, 1);
		}

		//draw rectangle
		if (rectangle != null) {
			for (int i = 0; i < 4; i++) {
				Point p1 = rectangle[i];
				Point p2 = rectangle[(i + 1) % 4];
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}

		super.repaint();
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	}

	public List<Point> getPoints() {
		return this.points;
	}

	public void setRectangle(Point[] rectangle) {
		this.rectangle = rectangle;
	}

	public void clear() {
		this.points.clear();
		this.rectangle = null;
		super.repaint();
	}

}
