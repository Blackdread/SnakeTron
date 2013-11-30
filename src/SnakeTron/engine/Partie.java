package SnakeTron.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import SnakeTron.utils.Colors;
import SnakeTron.utils.Configuration;
import SnakeTron.utils.Keyboard;
import SnakeTron.utils.RessourceManager;
import SnakeTron.utils.Timer;
import SnakeTron.views.ChoixJeuView;
/**
 * 
 * @author Yoann CAPLAIN
 * @since 13 10 2012
 */
public class Partie {
	
	private static Partie _instance;
	
	private int tab[][];
	private Player tabPlayer[];
	
	private Timer timer, timerPommeFurie;
	private int WAIT_TIME_BEFORE_NEXTR = 30;	// pourra etre change plus tard
	private final int WAIT_TIME_BEFORE_NEXT_POMME = 1900;
	
	private boolean fondNoir;
	
	private Image pomme;//, glow;
	private boolean modPommeON, modPommeFurieON;
	public final static int POMME_ID = 10;

	private boolean partieGagne=false;
	
	private Partie(){
		initPartie();
	}

	public static Partie instance() {
	      if(null == _instance) {
	         _instance = new Partie();
	      }
	      return _instance;
	 }
	
	private void initPartie(){
		timer = new Timer(WAIT_TIME_BEFORE_NEXTR);
		timerPommeFurie = new Timer(WAIT_TIME_BEFORE_NEXT_POMME);
		tabPlayer = new Player[6];
		tab = new int[ChoixJeuView.TAILLE_TABLEAU][ChoixJeuView.TAILLE_TABLEAU];
		modPommeON=false;
		modPommeFurieON=false;
		initTableau();
		
		RessourceManager.addImage("pomme", "pomme.png");
		pomme = RessourceManager.getImage("pomme");
		pomme = pomme.getScaledCopy(6, 6);
		
		//RessourceManager.addImage("glow", "glow.png");
		//glow = RessourceManager.getImage("glow").getScaledCopy(6, 6);
		
		partieGagne=false;
	}
	
