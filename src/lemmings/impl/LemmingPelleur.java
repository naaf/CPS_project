package lemmings.impl;

import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;

public class LemmingPelleur implements ActivityIF {

	@Override
	public void step(LemmingService lm) {
		// TODO Auto-generated method stub

	}

	@Override
	public ClasseType getTypeClasse() {
		
		return ClasseType.PELLEUR;
	}

}
