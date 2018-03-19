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
	private static final String XML_PATH ="data" + File.separator + "settings" + File.separator + "language" + File.separator + "english.xml";
	
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
			File file = new File(XMLGenerator.XML_PATH);
			if (!file.exists())
			{
				file.getParentFile().mkdirs();
			}
			XMLManager.exportXML(doc, file, 5);
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
		appendComponentToMap(doc, map, XMLIdentifier.MANAGE_PATIENTS_BUTTON_TEXT, "Manage Patients");
		appendComponentToMap(doc, map, XMLIdentifier.MANAGE_PATIENTS_BUTTON_TOOLTIP, "Manage patients data");
		appendComponentToMap(doc, map, XMLIdentifier.AUTHOR_TEXT, "Created by");
		appendComponentToMap(doc, map, XMLIdentifier.VERSION_TEXT, "Version");
		appendComponentToMap(doc, map, XMLIdentifier.SUPPORTED_BY_TEXT, "Supported by");
		
		//Entry Log
//		appendComponentToMap(doc, map, XMLIdentifier.ENTRY_LOG_TITLE, "Entry Log");
		appendComponentToMap(doc, map, XMLIdentifier.ENTRY_LOG_TITLE, "Input Your Data Here");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_LABEL, "Date");
		appendComponentToMap(doc, map, XMLIdentifier.START_TIME_LABEL, "Start Time");
		appendComponentToMap(doc, map, XMLIdentifier.HEADPAIN_LOCATION_AMOUNT_LABEL, "Amount of Headpain Location(s)");
		appendComponentToMap(doc, map, XMLIdentifier.ACTIVITY_LABEL, "Activity");
		appendComponentToMap(doc, map, XMLIdentifier.RECENT_MEDICATION_LABEL, "Recent Medication");
		appendComponentToMap(doc, map, XMLIdentifier.MEDICINE_COMPLAINT_LABEL, "Suspected Side Effects of Medicines");
		appendComponentToMap(doc, map, XMLIdentifier.COMMENTS_LABEL, "Comments");
