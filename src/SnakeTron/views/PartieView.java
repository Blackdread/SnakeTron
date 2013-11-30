package SnakeTron.views;

import org.newdawn.slick.Color;
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
import SnakeTron.engine.Partie;
import SnakeTron.engine.Player;
import SnakeTron.engine.gui.Slider;
import SnakeTron.utils.Colors;
import SnakeTron.utils.RessourceManager;
/**
 * 
 * @author Yoann CAPLAIN
 * @since 13 10 2012
 */
public class PartieView extends View {

	private Image background;
	private Image quitter, reset, plus, moins;
	private MouseOverArea buttonReset, buttonQuitter, buttonPlus, buttonMoins;
	
	private Partie partie;
	private boolean pause=true;
	private Slider sliderVitesseJeu;
	
	public PartieView(GameContainer container){
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		// Singleton instance
		partie = Partie.instance();
		
		int x = container.getWidth();
		int y = container.getHeight();
		
		RessourceManager.addImage("game3", "game3.jpg");
		background = RessourceManager.getImage("game3");
		background = background.getScaledCopy(x, y);
		
		quitter = RessourceManager.getImage("MenuQuitterOver");
		buttonQuitter = new MouseOverArea(container, quitter, x/10, y-y/10, 150, 50);
		buttonQuitter.setMouseOverImage(RessourceManager.getImage("MenuQuitter"));
		
		RessourceManager.addImage("restart", "restart.png");
		RessourceManager.addImage("restartOver", "restartOver.png");
		reset = RessourceManager.getImage("restartOver");
		buttonReset = new MouseOverArea(container, reset, x/10 + 190, y-y/10, 150, 50);
		buttonReset.setMouseOverImage(RessourceManager.getImage("restart"));
		
		RessourceManager.addImage("plus", "plus.png");
		RessourceManager.addImage("plusOver", "plusOver.png");
		plus = RessourceManager.getImage("plusOver");
		buttonPlus = new MouseOverArea(container, plus, x/10-80, y/10+140, 50, 50);
		buttonPlus.setMouseOverImage(RessourceManager.getImage("plus"));
		RessourceManager.addImage("moins", "moins.png");
		RessourceManager.addImage("moinsOver", "moinsOver.png");
		moins = RessourceManager.getImage("moinsOver");
		buttonMoins = new MouseOverArea(container, moins, x/10-40, y/10+140, 50, 50);
		buttonMoins.setMouseOverImage(RessourceManager.getImage("moins"));
		
		sliderVitesseJeu = new Slider(container, RessourceManager.getImage("slider").getScaledCopy(120, 20), RessourceManager.getImage("sliderCursor"), 20, y/10+200, partie.getWAIT_TIME_BEFORE_NEXTR(), 0, 100, true);
		sliderVitesseJeu.getCursor().setMouseOverImage(RessourceManager.getImage("sliderCursorOver"));
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbGame, int delta) throws SlickException {
		super.update(container, sbGame, delta);
		if(!pause)
			partie.update(container, sbGame, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		int x = container.getWidth();
		int y = container.getHeight();
		
		g.drawImage(background, 0, 0);
		
		partie.render(container, game, g);
		
		g.setColor(Color.red);
		if(pause && !partie.isPartieGagne())
			g.drawString("Appuyer sur Espace pour relancer la partie", x/2 - 165 , y/2 + 10);
		
		//if(partie.isPartieGagne())	// Dans partie.render
		
		g.setColor(Color.orange);
		g.drawString("Lenteur : "+partie.getWAIT_TIME_BEFORE_NEXTR(), x/10-90, y/10+120);
		sliderVitesseJeu.render(container, g);
		
		int ii=0;
		g.setColor(Color.orange);
		g.drawString("Match gagné", 5, 42);
		for(Player v : partie.getPlayer())
			if(v!=null){
				g.setColor(Colors.getColor(v.getColor()));
				g.drawString("Joueur "+(v.getId()+1)+" : "+v.getWin(), 10, 60+ii*20);
				ii++;
			}
		ii=0;
		g.setColor(Color.orange);
		g.drawString("Nb de pomme", 5, y/10+240);
		for(Player v : partie.getPlayer())
			if(v!=null){
				g.setColor(Colors.getColor(v.getColor()));
				g.drawString("Joueur "+(v.getId()+1)+" : "+v.getNbPomme(), 10, y/10+260+ii*20);
				ii++;
			}
		
		buttonMoins.render(container, g);
		buttonPlus.render(container, g);
		buttonReset.render(container, g);
		buttonQuitter.render(container, g);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if(key == Input.KEY_SPACE)
			if(pause){
				pause=false;
			}else if(partie.isPartieGagne()){
				partie.restartPartieApresWin();
			}else{
				pause=true;
			}
		if(key == Input.KEY_DELETE || key == Input.KEY_BACK)
			restartPartie();
		if(key == Input.KEY_MINUS){
			partie.decrementWAIT_TIME_BEFORE_NEXTR();
			if(partie.getWAIT_TIME_BEFORE_NEXTR()<0)
				partie.setWAIT_TIME_BEFORE_NEXTR(1);
			partie.restartTimer();
			sliderVitesseJeu.setValue((float)partie.getWAIT_TIME_BEFORE_NEXTR());
			sliderVitesseJeu.checkCursorPosition();
		}
		if(key == Input.KEY_EQUALS){
			partie.incrementWAIT_TIME_BEFORE_NEXTR();
			partie.restartTimer();
			sliderVitesseJeu.setValue((float)partie.getWAIT_TIME_BEFORE_NEXTR());
			sliderVitesseJeu.checkCursorPosition();
		}
		
		partie.keyPressed(key, c);
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		if(buttonQuitter.isMouseOver()){
			initPartie();
			goToMenu();
		}
		if(buttonReset.isMouseOver())
			restartPartie();
		if(buttonMoins.isMouseOver()){
			partie.decrementWAIT_TIME_BEFORE_NEXTR();
			partie.restartTimer();
			sliderVitesseJeu.setValue((float)partie.getWAIT_TIME_BEFORE_NEXTR());
			sliderVitesseJeu.checkCursorPosition();
		}
		if(buttonPlus.isMouseOver()){
			partie.incrementWAIT_TIME_BEFORE_NEXTR();
			partie.restartTimer();
			sliderVitesseJeu.setValue((float)partie.getWAIT_TIME_BEFORE_NEXTR());
			sliderVitesseJeu.checkCursorPosition();
		}
		if(sliderVitesseJeu.mouseReleased()){
			//Configuration.(sliderVitesseJeu.getValuePrecision2());
			partie.setWAIT_TIME_BEFORE_NEXTR((int)sliderVitesseJeu.getValue());
			partie.restartTimer();
		}
		partie.keyPressed(button, '0');	// POUR LE JOUEUR 6
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		super.mouseDragged(oldx, oldy, newx, newy);
		sliderVitesseJeu.isMouseGrabbed(oldx, oldy, newx, newy);
		// Pas sur que je gardes ca
		partie.setWAIT_TIME_BEFORE_NEXTR((int) sliderVitesseJeu.getValue());
	}
	
	private void initPartie(){
		pause=true;
		partie.initInstance();
	}
	private void restartPartie(){
		pause=true;
		partie.restartPartie();
	}
	
	private void goToMenu() {
		container.setMouseGrabbed(false);
		game.enterState(Game.MAIN_MENU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.PARTIE_VIEW_ID;
	}

}
