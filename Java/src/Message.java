

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
		
		JButton btnOk = new JButton("Ok");
		btnOk.setVisible(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.getRootPane().setDefaultButton(btnOk);
		btnOk.setBounds(137, 157, 89, 23);
		frame.getContentPane().add(btnOk);
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegressionGUI.yesNoFlag = true;
				frame.dispose();
			}
		});
		btnYes.setVisible(false);
		btnYes.setBounds(64, 178, 117, 29);
		frame.getContentPane().add(btnYes);
		
		JButton btnNo = new JButton("no");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegressionGUI.yesNoFlag = false;
				frame.dispose();
			}
		});
		btnNo.setVisible(false);
		btnNo.setBounds(193, 178, 117, 29);
		frame.getContentPane().add(btnNo);
		
		if(exitStatus == 0)	// 0 to dispose, 1 to close
			{
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				btnNo.setVisible(false);
				btnYes.setVisible(false);
				btnOk.setVisible(true);
			}
		else if(exitStatus == 1)
			{
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				btnNo.setVisible(false);
				btnYes.setVisible(false);
				btnOk.setVisible(true);
			}
		else if(exitStatus == 2) // will change the yesNo flag of the calling frame class
		{
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			btnNo.setVisible(true);
			btnYes.setVisible(true);
			btnOk.setVisible(false);
		}
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
