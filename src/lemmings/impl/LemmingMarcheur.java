package lemmings.impl;

import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.Direction;
import lemmings.services.LemmingService;

public class LemmingMarcheur implements ActivityIF {

	@Override
	public void step(LemmingService lm) {
		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
			if (lm.gameEng().lemmings().stream().anyMatch(l -> (l.getX() == lm.getX() + sens) && l.getY() == lm.getY()
					&& l.getClasseType().getTypeClasse() == ClasseType.STOPPEUR)) {
				lm.changeDirection();
			}
			else if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() - 1)
					|| (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY())
							&& lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() - 2))) {
				lm.changeDirection();
			} else {
				if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY())) {
					lm.setY(lm.getY() - 1);
				}
				lm.setX(lm.getX() + sens);
			}
		} else {
			lm.setClasseLemming(new LemmingTombeur());
		}
	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.MARCHEUR;
	}

}
