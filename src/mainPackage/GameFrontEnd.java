package mainPackage;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameFrontEnd {

	private Army aiArmy;
	private Army turn;
	private GameEvent gameEvent = null;

	private JFrame frame;
	private JTextField turnTextField;
	private JTextField statusTextField;

	private Army attackingArmy = null;
	private String attackingRegion = null;
	private int[] attackingDice = null;
	private int attackingDiceIndex = 0;

	private Army defendingArmy = null;
	private String defendingRegion = null;
	private int[] defendingDice = null;
	private int defendingDiceIndex = 0;

	private String statusText = "Status : null -------------------> null";

	private ArrayList<Pair<Integer, Boolean>> diceButtons = new ArrayList<Pair<Integer, Boolean>>();
	private int diceButtonsRow = 2;
	private int nrRedDice = -1;
	private int nrBlueDice = -1;

	private ArrayList<JButton> myRegionsButtons = new ArrayList<JButton>();
	private ArrayList<JButton> neighboursButtons = new ArrayList<JButton>();

	public void initializeFrame(Army turn, HashMap<RegionName, GameRegion> graph, Army aiArmy) {

		initializeGameVariables();

		this.aiArmy = aiArmy;
		this.turn = turn;

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 782, 0 };
		gridBagLayout.rowHeights = new int[] { 22, 43, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		// initialise frame

		turnTextField = new JTextField(new StringBuilder("TURN : ").append(turn).toString());
		turnTextField.setEditable(false);
		turnTextField.setColumns(10);
		GridBagConstraints gbc_TurnTextField = new GridBagConstraints();
		gbc_TurnTextField.anchor = GridBagConstraints.NORTH;
		gbc_TurnTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_TurnTextField.insets = new Insets(0, 0, 5, 0);
		gbc_TurnTextField.gridx = 0;
		gbc_TurnTextField.gridy = 0;
		frame.getContentPane().add(turnTextField, gbc_TurnTextField);
		// initialise turn text field

		statusTextField = new JTextField(statusText);
		statusTextField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(statusTextField, gbc_textField);
		statusTextField.setColumns(10);
		// initialise status text field

		setNextTurnButton();
		setMyRegionsButtons(graph);
		
		this.frame.setVisible(true);
	}

	private void initializeGameVariables() {

		this.turn = null;
		this.attackingArmy = null;
		this.attackingRegion = null;
		this.attackingDice = null;
		this.attackingDiceIndex = 0;

		this.defendingArmy = null;
		this.defendingRegion = null;
		this.defendingDice = null;
		this.defendingDiceIndex = 0;

		this.diceButtons = new ArrayList<Pair<Integer, Boolean>>();
		this.diceButtonsRow = 2;
		this.nrRedDice = -1;
		this.nrBlueDice = -1;
	}

	public void updateFrame(int mode, HashMap<RegionName, GameRegion> graph) {

		switch (mode) {

		case 0:
			setNextTurnButton();
			setMyRegionsButtons(graph);
			break;

		case 1:

			setNextTurnButton();
			
			for (JButton currentMyRegionButton : this.myRegionsButtons) {
				currentMyRegionButton.setEnabled(false);
			} // disable all my regions JButtons

			setNeighbourRegions(graph);
			break;

		case 2:

			for (JButton currentNeighbourButton : this.neighboursButtons) {
				currentNeighbourButton.setEnabled(false);
			} // disable all neighbour JButtons
			
			frame.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// UNIMPLEMENTED
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// UNIMPLEMENTED
				}

				@Override
				public void keyPressed(KeyEvent e) {

					if (nrRedDice > 0) {

						nrRedDice--;
						setNewButton(e.getExtendedKeyCode(), true);
						diceButtons.add(new Pair<Integer, Boolean>(e.getExtendedKeyCode() - 48, true));

					} else if (nrBlueDice > 0) {

						nrBlueDice--;
						setNewButton(e.getExtendedKeyCode(), false);
						diceButtons.add(new Pair<Integer, Boolean>(e.getExtendedKeyCode() - 48, false));

					} else if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {

						gameEvent = new GameEvent(RegionName.valueOf(attackingRegion), attackingArmy,
								RegionName.valueOf(defendingRegion), defendingArmy, attackingDice, defendingDice);
						frame.dispose();

					}
				}
			});
			break;

		default:
			break;
		}

		frame.invalidate();
		frame.validate();
		frame.repaint();

		frame.setFocusable(true);
		// set the frame focusable, in order to be able to listen to keys
	}

	private void setMyRegionsButtons(HashMap<RegionName, GameRegion> graph) {

		int rowIndex = 3;
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! do not display regions which are surrounded only by their army's territories
		for (GameRegion currentRegion : graph.values()) {

			if (currentRegion.occupyingArmy == this.turn && currentRegion.nrUnits > 1) {
				// display only the buttons of regions which belong to this.turn and which
				// have at least 2 units on them (they can attack)
				
				String buttonText = new StringBuilder(currentRegion.regionName.toString()).append(' ').append(graph.get(currentRegion.regionName).nrUnits).toString();
				
				JButton newButton = new JButton(buttonText);
				newButton.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.anchor = GridBagConstraints.WEST;
				gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
				gbc_btnNewButton.gridx = 0;
				gbc_btnNewButton.gridy = rowIndex;
				rowIndex += 2;
				frame.getContentPane().add(newButton, gbc_btnNewButton);

				newButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						String [] buttonText = newButton.getText().split(" ");
						
						attackingRegion = buttonText[0];
						attackingArmy = graph.get(RegionName.valueOf(attackingRegion)).occupyingArmy;
						computeStatusTextField();
						updateFrame(1, graph);
					}
				});

				this.myRegionsButtons.add(newButton);
			}
		}
	}

	private void setNeighbourRegions(HashMap<RegionName, GameRegion> graph) {

		RegionName chosenRegionEnum = RegionName.valueOf(attackingRegion);

		int rowIndex = 3;

		for (RegionName currentNeighbourRegion : graph.get(chosenRegionEnum).neighbours) {

			if (graph.get(currentNeighbourRegion).occupyingArmy != this.turn) {

				String buttonText = new StringBuilder(currentNeighbourRegion.toString()).append(' ').append(graph.get(currentNeighbourRegion).nrUnits).toString();
				
				JButton newButton = new JButton(buttonText);
				newButton.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.anchor = GridBagConstraints.EAST;
				gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
				gbc_btnNewButton.gridx = 0;
				gbc_btnNewButton.gridy = rowIndex;
				rowIndex += 2;
				frame.getContentPane().add(newButton, gbc_btnNewButton);

				newButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						String [] buttonText = newButton.getText().split(" ");
						defendingRegion = buttonText[0];
						defendingArmy = graph.get(RegionName.valueOf(defendingRegion)).occupyingArmy;
						computeStatusTextField();
						computeNrOfDice(graph);
						updateFrame(2, graph);
					}
				});
				this.neighboursButtons.add(newButton);
			}
		}
	}

	private void computeStatusTextField() {

		if (attackingRegion == null && defendingRegion == null) {
			this.statusText = "null -------------------> null";
		} else if (attackingRegion != null && defendingRegion == null) {
			this.statusText = new StringBuilder("Status : ").append(attackingRegion)
					.append(" -------------------> null").toString();
		} else {
			this.statusText = new StringBuilder("Status : ").append(attackingRegion).append(" -------------------> ")
					.append(defendingRegion).toString();
		}

		this.statusTextField.setText(this.statusText);
	}

	private void computeNrOfDice(HashMap<RegionName, GameRegion> graph) {

		if (graph.get(RegionName.valueOf(attackingRegion)).nrUnits == 2) {
			nrRedDice = 2;
		} else {
			nrRedDice = 3;
		}

		if (graph.get(RegionName.valueOf(defendingRegion)).nrUnits == 1) {
			nrBlueDice = 1;
		} else {
			nrBlueDice = 2;
		}

		this.attackingDice = new int[nrRedDice];
		this.defendingDice = new int[nrBlueDice];
	}

	private void setNewButton(int keyCode, boolean color) {

		JButton newButton = new JButton(String.valueOf(keyCode - 48));

		if (color) {
			newButton.setBackground(Color.RED);
			attackingDice[attackingDiceIndex++] = keyCode - 48;
		} else {
			newButton.setBackground(Color.BLUE);
			defendingDice[defendingDiceIndex++] = keyCode - 48;
		}

		newButton.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.CENTER;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = diceButtonsRow;
		diceButtonsRow += 2;
		frame.getContentPane().add(newButton, gbc_btnNewButton);

		frame.invalidate();
		frame.validate();
		frame.repaint();
		frame.setFocusable(true);
		// set the frame focusable, in order to be able to listen to keys
	}

	public GameEvent getGameEvent() {

//		if (this.gameEvent != null) {
//			
//			GameEvent temp = this.gameEvent;
//			this.gameEvent = null;
//			return temp;
//		} // once we returned a not null gameEvent, we put the gameEvent on null
//
//		return null;

		return this.gameEvent;
	}

	public void setGameEventToNull() {

		this.gameEvent = null;
	}
	
	private void setNextTurnButton() {
		
		JButton nextTurnButton = new JButton("Next Turn");
		nextTurnButton.setBackground(Color.GREEN);
		nextTurnButton.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		
		nextTurnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setEnabled(false);
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(nextTurnButton, gbc_btnNewButton);
	}

	public boolean frameIsEnabled() {
		return this.frame.isEnabled();
	}
}
