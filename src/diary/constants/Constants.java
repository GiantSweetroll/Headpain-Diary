package diary.constants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;

import diary.language.Language;
import giantsweetroll.Misc;

public final class Constants 
{
	public static final String PROGRAM_TITLE = "My Headpain Diary";
	public static final String VERSION = "1.0";
	
	public static final Dimension SCREENSIZE = Misc.getScreenSize();
	public static final Dimension IMAGE_SIZE = new Dimension(150, 130);
//	public static final Dimension IMAGE_SIZE = new Dimension(200, 180);
	
	public static final String LANGUAGE_FOLDER_PATH = "data" + File.separator + "settings" + File.separator + "language" + File.separator;
	public static final Language LANGUAGE = new Language();
	
	public static final String REQUIRED_IDENTIFIER = "* ";
	
	public static final String DATABASE_PATH = "data" + File.separator + "database" + File.separator;
	public static final String DATABASE_DEFAULT_PATH = "data" + File.separator + "database" + File.separator;
	public static final String SETTINGS_FOLDER_PATH = "data" + "/" + "settings" + "/";
	
	//Text Field Formats
	public static final NumberFormat AMOUNT_FORMAT = NumberFormat.getNumberInstance();
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	
	//Fonts
	private static final String FONT_TYPE_GENERAL = "verdana";
	public static final Font FONT_GENERAL = new Font(FONT_TYPE_GENERAL, Font.PLAIN, 15);
	public static final Font FONT_GENERAL_BOLD = new Font(FONT_TYPE_GENERAL, Font.BOLD, 15);
	public static final Font FONT_TITLE = new Font(FONT_TYPE_GENERAL, Font.BOLD, 40);
	public static final Font FONT_SMALL_NOTE = new Font(FONT_TYPE_GENERAL, Font.ITALIC, 8);
	
	//Insets
	private static final int INSETS_BASE = 5;
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
	public static final String[] GRAPH_CATEGORIES = {Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_VS_DATE_TEXT),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ENTRIES_VS_DATE_TEXT),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_INTENSITY_AVERAGE_VS_TIME_TEXT),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_DURATION_AVERAGE_VS_TIME_TEXT),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_KIND_VS_DATE),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_PAIN_LOCATION_VS_DATE),
														Constants.LANGUAGE.getTextMap().get(XMLIdentifier.GRAPH_CATEGORY_ACTIVITY_VS_DATE)};
	
	public static final String[] TABLE_FILTER_OPTIONS = {Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_AMOUNT_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_POSITIONS_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_PAIN_KINDS_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_INTENSITIES_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_DURATIONS_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_ACTIVITY_TEXT),
															Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_FILTER_TYPE_COMMENTS_TEXT)};
	
	public static final String[] TABLE_HEADERS = {Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_SELECT_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_DATE_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_START_TIME_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_PAIN_AMOUNT_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_PAIN_POSITIONS_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_PAIN_KINDS_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_INTENSITIES_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_DURATIONS_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_ACTIVITY_TEXT),
													Constants.LANGUAGE.getTextMap().get(XMLIdentifier.TABLE_HEADERS_COMMENTS_TEXT)};
	
	public static final Object[] OPTION_PANE_YES_NO_CANCEL_BUTTON_TEXTS = {Constants.LANGUAGE.getTextMap().get(XMLIdentifier.YES_TEXT),
																			Constants.LANGUAGE.getTextMap().get(XMLIdentifier.NO_TEXT),
																			Constants.LANGUAGE.getTextMap().get(XMLIdentifier.CANCEL_TEXT)};
}
