package gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Small class to start the simulation without menu.
 */
public class GUISimulation extends StateBasedGame {

	public GUISimulation(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//this.addState(new GameStateMenu());
		//this.addState(new GameStateLoad());
		this.addState(new GameStateSimRun());
		container.setTargetFrameRate(31);
		//Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER); 
	    //Renderer.getLineStripRenderer().setLineCaps(true);
		
	    
	}
	
	 public static void main(String[] argv) {  
	        try {  
	            AppGameContainer container = new AppGameContainer(new GUISimulation("Traffic simulation"));  
	            container.setDisplayMode(container.getScreenWidth(),container.getScreenHeight(),true);
	            container.setAlwaysRender(true);

	            container.start(); 
	        } catch (SlickException e) {  
	            e.printStackTrace();  
	        }  
	    } 

}
