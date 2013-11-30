package SnakeTron.engine;

import java.util.ArrayList;

/**
 * 
 * @author Yoann CAPLAIN
 * @since 13 10 2012
 */
public class PartieGoal {
	
	
	public static final boolean isPartieGoalComplete(Player tab[]){
		int vivant = 0, teamDifferent = 0, joueurEnEquipe=0;
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		for(Player v : tab)
			if(v!=null){
				if(!v.isDead()){
					vivant++;
					if(!array.contains(v.getTeamId())){
						array.add(v.getTeamId());
						teamDifferent++;
					}else if(v.getTeamId() == -1){
						teamDifferent++;
					}else{
						joueurEnEquipe++;
					}
				}
			}
		if(vivant == 0)	// Cas de match nul. C'est arrive dans mes tests si les 2 joueurs atteignent un bord en meme temps !!
			return true;
		
		//System.out.println("equipe "+joueurEnEquipe+" team diff "+teamDifferent+" array size "+array.size()+" vivant "+vivant);
		//if(vivant == teamDifferent && vivant != 1 || joueurEnEquipe-teamDifferent <= 0 && vivant != 1){
		if((vivant == teamDifferent && vivant != 1 || joueurEnEquipe-teamDifferent <= 0 && vivant != 1) && joueurEnEquipe+1 != vivant || teamDifferent > 1){
			return false;
		}
		
		for(Player v : tab)
			if(v!=null)
				if(!v.isDead()){
					v.incrementWin();
				}
		
		return true;
	}
}
