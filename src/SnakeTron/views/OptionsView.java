package SnakeTron.views;

import java.io.IOException;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
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
import SnakeTron.engine.GameMusic;
import SnakeTron.engine.gui.ListeDeroulante;
import SnakeTron.engine.gui.Slider;
import SnakeTron.utils.Configuration;
import SnakeTron.utils.Resolution;
import SnakeTron.utils.RessourceManager;
/**
 * 
 * @author Yoann CAPLAIN
 * @since 12 10 2012
 */
public class OptionsView extends View{

	private Image background, backgroundUp, listeElement,listeElementOver;
	private Image quitter;
	private Image error;
	MouseOverArea buttonQuitter, buttonFullscreen, buttonSonDesacti, buttonSonActi;
	
	private ListeDeroulante listeDerTailleScreen;
	private Slider sliderMusic;
	
	private static boolean errorON;
	private boolean errorFullScreenON;
	private static boolean errorChangeScreenON;
	
	public OptionsView(GameContainer container){
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		int x = container.getWidth();
		int y = container.getHeight();
		
		RessourceManager.addImage("option", "option.jpg");
		background = RessourceManager.getImage("option");
		background = background.getScaledCopy(x, y);
		RessourceManager.addImage("fondOption", "fondOption.png");
		backgroundUp = RessourceManager.getImage("fondOption");
		backgroundUp = backgroundUp.getScaledCopy(x, y-y/10-20);
		
		RessourceManager.addImage("listeDeroulante", "listeDeroulante.png");
		RessourceManager.addImage("listeDeroulanteOver", "listeDeroulanteOver.png");
		RessourceManager.addImage("listeElement", "listeElement.png");
		RessourceManager.addImage("listeElementOver", "listeElementOver.png");
		listeElement = RessourceManager.getImage("listeElement");
		listeElementOver = RessourceManager.getImage("listeElementOver");
		
		RessourceManager.addImage("fullscreen", "fullscreen.png");
		RessourceManager.addImage("fullscreenOver", "fullscreenOver.png");
		buttonFullscreen = new MouseOverArea(container, RessourceManager.getImage("fullscreenOver"), 50, 50, 150, 50);
		buttonFullscreen.setMouseOverImage(RessourceManager.getImage("fullscreen"));
		
		RessourceManager.addImage("error", "error.png");
		error = RessourceManager.getImage("error");
		
		quitter = RessourceManager.getImage("MenuQuitterOver");
		buttonQuitter = new MouseOverArea(container, quitter, x/10, y-y/10, 150, 50);
		buttonQuitter.setMouseOverImage(RessourceManager.getImage("MenuQuitter"));
		
		RessourceManager.addImage("activer", "activer.png");
		RessourceManager.addImage("activerOver", "activerOver.png");
		RessourceManager.addImage("desactiver", "desactiver.png");
		RessourceManager.addImage("desactiverOver", "desactiverOver.png");
		buttonSonActi = new MouseOverArea(container, RessourceManager.getImage("activerOver"), 50, 250, 150, 50);
		buttonSonActi.setMouseOverImage(RessourceManager.getImage("activer"));
		buttonSonDesacti = new MouseOverArea(container, RessourceManager.getImage("desactiverOver"), 50, 250, 150, 50);
		buttonSonDesacti.setMouseOverImage(RessourceManager.getImage("desactiver"));
		
		RessourceManager.addImage("slider", "slider.png");
		RessourceManager.addImage("sliderCursor", "sliderCursor.png");
		RessourceManager.addImage("sliderCursorOver", "sliderCursorOver.png");
		
		
		errorON=false;
		errorFullScreenON=false;
		
		listeDerTailleScreen = new ListeDeroulante((AppGameContainer)container, RessourceManager.getImage("listeDeroulante").getScaledCopy(150, 40), 250, 55);
		listeDerTailleScreen.setMouseOverImage(RessourceManager.getImage("listeDeroulanteOver").getScaledCopy(150, 40));
		
		DisplayMode modes[] = Resolution.getAvailableResolution();
		if(modes!=null)
		for (int i=0;i<modes.length;i++) {
            DisplayMode current = modes[i];
            if(current.getHeight() >= Game.MINIMUM_SCREEN_HAUTEUR)
            	if(!(new Resolution(current.getWidth(), current.getHeight()).equals(new Resolution(container.getWidth(), container.getHeight()))))
            		listeDerTailleScreen.addElementResolution(container, listeElement, current.getWidth(), current.getHeight(), 150, 25);
        }
		listeDerTailleScreen.chercherElementUsed();
		listeDerTailleScreen.applyImageOverAllElement(listeElementOver);
		
		sliderMusic = new Slider(container, RessourceManager.getImage("slider"), RessourceManager.getImage("sliderCursor"), container.getWidth()/14, 190, Configuration.getMusicVolume(), 0, 1, true);
		sliderMusic.getCursor().setMouseOverImage( RessourceManager.getImage("sliderCursorOver"));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		int x = container.getWidth();
		int y = container.getHeight();
		
		g.drawImage(background, 0, 0);
		g.drawImage(backgroundUp, 0, 0);
		
		buttonFullscreen.render(container, g);
		g.setColor(Color.white);
		sliderMusic.render(container, g);
		g.drawString("Volume de la musique :"+sliderMusic.getValuePrecision2(), sliderMusic.getX()+10, sliderMusic.getY()-32);
		
		listeDerTailleScreen.render(container, g);
		
		if(Configuration.isMusicOn())
			buttonSonDesacti.render(container, g);
		else
			buttonSonActi.render(container, g);
		
		renderToucheJoueur(container,g);
		
		if(errorON)
			g.drawImage(error, x/2-error.getWidth()/2, y/2-error.getHeight()/2);
		if(errorFullScreenON)
			g.drawString("Impossible de mettre en plein écran", x/2-155, y/2-10);
		if(errorChangeScreenON){
			g.drawString("Impossible de mettre cette", x/2-115, y/2-30);
			g.drawString("résolution d'écran", x/2-90, y/2-10);
		}
		
		buttonQuitter.render(container, g);
		super.render(container, game, g);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		undoError();
		switch(key){
		case Input.KEY_ESCAPE:
			goToMenu();
			break;
		}
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		undoError();
		if(buttonQuitter.isMouseOver())
			goToMenu();
		if(buttonFullscreen.isMouseOver())
			inverseFullscreen();
		if(buttonSonDesacti.isMouseOver()){
			if(Configuration.isMusicOn()){
				Configuration.setMusicOn(false);
				GameMusic.stopMainTheme();
			}else{//(buttonSonActi.isMouseOver())
				Configuration.setMusicOn(true);
				GameMusic.loopMainTheme();
			}
		}
		
		listeDerTailleScreen.isMouseOver();
		if(sliderMusic.mouseReleased()){
			Configuration.setMusicVolume(sliderMusic.getValuePrecision2());
			float temp1 = GameMusic.getMusicPosition();
			GameMusic.setMusicVolume(sliderMusic.getValuePrecision2());
			GameMusic.setMusicPostion(temp1);
		}
	}
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		super.mouseDragged(oldx, oldy, newx, newy);
		sliderMusic.isMouseGrabbed(oldx, oldy, newx, newy);
	}
	
	private void undoError(){
		errorON=false;
		errorFullScreenON=false;
		errorChangeScreenON=false;
	}
	private void inverseFullscreen(){
		if(!container.isFullscreen())
			try{
				container.setFullscreen(true);
				Configuration.setFullScreen(true);
			}catch(Exception e){
				errorON=true;
				errorFullScreenON=true;
			}
		else
			try{
				container.setFullscreen(false);
				Configuration.setFullScreen(false);
			}catch(Exception e){
				errorON=true;
				errorFullScreenON=true;
			}
	}
	
	private void goToMenu() {
			container.setMouseGrabbed(false);
			try {
				Configuration.saveNewConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
			game.enterState(Game.MAIN_MENU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.OPTIONS_VIEW_ID;
	}

	public static void setErrorChangeScreenTrue(){
		errorChangeScreenON=true;
		errorON=true;
	}
	
	public void renderToucheJoueur(GameContainer container, Graphics g){
		g.setColor(Color.cyan);
		g.drawString("Touches joueur :",container.getWidth()-240, 30);
		//*
		for(int i=0;i<6;i++){
			g.drawString("Joueur "+(i+1)+" :", container.getWidth()-335, 50+i*20);
		}
		//*/
		g.drawString("a(ou q), z (ou w), s, d", container.getWidth()-240, 50);
		g.drawString("les touches fléchées", container.getWidth()-240, 50+20);
		g.drawString("g, y, h, j", container.getWidth()-240, 50+40);
		g.drawString("k, o, l, m (ou ;)", container.getWidth()-240, 50+60);
		g.drawString("Numpad 4, 8, 5, 6", container.getWidth()-240, 50+80);
		g.drawString("Souris droite et gauche", container.getWidth()-240, 50+100);
		
		// Screenshot
		g.drawString("F1 pour prendre un\nscreenschot", container.getWidth()-240, 50+140);
	}
}
