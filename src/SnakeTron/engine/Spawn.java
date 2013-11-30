package SnakeTron.engine;

public class Spawn {
	
	//public static final int[] DIRECTION = new int[] {0, 1, 2, 3};	// droite, haut, gauche, bas
	public static final int DROITE = 0;
	public static final int HAUT = 1;
	public static final int GAUCHE = 2;
	public static final int BAS = 3;
	
	public static int getSpawnId(final int spawn,final boolean xOuY, int tabLenght) {
		// NECESSITE D'AVOIR DES TABLEAUX CARRE !!!!! dž au tablenght
		switch(spawn){
		case DROITE:		// gauche
			if(xOuY)
				return tabLenght/4;
			else
				return tabLenght/2;
		case HAUT:		// droite
			if(xOuY)
				return 3*tabLenght/4;
			else
				return tabLenght/2;
		case GAUCHE:		// haut
			if(xOuY)
				return tabLenght/2;
			else
				return tabLenght/4;
		case BAS:		// bas
			if(xOuY)
				return tabLenght/2;
			else
				return 3*tabLenght/4;
		case 4:		// haut gauche
			if(xOuY)
				return tabLenght/4;
			else
				return tabLenght/4;
		case 5:		// bas droite
			if(xOuY)
				return 3*tabLenght/4;
			else
				return 3*tabLenght/4;
		default:
			return 0;
		}
	}
	public static int getDirectionOnSpawnId(final int spawn) {
		//{0, 1, 2, 3};	// droite, haut, gauche, bas
		switch(spawn){
		case 0:
				return DROITE;
		case 1:
				return GAUCHE;
		case 2:
				return BAS;
		case 3:
				return HAUT;
		case 4:
				return BAS;
		case 5:
				return HAUT;
		default:
			return DROITE;
		}
	}
}
