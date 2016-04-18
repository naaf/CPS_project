package lemmings.services;

public interface LevelService {

	// Observators ---------------------------------------------------------
	int getHeight();
	int getWidth();
	boolean isEditing();
	Nature getNature(int x, int y);
	// pre: caseExiste(x,y)
	boolean caseExiste(int x,int y);
	int exitX();
	// pre: isEditing() = false
	int exitY();
	// pre: isEditing() = false
	int entranceX();
	// pre: isEditing() = false
	int entranceY();
	// pre: isEditing() = false
	
	// Invariants ----------------------------------------------------------
	/**
	 * inv: getHeight() >= 5
	 * inv: getWidth() >= 4
	 * inv: caseExiste(x,y) min= 0 <= x < getWidth() && 0 <= y < getHeight()
	 */
	
	
	// Constructors --------------------------------------------------------
	/**
	 * pre: width > 4 && height > 5
	 * post: getWidth() = width
	 * post: getHeigth() = height
	 * post: isEditing() = true
	 * post: ∀i ∈ {0 ... getWidth()-1},  ∀j ∈ {0 ... getHeigth()-1}
	 * 		getNature(i,j) = EMPTY
	 */
	void init(int width, int height);
	
	// Operators -----------------------------------------------------------
	/**
	 * 
	 * pre: isEditing()
	 * pre: caseExiste(x,y) 
	 * post: si x1=x2 && y1=y2 
	 * 		setNature(x1,y1,c).getNature(x2,y2) = c
	 * post: si x1 != x2 || y1 != y2 
	 * 		setNature(x1,y1,c).getNature(x2,y2) = getNature(x2,y2)
	 */
	void setNature(int x, int y, Nature n);
	
	/**
	 * pre: isEditing() = false
	 * pre: caseExiste(x,y)
	 * pre: getNature(x,y) = DIRTY
	 * post: isEditing() = isEditing()@pre
	 * post: getNature(x,y) = EMPTY
	 */
	void remove(int x, int y);
	
	/**
	 * pre: isEditing() = false
	 * pre: caseExiste(x,y)
	 * pre: getNature(x,y) = EMPTY
	 * pre: ¬(x = entranceX() && (y = entranceY() || y = entranceY() -1 && y = entranceY() +1))
	 * pre: ¬(x = exitX() && (y = exitY() || y = exitY() +1)) 
	 * post: isEditing() = isEditing()@pre
	 * post: getNature(x,y) = DIRTY
	 */
	void build(int x, int y);
	
	/**
	 * pre: isEditing() = true
	 * pre: ∀ i ∈ {0, getWidth()-1}; j ∈ {0 ... getHeight()-1}; getNature(i,j) = METAL
	 * pre: ∀ i ∈ {0 ... getWidth()-1}; j ∈ {0, getHeight()-1}; getNature(i,j) = METAL
	 * pre: getNature(eX,eY -1) = getNature(eX,eY) = getNature(eX,eY+1) = EMPTY  
	 * pre: getNature(qX,qY -1) = METAL && getNature(qX,qY) = EMPTY && getNature(qX,qY+1) = EMPTY 
	 * post: isEditing() = false 
	 * post: entranceX() = eX 
	 * post: entranceY() = eY 
	 * post: exitX() = qX 
	 * post: exitY() = qY 
	 */
	void goPlay(int eX, int eY, int qX, int qY);
	
}
