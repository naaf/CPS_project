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

	@Override
	public int enChute() {
		return delegate.enChute();
	}

	@Override
	public boolean estFlotteur() {
		return delegate.estFlotteur();
	}

	@Override
	public boolean estExploseur() {
		return delegate.estExploseur();
	}

	@Override
	public boolean estGrimpeur() {
		return delegate.estGrimpeur();
	}

	@Override
	public int attenteConstruction() {
		return delegate.attenteConstruction();
	}

	@Override
	public int attenteExplosion() {
		return delegate.attenteExplosion();
	}

	@Override
	public int nbDalle() {
		return delegate.nbDalle();
	}

	@Override
	public int nbCasse() {
		return delegate.nbCasse();
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

	@Override
	public void setFlotteur(boolean flotteur) {
		delegate.setFlotteur(flotteur);
	}

	@Override
	public void setExploseur(boolean ex) {
		delegate.setExploseur(ex);
	}

	@Override
	public void setEstGrimpeur(boolean estGrimpeur) {
		delegate.setEstGrimpeur(estGrimpeur);
	}

}
