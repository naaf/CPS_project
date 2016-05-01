package lemmings.services;

import java.util.Collection;

public interface GameEngService {
	// Observators ---------------------------------------------------------
	LevelService getLevel();
	int getSizeColony();
	int getSpawnSpeed();
	int getTour();
	boolean isObstacle(int x, int y);
	// pre: Level::caseExiste(x,y)
	boolean isGameOver();
	// pre: getLevel().isEditing() = false
	int getScore();
	// pre: isGameOver()
	int getNbSauves();
	int getNbCrees();
	public Collection<LemmingService> lemmings();
	boolean lemmingExiste(int id);
	LemmingService getLemming(int id);
	//pre: lemmingExiste(id)
	
	// Invariants ----------------------------------------------------------
	/**
	 * inv: card(getLemmings()) <= getSizeColony()
	 * inv: 0 <= nbSauves() < sizeColony()
	 * inv: getNbcrees() min= getTour() / getSpawnSpeed() 
	 * inv: getScore() min= getNbSauves()/getNbCrees() * 100
	 * inv: isObstacle(x,y) min= ¬(getLevel().nature(x,y) == EMPTY)
	 * inv: isGameOver() min= (getNbCrees() == getSizeColony()) &&
	 * 		(card(getLemmings()) == 0)
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: level != null
	 * pre: size > 0 && speed > 0 
	 * post: getLevel() = L
	 * post: getSizeColony() = size
	 * post: getSpawnspeed() = speed
	 * post: isGameOver() = false
	 * post: getTour() = 0
	 * post: getNbsauves() = 0
	 * post: getNbCrees() = 0
	 * post: lemmings() = {}
	 */
	void init(LevelService level,int size, int speed);
	
	// Operators -----------------------------------------------------------
	/**
	 * pre: lemmingExiste(id) 
	 * pre: gameOver() = false
	 * post: supprimeLemming(id).lemmings() = lemmings()@pre / getLemming(id)
	 * ∀ n ∈  getLemmings(G).id() / {num}, supprimeLemming(num).getLemming(n) = getLemming(n)
	 */
	void supprimeLemming(int id);
	
	/**
	 * pre: lemmingExiste(id) = false
	 * pre: getLemmings().size() < getSizeColony()
	 * pre: gameOver() = false
	 * post: getNbCrees() = getNbCrees()@pre + 1
	 * post: creeLemming(id,x,y).lemmings() = getLemmings()@pre ⋃ {Lemming::init(id,x,y)}
	 * post: if id = n then creeLemming(id,x,y).getLemming(n) = Lemming::init(id,x,y)
	 * 		 else creeLemming(id,x,y).getLemming(n) = getLemming(n)
	 */
	void creeLemming(int id, int x, int y);
	
	/**
	 * pre: lemmingExiste(id)
	 * pre: gameOver() = false
	 * post: saveLemming(id).getNbSauves() = getNbSauves()@pre +1
	 * post: saveLemming(id).lemmings() = getLemmings()@pre / getLemming(id)
	 * post: ∀ n ∈  lemmings().id() / {num}, saveLemming(num).getLemming(n) = getLemming(n)
	 */
	void saveLemming(int id);
	
	/**
	 * pre: isGameOver() = false
	 * post: runTour().getTour() = getTour()@pre + 1
	 */
	void runTour();
	
	/**
	 * pre: isGameOver() = false
	 * post: stopCreation().getTour() = getTour()@pre
	 * post: stopCreation().getNbSauves() = getNbSauves()@pre
	 * post: stopCreation().lemmings() = lemmings()@pre
	 */
	void stopCreation();
}
