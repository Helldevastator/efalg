package ch.fhnw.efalg.schwammberger.jonas.uebung4.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Uebung4 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Uebung4 window = new Uebung4();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel ControlsPanel = new JPanel();
		frame.getContentPane().add(ControlsPanel, BorderLayout.WEST);
		ControlsPanel.setLayout(new BoxLayout(ControlsPanel, BoxLayout.Y_AXIS));

		JButton btnCalculateRectangle = new JButton("Calculate Rectangle");
		ControlsPanel.add(btnCalculateRectangle);
		JButton btnClear = new JButton("Clear");
		ControlsPanel.add(btnClear);

		JPanel ClickPlane = new PointsPanel();

		frame.getContentPane().add(ClickPlane, BorderLayout.CENTER);

		//frame.getContentPane().add(panel, BorderLayout.NORTH);
	}
}
