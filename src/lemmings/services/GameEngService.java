package lemmings.services;

import java.util.Set;

public interface GameEngService {
	// Observators ---------------------------------------------------------
	LevelService getLevel();
	int getSizeColony();
	int getSpawnSpeed();
	int getTour();
	boolean isObstacle(int x, int y);
	// \pre: Level::caseExiste(x,y)
	boolean isGameOver();
	int getScore();
	// \pre: isGameOver()
	Set<LemmingService> getLemmings();
	boolean lemmingExiste(int id);
	LemmingService getLemming(int id);
	//\pre: lemmingExiste(id)
	
	// Invariants ----------------------------------------------------------
	/**
	 * \inv: card(getLemmings()) <= getSizeColony()
	 * \inv: isGameOver() min= (card(getLemmings()) = 0) && (getTour() * getSpawnSpeed() >= getSizeColony())
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * \pre: size > 0 && speed > 0 && L != NIL
	 * \post: getLevel() = L
	 * \post: getSizeColony() = size
	 * \post: getSpawnspeed() = speed
	 * \post: isGameOver() = false
	 * \post: getTour() = 0
	 * \post: getLemmings() = {}
	 * \post: isObstacle(x,y) = getLevel()::getNature(x,y) ∈ {DIRTY, METAL}
	 */
	void init(LevelService level,int size, int speed);
	
	// Operators -----------------------------------------------------------
	/**
	 * \pre: isGameOver() = false
	 * \post:
	 */
	void activeTour();
	void supprimeLemming(int id);
	void creeLemming(int id, int x, int y);
	
}
