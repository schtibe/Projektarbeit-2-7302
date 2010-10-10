package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import common.GlobalConstants;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dropdown.controller.DropDownControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.mouse.MouseInputEvent;
import de.lessvoid.nifty.lwjglslick.render.RenderDeviceLwjgl;
import de.lessvoid.nifty.lwjglslick.sound.SlickSoundDevice;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.sound.SoundSystem;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.TimeProvider;

/**
 * Game state to change the map
 */
public class GameStateChangeMap extends BasicGameState implements
		ScreenController {
	public static final int ID = 2;
	private GameContainer container;
	private Nifty nifty;
	private List<MouseInputEvent> mouseEvents = new ArrayList<MouseInputEvent>();
	private int mouseX;
	private int mouseY;
	private boolean mouseDown;
	private boolean startSimulation = false;
	private boolean showFPS = false;
	private DropDownControl mapDropDownControl;
	private StateBasedGame game;

	/**
	 * {@inheritDoc}
	 */
	public int getID() {
		return ID;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		this.game = game;

		// create nifty (gui)
		nifty = new Nifty(new RenderDeviceLwjgl(), new SoundSystem(
				new SlickSoundDevice()), new InputSystem() {
			public List<MouseInputEvent> getMouseEvents() {
				ArrayList<MouseInputEvent> result = new ArrayList<MouseInputEvent>(
						mouseEvents);
				mouseEvents.clear();
				return result;
			}
		}, new TimeProvider());

		this.generateGUI();
	}

	/*
	 * generates the GUI out of the xml file
	 */
	private void generateGUI() {

		try {
			// generate the gui
			nifty.fromXml("ressources/gui/mapMenu.xml", "changeMap", this);

			// populate the drop down
			mapDropDownControl = nifty.getCurrentScreen().findControl(
					"dropDownControl", DropDownControl.class);

			String path = GlobalConstants.getInstance().getMapRoot();
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int ix = 0; ix < listOfFiles.length; ix++) {

				if (listOfFiles[ix].isFile()) {
					mapDropDownControl.addItem(listOfFiles[ix].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		SlickCallable.enterSafeBlock();
		nifty.render(false);
		SlickCallable.leaveSafeBlock();
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (this.startSimulation) {
			game.enterState(1);
		}
		container.setShowFPS(this.showFPS);
	}

	public void keyReleased(int key, char c) {

		if (key == Input.KEY_F) {
			if (this.showFPS) {
				this.showFPS = false;
			} else {
				this.showFPS = true;
			}
		}

	}

	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		mouseX = newx;
		mouseY = newy;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	public void mousePressed(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = true;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	public void mouseReleased(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = false;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	private void forwardMouseEventToNifty(final int mouseX, final int mouseY,
			final boolean mouseDown) {
		mouseEvents.add(new MouseInputEvent(mouseX, container.getHeight()
				- mouseY, mouseDown));
	}

	public void bind(Nifty nifty, Screen screen) {
	}

	public void onEndScreen() {
	}

	public void onStartScreen() {
	}

	public void quit() {

		game.enterState(0);

	}

	public void apply() {
		GlobalConstants.getInstance().setStreetXMLSchema(
				GlobalConstants.getInstance().getMapRoot() + "/"
						+ mapDropDownControl.getSelectedItem());
		this.quit();
	}

	public void startSimulation() {
		this.startSimulation = true;
	}
}
