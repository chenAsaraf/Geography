package GUI;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Algorithms.ShortestPathAlgo;
import Game.Game;
import Game.Map;
import Geom.Point3D;

public class MyFrame extends JFrame	{
	private JMenuBar menuBar;
	private JMenu fileMenu, drawGame, play;
	private JMenuItem openItem, saveItem, pacmanItem, fruitItem, simulationItem, runItem, stopItem;
	private Point3D coord_upper_left = new Point3D(35.20270,32.105877,0);
	private Point3D coord_bottom_right = new Point3D(35.211784,32.101954,0);
	private String path= "C:\\Users\\owner\\Desktop\\forExe3\\Ariel1.png";
	private Map map = new Map(path,coord_upper_left,coord_bottom_right);
	private GameFrame board = new GameFrame(map);


	/**
	 * Constructor
	 */
	public MyFrame() {
		initComponents();
		initBoard();
		createMenu();
		initGui();
	}

	
	//Initializes frame elements
	private void initComponents() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileMenu.add(saveItem);
		menuBar.add(fileMenu);
		drawGame = new JMenu("Draw game");
		menuBar.add(drawGame);
		pacmanItem = new JMenuItem("Pacman");
		drawGame.add(pacmanItem);
		fruitItem = new JMenuItem("Fruit");
		drawGame.add(fruitItem);
		play = new JMenu("Play");
		stopItem = new JMenuItem("Stop");
		runItem = new JMenuItem("Run");
		simulationItem = new JMenuItem("Simulation");
		play.add(runItem);
		play.add(simulationItem);
		play.add(stopItem);
		menuBar.add(play);
	}

	
	//Adds the JPanel board 
	public void initBoard() {
		add(board);
	}

	

	//Initializes the frame buttons and their operations
	private void createMenu() {
		//set the open button
		openItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileDialog();
			}
		});
		//set the save button
		saveItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				board.save();
			}
		});
		//set the packman button
		pacmanItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent p) {
				board.addPackman();
			}
		});
		//set the fruit button
		fruitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent p) {
				board.addFruit();
			}
		});
		//set the simulation button
		simulationItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				board.simulation();
			}
		});
		//set the run button
		runItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				board.runSimulation();
			}
		});
		//set the stop button
		stopItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				board.Stop();
			}
		});

	}

	
	
	/**
	 * Make the Frame features
	 */
	public void initGui() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(map.getMyImage().getWidth(), map.getMyImage().getHeight());
	}

	
	//when clicking on Open - reads from a CSV file and initialize the game
	private void readFileDialog() {
		FileDialog fileDialog = new FileDialog(this, "Open text file", FileDialog.LOAD);
		fileDialog.setFile("*.csv");
		fileDialog.setDirectory("C:");
		fileDialog.setFilenameFilter(new FilenameFilter() {
			@Override 
			public boolean accept (File dir, String name) {
				return name.endsWith(".csv");
			}	
		});
		fileDialog.setVisible(true);
		String folder = fileDialog.getDirectory();
		String fileName = fileDialog.getFile();
		if(fileName!=null) {
			System.out.println("File opened: " + folder + fileName);
		}
		if(board == null) System.out.println("its null");
		board.setGame(new Game(folder + fileName));
		repaint();
	}



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame ex = new MyFrame();
		ex.setVisible(true);
	}

}
