/*

RAJASOORIYA R.B
E/13/276
Project 1

*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;


public class Main extends JPanel {

    static int WIDTH = 800;
    static int HEIGHT = 800;

    static int iterations;
    static double realMinus;
    static double realPlus;
    static double imagMinus;
    static double imagPlus;

    static double c_Real;
    static double c_Imag;
    static int iterNo = 1000;
    public static BufferedImage fractalImage;


    public static void main(String[] args) {


        fractalImage = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);  // this will print the final image

	
		// fractals for Mandelbrot set//
		
        if (args[0].equals("Mandelbrot")){
		
			if ( args.length == 1 ){		// condition for if the number of arguments is 0;
			realMinus = -1.0;
            realPlus = 1.0;
            imagMinus = -1.0;
            imagPlus = 1.0;
            iterations = 1000;
			}
			else if ( args.length == 5 ){
			realMinus = Double.parseDouble(args[1]);		// condition for if the number of arguments is 4;
            realPlus = Double.parseDouble(args[2]);
            imagMinus = Double.parseDouble(args[3]);
            imagPlus = Double.parseDouble(args[4]);
            iterations = 1000;
			}
			else if ( args.length == 6 ){
            realMinus = Double.parseDouble(args[1]);
            realPlus = Double.parseDouble(args[2]);			// condition for if all the arguments are provided;
            imagMinus = Double.parseDouble(args[3]);
            imagPlus = Double.parseDouble(args[4]);
				
				if ( Integer.parseInt(args[5])<0 ){
					System.out.println("ERROR! Enter a valid number for number of iterations");		// giving an error message if the 
					System.exit(0);																	// number of iterations which user entered is less than 0			
				}
				else{
				iterations = Integer.parseInt(args[5]);
				}
			}
			else {
			System.out.println("ERROR! Enter the correct number of arguments");			// giving an error message if the needed number of arguments are not provided
			System.exit(0);
			}
        }
		
		// fractals for Julia set//
        else if ( args[0].equals("Julia")) {			   
		
			if (args.length == 1){
			c_Real = -0.4;									// conditions for 0 arguments
            c_Imag = 0.6;
           
			}
		else if(args.length == 3){
            c_Real = Double.parseDouble(args[1]);
            c_Imag = Double.parseDouble(args[2]);			// conditions for 2 arguments
           
        }
		else {
		System.out.println("ERROR! Enter the correct number of arguments");		//giving an error message if the needed number of 
		System.exit(0);															// arguments are not provided by the user		
		}
		
		}

		else{

            System.out.println("ERROR! Enter the correct format!");			//giving an error messege if the correct format is not entered.
			System.exit(0);													// (whether Julia or Mandelbrot)
        }
		
		
		JFrame myFrame = new JFrame(args[0]);								// creating the frame for Julia set or mandelbrot set
		
		if (args[0].equals("Mandelbrot"))                  
            myFrame.setContentPane(new Mandelbrot());
        else
			myFrame.setContentPane(new Julia());
		
        
        myFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        myFrame.setSize(WIDTH, HEIGHT);										//setting up the frame properties
        myFrame.pack();
        myFrame.setVisible(true);
		
		    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(fractalImage, 0, 0, this);								
    }

}

// Mandelbrot class extends the main class
// implementing Mandelbrot set

class Mandelbrot extends  Main{

    public Mandelbrot(){
        updateFractol();			//calling the constructor and it will run the updateFractol method,which will continue the process from there on
    }

    private int computeIterations(double cReal,double cImag){
       /*
		
		Let c = c_r + c_i
		Let z = z_r + z_i

		z' = z*z + c
		   = (z_r + z_i)(z_r + z_i) + c_r + c_i
			 = z_r² + 2*z_r*z_i - z_i² + c_r + c_i

			 z_r' = z_r² - z_i² + c_r
			 z_i' = 2*z_i*z_r + c_i

		*/
        double Zr = 0.0;		//initial values of z is given as 0;
        double Zi = 0.0;

        int iterCount = 0;

