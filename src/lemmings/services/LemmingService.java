package lemmings.services;

public interface LemmingService {
	// Observators ---------------------------------------------------------
	GameEngService gameEng();
	int getX();
	int getY();
	int getId();
	Direction getDirection();
	ClasseType getClasseType();
	int enChute();
	boolean estFlotteur();
	boolean estExploseur();
	boolean estGrimpeur();
	int attenteConstruction();
	int attenteExplosion();
	int nbDalle();
	int nbCasse();

	
	// Invariants ----------------------------------------------------------
	/**
	 * env: gameEng().level().caseExiste(getX(), getY())
	 * env: gameEng().level().getNature(x, y +1) = EMPTY
	 * env: gameEng().level().getNature(x, y) = EMPTY
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: ges != null
	 * pre: ges.level().caseExiste(x,y)
	 * pre: ges.level().getNature(x, y) = EMPTY
	 * pre: gameEng()::level().getNature(x, y +1) = EMPTY
	 * post: getGameEng() = gameEng
	 * post: getX() = x
	 * post: getY() = y
	 * post: getDirection = DROITE
	 * post: getClasseType = MARCHEUR
	 * post: estFlotteur() = false
	 * post: estExploseur() = false
	 * post: estGrimpeur() = false
	 * post: enChute() = 0
	 * post: attenteConstruction() = 0
	 * post: attenteExplosion()  = 0
	 * post: nbDalle() = 0
	 * post: nbCasse() = 0
	 */
	void init(GameEngService ges,int id, int x, int y);
	
	// Operators -----------------------------------------------------------
	
	
	
	/**
	 * post: getX() = getX()@pre
	 * post: getY() = getY()@pre
	 * post: getDirection() = getDirection()@pre
	 * post: getClasseType() = classeType
	 * post: estFlotteur() = estFlotteur()@pre
	 * post: estExploseur() = estExploseur()@pre
	 * post: estGrimpeur() = estGrimpeur()@pre 
	 * post: enChute() = 0
	 * post: attenteConstruction() = 0
	 * post: attenteExplosion()  = attenteExplosion()@pre 
	 * post: nbDalle() = 0
	 * post: nbCasse() = 0
	 */
	void setClasseType(ClasseType classeType);
	
	/**
	 * post: getX() = getX()@pre
	 * post: getY() = getY()@pre
	 * post: if getDirection = GAUCHE then 
	 * 			changeDirection().getDirextion = DROITE
	 * 		 else changeDirection().getDirextion = GAUCHE
	 * post: getClasseType() = getClasseType()@pre
	 * post: estFlotteur() = estFlotteur()@pre
	 * post: estExploseur() = estExploseur()@pre
	 * post: estGrimpeur() = estGrimpeur()@pre 
	 * post: enChute() = enChute()@pre
	 * post: attenteConstruction() = attenteConstruction()@pre
	 * post: attenteExplosion()  = attenteExplosion()@pre
	 * post: nbDalle() = nbDalle()@pre
	 * post: nbCasse() = nbCasse()@pre
	 */
	void changeDirection();
	
	/**
	 * post: getX() = getX()@pre
	 * post: getY() = getY()@pre
	 * post: getDirection() = getDirection()@pre
	 * post: getClasseType() = getClasseType()@pre
	 * post: estFlotteur() = flotteur
	 * post: estExploseur() = estExploseur()@pre
	 * post: estGrimpeur() = estGrimpeur()@pre 
	 * post: enChute() = enChute()@pre
	 * post: attenteConstruction() = attenteConstruction()@pre
	 * post: attenteExplosion()  = attenteExplosion()@pre
	 * post: nbDalle() = nbDalle()@pre
	 * post: nbCasse() = nbCasse()@pre
	 */
	public void setFlotteur(boolean flotteur) ;
	
	/**
	 * post: getX() = getX()@pre
	 * post: getY() = getY()@pre
	 * post: getDirection() = getDirection()@pre
	 * post: getClasseType() = getClasseType()@pre
	 * post: estFlotteur() = estFlotteur()@pre
	 * post: estExploseur() = estExploseur()@pre
	 * post: estGrimpeur() = estGrimpeur
	 * post: enChute() = enChute()@pre
	 * post: attenteConstruction() = attenteConstruction()@pre
	 * post: attenteExplosion()  = attenteExplosion()@pre
	 * post: nbDalle() = nbDalle()@pre
	 * post: nbCasse() = nbCasse()@pre
	 */
	public void setEstGrimpeur(boolean estGrimpeur);
	
