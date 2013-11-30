package SnakeTron.engine;

import SnakeTron.utils.Configuration;
import SnakeTron.views.ChoixJeuView;


/**
 * Pas encore fait
 * @author Yoann CAPLAIN
 * @since 23 10 2012
 */
public class AI {
	
	public static void eviterCollisionBord(Player player, final int tab[][]){
		int tempX=player.getPosX(), tempY=player.getPosY();
		boolean cheminNonTrouver = true;
		int i=0;
		do{
			switch(player.getDirection()){
			case Spawn.DROITE:
				if(tempX+1 >= ChoixJeuView.TAILLE_TABLEAU)
					if(!ChoixJeuView.isModTraverserON()){
						player.setDirection(Spawn.HAUT);
					}else{
						player.setPosX(0);
						cheminNonTrouver = false;
					}
				break;
			case Spawn.HAUT:
				if(tempY-1 < 0)
					if(!ChoixJeuView.isModTraverserON()){
						player.setDirection(Spawn.GAUCHE);
					}else{
						player.setPosY(ChoixJeuView.TAILLE_TABLEAU-1);
						cheminNonTrouver = false;
					}
				break;
			case Spawn.GAUCHE:
				if(tempX-1 < 0)
					if(!ChoixJeuView.isModTraverserON())
						player.setDirection(Spawn.BAS);
					else{
						player.setPosX(ChoixJeuView.TAILLE_TABLEAU-1);
						cheminNonTrouver = false;
					}
				break;
			case Spawn.BAS:
				if(tempY+1 >= ChoixJeuView.TAILLE_TABLEAU)
					if(!ChoixJeuView.isModTraverserON())
						player.setDirection(Spawn.DROITE);
					else{
						player.setPosY(0);
						cheminNonTrouver = false;
					}
				break;
			}
			i++;
		}while(i<4 && cheminNonTrouver);
		
	}
	
	public static void eviterCollisionPlayer(Player player, final int tab[][]){
		int posX=player.getPosX(), posY=player.getPosY();
		int nbPomme = player.getNbPomme();
		boolean cheminNonTrouver = true;
		int i=0;
		do{
			switch(player.getDirection()){
			case Spawn.DROITE:
				if(tab[posY][posX+1]!=-1 && tab[posY][posX+1]!=Partie.POMME_ID && nbPomme==0){
						player.setDirection(Spawn.HAUT);
					}else{
						cheminNonTrouver = false;
					}
				break;
			case Spawn.HAUT:
				if(tab[posY-1][posX]!=-1 && tab[posY-1][posX]!=Partie.POMME_ID && nbPomme==0){
						player.setDirection(Spawn.GAUCHE);
					}else{
						cheminNonTrouver = false;
					}
				break;
			case Spawn.GAUCHE:
				if(tab[posY][posX-1]!=-1 && tab[posY][posX-1]!=Partie.POMME_ID && nbPomme==0)
						player.setDirection(Spawn.BAS);
					else{
						cheminNonTrouver = false;
					}
				break;
			case Spawn.BAS:
				if(tab[posY+1][posX]!=-1 && tab[posY+1][posX]!=Partie.POMME_ID && nbPomme==0)
						player.setDirection(Spawn.DROITE);
					else{
						cheminNonTrouver = false;
					}
				break;
			}
			i++;
		}while(i<4 && cheminNonTrouver);
	}

}
