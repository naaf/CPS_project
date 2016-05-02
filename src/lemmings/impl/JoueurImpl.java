package lemmings.impl;

import java.util.HashMap;

import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.JoueurService;
import lemmings.services.LemmingService;
import lemmings.services.LevelService;
import lemmings.services.Nature;
import lemmings.services.RequiredServiceGameEng;

public class JoueurImpl implements
		/* require */
		RequiredServiceGameEng,
		/* provide */
		JoueurService {

	// attribut ------------------------------------------------------------
	private int nbJetons;
	private GameEngService gameEng;
	private HashMap<ClasseType, Integer> classeTypes;
	private Nature[][] oldGrille;
	private int eX, eY, qX, qY;

	// Constructors --------------------------------------------------------
	public JoueurImpl() {
	}

	@Override
	public void init(GameEngService gameEng, int jetons) {
		this.nbJetons = jetons;
		this.classeTypes = new HashMap<>();
		for (ClasseType c : ClasseType.values()) {
			if (c != ClasseType.TOMBEUR && c != ClasseType.MARCHEUR) {
				classeTypes.put(c, jetons);
			}
		}
		bindServiceGameEng(gameEng);
	}

	@Override
	public void bindServiceGameEng(GameEngService ges) {
		gameEng = ges;
	}

	// Observators ---------------------------------------------------------
	@Override
	public GameEngService getGameEng() {
		return this.gameEng;
	}

	@Override
	public int getNbJetons() {
		return this.nbJetons;
	}

	@Override
	public int getJetons(ClasseType ct) {
		return classeTypes.get(ct);
	}

	@Override
	public HashMap<ClasseType, Integer> getClasseTypes() {
		return this.classeTypes;
	}

	// Operators -----------------------------------------------------------
	@Override
	public void assignerClasse(ClasseType cl, LemmingService l) {
		if (cl == ClasseType.EXPLOSEUR) {
			l.setExploseur(true);
		} else if (cl == ClasseType.FLOTTEUR) {
			l.setFlotteur(true);
		} else {
			l.setClasseType(cl);
		}
		classeTypes.computeIfPresent(cl, (k, v) -> v - 1);
	}

	@Override
	public void reset() {
		LevelService l = gameEng.getLevel();
		l.init(l.getWidth(), l.getHeight());
		for (int i = 0; i < l.getWidth(); i++) {
			for (int j = 0; j < l.getHeight(); j++) {
				l.setNature(i, j, oldGrille[i][j]);
			}
		}
		l.goPlay(eX, eY, qX, qY);
		gameEng.init(gameEng.getLevel(), gameEng.getSizeColony(), gameEng.getSpawnSpeed());
		for (ClasseType ct : classeTypes.keySet()) {
			classeTypes.put(ct, nbJetons);
		}
	}

	@Override
	public void startGame() {
		LevelService l = gameEng.getLevel();
		eX = l.entranceX();
		eY = l.entranceY();
		qX = l.exitX();
		qY = l.exitY();
		oldGrille = new Nature[l.getWidth()][l.getHeight()];
		for (int i = 0; i < l.getWidth(); i++) {
			for (int j = 0; j < l.getHeight(); j++) {
				oldGrille[i][j] = l.getNature(i, j);
			}
		}
	}

	@Override
	public void annihilation() {
		gameEng.stopCreation();
		gameEng.lemmings().forEach(l -> {
			assignerClasse(ClasseType.EXPLOSEUR, l);
		});
	}

}
