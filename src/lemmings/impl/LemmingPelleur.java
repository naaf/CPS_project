//package lemmings.impl;
//
//import lemmings.services.ActivityLemming;
//import lemmings.services.ClasseType;
//import lemmings.services.Direction;
//import lemmings.services.LemmingService;
//import lemmings.services.Nature;
//
//public class LemmingPelleur implements ActivityLemming {
//	private int nbCasse;
//	private static final int LIMITE_CASSE = 20;
//
//	public LemmingPelleur() {
//		nbCasse = 0;
//	}
//
//	@Override
//	public void step(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
//			for (int i = 0; i > -3; i--) {
//				if (nbCasse >= LIMITE_CASSE
//						|| lm.gameEng().getLevel().getNature(lm.getX() + sens, lm.getY() + i) != Nature.DIRTY) {
//					lm.setClasseType(new LemmingMarcheur());
//					return;
//				}
//			}
//			for (int i = 0; i > -3; i--) {
//				lm.gameEng().getLevel().remove(lm.getX() + sens, lm.getY() + i);
//			}
//			lm.setX(lm.getX() + sens);
//			nbCasse += 3;
//
//		} else {
//			lm.setClasseType(new LemmingTombeur());
//		}
//	}
//
//	@Override
//	public ClasseType getTypeClasse() {
//		return ClasseType.PELLEUR;
//	}
//
//}
