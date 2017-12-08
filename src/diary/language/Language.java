package diary.language;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import diary.constants.Constants;
import diary.constants.XMLIdentifier;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class Language 
{
	//Misc
	public String backText;
	public String finishText;
	
	//Main Menu
	//Button Texts
	public String mainNewEntryButtonText;
	public String mainViewGraphButtonText;
	public String mainViewTableButtonText;
	public String mainSettingsButtonText;
	public String mainExitButtonText;
	//Button tool tips
	public String mainNewEntryButtonTipsText;
	public String mainViewGraphButtonTipsText;
	public String mainViewTableButtonTipsText;
	public String mainSettingsButtonTipsText;
	//Version and Author
	public String mainAuthorCreatedByText;
	public String mainVersionText;
	
	//Entry Log
	public String entryLogTitle;
	public String entryLogStartTimeLabel;
	public String entryLogHeadpainLocationAmountLabel;
	public String entryLogActivityLabel;
	public String entryLogCommentsLabel;
	
	//Pain Position
	public String generalPositionLabel;
	public String generalPositionHeadText;
	public String generalPositionEyesText;
	public String generalPositionEarsText;
	public String generalPositionCheeksText;
	public String generalPositionChinText;
	public String selectLocationLabel;
	public String kindOfHeadpainLabel;
	public String intensityLabel;
	public String durationLabel;
	public String durationUnitsLabel;
	
	public Language()
	{
		this.setLanguage("english");
	}
	
	public Language(String language)
	{
		this.setLanguage(language);
	}
	
	//Methods
	private void setLanguage(String language)
	{
		try 
		{
			//Initialize Document
			Document xmlDoc = XMLManager.createDocument(Constants.LANGUAGE_FOLDER_PATH + language + ".xml", false);
			NodeList rootNodes = xmlDoc.getElementsByTagName(XMLIdentifier.NODE_NAME_LANGUAGE);
			Element rootElement = (Element)XMLManager.getElement(rootNodes, 0);
			
			//Getting the texts
			LinkedHashMap<String, String> textMap = this.createTextMap(rootElement);
			
			//Get data
			//Misc
			this.backText = textMap.get(XMLIdentifier.BACK_TEXT);
			this.finishText = textMap.get(XMLIdentifier.FINISH_TEXT);
			//Main
			this.mainNewEntryButtonText = textMap.get(XMLIdentifier.NEW_ENTRY_BUTTON_TEXT);
			this.mainViewGraphButtonText = textMap.get(XMLIdentifier.VIEW_GRAPH_BUTTON_TEXT);
			this.mainViewTableButtonText = textMap.get(XMLIdentifier.VIEW_TABLE_BUTTON_TEXT);
			this.mainSettingsButtonText = textMap.get((XMLIdentifier.SETTINGS_BUTTON_TEXT));
			this.mainExitButtonText = textMap.get(XMLIdentifier.EXIT_BUTTON_TEXT);
			this.mainNewEntryButtonTipsText = textMap.get(XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP);
			this.mainViewTableButtonTipsText = textMap.get(XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP);
			this.mainViewTableButtonTipsText = textMap.get(XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP);
			this.mainAuthorCreatedByText = textMap.get(XMLIdentifier.AUTHOR_TEXT);
			this.mainVersionText = textMap.get(XMLIdentifier.VERSION_TEXT);
			//Entry Log
			this.entryLogTitle = textMap.get(XMLIdentifier.ENTRY_LOG_TITLE);
			this.entryLogStartTimeLabel = textMap.get(XMLIdentifier.START_TIME_LABEL);
			this.entryLogHeadpainLocationAmountLabel = textMap.get(XMLIdentifier.HEADPAIN_LOCATION_AMOUNT_LABEL);
			this.entryLogActivityLabel = textMap.get(XMLIdentifier.ACTIVITY_LABEL);
			this.entryLogCommentsLabel = textMap.get(XMLIdentifier.COMMENTS_LABEL);
		
			//Pain Position
			this.generalPositionHeadText = textMap.get(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT);
			this.generalPositionEyesText = textMap.get(XMLIdentifier.GENERAL_POSITION_EYES_TEXT);
			this.generalPositionEarsText = textMap.get(XMLIdentifier.GENERAL_POSITION_EARS_TEXT);
			this.generalPositionCheeksText = textMap.get(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT);
			this.generalPositionChinText = textMap.get(XMLIdentifier.GENERAL_POSITION_CHIN_TEXT);
			this.selectLocationLabel = textMap.get(XMLIdentifier.SELECT_LOCATION_LABEL);
			this.kindOfHeadpainLabel = textMap.get(XMLIdentifier.KIND_OF_HEADPAIN_LABEL);
			this.intensityLabel = textMap.get(XMLIdentifier.INTENSITY_LABEL);
			this.durationLabel = textMap.get(XMLIdentifier.DURATION_LABEL);
			this.durationUnitsLabel = textMap.get(XMLIdentifier.DURATION_UNIT_LABEL);
		}
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
			MessageManager.showErrorDialog(e);
		}
	}
	
	private LinkedHashMap<String, String> createTextMap(Element rootElement)
	{
		ArrayList<Element> sectionList = XMLManager.getElements(rootElement.getElementsByTagName(XMLIdentifier.NODE_NAME_SECTION));
		ArrayList<ArrayList<Element>> textList = new ArrayList<ArrayList<Element>>();
		for (int i=0; i<sectionList.size(); i++)
		{
			textList.add(XMLManager.getElements(sectionList.get(i).getElementsByTagName(XMLIdentifier.NODE_NAME_TEXT)));
		}
		
		LinkedHashMap<String, String> textMap = new LinkedHashMap<String, String>();
		for (int i=0; i<textList.size(); i++)
		{
			for (int a=0; a<textList.get(i).size(); a++)
			{
				textMap.put(textList.get(i).get(a).getAttribute(XMLIdentifier.ATTRIBUTE_IDENTIFIER), 
						textList.get(i).get(a).getTextContent());
			}
		}
		
		return textMap;
	}
}
