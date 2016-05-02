//package lemmings.impl;
//
//import lemmings.services.ActivityLemming;
//import lemmings.services.ClasseType;
//import lemmings.services.Direction;
//import lemmings.services.LemmingService;
//import lemmings.services.Nature;
//
//public class LemmingMineur implements ActivityLemming {
//	private boolean diagB;
//
//	public LemmingMineur() {
//		diagB = false;
//	}
//
//	private boolean diagH(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (diagB)
//			return false;
//		if (!lm.gameEng().isObstacle(lm.getX() + sens, lm.getY())
//				|| lm.gameEng().getLevel().getNature(lm.getX() + sens, lm.getY() - 1) == Nature.METAL
//				|| lm.gameEng().getLevel().getNature(lm.getX() + sens, lm.getY() - 2) == Nature.METAL) {
//			return false;
//		}
//		if (lm.gameEng().lemmings().stream().anyMatch(
//				l -> (l.getX() == lm.getX() + sens) && (l.getY() == lm.getY() - 1 || l.getY() == lm.getY() - 2)
//						&& l.getClasseLemming().getTypeClasse() == ClasseType.STOPPEUR)) {
//			return false;
//		}
//		return true;
//	}
//
//	private boolean diagB(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (lm.gameEng().getLevel().getNature(lm.getX() + sens, lm.getY()) == Nature.METAL
//				|| lm.gameEng().getLevel().getNature(lm.getX() + sens, lm.getY() + 1) == Nature.METAL) {
//			return false;
//		}
//		if (lm.gameEng().lemmings().stream().anyMatch(l -> (l.getX() == lm.getX() + sens) && l.getY() == lm.getY()
//				&& l.getClasseLemming().getTypeClasse() == ClasseType.STOPPEUR)) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public void step(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)) {
//			if (diagH(lm)) {
//				if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() - 1)) {
//					lm.gameEng().getLevel().remove(lm.getX() + sens, lm.getY() - 1);
//				}
//				if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() - 2)) {
//					lm.gameEng().getLevel().remove(lm.getX() + sens, lm.getY() - 2);
//				}
//				lm.setX(lm.getX() + sens);
//				lm.setY(lm.getY() - 1);
//			} else if (diagB(lm)) {
//				if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY())) {
//					lm.gameEng().getLevel().remove(lm.getX() + sens, lm.getY());
//				}
//				if (lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() + 1)) {
//					lm.gameEng().getLevel().remove(lm.getX() + sens, lm.getY() + 1);
//				}
//				lm.setX(lm.getX() + sens);
//				lm.setY(lm.getY() + 1);
//				this.diagB = true;
//
//			} else {
//				lm.setClasseType(new LemmingMarcheur());
//			}
//		} else {
//			lm.setClasseType(new LemmingTombeur());
//		}
//
//	}
//
//	@Override
//	public ClasseType getTypeClasse() {
//		return ClasseType.MINEUR;
//	}
//
//}
