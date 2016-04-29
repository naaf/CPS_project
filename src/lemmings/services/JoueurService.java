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
	 * pre: jetons > 0
	 * pre: gameEng != NULL
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
	  * pre: getJetons(activity) > 0
	 * pre: getGameEng().lemmingExiste(lm.getId())
	 * post: ∀ t ∈ classeTypes(),
	 * 		if( t == activity.getClasseType() ) getJetons(t) = getJetons(t)@pre -1
	 * 		else getJetons(t) = getJetons(t)@pre
	 * post: l.getClasseLemming() = activity
	 */
	void assignerClasse(ActivityLemming activity, LemmingService lm);
	
	/**
	 * pre: getJetons(activity) > 0
	 * pre: getGameEng().lemmingExiste(lm.getId())
	 * post: ∀ t ∈ classeTypes(),
	 * 		if( t == activity.getClasseType() ) getJetons(t) = getJetons(t)@pre -1
	 * 		else getJetons(t) = getJetons(t)@pre
	 * post: l.getCumul() = activity
	 */
	void assignerCumul(ActivityLemming activity, LemmingService lm);

	/**
	 * post: ∀ ct ∈ getClasseTypes(); getJetons(ct) = getNbJetons()
	 * post: getNbJetons() = getNbJetons
	 * post: getGameEng() = getGameEng().init(getGameEng().level, 
	 * 		getGameEng().getSizeColony(), getGameEng().getSpawnSpeed())
	 */
	void reset();
	
	/**
	 * 
	 */
	void annihilation();
}
