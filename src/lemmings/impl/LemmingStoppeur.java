package lemmings.impl;

import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;

public class LemmingStoppeur implements ActivityLemming {

	@Override
	public void step(LemmingService lm) {
		
	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.STOPPEUR;
	}

}
