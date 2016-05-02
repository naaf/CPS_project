package lemmings.decorators;

import lemmings.services.ClasseType;
import lemmings.services.Direction;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;

public class LemmingDecorator implements LemmingService {

	// attribut ------------------------------------------------------------
	private LemmingService delegate;

	// Constructors --------------------------------------------------------
	public LemmingDecorator(LemmingService delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		delegate.init(ges, id, x, y);
	}
	
	// Observators ---------------------------------------------------------
	@Override
	public GameEngService gameEng() {
		return delegate.gameEng();
	}

	@Override
	public int getId() {
		return delegate.getId();
	}

	@Override
	public int getX() {
		return delegate.getX();
	}

	@Override
	public int getY() {
		return delegate.getY();
	}

	@Override
	public Direction getDirection() {
		return delegate.getDirection();
	}



	// Operators -----------------------------------------------------------
	@Override
	public void setClasseType(ClasseType cl) {
		delegate.setClasseType(cl);
	}

	@Override
	public void changeDirection() {
		delegate.changeDirection();
	}

	@Override
	public void step() {
		delegate.step();
	}

	@Override
	public ClasseType getClasseType() {
		return delegate.getClasseType();
	}
	
	
	

	
}
