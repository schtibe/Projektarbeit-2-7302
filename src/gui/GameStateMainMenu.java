package gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectEventId;
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
 * A game state to represent the main menu
 */
public class GameStateMainMenu extends BasicGameState implements
		ScreenController {
	public static final int ID = 0;
	private GameContainer container;
	private Nifty nifty;
	private List<MouseInputEvent> mouseEvents = new ArrayList<MouseInputEvent>();
	private int mouseX;
	private int mouseY;
	private boolean mouseDown;
	private boolean showFPS = false;
	private StateBasedGame game;
	private Element errorBox;

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
		nifty.fromXml("ressources/gui/mainMenu.xml", "mainMenu", this);

		// fetch the error box from the xml
		this.errorBox = getElement("errorLayer");

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		// draw the gui
		SlickCallable.enterSafeBlock();
		nifty.render(false);
		SlickCallable.leaveSafeBlock();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
	}

	public void keyReleased(int key, char c) {
		// toggle fps
		if (key == Input.KEY_F) {
			this.showFPS = !this.showFPS;
		}
	}

	private Element getElement(final String id) {
		return nifty.getCurrentScreen().findElementByName(id);
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
		nifty.getCurrentScreen().endScreen(new EndNotify() {
			public void perform() {
				container.exit();
			}
		});
	}

	public void startSimulation() throws SlickException {
		// nifty.getCurrentScreen().endScreen(new EndNotify() {
		// public void perform() {
		game.enterState(3);
		// }
		// });
	}

	public void startChangeResolution() {
		this.errorBox.startEffect(EffectEventId.onCustom);
		UIElementErrorMessageBox.create(nifty, getElement("errorLayer"),
				getElement("mainPanel"), "help!");
	}

	public void startChangeMap() {
		// nifty.getCurrentScreen().endScreen(new EndNotify() {

		// public void perform() {
		game.enterState(2);
		// }
		// });
	}
}
