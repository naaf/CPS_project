package lemmings.contracts;

import java.util.HashMap;

import lemmings.decorators.JoueurDecorator;
import lemmings.errors.Contractor;
import lemmings.services.ActivityIF;
import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.JoueurService;
import lemmings.services.LemmingService;

public class JoueurContract extends JoueurDecorator {
	private static final String SERVICE = "JoueurService";

	// attribut ---------------------------------------------------------------
	public void checkInvariant() {
		if (getNbJetons() <= 0) {
			Contractor.defaultContractor().invariantError(SERVICE, "getNbJetons() <= 0");
		}
		if (getClasseTypes().size() == 0) {
			Contractor.defaultContractor().invariantError(SERVICE, "getClasseTypes().size() == 0");
		}
	}

	// Constructors -----------------------------------------------------------
	public JoueurContract(JoueurService delegate) {
		super(delegate);
	}

	@Override
	public void init(GameEngService gameEng, int jetons) {
		// pre
		if (gameEng == null) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "gemaEng = null");
		}
		if (jetons <= 0) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "jetons <= 0");
		}
		// run
		super.init(gameEng, jetons);
		// inv post
		checkInvariant();
		// post
		if (getGameEng() != gameEng) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "gameEng != getGameEng()");
		}
		if (getNbJetons() != jetons) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getNbJetons() != jetons");
		}
		getClasseTypes().entrySet().stream().filter(e -> e.getValue() != jetons)
				.forEach(e -> Contractor.defaultContractor().postconditionError(SERVICE, "init",
						e.getKey() + "," + e.getValue() + "!= " + jetons));
	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------
	@Override
	public void assignerClasse(ActivityIF ct, LemmingService l) {
		// pre
		if (getJetons(ct.getTypeClasse()) <= 0) {
			Contractor.defaultContractor().preconditionError(SERVICE, "assignerClasse", "getJetons(ct) <= 0");
		}
		if (!getGameEng().lemmingExiste(l.getId())) {
			Contractor.defaultContractor().preconditionError(SERVICE, "assignerClasse", "lemming not existe");
		}
		// inv pre
		checkInvariant();
		// Captures
		HashMap<ClasseType, Integer> cts_at_pre = new HashMap<>();
		cts_at_pre.putAll(getClasseTypes());
		int jtnUse_at_pre = getJetons(ct.getTypeClasse());
		// run
		super.assignerClasse(ct, l);
		// inv post
		checkInvariant();
		// post
		if (getJetons(ct.getTypeClasse()) != jtnUse_at_pre - 1) {
			Contractor.defaultContractor().postconditionError(SERVICE, "assignerClasse",
					"getJetons(ct) != jtnUse_at_pre - 1");
		}
		cts_at_pre.entrySet().stream().filter(e -> e.getKey() != ct.getTypeClasse() && e.getValue() != getClasseTypes().get(e.getKey()))
				.forEach(e -> Contractor.defaultContractor().postconditionError(SERVICE, "assignerClasse",
						e.getKey() + "," + e.getValue() + "!= " + getClasseTypes().get(e.getKey())));
	}

	@Override
	public void reset() {
		// pre
		// inv pre
		checkInvariant();
		// run
		super.reset();
		// inv post
		checkInvariant();
		// post
		getClasseTypes().entrySet().stream().filter(e -> e.getValue() != getNbJetons())
				.forEach(e -> Contractor.defaultContractor().postconditionError(SERVICE, "reset",
						e.getKey() + "," + e.getValue() + "!= " + getNbJetons()));
	}

}
