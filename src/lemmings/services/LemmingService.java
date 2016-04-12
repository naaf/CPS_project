package lemmings.services;

public interface LemmingService {
	// Observators ---------------------------------------------------------
	GameEngService gameEng();
	int getId();
	int getX();
	int getY();
	Direction getDirection();
	ClasseType getClasseType();
	
	// Invariants ----------------------------------------------------------
	/**
	 * \env: gameEng()::level()::getNature(x, y -1) = EMPTY
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * \pre: gameEng()::level()::caseExiste(x,y)
	 * \pre: gameEng()::level()::getNature(x, y) = EMPTY
	 * \pre: gameEng()::level()::getNature(x, y -1) = EMPTY
	 * \post: getX() = x
	 * \post: getY() = y
	 * \post: getId() = id
	 * \post: getDirection = DROITE
	 * \post: getClasseType = MARCHEUR
	 * \post: getGameEng() = gameEng
	 */
	void init(GameEngService gameEng, int id, int x, int y);
	
	// Operators -----------------------------------------------------------
	
}
