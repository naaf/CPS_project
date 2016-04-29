package lemmings.contracts;

import lemmings.decorators.LemmingDecorator;
import lemmings.errors.Contractor;
import lemmings.impl.LemmingMarcheur;
import lemmings.services.ActivityLemming;
import lemmings.services.Direction;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.Nature;

public class LemmingContract extends LemmingDecorator {
	private static final String SERVICE = "LemmingService";

	// attribut ---------------------------------------------------------------
	public void checkInvariant() {
		if (!gameEng().getLevel().caseExiste(getX(), getY())) {
			Contractor.defaultContractor().invariantError(SERVICE, "coordonnées de lemmings out of bound");
		}
		if ((gameEng().getLevel().getNature(getX(), getY() - 1) != Nature.EMPTY)
				|| (gameEng().getLevel().getNature(getX(), getY()) != Nature.EMPTY)) {
			Contractor.defaultContractor().invariantError(SERVICE, "les cases lemming ne sont pas empty");
		}
	}

	// Constructors -----------------------------------------------------------
	public LemmingContract(LemmingService delegate) {
		super(delegate);
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		// pre
		if (ges == null) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "ges = null");
		}

		if (!ges.getLevel().caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "not existe case");
		}
		if (ges.getLevel().getNature(x, y) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "case lemming not empty");
		}
		if (ges.getLevel().getNature(x, y - 1) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "case tete lemming not empty");
		}

		// run
		super.init(ges, id, x, y);
		// inv post
		checkInvariant();
		// post
		if (getX() != x) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getX() != x");
		}
		if (getY() != y) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getY() != y");
		}
		if (getDirection() != Direction.DROITE) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getDirection() != Droite");
		}
		if (!(getClasseLemming() instanceof LemmingMarcheur)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "classeType() != Marcheur");
		}
		if (gameEng() != ges) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "gameEng() != ges");
		}
		if (getClasseLemming() == null) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getClasseLemming() == null");
		}
		if (getCumul().isPresent()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getCumul() != null");
		}
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------
	@Override
	public void setClasseLemming(ActivityLemming cl) {
		// pre
		// inv pre
		checkInvariant();
		// Captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		Direction dir_at_pre = getDirection();

		// run
		super.setClasseLemming(cl);
		// inv post
		checkInvariant();
		// post
		if (getX() != x_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getX() != x_at_pre");
		}
		if (getY() != y_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getY() != y_at_pre");
		}
		if (getDirection() != dir_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming",
					"getDirection() != dir_at_pre");
		}
		if (getClasseLemming() != cl) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getClasseLemming() != cl");
		}
		if (getClasseType() != cl.getTypeClasse()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming",
					"getClasseType() != cl.getTypeClasse()");
		}
	}

	@Override
	public void changeDirection() {
		// pre
		// inv pre
		checkInvariant();
		// Captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		ActivityLemming ct_at_pre = getClasseLemming();
		Direction dir_at_pre = getDirection();
		// run
		super.changeDirection();
		// inv post
		checkInvariant();
		// post
		if (getX() != x_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection", "getX() != x_at_pre");
		}
		if (getY() != y_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection", "getY() != y_at_pre");
		}
		if (getDirection() == dir_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection",
					"changeDirection() == dir_at_pre");
		}
		if (getClasseLemming() != ct_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection",
					"getClasseType() != ct_at_pre");
		}

	}

	@Override
	public void setX(int x) {
		// pre
		if (0 > getX() || getX() > gameEng().getLevel().getWidth()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "setX", "getX() out of bound");
		}
		// inv pre
		checkInvariant();
		// Captures
		// run
		super.setX(x);
		// inv post
		checkInvariant();
		// post
		if (getX() != x) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setX", "getX() != x");
		}
	}

	@Override
	public void setY(int y) {
		// pre
		if (0 > getY() || getY() > gameEng().getLevel().getHeight()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "setY", "geY() out of bound");
		}
		// inv pre
		checkInvariant();
		// Captures
		// run
		super.setY(y);
		// inv post
		checkInvariant();
		// post
		if (getY() != y) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setY", "getY() != y");
		}
	}

	@Override
	public void setCumul(ActivityLemming cumul) {
		// pre
		if (cumul == null) {
			Contractor.defaultContractor().preconditionError(SERVICE, "setCumul", "cumul = null");
		}
		// inv pre
		checkInvariant();
		// Captures
		// run
		super.setCumul(cumul);
		// inv post
		checkInvariant();
		// post

		try {
			if (getCumul().get() != cumul) {
				Contractor.defaultContractor().postconditionError(SERVICE, "getCumul", "getCumul().get() != cumul");
			}
		} catch (Exception e) {
			Contractor.defaultContractor().postconditionError(SERVICE, "getCumul", "getCumul().get() != cumul");
		}
	}

	@Override
	public void step() {
		// pre
		// inv pre
		checkInvariant();
		// run
		super.step();
		// inv post
		checkInvariant();
		// post
	}

}