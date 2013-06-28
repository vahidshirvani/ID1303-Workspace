import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;


public class Graphic extends JFrame{

	private static final long serialVersionUID = 1L;
	private JRadioButton[] radio = new JRadioButton[2];
	private ImageIcon[] icon = new ImageIcon[4];
	private JButton button;
	private JProgressBar bar;
	private Recorder recorder;
	private Player player;
	
	public Graphic(Recorder rec, Player play) {
	
		String[] iconNames = {
				"record.png", "stop.png", 
				"play.png", "pause.png"};
		for(int i = 0; i < iconNames.length; i++)
			icon[i] = new ImageIcon(
					this.getClass().getResource(
							iconNames[i]));
		
		radio[0] = new JRadioButton("Recorder");
		radio[1] = new JRadioButton("Player");
		ButtonGroup group = new ButtonGroup();
		group.add(radio[0]);
		group.add(radio[1]);
		JPanel top = new JPanel(new GridLayout(1, 2));
		top.add(radio[0]);
		top.add(radio[1]);
		radio[0].setSelected(true);
		
		button = new JButton(icon[0]);
		button.setBackground(Color.WHITE);
		bar = new JProgressBar(0, 100);
		
		Listener listener = new Listener();
		radio[0].addActionListener(listener);
		radio[1].addActionListener(listener);
		button.addActionListener(listener);
		
		this.setTitle("IO Sound");
		this.setLayout(new BorderLayout());
		this.setBounds(200, 200, 300, 100);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(top, BorderLayout.NORTH);
		this.add(button, BorderLayout.WEST);
		this.add(bar, BorderLayout.CENTER);
		this.recorder = rec;
		this.player = play;
	}
	
	private class Listener implements ActionListener{
		boolean isSelected = false;
		public void actionPerformed(ActionEvent event){
			Object source = event.getSource();
			
			if(source == button && radio[0].isSelected() && 
					!isSelected){
				
				button.setIcon(icon[1]);
				radio[0].setEnabled(false);
				radio[1].setEnabled(false);
				isSelected = true;
				recorder.startRecording();
				
			}else if(source == button && radio[0].isSelected() && 
					isSelected){
				
				button.setIcon(icon[0]);
				radio[0].setEnabled(true);
				radio[1].setEnabled(true);
				isSelected = false;
				// TODO recorder.interrupt();
				// TODO file choser
				recorder.stopRecording();
				
			}else if(source == button && radio[1].isSelected() && 
					!isSelected){
				
				button.setIcon(icon[3]);
				radio[0].setEnabled(false);
				radio[1].setEnabled(false);
				isSelected = true;
				player.startplay();
				// TODO Filechoser
				//player.start();
				
			}else if(source == button && radio[1].isSelected() && 
					isSelected){
				
				button.setIcon(icon[2]);
				radio[0].setEnabled(true);
				radio[1].setEnabled(true);
				isSelected = false;
				player.stopPlay();
				// TODO player.interrupt();
				
			}else if(source == radio[0]){
				button.setIcon(icon[0]);
			}else if(source == radio[1]){
				button.setIcon(icon[2]);
			}else{
				
			}
		}
	}
}
