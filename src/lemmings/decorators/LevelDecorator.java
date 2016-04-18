package lemmings.decorators;

import lemmings.services.LevelService;
import lemmings.services.Nature;

public class LevelDecorator implements LevelService {
	// attribut ------------------------------------------------------------
	private LevelService delegate;

	// Constructors --------------------------------------------------------
	@Override
	public void init(int width, int height) {
		delegate.init(width, height);
	}
	public LevelDecorator(LevelService delegate) {
		super();
		this.delegate = delegate;
	}

	// Observators ---------------------------------------------------------
	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public boolean isEditing() {
		return delegate.isEditing();
	}

	@Override
	public Nature getNature(int x, int y) {
		return delegate.getNature(x, y);
	}

	@Override
	public boolean caseExiste(int x, int y) {
		return delegate.caseExiste(x, y);
	}

	@Override
	public int exitX() {
		return delegate.exitX();
	}

	@Override
	public int exitY() {
		return delegate.exitY();
	}

	@Override
	public int entranceX() {
		return delegate.entranceX();
	}

	@Override
	public int entranceY() {
		return delegate.entranceY();
	}


	// Operators -----------------------------------------------------------
	@Override
	public void setNature(int x, int y, Nature n) {
		delegate.setNature(x, y, n);
	}

	@Override
	public void remove(int x, int y) {
		delegate.remove(x, y);		
	}

	@Override
	public void build(int x, int y) {
		delegate.build(x, y);		
	}

	@Override
	public void goPlay(int eX, int eY, int qX, int qY) {
		delegate.goPlay(eX, eY, qX, qY);		
	}


}