	public void update(GameContainer container, StateBasedGame sbGame, int delta) throws SlickException {
		if(!partieGagne){
			timer.update(delta);
			if(modPommeFurieON)
				timerPommeFurie.update(delta);
			if (timer.isTimeComplete()) {
				for(int t=0;t<3;t++)
					for(Player v : tabPlayer)
						if(v!=null)
							v.avancerPlayer(tab);
					
					//timer.resetTime();
					timer.resetTimeDiffDeltaAndEventTime();
			}
			if(timerPommeFurie.isTimeComplete()){
				Player.mettrePommeAuHasard(tab);
				timerPommeFurie.resetTime();
			}
			if(PartieGoal.isPartieGoalComplete(tabPlayer)){
				partieGagne=true;
			}
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		int x = container.getWidth();
		int y = container.getHeight();	
		
		int decalage = 30;
		
		g.setColor(Color.orange);
		g.drawRect(x/2 - tab.length - 2, y/2 - tab.length - 2 - decalage, tab.length *2 + 2, tab.length *2+ 2);
		
		if(fondNoir){
			g.setColor(Color.black);
			g.fillRect(x/2 - tab.length - 1, y/2 - tab.length -1 - decalage, tab.length *2 + 1, tab.length *2+ 1);
		}
		
		/*
		 * -1 = vide		0,1,2,3,4,5,6,... = la couleur		10 = POMME
		 */
		int temp22, temp33;
		for(int j=0;j<tab.length;j++)	// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!!
			for(int i=0;i<tab.length;i++){
				temp22 = x/2 - tab.length + i*2;
				temp33 = y/2 - tab.length + j*2 - decalage;
				if(tab[j][i]!=-1 && tab[j][i]!=POMME_ID){
					if(isColorSnakeHasApple(tab[j][i])){
						g.setColor(Color.cyan);
						//g.drawImage(glow,x/2 - tab.length + i*2 - 3, y/2 - tab.length + j*2 -decalage -3);
						g.fillRect(temp22 -1, temp33 -1, 1, 1);
						g.fillRect(temp22 +1, temp33 +1, 1, 1);
					}
					
					g.setColor(Colors.getColor(tab[j][i]));
					g.fillRect(x/2 - tab.length + i*2, temp33, 2, 2);
				}else if(tab[j][i]==POMME_ID && modPommeON){
					//System.out.println("pomme dessiner");
					g.drawImage(pomme,temp22 - pomme.getWidth()/2, temp33 - pomme.getHeight()/2);
				}else{
					if(Configuration.isDebug()){
						g.setColor(Color.red);
						//g.fillRect(x/2 - tab.length + i*2, y/2 - tab.length + j*2-decalage, 1, 1);
					}
				}
			}
		if(partieGagne){
			String temp="";
			g.setColor(Color.orange);
			g.drawString("Appuyer sur Espace pour relancer la partie", container.getWidth()/2 - 165 , container.getHeight()/2 - 60);
			for(Player v : tabPlayer)
				if(v!=null)
					if(!v.isDead())
						temp+=""+v.getId()+", ";
			if(temp.length()>2){
				temp = temp.substring(0, temp.length()-2);
				g.drawString("Round gagnŽ par joueur : "+(temp+1), container.getWidth()/2 - 100 - temp.length(), container.getHeight()/2 - 20);
			}else
				g.drawString("Match nul !!", container.getWidth()/2 - 42, container.getHeight()/2 - 20);
		}
		
	}
	
	public void keyPressed(final int key, final char c) {
		for(Player v : tabPlayer)
			if(v!=null)
				if(v.isPlayer())
					Keyboard.changeDirection(key,c,v);
	}
	
	private boolean isColorSnakeHasApple(int color){
		for(Player v : tabPlayer)
			if(v!=null)
				if(v.getColor() == color && v.getNbPomme()>0)
					return true;
		
		return false;
	}
	
	private void initTableau(){
		for(int j=0;j<tab.length;j++)	// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!!
			for(int i=0;i<tab.length;i++)
				tab[j][i]=-1;
	}
	
	public void initInstance(){
		initTableau();
		for(int i=0;i<6;i++)
			tabPlayer[i]=null;
		timer.resetTime();
		partieGagne=false;
	}
	public void restartPartie(){
		int temp=0;
		for(Player v : tabPlayer)
			if(v!=null){
				temp=v.getId();
				v.setWin(0);
				v.setPosX(Spawn.getSpawnId(temp, true, ChoixJeuView.TAILLE_TABLEAU));
				v.setPosY(Spawn.getSpawnId(temp, false, ChoixJeuView.TAILLE_TABLEAU));
				v.setDirection(Spawn.getDirectionOnSpawnId(temp));
				v.setDead(false);
				v.setNbPomme(0);
			}
		initTableau();
		timer.resetTime();
		timerPommeFurie.resetTime();
		partieGagne=false;
		if(modPommeON){
			tab[ChoixJeuView.TAILLE_TABLEAU/2][ChoixJeuView.TAILLE_TABLEAU/2] = POMME_ID;
		}
	}
	public void restartPartieApresWin(){
		int temp=0;
		for(Player v : tabPlayer)
			if(v!=null){
				temp=v.getId();
				//v.setWin(0);
				v.setPosX(Spawn.getSpawnId(temp, true, ChoixJeuView.TAILLE_TABLEAU));
				v.setPosY(Spawn.getSpawnId(temp, false, ChoixJeuView.TAILLE_TABLEAU));
				v.setDirection(Spawn.getDirectionOnSpawnId(temp));
				v.setDead(false);
				v.setNbPomme(0);
			}
		initTableau();
		timer.resetTime();
		timerPommeFurie.resetTime();
		partieGagne=false;
		if(modPommeON){
			tab[ChoixJeuView.TAILLE_TABLEAU/2][ChoixJeuView.TAILLE_TABLEAU/2] = POMME_ID;
		}
	}
	
	public Player[] getPlayer(){
		return tabPlayer;
	}
	
	public static Partie get_instance() {
		return _instance;
	}
	public static void set_instance(Partie _instance) {
		Partie._instance = _instance;
	}
	
	
	public boolean isModPommeON() {
		return modPommeON;
	}

	public void setModPommeON(boolean modPommeON) {
		this.modPommeON = modPommeON;
	}
	public void intModPomme(){
		if(modPommeON)
			tab[ChoixJeuView.TAILLE_TABLEAU/2][ChoixJeuView.TAILLE_TABLEAU/2] = POMME_ID;
	}
	public boolean isModPommeFurieON() {
		return modPommeFurieON;
	}
	public void setModPommeFurieON(boolean modPommeFurieON) {
		this.modPommeFurieON = modPommeFurieON;
	}

	public boolean isPartieGagne() {
		return partieGagne;
	}
	public void setPartieGagne(boolean partieGagne) {
		this.partieGagne = partieGagne;
	}

	public boolean isFondNoir() {
		return fondNoir;
	}

	public void setFondNoir(boolean fondNoir) {
		this.fondNoir = fondNoir;
	}
	public int getWAIT_TIME_BEFORE_NEXTR() {
		return WAIT_TIME_BEFORE_NEXTR;
	}

	public void setWAIT_TIME_BEFORE_NEXTR(int wAIT_TIME_BEFORE_NEXTR) {
		WAIT_TIME_BEFORE_NEXTR = wAIT_TIME_BEFORE_NEXTR;
	}


	public void incrementWAIT_TIME_BEFORE_NEXTR(){
		WAIT_TIME_BEFORE_NEXTR++;
	}
	public void decrementWAIT_TIME_BEFORE_NEXTR(){
		WAIT_TIME_BEFORE_NEXTR--;
	}
	
	public void restartTimer(){
		timer = new Timer(WAIT_TIME_BEFORE_NEXTR);
	}
	
	@SuppressWarnings("unused")
	private class Case{
		private int value;
		private boolean directionVertical;
		
		public Case(int value, boolean a){
			this.value=value;
			this.directionVertical=a;
		}
		
		public int getValue() {
			return value;
		}
		public boolean isDirectionVertical() {
			return directionVertical;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public void setDirectionVertical(boolean directionVertical) {
			this.directionVertical = directionVertical;
		}
		
		
		
	}
}


