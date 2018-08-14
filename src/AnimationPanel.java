import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class AnimationPanel extends JPanel implements Runnable,MouseListener,KeyListener{
	private double ballX=0,ballY=0;
	private boolean down=true,right=true;
	private int xs[] =new int[5];//the X coordinate of the bricks
	private int ys[] =new int[3];//the Y coordinate of the bricks
	private static final int BRICKWIDTH=75,BRICKHEIGHT=35;
	private static final int BALLSIZE=16;
	private boolean bs[]=new boolean[xs.length*ys.length];//true if the ball did not hit the brick
	private ArrayList<Sprites> alBricks=new ArrayList<Sprites>();
	private boolean gameIsRunning=false;
	private int paddleX=400,paddleY=625;
	private static final int PADDLEWIDTH=60,PADDLEHEIGHT=18;
	private double ballXSpeed = 1, ballYSpeed = 1;
	private boolean startMenu=true;
	private boolean keyRight=false,keyLeft=false;//,keyDown=false,keyUp=false;
	private boolean gameOver=false;
	private int bricksDestroyed=0; 
	private long startTime,endTime;
	private boolean showMessage =false;
	private Sound  gameSound = new Sound("resource/GameplayMusic.wav");
	public AnimationPanel(){
	
		Thread t = new Thread(this);
		
		t.start();
		addMouseListener(this);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	Sprites ball = new Sprites("resource/Ball.gif"),paddle = new Sprites("resource\\Paddle.gif"),background= new Sprites("resource\\Background1.jpg"),startScreen=new Sprites("./resource/Opening Screen.jpg");
	
	public void paint(Graphics g){
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(background.getImg(),0,0,getWidth(),getHeight(),null);//drawls the background
		
		g.drawImage(ball.getImg(), (int)ballX, (int)ballY, BALLSIZE, BALLSIZE, null);//drawls the ball
		
		
		
		
		
		
		if(!gameIsRunning){
			//initializes all the bs's to true
			for(int i=0;i<bs.length;i++){
			bs[i]=true;
		} 	//initializes the X coordinate for the bricks
			for(int i=0,k=50;i<xs.length;i++,k+=100){	
				xs[i]=k;
				
			}gameSound.stopMusic();
			//initializes the Y coordinate for the bricks
			for(int i=0,k=50;i<ys.length;i++,k+=100)	
				ys[i]=k;
		
			
			//choose the color for the bricks
			
			for (int k=0;k<ys.length*xs.length;k++){
				long r=(long) (Math.random()*4);
				alBricks.add(new Sprites(r));
				gameIsRunning=true;
			}
		
			
		}
		//draw the bricks 
		for (int y2=0,i=0;ys.length>y2;y2++){
			for (int x2=0;xs.length>x2;x2++,i++){
				if (bs[i]){
					g.drawImage(alBricks.get(i).getImg(), xs[x2], ys[y2], BRICKWIDTH, BRICKHEIGHT, null);
					
				}
			}
		}
			//draw paddle
			g.drawImage(paddle.getImg(),paddleX, paddleY, PADDLEWIDTH, PADDLEHEIGHT,null);
		
		//the startScreen
		if(startMenu){
			g.drawImage(startScreen.getImg(), 0, 0, getWidth(), getHeight(), null);
			ballX=300;ballY=400;
			paddleX=getWidth()/2+175;
			ballXSpeed=1;
			ballYSpeed=1;
			right=true;
			down=true;
	
		}
			
	}
	public void run(){
		while(!gameOver){
			
			//ball will bonus if it hits the top or sides
		if(ballX<0)right=true;
		if(ballY<0){down=true;}
		if(ballX>=getWidth()-BALLSIZE-1)right=false;
		
		//if the ball hits the bottom 
		if(gameIsRunning){
			if(ballY==getHeight()-BALLSIZE){
				gameIsRunning=false;
				startMenu=true;
				
			}

			
		}//if there are no more brick go to startMenu menu 
		for(int i =0; i<bs.length;i++){	
			if(!bs[i]&&!startMenu){
				bricksDestroyed++;
			if(bricksDestroyed==bs.length){
				gameIsRunning=false;
				startMenu=true;
				endTime=System.nanoTime();
				showMessage =true;
				
			}	
				}else{ bricksDestroyed=0;
					break;}
				
		}
		
		//collision with the paddle
		if (ballX<paddleX+PADDLEWIDTH&&ballX>paddleX-BALLSIZE&&ballY>=paddleY-BALLSIZE&&ballY<=paddleY-BALLSIZE+3){
			if (ballX>paddleX-BALLSIZE&&ballX<paddleX+PADDLEWIDTH/3){ballXSpeed=1.5;down = false;}
			else if (ballX>(paddleX+PADDLEWIDTH/3)-BALLSIZE&&ballX<paddleX+PADDLEWIDTH-PADDLEWIDTH/3){ballXSpeed=1;down=false;}
			else if (ballX>(paddleX+PADDLEWIDTH-PADDLEWIDTH/3)){ballXSpeed=1.5;down=false;}
		}else if ( ballX<paddleX+PADDLEWIDTH/2&&ballX>paddleX-BALLSIZE&&ballY>=paddleY-BALLSIZE&& ballY<=paddleY-BALLSIZE+PADDLEHEIGHT)right=false;
		else if ( ballX<paddleX+PADDLEWIDTH&&ballX>paddleX-BALLSIZE+(PADDLEWIDTH/2)&&ballY>=paddleY-BALLSIZE&& ballY<=paddleY-BALLSIZE+PADDLEHEIGHT)right=true;
		if(gameIsRunning){
		if (paddleX>getWidth()+1)paddleX=0-PADDLEWIDTH;
		if (paddleX<0-PADDLEWIDTH-1)paddleX=getWidth();
		}
		//collision with the bricks
		for(int y2=0,i=0; y2<ys.length;y2++){	
			 for(int x2=0;x2<xs.length;x2++,i++){	
				 if (bs[i]&&((((ys[y2]+BRICKHEIGHT>ballY&&ys[y2]+BRICKHEIGHT-3<ballY)||(ys[y2]-BALLSIZE<ballY&&ys[y2]-BALLSIZE+3>ballY))&&(ballX>xs[x2]-BALLSIZE&&ballX<xs[x2]+BRICKWIDTH)) || (((xs[x2]-BALLSIZE<ballX&&xs[x2]-BALLSIZE+3>ballX)||(xs[x2]+BRICKWIDTH>ballX&&xs[x2]+BRICKWIDTH-3<ballX))&&(ys[y2]-BALLSIZE<ballY&&ys[y2]+BRICKHEIGHT-1>ballY)))){
					 if((ys[y2]+BRICKHEIGHT>ballY&&ys[y2]+BRICKHEIGHT-3<ballY&&!down)||(ys[y2]-BALLSIZE<ballY&&ys[y2]-BALLSIZE+3>ballY&&down))
					
					 	down=!down;
					 else 
						 right=!right;
					 bs[i]=false;
			
			
				 }
			}
		}
		
		//paddle move
		if(keyRight)paddleX+=2;
		if(keyLeft)paddleX-=2;
	
		
		//the direction of the ball
		if(down){
		if(right)ballX+=ballXSpeed;
		else ballX-=ballXSpeed;
		ballY+=ballYSpeed;
		}
		else {
			if(right)ballX+=ballXSpeed;
			else ballX-=ballXSpeed;
			ballY-=ballYSpeed;
		}
		
		repaint();
		// message box when you win shows time in seconds
		if(showMessage){
			JOptionPane.showMessageDialog(null,
					(endTime-startTime)/1000000000+" Seconds",
			        "You win",
			        JOptionPane.INFORMATION_MESSAGE);
				showMessage=false;
				}
		try {
			
			Thread.sleep(5);
		}catch(Exception e){
			
		}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//start button
		if((e.getX()>40&&e.getX()<40+195&&e.getY()>280&&e.getY()<280+50)&&startMenu){
			startMenu=false;
			startTime=System.nanoTime();
			gameSound.startMusic();
		}
		
		//help button
		if(e.getX()>40&&e.getX()<40+195&&e.getY()>350&&e.getY()<350+50&&startMenu)JOptionPane.showMessageDialog(null,
				"press the right and left arrows to move the paddle\ntry to destroy all the bricks\n if you hit the ball in the middle of the paddle the ball will move slower than if you hit it more on the sides",
		        "help",
		        JOptionPane.INFORMATION_MESSAGE);

		//exit button
		if((e.getX()>40&&e.getX()<40+195&&e.getY()>420&&e.getY()<420+50)&&startMenu){System.exit(0);}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT)keyLeft=true;
		if(key==KeyEvent.VK_RIGHT)keyRight=true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key =e.getKeyCode();
		if(key==KeyEvent.VK_LEFT)keyLeft=false;
		if(key==KeyEvent.VK_RIGHT)keyRight=false;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		

}	public boolean gameOver(){
	return gameOver;
}

		  }
