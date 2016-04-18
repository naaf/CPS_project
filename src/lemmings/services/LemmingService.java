package lemmings.services;

public interface LemmingService {
	// Observators ---------------------------------------------------------
	GameEngService gameEng();
	int getId();
	int getX();
	int getY();
	Direction getDirection();
	ClasseType getClasseType();
	int enChute();
	
	// Invariants ----------------------------------------------------------
	/**
	 * env: gameEng().level().getNature(x, y +1) = EMPTY
	 * env: gameEng().level().getNature(x, y) = EMPTY
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: gameEng().level().caseExiste(x,y)
	 * pre: gameEng().level().getNature(x, y) = EMPTY
	 * pre: gameEng()::level().getNature(x, y +1) = EMPTY
	 * post: getX() = x
	 * post: getY() = y
	 * post: getId() = id
	 * post: getDirection = DROITE
	 * post: getClasseType = MARCHEUR
	 * post: getGameEng() = gameEng
	 * post: enChute()= 0
	 */
	void init(GameEngService ges, int id, int x, int y);
	
	// Operators -----------------------------------------------------------
	/**
	 * post: setClasseLemming(t).getClasseType = t
	 * post: setClasseLemming(t).enChute() = 0
	 * post: setClasseLemming(t).getX() = getX()@pre
	 * post: setClasseLemming(t).getY() = getY()@pre
	 * setClasseLemming(t).getDirection() = getDirection()@pre
	 */
	void setClasseLemming(ClasseType t);
	
	/**
	 * post: if getDirection = GAUCHE then 
	 * 			changeDirection().getDirextion = DROITE
	 * 		else changeDirection().getDirextion = GAUCHE
	 * post: changeDirection().getX() = getX()@pre
	 * post: changeDirection().getY() = getY()@pre
	 * post: changeDirection().getClasseType() = getClasseType()@pre
	 * 			
	 */
	void changeDirection();
	
	/**
	 * if getClasseType(L) = MARCHEUR then :
	 *		enChute() = enChute()@pre
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
	 *			if enChute() >= 8 then :
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
     *		else if (getClasseType() = GRIMPEUR) then :
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
