package diary.methods;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.JTableHeader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import diary.ImageExportPanel;
import diary.constants.Constants;
import diary.constants.Globals;
import diary.constants.ImageConstants;
import diary.constants.PainLocationConstants;
import diary.constants.PanelName;
import diary.constants.XMLIdentifier;
import diary.data.PainEntryData;
import diary.data.Settings;
import diary.gui.MainFrame;
import diary.gui.graphs.GraphPanel;
import diary.gui.table.TableScreen;
import diary.patientdata.PatientData;
import giantsweetroll.files.FileManager;
import giantsweetroll.numbers.GNumbers;
import giantsweetroll.xml.dom.XMLManager;

public class Methods 
{	
	public static String getTextData(JTextField tf)
	{
		return tf.getText().trim();
	}
	public static String getTextData(JFormattedTextField tf)
	{
		return tf.getText().trim().replace(",", "");
	}
	
	public static boolean isEmpty(JTextField tf)
	{
		if (Methods.getTextData(tf).equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public static String generatePainDataFolderPathName(PatientData patient, PainEntryData entry)
	{
		return Globals.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator +
				patient.getID() + File.separator +
				Integer.toString(entry.getDate().getYear()) + File.separator +
				Integer.toString(entry.getDate().getMonth()) + File.separator +
				Integer.toString(entry.getDate().getDay()) + File.separator;
	}
	public static String generatePainDataFilePathName(PatientData patient, PainEntryData entry)
	{
		return 	generatePainDataFolderPathName(patient, entry) + File.separator +
				entry.getTimeHour() + "-" +
				entry.getTimeMinutes()/* + "-" +
				entry.getDataMap().get(PainDataIdentifier.TIME_SECONDS)*/ +
				Constants.PAIN_DATA_ENTRY_FILE_EXTENSION;
	}
	/*
	public static String generatePainDataFolderPathName(Document doc)
	{
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		return MainFrame.setting.getDataMap().get(Settings.DATABASE_PATH) + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_YEAR), 0).getTextContent() + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_MONTH), 0).getTextContent() + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.DATE_DAY), 0).getTextContent();
	}
	
	public static String generatePainDataFilePathName(Document doc)
	{
		Element rootElement = XMLManager.getElement(doc.getElementsByTagName(PainDataIdentifier.MASTER_NODE), 0);
		
		return 	Methods.generatePainDataFolderPathName(doc) + File.separator +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_HOUR), 0).getTextContent() + "-" +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_MINUTE), 0).getTextContent() + "-" +
				XMLManager.getElement(rootElement.getElementsByTagName(PainDataIdentifier.TIME_SECONDS), 0).getTextContent() +
				".xml";
	}
	*/
	
	public static double getHighestValue(List<Double> list)
	{
		double max = 0;
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i)>max)
			{
				max = list.get(i);
			}
		}
		
