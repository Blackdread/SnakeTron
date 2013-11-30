package SnakeTron.views;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import SnakeTron.engine.Game;
import SnakeTron.engine.Partie;
import SnakeTron.engine.Player;
import SnakeTron.utils.Colors;
import SnakeTron.utils.RessourceManager;
/**
 * 
 * @author Yoann CAPLAIN
 * @since 12 10 2012
 */
@SuppressWarnings("deprecation")
public class ChoixJeuView extends View{

	private Image background, backgroundUp;
	private Image quitter, activer, desactiver;
	private MouseOverArea buttonPommeModActi, buttonPommeModDesacti, buttonJouer, buttonQuitter, buttonFondNoirActi, buttonFondNoirDesacti;
	private MouseOverArea buttonPommeFurieActi, buttonPommeFurieDesacti, buttonTraverserDesacti, buttonTraverserActi;
	
	private boolean modPommeON, modPommeFurieON;
	private static boolean modTraverserON;
	private boolean fondNoirON;
	
	private TextField color[];
	private TextField team[]; 
	private TextField joueur[];
	
	private Partie partie;
	
	public static final int TAILLE_TABLEAU = 300;
	
	public ChoixJeuView(GameContainer container){
		this.container = container;
		initResources();
	}
	
	@Override
	public void initResources() {
		// Singleton instance
		partie = Partie.instance();
		
		int x = container.getWidth();
		int y = container.getHeight();
		
		modPommeON=false;
		modPommeFurieON=false;
		modTraverserON=false;
		
		RessourceManager.addImage("game", "game.jpg");
		background = RessourceManager.getImage("game");
		background = background.getScaledCopy(x, y);
		
		backgroundUp = RessourceManager.getImage("fondOption");
		backgroundUp = backgroundUp.getScaledCopy(x, y-y/10-20);
		
		buttonJouer = new MouseOverArea(container, RessourceManager.getImage("MenuJouerOver"), x/10 + 190, y-y/10, 150, 50);
		buttonJouer.setMouseOverImage(RessourceManager.getImage("MenuJouer"));
		
		quitter = RessourceManager.getImage("MenuQuitterOver");
		buttonQuitter = new MouseOverArea(container, quitter, x/10, y-y/10, 150, 50);
		//buttonQuitter.setMouseDownImage(RessourceManager.getImage("MenuQuitterDown"));
		buttonQuitter.setMouseOverImage(RessourceManager.getImage("MenuQuitter"));
		
		RessourceManager.addImage("activer", "activer.png");
		RessourceManager.addImage("activerOver", "activerOver.png");
		activer = RessourceManager.getImage("activerOver");
		buttonPommeModActi = new MouseOverArea(container, activer, x/7 + -115, y/5 + 270, 150, 50);
		buttonPommeModActi.setMouseOverImage(RessourceManager.getImage("activer"));
		
		RessourceManager.addImage("desactiver", "desactiver.png");
		RessourceManager.addImage("desactiverOver", "desactiverOver.png");
		desactiver = RessourceManager.getImage("desactiverOver");
		buttonPommeModDesacti = new MouseOverArea(container, desactiver, x/7 + -115, y/5 + 270, 150, 50);
		buttonPommeModDesacti.setMouseOverImage(RessourceManager.getImage("desactiver"));
		
		buttonPommeFurieActi = new MouseOverArea(container, activer, x/7 + -115, y/5 + 350, 150, 50);
		buttonPommeFurieActi.setMouseOverImage(RessourceManager.getImage("activer"));
		buttonPommeFurieDesacti = new MouseOverArea(container, desactiver, x/7 + -115, y/5 + 350, 150, 50);
		buttonPommeFurieDesacti.setMouseOverImage(RessourceManager.getImage("desactiver"));
		
		buttonFondNoirActi = new MouseOverArea(container, activer, x/7 + -115, y/5 + 430, 150, 50);
		buttonFondNoirActi.setMouseOverImage(RessourceManager.getImage("activer"));
		buttonFondNoirDesacti = new MouseOverArea(container, desactiver, x/7 + -115, y/5 + 430, 150, 50);
		buttonFondNoirDesacti.setMouseOverImage(RessourceManager.getImage("desactiver"));

		buttonTraverserActi = new MouseOverArea(container, activer, x/7 + -115, y/5 + 190, 150, 50);
		buttonTraverserDesacti = new MouseOverArea(container, desactiver, x/7 + -115, y/5 + 190, 150, 50);
		buttonTraverserActi.setMouseOverImage(RessourceManager.getImage("activer"));
		buttonTraverserDesacti.setMouseOverImage(RessourceManager.getImage("desactiver"));
		
		Font awtFont2 = new Font("Times New Roman", Font.BOLD, 18);
		TrueTypeFont fontLogin2 = new TrueTypeFont(awtFont2, false);
		
		color = new TextField[6];
		team = new TextField[6];
		joueur = new TextField[6];
		for(int i=0;i<6;i++){
			if(i<3){
				color[i] = new TextField(container, fontLogin2, x/7 + i*(x/3) - 50, 50, 25, 25);	// ou y/5
				team[i] = new TextField(container, fontLogin2, x/7 + i*(x/3) - 50, 50 + 30, 25, 25);	// ou y/5
				joueur[i]  = new TextField(container, fontLogin2, x/7 + i*(x/3) - 50, 50 + 60, 25, 25);	// ou y/5
			}else{
				color[i] = new TextField(container, fontLogin2, x/7 + (i-3)*(x/3) - 50, 50 + 150, 25, 25);
				team[i] = new TextField(container, fontLogin2, x/7 + (i-3)*(x/3) - 50, 50 + 180, 25, 25);
				joueur[i]  = new TextField(container, fontLogin2, x/7 + (i-3)*(x/3) - 50, 50 + 210, 25, 25);
			}
			
			color[i].setMaxLength(1);
			color[i].setText(""+i);
			color[i].setCursorVisible(true);
			color[i].setBackgroundColor(null);
			color[i].setBorderColor(null);
			
			team[i].setMaxLength(2);
			team[i].setText("-1");	// -1 est un joueur seul sans team
			team[i].setCursorVisible(true);
			team[i].setBackgroundColor(null);
			team[i].setBorderColor(null);
			
			joueur[i].setMaxLength(2);
			joueur[i].setText("-1");	// -1 signifie que ce n'est rien, pas un joueur ni l'IA
			joueur[i].setCursorVisible(true);
			joueur[i].setBackgroundColor(null);
			joueur[i].setBorderColor(null);
		}
		joueur[0].setText("0");
		joueur[1].setText("0");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		g.drawImage(backgroundUp, 0, 0);
		
		int x = container.getWidth();
		int y = container.getHeight();
		
		//g.setColor(Color.red);
		for(int i=0;i<6;i++){
			//g.setColor(Colors.getColor(i));
			//*
			try{
				g.setColor(Colors.getColor(Integer.valueOf(color[i].getText())));
			}catch(Exception e){
				g.setColor(Color.red);
			}
			//*/
			if(i<3){
				g.drawString("Joueur "+(i+1)+" :", x/7 + i*(x/3), 70-40);	// ou y/5
				color[i].render(container, g);
				g.drawString("Couleur", x/7 + i*(x/3) - 25, 50);	// ou y/5
				team[i].render(container, g);
				g.drawString("team (-1 = seul)", x/7 + i*(x/3) - 25, 50 + 30);	// ou y/5
				joueur[i].render(container, g);
				g.drawString("type (0 humain, 1 IA)", x/7 + i*(x/3) - 25, 50 + 60);
			}else{
				g.drawString("Joueur "+(i+1)+" :", x/7 + (i-3)*(x/3), 60+120);	// ou y/5
				color[i].render(container, g);
				g.drawString("Couleur", x/7 + (i-3)*(x/3) - 25, 50+150);
				team[i].render(container, g);
				g.drawString("team (-1 = seul)", x/7 + (i-3)*(x/3) - 25, 50 + 180);
				joueur[i].render(container, g);
				g.drawString("type (0 humain, 1 IA)", x/7 + (i-3)*(x/3) - 25, 50 + 210);
			}
		}
		g.setColor(Color.green);
		g.drawString("Les bords ne tuent plus", x/7 + 50, y/5 + 205);
		g.drawString("Le mode pomme fait appara”tre des pommes \ndurant la partie " +
				     "et si vous l'a mangŽ \nvous pouvez traversez les obstacles\n" +
				     "pendant quelques secondes", x/7 + 50, y/5 + 255);
		g.drawString("Le mode pomme en furie !\nDes pommes apparaissent de partout !", x/7 + 50, y/5 + 358);	
		g.drawString("Le fond noir du plateau de jeu", x/7 + 50, y/5 + 445);
		
		buttonJouer.render(container, g);
		buttonQuitter.render(container, g);
		if(modPommeON)
			buttonPommeModDesacti.render(container, g);
		else
			buttonPommeModActi.render(container, g);
		if(modPommeFurieON)
			buttonPommeFurieDesacti.render(container, g);
		else
			buttonPommeFurieActi.render(container, g);
		if(fondNoirON)
			buttonFondNoirDesacti.render(container, g);
		else
			buttonFondNoirActi.render(container, g);
		if(modTraverserON)
			buttonTraverserDesacti.render(container, g);
		else
			buttonTraverserActi.render(container, g);
		
		
		super.render(container, game, g);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		switch(key){
		case Input.KEY_ESCAPE:
			goToMenu();
			break;
		}
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		if(buttonQuitter.isMouseOver())
			goToMenu();
		if(modPommeON){
			if(buttonPommeModDesacti.isMouseOver()){
				modPommeON=false;
				modPommeFurieON=false;
			}else//(buttonPommeModActi.isMouseOver())
				modPommeON=true;
		}else{
			if(buttonPommeModActi.isMouseOver())
				modPommeON=true;
			else{
				modPommeON=false;
				modPommeFurieON=false;
			}
		}
		if(modPommeFurieON){
			if(buttonPommeFurieDesacti.isMouseOver())
				modPommeFurieON=false;
			else{//(buttonPommeFurieActi.isMouseOver())
				modPommeON=true;
				modPommeFurieON=true;
			}
		}else{
			if(buttonPommeFurieActi.isMouseOver()){
				modPommeON=true;
				modPommeFurieON=true;
			}else
				modPommeFurieON=false;
		}
		if(fondNoirON){
			if(buttonFondNoirDesacti.isMouseOver())
				fondNoirON=false;
			else//(buttonFondNoirActi.isMouseOver())
				fondNoirON=true;
		}else{
			if(buttonFondNoirActi.isMouseOver())
				fondNoirON=true;
			else
				fondNoirON=false;
		}
		if(modTraverserON){
			if(buttonTraverserDesacti.isMouseOver())
				modTraverserON=false;
			else//(buttonFondNoirActi.isMouseOver())
				modTraverserON=true;
		}else{
			if(buttonTraverserActi.isMouseOver())
				modTraverserON=true;
			else
				modTraverserON=false;
		}
		if(buttonJouer.isMouseOver()){
			mettreLesValeursDansPartie();
			goToPartie();
		}
	}
	
