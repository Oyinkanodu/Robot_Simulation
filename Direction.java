package robotsimulation;

import java.util.Random;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    // Random instance for random direction generation
    private static final Random random = new Random();

    // Method to get a random direction
    public static Direction getRandomDirection() {
        return values()[random.nextInt(values().length)];
    }

    // Method to get the next direction in a clockwise order
    public static Direction fromString(String direction) {
        switch (direction.toUpperCase()) {
            case "NORTH": return NORTH;
            case "SOUTH": return SOUTH;
            case "EAST": return EAST;
            case "WEST": return WEST;
            default: throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
    
    //Returns a string representation of the Direction.
    public String toString() {
		return name();
	}
}



