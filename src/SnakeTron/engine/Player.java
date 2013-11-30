package SnakeTron.engine;

import SnakeTron.utils.Configuration;
import SnakeTron.views.ChoixJeuView;


/**
 * 
 * @author Yoann CAPLAIN
 * @since 12 10 2012
 */
public class Player {
	
	private int posX, posY;
	private int direction;
	
	private int color;
	private int win;
	private int id;	// numero du joueur (de 0 ˆ 5)
	private int teamId; //	-1 = pas de team (seul)
	private boolean isPlayer;	// sinon c'est une IA
	private boolean dead;
	private int nbPomme;
	//private AI ia;
	
	public Player(int spawn,int color, int team, boolean isPlayer, int id, int tabLenght){
		// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!! dž au tablenght
		this.posX=Spawn.getSpawnId(spawn, true, tabLenght);
		this.posY=Spawn.getSpawnId(spawn, false, tabLenght);
		this.direction=Spawn.getDirectionOnSpawnId(spawn);
		this.color=color;
		this.teamId=team;
		this.isPlayer=isPlayer;
		this.id=id;
		
		this.dead=false;
		this.win=0;
		this.nbPomme=0;
	}
	
	public void avancerPlayer(int tab[][]){
		// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!! dž au tablenght
		// {0, 1, 2, 3};	// droite, haut, gauche, bas
		if(!isPlayer && !dead){
			AI.eviterCollisionBord(this, tab);
			AI.eviterCollisionPlayer(this, tab);
		}
		
		collision(tab);
		
		tab[posY][posX]=color;	
		
		if(!dead)
			switch(direction){
			case Spawn.DROITE:
				posX++;
				break;
			case Spawn.HAUT:
				posY--;
				break;
			case Spawn.GAUCHE:
				posX--;
				break;
			case Spawn.BAS:
				posY++;
				break;
			}
	}
	/**
	 * L'interet de gerer les collisions ainsi est que les joueurs peuvent se croiser s'ils arrivent en meme temps sur
	 * la case, donc le joueur 0 n'est avantage par rapport au autres, etc...
	 * Cette classe gere les collisions avec les pommes, les joueurs et les bords
	 * @param tab tableau de int
	 */
	private void collision(int tab[][]){
		int tempX=posX, tempY=posY;
		switch(direction){
		case Spawn.DROITE:
			if(tempX+1 >= ChoixJeuView.TAILLE_TABLEAU)
				if(!ChoixJeuView.isModTraverserON())
					dead=true;
				else
					posX=0;
			break;
		case Spawn.HAUT:
			if(tempY-1 < 0)
				if(!ChoixJeuView.isModTraverserON())
					dead=true;
				else
					posY=ChoixJeuView.TAILLE_TABLEAU-1;
			break;
		case Spawn.GAUCHE:
			if(tempX-1 < 0)
				if(!ChoixJeuView.isModTraverserON())
					dead=true;
				else
					posX=ChoixJeuView.TAILLE_TABLEAU-1;
			break;
		case Spawn.BAS:
			if(tempY+1 >= ChoixJeuView.TAILLE_TABLEAU)
				if(!ChoixJeuView.isModTraverserON())
					dead=true;
				else
					posY=0;
			break;
		}
		
		if(!dead)
			if(tab[posY][posX]!=-1 && tab[posY][posX]!=Partie.POMME_ID && nbPomme==0)
				this.dead=true;
			else{
				if(posY+1 < ChoixJeuView.TAILLE_TABLEAU && posY-1 >= 0 && posX+1 < ChoixJeuView.TAILLE_TABLEAU && posX-1 >= 0)
					// Normalement la pomme ne se trouve pas sur les bords
					//if(tab[posY][posX] == Partie.POMME_ID){	// depend de si je veux qu'on soit vraiment pil dessus ou un peu a cote 
					if(tab[posY][posX] == Partie.POMME_ID || 
						tab[posY+1][posX] == Partie.POMME_ID || 
						tab[posY-1][posX] == Partie.POMME_ID || 
						tab[posY][posX-1] == Partie.POMME_ID || 
						tab[posY+1][posX+1] == Partie.POMME_ID || 
						tab[posY+1][posX-1] == Partie.POMME_ID || 
						tab[posY-1][posX-1] == Partie.POMME_ID || 
						tab[posY-1][posX+1] == Partie.POMME_ID || 
						tab[posY][posX+1] == Partie.POMME_ID){
						if(Configuration.isDebug())
							System.out.println("pomme++");
						nbPomme++;
						//*****
						//tab[posY][posX]=-1; // depend de si je veux qu'on soit vraiment pil dessus ou a cote un peu
						//enleverPomme(tab);
						enleverPommeDansZone(tab);
						//*****
						mettrePommeAuHasard(tab);
					}else if(nbPomme>0 && tab[posY][posX]!=-1){
						nbPomme--;
					}
			}
	}
	
