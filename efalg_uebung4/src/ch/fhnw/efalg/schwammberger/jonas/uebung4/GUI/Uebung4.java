package ch.fhnw.efalg.schwammberger.jonas.uebung4.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.fhnw.efalg.schwammberger.jonas.uebung4.Line;
import ch.fhnw.efalg.schwammberger.jonas.uebung4.SmallestRectangle;

public class Uebung4 {

	private JFrame frmSmallestEnclosingRectangle;
	private PointsPanel clickPlane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Uebung4 window = new Uebung4();
					window.frmSmallestEnclosingRectangle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Uebung4() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmallestEnclosingRectangle = new JFrame();
		frmSmallestEnclosingRectangle.setTitle("Smallest Enclosing Rectangle");
		frmSmallestEnclosingRectangle.setBounds(100, 100, 619, 496);
		frmSmallestEnclosingRectangle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmallestEnclosingRectangle.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel ControlsPanel = new JPanel();
		frmSmallestEnclosingRectangle.getContentPane().add(ControlsPanel, BorderLayout.WEST);
		ControlsPanel.setLayout(new BoxLayout(ControlsPanel, BoxLayout.Y_AXIS));

		JButton btnCalculateRectangle = new JButton("Calculate Rectangle");
		btnCalculateRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SmallestRectangle algo = new SmallestRectangle(clickPlane.getPoints());
				clickPlane.setRectangle(Line.calculateVertices(algo.calculateSmallestRectangle()));
			}
		});
		ControlsPanel.add(btnCalculateRectangle);
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickPlane.clear();
			}
		});
		ControlsPanel.add(btnClear);

		clickPlane = new PointsPanel();
		clickPlane.setBackground(Color.WHITE);

		frmSmallestEnclosingRectangle.getContentPane().add(clickPlane, BorderLayout.CENTER);

		//frame.getContentPane().add(panel, BorderLayout.NORTH);
	}
}
