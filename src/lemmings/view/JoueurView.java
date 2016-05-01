package lemmings.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lemmings.contracts.JoueurContract;
import lemmings.impl.LemmingConstructeur;
import lemmings.impl.LemmingCreuseur;
import lemmings.impl.LemmingExploseur;
import lemmings.impl.LemmingFlotteur;
import lemmings.impl.LemmingGrimpeur;
import lemmings.impl.LemmingMarcheur;
import lemmings.impl.LemmingMineur;
import lemmings.impl.LemmingPelleur;
import lemmings.impl.LemmingStoppeur;
import lemmings.impl.LemmingTombeur;
import lemmings.services.ActivityLemming;
import lemmings.services.ClasseType;
import lemmings.services.GameEngService;
import lemmings.services.LemmingService;
import lemmings.services.LevelService;
import lemmings.services.Nature;

public class JoueurView {
	public final static int CASE = 32;
	public final static int L_WIDTH = 36;
	public final static int WIDTH = CASE * L_WIDTH;
	public final static int L_HEIGHT = 16;
	public final static int HEIGHT = CASE * L_HEIGHT + 96;
	private final String LEMMING_IMG = "robot";
	private final String ENTRANCE_IMG = "ENTRANCE";
	private final String EXIT_IMG = "EXIT";

	private JFrame envFrame;
	private JPanel envLevel;
	private JPanel envButton;
	private JoueurContract jc;
	private GameEngService gc;
	private LevelService lc;
	private Optional<ClasseType> currentSelected;
	private Map<ClasseType, JButton> btns;
	private Map<String, BufferedImage> activeSprites;

	public JoueurView(JoueurContract jcnt) {
		jc = jcnt;
		gc = jc.getGameEng();
		lc = gc.getLevel();
		this.currentSelected = Optional.ofNullable(null);
		btns = new HashMap<>();
		activeSprites = new HashMap<String, BufferedImage>();

		envFrame = new JFrame("Lemming");
		envFrame.setSize(WIDTH, HEIGHT);

		Container contentPane = envFrame.getContentPane();
		Box vbox = Box.createVerticalBox();
		envLevel = new EnvPanel();
		Box vbtn = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		Box hbox1 = Box.createHorizontalBox();

		envButton = new JPanel();
		loadSprite();
		/** env Button **/
		envLevel.setPreferredSize(new Dimension(WIDTH, L_HEIGHT * CASE));
		envButton.setPreferredSize(new Dimension(WIDTH, CASE * 2));
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(evt -> jc.reset());
		hbox.add(btnReset);
		btnReset = new JButton("Annihilation");
		btnReset.addActionListener(evt -> {
			jc.annihilation();
		});
		hbox.add(btnReset);
		jc.getClasseTypes().entrySet().forEach(e -> {
			JButton btn = new JButton(e.getKey().toString() + " " + e.getValue());
			btns.put(e.getKey(), btn);
			hbox.add(btn);

			btn.addActionListener(evt -> {
				currentSelected = Optional.of(e.getKey());
				btn.setBackground(Color.GRAY);
				btns.values().stream().filter(b -> b != btn).forEach(b -> b.setBackground(null));
			});
		});

		/** env Level **/
		envLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					LemmingService lm = gc.lemmings().stream()
							.filter(l -> l.getX() == e.getX() / CASE && l.getY() == e.getY() / CASE).findFirst().get();
					System.out.println("trouveLemming");
					currentSelected.ifPresent(ct -> {
						if (ct == ClasseType.EXPLOSEUR || ct == ClasseType.FLOTTEUR) {
							jc.assignerCumul(getActivity(ct), lm);
						} else {
							jc.assignerClasse(getActivity(ct), lm);
						}
						btns.get(ct).setText(ct.toString() + " " + jc.getJetons(ct));

					});

				} catch (NoSuchElementException e1) {
					System.out.println("noLemming");
				}
				System.out.println("case (" + e.getX() / CASE + ", " + e.getY() / CASE + ")");
			}
		});

		envLevel.setBackground(Color.WHITE);

		/** bind env on window **/

		vbtn.add(hbox);
		vbtn.add(hbox1);
		envButton.add(vbtn);
		vbox.add(envLevel);
		vbox.add(envButton);
		contentPane.add(vbox);
		envFrame.setResizable(false);
		envFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		envFrame.setVisible(true);
	}

	private ActivityLemming getActivity(ClasseType ct) {
		switch (ct) {
		case TOMBEUR:
			return new LemmingTombeur();
		case MARCHEUR:
			return new LemmingMarcheur();
		case CREUSEUR:
			return new LemmingCreuseur();
		case GRIMPEUR:
			return new LemmingGrimpeur();
		case CONSTRUCTEUR:
			return new LemmingConstructeur();
		case STOPPEUR:
			return new LemmingStoppeur();
		case EXPLOSEUR:
			return new LemmingExploseur();
		case FLOTTEUR:
			return new LemmingFlotteur();
		case MINEUR:
			return new LemmingMineur();
		case PELLEUR:
			return new LemmingPelleur();

		}
		return null;

	}

	public void repain() {
		envLevel.repaint();
	}

	private void loadSprite() {
		BufferedImage img = null;
		String nat = null;
		try {
			for (Nature n : Nature.values()) {
				nat = n.toString();
				img = ImageIO.read(new File("assets/" + nat + ".png"));
				activeSprites.put(nat, img);
			}
			nat = LEMMING_IMG;
			img = ImageIO.read(new File("assets/" + nat + ".png"));
			activeSprites.put(nat, img);
			nat = ENTRANCE_IMG;
			img = ImageIO.read(new File("assets/" + nat + ".png"));
			activeSprites.put(nat, img);
			nat = EXIT_IMG;
			img = ImageIO.read(new File("assets/" + nat + ".png"));
			activeSprites.put(nat, img);

		} catch (IOException e) {
			throw new Error("Cannot load image file: " + nat + ".png");
		}

	}

	class EnvPanel extends JPanel {
		private static final long serialVersionUID = -3764464748815416179L;

		EnvPanel() {
			super(true);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			setBackground(Color.WHITE);
			for (int i = 0; i < L_WIDTH; i++) {
				for (int j = 0; j < L_HEIGHT; j++) {
					if (gc.isObstacle(i, j)) {
						g2d.drawImage(activeSprites.get(lc.getNature(i, j).toString()), i * CASE, j * CASE,
								i * CASE + CASE, j * CASE + CASE, 0, 0, CASE, CASE, null);
					}
				}
			}
			int x = lc.entranceX(), y = lc.entranceY();
			g2d.drawImage(activeSprites.get(ENTRANCE_IMG), x * CASE, y * CASE, x * CASE + CASE, y * CASE + CASE, 0, 0,
					CASE, CASE, null);

			x = lc.exitX();
			y = lc.exitY();
			g2d.drawImage(activeSprites.get(EXIT_IMG), x * CASE, y * CASE, x * CASE + CASE, y * CASE + CASE, 0, 0, CASE,
					CASE, null);

			gc.lemmings().forEach(l -> {
				int xe = l.getX(), ye = l.getY();
				g2d.drawImage(activeSprites.get(LEMMING_IMG), xe * CASE, ye * CASE, xe * CASE + CASE, ye * CASE + CASE,
						0, 0, CASE, CASE, null);
			});
		}

	}

}
