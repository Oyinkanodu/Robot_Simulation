package robotsimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


//	import javax.swing.JFileChooser;
//	import javax.swing.filechooser.FileNameExtensionFilter;

	/**
	 * A class for reading from and writing to text files
	 * User can define the extension for such files
	 * Now exist methods to read/write whole file as single string lines separated by \n
	 * @author Oyinkansola Odugbemi
	 *
	 */
	public class TextFile {

		private BufferedReader inBuffer;	// buffer used for reading files
		private BufferedWriter outBuffer;	// buffer used for writing files
		private String extension;			// extension of files that are opened/create   eg txt
		private String extDescription;		// description used for these eg Text Files
		private String nameOfFile = "";		// name of file used in last operation
		private Scanner scanner;
		private FileReader reader;			// used for reading files
		private String fileLine;			// string containing latest line from file
		private JFileChooser chooser;		// object used to allow user to select files
		private String directory;
	    private String fileName;
	    private File file;

		/**
		 *  constructor used to set up object ... pass it the description of files and extension
		 *  @param ftypeD is string defining type of files - eg Text files
		 *  @param fExtension is string defining extension eg "txt" for text files
		 */
		public TextFile(String ftypeD, String fExtension) {
			extDescription = ftypeD;					// remember these arguments
			extension = fExtension;
			// now set up the dialog whereby user sets file name
			String curDir = System.getProperty("user.dir");						// find current folder
			chooser = new JFileChooser(curDir);									// and set chooser to start there
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);	// set chooser up to find
			FileNameExtensionFilter filter = new FileNameExtensionFilter(extDescription, extension);
													// define filter so only look for files with extension
			chooser.setFileFilter(filter);			// set up chooser
		}

		/**
		 * report name of file used for reading/writing - empty string if open/create file failed
		 * @return  name of file
		 */
		public String usedFileName() {
			return 	nameOfFile;
		}

		/**
		 * method to open a text file for reading
		 * @return true if file has been opened
		 */
		public boolean openFile() {
	        nameOfFile = "";  // by default no file specified
	        int oDVal = chooser.showOpenDialog(null);  // run dialog for asking user to select file

	        if (oDVal == JFileChooser.APPROVE_OPTION) {  // if successful
	            File selFile = chooser.getSelectedFile();  // get the file chosen
	            if (selFile.isFile()) {  // if it is a file
	                try {
	                    scanner = new Scanner(selFile);  // set up scanner for the file
	                    nameOfFile = selFile.getAbsolutePath();  // remember name of file
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();  // if there is a problem, report it
	                }
	            }
	        }
	        return nameOfFile.length() > 0;  // return true if a file name specified and file opened ok
	    }


		/**
		 * Close the file which has been read
		 */
		void closeFile() {
	        if (scanner != null) {
	            scanner.close();  // close the scanner
	        }
	    }

		/**
		 * function to get the next line from the text file
		 * @return true if a line is there
		 */
		boolean getNextLine() {
	        return scanner.hasNextLine();  // check if there is another line
	    }

		/**
		 * return the last line that was read successfully by getNextline
		 * @return the line is returned as a string
		 */
		String nextLine() {
	        return scanner.nextLine();  // return the next line
	    }

		/**
		 * read all of file, returning one string; each line separated by \n
		 * @return
		 */
	    public String readAllFile() {
	        StringBuilder ans = new StringBuilder();
	        while (getNextLine()) {  // while there is a line to read
	            ans.append(nextLine()).append("\n");  // get it and add to answer
	        }
	        closeFile();  // close file
	        return ans.toString();
	    }


		/**
		 * create a new file for writing
		 * @return true if file is created
		 */
	    public boolean createFile() {
	        nameOfFile = "";  // Reset the name of the file
	        int oDVal = chooser.showSaveDialog(null);
	        
	        if (oDVal == JFileChooser.APPROVE_OPTION) {  // If file selected
	            File wFile = chooser.getSelectedFile();  // Get file path
	            if (!chooser.getFileFilter().accept(wFile)) {  // Ensure correct extension
	                wFile = new File(wFile.getAbsolutePath() + "." + extension); // Add extension if needed
	            }
	            nameOfFile = wFile.getAbsolutePath();  // Save the name of the file
	            
	            // Initialize BufferedWriter after file is selected
	            try {
	                outBuffer = new BufferedWriter(new FileWriter(nameOfFile));  // Initialize BufferedWriter
	            } catch (IOException e) {
	                e.printStackTrace();
	                return false;  // Return false if there's an issue initializing the writer
	            }
	        }
	        return nameOfFile.length() > 0;  // Return true if file creation succeeded
	    }
		/**
		 * writes a string then newline to the file
		 * @param s : string to be written
		 */
	    void putNextLine(String s) {
	        try {
	            if (outBuffer != null) {  // Ensure BufferedWriter is initialized
	                outBuffer.write(s);
	                outBuffer.newLine();  // Add newline after each line
	            } else {
	                System.out.println("BufferedWriter not initialized");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


		/**
		 * close the file that has been written to
		 */
	        void closeWriteFile() {
	            try {
	                if (outBuffer != null) {
	                    outBuffer.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

		/**
		 * write data to created file; data is series of strings separated by \n
		 * @param data
		 */
		public void writeAllFile(String data) {
			String[] manyStrings = data.split("\n");		// split data into lines
			for (String manyString : manyStrings) { // for each line
				putNextLine(manyString);				// put into file
			}
				closeWriteFile();							// close file
			}


		public static void main(String[] args) {
			// Code to test text file class
			TextFile tf = new TextFile("Text files", "txt");		// create object looking for *.txt Text files

			if (tf.openFile()) {									// open file
				System.out.println("Reading from " + tf.usedFileName());
				System.out.println(tf.readAllFile());				// read whole file into str which is printed to console

			} else { // open file
				System.out.println("No read file selected");
			}

			if (tf.createFile()) {									// create file to be written to
				System.out.println("Writing to " + tf.usedFileName());
				tf.writeAllFile("30 10" + "\n" + "0 5 6 EAST"+ "\n" + "1 2 7 SOUTH");
			} else { // create file to be written to
				System.out.println("No write file selected");
			}

		}
}
