package diary.constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Comparator;

import javax.swing.filechooser.FileNameExtensionFilter;

import diary.methods.Methods;
import giantsweetroll.GGUtilities;
import giantsweetroll.ImageManager;
import giantsweetroll.numbers.GNumbers;

public final class Constants 
{
	public static final String PROGRAM_TITLE = "My Headpain Diary";
	public static final String VERSION = "0.2b";
	
	public static final Dimension SCREENSIZE = GGUtilities.getScreenSize();
	public static final Dimension IMAGE_SIZE = new Dimension(150, 130);
//	public static final Dimension IMAGE_SIZE = new Dimension(200, 180);
	
	public static final String LANGUAGE_FOLDER_PATH = "data" + File.separator + "settings" + File.separator + "language" + File.separator;
	public static final String DEFAULT_LANGUAGE = "bahasa_indonesia";
	public static final String REQUIRED_IDENTIFIER = "*";
	
	public static final String DATABASE_DEFAULT_PATH = "data" + File.separator + "database" + File.separator;
	public static final String PATIENTS_LIST_DEFAULT_PATH = "data" + File.separator + "users" + File.separator;
//	public static final String DATABASE_PATH = MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator;
	public static final String SETTINGS_FOLDER_PATH = "data" + File.separator + "settings" + File.separator;
	public static final String SETTINGS_FILE_NAME = "settings.xml";
	
	public static final String HISTORY_FOLDER_PATH = "data" + File.separator + "history" + File.separator;
	public static final String HISTORY_RECENT_MEDICATION_NAME = "recent_medication";
	public static final String HISTORY_MEDICINE_COMPLAINT_NAME = "medicine_complaint";
	public static final String HISTORY_PAIN_KIND_NAME = "pain_kind";
	
	public static final String PAIN_DATA_ENTRY_FILE_EXTENSION = ".xml";
	
	public static final String PAIN_ENTRY_DATE_SEPARATOR = "/";
	
	//Dimensions
	public static final Dimension CUSTOM_PAIN_LOCATION_IMAGE_SIZE = new Dimension((ImageManager.getImageIcon(PainLocationPath.CUSTOM_PAIN_BACK_1).getIconWidth()),
																					(ImageManager.getImageIcon(PainLocationPath.CUSTOM_PAIN_BACK_1).getIconHeight()));
	
	//Ratios
	public static final int BUTTON_IMAGE_SIZE_RATIO = Methods.getPercentageValue(Constants.SCREENSIZE.width, 4);
	
	//Text Field Formats
	public static final NumberFormat AMOUNT_FORMAT = NumberFormat.getNumberInstance();
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	
	//Colors
	public static final Color COLOR_MAIN_MENU_BUTTONS = new Color(23, 74, 211);
	public static final Color COLOR_MAIN_MENU_BACKGROUND = new Color(0, 187, 212);
	public static final Color COLOR_TRANSPARENT = new Color (0, 0, 0, 0);
	public static final Color COLOR_CUSTOM_PAIN_LOCATION_HIGHLIGHT = new Color (16, 255, 22, 60);
	public static final Color COLOR_GRAPH_AXES_MARKER_LABELS = new Color(23, 74, 211);
	public static final Color COLOR_TABLE_HEADER = new Color (23, 74, 211);
	public static final Color COLOR_DISABLED_COMPONENT = new Color(224, 224, 224);
	public static final Color COLOR_HIGHLIGHT_BUTTON = new Color(28, 203, 15);
	
	//Fonts
	public static final String FONT_TYPE_GENERAL = "century gothic";
	public static final int FONT_GENERAL_SIZE = 15;
	public static final Font FONT_GENERAL = new Font(FONT_TYPE_GENERAL, Font.PLAIN, FONT_GENERAL_SIZE);
	public static final Font FONT_GENERAL_BOLD = new Font(FONT_TYPE_GENERAL, Font.BOLD, FONT_GENERAL_SIZE);
	public static final Font FONT_GENERAL_A_BIT_BIGGER = new Font(FONT_TYPE_GENERAL, Font.BOLD, FONT_GENERAL_SIZE+3);
	public static final Font FONT_SUB_TITLE = new Font(FONT_TYPE_GENERAL, Font.BOLD, FONT_GENERAL_SIZE*2);
	public static final Font FONT_TITLE = new Font(FONT_TYPE_GENERAL, Font.BOLD, (int)GNumbers.round((float)FONT_GENERAL_SIZE*2.5f, 0));
	public static final Font FONT_SMALL_NOTE = new Font(FONT_TYPE_GENERAL, Font.ITALIC, (int)GNumbers.round(((float)FONT_GENERAL_SIZE/15f)*8f, 0));
	public static final Font FONT_HEADER = new Font(FONT_TYPE_GENERAL, Font.BOLD, FONT_GENERAL_SIZE+10);
	
