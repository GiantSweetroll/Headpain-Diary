package diary.constants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;

import diary.language.English;
import diary.language.Language;
import giantsweetroll.Misc;

public class Constants 
{
	public static final String PROGRAM_TITLE = "My Headpain Diary";
	public static final String VERSION = "1.0";
	
	public static final Dimension SCREENSIZE = Misc.getScreenSize();
	
	public static final Language LANGUAGE = new Language();
	public static final String LANGUAGE_FOLDER_PATH = "data" + "/" + "settings" + "/" + "language" + "/";
	
	//Text Field Formats
	public static final NumberFormat AMOUNT_FORMAT = NumberFormat.getNumberInstance();
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	
	//Fonts
	private static final String FONT_TYPE_GENERAL = "verdana";
	public static final Font FONT_GENERAL = new Font(FONT_TYPE_GENERAL, Font.PLAIN, 15);
	public static final Font FONT_GENERAL_BOLD = new Font(FONT_TYPE_GENERAL, Font.BOLD, 15);
	public static final Font FONT_TITLE = new Font(FONT_TYPE_GENERAL, Font.BOLD, 40);
	
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
	
	public static void main(String args[])
	{
		System.out.println(Constants.LANGUAGE_FOLDER_PATH);
	}
}
