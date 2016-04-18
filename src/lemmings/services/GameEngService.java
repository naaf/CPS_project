package lemmings.services;

import java.util.Set;

public interface GameEngService {
	// Observators ---------------------------------------------------------
	LevelService getLevel();
	int getSizeColony();
	int getSpawnSpeed();
	int getTour();
	boolean isObstacle(int x, int y);
	// pre: Level::caseExiste(x,y)
	boolean isGameOver();
	int getScore();
	// pre: isGameOver()
	int getNbSauves();
	Set<Integer> getLemmings();
	boolean lemmingExiste(int id);
	LemmingService getLemming(int id);
	//pre: lemmingExiste(id)
	
	// Invariants ----------------------------------------------------------
	/**
	 * inv: card(getLemmings()) <= getSizeColony()
	 * inv: 0 <= nbSauves() < sizeColony()
	 * getScore() min= getNbSauves()/getSizeColony() * 100
	 * isObstacle(x,y) min= ¬(getLevel().nature(x,y) == EMPTY)
	 * inv: isGameOver() min= (getTour() * getSpawnSpeed() == getSizeColony()) &&
	 * 		(card(getLemmings()) == 0)
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: size > 0 && speed > 0 
	 * post: getLevel() = L
	 * post: getSizeColony() = size
	 * post: getSpawnspeed() = speed
	 * post: isGameOver() = false
	 * post: getTour() = 0
	 * post: getNbsauves() = 0
	 * post: getLemmings() = {}
	 */
	void init(LevelService level,int size, int speed);
	
	// Operators -----------------------------------------------------------
	/**
	 * pre: lemmingExiste(id) 
	 * post: supprimeLemming(id).getLemmings() = getLemmings()@pre / id
	 * ∀ n ∈  getLemmings(G) / {num}, supprimeLemming(num).getLemming(n) = getLemming(n)
	 */
	void supprimeLemming(int id);
	
	/**
	 * pre: ¬lemmingExiste(id)
	 * pre: getLemmings().size() < getSizeColony()
	 * post: creeLemming(id,x,y).getLemmings() = getLemmings()@pre ⋃ {id}
	 * post: if id = n then creeLemming(id,x,y).getLemming(n) = Lemming::init(id,x,y)
	 * 		 else creeLemming(id,x,y).getLemming(n) = getLemming(n)
	 */
	void creeLemming(int id, int x, int y);
	
	/**
	 * pre: lemmingExiste(id)
	 * post: saveLemming(id).getNbSauves() = getNbSauves()@pre +1
	 * post: saveLemming(id).getLemmings() = getLemmings()@pre / id
	 * post: ∀ n ∈  getLemmings() / {num}, saveLemming(num).getLemming(n) = getLemming(n)
	 */
	void saveLemming(int id);
	
	/**
	 * pre: isGameOver() = false
	 * post: activeTour().getTour() = getTour()@pre + 1
	 * post: activeTour().getLemmings() = getLemmings()
	 * post: ∀ n ∈ getLemmings(), activeTour().getLemming(n) = getLemming(n)
	 */
	void activeTour();
	
}