		return max;
	}
	
	public static float round(float value, int precision)
	{
	    int scale = (int) Math.pow(10, precision);
	    return (float) Math.round(value * scale) / scale;
	}
	
	/*
	public static String convertToLanguageText(String painLocationID)	//Method used to convert Pain Location ID to the language's text
	{
		//General
		if (painLocationID.equals(PainLocationConstants.HEAD))
		{
			return Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_HEAD_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS))
		{
			return Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EARS_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES))
		{
			return Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_EYES_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS))
		{
			return Methods.getLanguageText(XMLIdentifier.GENERAL_POSITION_CHEEKS_TEXT);
		}
		//General 2
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_HEAD_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_HEAD_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_HEAD_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_HEAD_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_EARS_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_EARS_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_EYES_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_EYES_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_GENERAL))
		{
			return Methods.getLanguageText(XMLIdentifier.SPECIFIC_POSITION_CHEEKS_RIGHT_TEXT);
		}
		//Specific
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_BOTTOM_LEFT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_BOTTOM_RIGHT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_BOTTOM_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_CENTER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_TOP_LEFT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_BACK_TOP_RIGHT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_BACK_TOP_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_CENTER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_LEFT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_LEFT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_FRONT_RIGHT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_FRONT_RIGHT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_BACK))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_CENTER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_LEFT_FRONT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_BACK))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_BACK_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_CENTER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_CENTER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.HEAD_SIDE_RIGHT_FRONT))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_HEAD_SIDE_FRONT_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_HOLE))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_LEAF_BOTTOM))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_LEFT_LEAF_TOP))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_HOLE))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_HOLE_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_LEAF_BOTTOM))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EARS_RIGHT_LEAF_TOP))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EARS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_EYEBALL))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_BEHIND))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_EYEBROW))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_LEFT_SOCKET))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_EYEBALL))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBALL_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_BEHIND))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_BEHIND_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_EYEBROW))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_EYEBROW_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.EYES_RIGHT_SOCKET))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_EYES_SOCKET_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_LOWER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_LEFT_UPPER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_LOWER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_LOWER_TEXT);
		}
		else if (painLocationID.equals(PainLocationConstants.CHEEKS_RIGHT_UPPER))
		{
			return Methods.getLanguageText(XMLIdentifier.VERY_SPECIFIC_POSITION_CHEEKS_UPPER_TEXT);
		}
		
		return "";
	}	*/
	
	public static void makeFullscreen(JFrame frame)
	{
		frame.dispose();
		try
		{
			frame.add(MainFrame.jComponent);
		}
		catch(NullPointerException ex){}
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		MainFrame.isFullScreen = true;
	}
	public static void makeWindowed(JFrame frame)
	{
		frame.dispose();
		try
		{
			frame.add(MainFrame.jComponent);
		}
		catch(NullPointerException ex){}
		frame.setExtendedState(JFrame.NORMAL);
		frame.setUndecorated(false);
		frame.setSize(Constants.SCREENSIZE.width/2, (Constants.SCREENSIZE.height/4)*3);
	//	frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		MainFrame.isFullScreen = false;
	}

	public static String[] getLanguages()
	{
		List<String> rawFiles = new ArrayList<String>();
		FileManager.getListOfFiles(rawFiles, Constants.LANGUAGE_FOLDER_PATH, false, FileManager.FILE_ONLY, FileManager.NAME_ONLY);
		
		//Trim to only xml files
		for (int i=0; i<rawFiles.size(); i++)
		{
	//		System.out.println(rawFiles.get(i));
	//		System.out.println(rawFiles.get(i).substring(rawFiles.get(i).length()-4, rawFiles.get(i).length()));
			
			if (!rawFiles.get(i).substring(rawFiles.get(i).length()-4, rawFiles.get(i).length()).equalsIgnoreCase(".xml"))
			{
	//			System.out.println("Deleting " + rawFiles.get(i) + ".....");
				rawFiles.remove(i);
				i = -1;
			}
		}
		
		for (int i=0; i<rawFiles.size(); i++)
		{
			//Remove XML extension
			rawFiles.set(i, rawFiles.get(i).substring(0, rawFiles.get(i).length()-4));
		}
		
		return rawFiles.toArray(new String[rawFiles.size()]);
	}
	
	public static boolean hasRegisteredUser()		//Check if there are registered users
	{
		if (FileOperation.getListOfPatients().size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isPartOfDefaultActivity(String activity)
	{
		for (String def : Constants.DEFAULT_ACTIVITIES)
		{
			if (activity.equals(def))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPartOfDefaultPainKind(String pain)
	{
		for (String def : Constants.DEFAULT_PAIN_KIND)
		{
			if (pain.equals(def))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static String getLanguageText(String key)
	{
		try
		{
			return Constants.LANGUAGE.getTextMap().get(key);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
	}
	
	public static BufferedImage createImage(JTable table)
	{
		JTableHeader tableHeader = table.getTableHeader();
		int totalWidth = tableHeader.getWidth() + table.getWidth();
		int totalHeight = tableHeader.getHeight() + table.getHeight();
		BufferedImage tableImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)tableImage.getGraphics();
		tableHeader.paint(g2);
		g2.translate(0, tableHeader.getHeight());
		table.paint(g2);
		
		return tableImage;
	}
	
	public static BufferedImage createImage(JPanel panel)
	{
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		panel.paint(g2);
		
		return image;
	}
	
	/*
	@Deprecated
	public static void exportPanelImage(ImagePanel panel, boolean showPreview)
	{
		MainFrame.changePanel(panel);
		if (showPreview)
		{
			FileOperation.exportImage(Methods.createImage((ImagePanel)MainFrame.jComponent));
		}		
		MainFrame.changePanel(MainFrame.lastComponent);
		try
		{
			if (MainFrame.jComponent instanceof GraphPanel)
			{
				((GraphPanel)MainFrame.jComponent).refreshGraph();		//Redraws graph to its original state
			}
			else if (MainFrame.jComponent instanceof TableScreen)
			{
				((TableScreen)MainFrame.jComponent).initTable();		//Recreates table to its original state
			}
		}
		catch(ClassCastException ex) {}
		if (!showPreview)
		{
			FileOperation.exportImage(Methods.createImage((ImagePanel)MainFrame.lastComponent));
		}
	}
	*/
	public static void exportPanelImage(MainFrame mainFrame, ImageExportPanel panel, boolean showPreview)
	{
	//	MainFrame.changePanel(panel);
		mainFrame.changePanel(panel, PanelName.IMAGE_EXPORT_PANEL);
		if (showPreview)
		{
			FileOperation.exportImage(Methods.createImage((ImageExportPanel)MainFrame.jComponent));
		}		
//		MainFrame.changePanel(MainFrame.lastComponent);
		mainFrame.changePanel(PanelName.GRAPH_PANEL);
		try
		{
			if (MainFrame.jComponent instanceof GraphPanel)
			{
				((GraphPanel)MainFrame.jComponent).refreshGraph();		//Redraws graph to its original state
			}
			else if (MainFrame.jComponent instanceof TableScreen)
			{
				((TableScreen)MainFrame.jComponent).refreshTable();		//Recreates table to its original state
			}
		}
		catch(ClassCastException ex) {}
		if (!showPreview)
		{
			FileOperation.exportImage(Methods.createImage((GraphPanel)MainFrame.lastComponent));
		}
	}
	
	public static boolean isLastIndex(JComboBox<?> combo)
	{
		return combo.getSelectedIndex() == combo.getModel().getSize()-1;
	}
	
	public static ImageIcon resizeImageByRatio(ImageIcon image, int percentage)
	{
		Image img = image.getImage();
		img = img.getScaledInstance((image.getIconWidth()/100)*percentage, (image.getIconHeight()/100)*percentage, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	public static final LinkedHashMap<String, LinkedHashMap<URL, String>> generatePainLocationsTextURLMap()
	{
		LinkedHashMap<String, LinkedHashMap<URL, String>> map = new LinkedHashMap<String, LinkedHashMap<URL, String>>();
		
		LinkedHashMap<URL, String> subMap = new LinkedHashMap<URL, String>();
		
		subMap.put(ImageConstants.PAIN_LOCATION_EYES_AND_FOREHEAD, PainLocationConstants.EYES_AND_FOREHEAD);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_EYE_AND_FOREHEAD_TEXT), subMap);
		
		subMap = new LinkedHashMap<URL, String>();
		subMap.put(ImageConstants.PAIN_LOCATION_FACE_LEFT_AND_HEAD, PainLocationConstants.FACE_LEFT_AND_HEAD);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_FACE_LEFT_AND_HEAD_TEXT), subMap);
		
		subMap = new LinkedHashMap<URL, String>();
		subMap.put(ImageConstants.PAIN_LOCATION_FACE_RIGHT_AND_HEAD, PainLocationConstants.FACE_RIGHT_AND_HEAD);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_FACE_RIGHT_AND_HEAD_TEXT), subMap);
		
		subMap = new LinkedHashMap<URL, String>();
		subMap.put(ImageConstants.PAIN_LOCATION_HEAD_FULL, PainLocationConstants.HEAD_FULL);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_HEAD_ALL_TEXT), subMap);
		
		subMap = new LinkedHashMap<URL, String>();
		subMap.put(ImageConstants.PAIN_LOCATION_HEAD_BACK, PainLocationConstants.HEAD_BACK);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_HEAD_BACK_TEXT), subMap);
		
		subMap = new LinkedHashMap<URL, String>();
		subMap.put(ImageConstants.PAIN_LOCATION_HEAD_FRONT, PainLocationConstants.HEAD_FRONT);
		map.put(Methods.getLanguageText(XMLIdentifier.PAIN_LOCATION_HEAD_FRONT_TEXT), subMap);
		
		return map;
	}
	
	public static boolean isSelectedItem(JComboBox<String> combo, String item, boolean ignoreCase)
	{
		if(ignoreCase)
		{
			if (combo.getSelectedItem().toString().equalsIgnoreCase(item))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (combo.getSelectedItem().toString().equals(item))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public static int getPercentage(ImageIcon image, int targetValue)
	{
		return (int)GNumbers.round((((float)targetValue)/((float)image.getIconWidth())) * 100f, 0);
	}
	public static int getPercentageValue(int source, int percentage)
	{
		return (int)GNumbers.round((((float)source)/100f)* ((float)percentage), 0);
	}
	
	public static boolean elementExists(List<String> list, String element, boolean ignoreCase)
	{
		for (String item : list)
		{
			if (ignoreCase)
			{
				if (item.equalsIgnoreCase(element))
				{
					return true;
				}
			}
			else
			{
				if (item.equals(element))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	public static void deleteElement(List<String> list, String element, boolean ignoreCase)
	{
		for (int i=0; i<list.size(); i++)
		{
			if (ignoreCase)
			{
				if (list.get(i).equalsIgnoreCase(element))
				{
					list.remove(i);
					i=-1;
					continue;
				}
			}
			else
			{
				if (list.get(i).equals(element))
				{
					list.remove(i);
					i=-1;
					continue;
				}
			}
		}
	}
	
	public static String convertPainKindLanguageToID(String text)
	{
		if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING)))
		{
			return XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING)))
		{
			return XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING)))
		{
			return XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND)))
		{
			return XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND;
		}
