package lemmings.services;

import java.util.HashMap;

public interface JoueurService {
	// Observators ---------------------------------------------------------
	GameEngService getGameEng();
	int getNbJetons();
	int getJetons(ClasseType ct);
	HashMap<ClasseType,Integer> getClasseTypes();
	
	// Invariants ----------------------------------------------------------
	/**
	 * inv: getNbJetons() > 0
	 * inv: getClasseTypes() != {}
	 */
	// Constructors --------------------------------------------------------
	/**
	 * pre: jetons > 0 && gameEng != NULL
	 * post: getGameEng() = gameEng
	 * post: getNbJetons() = jetons
	 * post: ∀ ct ∈ getClasseTypes(), getJetons(ct) = jetons
	 */
	void init(GameEngService gameEng, int jetons);
	
	// Operators -----------------------------------------------------------
	/**
	 * 
	 */
	void startGame();
	
	/**
	 * pre: getJetons(ct) > 0
	 * pre: getGameEng().lemmingExiste(l.getId())
	 * post: ∀ t ∈ classeTypes(),
	 * 		if( c == ct ) getJetons(ct) = getJetons(ct)@pre -1
	 * 		else getJetons(ct) = getJetons(ct)@pre
	 */
	void assignerClasse(ActivityIF ct, LemmingService l);
	
	/**
	 * post: ∀ ct ∈ getClasseTypes(); getJetons(ct) = getNbJetons()
	 * post: getNbJetons() = getNbJetons
	 * post: getGameEng() = getGameEng().init(getGameEng().level, 
	 * 		getGameEng().getSizeColony(), getGameEng().getSpawnSpeed())
	 */
	void reset();
}
