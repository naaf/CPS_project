package lemmings.impl;

import java.util.HashMap;
import java.util.Set;

import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.LevelService;
import lemmings.services.Nature;
import lemmings.services.RequiredServiceLevel;

public class GameEngImpl implements
		/* require */
		RequiredServiceLevel,
		/* provide */
		GameEngService {

	// attribut ------------------------------------------------------------
	private LevelService level;
	private int sizeColony;
	private int spawnSpeed;
	private int tour;
	private int nbSauves;
	private HashMap<Integer, LemmingService> lemmings;

	// Constructors --------------------------------------------------------
	public GameEngImpl() {
	}

	@Override
	public void init(LevelService level, int size, int speed) {
		this.sizeColony = size;
		this.spawnSpeed = speed;
		this.tour = 0;
		this.nbSauves = 0;
		this.lemmings = new HashMap<>();
		bindLevelService(level);
	}

	@Override
	public void bindLevelService(LevelService ls) {
		this.level = ls;
	}
	// Observators ---------------------------------------------------------

	@Override
	public LevelService getLevel() {
		return this.level;
	}

	@Override
	public int getSizeColony() {
		return this.sizeColony;
	}

	@Override
	public int getSpawnSpeed() {
		return this.spawnSpeed;
	}

	@Override
	public int getTour() {
		return this.tour;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return level.getNature(x, y) != Nature.EMPTY;
	}

	@Override
	public boolean isGameOver() {
		return (tour * spawnSpeed == sizeColony) && (lemmings.size() == 0);
	}

	@Override
	public int getScore() {
		return nbSauves / sizeColony * 100;
	}

	@Override
	public int getNbSauves() {
		return nbSauves;
	}

	@Override
	public Set<Integer> getLemmings() {
		return lemmings.keySet();
	}

	@Override
	public boolean lemmingExiste(int id) {
		return lemmings.containsKey(id);
	}

	@Override
	public LemmingService getLemming(int id) {
		return lemmings.get(id);
	}

	// Operators -----------------------------------------------------------

	@Override
	public void supprimeLemming(int id) {
		lemmings.remove(id);
	}

	@Override
	public void creeLemming(int id, int x, int y) {
		LemmingService l = new LemmingImpl();
		l.init(this, id, x, y);
		lemmings.put(id, l);
	}

	@Override
	public void saveLemming(int id) {
		this.nbSauves++;
		lemmings.remove(id);
	}

	@Override
	public void activeTour() {
		for (LemmingService l : lemmings.values()) {
			l.step();
		}
		tour = tour + 1;
	}

}