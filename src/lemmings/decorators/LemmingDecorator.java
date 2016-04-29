package lemmings.decorators;

import java.util.Optional;

import lemmings.services.ActivityLemming;
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

	@Override
	public ActivityLemming getClasseLemming() {
		return delegate.getClasseLemming();
	}

	// Operators -----------------------------------------------------------
	@Override
	public void setClasseLemming(ActivityLemming cl) {
		delegate.setClasseLemming(cl);
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
	public void setX(int x) {
		delegate.setX(x);
	}

	@Override
	public void setY(int y) {
		delegate.setY(y);
	}

	@Override
	public void setCumul(ActivityLemming cumul) {
		delegate.setCumul(cumul);		
	}

	@Override
	public Optional<ActivityLemming> getCumul() {
		return delegate.getCumul();
	}

	@Override
	public ClasseType getClasseType() {
		return delegate.getClasseType();
	}
	
	
	

	
}
