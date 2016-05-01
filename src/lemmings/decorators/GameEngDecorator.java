package lemmings.decorators;

import java.util.Collection;

import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.LevelService;

public class GameEngDecorator implements GameEngService {
	
	// attribut ------------------------------------------------------------
	private GameEngService delegate;

	// Constructors --------------------------------------------------------
	public GameEngDecorator(GameEngService delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void init(LevelService level, int size, int speed) {
		delegate.init(level, size, speed);		
	}

	// Observators ---------------------------------------------------------
	@Override
	public LevelService getLevel() {
		return delegate.getLevel();
	}


	@Override
	public int getSizeColony() {
		return delegate.getSizeColony();
	}

	@Override
	public int getSpawnSpeed() {
		return delegate.getSpawnSpeed();
	}

	@Override
	public int getTour() {
		return delegate.getTour();
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return delegate.isObstacle(x, y);
	}

	@Override
	public boolean isGameOver() {
		return delegate.isGameOver();
	}

	@Override
	public int getScore() {
		return delegate.getScore();
	}

	@Override
	public int getNbSauves() {
		return delegate.getNbSauves();
	}

	@Override
	public boolean lemmingExiste(int id) {
		return delegate.lemmingExiste(id);
	}

	@Override
	public LemmingService getLemming(int id) {
		return delegate.getLemming(id);
	}

	@Override
	public Collection<LemmingService> lemmings() {
		return delegate.lemmings();
	}
	
	@Override
	public int getNbCrees() {
		return delegate.getNbCrees();
	}

	// Operators -----------------------------------------------------------
	@Override
	public void supprimeLemming(int id) {
		delegate.supprimeLemming(id);		
	}

	@Override
	public void creeLemming(int id, int x, int y) {
		delegate.creeLemming(id, x, y);		
	}

	@Override
	public void saveLemming(int id) {
		delegate.saveLemming(id);		
	}

	@Override
	public void runTour() {
		delegate.runTour();		
	}



	@Override
	public void stopCreation() {
		delegate.stopCreation();
	}

}