//		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_LABEL, "General Position");
		appendComponentToMap(doc, map, XMLIdentifier.GENERAL_POSITION_LABEL, "Your Most Painful Location");
		appendComponentToMap(doc, map, XMLIdentifier.SELECT_LOCATION_LABEL, "Select Location");
		appendComponentToMap(doc, map, XMLIdentifier.KIND_OF_HEADPAIN_LABEL, "Kind of Headpain");
		appendComponentToMap(doc, map, XMLIdentifier.INTENSITY_LABEL, "Intensity");
		appendComponentToMap(doc, map, XMLIdentifier.INTENSITIY_DESCRIPTION_LABEL, "0 = no pain, 10 = most painful/crying");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_LABEL, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_LABEL, "Seconds");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_SECONDS_TEXT, "Seconds");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_MINUTES_TEXT, "Minutes");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_HOURS_TEXT, "Hours");
		appendComponentToMap(doc, map, XMLIdentifier.DURATION_UNIT_DAYS_TEXT, "Days");
		appendComponentToMap(doc, map, XMLIdentifier.YOUR_MOST_PAINFUL_AREA_LABEL, "Your Most Painful Area");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_PRESETS_LABEL, "Common Area");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_CUSTOM_LABEL, "Others");
		appendComponentToMap(doc, map, XMLIdentifier.COMMENTS_LABEL_LONG, "Comments (including what activity that cannot be done during pain strike, etc.)");
		
		//Default Activities
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT, "Sunlight");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP, "Improper Diet/Sleep Routine");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY, "Physical Activity");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR, "Stressor");
		
		//Default Pain Kind
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING, "Pulsating");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING, "Radiating");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING, "Throbbing");
		appendComponentToMap(doc, map, XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND, "Tight Band");
		
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
		
		//Pain Location New
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_EYE_AND_FOREHEAD_TEXT, "Eye Area And Forehead");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_FACE_LEFT_AND_HEAD_TEXT, "Left-Side Face and Head");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_FACE_RIGHT_AND_HEAD_TEXT, "Right-Side Face And Head");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_HEAD_ALL_TEXT, "Full Head");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_HEAD_BACK_TEXT, "Back Head");
		appendComponentToMap(doc, map, XMLIdentifier.PAIN_LOCATION_HEAD_FRONT_TEXT, "Front Head");
		
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
		appendComponentToMap(doc, map, XMLIdentifier.SWITCH_TEXT, "Switch");
		appendComponentToMap(doc, map, XMLIdentifier.SHOW_TEXT, "Show");
		appendComponentToMap(doc, map, XMLIdentifier.NEW_TEXT, "New");
		appendComponentToMap(doc, map, XMLIdentifier.ETC_TEXT, "Etc");
		appendComponentToMap(doc, map, XMLIdentifier.OTHER_TEXT, "Other");
		appendComponentToMap(doc, map, XMLIdentifier.IMAGE_FILES_TEXT, "Image Files");
		appendComponentToMap(doc, map, XMLIdentifier.SAVE_IMAGE_TOOLIP_TEXT, "Save as Image");
		appendComponentToMap(doc, map, XMLIdentifier.YOUR_LEFT_TEXT, "Your Left");
		appendComponentToMap(doc, map, XMLIdentifier.YOUR_RIGHT_TEXT, "Your Right");
		appendComponentToMap(doc, map, XMLIdentifier.PRESETS_TEXT, "Presets");
		appendComponentToMap(doc, map, XMLIdentifier.CUSTOM_TEXT, "Custom");
		appendComponentToMap(doc, map, XMLIdentifier.OPTIONS_TEXT, "Options");
		appendComponentToMap(doc, map, XMLIdentifier.AMOUNT_OF_ENTRIES_TEXT, "Frequency");
		appendComponentToMap(doc, map, XMLIdentifier.AVERAGE_TEXT, "Average");
		appendComponentToMap(doc, map, XMLIdentifier.TIME_TEXT, "Time");
		appendComponentToMap(doc, map, XMLIdentifier.KINDS_OF_HEADPAINS_TEXT, "Kinds of Headpains");
		appendComponentToMap(doc, map, XMLIdentifier.AMOUNT_TEXT, "Amount");
		appendComponentToMap(doc, map, XMLIdentifier.AVERAGE_INTENSITY_TEXT, "Average Intensity");
		appendComponentToMap(doc, map, XMLIdentifier.AVERAGE_DURATION_TEXT, "Average Duration");
		appendComponentToMap(doc, map, XMLIdentifier.DAYS_TEXT, "Days");
		appendComponentToMap(doc, map, XMLIdentifier.DATE_RANGE_TEXT, "Date Range");
		appendComponentToMap(doc, map, XMLIdentifier.EXPORT_TEXT, "Export");
		appendComponentToMap(doc, map, XMLIdentifier.DETAILS_TEXT, "Details");
		appendComponentToMap(doc, map, XMLIdentifier.DAY_TEXT, "Day");
		appendComponentToMap(doc, map, XMLIdentifier.MONTH_TEXT, "Month");
		appendComponentToMap(doc, map, XMLIdentifier.YEAR_TEXT, "Year");
		appendComponentToMap(doc, map, XMLIdentifier.EPISODE_TEXT, "Episode");
		appendComponentToMap(doc, map, XMLIdentifier.TRIGGER_TEXT, "Trigger");
		
		//Graph
