package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.CustomControlCreator;
import de.lessvoid.nifty.controls.dynamic.LabelCreator;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;

/**
 * A small error dialog using nifty
 */
public class UIElementErrorMessageBox {
	public static void create(Nifty nifty, Element show, Element hide, String errorMessage) {		
		//Red error panel
		PanelCreator errorPanel = new PanelCreator("errorPanelID");
	  	errorPanel.setChildLayout("vertical");
	  	errorPanel.setStyle("nifty-panel-red");
	  	errorPanel.setVAlign("center");
	  	errorPanel.setAlign("center");
	  	errorPanel.setHeight("150px");
	  	errorPanel.setWidth("400px");
	  	errorPanel.setVisible("true");
	  	Element Element1 = errorPanel.create(nifty,  nifty.getCurrentScreen(), show);

	  	//Error label
	    LabelCreator errorLabel = new LabelCreator("errorLabelID","Error");
	    errorLabel.setAlign("center");
	    errorLabel.setVAlign("center");
	    errorLabel.setStyle("nifty-label");
	   
	    errorLabel.create(nifty, nifty.getCurrentScreen(), nifty.getCurrentScreen().findElementByName("errorPanelID"));
	    
	  	//Error message
	    LabelCreator errorMessageLabel = new LabelCreator("errorMessageID",errorMessage);
	    errorMessageLabel.setAlign("center");
	    errorMessageLabel.setVAlign("center");
	    errorMessageLabel.setStyle("nifty-label");
	    errorMessageLabel.create(nifty, nifty.getCurrentScreen(), nifty.getCurrentScreen().findElementByName("errorPanelID"));
	    
	    //Error button
	    CustomControlCreator errorButton = new CustomControlCreator("errorButton","button");
	    errorButton.setAlign("center");
	    errorButton.setVAlign("center");
	    errorButton.set("label", "exit");
	    errorButton.setInteractOnClick("quit()");
	    errorButton.create(nifty, nifty.getCurrentScreen(), nifty.getCurrentScreen().findElementByName("errorPanelID"));
	    
		//Hide GUI
		//hide.disable();
	    hide.startEffect(EffectEventId.onCustom);
	    /*
	    for(Element e1 : hide.getElements()) {
	    	//e1.setFocusable(false);
	    	//e1.setVisible(false);
	    	//e1.disable();
	    }
	    */
		Element1.setVisibleToMouseEvents(true);
	}
}
