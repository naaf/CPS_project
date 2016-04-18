package lemmings.contracts;

import lemmings.decorators.LevelDecorator;
import lemmings.errors.Contractor;
import lemmings.errors.PreconditionError;
import lemmings.services.LevelService;
import lemmings.services.Nature;

public class LevelContract extends LevelDecorator {

	// attribut ---------------------------------------------------------------
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
	public void setNature(int x, int y, Nature nat) {
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
		// Captures
		Nature[][] grille_at_pre = new Nature[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				grille_at_pre[i][j] = getNature(i, j);
			}
		}
		// run
		super.setNature(x, y, nat);
		// inv post
		checkInvariant();
		// post:
		if (!isEditing()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setNature", "editing == false");
		}
		if (getNature(x, y) != nat) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setNature",
					"nature(" + x + "," + y + ")" + "!=" + nat);
		}
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (i != x || j != y && getNature(i, j) != grille_at_pre[i][j]) {
					Contractor.defaultContractor().postconditionError(SERVICE, "setNature",
							"nature(" + x + "," + y + ")@pre" + " a changé");
				}
			}
		}

	}

	@Override
	public void remove(int x, int y) {
		// pre
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "remove", "editing == true");
		}
		if (!caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "remove",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		if (getNature(x, y) != Nature.DIRTY) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setNature",
					"nature(" + x + "," + y + ")" + "!= DIRTY");
		}

		// inv pre
		checkInvariant();
		// Captures
		Nature[][] grille_at_pre = new Nature[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				grille_at_pre[i][j] = getNature(i, j);
			}
		}
		int exitX_at_pre = exitX();
		int exitY_at_pre = exitY();
		int entranceX_at_pre = entranceX();
		int entranceY_at_pre = entranceY();
		// run
		super.remove(x, y);
		// inv post
		checkInvariant();
		// post:
		if (isEditing()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove", "editing == true");
		}
		if (getNature(x, y) != Nature.EMPTY) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove",
					"nature(" + x + "," + y + ")" + "!= EMPTY");
		}
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (i != x || j != y && getNature(i, j) != grille_at_pre[i][j]) {
					Contractor.defaultContractor().postconditionError(SERVICE, "remove",
							"nature(" + x + "," + y + ")@pre" + " a changé");
				}
			}
		}
		if (exitX_at_pre != exitX()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove", "exitX" + " a changé");
		}
		if (exitY_at_pre != exitY()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove", "exitY" + " a changé");
		}
		if (entranceX_at_pre != entranceX()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove", "entranceX" + " a changé");
		}
		if (entranceY_at_pre != entranceY()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "remove", "entranceY" + " a changé");
		}
	}

	@Override
	public void build(int x, int y) {
		// pre
		if (isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "build", "editing == true");
		}
		if (!caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "build",
					"case(" + x + "," + y + ")" + "notExiste");
		}
		if (getNature(x, y) != Nature.EMPTY) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build",
					"nature(" + x + "," + y + ")" + "!= EMPTY");
		}
		if (x == entranceX() && (y == entranceY() || y == entranceY() - 1 && y == entranceY() + 1)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "build", "editing == true");
		}
		if (x == exitX() && (y == exitY() || y == exitY() + 1)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "build", "editing == true");
		}
		// inv pre
		checkInvariant();
		// Captures
		Nature[][] grille_at_pre = new Nature[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				grille_at_pre[i][j] = getNature(i, j);
			}
		}
		int exitX_at_pre = exitX();
		int exitY_at_pre = exitY();
		int entranceX_at_pre = entranceX();
		int entranceY_at_pre = entranceY();

		// run
		super.build(x, y);
		// inv post
		checkInvariant();
		// post
		if (isEditing()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build", "editing == true");
		}
		if (getNature(x, y) != Nature.DIRTY) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build",
					"nature(" + x + "," + y + ")" + "!= DIRTY");
		}
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (i != x || j != y && getNature(i, j) != grille_at_pre[i][j]) {
					Contractor.defaultContractor().postconditionError(SERVICE, "build",
							"nature(" + x + "," + y + ")@pre" + " a changé");
				}
			}
		}
		if (exitX_at_pre != exitX()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build", "exitX" + " a changé");
		}
		if (exitY_at_pre != exitY()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build", "exitY" + " a changé");
		}
		if (entranceX_at_pre != entranceX()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build", "entranceX" + " a changé");
		}
		if (entranceY_at_pre != entranceY()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "build", "entranceY" + " a changé");
		}

	}

	@Override
	public void goPlay(int eX, int eY, int qX, int qY) {
		// pre
		if (!isEditing()) {
			Contractor.defaultContractor().preconditionError(SERVICE, "goPlay", "editing == false");
		}
		for (int x = 0; x < getWidth(); x++) {
			if (getNature(x, 0) != Nature.METAL || getNature(x, getHeight() - 1) != Nature.METAL) {
				Contractor.defaultContractor().preconditionError(SERVICE, "goPlay", "ligne de mur horizontal");
			}
		}
		for (int y = 0; y < getHeight(); y++) {
			if (getNature(0, y) != Nature.METAL || getNature(getWidth() - 1, y) != Nature.METAL) {
				Contractor.defaultContractor().preconditionError(SERVICE, "goPlay", "ligne mur verticale ");
			}
		}
		if (getNature(eX, eY - 1) != Nature.EMPTY || getNature(eX, eY) != Nature.EMPTY
				|| getNature(eX, eY + 1) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "goPlay", "entrance");

		}
		if (getNature(qX, qY - 1) != Nature.METAL || getNature(qX, qY) != Nature.EMPTY
				|| getNature(qX, qY + 1) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "goPlay", "exit");
		}
		// inv pre
		checkInvariant();
		// Captures
		Nature[][] grille_at_pre = new Nature[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				grille_at_pre[i][j] = getNature(i, j);
			}
		}
		// run
		super.goPlay(eX, eY, qX, qY);
		// inv post
		checkInvariant();
		// post
		if (isEditing()) {
			Contractor.defaultContractor().postconditionError(SERVICE, "goPlay", "editing == true");
		}
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (getNature(i, j) != grille_at_pre[i][j]) {
					Contractor.defaultContractor().postconditionError(SERVICE, "goPlay",
							"nature(" + i + "," + j + ")@pre" + " a changé");
				}
			}
		}
		if(entranceX() != eX){
			Contractor.defaultContractor().postconditionError(SERVICE, "goPlay", "entranceX()!=" + eX);
		}
		if(entranceY() != eY){
			Contractor.defaultContractor().postconditionError(SERVICE, "goPlay", "entranceY()!=" + eY);
		}
		if(exitX() != qX){
			Contractor.defaultContractor().postconditionError(SERVICE, "goPlay", "exitX()!=" + qX);
		}
		if(exitY() != qY){
			Contractor.defaultContractor().postconditionError(SERVICE, "goPlay", "exitY()!=" + qY);
		}
	}

}
