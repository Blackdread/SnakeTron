package SnakeTron.views;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
 * @since 12 10 2012
 */
public class MainMenuView extends View{

	private Image background, logo;
	private Image jouer, option, quitter, credits;
	MouseOverArea buttonJouer, buttonOption, buttonQuitter, buttonCredits;
	Font font;
	
	public MainMenuView(GameContainer container){
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		//background = RessourceManager.getImage("menu.jpg");
		int x = container.getWidth() / 2;
		int y = container.getHeight() / 2;
		
		RessourceManager.addImage("menu", "menu.jpg");
		background = RessourceManager.getImage("menu");
		background = background.getScaledCopy(container.getWidth(), container.getHeight());
		
		RessourceManager.addImage("logo", "logo.png");
		logo = RessourceManager.getImage("logo").getScaledCopy(100, 100);
		
		RessourceManager.addImage("MenuJouer", "MenuJouer.png");
		RessourceManager.addImage("MenuJouerOver", "MenuJouerOver.png");
		jouer = RessourceManager.getImage("MenuJouer");
		buttonJouer = new MouseOverArea(container, jouer, x-75, y-25-60, 150, 50);
		buttonJouer.setMouseOverImage(RessourceManager.getImage("MenuJouerOver"));
		
		RessourceManager.addImage("MenuOption", "MenuOption.png");
		RessourceManager.addImage("MenuOptionOver", "MenuOptionOver.png");
		option = RessourceManager.getImage("MenuOption");
		buttonOption = new MouseOverArea(container, option, x-75, y-25+20, 150, 50);
		buttonOption.setMouseOverImage(RessourceManager.getImage("MenuOptionOver"));
		
		RessourceManager.addImage("MenuQuitter", "MenuQuitter.png");
		//RessourceManager.addImage("MenuQuitterDown", "MenuQuitterDown.jpg");
		RessourceManager.addImage("MenuQuitterOver", "MenuQuitterOver.png");
		quitter = RessourceManager.getImage("MenuQuitter");
		buttonQuitter = new MouseOverArea(container, quitter, x-75, y-25+100, 150, 50);
		buttonQuitter.setMouseOverImage(RessourceManager.getImage("MenuQuitterOver"));
		
		RessourceManager.addImage("MenuCredits", "MenuCredits.png");
		RessourceManager.addImage("MenuCreditsOver", "MenuCreditsOver.png");
		credits = RessourceManager.getImage("MenuCredits");
		buttonCredits = new MouseOverArea(container, credits, container.getWidth()-200, container.getHeight()-100, 150, 50);
		buttonCredits.setMouseOverImage(RessourceManager.getImage("MenuCreditsOver"));
		
		font = container.getDefaultFont();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		g.drawImage(background, 0, 0);
		g.drawImage(logo, 10, 10);
		//g.setColor(Color.cyan);
		font.drawString(container.getWidth()-font.getWidth(Game.VERSION)-5, container.getHeight()-font.getHeight(Game.VERSION)-5, Game.VERSION, Color.cyan);
		//g.drawString(Game.VERSION, container.getWidth()-110, container.getHeight()-20);
		buttonJouer.render(container, g);
		buttonOption.render(container, g);
		buttonQuitter.render(container, g);
		buttonCredits.render(container, g);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		switch(key){
		case Input.KEY_ESCAPE:
			gotoLastView();
			break;
		}
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if(buttonJouer.isMouseOver())
			gotoJouer();
		if(buttonOption.isMouseOver())
			gotoOption();
		if(buttonCredits.isMouseOver())
			gotoCredits();
		if(buttonQuitter.isMouseOver())
			gotoLastView();
	}

	private void gotoJouer() {
			container.setMouseGrabbed(false);
			game.enterState(Game.CHOIX_JEU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	private void gotoOption() {
			container.setMouseGrabbed(false);
			game.enterState(Game.OPTIONS_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	private void gotoCredits() {
		container.setMouseGrabbed(false);
		game.enterState(Game.CREDITS_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	private void gotoLastView() {
		container.setMouseGrabbed(false);
		game.enterState(Game.LAST_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.MAIN_MENU_VIEW_ID;
	}

}
