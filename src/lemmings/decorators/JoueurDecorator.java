package lemmings.decorators;

import java.util.HashMap;

import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.JoueurService;
import lemmings.services.LemmingService;

public class JoueurDecorator implements JoueurService {
	// attribut ------------------------------------------------------------
	private JoueurService delegate;

	// Constructors --------------------------------------------------------
	@Override
	public void init(GameEngService gameEng, int jetons) {
		delegate.init(gameEng, jetons);
	}
	
	public JoueurDecorator(JoueurService delegate) {
		super();
		this.delegate = delegate;
	}

	// Observators ---------------------------------------------------------
	@Override
	public GameEngService getGameEng() {
		return delegate.getGameEng();
	}

	@Override
	public int getNbJetons() {
		return delegate.getNbJetons();
	}

	@Override
	public int getJetons(ClasseType ct) {
		return delegate.getJetons(ct);
	}

	@Override
	public HashMap<ClasseType, Integer> getClasseTypes() {
		return delegate.getClasseTypes();
	}

	// Operators -----------------------------------------------------------
	@Override
	public void assignerClasse(ActivityLemming ct, LemmingService l) {
		delegate.assignerClasse(ct, l);
	}

	@Override
	public void reset() {
		delegate.reset();
	}

	@Override
	public void startGame() {
		delegate.startGame();
	}

	@Override
	public void annihilation() {
		delegate.annihilation();
	}

	@Override
	public void assignerCumul(ActivityLemming activity, LemmingService lm) {
		delegate.assignerCumul(activity, lm);
		
	}

}
