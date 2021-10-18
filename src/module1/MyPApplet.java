package module1;

import processing.core.PApplet;
import processing.core.PImage;

public class MyPApplet extends PApplet {
	private String URL = "https://upload.wikimedia.org/wikipedia/commons/1/13/Brasil_-_Paran%C3%A1_-_Irati_-_Vista_de_la_rua_15_de_Novembro.jpg";
	private PImage backgroundImg;

	
	public void setup() {
		size(400,400);
		backgroundImg = loadImage(URL,"jpg");
		backgroundImg.resize(0, height);//height is an instance variable that tells the number of pixels high the pic is	

		
	}
	public void draw() {
		int[] color = sunColorSec(second());
		image(backgroundImg,0,0);
		fill(color[0],color[1],color[2]);
		ellipse(width/4,height/5,width/5,height/5);
	}
	public int[] sunColorSec(float seconds) {
		int[] rgb = new int[3];
		//scale the brightness of the yellow based on the seconds
		//30seconds is black, 0 is bright yellow
		float diffFrom30 = Math.abs(30-seconds);
		
		float ratio = diffFrom30/30;//will be between 0 and 1;
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		
		return rgb;
		
	}
}