//		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_LABEL, "Category");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_LABEL, "Show");
//		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT, "Pain vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT, "Frequency vs Day");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_DATE_TEXT, "Average Intensity vs Day");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_DATE_TEXT, "Average Duration vs Day");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_INTENSITY_VS_TIME, "Intensity vs Episode");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_DURATION_VS_TIME, "Duration vs Episode");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_DATE, "Kinds of Pains vs Day");
//		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE, "Pain Locations vs Date");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORY_TRIGGER_VS_DATE, "Trigger vs Day");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORIES_INTENSITY_AVERAGE_VS_MONTH_TEXT, "Average Intensity vs Month");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_CATEGORIES_DURATION_AVERAGE_VS_MONTH_TEXT, "Average Duration vs Month");
		//Graph Settings
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_SETTINGS_ENABLE_DATA_VALUES, "Show Data Values");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_SETTINGS_DISPLAY_VOID_DATA, "Display Empty Data");
		appendComponentToMap(doc, map, XMLIdentifier.GRAPH_SETTINGS_DISPLAY_DATA_POINTS, "Display Data Points");
		
		//Table
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_BY_LABEL, "Filter By");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_KEYWORD_LABEL, "Keyword");
		//Table Filters
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT, "Pain Amount");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT, "Comments");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT, "Intensity");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_TRIGGER_TEXT, "Trigger");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT, "Pain Kind");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT, "Pain Position");
		//Table Usage Guide
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_FILTER_GUIDE_USAGE_TEXT, "Empty fields means all data (no filter), otherwise it will search the table for the exact text");
		//Table Button Tooltips
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_BACK_TEXT, "Go back to main menu");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_DELETE_TEXT, "Delete the selected entries (cannot be undone)");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_TOOLTIPS_BUTTON_SELECT_TEXT, "Open the Entry Log of the selected entry (only one at a time)");
		//Table Headers
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_TRIGGER_TEXT, "Trigger");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_TRIGGER_DETAILS_TEXT, "Trigger Details");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_COMMENTS_TEXT, "Comments");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_DATE_TEXT, "Date");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_DURATIONS_TEXT, "Duration");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_INTENSITIES_TEXT, "Intensities");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_AMOUNT_TEXT, "Amount of Headpain Location(s)");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_KINDS_TEXT, "Kinds of Headpain");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_PAIN_POSITIONS_TEXT, "Pain Positions");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_SELECT_TEXT, "Select");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_START_TIME_TEXT, "Start Time");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_RECENT_MEDICATION_TEXT, "Recent Medication");
		appendComponentToMap(doc, map, XMLIdentifier.TABLE_HEADERS_MEDICINE_COMPLAINT_TEXT, "Suspected Side Effects of Medicines");
		
		//Program Settings
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_TITLE, "Window");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_TEXT, "Window Mode");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_FULLSCREEN_TEXT, "Fullscreen");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_WINDOW_MODE_WINDOWED_TEXT, "Windowed");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_TITLE, "Database");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_PATH_TEXT, "Database Location");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_PATH_SELECT_TEXT, "Select Database Location");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_USERS_PATH_TEXT, "Users Database Location");
		appendComponentToMap(doc, map, XMLIdentifier.SETTINGS_DATABASE_USERS_SELECT_TEXT, "Select Users Database Location");
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
		
		//Patient Data Form
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL, "Medical ID");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL, "Name");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL, "Date of Birth");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_FORM_MED_ID_TOOLTIP_TEXT, "Medical Record (If for personal use only, just enter anything)");
		
		//Patient Data Registration Form
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_TITLE_LABEL, "Patient Data Registeration Form");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_ID_MESSAGE, "Medical ID cannot be empty!");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_NAME_MESSAGE, "Name cannot be empty!");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_ID_MESSAGE_TITLE, "Empty ID");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_EMPTY_NAME_MESSAGE_TITLE, "Empty Name");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_OVERWRITE_MESSAGE_TITLE, "Confirm Overwrite");
		appendComponentToMap(doc, map, XMLIdentifier.PATIENT_DATA_REGISTERATION_FORM_OVERWRITE_MESSAGE_TEXT, "Another data of the same ID already exists, do you want to overwrite?");
		
		//Active Patient Panel
		appendComponentToMap(doc, map, XMLIdentifier.ACTIVE_PATIENT_PANEL_PATIENT_TEXT, "User");
		
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
