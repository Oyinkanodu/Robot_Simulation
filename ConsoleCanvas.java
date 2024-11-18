package robotsimulation;

	public class ConsoleCanvas {

		private int xsize, ysize;
		private char[][] displayChars;

		
		/** 
		 * @param xS         The width (horizontal size) of the canvas.
		 * @param yS         The height (vertical size) of the canvas.
		 */
		
		ConsoleCanvas (int xS, int yS, String userStr) {
			xsize = xS;
			ysize = yS;
			displayChars = new char[xsize][ysize];	// Create the 2D array for the canvas
			padChars(' ', '#', userStr);			// Fill the canvas with the padding and borders
		}

		/*
		 * function to fill the displayChars array with character pchar, putting bchar in the border
		 * @param pchar			character to pad array
		 * @param bchar			character used for border
		 * @param userStr		8 char string identifying user put in centre of top row
		 */

		private void padChars (char pchar, char bchar, String userStr) {
			int topchk = Math.max((xsize - 8) /2, 0);

	        // Loop through each grid cell to fill the array
			for (int xct = 0; xct< xsize; xct++) {
			for (int yct = 0; yct<ysize; yct++) {
			if (xct>0 && xct<xsize-1 && yct>0 && yct<ysize-1) {
				displayChars[xct][yct] = pchar;
			} else if (xct>=topchk && xct<8+topchk && yct==0) {
				displayChars[xct][yct] = userStr.charAt(xct-topchk);
			} else {
				displayChars[xct][yct] = bchar;
			}
		}
			}
		}

		/*
		 * @param x
		 * @param y
		 * @param ch
		 */

		public void showIt(int x, int y, char ch) {
		    if (x >= 0 && x < xsize && y >= 0 && y < ysize) {  // Ensures x and y are within bounds
		        displayChars[x][y] = ch;	//// Place the character on the canvas
		    }
		}

		/*
		 * @param pchar
		 */

		//Converts the canvas into a string representation, row by row.
		public String toString() {
		        StringBuilder ans = new StringBuilder();
		        for (int yct = 0; yct < ysize; yct++) {
		            for (int xct = 0; xct < xsize; xct++) {
		                ans.append(displayChars[xct][yct]);
		            }
		            ans.append("\n");
		        }
		        return ans.toString();
		    }
		/**
		 * display the Robot in the canvas
		 * @param c the canvas used
		 */

		//Main method to test the ConsoleCanvas
		public static void main(String[] args) {
			ConsoleCanvas c = new ConsoleCanvas (20, 6, "32004637");
			c.showIt(7, 4, 'R');  // Robot at (7, 4)
	        c.showIt(16, 1, 'R'); // Robot at (16, 1)
	        c.showIt(13, 4, 'R'); // Robot at (13, 4)
	        c.showIt(14, 4, 'R'); // Robot at (14, 4)
	        c.showIt(7, 3, 'R');  // Robot at (7, 3)

			System.out.println(c.toString());
		}


	}



