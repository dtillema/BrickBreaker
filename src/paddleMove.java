import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


public class paddleMove extends JPanel implements Runnable, KeyListener {
	
	public paddleMove(){
	Thread t=new Thread(this);
	t.start();
	addKeyListener(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				Thread.sleep(1);
			}catch(Exception e){
				
			}
			}
			
		}
		@Override
		
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
			int key = arg0.getKeyCode();
			if (KeyEvent.VK_UP==key)
			System.out.println("hi");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
