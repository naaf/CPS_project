package lemmings.impl;

import lemmings.services.LevelService;
import lemmings.services.Nature;

public class LevelImpl implements LevelService {

	// attribut ------------------------------------------------------------
	private int height;
	private int width;
	private boolean editing;
	private int exitX;
	private int exitY;
	private int entranceX;
	private int entranceY;
	private Nature[][] grille;

	// Constructors --------------------------------------------------------
	public LevelImpl() {
	}

	@Override
	public void init(int width, int height) {
		this.height = height;
		this.width = width;
		this.editing = true;
		this.grille = new Nature[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grille[x][y] = Nature.EMPTY;
			}
		}
	}

	// Observators ---------------------------------------------------------
	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public boolean isEditing() {
		return this.editing;
	}

	@Override
	public Nature getNature(int x, int y) {
		return grille[x][y];
	}

	@Override
	public boolean caseExiste(int x, int y) {
		return x < width && y < height;
	}

	@Override
	public int exitX() {
		return this.exitX;
	}

	@Override
	public int exitY() {
		return this.exitY;
	}

	@Override
	public int entranceX() {
		return this.entranceX;
	}

	@Override
	public int entranceY() {
		return this.entranceY;
	}

	// Operators -----------------------------------------------------------
	@Override
	public void setNature(int x, int y, Nature n) {
		grille[x][y] = n;
	}

	@Override
	public void remove(int x, int y) {
		grille[x][y] = Nature.EMPTY;
	}

	@Override
	public void build(int x, int y) {
		grille[x][y] = Nature.DIRTY;
	}

	@Override
	public void goPlay(int exitX, int exitY, int entX, int entY) {
		this.editing = false;
		this.entranceX = entX;
		this.entranceY = entY;
		this.exitX = exitX;
		this.exitY = exitY;
	}

}
