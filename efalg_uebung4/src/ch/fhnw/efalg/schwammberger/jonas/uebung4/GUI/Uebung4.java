package ch.fhnw.efalg.schwammberger.jonas.uebung4.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.fhnw.efalg.schwammberger.jonas.uebung4.Line;
import ch.fhnw.efalg.schwammberger.jonas.uebung4.SmallestRectangle;

/**
 * Main class for this application. It is responsible for the GUI.
 * 
 * @author Jon
 * 
 */
public class Uebung4 {

	private JFrame frmSmallestEnclosingRectangle;
	private PointsPanel clickPlane;
	private JButton btnCalculateRectangle;

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
	 * Initialize the contents of the frame. Party generated with Windowbuilder
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

		btnCalculateRectangle = new JButton("Calculate Rectangle");
		btnCalculateRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickPlane.getPointCount() >= 3) {
					clickPlane.setRectangle(Line.calculateVertices(SmallestRectangle.calculate(clickPlane.getPoints())));
				} else {

					JOptionPane.showMessageDialog(frmSmallestEnclosingRectangle, "Needs at least 3 Points for the smallest enclosing Algorithm");
				}
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

	}
}
