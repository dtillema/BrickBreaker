import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import sun.audio.*;

public class Sound implements ActionListener{
	private static String file;
	AudioPlayer MGP=AudioPlayer.player;
	AudioStream BGM;
	AudioData MD ;
	ContinuousAudioDataStream loop = null;
	//takes in the file location 
	public Sound(String s){
		file = s;
		
	
	}//plays Music in a continues loop
	public  void startMusic(){
		
		
		try{
		InputStream test = new FileInputStream(file);
			
		BGM = new AudioStream(test);
		AudioPlayer.player.start(BGM);
		
		}catch (IOException e){
			System.out.println(e);
		}
		
		
		MGP.start(loop);
		
		
		//Stops the Music
	}public void stopMusic()
	{
		MGP.stop(BGM);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		}
	
}

