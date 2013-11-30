package SnakeTron.utils;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class RessourceManager {
	
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private static HashMap<String, Music> musics = new HashMap<String, Music>();
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	//private static HashMap<String, SpriteSheet> sprites = new HashMap<String, SpriteSheet>();
	//private static HashMap<String, String> systemsAndEmitters = new HashMap<String, String>();
	
	
	/**
	 * Get an image from the resources Manager.
	 * 
	 * @param name
	 *            the name of the image.
	 * @return the requested image or null if the resource was not found.
	 */
	public static Image getImage(String name) {
		return images.get(name);
	}

	/**
	 * Get a music from the resources Manager.
	 * 
	 * @param name
	 *            the name of the music.
	 * @return the requested music or null if the resource was not found.
	 */
	public static Music getMusic(String name) {
		return musics.get(name);
	}

	/**
	 * Get a sound from the resources Manager.
	 * 
	 * @param name
	 *            the name of the sound.
	 * @return the requested sound or null if the resource was not found.
	 */
	public static Sound getSound(String name) {
		return sounds.get(name);
	}

	/**
	 * Get a sprite from the resources.
	 * 
	 * @param name
	 *            the name of the sprite.
	 * @return the requested sprite or null if the resource was not found.
	 */
	/*public static SpriteSheet getSpriteSheet(String name) {
		return sprites.get(name);
	}*/
	
	/**
	 * add an image to the resources manager.
	 * 
	 * @param name the name of the sprite.
	 * @param path contains the name file and extension
	 * @return the requested sprite or null if the resource was not found.
	 */
	public static void addImage(String name, String path){
		try {
			images.put(name, new Image("ressources/images/"+path));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * add a music to the resources manager.
	 * 
	 * @param name the name of the sprite.
	 * @param path contains the name file and extension
	 * @return the requested sprite or null if the resource was not found.
	 */
	public static void addMusic(String name, String path){
		try {
			musics.put(name, new Music("ressources/musics/"+path));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * add a sound to the resources manager.
	 * 
	 * @param name the name of the sprite.
	 * @param path contains the name file and extension
	 * @return the requested sprite or null if the resource was not found.
	 */
	public static void addSound(String name, String path){
		try {
			sounds.put(name, new Sound("ressources/sounds/"+path));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get an image from the resources.
	 * 
	 * @param name the name of the image. And extension ".jpg etc"
	 * @param extension extension of file
	 * @return the requested image or null if the resource was not found.
	 */
	public static Image getImage(String name, String extension){
		try {
			return new Image("ressources/images/"+name+"."+extension);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//return images.get(name);
	}
	
	/**
	 * Get a music from the resources.
	 * 
	 * @param name the name of the music. And extension ".wav etc"
	 * @return the requested music or null if the resource was not found.
	 */
	public static Music getMusic(String name, String extension){
		try {
			return new Music("ressources/musics/"+name);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//return musics.get(name);
	}
	
	/**
	 * Get a sound from the resources.
	 * 
	 * @param name the name of the sound. And extension ".wav etc"
	 * @return the requested sound or null if the resource was not found.
	 */
	public static Sound getSound(String name, String extension){
		try {
			return new Sound("ressources/sounds/"+name);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
