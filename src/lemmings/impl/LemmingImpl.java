package lemmings.impl;

import java.util.Optional;

import lemmings.services.ActivityLemming;
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
	private ActivityLemming lemmingClasse;
	private ActivityLemming cumul;

	// Constructors -----------------------------------------------------------
	public LemmingImpl() {
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = Direction.DROITE;
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
	public ActivityLemming getClasseLemming() {
		return this.lemmingClasse;
	}

	// Operators ---------------------------------------------------------------

	@Override
	public void setClasseLemming(ActivityLemming cl) {
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
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public Optional<ActivityLemming> getCumul() {
		return Optional.ofNullable(cumul);
	}
	
	@Override
	public void setCumul(ActivityLemming cumul) {
		this.cumul = cumul;
	}

	@Override
	public ClasseType getClasseType() {
		return lemmingClasse.getTypeClasse();
	}
	
	
}