package SnakeTron.views;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import SnakeTron.engine.Game;
import SnakeTron.utils.RessourceManager;

/**
 * 
 * @author Yoann CAPLAIN
 * @since 09 10 2012
 */
public class CreditsView extends View{
	
	private Image background;
	private Image quitter;
	MouseOverArea buttonQuitter;
	
	public CreditsView(GameContainer container){
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		background = RessourceManager.getImage("option");
		background = background.getScaledCopy(container.getWidth(), container.getHeight());
		
		RessourceManager.addImage("credits", "credits.png");
		
		try {
			Graphics temp = background.getGraphics();
			temp.drawImage(RessourceManager.getImage("credits").getScaledCopy(container.getWidth(), container.getHeight()), 0, 0);
			temp.flush();
			temp=null;
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		//background = RessourceManager.getImage("credits");
		//background = background.getScaledCopy(container.getWidth(), container.getHeight());
		
		
		quitter = RessourceManager.getImage("MenuQuitterOver");
		buttonQuitter = new MouseOverArea(container, quitter, container.getWidth()/10, container.getHeight()-container.getHeight()/10, 150, 50);
		//buttonQuitter.setMouseDownImage(RessourceManager.getImage("MenuQuitterDown"));
		buttonQuitter.setMouseOverImage(RessourceManager.getImage("MenuQuitter"));
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbGame, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		buttonQuitter.render(container, g);
		super.render(container, game, g);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		if(buttonQuitter.isMouseOver())
			goToMenu();
	}

	private void goToMenu() {
		container.setMouseGrabbed(false);
		game.enterState(Game.MAIN_MENU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	
	@Override
	public int getID() {
		return Game.CREDITS_VIEW_ID;
	}

}
