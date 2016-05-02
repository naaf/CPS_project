package lemmings.contracts;

import java.util.List;
import java.util.stream.Collectors;

import lemmings.decorators.LemmingDecorator;
import lemmings.errors.Contractor;
import lemmings.services.ClasseType;
import lemmings.services.Direction;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.Nature;

public class LemmingContract extends LemmingDecorator {
	private static final String SERVICE = "LemmingService";

	// attribut ---------------------------------------------------------------
	public void checkInvariant() {
		if (!gameEng().getLevel().caseExiste(getX(), getY())) {
			Contractor.defaultContractor().invariantError(SERVICE, "coordonnées de lemmings out of bound");
		}
		if ((gameEng().getLevel().getNature(getX(), getY() - 1) != Nature.EMPTY)
				|| (gameEng().getLevel().getNature(getX(), getY()) != Nature.EMPTY)) {
			Contractor.defaultContractor().invariantError(SERVICE, "les cases lemming ne sont pas empty");
		}
	}

	// Constructors -----------------------------------------------------------
	public LemmingContract(LemmingService delegate) {
		super(delegate);
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		// pre
		if (ges == null) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "ges = null");
		}

		if (!ges.getLevel().caseExiste(x, y)) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "not existe case");
		}
		if (ges.getLevel().getNature(x, y) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "case lemming not empty");
		}
		if (ges.getLevel().getNature(x, y - 1) != Nature.EMPTY) {
			Contractor.defaultContractor().preconditionError(SERVICE, "init", "case tete lemming not empty");
		}

		// run
		super.init(ges, id, x, y);
		// inv post
		checkInvariant();
		// post
		if (getX() != x) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getX() != x");
		}
		if (getY() != y) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getY() != y");
		}
		if (getDirection() != Direction.DROITE) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "getDirection() != Droite");
		}
		if (!(getClasseType() == ClasseType.MARCHEUR)) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "classeType() != Marcheur");
		}
		if (gameEng() != ges) {
			Contractor.defaultContractor().postconditionError(SERVICE, "init", "gameEng() != ges");
		}

	}

	// Observators -------------------------------------------------------------
	// Operators ---------------------------------------------------------------
	@Override
	public void setClasseType(ClasseType cl) {
		// pre
		// inv pre
		checkInvariant();
		// Captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		Direction dir_at_pre = getDirection();
		// run
		super.setClasseType(cl);
		// inv post
		checkInvariant();
		// post
		if (getX() != x_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getX() != x_at_pre");
		}
		if (getY() != y_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getY() != y_at_pre");
		}
		if (getDirection() != dir_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming",
					"getDirection() != dir_at_pre");
		}
		if (getClasseType() != cl) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming", "getClasseLemming() != cl");
		}
		if (getClasseType() != cl) {
			Contractor.defaultContractor().postconditionError(SERVICE, "setClasseLemming",
					"getClasseType() != cl.getTypeClasse()");
		}
	}

	@Override
	public void changeDirection() {
		// pre
		// inv pre
		checkInvariant();
		// Captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		ClasseType ct_at_pre = getClasseType();
		Direction dir_at_pre = getDirection();
		// run
		super.changeDirection();
		// inv post
		checkInvariant();
		// post
		if (getX() != x_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection", "getX() != x_at_pre");
		}
		if (getY() != y_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection", "getY() != y_at_pre");
		}
		if (getDirection() == dir_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection",
					"changeDirection() == dir_at_pre");
		}
		if (getClasseType() != ct_at_pre) {
			Contractor.defaultContractor().postconditionError(SERVICE, "changeDirection",
					"getClasseType() != ct_at_pre");
		}

	}

	@Override
	public void step() {
		// pre
		// inv pre
		checkInvariant();
		// captures
		int x_at_pre = getX();
		int y_at_pre = getY();
		Direction dir_at_pre = getDirection();
		List<Integer> lemmings_at_pre = gameEng().lemmings().stream().map(LemmingService::getId)
				.collect(Collectors.toList());
		int enchute_at_pre = enChute();
		int attenteConstruction_at_pre = attenteConstruction();
		int nbDalle_at_pre = nbDalle();
		ClasseType ct_at_pre = getClasseType();
		int id_at_pre = getId();
		boolean estFlotteur_at_pre = estFlotteur();
		int sens;
		// run
		super.step();
		// inv post
		checkInvariant();
		// post
		switch (ct_at_pre) {
		
		case TOMBEUR:
			if (gameEng().isObstacle(x_at_pre, y_at_pre + 1)) {
				if (enChute() >= 8 && !estFlotteur_at_pre) {
					if (lemmings_at_pre.contains(id_at_pre)) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step",
								"tombeur lemming existe tjrs");
					}
				}
				if (estFlotteur() != false) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step", "tombeur tjrs flotteur");
				}
				if (getClasseType() != ClasseType.MARCHEUR) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step",
							"tombeur ne deviens pas marcheur");
				}
			} else {
				if (!estFlotteur_at_pre && enChute() != enchute_at_pre + 1) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step", "enChute ne s'incremente pas");
				}
			}
			break;
		case CREUSEUR:
			if (gameEng().isObstacle(x_at_pre, y_at_pre + 1)) {
				if (gameEng().getLevel().getNature(x_at_pre, y_at_pre + 1) != Nature.DIRTY) {
					if (getClasseType() != ClasseType.MARCHEUR) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step",
								"creuseur ne deviens pas marcheur");
					}
				} else {
					for (int i = -1; i < 2; i++) {
						if (gameEng().getLevel().getNature(x_at_pre + i, y_at_pre + 1) == Nature.DIRTY) {
							if (gameEng().getLevel().getNature(x_at_pre + i, y_at_pre + 1) != Nature.EMPTY) {
								Contractor.defaultContractor().postconditionError(SERVICE, "step",
										"creuseur DIRTY ne deviens pas EMPTY");
							}

						}
					}
					if (getY() != y_at_pre + 1) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step", "creuseur ne descend pas ");
					}
				}
			} else {
				if (getClasseType() != ClasseType.TOMBEUR) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step",
							"creuseur ne deviens pas tombeur");
				}
			}
			break;
		case CONSTRUCTEUR:
			sens = (dir_at_pre== Direction.DROITE) ? 1 : -1;
			if (gameEng().isObstacle(x_at_pre, y_at_pre + 1)) {
				for (int i = 0; i < 3; i++) {
					for (int j = i == 0 ? 1 : i; i == 0 ? j < 4 : j < 3; j++) {
						if (gameEng().isObstacle(x_at_pre + sens * j, y_at_pre - i) || nbDalle_at_pre == 3) {
							if (getClasseType() != ClasseType.MARCHEUR) {
								Contractor.defaultContractor().postconditionError(SERVICE, "step",
										"creuseur ne deviens pas tombeur");
							}
							return;
						}
					}
				}
				if (attenteConstruction_at_pre == 3) {
					for (int i = 1; i <= 3; i++) {
						if (gameEng().getLevel().getNature(x_at_pre + i * sens, y_at_pre) != Nature.DIRTY) {
							Contractor.defaultContractor().postconditionError(SERVICE, "step",
									"constructeur empty ne deviens pas Dirty");
						}
					}
					if (getY() != y_at_pre - 1) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step", "constructeur ");
					}
					if (getX() != x_at_pre + 2 * sens) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step", "constructeur ");
					}
					if (nbDalle() != nbDalle_at_pre + 3) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step", "constructeur ");
					}
					if (attenteConstruction() != 0) {
						Contractor.defaultContractor().postconditionError(SERVICE, "step", "constructeur ");
					}
				}
				if (attenteConstruction() != attenteConstruction_at_pre + 1) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step", "constructeur ");
				}
			} else {
				if (getClasseType() != ClasseType.TOMBEUR) {
					Contractor.defaultContractor().postconditionError(SERVICE, "step",
							"creuseur ne deviens pas tombeur");
				}
			}
			break;
		default:
			break;
		}

	}

}