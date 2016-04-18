package lemmings.contracts;

import lemmings.decorators.LevelDecorator;
import lemmings.errors.Contractor;
import lemmings.errors.PreconditionError;
import lemmings.services.LevelService;
import lemmings.services.Nature;

public class LevelContract extends LevelDecorator {

	private final String SERVICE = "LevelService";

	public void checkInvariant() {
		// inv: getHeight() >= 5 && getWidth() >= 4
		if (getHeight() < 5 || getWidth() < 4) {
			Contractor.defaultContractor().invariantError(SERVICE, "dimension WxH < 4x5");
		}
	}

	// Constructors -----------------------------------------------------------
	public LevelContract(LevelService delegate) {
		super(delegate);
	}

	@Override
	public void init(int width, int height) {
		// pre
		if (getHeight() < 5 || getWidth() < 4) {
			throw new PreconditionError("Dimension inférieur au minimal 4 x 5");
		}
		// run
		super.init(width, height);
		// inv post
		checkInvariant();
		// post
		if (getHeight() < 5 || getWidth() < 4) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "dimension WxH < 4x5");
		}
		if (!isEditing()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "editing = false");
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (getNature(x, y) != Nature.EMPTY)
					Contractor.defaultContractor().postconditionError(SERVICE, "init",
							"(" + x + "," + y + ")" + " != EMPTY");
			}
		}

	}

	// Observators -------------------------------------------------------------

	@Override
	public Nature getNature(int x, int y) {
		// pre: caseExiste(x,y)
		if (!caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "getNature",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		return super.getNature(x, y);
	}

	@Override
	public boolean caseExiste(int x, int y) {
		if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "caseExiste",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		return super.caseExiste(x, y);
	}

	@Override
	public int exitX() {
		// pre: isEditing() = false
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "exitX", "editing = true");
		}
		return super.exitX();
	}

	@Override
	public int exitY() {
		// pre: isEditing() = false
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "exitY", "editing = true");
		}
		return super.exitY();
	}

	@Override
	public int entranceX() {
		// pre: isEditing() = false
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "entranceX", "editing = true");
		}
		return super.entranceX();
	}

	@Override
	public int entranceY() {
		// pre: isEditing() = false
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "entranceY", "editing = true");
		}
		return super.entranceY();
	}

	// Operators ---------------------------------------------------------------

	@Override
	public void setNature(int x, int y, Nature n) {
		// pre
		if (!isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "setNature", "editing == false");
		}
		if (!caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "setNature",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		// inv pre
		checkInvariant();
		// run
		super.setNature(x, y, n);
		// inv post
		checkInvariant();
	}

	@Override
	public void remove(int x, int y) {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// TODO Auto-generated method stub
		super.remove(x, y);
	}

	@Override
	public void build(int x, int y) {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// TODO Auto-generated method stub
		super.build(x, y);
	}

	@Override
	public void goPlay(int eX, int eY, int qX, int qY) {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// TODO Auto-generated method stub
		super.goPlay(eX, eY, qX, qY);
	}

}
