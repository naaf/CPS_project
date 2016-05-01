package lemmings.test;

import lemmings.contracts.GameEngContract;
import lemmings.contracts.JoueurContract;
import lemmings.contracts.LevelContract;
import lemmings.impl.GameEngImpl;
import lemmings.impl.JoueurImpl;
import lemmings.impl.LevelImpl;

public class LemmingTest {

	public static void main(String[] args) {
		LevelContract lc = new LevelContract(new LevelImpl());
		lc.init(15, 15);
		GameEngContract gc = new GameEngContract(new GameEngImpl());
		gc.init(lc, 12, 1);
		JoueurContract jc = new JoueurContract(new JoueurImpl());
		jc.init(gc, 12);
		
		while(!gc.isGameOver()){
			gc.runTour();
			System.out.println("tour " + gc.getTour());
		}
	}

}
