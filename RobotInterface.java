package robotsimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Simple program to show arena with multiple robots
* @author Oyinkansola Odugbemi
 *
 */

public class RobotInterface {

	private static final Direction randomDirection = null;
	private Scanner s;								// scanner used for input from user
    private RobotArena myArena;				// arena in which Robots are shown
    
    /**
    	 * constructor for RobotInterface
    	 * sets up scanner used for input and the arena
    	 * then has main loop allowing user to enter commands
     */
    public RobotInterface() {
    	 s = new Scanner(System.in);			// set up scanner for user input
    	 myArena = new RobotArena(20, 6);	// create arena of size 20*6

        char ch = ' '; // main loop for user commands
        do {
        	System.out.print("Enter (N)ew Arena, (A)dd Robot, get (I)nformation, (D)isplay Arena, (M)ove Robots once, (S)imulate, Sa(V)e, (L)oad or e(X)it > ");
        	ch = s.next().charAt(0);
        	s.nextLine();
        	switch (ch) {

    			case 'A' :
    			case 'a' :
        					myArena.addRobot();	// add a new Robot to arena
        					break;
        		case 'I' :
        		case 'i' :
        					System.out.println(myArena.getArenaDetails()); //Display robot information
            				break;
        		case 'D':
        		case 'd':
        					doDisplay(); //Display arena and robots in it.
        					break;

        		 case 'M': //moves the robot once
        		 case 'm':
        			 		moveRobotsOnce();
        		 			break;

        		 case 'S':
        		 case 's':
        			 		animateRobots(); // Animate robots moving in 10 steps
        		  			break;

        		 case 'N':
        		 case 'n':
        			 		CreateNewArena(s); // Create a new arena with user-defined dimensions 
        		  			break;

        		 case 'V':
        		 case 'v':
        			 		saveArena(); //Save the current arena to a file
        			 		break;

        		 case 'L':
        		 case 'l':
        			 		loadArena(); // Load an arena from a file
        			 		break;


        		case 'x' : 	ch = 'X';				// when X detected program ends
        					break;
        	}
    		} while (ch != 'X');						// test if end

       s.close();									// close scanner
    }

    public void CreateNewArena(Scanner scanner) {
    	// Prompt user for arena dimensions
    	System.out.print("Enter width: ");
    	int width = scanner.nextInt();
    	System.out.print("Enter Height: ");
    	int height = scanner.nextInt();
    	
    	//Create new arena
    	myArena = new RobotArena(width, height);
    	System.out.println("New Arena created with size " + width + " by "+ height);
    }
    
    //Displays detailed information about the robots in the arena.
    public void displayRobotInfo() {
    	ArrayList<Robot> robots = myArena.getRobots();
    	if (robots.isEmpty()) {
            System.out.println("No robots to display."); //If no robots exist, the user is notified.
        } else {
            System.out.println("Arena size: " + myArena.getSizeX() + " by " + myArena.getSizeY() + " with robots:");
            for (Robot robot : robots) {
                System.out.println(robot);  	// Print each robot's information

            }
        }
    }

    
    //Moves all robots once in the current arena
    public void moveRobotsOnce() {
    	myArena.moveAllRobots();
        displayRobotInfo(); //displays robot information after moving
    }

    
    //Displays the current size of the arena and all robots
    void doDisplay() {
    	int arenaWidth = myArena.getSizeX();           // Arena width (xmax)
        int arenaHeight = myArena.getSizeY();          // Arena height (ymax)

        ConsoleCanvas c = new ConsoleCanvas(arenaWidth, arenaHeight, "32004637");
        myArena.showRobots(c);                         // Show robots on the canvas
        System.out.println(c.toString());			   // Print canvas to console
    }

    
    //Animates robots by moving them for 10 steps, with a small delay between steps.
    public void animateRobots() {
        for (int i = 0; i < 10; i++) {
        	myArena.moveAllRobots();
            displayRobotInfo();
            try {
                Thread.sleep(200); // Delay for 200ms between steps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Saves the current arena to a file specified by the user.
    public void saveArena() {
        // Ask for file name
        System.out.print("Enter saved file name: ");
        String fileName = s.nextLine();
        
        // Save arena to the file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(myArena.toString());  // Assuming arena.toString() gives the string representation of the arena
            System.out.println("Arena saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving arena: " + e.getMessage());
        }
    }

    
    //Loads an arena from a specified file.
    public void loadArena() {
        System.out.print("Enter saved file name: ");
        String fileName = s.nextLine();

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return;
        }
        
        // Load arena from file
        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextLine()) {
                String arenaSize = fileScanner.nextLine();
                System.out.println("Arena size: " + arenaSize);

                String[] sizeParts = arenaSize.split(" ");
                if (sizeParts.length < 5) {
                    System.out.println("Invalid arena size format.");
                    return;
                }

                int xmax = Integer.parseInt(sizeParts[2]);
                int ymax = Integer.parseInt(sizeParts[4]);
                myArena  = new RobotArena(xmax, ymax);

                // Regular expression to match robot data in the file
                Pattern robotPattern = Pattern.compile("Robot (\\d+) at (\\d+), (\\d+) moving in direction (\\w+)");

                while (fileScanner.hasNextLine()) {
                    String robotData = fileScanner.nextLine();
                    System.out.println("Attempting to load: " + robotData);

                    Matcher matcher = robotPattern.matcher(robotData);

                    if (matcher.matches()) {
                        int id = Integer.parseInt(matcher.group(1));
                        int x = Integer.parseInt(matcher.group(2));
                        int y = Integer.parseInt(matcher.group(3));
                        String directionStr = matcher.group(4);

                        try {
                            Direction direction = Direction.valueOf(directionStr.toUpperCase());
                            if (x < 0 || x >= xmax || y < 0 || y >= ymax) {
                                System.out.println("Invalid robot position: " + x + ", " + y);
                                continue;
                            }
                            
                            // Create the robot and add it to the arena
                            Robot robot = new Robot(x, y, direction);
                            myArena.addRobot();
                            System.out.println("Robot added: " + robot);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid direction: " + directionStr);
                        }
                    } else {
                        System.out.println("Invalid robot data format: " + robotData);
                    }
                }

                System.out.println("Arena loaded successfully.");
            } else {
                System.out.println("File is empty.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    //Main method to start the robot simulation interface.
	public static void main(String[] args) {
		RobotInterface r = new RobotInterface();	// just call the interface
	}

}
