// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;
import java.util.Arrays;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {

		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);
	
		// Creates an image which will be the result of various 
		Color[][] imageOut;

		/// Tests the horizontal flipping of an image:
		// imageOut = flippedHorizontally(tinypic);
		// System.out.println();
		// print(imageOut);

		/// Tests the horizontal flipping of an image:
		// imageOut = flippedVertically(tinypic);
		// System.out.println();
		// print(imageOut);

		// Testsimage:
		imageOut = scaled(tinypic, 3 ,5);
		System.out.println();
		print(imageOut);
		
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols]; 
		// Reads the RGB values from the file, into the image array. 
		for (int i = 0 ; i < numRows ; i ++){
			for (int j = 0 ; j < numCols  ; j ++){
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	private static void print(Color[][] image) {
		for (int i = 0 ; i < image.length ; i ++){
			for (int j = 0 ; j < image[i].length  ; j ++){
				Color c = image[i][j] ; 
				print(c); 
			}
			System.out.printf("%n");
		}
	}
	
	 //Returns an image which is the horizontally flipped version of the given image. 
	public static Color[][] flippedHorizontally(Color[][] image) {
		int n = image.length ;
		int m = image[0].length ;

		Color[][] FHImage = new Color[n][m];
		for (int i = 0 ; i < n ; i ++){
			for (int j = 0 ; j < m ; j ++){
				FHImage[i][j] = image[i][m-j-1];
			}
		}
		return FHImage;
	}
	

	 //Returns an image which is the vertically flipped version of the given image. 
	public static Color[][] flippedVertically(Color[][] image){
		int n = image.length ;
		int m = image[0].length ;

		Color[][] FVImage = new Color[n][m];
		for (int i = 0 ; i < n ; i ++){
			for (int j = 0 ; j < m ; j ++){
				FVImage[i][j] = image[n-i-1][j];
			}
		}
		return FVImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	public static Color luminance(Color pixel) {
		int r = (int)(pixel.getRed()*0.299) ;
		int g = (int)(pixel.getGreen()*0.587) ;
		int b = (int)(pixel.getBlue()*0.114) ; 

		int lum = r + g + b ;
		
		Color luminance = new Color(lum , lum , lum) ;
		return luminance;
	}
	
	
	//Returns an image which is the grayscaled version of the given image.
	public static Color[][] grayScaled(Color[][] image) {
		int n = image.length ;
		int m = image[0].length ;

		Color[][] gSmage = new Color[n][m];
		for (int i = 0 ; i < n ; i ++){
			for (int j = 0 ; j < m ; j ++){
				gSmage[i][j] = luminance(image[i][j]);
			}
		}
		return gSmage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int n = image.length ;
		int m = image[0].length ;

		Color[][] scaled = new Color[height][width];
		for (int i = 0 ; i < height ; i ++){
			for (int j = 0 ; j < width ; j ++){
				int a = (int)(i * (double) n / height);
				int b = (int)(j * (double) m / width) ; 
				scaled[i][j] = image[a][b]; 
			}
		}
		return scaled;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r = (int)((double)(c1.getRed()*alpha + c2.getRed()*(1-alpha))) ;
		int g = (int)((double)(c1.getGreen()*alpha + c2.getGreen()*(1-alpha))) ;
		int b = (int)((double)(c1.getBlue()*alpha + c2.getBlue()*(1-alpha))) ; 

		Color blend = new Color(r , g ,b);
		return blend;
	}
	
	//Cosntructs and returns an image which is the blending of the two given images.
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int n = image1.length ;
		int m = image1[0].length ;

		Color blendImage[][] = new Color[n][m] ; 

		for (int i = 0 ; i < n ; i++){
			for (int j = 0 ; j < m ; j++){
				blendImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}

		return blendImage;
	}

	//Morphs the source image into the target image, gradually, in n steps.
	public static void morph(Color[][] source, Color[][] target, int n) { 
		target = scaled(target, source[0].length , source.length);
		
		for (int i = 0 ; i < n ; i++){
			double alpha = (double)(n - i)/n ;
			Color[][] blended = blend(source, target, alpha);
			source = blended ;
			Runigram.display(blended);
			StdDraw.pause(500);
		}

	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