	private void goToMenu() {
		container.setMouseGrabbed(false);
		game.enterState(Game.MAIN_MENU_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	private void mettreLesValeursDansPartie() {
		partie = Partie.instance();
		
		//	Player(int spawn,int color, int team, boolean isPlayer, int id, int tabLenght)
		int tempColor, tempTeam, tempIsPlayer;
		boolean tempPlayerBoolean, noPlayer;
		for(int i=0;i<6;i++){
			noPlayer=false;
			tempPlayerBoolean = false;
			try{
				tempColor = Integer.valueOf(""+color[i].getText());
			}catch(Exception e){ tempColor=0; }
			try{
				tempTeam = Integer.valueOf(""+team[i].getText());
			}catch(Exception e){ tempTeam=-1; }
			try{
				tempIsPlayer = Integer.valueOf(""+joueur[i].getText());
				if(tempIsPlayer==0)
					tempPlayerBoolean=true;
				else if(tempIsPlayer==1)
					tempPlayerBoolean=false;
				else
					noPlayer=true;
			}catch(Exception e){ noPlayer=true; }

			if(!noPlayer)
				partie.getPlayer()[i] = new Player(i,tempColor, tempTeam, tempPlayerBoolean, i, TAILLE_TABLEAU);
			else
				partie.getPlayer()[i] = null;
		}
		
		partie.setModPommeON(modPommeON);
		partie.setModPommeFurieON(modPommeFurieON);
		partie.setFondNoir(fondNoirON);
		partie.intModPomme();
	}
	private void goToPartie() {
		container.setMouseGrabbed(false);
		game.enterState(Game.PARTIE_VIEW_ID, new FadeOutTransition(), new FadeInTransition());
	}
	public static boolean isModTraverserON() {
		return modTraverserON;
	}
	
	@Override
	public int getID() {
		return Game.CHOIX_JEU_VIEW_ID;
	}

}
