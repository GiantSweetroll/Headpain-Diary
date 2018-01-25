package diary.methods;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.constants.XMLIdentifier;
import giantsweetroll.message.MessageManager;
import giantsweetroll.xml.dom.XMLManager;

public class XMLGenerator 
{
	private static final String XML_PATH = "." + File.separator + "data" + File.separator + "settings" + File.separator + "language" + File.separator + "english.xml";
	
	public static final void generateXML()
	{
		try 
		{	
			Document doc = XMLManager.createDocument();
			Element rootElement = doc.createElement(XMLIdentifier.NODE_NAME_LANGUAGE);
			doc.appendChild(rootElement);
		
			//add childs
			LinkedHashMap<String, Element> map = XMLGenerator.createElementMap(doc);
			
			for (Map.Entry<String, Element> entry : map.entrySet())
			{
				rootElement.appendChild(entry.getValue());
			}
			
			//Export
			XMLManager.exportXML(doc, new File(XMLGenerator.XML_PATH), 5);
		} 
		catch (ParserConfigurationException | TransformerException e) 
		{
			e.printStackTrace();
			MessageManager.showErrorDialog(e);
		}
	}
	
	private static LinkedHashMap<String, Element> createElementMap(Document doc)
	{
		LinkedHashMap<String, Element> map = new LinkedHashMap<String, Element>();
		
		//Main Menu
		appendComponentToMap(doc, map, XMLIdentifier.NEW_ENTRY_BUTTON_TEXT, "New Entry");
		appendComponentToMap(doc, map, XMLIdentifier.VIEW_GRAPH_BUTTON_TEXT, "View Graph");
		appendComponentToMap(doc, map, XMLIdentifier.VIEW_TABLE_BUTTON_TEXT, "View Table");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_BUTTON_TEXT, "Settings");
		appendComponentToMap(doc, map, XMLIdentifier.EXIT_BUTTON_TEXT, "Exit");
		appendComponentToMap(doc, map, XMLIdentifier.NEW_ENTRY_BUTTON_TOOLTIP, "Log new pain entry");
		appendComponentToMap(doc, map, XMLIdentifier.VIEW_GRAPH_BUTTON_TOOLTIP, "View pain history graph");
		appendComponentToMap(doc, map, XMLIdentifier.VIEW_TABLE_BUTTON_TOOLTIP, "View pain history table");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_BUTTON_TOOLTIP, "Configure program settings");
		appendComponentToMap(doc, map, XMLIdentifier.AUTHOR_TEXT, "Created by");
		appendComponentToMap(doc, map, XMLIdentifier.VERSION_TEXT, "Version");
		
		//Entry Log
		appendComponentToMap(doc, map, XMLIdentifier.ENTRY_LOG_TITLE, "Entry Log");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_LABEL, "Date");
		appendComponentToMap(doc, map, XMLIdentifier.START_TIME_LABEL, "Start Time");
		appendComponentToMap(doc, map, XMLIdentifier.HEADPAIN_LOCATION_AMOUNT_LABEL, "Amount of Headpain Location(s)");
		appendComponentToMap(doc, map, XMLIdentifier.ACTIVITY_LABEL, "Activity");
		appendComponentToMap(doc, map, XMLIdentifier.COMMENTS_LABEL, "Comments");
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_LABEL, "General Position");
		appendComponentToMap(doc, map, XMLIdentifier.SELECT_LOCATION_LABEL, "Select Location");
		appendComponentToMap(doc, map, XMLIdentifier.KIND_OF_HEADPAIN_LABEL, "Kind of Headpain");
		appendComponentToMap(doc, map, XMLIdentifier.INTENSITY_LABEL, "Intensity");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_LABEL, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_LABEL, "Seconds");
		
		//Date
		appendComponentToMap(doc, map, XMLIdentifier.DATE_AUTO_BUTTON_TOOLTIP_TEXT, "Automatically set the date to that of local time");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_RESET_BUTTON_TOOLTIP_TEXT, "Resets date changes to it's orginal status");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_FROM_LABEL, "From");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_TO_LABEL, "To");
		//Month Names
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_JANUARY, "January");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_FEBRUARY, "February");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_MARCH, "March");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_APRIL, "April");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_MAY, "May");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_JUNE, "June");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_JULY, "July");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_AUGUST, "August");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_SEPTEMBER, "September");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_OCTOBER, "October");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_NOVEMBER, "November");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_MONTH_DECEMBER, "December");
		
		//Time
		appendComponentToMap(doc, map, XMLIdentifier.TIME_AUTO_BUTTON_TOOLTIP_TEXT, "Automatically set the time to that of local time");
		appendComponentToMap(doc, map, XMLIdentifier.TIME_RESET_BUTTON_TOOLTIP_TEXT, "Resets time changes to it's orginal values");
		
		//Pain Location
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_HEAD_TEXT, "Head");
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT, "Cheeks");
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_EARS_TEXT, "Ears");
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_EYES_TEXT, "Eyes");
		//Head
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_HEAD_BACK_TEXT, "Back Side");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_HEAD_FRONT_TEXT, "Front Side");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_HEAD_LEFT_TEXT, "Left Side");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_HEAD_RIGHT_TEXT, "Right Side");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_LEFT_TEXT, "Bottom Left");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_RIGHT_TEXT, "Bottom Right");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_CENTER_TEXT, "Center");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_LEFT_TEXT, "Top Left");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_RIGHT_TEXT, "Top Right");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT, "Back Side");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT, "Center");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT, "Frontal Side");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_CENTER_TEXT, "Center");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_LEFT_TEXT, "Left Side");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_RIGHT_TEXT, "Front Side");
		//Cheeks
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_CHEEKS_LEFT_TEXT, "Left");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_CHEEKS_RIGHT_TEXT, "Right");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT, "Lower Side");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT, "Upper Side");
		//Ears
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_EARS_LEFT_TEXT, "Left");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_EARS_RIGHT_TEXT, "Right");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT, "Hole");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT, "Lower Leaf Half");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT, "Upper Leaf Half");
		//Eyes
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_EYES_LEFT_TEXT, "Left");
		appendComponentToMap(doc, map, XMLIdentifier.SPECIFIC_POSITION_EYES_RIGHT_TEXT, "Right");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT, "Behind Eyeballs");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT, "Eyeball");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT, "Eyebrow");
		appendComponentToMap(doc, map, XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT, "Socket");
		
		//Misc
		appendComponentToMap(doc, map, XMLIdentifier.BACK_TEXT, "Back");
		appendComponentToMap(doc, map, XMLIdentifier.FINISH_TEXT, "Finish");
		appendComponentToMap(doc, map, XMLIdentifier.RESET_TEXT, "Reset");
		appendComponentToMap(doc, map, XMLIdentifier.CURRENT_TEXT, "Current");
		appendComponentToMap(doc, map, XMLIdentifier.AUTO_TEXT, "Auto");
		appendComponentToMap(doc, map, XMLIdentifier.CONFIRM_TEXT, "Confirm");
		appendComponentToMap(doc, map, XMLIdentifier.REFRESH_TEXT, "Refresh");
		appendComponentToMap(doc, map, XMLIdentifier.DELETE_TEXT, "Delete");
		appendComponentToMap(doc, map, XMLIdentifier.SELECT_TEXT, "Select");
		appendComponentToMap(doc, map, XMLIdentifier.NOTE_TEXT, "Note");
		appendComponentToMap(doc, map, XMLIdentifier.FILTER_TEXT, "Filter");
		appendComponentToMap(doc, map, XMLIdentifier.YES_TEXT, "Yes");
		appendComponentToMap(doc, map, XMLIdentifier.NO_TEXT, "No");
		appendComponentToMap(doc, map, XMLIdentifier.CANCEL_TEXT, "Cancel");
		appendComponentToMap(doc, map, XMLIdentifier.BROWSE_TEXT, "Browse");
		appendComponentToMap(doc, map, XMLIdentifier.SAVE_TEXT, "Save");
		
		//Graph
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_LABEL, "Category");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT, "Pain vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT, "Entries vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_TIME_TEXT, "Average Intensity vs Time");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_TIME_TEXT, "Average Duration vs Time");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_DATE, "Kinds of Pains vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE, "Pain Locations vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_ACTIVITY_VS_DATE, "Activity vs Date");
		//Graph Settings
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_SETTINGS_ENABLE_DATA_VALUES, "Show Data Values");
		
		//Table
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_BY_LABEL, "Filter By");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_KEYWORD_LABEL, "Keyword");
		//Table Filters
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT, "Pain Amount");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT, "Comments");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT, "Intensity");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_ACTIVITY_TEXT, "Activity");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT, "Pain Kind");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT, "Pain Position");
		//Table Usage Guide
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_GUIDE_USAGE_TEXT, "Empty fields means all data (no filter), otherwise it will search the table for the exact text");
		//Table Button Tooltips
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_BACK_TEXT, "Go back to main menu");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_DELETE_TEXT, "Delete the selected entries (cannot be undone)");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_SELECT_TEXT, "Open the Entry Log of the selected entry (only one at a time)");
		//Table Headers
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_ACTIVITY_TEXT, "Activity");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_COMMENTS_TEXT, "Comments");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_DATE_TEXT, "Date");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_DURATIONS_TEXT, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_INTENSITIES_TEXT, "Intensities");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_AMOUNT_TEXT, "Amount of Headpain Location(s)");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_KINDS_TEXT, "Kinds of Headpain");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_POSITIONS_TEXT, "Pain Positions");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_SELECT_TEXT, "Select");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_START_TIME_TEXT, "Start Time");
		
		//Program Settings
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_TITLE, "Window");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_TEXT, "Window Mode");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_FULLSCREEN_TEXT, "Fullscreen");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_WINDOWED_TEXT, "Windowed");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_TITLE, "Database");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_PATH_TEXT, "Database Location");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_PATH_SELECT_TEXT, "Select Database Location");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_PROGRAM_TITLE, "Program");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_LANGUAGE_TEXT, "Language");
		
		//Error Handling
		appendComponentToMap(doc, map, XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TEXT, "Please fill all required fields (marked with *)");
		appendComponentToMap(doc, map, XMLIdentifier.ERROR_REQUIRED_FIELDS_DIALOG_TITLE, "Fill in all required fields");
		
		//Message Dialogs
		appendComponentToMap(doc, map, XMLIdentifier.MESSAGE_DELETE_CONFIRM_TEXT, "Are you sure you want to delete the following entries?");
		appendComponentToMap(doc, map, XMLIdentifier.MESSAGE_DELETE_CONFIRM_TITLE, "Confirm delete?");
		appendComponentToMap(doc, map, XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TEXT, "Another entry of the same start time already exists. Overwrite?");
		appendComponentToMap(doc, map, XMLIdentifier.MESSAGE_OVERWRITE_CONFIRM_TITLE, "Overwrite?");
		
		return map;
	}
	private static void appendComponentToMap(Document doc, LinkedHashMap<String, Element> map, String key, String text)	//Create the element and then add to the element map
	{
		map.put(key, doc.createElement(XMLIdentifier.NODE_NAME_TEXT));
		map.get(key).setAttribute(XMLIdentifier.ATTRIBUTE_IDENTIFIER, key);
		map.get(key).appendChild(doc.createTextNode(text));
	}
	
	public static void main(String args[]) throws ParserConfigurationException
	{
		//MessageManager.printLine(XML_PATH);
		XMLGenerator.generateXML();
		MessageManager.showDialog("DONE!");
		
	}
}
