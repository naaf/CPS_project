package lemmings.test;

import lemmings.contracts.GameEngContract;
import lemmings.contracts.JoueurContract;
import lemmings.contracts.LevelContract;
import lemmings.impl.GameEngImpl;
import lemmings.impl.JoueurImpl;
import lemmings.impl.LevelImpl;
import lemmings.services.Nature;
import lemmings.view.JoueurView;

public class ViewTest {
	public static void main(String[] args) {
		LevelContract lc = new LevelContract(new LevelImpl());
		lc.init(JoueurView.L_WIDTH, JoueurView.L_HEIGHT);
		for (int i = 0; i < JoueurView.L_WIDTH; i++) {
			lc.setNature(i, 6, Nature.DIRTY);
			lc.setNature(i, 5, Nature.DIRTY);
			lc.setNature(i, 4, Nature.DIRTY);
		}
//		for (int i = 0; i < JoueurView.L_HEIGHT; i++) {
//			lc.setNature(7, i, Nature.DIRTY);
//			lc.setNature(14, i, Nature.DIRTY);
//		}
		for (int i = 0; i < JoueurView.L_WIDTH; i++) {
			lc.setNature(i, 0, Nature.METAL);
			lc.setNature(i, JoueurView.L_HEIGHT - 1, Nature.METAL);
		}
		for (int i = 0; i < JoueurView.L_HEIGHT; i++) {
			lc.setNature(0, i, Nature.METAL);
			lc.setNature(JoueurView.L_WIDTH - 1, i, Nature.METAL);
		}
		lc.setNature(7, 14, Nature.EMPTY);
		lc.setNature(7, 13, Nature.EMPTY);
		
		GameEngContract gc = new GameEngContract(new GameEngImpl());
		gc.init(lc, 4, 3);
		JoueurContract jc = new JoueurContract(new JoueurImpl());
		jc.init(gc, 12);
		lc.goPlay(2, 2, 13, 14);
		JoueurView jv = new JoueurView(jc);
		
		jc.startGame();
		while (!gc.isGameOver()) {
			gc.activeTour();
			System.out.println("tour " + gc.getTour());
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jv.repain();

		}
		System.out.println(gc.getNbSauves() + ",fin Partie " + gc.getScore());
	}
}