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
	 * post: setNature(x,y,n).getNature(x,y) = n
	 * post: ∀ x1 ∈ {0 ... w -1}, ∀y1 ∈ {0 ... h -1}; x != x1 || y != y1
	 * 		setNature(x,y,c).getNature(x1,y1) = getNature(x1,y1)
	 */
	void setNature(int x, int y, Nature n);
	
	/**
	 * pre: isEditing() = false
	 * pre: caseExiste(x,y)
	 * pre: getNature(x,y) = DIRTY
	 * post: isEditing() = isEditing()@pre
	 * post: getNature(x,y) = EMPTY
	 * post: ∀ x1 ∈ {0 ... w -1}, ∀y1 ∈ {0 ... h -1}; x != x1 || y != y1   
	 *		remove(x, y).getNature(x1, y1) = getNature(x1, y1)@pre
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
	 * post: ∀ x1 ∈ {0 ... w -1}, ∀y1 ∈ {0 ... h -1}; x != x1 || y != y1   
	 *		build(x, y).getNature(x1, y1) = getNature(x1, y1)@pre
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
	 * post: ∀ x1 ∈ {0 ... w -1}, ∀y1 ∈ {0 ... h -1}; x != x1 || y != y1   
	 *		goPlay(eX,eY,qX,qY).getNature(x1, y1) = getNature(x1, y1)@pre
	 */
	void goPlay(int eX, int eY, int qX, int qY);
	
}
