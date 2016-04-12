package lemmings.services;

public interface LevelService {

	// Observators ---------------------------------------------------------
	int getHeight();
	int getWidth();
	boolean isEditing();
	Nature getNature(int x, int y);
	boolean caseExiste(int x,int y);
	boolean onExit(int x, int y);
	// \pre: isEditing() = false
	int[] entrance();
	// \pre: isEditing() = false
	
	// Invariants ----------------------------------------------------------
	/**
	 * \inv: getHeight() > 5
	 * \inv: getWidth() > 4
	 * \inv: caseExiste(x,y) min= 0 <= x < getWidth() && 0 <= y < getHeight()
	 */
	
	// Constructors --------------------------------------------------------
	/**
	 * \pre: width > 4 && height > 5
	 * \post: getWidth() = width
	 * \post: getHeigth() = height
	 * \post: isEditing() = true
	 * \post: ∀i ∈ {0 ... getWidth()-1},  ∀j ∈ {0 ... getHeigth()-1}
	 * 		getNature(i,j) = EMPTY
	 */
	void init(int width, int height);
	
	// Operators -----------------------------------------------------------
	/**
	 * 
	 * \pre: caseExiste(x,y) && isEditing()
	 * \post: si x1=x2 && y1=y2 
	 * 		setNature(x1,y1,c)::getNature(x2,y2) = c
	 * \post: si x1 != x2 || y1 != y2 
	 * 		setNature(x1,y1,c)::getNature(x2,y2) = getNature(x2,y2)
	 */
	void setNature(int x, int y, Nature n);
	
	/**
	 * \pre: isEditing() = false
	 * \pre: caseExiste(x,y)
	 * \pre: getNature(x,y) = DIRTY
	 * \post: isEditing() = isEditing()@pre
	 * \post: getNature(x,y) = EMPTY
	 */
	void remove(int x, int y);
	
	/**
	 * \pre: isEditing() = false
	 * \pre: caseExiste(x,y)
	 * \pre: getNature(x,y) = EMPTY
	 * \post: isEditing() = isEditing()@pre
	 * \post: getNature(x,y) = DIRTY
	 */
	void build(int x, int y);
	
	/**
	 * \pre: isEditing() = true
	 * \pre: ∀ i ∈ {0, getWidth()-1}; j ∈ {0 ... getHeight()-1}; getNature(i,j) = METAL
	 * \pre: ∀ i ∈ {0 ... getWidth()-1}; j ∈ {0, getHeight()-1}; getNature(i,j) = METAL
	 * \pre: ∃ y1,y2,y3 consécutifs ∈ {1 ... height(L)-1}, ∃x ∈ {1 ... width(L)-1}
	 *		getNature(x,y1) = getNature(x,y2) = getNature(x,y3) = EMPTY
	 * \pre: ∃ k1,k2,k3 consécutifs ∈ {1 ... height(L)-1}, ∃m ∈ {1 ... width(L)-1}
	 *		getNature(m,k1) = getNature(m,k2) = EMPTY && getNature(m,k3) = METAL
	 * \post: isEditing() = false 
	 * \post: onExit(m,k2) = true && 
	 * 	∀ i ∈ {0 ... getWidth()-1}/{m}; j ∈ {0, getHeight()-1}/{k}; onExit(i,j) = false
	 * \post: entrance() = <x,y2>
	 */
	void goPlay();
	
}
