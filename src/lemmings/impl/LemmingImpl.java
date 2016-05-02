package lemmings.impl;

import java.util.Optional;

import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.Direction;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.Nature;
import lemmings.services.RequiredServiceGameEng;

public class LemmingImpl implements
		/* require */
		RequiredServiceGameEng,
		/* provide */
		LemmingService {

	// attribut ---------------------------------------------------------------
	private static final int LIMITE_CHUTE = 8;
	private static final int ATTENTE_CONSTRUCTION = 3;
	private static final int ATTENTE_EXPLOSION = 5;
	private static final int LIMITE_DALLE = 12;
	private static final int LIMITE_CASSE = 20;
	private GameEngService gEng;
	private int id;
	private int x;
	private int y;
	private int enchute;
	private Direction direction;
	private ClasseType classeType;
	private boolean estFlotteur;
	private boolean estExploseur;
	private int attenteConstruction;
	private int attenteExplosion;
	private int nbDalle;
	private int nbCasse;
	private boolean diagB;

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

	// Operators ---------------------------------------------------------------

	@Override
	public void setClasseType(ClasseType classeType) {
		this.classeType = classeType;
		enchute = 0;
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
		switch(classeType){
		case MARCHEUR: marcheur();break;
		case TOMBEUR: tombeur(); break;
		case GRIMPEUR: grimpeur();break;
		case CONSTRUCTEUR:constructeur();break;
		case CREUSEUR : creuseur();break;
		case PELLEUR : pelleteur(); break;
		case MINEUR : mineur(); break;
		default:
			break;
		}
		
		if(estExploseur)
			exploseur();

	}

	
	@Override
	public ClasseType getClasseType() {
		return this.classeType;
	}

	private void marcheur() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			if (gameEng().lemmings().stream().anyMatch(l -> (l.getX() == getX() + sens) && l.getY() == getY()
					&& l.getClasseType() == ClasseType.STOPPEUR)) {
				changeDirection();
			} else if (gameEng().isObstacle(getX() + sens, getY() - 1) || (gameEng().isObstacle(getX() + sens, getY())
					&& gameEng().isObstacle(getX() + sens, getY() - 2))) {
				changeDirection();
			} else {
				if (gameEng().isObstacle(getX() + sens, getY())) {
					y = getY() - 1;
				}
				x = getX() + sens;
			}
		} else {
			setClasseType(ClasseType.TOMBEUR);
		}
	}

	private void tombeur() {
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			if (enchute >= LIMITE_CHUTE && !estFlotteur) {
				gameEng().supprimeLemming(getId());
			}
			setClasseType(ClasseType.MARCHEUR);
		} else {
			if (estFlotteur) {
				y = getY() + (enchute % 2 == 0 ? 1 : 0);
			} else {
				y = getY() + 1;
			}
			enchute++;
		}
	}

	private void grimpeur() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (!gameEng().isObstacle(getX(), getY() + 1)) {
			setClasseType(ClasseType.TOMBEUR);
		}
		if (!gameEng().isObstacle(getX(), getY() - 1) && gameEng().isObstacle(getX() + sens, getY())) {
			if (!gameEng().isObstacle(getX(), getY() - 2) && gameEng().isObstacle(getX() + sens, getY() - 1)) {
				y = y - 1;
			} else if (!gameEng().isObstacle(getX() + sens, getY() - 1)
					&& !gameEng().isObstacle(getX() + sens, getY() - 2)) {
				y = getY() - 1;
				x = getX() + sens;
			} else {
				setClasseType(ClasseType.MARCHEUR);
			}
		} else {
			setClasseType(ClasseType.MARCHEUR);
		}

	}

	private void creuseur() {
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			if (gameEng().getLevel().getNature(getX(), getY() + 1) != Nature.DIRTY) {
				setClasseType(ClasseType.MARCHEUR);
			} else {
				for (int i = -1; i < 2; i++) {
					if (gameEng().getLevel().getNature(getX() + i, getY() + 1) == Nature.DIRTY) {
						gameEng().getLevel().remove(getX() + i, getY() + 1);
					}
				}
				y = y +1;
			}
		} else {
			setClasseType(ClasseType.TOMBEUR);
		}
	}

	private void constructeur() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			for (int i = 0; i < 3; i++) {
				for (int j = i == 0 ? 1 : i; i == 0 ? j < 4 : j < 3; j++) {
					if (gameEng().isObstacle(getX() + sens * j, getY() - i) || nbDalle == LIMITE_DALLE) {
						setClasseType(ClasseType.MARCHEUR);
						return;
					}
				}
			}
			if (attenteConstruction == ATTENTE_CONSTRUCTION) {

				if (gameEng().lemmings().stream().anyMatch(l -> {
					for (int i = 1; i <= 3; i++) {
						if (l.getX() == getX() + i * sens && (l.getY() == getY() || l.getY() - 1 == getY()))
							return true;
					}
					return false;
				})) {
					return;
				}

				for (int i = 1; i <= 3; i++) {
					gameEng().getLevel().build(getX() + i * sens, getY());
				}
				y = y - 1;
				x = x + 2 *sens;
				nbDalle += 3;
				attenteConstruction = 0;
			}
			attenteConstruction++;
		} else {
			setClasseType(ClasseType.TOMBEUR);
		}
	}

	private void exploseur() {
		if (attenteExplosion == ATTENTE_EXPLOSION) {
			for (int i = -1; i < 2; i++) {
				for (int j = -2; j < 3; j++) {
					if (gameEng().getLevel().caseExiste(x + j, y + i)
							&& gameEng().getLevel().getNature(x + j, y + i) != Nature.EMPTY
							&& gameEng().getLevel().getNature(x + j, y + i) != Nature.METAL) {
						gameEng().getLevel().remove(x + j, y + i);
					}
				}
				gameEng().lemmings().stream()
						.filter(l -> x - 2 <= l.getX() && l.getX() <= x + 2 && y - 1 <= l.getY() && l.getY() <= y + 1)
						.forEach(l -> gameEng().supprimeLemming(l.getId()));
			}
		}
		attenteExplosion++;
	}

	private void pelleteur() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			for (int i = 0; i > -3; i--) {
				if (nbCasse >= LIMITE_CASSE
						|| gameEng().getLevel().getNature(getX() + sens, getY() + i) != Nature.DIRTY) {
					setClasseType(ClasseType.MARCHEUR);
					return;
				}
			}
			for (int i = 0; i > -3; i--) {
				gameEng().getLevel().remove(getX() + sens, getY() + i);
			}
			x = x +sens;
			nbCasse += 3;

		} else {
			setClasseType(ClasseType.TOMBEUR);
		}
	}

	private boolean diagH() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (diagB)
			return false;
		if (!gameEng().isObstacle(getX() + sens, getY())
				|| gameEng().getLevel().getNature(getX() + sens, getY() - 1) == Nature.METAL
				|| gameEng().getLevel().getNature(getX() + sens, getY() - 2) == Nature.METAL) {
			return false;
		}
		if (gameEng().lemmings().stream()
				.anyMatch(l -> (l.getX() == getX() + sens) && (l.getY() == getY() - 1 || l.getY() == getY() - 2)
						&& l.getClasseType() == ClasseType.STOPPEUR)) {
			return false;
		}
		return true;
	}

	private boolean diagB() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (gameEng().getLevel().getNature(getX() + sens, getY()) == Nature.METAL
				|| gameEng().getLevel().getNature(getX() + sens, getY() + 1) == Nature.METAL) {
			return false;
		}
		if (gameEng().lemmings().stream().anyMatch(l -> (l.getX() == getX() + sens) && l.getY() == getY()
				&& l.getClasseType() == ClasseType.STOPPEUR)) {
			return false;
		}
		return true;
	}

	private void mineur() {
		int sens = (getDirection() == Direction.DROITE) ? 1 : -1;
		if (gameEng().isObstacle(getX(), getY() + 1)) {
			if (diagH()) {
				if (gameEng().isObstacle(getX() + sens, getY() - 1)) {
					gameEng().getLevel().remove(getX() + sens, getY() - 1);
				}
				if (gameEng().isObstacle(getX() + sens, getY() - 2)) {
					gameEng().getLevel().remove(getX() + sens, getY() - 2);
				}
				x+=sens;
				y--;
			} else if (diagB()) {
				if (gameEng().isObstacle(getX() + sens, getY())) {
					gameEng().getLevel().remove(getX() + sens, getY());
				}
				if (gameEng().isObstacle(getX() + sens, getY() + 1)) {
					gameEng().getLevel().remove(getX() + sens, getY() + 1);
				}
				x+=sens;
				y++;
				this.diagB = true;

			} else {
				setClasseType(ClasseType.MARCHEUR);
			}
		} else {
			setClasseType(ClasseType.TOMBEUR);
		}

	}

}