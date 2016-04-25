package lemmings.impl;

import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;

public class LemmingTombeur implements ActivityIF {
	private static final int LIMITE_CHUTE = 8;
	private int chute;

	public LemmingTombeur() {
		super();
		this.chute = 0;
	}

	@Override
	public void step(LemmingService lm) {
		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
			if (chute >= LIMITE_CHUTE
					&& !lm.getCumul().filter(c -> c.getTypeClasse() == ClasseType.FLOTTEUR).isPresent()) {
				lm.gameEng().supprimeLemming(lm.getId());
			}
			lm.setClasseLemming(new LemmingMarcheur());
		} else {
			if (lm.getCumul().filter(c -> c.getTypeClasse() == ClasseType.FLOTTEUR).isPresent()) {
				lm.setY(lm.getY() + (chute % 2 == 0 ? 1 : 0));
			} else {
				lm.setY(lm.getY() + 1);
			}
			chute++;
		}

	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.TOMBEUR;
	}

}