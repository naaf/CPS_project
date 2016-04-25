package lemmings.impl;

import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;

public class LemmingFlotteur implements ActivityIF {

	@Override
	public void step(LemmingService lm) {
		
	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.FLOTTEUR;
	}
	

}
