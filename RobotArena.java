package robotsimulation;

import java.util.ArrayList;
import java.util.Random;


public class RobotArena {

	private int xmax;		//max x-coordinate of the arena
	private int ymax;		//max y coordinate of the arena
	private ArrayList<Robot> robots;	//list to store all robots
	private Random randomGenerator;		//Random generator for positioning robots

	
	//Constructor to initialize the arena with a specified size.
	public RobotArena (int xmax, int ymax) {
		this.xmax = xmax;
        this.ymax = ymax;
        robots = new ArrayList<>();		//Initialize the list of robots
        randomGenerator = new Random();	// Initialize the random generator
	}

	//Converts the arena's data to a string
	public String toSaveString() {
		StringBuilder builder = new StringBuilder();
		builder.append(xmax).append(" ").append(ymax).append("\n");
		for (Robot robot : robots) {
			builder.append(robot.toSaveString()).append("\n");
		}
		return builder.toString();
	}

	//Saves the arena's data to a file.
	public void SaveArena() {
	    TextFile file = new TextFile("Text files", "txt");  // Create a TextFile object for handling *.txt files
	    if (file.createFile()) {  // Show the save dialog and check if a file was created
	        System.out.println("Saving to " + file.usedFileName());  // Print the file name

	        String dataToSave = this.toSaveString();  // Get the data you want to save
	        file.writeAllFile(dataToSave);  // Write all data to the file
	        System.out.println("Arena saved successfully.");
	    } else {
	        System.out.println("No file selected for saving.");
	    }
	}

	
	//Loads the arena's data from a file.
	public void LoadArena() {
	    TextFile file = new TextFile("Text files", "txt");  // Create a TextFile object for handling *.txt files
	    if (file.openFile()) {  // Open the file using the dialog
	        System.out.println("Loading from " + file.usedFileName());  // Print the file name
	        String fileContent = file.readAllFile();  // Read the entire file into a string
	        this.fromFile(fileContent);  // Process the file content
	        System.out.println("Arena loaded successfully.");
	    } else {
	        System.out.println("No file selected for loading.");
	    }
	}

	//Processes the file content to reconstruct the arena from saved data.
	public void fromFile(String filename) {
	    TextFile file = new TextFile("Text files", "txt");  // Use TextFile class to handle file operations
	    if (file.openFile()) {  // Open the file using the file dialog
	        String fileContent = file.readAllFile();  // Read the entire file content into a string
	        try {
	            // Split the file content into lines
	            String[] lines = fileContent.split("\n");

	            // Parse the first line to get the arena size (xmax and ymax)
	            String[] arenaSize = lines[0].split(" ");
	            this.xmax = Integer.parseInt(arenaSize[0]);
	            this.ymax = Integer.parseInt(arenaSize[1]);
	            this.robots.clear();  // Clear existing robots if any

	            // Iterate over the remaining lines to parse robots
	            for (int i = 1; i < lines.length; i++) {
	                String line = lines[i].trim();  // Trim any extra spaces or newlines
	                if (!line.isEmpty()) {
	                    Robot robot = Robot.fromSaveString(line);  // Assuming Robot has this method
	                    this.robots.add(robot);  // Add parsed robot to the arena's robots list
	                }
	            }

	            System.out.println("Arena loaded successfully from file.");
	        } catch (Exception e) {
	            System.out.println("Error loading arena from file: " + e.getMessage());
	        }
	    } else {
	        System.out.println("No file selected for loading.");
	    }
	}

	
	//Adds a new robot to the arena at a random position, ensuring the position is not already occupied.
	public void addRobot() {
	    int randomX, randomY;
	    Robot existingRobot;

	    // Keep generating random positions until a valid one is found
	    do {
	        // Generate random x and y coordinates within the internal bounds (no border interaction)
	        randomX = randomGenerator.nextInt(xmax - 2) + 1;  // Random x between 1 and xmax-2
	        randomY = randomGenerator.nextInt(ymax - 2) + 1;  // Random y between 1 and ymax-2

	        // Check if a robot already exists at this position
	        existingRobot = getRobotAt(randomX, randomY);
	    } while (existingRobot != null);  // Repeat until the position is free

	    // Create a new robot at the valid position
	    Direction randomDirection = Direction.getRandomDirection();
	    Robot newRobot = new Robot(randomX, randomY, randomDirection);
	    robots.add(newRobot);
	}

	/**
	 * search arraylist of Robots to see if there is a Robot at x,y
	 * @param x
	 * @param y
	 * @return null if no Robot there, otherwise return Robot
	 */
	
	//Checks if a robot can move to the specified (x, y) position.
	public boolean canMoveHere(int x, int y) {
        // Check if within bounds
        if (x <= 0 || x >= xmax - 1 || y <= 0 || y >= ymax - 1) {
            return false;

        }

        // Check if the position is occupied by another robot
        for (Robot robot : robots) {
            if (robot.getX() == x && robot.getY() == y) {
                return false;
            }
        }
        return true;
    }

	public ArrayList<Robot> getRobots() {
        return robots;
    }


	// Move all robots in the arena
    public void moveAllRobots() {
        for (Robot robot : robots) {
            robot.tryToMove(this); // Pass the arena to each robot
        }
    }

    //Searches for a robot at the specified (x, y) position
	public Robot getRobotAt(int x, int y) {
		for (Robot r : robots) {
			if (r.isHere(x, y)) {
				return r; //Return the robot at the position (x,y)
			}
		}
		return null;
	 }

	/**
	 * show all the Robots in the interface
	 * @param c the canvas in which Robots are shown
	 */
	public void showRobots(ConsoleCanvas c) {
		for (Robot robot : robots) {
	        int x = robot.getX();
	        int y = robot.getY();

	        if (x >= 0 && x < xmax && y >= 0 && y < ymax) {
	            c.showIt(x, y, 'R');  // Place 'R' on the canvas at (x, y)
	        } else {
	            System.out.println("Robot out of bounds at: " + x + ", " + y);
	        }
	    }
	}


	/*
	 * return size of x direction of the Arena
	 */

	public int getSizeX() {
		return xmax;	//Return width of the arena
	}

	/*
	 * return size of y direction of the Arena
	 */

	public int getSizeY() {
		return ymax;	//Return height of the arena
	}

	/*
	 * create toString method -> to generate arena size
	 */
	
	 public String toString() {
		StringBuilder sb = new StringBuilder("Arena size " + xmax + " by " + ymax + " with robots:\n");
		for (Robot r : robots) {
			sb.append(r.toString()).append("\n");
		}
		return sb.toString();
	}
	 
	 public String getArenaDetails() {
	        StringBuilder details = new StringBuilder("Arena size " + xmax + " by " + ymax + " with robots:\n");
	        for (Robot robot : robots) {
	            details.append(robot).append("\n");
	        }
	        return details.toString();
	    }



	public int canGoHere(int x, int y) {
	    int status = 0;
	    if (x <= 0 || x >= xmax - 1)
		 {
			status += 1;  // Ensure it's not at x = 0 or xmax-1
		}
	    if (y <= 0 || y >= ymax - 1)
		 {
			status += 2;  // Ensure it's not at y = 0 or ymax-1
		}
	    return status;
	}

	//Main method to test adding robots to the arena.
	public static void main(String[] args) {
		RobotArena a = new RobotArena(20, 6); // create Robot arena
		a.addRobot();
		a.addRobot();
		a.addRobot();
		System.out.println(a.toString());// print arena size and where robot i
	}



}
