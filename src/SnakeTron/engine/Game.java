package SnakeTron.engine;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import SnakeTron.utils.Configuration;
import SnakeTron.views.ChoixJeuView;
import SnakeTron.views.CreditsView;
import SnakeTron.views.LastView;
import SnakeTron.views.PartieView;
import SnakeTron.views.PresentationView;
import SnakeTron.views.MainMenuView;
import SnakeTron.views.OptionsView;
import SnakeTron.views.View;

/**
 * 
 * @author Yoann CAPLAIN
 * @since 09 10 2012
 */
public class Game extends StateBasedGame {
	private static AppGameContainer container;
	
	public static final int MINIMUM_SCREEN_HAUTEUR = 768;
	
	// State IDS
	public static final int PRESENTATION_STATE_ID = 0;
	//public static final int RESOURCES_STATE_ID = 1;
	public static final int MAIN_MENU_VIEW_ID = 1;
	//public static final int NETWORK_VIEW_ID = 3;
	public static final int OPTIONS_VIEW_ID = 2;
	public static final int CREDITS_VIEW_ID = 3;
	public static final int CHOIX_JEU_VIEW_ID = 4;
	public static final int PARTIE_VIEW_ID = 5;
	//public static final int ENGINE_VIEW_ID = 6;
	//public static final int CREATE_VIEW_ID = 7;
	public static final int LAST_VIEW_ID = 6;
	
	/**
	 * The current name of the project.
	 */
	public static final String NAME = "Snake Tron By Yoann CAPLAIN 09-10-2012";
	/**
	 * The current version of the project.
	 */
	public static final String VERSION = "Version 1.0";
	
	private static ArrayList<View> states;

	
	public Game(String configFileLocation) throws IOException, SlickException {
        super(NAME);
        states = new ArrayList<View>();
        
        Configuration.init(configFileLocation);
    }
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		//addState(new ResourcesView(container));
		addState(new PresentationView(container));
		addState(new MainMenuView(container));
		//addState(new NetworkView());
		addState(new OptionsView(container));
		addState(new CreditsView(container));
		addState(new ChoixJeuView(container));
		addState(new PartieView(container));
		//addState(new Engine());
		//addState(new CreateView());
		addState(new LastView(container));
	}	
	/**
	 * Entry point to launch the game.
	 * 
	 * @throws SlickException
	 * @throws IOException
	 * @throws LWJGLException 
	 */
	public void launch() throws SlickException, IOException {
		//AppGameContainer container = new AppGameContainer(this);
		container = new AppGameContainer(this);
		
		// Icon		ici car ca a besoin d'etre mis avant que le container ne commence
		String icon[] = {"ressources/images/ico16.png", "ressources/images/ico24.png","ressources/images/ico32.png"};
		try {
			container.setIcons(icon);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		// Mandatory
		container.setMinimumLogicUpdateInterval(10);
		container.setMaximumLogicUpdateInterval(10);
		container.setUpdateOnlyWhenVisible(false);
		container.setAlwaysRender(true);
		
		// Apply Configuration
		applyCurrentConfiguration(container);

		// Start the game
		container.start();
	}
	
	
	private void applyCurrentConfiguration(AppGameContainer container) throws IOException, SlickException {
		Configuration.updateConfigFile();
		container.setDisplayMode(Configuration.getWidth(), Configuration.getHeight(), Configuration.isFullScreen());  
		container.setTargetFrameRate(Configuration.getTargetFPS());
		container.setSmoothDeltas(Configuration.isSmoothDeltas());
		container.setVSync(Configuration.isVSynch());
		container.setMusicVolume(Configuration.getMusicVolume());
		container.setMusicOn(Configuration.isMusicOn());
		container.setSoundVolume(Configuration.getSoundVolume());
		container.setShowFPS((Configuration.isDebug()) ? true : false);
		container.setVerbose((Configuration.isDebug()) ? true : false);
	}
	
	/**
	 * Apply the current configuration to the game container of the game
	 * context.
	 * 
	 * @throws IOException
	 *             If the configuration loading failed.
	 * @throws SlickException
	 *             If the configuration loading failed.
	 */
	public void applyCurrentConfiguration() throws IOException, SlickException {
		applyCurrentConfiguration((AppGameContainer) this.getContainer());
	}
	
	@Override
	public void addState(GameState state) {
		super.addState(state);
		states.add((View) state);
	}

	public View getStateByIndex(int index) {
		return (View) states.get(index);
	}
	 
	public static void changeResolution(int width, int height) throws SlickException{
		((AppGameContainer) Game.container).setDisplayMode(width, height, Configuration.isFullScreen());
	}
	/**
	 * Plus simple que de devoir recharger que celles dont on a besoin. C'est un petit jeu apres tout :)
	 */
	public static void rechargerToutesLesRessources(){
		for(View v : states)
			if(v!=null){
				v.initResources();
			}
		
	}
	
	public static void rechargerLesRessourcesDuAuChangementDeResolution(){
		
	}
	
	
}
