package lemmings.contracts;

import java.util.List;
import java.util.stream.Collectors;

import lemmings.decorators.GameEngDecorator;
import lemmings.errors.Contractor;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.LevelService;

public class GameEngContract extends GameEngDecorator {

	// attribut ---------------------------------------------------------------
	private final String SERVICE = "GameEngService";

	public void checkInvariant() {
		if (lemmings().size() > getSizeColony()) {
			Contractor.defaultContractor().invariantError(SERVICE, "getLemmings().size() > getSizeColony()");
		}
		if (0 > getNbSauves() || getNbSauves() > getSizeColony()) {
			Contractor.defaultContractor().invariantError(SERVICE, "0 > getNbSauves() > getSizeColony()");
		}
		if (isGameOver() != (getNbCrees() == getSizeColony()) && (lemmings().size() == 0)) {
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
		if (getNbCrees() != 0) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getNbCrees() != 0");
		}
		if (lemmings().size() != 0) {
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
		// post
		if (sc != (int) ((double) getNbSauves() / getSizeColony() * 100)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "getScore", "score incorrect ");
		}

		return sc;
	}

	@Override
	public LemmingService getLemming(int id) {
		// pre
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "supprimeLemming",
					"not lemmingExiste(" + id + ")");
		}
		// inv pre
		checkInvariant();
		// run
		LemmingService lm = super.getLemming(id);
		// inv post
		checkInvariant();
		// post
		return lm;
	}

	// Operators ---------------------------------------------------------------
	@Override
	public void supprimeLemming(int id) {
		// pre
		if (isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isGameOver = true");
		}
		if (!lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "supprimeLemming",
					"not lemmingExiste(" + id + ")");
		}
		// inv pre
		checkInvariant();
		// Captures
		List<Integer> lemmings_at_pre = lemmings().stream().map(LemmingService::getId).collect(Collectors.toList());
		// run
		super.supprimeLemming(id);
		// inv post
		checkInvariant();
		// post
		if (lemmings().contains(id)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "supprimeLemming", "Lemming" + id + " existe");
		}
		lemmings_at_pre.stream().filter(num -> (num != id) && lemmings().stream().noneMatch(l -> l.getId() == num))
				.forEach(num -> Contractor.defaultContractor().postconditionError(SERVICE, "supprimeLemming",
						"Lemming supprimé " + num + "!= (id) " + id));
	}

	@Override
	public void creeLemming(int id, int x, int y) {
		// pre
		if (isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isGameOver = true");
		}
		if (lemmings().size() >= getSizeColony()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "creeLemming",
					"les nombre lemmings actives atteint son limite");
		}
		if (lemmingExiste(id)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "creeLemming", "lemming existe déjà");
		}
		// inv pre
		checkInvariant();
		// Captures
		int size_at_pre = lemmings().size();
		// run
		super.creeLemming(id, x, y);
		// inv post
		checkInvariant();
		// post
		if (lemmings().size() != size_at_pre + 1) {
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
		if (isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isGameOver = true");
		}
		// inv pre
		checkInvariant();
		// Captures
		int sauves_at_pre = getNbSauves();
		List<Integer> lemmings_at_pre = lemmings().stream().map(LemmingService::getId).collect(Collectors.toList());

		// run
		super.saveLemming(id);
		// inv post
		checkInvariant();
		// post
		if (sauves_at_pre + 1 != getNbSauves()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "saveLemming",
					"sauves_at_pre + 1 != getNbSauves()");
		}
		if (lemmings().contains(id)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "saveLemming", "Lemming" + id + " existe");
		}
		lemmings_at_pre.stream().filter(num -> (num != id) && lemmings().stream().noneMatch(l -> l.getId() == num))
				.forEach(num -> Contractor.defaultContractor().postconditionError(SERVICE, "saveLemming",
						"Lemming sauvée " + num + "!= (id) " + id));
	}

	@Override
	public void runTour() {
		// pre
		if (isGameOver()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "activeTour", "isGameOver = true");
		}
		// inv pre
		checkInvariant();
		// Captures
		int tour_at_pre = getTour();
		// run
		super.runTour();
		// inv post
		checkInvariant();
		// post
		if (getTour() != tour_at_pre + 1) {
			Contractor.defaultContractor().postconditionError(SERVICE, "activeTour", "getTour() != tour_at_pre +1");
		}

	}

}
