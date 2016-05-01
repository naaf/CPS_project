package lemmings.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lemmings.contracts.LemmingContract;
import lemmings.services.ClasseType;
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
	private int nbCree;
	private Map<Integer, LemmingService> lemmings;

	// Constructors --------------------------------------------------------
	public GameEngImpl() {
	}

	@Override
	public void init(LevelService level, int size, int speed) {
		this.sizeColony = size;
		this.spawnSpeed = speed;
		this.tour = 0;
		this.nbSauves = 0;
		this.nbCree = 0;
		this.lemmings = new ConcurrentHashMap<>();
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
		return level.getNature(x, y) != Nature.EMPTY || lemmings().stream()
				.filter(l -> l.getX() == x && l.getY() == y && l.getClasseLemming().getTypeClasse() == ClasseType.STOPPEUR)
				.findFirst().isPresent();
	}

	@Override
	public boolean isGameOver() {
		return (nbCree == sizeColony) && (lemmings.size() == 0);
	}

	@Override
	public int getScore() {
		return (int) ((double) nbSauves / sizeColony * 100);
	}

	@Override
	public int getNbSauves() {
		return nbSauves;
	}

	@Override
	public boolean lemmingExiste(int id) {
		return lemmings.containsKey(id);
	}

	
	@Override
	public LemmingService getLemming(int id) {
		return lemmings.get(id);
	}

	@Override
	public Collection<LemmingService> lemmings() {
		return lemmings.values();
	}

	@Override
	public int getNbCrees() {
		return nbCree;
	}
	// Operators -----------------------------------------------------------

	@Override
	public void supprimeLemming(int id) {
		lemmings.remove(id);
		System.out.println("mort " + id);
	}

	@Override
	public void creeLemming(int id, int x, int y) {
		LemmingService l = new LemmingContract(new LemmingImpl());
		l.init(this, id, x, y);
		lemmings.put(id, l);
	}

	@Override
	public void saveLemming(int id) {
		this.nbSauves++;
		lemmings.remove(id);
	}

	@Override
	public void runTour() {
		lemmings.entrySet().forEach(l -> l.getValue().step());
		tour = tour + 1;
		if (tour % spawnSpeed == 0 && nbCree < sizeColony) {
			creeLemming(nbCree, level.entranceX(), level.entranceY());
			System.out.println("creation lemming " + nbCree);
			nbCree++;
		}
	}


	@Override
	public void stopCreation() {
		nbCree = getSizeColony();
	}
}