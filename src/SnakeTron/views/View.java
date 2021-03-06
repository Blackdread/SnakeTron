package SnakeTron.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import SnakeTron.engine.Game;

/**
 * This class represent advance game state like "in game" phases.
 * 
 * @author Yoann CAPLAIN
 * 
 */
public abstract class View extends BasicGameState {

	protected GameContainer container;
	protected Game game;

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.game = (Game) game;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//this.game.updateTWL();
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		switch (key) {
		case Input.KEY_F1:
			takeScreenShot();
			break;
		default:
			break;
		}
	}

	private void takeScreenShot() {
		try {
			Image image = new Image(container.getWidth(), container.getHeight());
			container.getGraphics().copyArea(image, 0, 0);
			ImageOut.write(image, "screenshot/screenshot_" + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(Calendar.getInstance().getTime()) + ".jpg");
		} catch (Exception e) {
			System.err.println("Could not save screenshot: " + e.getMessage());
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	
		
	}

	/**
	 * Developer must initialize the state resources here.
	 * 
	 * @param container
	 *            The game container associated to the game context.
	 * @param game
	 *            The Game context.
	 */
	public abstract void initResources();

	//public abstract void initOthersComponent();


}
