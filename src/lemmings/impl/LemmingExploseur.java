package lemmings.impl;

import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;
import lemmings.services.Nature;

public class LemmingExploseur implements ActivityIF {
	private static final int ATTENTE_TOUR = 5;
	private int attente;

	@Override
	public void step(LemmingService lm) {
		if (attente == ATTENTE_TOUR) {
			int x = lm.getX(), y = lm.getY();
			for (int i = -1; i < 2; i++) {
				for (int j = -2; j < 3; j++) {
					if (lm.gameEng().getLevel().caseExiste(x + j, y + i)
							&& lm.gameEng().getLevel().getNature(x + j, y + i) != Nature.EMPTY
							&& lm.gameEng().getLevel().getNature(x + j, y + i) != Nature.METAL) {
						lm.gameEng().getLevel().remove(x + j, y + i);
					}
				}
				lm.gameEng().lemmings().stream()
						.filter(l -> x - 2 <= l.getX() && l.getX() <= x + 2 && y - 1 <= l.getY() && l.getY() <= y + 1)
						.forEach(l -> lm.gameEng().supprimeLemming(l.getId()));
			}
		}
		attente++;
	}

	@Override
	public ClasseType getTypeClasse() {
		return ClasseType.EXPLOSEUR;
	}

}
