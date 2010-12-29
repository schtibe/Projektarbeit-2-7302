package gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import simulation.Simulator;

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
import environment.Gaia;


/**
 * Game state to run the simulation
 */
public class GameStateSimRun extends BasicGameState implements ScreenController {
	public static final int ID = 3;
	private GameContainer container;
	private Nifty nifty;
	private List<MouseInputEvent> mouseEvents = new ArrayList<MouseInputEvent>();
	private int mouseX;
	private int mouseY;
	private boolean mouseDown;
	private boolean isGridEnabled = false;
	private List<Path> grid;
	private StateBasedGame game;
	private Simulator simulator;
	private final float GRIDSIZE = 50;
	private final int FONTSIZE = 12;
	private Element errorBox;
	protected boolean simulationStarted = false;
	protected Font font;
    TrueTypeFont ttf;

	public GameStateSimRun() {
		try {
			this.simulator = new Simulator();
			
			this.font =  new Font("Verdana", Font.BOLD, FONTSIZE);
			this.ttf = new TrueTypeFont(font.deriveFont(Font.BOLD, FONTSIZE), true, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.errorBox.startEffect(EffectEventId.onCustom);
		}
	}

	/**
	 * This function is called when the simulation is actually
	 * started
	 * 
	 * Generate a gaia then start the simulation
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		try {
			super.enter(container, game);
			GameCache.getInstance().setGAIA(
					UIElementFactory.getUIElement(
							Gaia.getInstance(),
							container.getWidth(), 
							container.getHeight()
					)
			);
			
			// generate the grid
			this.generateGrid();
		} catch (Exception e1) {
			e1.printStackTrace();
			this.errorBox.startEffect(EffectEventId.onCustom);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		this.container = container;
		this.game = game;

		// create nifty (gui)
		nifty = new Nifty(new RenderDeviceLwjgl(), new SoundSystem(
				new SlickSoundDevice()), new InputSystem() {
			@Override
			public List<MouseInputEvent> getMouseEvents() {
				ArrayList<MouseInputEvent> result = new ArrayList<MouseInputEvent>(
						mouseEvents);
				mouseEvents.clear();
				 return result;
			}
		}, new TimeProvider());
		nifty.fromXml("ressources/gui/simMenu.xml", "simMenu", this);

		// fetch the error box from the xml
		this.errorBox = getElement("errorLayer");
	}

	/**
	 * Renders the Simulation
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setAntiAlias(false);
		
		this.drawGrid(g);
		this.drawTrafficCarriers(g);		
		this.drawVehicles(g);
		this.drawWaypoints(g);

		//draw the gui
		SlickCallable.enterSafeBlock();
		nifty.render(false);
		SlickCallable.leaveSafeBlock();
	}

	/**
	 * Draws the vehicles
	 * 
	 * @param g
	 */
	private void drawVehicles(Graphics g) {
		for (IUIAdapterVehicle<?> vehicle : GameCache.getInstance()
				.getGAIA().getVehicles()) {

			vehicle.draw(g);
			/*
			g.setColor(vehicle.getColor());
			g.fill(vehicle.getBoundingBox());
			g.raw(vehicle.getBoundingBox());
			*/
		}
	}

	/**
	 * Draws the traffic carriers
	 * 
	 * @param g
	 */
	private void drawTrafficCarriers(Graphics g) { 
		for (IUIAdapterTrafficCarrier<?> road : GameCache.getInstance()
				.getGAIA().getRoads()) {
			for (IUIAdapterLane<?> lane : road.getLanes()) {
				g.setColor(lane.getColor());
				g.draw(lane.getPath());
			}
		}
	}

	/**
	 * Draw the Grid if wanted
	 * 
	 * @param g
	 */
	private void drawGrid(Graphics g) {
		g.setLineWidth(1);
		if (isGridEnabled) {
			for (Path tmpPath : grid) {
				g.draw(tmpPath);
			}
		}
	}

	/**
	 * Draw the way points
	 * 
	 * @param g
	 */
	private void drawWaypoints(Graphics g) {
		for(IUIAdapterWayPoint<?> wayPoint : GameCache.getInstance().getGAIA().getWaypoints()) {
			if (wayPoint.doDraw()) {
				g.setColor(wayPoint.getColor());
				g.draw(wayPoint.getShape());
				
				wayPoint.drawString(ttf);
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		try {
			if (!this.simulationStarted) {
				this.simulator.startSimulation(Calendar.getInstance().getTimeInMillis(), 1);
				this.simulationStarted = true;
			}
			this.simulator.update(Calendar.getInstance().getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
			this.errorBox.startEffect(EffectEventId.onCustom);
			// added to see stack traces immediately after error!!
			container.exit();
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_F) {
			this.container.setShowFPS(!this.container.isShowingFPS());
		}
	}

	private Element getElement(final String id) {
		return nifty.getCurrentScreen().findElementByName(id);
	}

	@Override
	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	@Override
	public void mousePressed(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = true;

		for (IUIAdapterTrafficCarrier<?> road : GameCache.getInstance()
				.getGAIA().getRoads()) {
			for (IUIAdapterLane<?> lane : road.getLanes()) {
				if (lane.getPath()
						.intersects(new Ellipse(mouseX, mouseY, 2, 2))) {
					try {
						GameCache.getInstance().getGAIA().addVehicle(lane);
					} catch (Exception e) {
					//System.out.println("error");
						e.printStackTrace();
					}
				}
			}
		}


		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	@Override
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

	@Override
	public void bind(Nifty nifty, Screen screen) { }

	@Override
	public void onEndScreen() { }

	@Override
	public void onStartScreen() { }

	public void restartSimulation() {
		this.game.enterState(3);
	}

	public void exit() {
		GameCache.getInstance().getGAIA().destroy(); // destroying the gaia
		// nifty.getCurrentScreen().endScreen(new EndNotify() {
		// public void perform() {
		game.enterState(0);
		// }
		// });
	}

	public void showGrid() {
		this.isGridEnabled = !this.isGridEnabled;
	}

	public void showFPS() {
		this.container.setShowFPS(!this.container.isShowingFPS());
	}
	
	/**
	 * Toggle whether the driver views should be displayed
	 */
	public void toggleDriverView() {
		System.out.println("Toggling driver view");
		GUIConstants.getInstance().toggleShowDriverView();
	}
	
	private void generateGrid() {
		this.grid = new ArrayList<Path>();
		for (float x = 0; x <= container.getScreenWidth(); x += GRIDSIZE) {
			Path tmpPath = new Path(x, 0);
			tmpPath.lineTo(x, container.getHeight());
			this.grid.add(tmpPath);
		}
		for (float y = 0; y <= container.getScreenHeight(); y += GRIDSIZE) {
			Path tmpPath = new Path(0, y);
			tmpPath.lineTo(container.getWidth(), y);
			this.grid.add(tmpPath);
		}
	}
}
