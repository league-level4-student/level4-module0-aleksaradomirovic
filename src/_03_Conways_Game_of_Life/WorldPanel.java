package _03_Conways_Game_of_Life;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;
	
	private Timer timer;
	
	//1. Create a 2D array of Cells. Do not initialize it.
	Cell[][] cells;
	
	BufferedImage kanye;
	
	
	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;
		
		try {
			kanye = ImageIO.read(this.getClass().getResourceAsStream("kanye.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//2. Calculate the cell size.
		cellSize = w/cpr;
		//3. Initialize the cell array to the appropriate size.
		cells = new Cell[cpr][cpr];
		//3. Iterate through the array and initialize each cell.
		//   Don't forget to consider the cell's dimensions when 
		//   passing in the location.
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				cells[x][y] = new Cell(x, y, cellSize);
			}
		}
	}
	
	public void randomizeCells() {
		Random rnd = new Random();
		//4. Iterate through each cell and randomly set each
		//   cell's isAlive memeber to true of false
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				cells[x][y].isAlive = rnd.nextBoolean();
			}
		}
		
		repaint();
	}
	
	public void clearCells() {
		//5. Iterate through the cells and set them all to dead.
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				cells[x][y].isAlive = false;
			}
		}
		
		repaint();
	}
	
	public void startAnimation() {
		timer.start();
	}
	
	public void stopAnimation() {
		timer.stop();
	}
	
	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(kanye, 0, 0,this.getPreferredSize().width,this.getPreferredSize().height, null);
		//6. Iterate through the cells and draw them all
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				cells[x][y].draw(g);
			}
		}
		
		
		// draws grid
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
	
	//advances world one step
	public void step() {
		//7. iterate through cells and fill in the livingNeighbors array
		// . using the getLivingNeighbors method.
		int[][] livingNeighbors = new int[cellsPerRow][cellsPerRow];
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				livingNeighbors[x][y] = getLivingNeighbors(x, y);
			}
		}
		
		//8. check if each cell should live or die
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				if(livingNeighbors[x][y] < 2) cells[x][y].isAlive = false;
				if(livingNeighbors[x][y] == 3) cells[x][y].isAlive = true;
				if(livingNeighbors[x][y] > 3) cells[x][y].isAlive = false;
			}
		}
		
		repaint();
	}
	
	//9. Complete the method.
	//   It returns an int of 8 or less based on how many
	//   living neighbors there are of the 
	//   cell identified by x and y
	public int getLivingNeighbors(int x, int y) {
		int r = 0;
		
		if(x != 0) {
			if(y != 0 && cells[x-1][y-1].isAlive) r++;
			if(cells[x-1][y].isAlive) r++;
			if(y != cellsPerRow-1 && cells[x-1][y+1].isAlive) r++;
		}
		if(x != cellsPerRow-1) {
			if(y != 0 && cells[x+1][y-1].isAlive) r++;
			if(cells[x+1][y].isAlive) r++;
			if(y != cellsPerRow-1 && cells[x+1][y+1].isAlive) r++;
		}
		if(y != 0 && cells[x][y-1].isAlive) r++;
		if(y != cellsPerRow-1 && cells[x][y+1].isAlive) r++;
		
		return r;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//10. Use e.getX() and e.getY() to determine
		//    which cell is clicked. Then toggle
		//    the isAlive variable for that cell.
		
		
		
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();		
	}
}
