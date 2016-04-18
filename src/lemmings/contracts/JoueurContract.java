package lemmings.contracts;

import lemmings.decorators.JoueurDecorator;
import lemmings.services.JoueurService;

public class JoueurContract extends JoueurDecorator {
	// attribut ---------------------------------------------------------------
	public void checkInvariant() {

	}

	// Constructors -----------------------------------------------------------
	public JoueurContract(JoueurService delegate) {
		super(delegate);
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

}
