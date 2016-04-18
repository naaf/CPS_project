package lemmings.contracts;

import lemmings.decorators.GameEngDecorator;
import lemmings.services.GameEngService;

public class GameEngContract extends GameEngDecorator {

	// attribut ---------------------------------------------------------------
	public void checkInvariant() {

	}

	// Constructors -----------------------------------------------------------
	public GameEngContract(GameEngService delegate) {
		super(delegate);
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------

}