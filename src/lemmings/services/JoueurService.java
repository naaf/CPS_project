package lemmings.services;

import java.util.HashMap;

public interface JoueurService {
	// Observators ---------------------------------------------------------
	GameEngService getGameEng();
	int getNbJetons();
	int getJetons(ClasseType ct);
	HashMap<ClasseType,Integer> getClasseTypes();
	
	// Invariants ----------------------------------------------------------
	
	// Constructors --------------------------------------------------------
	/**
	 * 
	 */
	void init(GameEngService gameEng, int jetons);
	
	// Operators -----------------------------------------------------------
	
	void assignerClasse(ClasseType ct, LemmingService l);
	void reset();
}
