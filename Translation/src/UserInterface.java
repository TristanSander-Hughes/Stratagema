import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Rectangle;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;


class PieSlice{
	int number;
	Color colour;
	PieSlice(int newNum, Color newColour)
	{
		number=newNum;
		colour=newColour;
	}
}
class PieComponent extends JComponent{
	PieSlice[] slices;
	PieComponent(ArrayList<Integer> values, ArrayList<Color> colours) {
		slices=new PieSlice[values.size()];
		for(int i=0; i<values.size();i++)
		{
			slices[i]=new PieSlice(values.get(i),colours.get(i));
		}
		
	}
	public void paint(Graphics g) {
		drawPie((Graphics2D)g,getBounds(),slices);
	}
	void drawPie(Graphics2D g,Rectangle area, PieSlice[] slices)
	{
		double total=0.0D;
		for(int i=0; i<slices.length;i++)
		{
			total+=slices[i].number;
		}
		double currValue=0.0D;
		int startAngle=0;
		for( int i=0; i<slices.length;i++)
		{
			startAngle=(int)(currValue*360/total);
			int arcAngle=(int)(slices[i].number*360/total);
			g.setColor(slices[i].colour);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
			currValue+=slices[i].number;
		}
	}
}
public class UserInterface {

	private JFrame frame;
	private JTable OpponentTable;
	private static Player playerInterface;
	private static HashMap<String,String> opponentData;
	private JTable popTable;
	private JTable environmentTable;
	private Popup pop;
	private int ALTERTEMPCOST = 20;
	private int ALTERRADIATIONCOST = 100;
	private int ALTERPHCOST = 50;
	private JLabel StatusLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//test();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					playerInterface=new Player();
					//test();
					UserInterface window = new UserInterface();
					window.disableAll("Waiting for Connections");
					playerInterface.setInterface(window);
					playerInterface.initialize();
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
	public UserInterface() {
		initialize();
		//updateData(playerInterface.getOpponentData(),playerInterface.getOpponentFitness());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1270, 591);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Object[][] data;
		String[] columns;
		
		JComboBox<String> OpponentCmbBox = new JComboBox<String>();
		OpponentCmbBox.setBounds(443, 381, 164, 22);
		frame.getContentPane().add(OpponentCmbBox);
		
		JButton LowerTemperatureBtn = new JButton("Lower Temperature");
		LowerTemperatureBtn.setToolTipText("Lowers Temperature by 10 degrees");
		LowerTemperatureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.lowerTemperature();
				playerInterface.tradeAwayPops(ALTERTEMPCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			LowerTemperatureBtn.setEnabled(false);
		}
		else {
			LowerTemperatureBtn.setEnabled(true);
		}
		LowerTemperatureBtn.setBounds(48, 409, 135, 23);
		frame.getContentPane().add(LowerTemperatureBtn);
		
		JButton RaiseTemperatureBtn = new JButton("Raise Temperature");
		RaiseTemperatureBtn.setToolTipText("Raises the temperature by 10 degrees");
		RaiseTemperatureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.raiseTemperature();
				playerInterface.tradeAwayPops(ALTERTEMPCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			RaiseTemperatureBtn.setEnabled(false);
		}
		else {
			RaiseTemperatureBtn.setEnabled(true);
		}
		RaiseTemperatureBtn.setBounds(48, 437, 135, 23);
		frame.getContentPane().add(RaiseTemperatureBtn);
		
		JButton LowerPhBtn = new JButton("Lower pH");
		LowerPhBtn.setToolTipText("Lowers pH by 1");
		LowerPhBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.lowerPh();
				playerInterface.tradeAwayPops(ALTERPHCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		LowerPhBtn.setBounds(48, 468, 135, 23);
		frame.getContentPane().add(LowerPhBtn);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			LowerPhBtn.setEnabled(false);
		}
		else {
			LowerPhBtn.setEnabled(true);
		}
		
		JButton RaisePhBtn = new JButton("Raise pH");
		RaisePhBtn.setToolTipText("Raises pH by 1");
		RaisePhBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.raisePh();
				playerInterface.tradeAwayPops(ALTERPHCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		RaisePhBtn.setBounds(48, 499, 135, 23);
		frame.getContentPane().add(RaisePhBtn);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			RaisePhBtn.setEnabled(false);
		}
		else {
			RaisePhBtn.setEnabled(true);
		}
		
		JButton AlterRadiationBtn = new JButton("Alter Radiation");
		AlterRadiationBtn.setToolTipText("Alters the radiation value ");
		AlterRadiationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.alterRadiation();
				playerInterface.tradeAwayPops(ALTERRADIATIONCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		AlterRadiationBtn.setBounds(48, 381, 135, 23);
		frame.getContentPane().add(AlterRadiationBtn);
		if(playerInterface.getPopulationSize()<ALTERRADIATIONCOST)
		{
			AlterRadiationBtn.setEnabled(false);
		}
		else {
			AlterRadiationBtn.setEnabled(true);
		}
		
		JButton AlterOpponentRadiationBtn = new JButton("Alter Opponent Radiation");
		AlterOpponentRadiationBtn.setToolTipText("Alters selected opponent's radiation value");
		AlterOpponentRadiationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				playerInterface.alterOpponentRadiation(OpponentCmbBox.getSelectedItem().toString());
				playerInterface.tradeAwayPops(ALTERRADIATIONCOST);
				updateLocalData();
				checkAvailability();
				
			}
		});
		AlterOpponentRadiationBtn.setBounds(231, 381, 179, 23);
		frame.getContentPane().add(AlterOpponentRadiationBtn);
		if(playerInterface.getPopulationSize()<ALTERRADIATIONCOST)
		{
			AlterOpponentRadiationBtn.setEnabled(false);
		}
		else {
			AlterOpponentRadiationBtn.setEnabled(true);
		}
		
		JButton LowerOpponentTemperatureBtn = new JButton("Lower Opponent Temperature");
		LowerOpponentTemperatureBtn.setToolTipText("Lowers selected opponent's temperature by 10");
		LowerOpponentTemperatureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.lowerOpponentTemperature(OpponentCmbBox.getSelectedItem().toString());
				playerInterface.tradeAwayPops(ALTERTEMPCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		LowerOpponentTemperatureBtn.setBounds(231, 409, 179, 23);
		frame.getContentPane().add(LowerOpponentTemperatureBtn);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			LowerOpponentTemperatureBtn.setEnabled(false);
		}
		else {
			LowerOpponentTemperatureBtn.setEnabled(true);
		}
		
		JButton RaiseOpponentTemperatureBtn = new JButton("Raise Opponent Temperature");
		RaiseOpponentTemperatureBtn.setToolTipText("Raise selected opponent's temperature by 10");
		RaiseOpponentTemperatureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.raiseOpponentTemperature(OpponentCmbBox.getSelectedItem().toString());
				playerInterface.tradeAwayPops(ALTERTEMPCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		RaiseOpponentTemperatureBtn.setBounds(231, 437, 179, 23);
		frame.getContentPane().add(RaiseOpponentTemperatureBtn);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			RaiseOpponentTemperatureBtn.setEnabled(false);
		}
		else {
			RaiseOpponentTemperatureBtn.setEnabled(true);
		}
		
		JButton LowerOpponentPhBtn = new JButton("Lower Opponent pH");
		LowerOpponentPhBtn.setToolTipText("Lowers selected opponent's pH by 1");
		LowerOpponentPhBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.lowerOpponentPh(OpponentCmbBox.getSelectedItem().toString());
				playerInterface.tradeAwayPops(ALTERPHCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		LowerOpponentPhBtn.setBounds(231, 468, 179, 23);
		frame.getContentPane().add(LowerOpponentPhBtn);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			LowerOpponentPhBtn.setEnabled(false);
		}
		else {
			LowerOpponentPhBtn.setEnabled(true);
		}
		
		JButton RaiseOpponentPhBtn = new JButton("Raise Opponent pH");
		RaiseOpponentPhBtn.setToolTipText("Raise selected opponent's temperature by 1");
		RaiseOpponentPhBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerInterface.raiseOpponentPh(OpponentCmbBox.getSelectedItem().toString());
				playerInterface.tradeAwayPops(ALTERPHCOST);
				updateLocalData();
				checkAvailability();
			}
		});
		RaiseOpponentPhBtn.setBounds(231, 499, 179, 23);
		frame.getContentPane().add(RaiseOpponentPhBtn);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			RaiseOpponentPhBtn.setEnabled(false);
		}
		else {
			RaiseOpponentPhBtn.setEnabled(true);
		}
		
		JLabel lblNewLabel = new JLabel("Opponent Info");
		lblNewLabel.setBounds(602, 23, 81, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel PopulationPanel = new JPanel();
		PopulationPanel.setBounds(48, 27, 263, 343);
		ArrayList<Double> ChartFitness=playerInterface.getFitnesses();
		ArrayList<Color> ChartColours=new ArrayList<Color>();
		for(int i=0; i<ChartFitness.size();i++)
		{
			ChartColours.add(getColour(ChartFitness.get(i)/14.0));
		}
		System.out.println(ChartColours.toString());
		ArrayList<Integer> ChartNums=playerInterface.getFrequencies();
		PopulationPanel.add(new PieComponent(ChartNums,ChartColours));
		PopulationPanel.setVisible(true);
		frame.getContentPane().add(PopulationPanel);
		
		JLabel lblPopulationInfo = new JLabel("Population Info");
		lblPopulationInfo.setBounds(443, 414, 81, 14);
		frame.getContentPane().add(lblPopulationInfo);
		
		JLabel lblEnvironmentInfo = new JLabel("Environment Info");
		lblEnvironmentInfo.setBounds(350, 23, 103, 14);
		frame.getContentPane().add(lblEnvironmentInfo);
		
		
		
		
		
		JPanel environmentPane = new JPanel(new BorderLayout());
		environmentPane.setBounds(350, 48, 206, 284);
		
		
		columns=new String[] {"Parameter","Value"};
		
		data=new Object[][] {
			{"Temperature",playerInterface.getTemperature()},
			{"pH",playerInterface.getPh()},
			{"Radiation Present",playerInterface.getRadiation()},
			{"Population Size",playerInterface.getPopulationSize()},
			{"Generations", playerInterface.getGenerations()}
		};
		
		environmentTable = new JTable(data,columns);
		environmentTable.setBounds(250, 233, 206, -277);
		environmentPane.add(environmentTable,BorderLayout.CENTER);
		environmentPane.add(environmentTable.getTableHeader(),BorderLayout.NORTH);
		frame.getContentPane().add(environmentPane);
		
		JPanel opponentPanel = new JPanel();
		opponentPanel.setBounds(602, 48, 164, 299);
		frame.getContentPane().add(opponentPanel);
		
		
		
		OpponentTable = new JTable();
		opponentPanel.add(OpponentTable);
		OpponentTable.setBackground(Color.LIGHT_GRAY);
		OpponentTable.setRowSelectionAllowed(false);
		
		
		columns=new String[] {"Genotype","Breakdown","Frequency","Fitness"};
		ArrayList< Double>Fitnesses=playerInterface.getFitnesses();
		ArrayList<Integer>Frequencies=playerInterface.getFrequencies();
		ArrayList<String>Genotypes=playerInterface.getGenotypes();
		data=new Object[Genotypes.size()][4];
		for(int i=0; i<Genotypes.size();i++)
		{
			
			data[i][0]=Genotypes.get(i);
			 String nextGenotype="";
			 //for (int j = 0; j < Genotypes.size(); j++)
			    //{
			       
			        //nextGenotype += "Properties: ";
				 	nextGenotype="";
				 	if (Genotypes.get(i).charAt(0) == '1')
			        {
			            nextGenotype += " F ";
			        }
			        if (Genotypes.get(i).charAt(1) == '1')
			        {
			            nextGenotype += " Xer ";
			        }
			        if (Genotypes.get(i).charAt(2) == '1')
			        {
			            nextGenotype += " Psy ";
			        }
			        if (Genotypes.get(i).charAt(6) == '1')
			        {
			            nextGenotype += " HS ";
			        }
			        if (Genotypes.get(i).charAt(7) == '1')
			        {
			            nextGenotype += " A ";
			        }
			        if (Genotypes.get(i).charAt(8) == '1')
			        {
			            nextGenotype += " P ";
			        }
			        if (Genotypes.get(i).charAt(9) == '1')
			        {
			            nextGenotype += " C ";
			        }
			        if (Genotypes.get(i).charAt(10) == '1')
			        {
			            nextGenotype += " S ";
			        }
			        /*if (Genotypes.get(j).charAt(0) == '1')
			        {
			            nextGenotype += " Flaggelate ";
			        }
			        if (Genotypes.get(j).charAt(1) == '1')
			        {
			            nextGenotype += " Xerophile ";
			        }
			        if (Genotypes.get(j).charAt(2) == '1')
			        {
			            nextGenotype += " Psychrophile ";
			        }
			        if (Genotypes.get(j).charAt(6) == '1')
			        {
			            nextGenotype += " Hardened Shell ";
			        }
			        if (Genotypes.get(j).charAt(7) == '1')
			        {
			            nextGenotype += " Aggressor ";
			        }
			        if (Genotypes.get(j).charAt(8) == '1')
			        {
			            nextGenotype += " Producer ";
			        }
			        if (Genotypes.get(j).charAt(9) == '1')
			        {
			            nextGenotype += " Consumer ";
			        }
			        if (Genotypes.get(j).charAt(10) == '1')
			        {
			            nextGenotype += " Sterile ";
			        }*/
			   // }
			 data[i][1]=nextGenotype;
			 data[i][2]=Frequencies.get(i);
			 data[i][3]=Fitnesses.get(i);
		}
		popTable = new JTable(data, columns);
		popTable.setToolTipText("F= Flagellate Xer=Xerophile Psy=Psychrophile HS=Hardened Bacterial Shell A=Aggressor P=Producer C=Consumer S=Sterile");
		popTable.setBounds(22, 5, 543, 47);
		
		JScrollPane PopInfoPane=new JScrollPane(popTable);
		PopInfoPane.setToolTipText("F= Flagellate Xer=Xerophile Psy=Psychrophile HS=Hardened Bacterial Shell A=Aggressor P=Producer C=Consumer S=Sterile");
		PopInfoPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		PopInfoPane.setSize(600, 100);
		PopInfoPane.setLocation(450, 432);
		PopInfoPane.setVisible(true);
		
		//JPanel PopInfoPane = new JPanel(new BorderLayout());
		//PopInfoPane.setBounds(443, 437, 575, 104);
		//PopInfoPane.setLayout(null);
		//PopInfoPane.add(popTable,BorderLayout.CENTER);
		//PopInfoPane.add(popTable.getTableHeader(),BorderLayout.NORTH);
		frame.getContentPane().add(PopInfoPane);
		
		StatusLabel = new JLabel("");
		StatusLabel.setBounds(10, 2, 152, 14);
		frame.getContentPane().add(StatusLabel);
		
		/*try {
		this.wait(5000);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		//updateData(playerInterface.getOpponentData(),playerInterface.getOpponentFitness());
	}
	public void updateLocalData()
	{
		Object[][] data;
		String[] columns;
		columns=new String[] {"Parameter","Value"};
		
		data=new Object[][] {
			{"Temperature",playerInterface.getTemperature()},
			{"pH",playerInterface.getPh()},
			{"Radiation Present",playerInterface.getRadiation()},
			{"Population Size",playerInterface.getPopulationSize()},
			{"Generations", playerInterface.getGenerations()}
		};
		environmentTable = new JTable(data,columns);
		JPanel tablePanel;
		tablePanel=(JPanel)frame.getContentPane().getComponent(15);//15?
	
		tablePanel.removeAll();
		tablePanel.add(environmentTable,BorderLayout.CENTER);
		tablePanel.add(environmentTable.getTableHeader(),BorderLayout.NORTH);
		tablePanel.revalidate();
		
		columns=new String[] {"Genotype","Breakdown","Frequency","Fitness"};
		ArrayList< Double>Fitnesses=playerInterface.getFitnesses();
		ArrayList<Integer>Frequencies=playerInterface.getFrequencies();
		ArrayList<String>Genotypes=playerInterface.getGenotypes();
		data=new Object[Genotypes.size()][4];
		for(int i=0; i<Genotypes.size();i++)
		{
			data[i][0]=Genotypes.get(i);
			 String nextGenotype="";
			// for (int j = 0; j < Genotypes.size(); j++)
			   // {
			       
			        //nextGenotype += "Properties: ";
				 	nextGenotype="";
				 	if (Genotypes.get(i).charAt(0) == '1')
			        {
			            nextGenotype += " F ";
			        }
			        if (Genotypes.get(i).charAt(1) == '1')
			        {
			            nextGenotype += " Xer ";
			        }
			        if (Genotypes.get(i).charAt(2) == '1')
			        {
			            nextGenotype += " Psy ";
			        }
			        if (Genotypes.get(i).charAt(6) == '1')
			        {
			            nextGenotype += " HS ";
			        }
			        if (Genotypes.get(i).charAt(7) == '1')
			        {
			            nextGenotype += " A ";
			        }
			        if (Genotypes.get(i).charAt(8) == '1')
			        {
			            nextGenotype += " P ";
			        }
			        if (Genotypes.get(i).charAt(9) == '1')
			        {
			            nextGenotype += " C ";
			        }
			        if (Genotypes.get(i).charAt(10) == '1')
			        {
			            nextGenotype += " S ";
			        }
			        /*if (Genotypes.get(j).charAt(0) == '1')
			        {
			            nextGenotype += " Flaggelate ";
			        }
			        if (Genotypes.get(j).charAt(1) == '1')
			        {
			            nextGenotype += " Xerophile ";
			        }
			        if (Genotypes.get(j).charAt(2) == '1')
			        {
			            nextGenotype += " Psychrophile ";
			        }
			        if (Genotypes.get(j).charAt(6) == '1')
			        {
			            nextGenotype += " Hardened Shell ";
			        }
			        if (Genotypes.get(j).charAt(7) == '1')
			        {
			            nextGenotype += " Aggressor ";
			        }
			        if (Genotypes.get(j).charAt(8) == '1')
			        {
			            nextGenotype += " Producer ";
			        }
			        if (Genotypes.get(j).charAt(9) == '1')
			        {
			            nextGenotype += " Consumer ";
			        }
			        if (Genotypes.get(j).charAt(10) == '1')
			        {
			            nextGenotype += " Sterile ";
			        }*/
			    //}
			 data[i][1]=nextGenotype;
			 data[i][2]=Frequencies.get(i);
			 data[i][3]=Fitnesses.get(i);
		}
		popTable=new JTable(data,columns);
		
		//JScrollPane popPane;
		frame.getContentPane().remove(17);
		JScrollPane PopInfoPane=new JScrollPane(popTable);
		PopInfoPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		PopInfoPane.setSize(600, 100);
		PopInfoPane.setLocation(450, 432);
		PopInfoPane.setVisible(true);
		frame.getContentPane().add(PopInfoPane);
		/*
		tablePanel.removeAll();
		tablePanel.add(popTable,BorderLayout.CENTER);
		tablePanel.add(popTable.getTableHeader(),BorderLayout.NORTH);
		tablePanel.revalidate();*/
		
		
		
	}
	
	public void enableAll()
	{
		for(int i=0;i<frame.getContentPane().getComponentCount();i++)
		{
			frame.getContentPane().getComponent(i).setEnabled(true);
		}
		pop.hide();
		
		checkAvailability();
		
	}
	public void disableAll(String message)
	{
		
		for(int i=0;i<frame.getContentPane().getComponentCount();i++)
		{
			frame.getContentPane().getComponent(i).setEnabled(false);
		}
		//StatusLabel.setEnabled(true);
		
		JPanel popFrame=new JPanel();
		JLabel popLable=new JLabel(message);
		popFrame.setSize(100,100);
		popFrame.removeAll();
		popFrame.add(popLable);
		PopupFactory factory=new PopupFactory();
		pop=factory.getPopup(frame, popFrame, 180, 100);
		pop.show();
	}
	private void checkAvailability()
	{
		JButton LowerTemperatureBtn=(JButton)frame.getContentPane().getComponent(1);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			LowerTemperatureBtn.setEnabled(false);
		}
		else {
			LowerTemperatureBtn.setEnabled(true);
		}
		JButton RaiseTemperatureBtn=(JButton)frame.getContentPane().getComponent(2);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			RaiseTemperatureBtn.setEnabled(false);
		}
		else {
			RaiseTemperatureBtn.setEnabled(true);
		}
		JButton LowerPhBtn=(JButton)frame.getContentPane().getComponent(3);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			LowerPhBtn.setEnabled(false);
		}
		else {
			LowerPhBtn.setEnabled(true);
		}
		JButton RaisePhBtn=(JButton)frame.getContentPane().getComponent(4);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			RaisePhBtn.setEnabled(false);
		}
		else {
			RaisePhBtn.setEnabled(true);
		}
		JButton AlterRadiationBtn=(JButton)frame.getContentPane().getComponent(5);
		if(playerInterface.getPopulationSize()<ALTERRADIATIONCOST)
		{
			AlterRadiationBtn.setEnabled(false);
		}
		else {
			AlterRadiationBtn.setEnabled(true);
		}
		JButton AlterOpponentRadiationBtn=(JButton)frame.getContentPane().getComponent(6);
		if(playerInterface.getPopulationSize()<ALTERRADIATIONCOST)
		{
			AlterOpponentRadiationBtn.setEnabled(false);
		}
		else {
			AlterOpponentRadiationBtn.setEnabled(true);
		}
		JButton LowerOpponentTemperatureBtn=(JButton)frame.getContentPane().getComponent(7);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			LowerOpponentTemperatureBtn.setEnabled(false);
		}
		else {
			LowerOpponentTemperatureBtn.setEnabled(true);
		}
		JButton RaiseOpponentTemperatureBtn=(JButton)frame.getContentPane().getComponent(8);
		if(playerInterface.getPopulationSize()<ALTERTEMPCOST)
		{
			RaiseOpponentTemperatureBtn.setEnabled(false);
		}
		else {
			RaiseOpponentTemperatureBtn.setEnabled(true);
		}
		JButton LowerOpponentPhBtn=(JButton)frame.getContentPane().getComponent(9);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			LowerOpponentPhBtn.setEnabled(false);
		}
		else {
			LowerOpponentPhBtn.setEnabled(true);
		}
		JButton RaiseOpponentPhBtn=(JButton)frame.getContentPane().getComponent(10);
		if(playerInterface.getPopulationSize()<ALTERPHCOST)
		{
			RaiseOpponentPhBtn.setEnabled(false);
		}
		else {
			RaiseOpponentPhBtn.setEnabled(true);
		}
		
	}
	public void updateData()
	{
		//opponent portion
		
		Object[][] data;
		String[] columns;
		HashMap<String,Double> opponentFitness=playerInterface.getOpponentFitness();
		HashMap<String,String> opponentData=playerInterface.getOpponentData();
		 columns=new String[] {"OpponentID","Most Fit Genotype","Fitness"} ;
		 data=new Object[opponentData.size()][3];
		Iterator<HashMap.Entry<String,String>> entries=opponentData.entrySet().iterator();
		int row=0;
		JComboBox<String> oppComboBox=(JComboBox<String>)frame.getContentPane().getComponent(0);
		oppComboBox.removeAllItems();
		while (entries.hasNext()) {
			HashMap.Entry<String,String> entry=entries.next();
			
			
			oppComboBox.addItem(entry.getKey());
			data[row][0]=entry.getKey();
			data[row][1]=entry.getValue();
			data[row][2]=opponentFitness.get(entry.getKey());
			row++;
	
	
		}
		OpponentTable=new JTable(data,columns);
		frame.getContentPane().remove(16);
		JScrollPane OppInfoPane=new JScrollPane(OpponentTable);
		OppInfoPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		OppInfoPane.setBounds(602, 48, 350, 100);
		OppInfoPane.setVisible(true);
		frame.getContentPane().add(OppInfoPane);
		
		//player portion
		//environment
		/*columns=new String[] {"Parameter","Value"};
		
		data=new Object[][] {
			{"Temperature",playerInterface.getTemperature()},
			{"pH",playerInterface.getPh()},
			{"Radiation Present",playerInterface.getRadiation()}
		};
		System.out.println(data);
		frame.getContentPane().remove(environmentTable);
		environmentTable=new JTable(data,columns);
		environmentTable.setBackground(Color.LIGHT_GRAY);
		//environmentTable.setRowSelectionAllowed(false);
		environmentTable.setBounds(350, 333, 206, -277);
		
		environmentTable.repaint();
		
		//population
		columns=new String[] {"Genotype","Breakdown","Frequency","Fitness"};
		ArrayList< Double>Fitnesses=playerInterface.getFitnesses();
		ArrayList<Integer>Frequencies=playerInterface.getFrequencies();
		ArrayList<String>Genotypes=playerInterface.getGenotypes();
		data=new Object[Genotypes.size()][4];
		for(int i=0; i<Genotypes.size();i++)
		{
			data[i][0]=Genotypes.get(i);
			 String nextGenotype="";
			 for (int j = 0; j < Genotypes.size(); j++)
			    {
			       
			        nextGenotype += "Properties: ";
			        if (Genotypes.get(j).charAt(0) == '1')
			        {
			            nextGenotype += " Flaggelate ";
			        }
			        if (Genotypes.get(j).charAt(1) == '1')
			        {
			            nextGenotype += " Xerophile ";
			        }
			        if (Genotypes.get(j).charAt(2) == '1')
			        {
			            nextGenotype += " Psychrophile ";
			        }
			        if (Genotypes.get(j).charAt(6) == '1')
			        {
			            nextGenotype += " Hardened Shell ";
			        }
			        if (Genotypes.get(j).charAt(7) == '1')
			        {
			            nextGenotype += " Aggressor ";
			        }
			        if (Genotypes.get(j).charAt(8) == '1')
			        {
			            nextGenotype += " Producer ";
			        }
			        if (Genotypes.get(j).charAt(9) == '1')
			        {
			            nextGenotype += " Consumer ";
			        }
			        if (Genotypes.get(j).charAt(10) == '1')
			        {
			            nextGenotype += " Sterile ";
			        }
			    }
			 data[i][1]=nextGenotype;
			 data[i][2]=Frequencies.get(i);
			 data[i][3]=Fitnesses.get(i);
		}
		popTable=new JTable(data,columns);
		*/
		
	}
	class PopPannel extends JPanel{
		public PopPannel()
		{
			BorderFactory.createLineBorder(Color.black);

		}
		public Dimension getPrefferedSize() {
			return new Dimension(200,250);
			
		}
		public void paintComponent(Graphics G) {
			super.paintComponent(G);
			ArrayList< Double>Fitnesses=playerInterface.getFitnesses();
			ArrayList<Integer>Frequencies=playerInterface.getFrequencies();
			
			//figure out how to make nice pretty dots and background
			
		}
	}
	public static Color getColour(double percentage)
	{
		double hue=percentage*0.4;
		return Color.getHSBColor((float)hue,(float) 0.9,(float) 0.9);
	}
	
	public static void test()
	{//population test
		/*Population testPop=new Population(10,3,40,6);
		testPop.detectGenotypes();
		testPop.detectProducers();
		System.out.println("Testing getters");
		System.out.println(testPop.getProducersPresent());
		System.out.println(testPop.getMaxSize());
		System.out.println(testPop.getPopulationSize());
		System.out.println(testPop.getGenotypes());
		System.out.println(testPop.getFitnesses());
		System.out.println(testPop.getFrequencies());
		testPop.reproduce();
		testPop.detectGenotypes();
		System.out.println(testPop.getProducersPresent());
		System.out.println(testPop.getMaxSize());
		System.out.println(testPop.getPopulationSize());
		System.out.println(testPop.getGenotypes());
		System.out.println(testPop.getFitnesses());
		System.out.println(testPop.getFrequencies());*/
	//environment test
		/*Environment testEnviron=new Environment();
		System.out.println("Testing Getters");
		System.out.println(testEnviron.getGlucose());
		System.out.println(testEnviron.getPh());
		System.out.println(testEnviron.getPopulationSize());
		System.out.println(testEnviron.getRadiation());
		System.out.println(testEnviron.getTemperature());
		System.out.println(testEnviron.getFitnesses());
		System.out.println(testEnviron.getFrequencies());
		System.out.println(testEnviron.getGenotypes());
		System.out.println("Reproducing:");
		testEnviron.reproductionEvent();
		System.out.println(testEnviron.getGlucose());
		System.out.println(testEnviron.getPh());
		System.out.println(testEnviron.getPopulationSize());
		System.out.println(testEnviron.getRadiation());
		System.out.println(testEnviron.getTemperature());
		System.out.println(testEnviron.getFitnesses());
		System.out.println(testEnviron.getFrequencies());
		System.out.println(testEnviron.getGenotypes());*/
		
	//player test
		/*Player testPlayer=new Player();
		System.out.println("Testing getters");
		System.out.println(testPlayer.getGlucose());
		System.out.println(testPlayer.getMaxFitness());
		System.out.println(testPlayer.getMostFitGenotype());
		System.out.println(testPlayer.getPh());
		System.out.println(testPlayer.getPlayerID());
		System.out.println(testPlayer.getPopulationSize());
		System.out.println(testPlayer.getRadiation());
		System.out.println(testPlayer.getTemperature());
		System.out.println(testPlayer.getFitnesses());
		System.out.println(testPlayer.getFrequencies());
		System.out.println(testPlayer.getGenotypes());
		testPlayer.bufferAlterRadiation();
		testPlayer.bufferPhIncrease();
		testPlayer.bufferPhIncrease();
		testPlayer.bufferPhDecrease();
		testPlayer.bufferTemperatureIncrease();
		testPlayer.bufferTemperatureIncrease();
		testPlayer.bufferTemperatureDecrease();
		testPlayer.triggerReproduction();
		testPlayer.updateStoredData();
		System.out.println(testPlayer.getGlucose());
		System.out.println(testPlayer.getMaxFitness());
		System.out.println(testPlayer.getMostFitGenotype());
		System.out.println(testPlayer.getPh());
		System.out.println(testPlayer.getPlayerID());
		System.out.println(testPlayer.getPopulationSize());
		System.out.println(testPlayer.getRadiation());
		System.out.println(testPlayer.getTemperature());
		System.out.println(testPlayer.getFitnesses());
		System.out.println(testPlayer.getFrequencies());
		System.out.println(testPlayer.getGenotypes());*/
		
		//outbound interface testing
	
		
		
	}
}
