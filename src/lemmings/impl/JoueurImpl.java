package lemmings.impl;

import java.util.HashMap;

import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.JoueurService;
import lemmings.services.LemmingService;
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
	
	// Constructors --------------------------------------------------------
	public JoueurImpl() {
	}

	@Override
	public void init(GameEngService gameEng, int jetons) {
		this.nbJetons = jetons;
		this.classeTypes =  new HashMap<>();
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
	public void assignerClasse(ClasseType ct, LemmingService l) {
		l.setClasseLemming(ct);
		classeTypes.computeIfPresent(ct, (k, v) -> v - 1);
	}

	@Override
	public void reset() {
		gameEng.init(gameEng.getLevel(), gameEng.getSizeColony(), gameEng.getSpawnSpeed());
		for(ClasseType ct : classeTypes.keySet()){
			classeTypes.put(ct, nbJetons);
		}
	}

}
