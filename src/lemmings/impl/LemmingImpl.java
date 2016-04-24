package lemmings.impl;

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

	private static final int LIMITE_CHUTE = 8;
	// attribut ---------------------------------------------------------------
	private GameEngService gEng;
	private int id;
	private int x;
	private int y;
	private Direction direction;
	private int enchute;
	private ClasseType classeType;

	// Constructors -----------------------------------------------------------
	public LemmingImpl() {
	}

	@Override
	public void init(GameEngService ges, int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = Direction.DROITE;
		this.enchute = 0;
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

	@Override
	public ClasseType getClasseType() {
		return this.classeType;
	}

	@Override
	public int enChute() {
		return this.enchute;
	}

	// Operators ---------------------------------------------------------------

	@Override
	public void setClasseLemming(ClasseType t) {
		this.classeType = t;
		enchute = 0;
	}

	@Override
	public void changeDirection() {
		direction = (direction == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
	}

	@Override
	public void step() {
		if(x == gEng.getLevel().exitX() && y == gEng.getLevel().exitY()){
			gEng.saveLemming(id);
			return;
		}
		switch (classeType) {
		case TOMBEUR:
			tombeur();
			break;
		case MARCHEUR:
			marcheur();
			break;
		case CREUSEUR:
			creusuer();
			break;
		case GRIMPEUR:
			grimpeur();
			break;
		default:
			break;
		}

	}

	private void marcheur() {
		int sens = (direction == Direction.DROITE) ? 1 : -1;
		if (gEng.isObstacle(x, y + 1)) {
			if (gEng.isObstacle(x + sens, y - 1)
					|| (gEng.isObstacle(x + sens, y) && gEng.isObstacle(x + sens, y + (2 * sens)))) {
				changeDirection();
			} else {
				if (gEng.isObstacle(x + sens, y)) {
					y++;
				}
				x = x + sens;
			}
		} else {
			setClasseLemming(ClasseType.TOMBEUR);
		}
	}

	private void tombeur() {
		if (gEng.isObstacle(x, y + 1)) {
			if (enchute >= LIMITE_CHUTE) {
				gEng.supprimeLemming(id);
			}
			setClasseLemming(ClasseType.MARCHEUR);
		} else {
			y++;
			enchute++;
		}
	}

	private void creusuer() {
		if (gEng.isObstacle(x, y + 1)) {
			if (gEng.getLevel().getNature(x, y + 1) == Nature.METAL) {
				setClasseLemming(ClasseType.MARCHEUR);
			} else {
				for (int i = -1; i < 2; i++) {
					if (gEng.getLevel().getNature(x + i, y + 1) == Nature.DIRTY) {
						gEng.getLevel().remove(x + i, y + 1);
					}
				}
				y++;
			}
		} else {
			setClasseLemming(ClasseType.TOMBEUR);
		}
	}

	private void grimpeur() {
		int sens = (direction == Direction.DROITE) ? 1 : -1;
		if (!gEng.isObstacle(x, y - 1) && gEng.isObstacle(x + sens, y)) {
			if (!gEng.isObstacle(x, y - 2) && gEng.isObstacle(x + sens, y - 1)) {
				y--;
			} else if (!gEng.isObstacle(x + sens, y - 1) && !gEng.isObstacle(x + sens, y - 2)) {
				y--;
				x = x + sens;
			} else {
				setClasseLemming(ClasseType.MARCHEUR);
			}
		} else {
			setClasseLemming(ClasseType.MARCHEUR);
		}
	}

	@Override
	public String toString() {
		return "LemmingImpl [gEng=" + gEng + ", id=" + id + ", x=" + x + ", y=" + y + ", direction=" + direction
				+ ", enchute=" + enchute + ", classeType=" + classeType + "]";
	}

}