/*		else if (text.equals(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)))
		{
			return XMLIdentifier.OTHER_TEXT;
		}			*/
		else
		{
			return text;
		}
	}
	public static String convertPainKindIDToLanguage(String id)
	{
		if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND);
		}
		else if (id.equals(XMLIdentifier.OTHER_TEXT))
		{
			return Methods.getLanguageText(XMLIdentifier.OTHER_TEXT);
		}
/*		else if (!Methods.isPartOfDefaultPainKind(id))
		{
			return Methods.getLanguageText(XMLIdentifier.OTHER_TEXT);
		}		*/
		else
		{
			return id;
		}
	}

	public static String convertActivityLanguageToID(String text)
	{
		if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT)))
		{
			return XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP)))
		{
			return XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY)))
		{
			return XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY;
		}
		else if (text.equals(Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR)))
		{
			return XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR;
		}
/*		else if (text.equals(Methods.getLanguageText(XMLIdentifier.OTHER_TEXT)))
		{
			return XMLIdentifier.OTHER_TEXT;
		}			*/
		else
		{
			return text;
		}
	}
	public static String convertActivityIDToLanguage(String id)
	{
		if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY);
		}
		else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR))
		{
			return Methods.getLanguageText(XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR);
		}
/*		else if (id.equals(XMLIdentifier.OTHER_TEXT))
		{
			return Methods.getLanguageText(XMLIdentifier.OTHER_TEXT);
		}			*/
		else
		{
			return id;
		}
	}
	
	public static void setUIFont(FontUIResource f)
	{
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements())
		{
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
			{
				UIManager.put(key, f);
			}
		}
	}
	
	public static final String createTextWithRequiredIdentifier(String text)
	{
		return "<html>" + text + "<font color='red'>" + Constants.REQUIRED_IDENTIFIER + "</font></html>";
	}
	
	public static void removeDuplicatesFromStringList(List<String> list)
	{
		for (int i=0; i<list.size()-1; i++)
		{
			for (int a=i+1; a<list.size(); a++)
			{
				if (list.get(i).equals(list.get(a)))
				{
					list.remove(a);
					a=i;
				}
			}
		}
	}
	
	public static void refresHistories(PatientData patient)
	{
		Globals.HISTORY_MEDICINE_COMPLAINT.refresh(patient);
		Globals.HISTORY_RECENT_MEDICATION.refresh(patient);
	}
	
	public static Border createTransparentBorder(int thickness)
	{
		return BorderFactory.createLineBorder(new Color(0, 0, 0, 0), thickness);
	}
	
	public static void setButtonToFollowMainMenuLayout(JButton button)
	{
		button.setBackground(Constants.COLOR_MAIN_MENU_BUTTONS);
		button.setForeground(Color.WHITE);
	}
	
	public static void changePanel(JComponent component, String keyword)
	{
		if (component.getLayout() instanceof CardLayout)
		{
			((CardLayout)component.getLayout()).show(component, keyword);
		}
	}
	
    public static void printArray(Object arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
    
    public static String getElementTextContent(Document doc, String key)			//Get text content of element from the selected document (only takes text content from the first NodeList)
    {
    	try
    	{
    		return XMLManager.getElement(doc.getElementsByTagName(key), 0).getTextContent();
    	}
    	catch(NullPointerException ex)
    	{
    		return "";
    	}
    }
    
    public static List<String> getTextContentsFromElements(List<Element> elements)
    {
    	List<String> list = new ArrayList<String>();
    	
    	for (Element element : elements)
    	{
    		list.add(element.getTextContent());
    	}
    	
    	return list;
    }
    
    public static int secondsToMinutes(int sec)
    {
    	return sec/60;
    }
    public static int secondsToHours(int sec)
    {
    	return sec/3600;
    }
    public static int secondsToDays(long sec)
    {
    	return Integer.parseInt(Long.toString(sec/86400));
    }
    public static int minutesToSeconds(int min)
    {
    	return min*60;
    }
    public static int hoursToSeconds(int hours)
    {
    	return hours*3600;
    }
    public static long daysToSeconds(int days)
    {
    	return days*86400;
    }
}