	public static void mettrePommeAuHasard(int tab[][]){
		boolean temp=true;
		do{
			// Permet de ne pas mettre de pomme trop pres des bords
			int xx = (int)(ChoixJeuView.TAILLE_TABLEAU/4 + Math.random()*2*ChoixJeuView.TAILLE_TABLEAU/3);
			int yy = (int)(ChoixJeuView.TAILLE_TABLEAU/4 + Math.random()*2*ChoixJeuView.TAILLE_TABLEAU/3);
			if(tab[yy][xx] == -1 && tab[yy][xx] != Partie.POMME_ID){
				tab[yy][xx] = Partie.POMME_ID;
				temp=false;
			}
		}while(temp);
	}
	
	@SuppressWarnings("unused")
	private void enleverPomme(int tab[][]){
		for(int j=0;j<tab.length;j++)	// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!!
			for(int i=0;i<tab.length;i++)
				if(tab[j][i] == Partie.POMME_ID){
					tab[j][i]=-1;
					return;
				}
	}
	private void enleverPommeDansZone(int tab[][]){
		if(tab[posY][posX] == Partie.POMME_ID)
			tab[posY][posX] = -1;
		if(posY+1 < ChoixJeuView.TAILLE_TABLEAU)
			if(tab[posY+1][posX] == Partie.POMME_ID)
				tab[posY+1][posX]=-1;
		if(posY-1 >= 0)
			if(tab[posY-1][posX] == Partie.POMME_ID) 
				tab[posY-1][posX]=-1;
		if(posX-1 >= 0)
			if(tab[posY][posX-1] == Partie.POMME_ID)
				tab[posY][posX-1]=-1;
		if(posY+1 < ChoixJeuView.TAILLE_TABLEAU && posX+1 < ChoixJeuView.TAILLE_TABLEAU)
			if(tab[posY+1][posX+1] == Partie.POMME_ID )
				tab[posY+1][posX+1]=-1;
		if(posY+1 < ChoixJeuView.TAILLE_TABLEAU && posX-1 >= 0)
			if(tab[posY+1][posX-1] == Partie.POMME_ID ) 
				tab[posY+1][posX-1]=-1;
		if(posY-1 >= 0 && posX-1 >= 0)
			if(tab[posY-1][posX-1] == Partie.POMME_ID)
				tab[posY-1][posX-1]=-1;
		if(posY-1 >= 0 && posX+1 < ChoixJeuView.TAILLE_TABLEAU)
			if(tab[posY-1][posX+1] == Partie.POMME_ID)
				tab[posY-1][posX+1]=-1;
		if(posX+1 < ChoixJeuView.TAILLE_TABLEAU)
			if(tab[posY][posX+1] == Partie.POMME_ID)
				tab[posY][posX+1]=-1;
	}
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosX(int spawnX) {
		this.posX = spawnX;
	}
	public void setPosY(int spawnY) {
		this.posY = spawnY;
	}
	public int getColor() {
		return color;
	}
	public int getWin() {
		return win;
	}
	public int getId() {
		return id;
	}
	public int getTeamId() {
		return teamId;
	}
	public boolean isPlayer() {
		return isPlayer;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public void incrementWin(){
		this.win++;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getNbPomme() {
		return nbPomme;
	}

	public void setNbPomme(int nbPomme) {
		this.nbPomme = nbPomme;
	}


}
