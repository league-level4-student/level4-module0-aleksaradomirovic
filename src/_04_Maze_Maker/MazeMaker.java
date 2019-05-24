package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		Cell c = maze.getCell(randGen.nextInt(width), randGen.nextInt(height));
		
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(c);
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell cc) {
		//A. mark cell as visited
		cc.setBeenVisited(true);

		//B. check for unvisited neighbors using the cell
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(cc);
		Cell nc;
		//C. if has unvisited neighbors,
		if(neighbors.size() > 0) {
			//C1. select one at random.
			nc = neighbors.get(randGen.nextInt(neighbors.size()));
			//C2. push it to the stack
			uncheckedCells.push(nc);
			//C3. remove the wall between the two cells
			removeWalls(cc,nc);
			//C4. make the new cell the current cell and mark it as visited
			cc = nc;
			cc.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(cc);
		}	
			
		//D. if all neighbors are visited
		else {
			//D1. if the stack is not empty
			if(!uncheckedCells.empty()) {
				// D1a. pop a cell from the stack
				
				// D1b. make that the current cell
				cc = uncheckedCells.pop();
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(cc);
			}
		}		
			
		
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell cc, Cell nc) {
		int x = cc.getX(), y = cc.getY();
		
		if(nc.getX() < x) {
			cc.setWestWall(false);
			nc.setEastWall(false);
		}
		if(nc.getX() > x) {
			nc.setWestWall(false);
			cc.setEastWall(false);
		}
		if(nc.getY() < y) {
			nc.setNorthWall(false);
			cc.setSouthWall(false);
		}
		if(nc.getY() > y) {
			cc.setNorthWall(false);
			nc.setSouthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell cc) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		int x = cc.getX(), y = cc.getY();
		
		if(x > 0 && !maze.getCell(x-1, y).hasBeenVisited()) {
			neighbors.add(maze.getCell(x-1, y));
		}
		if(x < maze.width-1 && !maze.getCell(x+1, y).hasBeenVisited()) {
			neighbors.add(maze.getCell(x+1, y));
		}
		if(y > 0 && !maze.getCell(x, y-1).hasBeenVisited()) {
			neighbors.add(maze.getCell(x, y-1));
		}
		if(y < maze.height-1 && !maze.getCell(x, y+1).hasBeenVisited()) {
			neighbors.add(maze.getCell(x, y+1));
		}
		return neighbors;
	}
}