/* 
this will return the maximum number of iterations if the absolute value is less that 2 within the given number of iterations.
else return the number of iterations after the absolute value is greater than 2.

*/
		
        while ( ( (Zr*Zr)+(Zi*Zi) )<= 4.0 ){
            double Zr_Temp = Zr;
            

            Zr = (Zr*Zr)-(Zi*Zi)+cReal;
            Zi = (2*Zr_Temp*Zi)+cImag;

            if(iterCount >= iterations){
                return iterCount;        
            }
            else{
                iterCount++;
            }
            

        }
        return  iterCount;
    }

    public void updateFractol(){

             for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    double cReal = getXPosition(x);
                    double cImag = getYPosition(y);

                    int iterationCount = computeIterations(cReal, cImag);
                   
                    Color pixelColor = null;
					
					// giving the colors to the pixels
					
                    if (iterationCount == iterations){
						pixelColor = Color.black;
					}
					else if (iterationCount < iterations/100){
						pixelColor = Color.red;
					}
					else if (iterationCount < (iterations/100)*2){
						pixelColor = Color.yellow;
					}
					else if (iterationCount < (iterations/100)*3){
						pixelColor = Color.blue;
					}
					else if (iterationCount < (iterations/100)*4) {
						pixelColor = Color.cyan;
					}
					else if (iterationCount < (iterations/100)*5){
						pixelColor = Color.white;
					}
					else if (iterationCount < (iterations/100)*6){
						pixelColor = Color.orange;
					}
					else if (iterationCount < (iterations/100)*7){
						pixelColor = Color.lightGray;
					}
					else if (iterationCount < (iterations/100)*8){
						pixelColor = Color.gray;
					}
					else{
						pixelColor = Color.magenta;
					}
				fractalImage.setRGB(x,y,pixelColor.getRGB());  			//printing the colour of a pixel.
                }
		     
			}
    }
	
	
	  private double getXPosition(double X){
        return (((realPlus - realMinus)/HEIGHT) * X);		// mapping the X,Y coordinates to the complex plane
    }
    private double getYPosition(double Y){
        return (((imagPlus - imagMinus)/WIDTH) * Y);
    }
}

// Julia class extends the main class
// implementing Julia set

class Julia extends Main{

	public Julia(){
        updateFractol();
    }

	public void updateFractol(){

             for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    double cReal = getXPosition(x);
                    double cImag = getYPosition(y);

                    int iterationCount = computeIterations(cReal, cImag);
                  
                    Color pixelColor = null;

                    if (iterationCount == iterNo){
						pixelColor = Color.black;
					}
					else if (iterationCount < 1){
						pixelColor = Color.blue;
					}
					else if (iterationCount < 2){
						pixelColor = Color.yellow;
					}
					else if (iterationCount < 3){
						pixelColor = Color.red;
					}
					else if (iterationCount < 4){
						pixelColor = Color.cyan;
					}
					else if (iterationCount < 5){
						pixelColor = Color.white;
					}
					else if (iterationCount < 6){
						pixelColor = Color.orange;
					}
					else if (iterationCount < 7){
						pixelColor = Color.lightGray;
					}
					else if (iterationCount < 8){
						pixelColor = Color.gray;
					}
					else{
						pixelColor = Color.magenta;
					}
						fractalImage.setRGB(x,y,pixelColor.getRGB());  
                }
				
                }
		     
			
    }
	
	
	  private double getXPosition(double X){
        return  ( (2.0 / HEIGHT) * X );    
    }
    private double getYPosition(double Y){
        return ( (2.0 / WIDTH) * Y );
    }
	
	
	private int computeIterations(double Zr,double Zi){
  
        int iterCount = 0;
/* 
this will return the maximum number of iterations if the absolute value is less that 2 within the given number of iterations.
Else this will return the number of iterations ,after the absolute value less is than 2

*/
        while ( ( (Zr*Zr)+(Zi*Zi) )<= 4.0 ){
            double Zr_Temp = Zr;
         

            Zr = (Zr*Zr)-(Zi*Zi)+c_Real;
            Zi = (2*Zr_Temp*Zi)+c_Imag;

            if(iterCount >= iterNo){
                return iterCount;        
            }
            else{
                iterCount++;
            }
          

        }
        return  iterCount;
    }


}
