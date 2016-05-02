//package lemmings.impl;
//
//import lemmings.services.ActivityLemming;
//import lemmings.services.ClasseType;
//import lemmings.services.Direction;
//import lemmings.services.LemmingService;
//
//public class LemmingGrimpeur implements ActivityLemming {
//
//	@Override
//	public void step(LemmingService lm) {
//		int sens = (lm.getDirection() == Direction.DROITE) ? 1 : -1;
//		if (!lm.gameEng().isObstacle(lm.getX(), lm.getY() + 1)){
//			lm.setClasseType(new LemmingTombeur());
//		}
//		if (!lm.gameEng().isObstacle(lm.getX(), lm.getY() - 1) && lm.gameEng().isObstacle(lm.getX() + sens, lm.getY() )) {
//			if (!lm.gameEng().isObstacle(lm.getX(), lm.getY()  - 2) && lm.gameEng().isObstacle(lm.getX() + sens, lm.getY()  - 1)) {
//				lm.setY(lm.getY() -1);
//			} else if (!lm.gameEng().isObstacle(lm.getX() + sens, lm.getY()  - 1) && !lm.gameEng().isObstacle(lm.getX() + sens, lm.getY()  - 2)) {
//				lm.setY(lm.getY() -1);
//				lm.setX(lm.getX() + sens) ;
//			} else {
//				lm.setClasseType( new LemmingMarcheur());
//			}
//		} else {
//			lm.setClasseType(new LemmingMarcheur());
//		}
//
//	}
//
//	@Override
//	public ClasseType getTypeClasse() {
//		 return ClasseType.GRIMPEUR;
//	}
//
//}
