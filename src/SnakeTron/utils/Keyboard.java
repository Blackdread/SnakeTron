package SnakeTron.utils;

import org.newdawn.slick.Input;

import SnakeTron.engine.Player;
import SnakeTron.engine.Spawn;

/**
 * Pour le moment les touches sont fixes
 * @author Yoann CAPLAIN
 * @since 13 10 2012
 */
public class Keyboard {
	
	public static void changeDirection(final int key, final char c, Player v){
		switch(v.getId()){
		case 0:
			if((key == Input.KEY_A || key == Input.KEY_Q) && v.getDirection() != Spawn.DROITE)
				v.setDirection(Spawn.GAUCHE);
			else if((key == Input.KEY_W || key == Input.KEY_Z) && v.getDirection() != Spawn.BAS)
				v.setDirection(Spawn.HAUT);
			else if(key == Input.KEY_S && v.getDirection() != Spawn.HAUT)
				v.setDirection(Spawn.BAS);
			else if(key == Input.KEY_D && v.getDirection() != Spawn.GAUCHE)
				v.setDirection(Spawn.DROITE);
			break;
		case 1:
			if(key == Input.KEY_LEFT && v.getDirection() != Spawn.DROITE)
				v.setDirection(Spawn.GAUCHE);
			else if(key == Input.KEY_UP && v.getDirection() != Spawn.BAS)
				v.setDirection(Spawn.HAUT);
			else if(key == Input.KEY_DOWN && v.getDirection() != Spawn.HAUT)
				v.setDirection(Spawn.BAS);
			else if(key == Input.KEY_RIGHT && v.getDirection() != Spawn.GAUCHE)
				v.setDirection(Spawn.DROITE);
			break;
		case 2:
			if(key == Input.KEY_G && v.getDirection() != Spawn.DROITE)
				v.setDirection(Spawn.GAUCHE);
			else if(key == Input.KEY_Y && v.getDirection() != Spawn.BAS)
				v.setDirection(Spawn.HAUT);
			else if(key == Input.KEY_H && v.getDirection() != Spawn.HAUT)
				v.setDirection(Spawn.BAS);
			else if(key == Input.KEY_J && v.getDirection() != Spawn.GAUCHE)
				v.setDirection(Spawn.DROITE);
			break;
		case 3:
			if(key == Input.KEY_K && v.getDirection() != Spawn.DROITE)
				v.setDirection(Spawn.GAUCHE);
			else if(key == Input.KEY_O && v.getDirection() != Spawn.BAS)
				v.setDirection(Spawn.HAUT);
			else if(key == Input.KEY_L && v.getDirection() != Spawn.HAUT)
				v.setDirection(Spawn.BAS);
			else if((key == Input.KEY_SEMICOLON || key == Input.KEY_M) && v.getDirection() != Spawn.GAUCHE)
				v.setDirection(Spawn.DROITE);
			break;
		case 4:
			if(key == Input.KEY_NUMPAD4 && v.getDirection() != Spawn.DROITE)
				v.setDirection(Spawn.GAUCHE);
			else if(key == Input.KEY_NUMPAD8 && v.getDirection() != Spawn.BAS)
				v.setDirection(Spawn.HAUT);
			else if(key == Input.KEY_NUMPAD5 && v.getDirection() != Spawn.HAUT)
				v.setDirection(Spawn.BAS);
			else if((key == Input.KEY_NUMPAD6) && v.getDirection() != Spawn.GAUCHE)
				v.setDirection(Spawn.DROITE);
			break;
		case 5:
			if(key == Input.MOUSE_LEFT_BUTTON){
				if(v.getDirection() == Spawn.DROITE){
					v.setDirection(Spawn.HAUT);
				}else if(v.getDirection() == Spawn.GAUCHE){
					v.setDirection(Spawn.BAS);
				}else if(v.getDirection() == Spawn.HAUT){
					v.setDirection(Spawn.GAUCHE);
				}else	// BAS
					v.setDirection(Spawn.GAUCHE);	// A VOIR
			}else if(key == Input.MOUSE_RIGHT_BUTTON){
				if(v.getDirection() == Spawn.DROITE){
					v.setDirection(Spawn.BAS);
				}else if(v.getDirection() == Spawn.GAUCHE){
					v.setDirection(Spawn.HAUT);
				}else if(v.getDirection() == Spawn.HAUT){
					v.setDirection(Spawn.DROITE);
				}else	// BAS
					v.setDirection(Spawn.DROITE);	// A VOIR
			}
			break;
		}
	}
}
