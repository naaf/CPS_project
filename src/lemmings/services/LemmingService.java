package lemmings.services;

import java.util.Optional;

public interface LemmingService {
	// Observators ---------------------------------------------------------
	GameEngService gameEng();
	int getX();
	int getY();
	int getId();
	Direction getDirection();
	ClasseType getClasseType();
	ActivityLemming getClasseLemming();
	Optional<ActivityLemming> getCumul();
	
	// Invariants ----------------------------------------------------------
	/**
	 * env: gameEng().level().caseExiste(getX(), getY())
	 * env: gameEng().level().getNature(x, y +1) = EMPTY
	 * env: gameEng().level().getNature(x, y) = EMPTY
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: ges != NULL
	 * pre: ges.level().caseExiste(x,y)
	 * pre: ges.level().getNature(x, y) = EMPTY
	 * pre: gameEng()::level().getNature(x, y +1) = EMPTY
	 * post: getX() = x
	 * post: getY() = y
	 * post: getDirection = DROITE
	 * post: getClasseType = MARCHEUR
	 * post: getGameEng() = gameEng
	 * post: getClasseLemming != null
	 * post: getCumul() = null
	 */
	void init(GameEngService ges,int id, int x, int y);
	
	// Operators -----------------------------------------------------------
	
	/**
	 * pre: 0<= x && x < gameEng().getLevel().getWidh()
	 * post: setX(x).getX() = x
	 */
	void setX(int x);
	
	/**
	 * pre: 0<= y && x < gameEng().getLevel().getHeight()
	 * post: setY(y).getY() = y
	 */
	void setY(int y);
	
	/**
	 * pre: cumulActivity != null
	 * post: setCumul(cumulActivity).getCumul() = cumulActivity
	 */
	void setCumul(ActivityLemming cumulActivity);
	
	/**
	 * post: setClasseLemming(activityLemming).getClasseType = activityLemming.getClasseType()
	 * post: setClasseLemming(activityLemming).getClasseLemming = activityLemming
	 * post: setClasseLemming(activityLemming).getX() = getX()@pre
	 * post: setClasseLemming(activityLemming).getY() = getY()@pre
	 * post: setClasseLemming(activityLemming).getDirection() = getDirection()@pre
	 */
	void setClasseLemming(ActivityLemming activityLemming);
	
	/**
	 * post: if getDirection = GAUCHE then 
	 * 			changeDirection().getDirextion = DROITE
	 * 		else changeDirection().getDirextion = GAUCHE
	 * post: changeDirection().getX() = getX()@pre
	 * post: changeDirection().getY() = getY()@pre
	 * post: changeDirection().getClasseType() = getClasseType()@pre
	 */
	void changeDirection();
	
	/**
	 * pre: gameEng().isGameOver() = false
	 * post:
	 * if getClasseType(L) = MARCHEUR then :
	 *		if isObstacle(L, getX(), getY()-1)  then :
	 *			getClasseType() = getClasseType()@pre
	 *			if isObstacle(getX()+1,getY()+1) || ( isObstacle(getX()+1, getY()) && isObstacle(getY()+1,getY()+2) ) then :
	 *				getDirection() = changeDirection().getDirection()
	 *				getX() = getX()@pre
	 *				getY() = getY()@pre
	 *			else 
	 *				getDirection() = getDirection()@pre
	 *				getX() = getX()@pre +1
	 *				if isObstacle(getX()+1, getY())  then :
	 *					getY() = getY()@pre +1
	 *				else
	 *					getY() = getY()@pre
	 *		else
	 *			getClasseType() = TOMBEUR
	 *			
	 *			
	 *	else if getClasseType() = TOMBEUR then :
	 *		getX() = getX()@pre
	 *		getDirection() = getDirection()@pre
	 *		if(isObstaclegetX(), getY()-1)) then :
	 *			getY() = getY()@pre
	 *			getClasseType() = MARCHEUR
	 *			if enChute >= 8 then :
	 *				L ∉ GameEng::lemmings(gameEng(L))
	 *		else
	 *			getY() = getY()@pre -1
	 *			getClasseType() = getClasseType()@pre
	 *			enChute() = enChute()@pre +1
	 *			
	 *	else if classeType(L) = CREUSEUR then :
	 *		getX() = getX()@pre
	 *		getDirection() = getDirection()@pre
	 *		if Level::nature(GameEng::level(gameEng(L)), x(L), y(L)-1) = DIRTY
	 *			getY() = getY()@pre +1
	 *			getClasseType() = getClasseType()@pre
	 *		
	 *		else if Level::nature(GameEng::level(getGameEng()), getX(), getY()-1) = METAL then :
	 *			getY() = getY()@pre
	 *			getClasseType() = MARCHEUR
	 *		else
	 *			getY() = getY()@pre
	 *			getClasseType() = TOMBEUR
     *	
     *	else if (getClasseType() = GRIMPEUR) then :
     *			
	 *		if isObstacle(L,getX()+1,getY()) && isObstacle(getX()+1,getY()+1) &&
	 *			¬isObstacle(getX(), getY()+2) then :
	 *			getDirection() = getDirection()@pre
	 *			getClasseType() = getClasseType()@pre
	 *			getY() = getY()@pre +1
	 *			getX() = getX()@pre
	 *		else 
	 *			if isObstacle(getX(),getY() + 2) then :
	 *				getClasseType() = TOMBEUR
	 *				getDirection() = getDirection()@pre
	 *				getY() = getY()@pre
	 *			else if isObstacle(getX()+1,getY()) && ¬isObstacle(getX()+1,getY()+1)
	 *				getClasseType() = MARCHEUR
	 *				getY() = getY()@pre +1
	 *				getX() = getX()@pre +1
	  **/
	void step();
	
}
