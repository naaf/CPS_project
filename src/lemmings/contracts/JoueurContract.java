package lemmings.contracts;

import lemmings.decorators.JoueurDecorator;
import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.JoueurService;
import lemmings.services.LemmingService;

public class JoueurContract extends JoueurDecorator {
	// attribut ---------------------------------------------------------------
	public void checkInvariant() {

	}

	// Constructors -----------------------------------------------------------
	public JoueurContract(JoueurService delegate) {
		super(delegate);
	}

	@Override
	public void init(GameEngService gameEng, int jetons) {
		// TODO Auto-generated method stub
		super.init(gameEng, jetons);
	}

	@Override
	public void assignerClasse(ClasseType ct, LemmingService l) {
		// TODO Auto-generated method stub
		super.assignerClasse(ct, l);
	}

	@Override
	public void reset() {
		// pre
		// inv pre
		checkInvariant();
		// run
		// inv post
		checkInvariant();
		// post
		// TODO Auto-generated method stub
		super.reset();
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

}
