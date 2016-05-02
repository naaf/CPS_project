package lemmings.contracts;

import lemmings.decorators.LemmingDecorator;
import lemmings.errors.Contractor;
import lemmings.services.ClasseType;
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
		if (!(getClasseType() == ClasseType.MARCHEUR)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "classeType() != Marcheur");
		}
		if (gameEng() != ges) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "gameEng() != ges");
		}
		
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------
	@Override
	public void setClasseType(ClasseType cl) {
		// pre
		// inv pre
		checkInvariant();
		// Captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		Direction dir_at_pre = getDirection();

		// run
		super.setClasseType(cl);
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
		if (getClasseType() != cl) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getClasseLemming() != cl");
		}
		if (getClasseType() != cl) {
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
		ClasseType ct_at_pre = getClasseType();
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
		if (getClasseType() != ct_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection",
					"getClasseType() != ct_at_pre");
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