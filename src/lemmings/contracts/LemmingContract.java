package lemmings.contracts;

import lemmings.decorators.LemmingDecorator;
import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;

public class LemmingContract extends LemmingDecorator {
	// attribut ---------------------------------------------------------------
	public void checkInvariant() {

	}

	// Constructors -----------------------------------------------------------
	public LemmingContract(LemmingService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		// TODO Auto-generated method stub
		super.init(ges, id, x, y);
	}

	@Override
	public void setClasseLemming(ClasseType t) {
		// TODO Auto-generated method stub
		super.setClasseLemming(t);
	}

	@Override
	public void step() {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// post
		// TODO Auto-generated method stub
		super.step();
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

}