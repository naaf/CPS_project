package lemmings.contracts;

import lemmings.decorators.GameEngDecorator;
import lemmings.errors.Contractor;
import lemmings.services.GameEngService;
import lemmings.services.LevelService;

public class GameEngContract extends GameEngDecorator {

	// attribut ---------------------------------------------------------------
	private final String SERVICE = "GameEngService";

	public void checkInvariant() {
		if (getLemmings().size() > getSizeColony()) {
			Contractor.defaultContractor().invariantError(SERVICE, "getLemmings().size() > getSizeColony()");
		}
		if (0 > getNbSauves() || getNbSauves() > getSizeColony()) {
			Contractor.defaultContractor().invariantError(SERVICE, "0 > getNbSauves() > getSizeColony()");
		}
		if (getScore() != getNbSauves() / getSizeColony() * 100) {
			Contractor.defaultContractor().invariantError(SERVICE,
					"getScore() != getNbSauves() / getSizeColony() * 100");
		}
		if (isGameOver() != (getTour() * getSpawnSpeed() != getSizeColony()) || (getLemmings().size() != 0)) {
			Contractor.defaultContractor().invariantError(SERVICE,
					"isGameOver() != toute la colony est creer && il n'y a plus de lemmings active");
		}
	}

	// Constructors -----------------------------------------------------------
	public GameEngContract(GameEngService delegate) {
		super(delegate);
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

	@Override
	public void supprimeLemming(int id) {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// post
		// TODO Auto-generated method stub
		super.supprimeLemming(id);
	}

	@Override
	public void creeLemming(int id, int x, int y) {
		// TODO Auto-generated method stub
		super.creeLemming(id, x, y);
	}

	@Override
	public void saveLemming(int id) {
		// TODO Auto-generated method stub
		super.saveLemming(id);
	}

	@Override
	public void activeTour() {
		// TODO Auto-generated method stub
		super.activeTour();
	}

	@Override
	public void init(LevelService level, int size, int speed) {
		// TODO Auto-generated method stub
		super.init(level, size, speed);
	}

	@Override
	public boolean isObstacle(int x, int y) {
		// TODO Auto-generated method stub
		return super.isObstacle(x, y);
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return super.getScore();
	}

}
