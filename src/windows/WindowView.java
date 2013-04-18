package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import library.FunLibrary;

import battle.Battle;
import entities.Enemy;
import entities.Player;

public class WindowView extends JFrame {// view/controller.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Container contentPane;
	private JPanel statScreen;
	private JLabel statLabel;

	private JButton otherFunctionButton;

	private JPanel buttonPanel;
	private JComboBox skillList;
	private JButton progressButton;

	private JPanel outputPanel;
	private JLabel battleLabel;

	private Player p;
	private Enemy e;
	private Battle b;

	public WindowView(Player p)
	{
		WindowView w = this;
		this.p=p;
		b = null;
		contentPane= getContentPane();
		setTitle("RPG TESTING");
		progressButton = new JButton("Fight");
		statScreen = new JPanel();
		buttonPanel = new JPanel();
		outputPanel = new JPanel();
		contentPane.add(statScreen, BorderLayout.WEST);
		statLabel=new JLabel(FunLibrary.htmlFormatString(p.toString()));
		statScreen.add(statLabel);
		
		contentPane.add(outputPanel, BorderLayout.CENTER);
		outputPanel.setBackground(Color.WHITE);
		outputPanel.setPreferredSize(new Dimension(600, 600));
		battleLabel = new JLabel();
		outputPanel.add(battleLabel);
		otherFunctionButton = new JButton("Start Battle");
		outputPanel.add(otherFunctionButton);
		otherFunctionButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(WindowView.this.p.isDead())
				{
					battleLabel.setText("You shouldn't be fighting");
				}
				else{
				WindowView.this.startBattle(new Enemy("F.O.E", 13));
				}
			}
		}
		);
		
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		String[] skills = {"no skill", "skill 1", "skill 2"};
		skillList = new JComboBox(skills);
		buttonPanel.add(skillList);
		skillList.setVisible(false);
		buttonPanel.add(progressButton);
		progressButton.setVisible(false);
		progressButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				WindowView.this.passTurn();
				
			}
		
		});
		pack();
	}
	public void startBattle(Enemy enemy)
	{
		e=enemy;
		b = new Battle(p, e);
		progressButton.setVisible(true);
		skillList.setVisible(true); 
		otherFunctionButton.setVisible(false);
		passTurn();
	}
	public void passTurn()
	{
		boolean battleContinues = b.takeTurn();
		statLabel.setText(FunLibrary.htmlFormatString(p.toString()));
		battleLabel.setText(FunLibrary.htmlFormatString(b.getOutput()));
		if(!battleContinues)
		{
			progressButton.setVisible(false);
			skillList.setVisible(false);
			otherFunctionButton.setVisible(true);
		}
	}
	public void refresh() {
		statLabel.setText(FunLibrary.htmlFormatString(p.toString()));
		
	}
}
