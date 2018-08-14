import javax.swing.JFrame;


public class Driver extends JFrame {
//make new panel
	public Driver() {
		super("Brick Breaker AHHHHHH");
		AnimationPanel ap = new AnimationPanel();
		setContentPane(ap);
		setSize(600,700);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String []args){
		
		Driver Af = new Driver();
		Af.setVisible(true);
		
	}
}
