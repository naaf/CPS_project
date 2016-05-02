//package lemmings.impl;
//
//import lemmings.services.ActivityLemming;
//import lemmings.services.ClasseType;
//import lemmings.services.Direction;
//import lemmings.services.LemmingService;
//
//public class LemmingConstructeur implements ActivityLemming {
//	private int attenteConstruction;
//	private int nbDalle;
//	private static final int LIMITE_DALLE = 12;
//	private static final int ATTENTE_CONSTRUCTION = 3;
//
//	public LemmingConstructeur() {
//		this.attenteConstruction = 0;
//		this.nbDalle = 0;
//	}
//
//	@Override
//	public void step(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
//			for (int i = 0; i < 3; i++) {
//				for (int j = i == 0 ? 1 : i; i == 0 ? j < 4 : j < 3; j++) {
//					if (lm.gameEng().isObstacle(lm.getX() + sens * j, lm.getY() - i) || nbDalle == LIMITE_DALLE) {
//						lm.setClasseType(new LemmingMarcheur());
//						return;
//					}
//				}
//			}
//			if (attenteConstruction == ATTENTE_CONSTRUCTION) {
//
//				if (lm.gameEng().lemmings().stream().anyMatch(l -> {
//					for (int i = 1; i <= 3; i++) {
//						if (l.getX() == lm.getX() + i * sens && (l.getY() == lm.getY() || l.getY() - 1 == lm.getY()))
//							return true;
//					}
//					return false;
//				})) {
//					return;
//				}
//
//				for (int i = 1; i <= 3; i++) {
//					lm.gameEng().getLevel().build(lm.getX() + i * sens, lm.getY());
//				}
//				lm.setY(lm.getY() - 1);
//				lm.setX(lm.getX() + 2 * (sens));
//				nbDalle += 3;
//				attenteConstruction = 0;
//			}
//			attenteConstruction++;
//		} else {
//			lm.setClasseType(new LemmingTombeur());
//		}
//	}
//
//	@Override
//	public ClasseType getTypeClasse() {
//		return ClasseType.CONSTRUCTEUR;
//	}
//}