	//File Filter for JFileChooser
	public static FileNameExtensionFilter FILTER_IMAGE_FILES;
	
	
	//Insets
	public static final int INSETS_BASE = 5;
	public static final Insets INSETS_GENERAL = new Insets (INSETS_BASE, 
															INSETS_BASE, 
															INSETS_BASE, 
															INSETS_BASE);
	public static final Insets INSETS_GENERAL_MORE_LEFT = new Insets (INSETS_GENERAL.top, 
																		INSETS_GENERAL.left, 
																		INSETS_GENERAL.bottom, 
																		INSETS_GENERAL.right + 45);
	public static final Insets INSETS_TOP_COMPONENT = new Insets (INSETS_GENERAL.top + 10, 
																	INSETS_GENERAL.left, 
																	INSETS_GENERAL.bottom, 
																	INSETS_GENERAL.right);
	public static final Insets INSETS_TOP_COMPONENT_MORE_LEFT = new Insets (INSETS_TOP_COMPONENT.top,
																			INSETS_TOP_COMPONENT.left, 
																			INSETS_TOP_COMPONENT.bottom,
																			INSETS_GENERAL_MORE_LEFT.right);
	public static final Insets INSETS_TITLE = new Insets (INSETS_TOP_COMPONENT.top, 
															INSETS_GENERAL.left, 
															INSETS_GENERAL.bottom + 45, 
															INSETS_GENERAL.right);
	
	//JScrollPane
	public static final int SCROLL_SPEED = 10;
	
	//Time
	public static final int[] TIME_60()
	{
		int[] arr = new int[61];
		
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = i;
		}
		
		return arr;
	}
	public static final int[] TIME_24()
	{
		int[] arr = new int[25];
		
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = i;
		}
		
		return arr;	
	}

	//Arrays
	/*
	public static final String[] GRAPH_CATEGORIES = {//Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_DATE_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_DATE_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORIES_INTENSITY_AVERAGE_VS_MONTH_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORIES_DURATION_AVERAGE_VS_MONTH_TEXT),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_VS_TIME),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_DURATION_VS_TIME),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_AMOUNT),
									//					Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE),
														Methods.getLanguageText(XMLIdentifier.GRAPH_CATEGORY_TRIGGER_VS_AMOUNT)};
	
	public static final String[] TABLE_FILTER_OPTIONS = {//Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_TRIGGER_TEXT),
															Methods.getLanguageText(XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT)};
	
	public static final String[] ENTRY_TABLE_HEADERS = {Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_SELECT_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_DATE_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_START_TIME_TEXT),
					//								Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_PAIN_AMOUNT_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_PAIN_POSITIONS_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_PAIN_KINDS_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_INTENSITIES_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_DURATIONS_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_TRIGGER_TEXT),
//													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_TRIGGER_DETAILS_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_RECENT_MEDICATION_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_MEDICINE_COMPLAINT_TEXT),
													Methods.getLanguageText(XMLIdentifier.TABLE_HEADERS_COMMENTS_TEXT)};
	
	public static final Object[] OPTION_PANE_YES_NO_CANCEL_BUTTON_TEXTS = {Methods.getLanguageText(XMLIdentifier.YES_TEXT),
																			Methods.getLanguageText(XMLIdentifier.NO_TEXT),
																			Methods.getLanguageText(XMLIdentifier.CANCEL_TEXT)};
	
	public static final String[] DURATION_UNITS = {Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_SECONDS_TEXT),
													Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_MINUTES_TEXT),
													Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_HOURS_TEXT),
													Methods.getLanguageText(XMLIdentifier.DURATION_UNIT_DAYS_TEXT)};
	
	public static final String[] PATIENT_TABLE_HEADERS = {Methods.getLanguageText(XMLIdentifier.SELECT_TEXT),
															Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_MED_ID_LABEL),
															Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_NAME_LABEL),
															Methods.getLanguageText(XMLIdentifier.PATIENT_DATA_FORM_DOB_LABEL)};
	
	public static final String[] DEFAULT_ACTIVITIES = {Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP),
														Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)};
	
	public static final String[] DEFAULT_PAIN_KIND = {Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING),
														Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND),
														Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)};
	
	public static final String[] DEFAULT_RECENT_MEDICATION = {Methods.getLanguageText(XMLIdentifier.DEFAULT_RECENT_MEDICATION_PRESCRIBED_MIX_CAPSULE)};
	*/
	public static final String[] EMPTY_STRING_ARRAY = {};
	
//	public static final LinkedHashMap<String, LinkedHashMap<URL, String>> PAIN_LOCATIONS = Methods.generatePainLocationsTextURLMap();
	
	//For JSlider
	/*
	public static final MetalSliderUI SLIDER_CUSTOM_UI = new MetalSliderUI()
			{
				@Override
				protected void scrollDueToClickInTrack(int direction)
				{
					//scrollByBlock(direction);			//Normal behaviour
					
					int value = slider.getValue();
					
					if (slider.getOrientation() == JSlider.HORIZONTAL)
					{
						value = this.valueForXPosition(slider.getMousePosition().x);
					}
					else if (slider.getOrientation() == JSlider.VERTICAL)
					{
						value = this.valueForYPosition(slider.getMousePosition().y);
					}
					
					slider.setValue(value);
				}
			};				//Still needs investigation (somehow connected to 2 different sliders, nullpointerexception at duration slider)
			*/

	public static final Comparator<String> STRING_COMPARATOR = new Comparator<String>()
	{

		@Override
		public int compare(String str1, String str2) 
		{
			return str1.compareTo(str2);
		}
	};
}
