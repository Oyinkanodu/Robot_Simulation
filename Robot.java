package robotsimulation;

public class Robot {
	
	private int x, y, robotID, dx, dy;
	private Direction direction;
	private static int robotcount = 0;
	
	/**
	 * 
	 * @param bx
	 * @param by
	 * @param b
	 */
	
	public Robot (int bx, int by ) {
		x = bx;
		y = by;
		robotID  = robotcount++;
		dx = 1;			//Set default direction of movement
		dy = 1;
	}
	
	

	/**
	 * Constructs a Robot with position from a string.
	 * @param s String containing x and y coordinates separated by spaces
	 */

    public void displayRobot(ConsoleCanvas c) {
    	// call the showIt method in c to put a R where the Robot is
    	c.showIt(y, x, 'R'); // Place 'R' on the canvas at (x, y)
    	}
    
	public Robot (String s) {
			this (0,0); 
			StringSplitter ss = new StringSplitter(s, " ");
			setXY(ss.getNthInt(0, 5), ss.getNthInt(1, 8));
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
		return "Robot " + robotID + " at " + x + ", " + y;
	}
	
	/**
	 * show Robot in canvas c
	 * @param c
 	*/
	
	
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

	public static void main(String[] args) {
		Robot d = new Robot(5, 3); // create Robot
		System.out.println(d.toString());// print where is
		Robot l = new Robot(2, 4); // Create another robot to test
		System.out.println(l.toString());
		}
}
