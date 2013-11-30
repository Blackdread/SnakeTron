package SnakeTron.views;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import SnakeTron.engine.GameMusic;

import SnakeTron.engine.Game;
import SnakeTron.utils.Configuration;
import SnakeTron.utils.RessourceManager;
import SnakeTron.utils.Timer;
/**
 * 
 * @author Yoann CAPLAIN
 * @since 12 10 2012
 */
public class PresentationView extends View {

	private static final int WAIT_TIME_BEFORE_NEXTR = 2000;
	
	private Image background;
	private boolean ready;	// pret a passer au menu
	private Timer timer;
	
	public PresentationView(GameContainer container){
		timer = new Timer(WAIT_TIME_BEFORE_NEXTR);
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		RessourceManager.addImage("intro", "intro2.png");
		background = RessourceManager.getImage("intro");
		background = background.getScaledCopy(container.getWidth(), container.getHeight());
		
		// Init Music and Sounds
		if(Configuration.isMusicOn()){
			GameMusic.initMainTheme();
			GameMusic.setMusicVolume(Configuration.getMusicVolume());
			GameMusic.loopMainTheme();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbGame, int delta) throws SlickException {
		super.update(container, sbGame, delta);
		timer.update(delta);
		if (timer.isTimeComplete()) {
			// Voir si je ne fais pas d'autres trucs entre temps... (charger les images du prochain etat...)
			ready = true;
			timer.resetTime();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		super.render(container, game, g);
		if (ready) {
			g.drawString("Press a key or click", container.getWidth() / 2 - 90, container.getHeight() / 2 + 10);
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		goToMenu();
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		goToMenu();
	}
	
	private void goToMenu() {
		if (ready) {
			container.setMouseGrabbed(false);
			game.enterState(Game.MAIN_MENU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.PRESENTATION_STATE_ID;
	}

}
