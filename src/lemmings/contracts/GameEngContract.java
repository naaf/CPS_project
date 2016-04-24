package lemmings.contracts;

import java.util.HashSet;
import java.util.Set;

import lemmings.decorators.GameEngDecorator;
import lemmings.errors.Contractor;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
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
		if (isGameOver() != (getTour() * getSpawnSpeed() >= getSizeColony()) && (getLemmings().size() == 0)) {
			Contractor.defaultContractor().invariantError(SERVICE,
					"isGameOver() != toute la colony est creer && il n'y a plus de lemmings active");
		}
	}

	// Constructors -----------------------------------------------------------
	public GameEngContract(GameEngService delegate) {
		super(delegate);
	}

	@Override
	public void init(LevelService level, int size, int speed) {
		// pre
		if (level == null) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "level = null");
		}
		if (size <= 0 && speed <= 0) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "sizecolony<=0 || spawnspeed <=0");
		}
		// run
		super.init(level, size, speed);
		// inv post
		checkInvariant();
		// post
		if (getLevel() != level) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getLevel() != level");
		}
		if (getSizeColony() != size) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getSizeColony()!= size");
		}
		if (getSpawnSpeed() != speed) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getSpawnSpeed() != speed");
		}
		if (isGameOver()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "isGameOver() == true");
		}
		if (getTour() != 0) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getTour() != 0");
		}
		if (getNbSauves() != 0) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getNbSauves() !=0");
		}
		if (getLemmings().size() != 0) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "card(getLemmings()) !=0 ");
		}
	}

	// Observators -------------------------------------------------------------
	@Override
	public boolean isObstacle(int x, int y) {
		// pre
		if (!getLevel().caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "isObstacle",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		// inv pre
		checkInvariant();
		// run
		boolean obs = super.isObstacle(x, y);
		// inv post
		checkInvariant();
		return obs;
	}

	@Override
	public int getScore() {
		// pre
		if (!isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "getScore", "isGameOver() ==false ");
		}
		// inv pre
		checkInvariant();
		// run
		int sc = super.getScore();
		// inv post
		checkInvariant();

		return sc;
	}

	@Override
	public LemmingService getLemming(int id) {
		// pre
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "getLemming",
					"lemmingExiste(" + id + ") == null");
		}
		// inv pre
		checkInvariant();
		// run
		LemmingService lm = super.getLemming(id);
		// inv post
		checkInvariant();
		return lm;
	}

	// Operators ---------------------------------------------------------------
	@Override
	public void supprimeLemming(int id) {
		// pre
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "supprimeLemming",
					"not lemmingExiste(" + id + ")");
		}
		// inv pre
		checkInvariant();
		// Captures
		Set<Integer> lemmings_at_pre = new HashSet<>();
		lemmings_at_pre.addAll(getLemmings());
		// run
		super.supprimeLemming(id);
		// inv post
		checkInvariant();
		// post
		if (getLemmings().contains(id)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "supprimeLemming", "Lemming" + id + " existe");
		}
		lemmings_at_pre.stream().filter(num -> (num != id) && !getLemmings().contains(num))
				.forEach(num -> Contractor.defaultContractor().postconditionError(SERVICE, "supprimeLemming",
						"getLemmings() a changé aussi en " + num));

	}

	@Override
	public void creeLemming(int id, int x, int y) {
		// pre
		if (lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "creeLemming", "lemming existe déjà");
		}
		if (getLemmings().size() >= getSizeColony()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "creeLemming",
					"les nombre lemmings actives atteint son limite");
		}
		if (getLevel().isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "creerLemming", "isEditing = true");
		}
		// inv pre
		checkInvariant();
		// Captures
		int size_at_pre = getLemmings().size();
		// run
		super.creeLemming(id, x, y);
		// inv post
		checkInvariant();
		// post
		if (getLemmings().size() != size_at_pre + 1) {
			Contractor.defaultContractor().postconditionError(SERVICE, "creeLemming", "getLemmings()@pre ⋃ {id}");
		}
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "creeLemming", "not lemmingExiste(" + id + ")");
		}

	}

	@Override
	public void saveLemming(int id) {
		// pre
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "saveLemming", "not lemmingExiste(" + id + ")");
		}
		if (getLevel().isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "saveLemming", "isEditing = true");
		}
		// inv pre
		checkInvariant();
		// Captures
		int sauves_at_pre = getNbSauves();
		Set<Integer> lemmings_at_pre = new HashSet<>();
		lemmings_at_pre.addAll(getLemmings());
		// run
		super.saveLemming(id);
		// inv post
		checkInvariant();
		// post
		if (sauves_at_pre + 1 != getNbSauves()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "saveLemming",
					"sauves_at_pre + 1 != getNbSauves()");
		}
		lemmings_at_pre.stream().filter(num -> (num != id) && !getLemmings().contains(num))
				.forEach(num -> Contractor.defaultContractor().postconditionError(SERVICE, "saveLemming",
						"getLemmings() a changé aussi en " + num));
	}

	@Override
	public void activeTour() {
		// pre
		if (isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isGameOver = true");
		}
		if (getLevel().isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isEditing = true");
		}
		// inv pre
		checkInvariant();
		// Captures
		int tour_at_pre = getTour();
		// run
		super.activeTour();
		// inv post
		checkInvariant();
		// post
		if (getTour() != tour_at_pre + 1) {
			Contractor.defaultContractor().postconditionError(SERVICE, "activeTour", "getTour() != tour_at_pre +1");
		}

	}

}
