package lemmings.contracts;

import lemmings.decorators.LemmingDecorator;
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

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

}