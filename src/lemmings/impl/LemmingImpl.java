package lemmings.impl;

import lemmings.services.ClasseType;
import lemmings.services.Direction;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.RequiredServiceGameEng;

public class LemmingImpl implements
		/* require */
		RequiredServiceGameEng,
		/* provide */
		LemmingService {

	// attribut ---------------------------------------------------------------
	private GameEngService gEng;
	private int id;
	private int x;
	private int y;
	private Direction direction;
	private int enchute;
	private ClasseType classeType;

	// Constructors -----------------------------------------------------------
	public LemmingImpl() {
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		bindServiceGameEng(ges);
	}

	@Override
	public void bindServiceGameEng(GameEngService ges) {
		gEng = ges;
	}
	// Observators -------------------------------------------------------------

	@Override
	public GameEngService gameEng() {
		return this.gEng;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public ClasseType getClasseType() {
		return this.classeType;
	}

	@Override
	public int enChute() {
		return this.enchute;
	}

	// Operators ---------------------------------------------------------------

	@Override
	public void setClasseLemming(ClasseType t) {
		this.classeType = t;
	}

	@Override
	public void changeDirection() {
		direction = (direction == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

}