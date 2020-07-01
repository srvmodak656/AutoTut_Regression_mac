

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Message {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Message window = new Message("", 0);
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
	public Message(String Message, int exitStatus) {
		initialize(Message, exitStatus);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize(String Message, int exitStatus) {
		frame = new JFrame();
		frame.setBounds(100, 100, 378, 235);
		if(exitStatus == 0)
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea messageText = new JTextArea();
		messageText.setWrapStyleWord(true);
		messageText.setLineWrap(true);
		messageText.setAlignmentX(SwingConstants.CENTER);
		messageText.setText(Message);
		messageText.setEditable(false);
		messageText.setBounds(38, 11, 286, 135);
		frame.getContentPane().add(messageText);
		frame.setLocationRelativeTo(null);
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.getRootPane().setDefaultButton(btnOk);
		btnOk.setBounds(137, 157, 89, 23);
		frame.getContentPane().add(btnOk);
	}
	
	public static void setMessage(String Message, int exitStatus) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Message window = new Message(Message, exitStatus);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
