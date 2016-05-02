package lemmings.decorators;

import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.LemmingService;

public class ActivityDecorator implements ActivityLemming{
	private ActivityLemming delegate;
	@Override
	public void step(LemmingService lm) {
		delegate.step(lm);
	}

	@Override
	public ClasseType getTypeClasse() {
		return delegate.getTypeClasse();
	}

}
