import java.awt.Image;
import javax.swing.ImageIcon;

// returns an image
public class Sprites{ 
	//takes in the file location 
	public Sprites(long lin){
		 
		this(new ImageIcon(random(lin)).getImage());
	}

  private Image img;
  public Sprites(String img) {
    this(new ImageIcon(img).getImage());

  }

  public Sprites(Image img) {
    this.img = img;
  }
  
  public static String random(long in){

	  if(in==0) return "resource\\Blue-Brick.gif";
	  else if(in==1) return "resource\\Red-Brick.gif";
	  else if(in==2) return "resource\\Green-Brick.gif";
	  else return "resource\\Pink-Brick.gif";

	  
	  
  }
  
  public Image getImg(){
	  return this.img;
  }
  


	
}


