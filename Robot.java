package robotsimulation;

public class Robot {

	private int x, y, robotID, dx, dy;
	private Direction direction;
	private int ID;
	private static int robotcount = 0;

	/**
	 *
	 * @param bx
	 * @param by
	 * @param randomDirection
	 * @param b
	 */


	//Constructor with position and random direction
	public Robot (int bx, int by, Direction randomDirection) { //
		x = bx;
		y = by;
		robotID  = robotcount++;
		dx = 1;			//Set default direction of movement
		dy = 1;
		this.direction = randomDirection;
	}


	// Constructor with just x, y coordinates (default direction)
    public Robot(int bx, int by) {
        this(bx, by, Direction.getRandomDirection()); // Use the 3-parameter constructor
    }
    
    // Constructor that takes a string
    public Robot (String s) {
		this (0,0);
		StringSplitter ss = new StringSplitter(s, " ");
		setXY(ss.getNthInt(0, 5), ss.getNthInt(1, 8));
	}
    
    /**
	 * show Robot in canvas c
	 * @param c
 	*/

    public void displayRobot(ConsoleCanvas c) {
        // Call the showIt method in c to put a R where the Robot is
    	c.showIt(y, x, 'R'); // Place 'R' on the canvas at (x, y)
    	}
    
	/**
	 * get value of x
	 * @return x
	 */

	public int getX() {
		 return x;
	 }
	
	/**
	 * get values of y
	 * @return y
	 */
	
	public int getY() {
		 return y;
	 }
	
	//returns the direction of the robot
	public String getDirection() {
		return direction.toString();
	}

	/**
	 * set values of x and y
	 * @param nx
	 * @param ny
	 */

	public void setXY(int nx, int ny) {
		x = nx;
		y = ny;
	}

	/**
	 * Is the Robot at this x,y position
	 * @param sx x position
	 * @param sy y position
	 * @return true if Robot is at sx,sy, false otherwise
	 */

	public boolean isHere (int sx, int sy) {
		return x == sx && y == sy;
	}

	/**
	 * return info about ball in string
	 */

	public String toString() {
		return "Robot " + robotID + " at " + x + ", " + y + " moving in direction " + direction.toString();
	}

	/**
	 * try to move robot in current direction change dire if can't.
	 * @param ra this is the robot arena the robot is in.
	 */

	public void moveRobot(RobotArena ra) {
		int newx = x + dx;
		int newy = y + dy;
		switch (ra.canGoHere(newx, newy)) {
		case 0 : x = newx;
				 y = newy;
				 break;
		case 1 : dx = -dx;
				 break;
		case 2 : dy = -dy;
				 break;
		case 3 : dx = -dx;
				 dy = -dy;
				 break;
		}
	}
	
	

	public void tryToMove(RobotArena arena) {
		int newX = x, newY = y;

		switch (direction) {
		case NORTH: newY--; break;
		case EAST: newX++; break;
		case SOUTH: newY++; break;
		case WEST: newX--; break;
		}

		if (arena.canMoveHere(newX, newY)) {
			x = newX;
			y = newY;
		}
		else {
			direction = direction.getRandomDirection();
		}
	}


	public String toSaveString() {
		return x + " " + y + " " + direction;
	}

	public static Robot fromSaveString(String saveString) {
		String[] parts = saveString.split(" ");
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);
	    Direction direction = Direction.valueOf(parts[2]); // Assuming Direction is an enum
		return new Robot(x, y, direction);
	}

	public static void main(String[] args) {
		Robot d = new Robot(5, 3); // create Robot
		System.out.println(d.toString());// print where is
		Robot l = new Robot(2, 4); // Create another robot to test
		System.out.println(l.toString());  //Print it out as well
		Robot m = new Robot(5, 2,Direction.NORTH); //Create Robot with a specific direction
		System.out.println(m.toString()); //Print it out as well
	}
}


