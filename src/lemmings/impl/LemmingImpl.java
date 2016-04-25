package lemmings.impl;

import java.util.Optional;

import lemmings.services.ActivityIF;
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
	private ClasseType classeType;
	private ActivityIF lemmingClasse;
	private ActivityIF cumul;

	// Constructors -----------------------------------------------------------
	public LemmingImpl() {
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = Direction.DROITE;
		this.classeType = ClasseType.MARCHEUR;
		this.lemmingClasse = new LemmingMarcheur();
		bindServiceGameEng(ges);
	}

	@Override
	public void bindServiceGameEng(GameEngService ges) {
		this.gEng = ges;
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
	public ActivityIF getClasseType() {
		return this.lemmingClasse;
	}

	// Operators ---------------------------------------------------------------

	@Override
	public void setClasseLemming(ActivityIF cl) {
		this.lemmingClasse = cl;
	}

	@Override
	public void changeDirection() {
		direction = (direction == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
	}

	@Override
	public void step() {
		if (x == gEng.getLevel().exitX() && y == gEng.getLevel().exitY()) {
			gEng.saveLemming(id);
			return;
		}
		lemmingClasse.step(this);
		if(cumul != null){
			cumul.step(this);
		}

	}

	@Override
	public String toString() {
		return "LemmingImpl [gEng=" + gEng + ", id=" + id + ", x=" + x + ", y=" + y + ", direction=" + direction
				+ ", classeType=" + classeType + "]";
	}


	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public Optional<ActivityIF> getCumul() {
		return Optional.ofNullable(cumul);
	}
	
	@Override
	public void setCumul(ActivityIF cumul) {
		this.cumul = cumul;
	}
	
	
}