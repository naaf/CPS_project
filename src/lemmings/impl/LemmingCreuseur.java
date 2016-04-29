package lemmings.impl;

import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;
import lemmings.services.Nature;

public class LemmingCreuseur implements ActivityLemming {

	@Override
	public void step(LemmingService lm) {
		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
			if (lm.gameEng().getLevel().getNature(lm.getX(), lm.getY() + 1) != Nature.DIRTY) {
				lm.setClasseLemming( new LemmingMarcheur());
			} else {
				for (int i = -1; i < 2; i++) {
					if (lm.gameEng().getLevel().getNature(lm.getX() + i, lm.getY()+ 1) == Nature.DIRTY) {
						lm.gameEng().getLevel().remove(lm.getX() + i, lm.getY()+ 1);
					}
				}
				lm.setY(lm.getY() + 1);
			}
		} else {
			lm.setClasseLemming(new LemmingTombeur());
		}

	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.CREUSEUR;
	}

}