	/**
	 * post: getX() = getX()@pre
	 * post: getY() = getY()@pre
	 * post: getDirection() = getDirection()@pre
	 * post: getClasseType() = getClasseType()@pre
	 * post: estFlotteur() = estFlotteur()@pre
	 * post: estExploseur() = ex
	 * post: estGrimpeur() = estGrimpeur()@pre
	 * post: enChute() = enChute()@pre
	 * post: attenteConstruction() = attenteConstruction()@pre
	 * post: attenteExplosion()  = 0
	 * post: nbDalle() = nbDalle()@pre
	 * post: nbCasse() = nbCasse()@pre
	 */
	void setExploseur(boolean ex);
	
	/**
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
	 *		 estFlotteur() = estFlotteur()@pre
	 * 		 estExploseur() = estExploseur()@pre
	 * 		 estGrimpeur() = estGrimpeur()@pre
	 * 		 attenteConstruction() = attenteConstruction()@pre
	 * 		 attenteExplosion()  = attenteExplosion()@pre
	 * 		 nbDalle() = nbDalle()@pre
	 * 		 nbCasse() = nbCasse()@pre
	 *			
	 *			
	 *	else if getClasseType() = TOMBEUR then :
	 *		getX() = getX()@pre
	 *		getDirection() = getDirection()@pre
	 *		if(isObstaclegetX(), getY()-1)) then :
	 *			estFlotteur() = false
	 *			getY() = getY()@pre
	 *			getClasseType() = MARCHEUR
	 *			if enChute >= 8 then :
	 *				L ∉ GameEng::lemmings(gameEng(L))
	 *		else
	 *			getY() = getY()@pre -1
	 *			getClasseType() = getClasseType()@pre
	 *			enChute() = enChute()@pre +1
	 * 		 	estFlotteur() = estFlotteur()@pre
	 * 		 estExploseur() = estExploseur()@pre
	 * 		 estGrimpeur() = estGrimpeur()@pre
	 * 		 attenteConstruction() = attenteConstruction()@pre
	 * 		 attenteExplosion()  = attenteExplosion()@pre
	 * 		 nbDalle() = nbDalle()@pre
	 * 		 nbCasse() = nbCasse()@pre
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
	 *		 enChute() = enChute()@pre
	 *		 estFlotteur() = estFlotteur()@pre
	 * 		 estExploseur() = estExploseur()@pre
	 * 		 estGrimpeur() = estGrimpeur()@pre
	 * 		 attenteConstruction() = attenteConstruction()@pre
	 * 		 attenteExplosion()  = attenteExplosion()@pre
	 * 		 nbDalle() = nbDalle()@pre
	 * 		 nbCasse() = nbCasse()@pre
     *	
     *	else if (estGrimpeur()) then :
	 *		if isObstacle(L,getX()+1,getY()) && isObstacle(getX()+1,getY()-1) &&
	 *			¬isObstacle(getX(), getY()+2) then :
	 *			getDirection() = getDirection()@pre
	 *			getClasseType() = getClasseType()@pre
	 *			getY() = getY()@pre -1
	 *			getX() = getX()@pre
	 *		else 
	 *			if isObstacle(getX(),getY() + 2) then :
	 *				getClasseType() = TOMBEUR
	 *				getDirection() = getDirection()@pre
	 *				getY() = getY()@pre
	 *			else if isObstacle(getX()+1,getY()) && ¬isObstacle(getX()+1,getY()-1)
	 *				getClasseType() = MARCHEUR
	 *				getY() = getY()@pre -1
	 *				getX() = getX()@pre +1
	 *		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		attenteExplosion()  = attenteExplosion()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		nbCasse() = nbCasse()@pre
	 * 
	 * else if(getClasseType() = CONSTRUCTEUR) then :
	 * 
	 * else if(getClasseType() = STOPPEUR) then :
	 *		getDirection() = getDirection()@pre	
	 *		if ¬obstacle( getX(), getY()-1)  then :
 	 *			getClasseType() = TOMBEUR
	 *		else
	 *			getClasseType() = STOPPEUR
	 *		y(L) = y(L)@pre
	 *		x(L) = x(L)@pre
	 *		direction(L) = direction(L)@pre
	 *		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		attenteExplosion()  = attenteExplosion()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		nbCasse() = nbCasse()@pre
	 * 
	 * else if(estFlotteur()) then :
	 * 		getClasseType() = getClasseType()@pre
	 *		getDirection() = getDirection(L)@pre
	 *		getY() = getY()@pre
	 *		getX() = getX()@pre
	 *		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		attenteExplosion()  = attenteExplosion()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		nbCasse() = nbCasse()@pre
	 * 
	 * else if(estExploseur()) then :
	 *		if  attenteExplosion = 5 then
	 *			L ∉ gameEng().lemmings()
	 *			∀(x,y) x ∈ { getX()-2, ..., getX()+2} et y ∈ { getY()-1, ..., getY()+1}
	 *				if gameEng().getLevel().getNature( x, y) != METAL
	 *					gameEng().getLevel().getNature(  x, y) = EMPTY
	 *				else
	 *					gameEng().getLevel().getNature(  x, y) = METAL
	 *			∀ l ∈ gameEng().lemmings()
	 *				if l.getX() = x && l.getY() = y then
	 *					l ∉ gameEng().lemmings()
	 *				else
	 *					l ∈ gameEng().lemmings()
	 *		 else
	 * 			attenteExplosion()  = attenteExplosion()@pre +1
	 * 		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		nbCasse() = nbCasse()@pre
	 * 
	 * else if(getClasseType() = PELLETEUR) then :
	 * 		getCumul() = getCumul()@pre
	 *		if isObstacle(getX(), getYy()+1)  then :		
	 *			if gameEng().getLevel().getNature(getX()+1, getY()) = DIRTY &&
	 *			   gameEng().getLevel().getNature(getX()+1, getY() +1) = DIRTY && 
	 *			   gameEng().getLevel().getNature(getX()+1, getY() +2) = DIRTY &&
	 *				nbCasse() < 12 then :
	 *					getClasseType() = PELLETEUR
	 *					gameEng().getLevel().getNature(getX()+1, getY()) = EMPTY 
	 *			  		gameEng().getLevel().getNature(getX()+1, getY()+1) = EMPTY 
	 *			   		gameEng().getLevel().getNature(getX()+1, getY()+2) = EMPTY 
	 *					nbCasse() = nbCasse()@pre +3
	 *			else
	 *				getClasseType() = MARCHEUR
	 *				nbCasse() = 0
	 *		else
	 *			getClasseType() = TOMBEUR	
	 *		attenteExplosion()  = attenteExplosion()@pre
	 * 		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		
	 * 
	 * else if(getClasseType() = MINEUR) then :
	 * 		if ¬isObstacle(getX(), getY() -1) then :
	 * 			if gameEng().getLevel().getNature(getX() +1, getY() +1) != METAL && 
	 * 			   gameEng().getLevel().getNature(getX() +1, getY() +2) != METAL then :
	 * 					getClasseType() = MINEUR
	 * 					gameEng().getLevel().getNature(getX() +1, getY() +1) = EMPTY
	 * 					gameEng().getLevel().getNature(getX() +1, getY() +2) = EMPTY
	 * 					getX() = getX() +1
	 * 					getY() = getY() +1
	 * 			else
	 * 				if gameEng().getLevel().getNature(getX() +1, getY()) != METAL &&
	 * 					gameEng().getLevel().getNature(getX() +1, getY() -1) != METAL then :
	 * 						getClasseType() = MINEUR
	 * 						gameEng().getLevel().getNature(getX() +1, getY()) = EMPTY
	 * 						gameEng().getLevel().getNature(getX() +1, getY() -1) = EMPTY
	 * 						getX() = getX() +1
	 * 						getY() = getY() -1
	 *				else
	 *					getClasseType() = MARCHEUR
	 *		else
	 *			getClasseType() = TOMBEUR
	 *		attenteExplosion()  = attenteExplosion()@pre
	 * 		enChute() = enChute()@pre
	 *		estFlotteur() = estFlotteur()@pre
	 * 		estExploseur() = estExploseur()@pre
	 * 		estGrimpeur() = estGrimpeur()@pre
	 * 		attenteConstruction() = attenteConstruction()@pre
	 * 		nbDalle() = nbDalle()@pre
	 * 		nbCasse() = nbCasse()@pre
	 * 	
	  **/
	void step();
	
}
