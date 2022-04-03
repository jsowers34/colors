package com.rff.utilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
/**
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%" summary="Purpose of the Class">
 * <TR style="background-color:#EEEEFF">
 * <TD><B>PURPOSE</B></TD>
 * </TR>
 * </TABLE>
 *     Class description for the RFFUtilities.java code.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%" summary="Modification dates, etc.">
 * <THEAD>
 * <TR style="background-color:#EEEEFF"> <td> <th scope=rowgroup> MODIFICATIONS <td>
 * <TR style="background-color:#EEEEFF">
 * <TH>Date <TH>Author <TH>Change Description
 * </THEAD>
 * <TR>
 * <TD> August 07, 2017 <TD>JL Sowers <TD>Initial Entry
 * <TR> <TD> <TD> <TD>
 * </TABLE>
 *
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%" summary="Class Design">
 * <TR style="background-color:#EEEEFF">
 * <TD><B>DESIGN NOTES:</B></TD>
 * </TR>
 * </TABLE>
 * None
 */

public class RFFUtilities {
    
    /** The actions. */
    protected static Hashtable<Object, Action> actions = null;

    /**
     * Allowable Message Types.
     */
    private static int[] messageType = { JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
	    JOptionPane.QUESTION_MESSAGE, JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE };
    /**
     * Allowable Option Types
     */
    private static int[] optionType  = { JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION,
	    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION };
    
    
    /**
     * This method sets the minimum, maximum and preferred sizes of the
     * component to the same values x, y
     * @param c   Component to have size set.
     * @param x   width
     * @param y   height
     */
    public static void setFixedSize(JComponent c, int x, int y) {
	Dimension size = new Dimension(x, y);
	c.setMinimumSize(size);
	c.setPreferredSize(size);
	c.setMaximumSize(size);
    }

    /**
     * Positions the component at the center. Works with dual monitors.
     * @param Component
     * @param width of dialog in pixels
     * @param height of dialog in pixels
     */
    public static void setScreenCenter(Component comp, int width, int height) {
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	Point centerPoint = ge.getCenterPoint();
	Point windowPoint = new Point();
	windowPoint.setLocation(centerPoint.getX() - width / 2, centerPoint.getY() - height / 2);
	comp.setLocation(windowPoint);
	comp.setSize(width, height);
    }

    /**
     * Utility method to clear all flavors of TextField except time fields.
     */
    public static void clearField(JTextField Tf) {
	int len = Tf.getDocument().getLength();
	if (len > 0) {
	    try {
		Tf.getDocument().remove(0, len);
	    } catch (BadLocationException e) {
	    }
	}
    }

    /**
     * Replaces a quote nark in a string by a escape-quote.
     * @return String
     */
    public static String escapeQuotes(String str) {
	StringBuffer sb = new StringBuffer();
	char c;
	for (int i = 0; i < str.length(); i++) {
	    c = str.charAt(i);
	    if (c == '\'') {
		sb.append("\\'");
	    } else {
		sb.append(c);
	    }
	}
	return sb.toString();
    }

    /**
     * Find the index of a value within the array.
     * 
     * @param array
     *            array to search
     * @param id
     *            value needed
     * @return index or -1 if not found
     */
    public static int findIndex(int[] array, int id) {
	for (int i = 0; i < array.length; i++) {
	    if (array[i] == id)
		return i;
	}
	return -1;
    }

    /**
     * Find the index of a value within the array.
     * 
     * @param array
     *            array to search
     * @param id
     *            value needed
     * @return index or -1 if not found
     */
    public static int findIndex(String[] array, String id) {
	for (int i = 0; i < array.length; i++) {
	    if (array[i].equalsIgnoreCase(id))
		return i;
	}
	return -1;
    }

    /**
     * Find the index of a value within the arraylist.
     * 
     * @param array
     *            arraylist to search
     * @param id
     *            value needed
     * @return index or -1 if not found
     */
    public static int findIndex(ArrayList<Integer> array, int id) {
	for (int i = 0; i < array.size(); i++) {
	    if (array.get(i).equals(id)) {
		return i;
	    }
	}
	return -1;
    }

    /**
     * Find the index of a value within the arraylist.
     * 
     * @param array
     *            arraylist to search
     * @param id
     *            value needed
     * @return index or -1 if not found
     */
    public static int findIndex(ArrayList<String> array, String id) {
	for (int i = 0; i < array.size(); i++) {
	    if (array.get(i).equalsIgnoreCase(id)) {
		return i;
	    }
	}
	return -1;
    }

    /**
     * Positions the component at the center. Works with dual monitors.
     * 
     * @param Component
     */
    public static void setScreenCenter(Component comp) {
	setScreenCenter(comp, comp.getWidth(), comp.getHeight());
    }

    /**
     * Get the extension of a file
     * 
     * @param f
     *            filename
     * @return String
     */
    public static String getExtension(File f) {
	String s = f.getName();
	return getExtension(s);
    }

    /**
     * Return extension of filename
     * 
     * @param s
     * @return String
     */
    public static String getExtensionLC(String s) {
	String ext = null;
	int i = s.lastIndexOf('.');

	if (i > 0 && i < s.length() - 1) {
	    ext = s.substring(i + 1).toLowerCase();
	}
	return ext;
    }

    /**
     * Extracts the file extension.
     * 
     * @param theFullName
     * @return extension
     */
    public static String getExtension(String theFullName) {
	int dot = theFullName.lastIndexOf(".");
	return theFullName.substring(dot + 1);
    }

    /**
     * Returns the filename portion of the full path removing the initial path
     * and the extension. For example, '/opt/tfms/ook/thisfile.txt' will return
     * 'thisfile'.
     * 
     * @param theFullName
     * @return filename
     */
    public static String getFileName(String theFullName) {
	int dot = theFullName.lastIndexOf(".");
	int sep = theFullName.lastIndexOf("/");
	return theFullName.substring(sep + 1, dot);
    }

    /**
     * Return the path portion from the full pathname. For example,
     * '/opt/tfms/ook/thisfile.txt' returns '/opt/tfms/ook'.
     * 
     * @param theFullName
     * @return
     */
    public static String getPath(String theFullName) {
	int sep = theFullName.lastIndexOf("/");
	return theFullName.substring(0, sep);
    }

    /**
     * Creates an invisible horizontally oriented panel
     * 
     * @return JPanel
     */
    public static JPanel createHorizontalPanel() {
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
	return (p);
    }

    /**
     * Creates an invisible vertically oriented panel
     * 
     * @return JPanel
     */
    public static JPanel createVerticalPanel() {
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	return (p);
    }

    /**
     * Pops up a dialog box with a message and an input field.
     * 
     * @param Component
     *            parent
     * @param String
     *            message
     * @return String if OK button is selected, null otherwise.
     */
    public static String popupInputDialog(Component parent, String message) {
	String inputValue = null;

	JOptionPane op = new JOptionPane(message, messageType[2], optionType[3]);
	op.setWantsInput(true);
	op.setValue(new Integer(JOptionPane.OK_OPTION));

	JDialog dialog = op.createDialog(parent, "Input");

	dialog.setVisible(true);

	if (op.getValue() != null) {
	    if (((Integer) op.getValue()).intValue() == JOptionPane.OK_OPTION) {
		inputValue = (String) op.getInputValue();
	    }
	}
	return inputValue;
    }

    /**
     * Private method to popup a dialog panel of various types.
     */
    private static void popupMessage(Component parent, String message, String title, int mtype) {
	JOptionPane op = new JOptionPane(message, messageType[mtype]);
	JDialog dialog = op.createDialog(parent, title);

	if (parent == null) {
	    setScreenCenter(dialog);
	}
	dialog.setVisible(true);
    }

    /**
     * Private message to popup a confirm dialog in case of procedural confusion
     * on the part of the user.
     */
    private static int popupConfirm(Component parent, String message, String title, int otype, int mtype) {

	// CH77 return JOptionPane.showConfirmDialog(parent, message, title,
	// optionType[otype],
	// messageType[mtype]);
	JOptionPane op = new JOptionPane(message, messageType[mtype], optionType[otype]);
	JDialog dialog = op.createDialog(new JFrame(), title);
	dialog.setVisible(true);

	Object rtnValue = op.getValue();
	if (rtnValue == null) {
	    if (title.equals("COB")) { // CH160
		return JOptionPane.OK_OPTION; // CH160
	    } // CH160
	    else {
		return JOptionPane.CLOSED_OPTION;
	    }
	} else if (rtnValue instanceof Integer) {
	    return ((Integer) rtnValue).intValue();
	} else {
	    return JOptionPane.CLOSED_OPTION;
	}

	/*
	 * if(op.getValue() == null) return JOptionPane.CLOSED_OPTION; else
	 * return ((Integer)op.getValue()).intValue();
	 */
    }

    /**
     * Private message to popup a confirm dialog in case of procedural confusion
     * on the part of the user. DOES NOT set color/font. To be used only when
     * error occurs prior to appearance of Login panel.
     */
    private static int popupConfirm2(Component parent, String message, String title, int otype, int mtype) {
	JOptionPane op = new JOptionPane(message, messageType[mtype], optionType[otype]);
	JDialog dialog = op.createDialog(new JFrame(), title);
	dialog.setVisible(true);
	if (op.getValue() == null) {
	    return JOptionPane.CLOSED_OPTION;
	} else {
	    return ((Integer) op.getValue()).intValue();
	}
    }

    /**
     * Popup a Question Confirm Message.
     * 
     * @return JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION or
     *         JOptionPane.CLOSE_OPTION
     */
    public static int questionConfirm(Component parent, String message) {
	StringBuffer msg = new StringBuffer(
		"Press OK to discard changes and proceed.\n" + "Press CANCEL to return to panel.");
	msg.insert(0, "\n\n");
	msg.insert(0, message);
	return popupConfirm(parent, msg.toString(), "Question", 3, 2);
    }

    /**
     * Popup a Question Confirm Message.
     * 
     * @return JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION or
     *         JOptionPane.CLOSE_OPTION
     */
    public static int questionConfirm(Component parent, String message, String qstn) {
	StringBuffer msg = new StringBuffer(message + "\n\n" + qstn);
	return popupConfirm(parent, msg.toString(), "Question", 3, 2);
    }

    /**
     * Popup a Question Confirm Message.
     * 
     * @return JOptionPane.OK_OPTION, JOptionPane.NO_OPTION
     *         JOptionPane.CLOSE_OPTION
     */
    public static int questionConfirm(Component parent, String message, String qstn, String title) {
	StringBuffer msg = new StringBuffer(message + "\n\n" + qstn);
	return popupConfirm(parent, msg.toString(), title, 1, 2); // CH84
    }

    /**
     * Popup an error message with the chance to override.
     */
    public static int errorConfirm(Component parent, String errmsg, String qstn) {
	String msg = errmsg + "\n\n" + qstn;
	return popupConfirm(parent, msg, "ERROR", 1, 4);
    }

    /**
     * Popup an error message with the chance to override. Calls popupConfirm2
     * which does not set colors/fonts. This is because this method was written
     * to be invoked from TM_Log prior to the creation of login panel.
     */
    public static int errorConfirm2(Component parent, String errmsg, String qstn) {
	String msg = errmsg + "\n\n" + qstn;
	return popupConfirm2(parent, msg, "ERROR", 1, 4);
    }

    /**
     * Popup an warning message with yes no options
     */
    public static int warningConfirm(Component parent, String message, String qstn) {
	String msg = message + "\n\n" + qstn;
	return popupConfirm(parent, msg, "Warning", 1, 3);
    }

    /**
     * Popup a standard message.
     */
    public static void message(Component parent, String message) {
	popupMessage(parent, message, "Message", 0);
    }

    /**
     * Popup an Information message.
     */
    public static void infoMessage(Component parent, String message) {
	popupMessage(parent, message, "Information", 1);
    }

    /**
     * Popup a Question message.
     */
    public static void questionMessage(Component parent, String message) {
	popupMessage(parent, message, "Question", 2);
    }

    /**
     * Popup a Warning message.
     */
    public static void warningMessage(Component parent, String message) {
	popupMessage(parent, message, "Warning", 3);
    }

    /**
     * Popup an Error message.
     */
    public static void errorMessage(Component parent, String message) {
	popupMessage(parent, message, "ERROR", 4);
    }

   
    /**
     * Convert from degrees to radians.
     * @param degrees
     * @return value of degrees in radians
     */
    public static double toRadians(double degrees) {
	return Math.PI * degrees / 180.0;
    }

    /**
     * Calculate column width.
     * @param table the table
     * @param columnIndex the column index
     * @return the int
     */
    // Calculate the required width of a table column
    public static int calculateColumnWidth(JTable table, int columnIndex) {
	int width = 0; // The return value
	int rowCount = table.getRowCount();

	for (int i = 0; i < rowCount; i++) {
	    TableCellRenderer renderer = table.getCellRenderer(i, columnIndex);
	    Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(i, columnIndex), false,
		    false, i, columnIndex);
	    int thisWidth = comp.getPreferredSize().width;
	    if (thisWidth > width) {
		width = thisWidth;
	    }
	}
	return width;
    }

    /**
     * Sets the column widths.
     * @param table the table
     * @param insets the insets
     * @param setMinimum the set minimum
     * @param setMaximum the set maximum
     */
    // Set the widths of every column in a table
    public static void setColumnWidths(JTable table, Insets insets, boolean setMinimum, boolean setMaximum) {
	int columnCount = table.getColumnCount();
	TableColumnModel tcm = table.getColumnModel();
	int spare = (insets == null ? 0 : insets.left + insets.right);

	for (int i = 0; i < columnCount; i++) {
	    int width = calculateColumnWidth(table, i);
	    width += spare;

	    TableColumn column = tcm.getColumn(i);
	    column.setPreferredWidth(width);
	    if (setMinimum == true) {
		column.setMinWidth(width);
	    }
	    if (setMaximum == true) {
		column.setMaxWidth(width);
	    }
	}
    }

    /**
     * Extract semester.
     * @param name the name
     * @return the string
     */
    public static String extractSemester(String name) {
	String result = name;
	String pattern = "([A-Za-z]*)\\d*_?\\d*";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(name);
	if (m.matches()) {
	    result = m.group(1);
	}
	return result;
    }

    /**
     * Extract year.
     * @param name the name
     * @return the string
     */
    public static String extractYear(String name) {
	String result = name;
	String pattern = "[A-Za-z]*(\\d*)_?\\d*";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(name);
	if (m.matches()) {
	    result = m.group(1);
	}
	return result;
    }

    /**
     * Extract section.
     * @param name the name
     * @return the string
     */
    public static String extractSection(String name) {
	String result = "01";
	String pattern = "[A-Za-z]*\\d*_?(\\d*)";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(name);
	if (m.matches()) {
	    result = m.group(1);
	}
	if (result.isEmpty()) {
	    result = "01";
	}
	return result;
    }
    
    /**
     * Construct a full name of first middle last
     * @param firstname
     * @param mi
     * @param lastname
     * @return fullname
     */
    public static String getFullName(String firstname, String mi, String lastname) {
	StringBuffer fullname = new StringBuffer();
/*	    
           if(lastname.equalsIgnoreCase("ANY")) {
		fullname.append("ANY");
		return fullname.toString();
	    }
*/
	    fullname.append(firstname.toUpperCase() + " ");
	    if(mi != null) {
		fullname.append(mi + ". ");
	    }
	    fullname.append(lastname.toUpperCase());
	 return fullname.toString();
    }
    
    /**
     * Break the person's full name into First, MI and Last.
     * @param fullname  Full Name
     * @return String array of {first, middle, last}
     */
    public static String[] parseName(String fullname) {
	String pattern = "([A-Za-z]*)[.]*\\s?([A-Za-z]*)[.]*\\s?([A-Za-z\\-]*)\\s*([, A-Za-z]*).*";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(fullname);
	String lastName = "";
	String firstName ="";
	String middleInitial = "";
	String[] name = new String[3];
	if (m.matches()) {
	    String fname = m.group(1);
	    String mlname = m.group(2);
	    String lname = m.group(3);
	    boolean ln_f = lname.isEmpty();
	    boolean fn_f = fname.isEmpty();
	    boolean mn_f = mlname.isEmpty();
	    
	    if(!ln_f){
		lastName = lname.toUpperCase();
	    } else if(mn_f) {
		lastName = fname.toUpperCase();  // Last and MI are empty
		fname = "";
		fn_f = true;
	    } else {
		lastName = mlname.toUpperCase();  // Last empty, but middle present
		mlname = "";
		mn_f = true;
	    }
	    if(!fn_f) {
		firstName = fname.toUpperCase();
	    }
	    if(!mn_f) {
		middleInitial = mlname.toUpperCase();
		middleInitial = middleInitial.substring(0,1);
	    }
	    // Final adjustment.  If first char of last name is a hyphen, combine mi and lname
	    if(lastName.substring(0, 1).equals("-")) {
		lastName = mlname + lname;
		middleInitial = "";
	    }
	    
	    if(!m.group(4).isEmpty()) {
		lastName += m.group(4).toUpperCase();
	    }
	    name[0] = firstName;
	    name[1] = middleInitial;
	    name[2] = lastName;
	}
	return name;
    }
    
    
    /** The Constant monthNames. */
    // Some Simple Date/Calendar utilities
    private static final String[] monthNames	 = { "January", "February", "March", "April", "May",
	    "June", "July", "August", "September", "October", "November", "December" };
   

    /**
     * Gets the month.
     * @param dt the dt
     * @return the month
     */
    public static String getMonth(Date dt) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(dt);
	int month = cal.get(Calendar.MONTH);
	return monthNames[month];
    }
    
    /**
     * Gets the month.
     *
     * @param m the m
     * @return the month
     */
    public static String getMonth(int m) {
	return monthNames[m - 1];
    }
    
    /**
     * Gets the year.
     *
     * @param dt the dt
     * @return the year
     */
    public static String getYear(Date dt) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(dt);
	int year = cal.get(Calendar.YEAR);
	return String.valueOf(year);
    }    
    
    /**
     * Gets the day.
     * @param dt the dt
     * @return the day
     */
    public static String getDay(Date dt) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(dt);
	int day = cal.get(Calendar.DAY_OF_MONTH);
	return String.valueOf(day);
    }
    
    /**
     * Get Today's Date object.
     * @return the date
     */
    public static Date Today() {
	Calendar cal = Calendar.getInstance(); 
	// set to midnight
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
    }
    
    /**
     * Get Today's String value.
     * @return the yesterday
     */
    public static String getToday() {
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DAY_OF_MONTH);
	return String.valueOf(day);	
    }
    
    /**
     * Get Yesterday's Date object.
     * @return the date
     */
    public static Date Yesterday() {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, -1); // number represents number of days	 
	// set to midnight
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
    }
    
    /**
     * Get Yesterday's String value.
     * @return the yesterday
     */
    public static String getYesterday() {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, -1);
	int day = cal.get(Calendar.DAY_OF_MONTH);
	return String.valueOf(day);
    }
    
    /**
     * Gets the hour.
     * @param dt the dt
     * @return the hour
     */
    public static String getHour(Date dt) {
 	Calendar cal = Calendar.getInstance();
 	cal.setTime(dt);
 	int hour = cal.get(Calendar.HOUR_OF_DAY);
 	return String.valueOf(hour);
     }

    /**
     * Gets the minute.
     * @param dt the dt
     * @return the minute
     */
    public static String getMinute(Date dt) {
 	Calendar cal = Calendar.getInstance();
 	cal.setTime(dt);
 	int minute = cal.get(Calendar.MINUTE);
 	return String.valueOf(minute);
     }
    
    /**
     * Gets the month index.
     * @param month the month
     * @return the month index
     */
    public static int getMonthIndex(String month) {
	int ix = 0;
	if(month.equals("*")) return ix;
	for(int m = 0; m < monthNames.length; m++) {
	    if(monthNames[m].equals(month)) {
		ix = m + 1;
		break;
	    }
	}
	return ix;
    }
    
    /**
     * Return a non-duplicative random integer array.
     * @param cap the number of values desired
     * @param origin the starting value (inclusive)
     * @param ending the ending value (exclusive)
     * @return the array of 'cap' non-duplicative random integer between origin and ending
     */
    public static Integer[]  dealArray(int cap, int origin, int ending) {
	ArrayList<Integer> alist = dealList(cap, origin, ending);
	Integer list[] = new Integer[alist.size()];
	list= alist.toArray(list);
	return list;
    }
    
    /**
     * Return a non-duplicative random integer list.
     * @param cap the number of values desired
     * @param origin the starting value (inclusive)
     * @param ending the ending value (exclusive)
     * @return the ArrayList of 'cap' non-duplicative random integer between origin and ending
     */
    public static ArrayList<Integer> dealList(int cap, int origin, int ending) {
	if(cap > (ending-origin)) throw new IllegalArgumentException("Capacity too big");
	Random r = new Random();
	IntStream integers = r.ints(origin, ending);
	Set<Integer> set = new HashSet<Integer>();
	Iterator<Integer> iter = integers.iterator();
	while(set.size() <= cap) {
	    int x = (int) iter.next();
	    set.add(x);
	}
	ArrayList<Integer> alist = new ArrayList<Integer>();
	for(Integer x: set) {
	    alist.add(x);
	}
	return alist;
    }
    
    /**
     *   Finds an action provided by the editor kit 
     *  @param JTextComponent 
     */
    protected static void createActionTable(JTextComponent textComponent) {
        actions = new Hashtable<Object, Action>();
        Action[] actionsArray = textComponent.getActions();

        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
    }

    /**
     *   Finds an action provided by the editor kit 
     *  @param String name
     */
    protected static Action getActionByName(String name) {
        return (actions.get(name));
    }

    /**
     *  Add a couple of emacs key bindings to the key map for navigation.   
     *  <PRE>
     *        Ctrl-b     Go backward one character
     *        Ctrl-f     Go forward one character
     *        Ctrl-p     Go up one line
     *        Ctrl-n     Go down one line
     *        Ctrl-c     Copy
     *        Ctrl-x     Cut
     *        Ctrl-v     Paste
     *        Ctrl-a     Beginning of Line
     *        Ctrl-e     End of Line
     *  </PRE>
     */
    public static void addKeymapBindings(JTextArea textArea) {
        //Add a new key map to the keymap hierarchy.
        Keymap keymap = JTextComponent.addKeymap("KeyBindings", textArea.getKeymap());

        //Ctrl-b to go backward one character
        Action action = getActionByName(DefaultEditorKit.backwardAction);
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);

        keymap.addActionForKeyStroke(key, action);

        //Ctrl-f to go forward one character
        action = getActionByName(DefaultEditorKit.forwardAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-p to go up one line
        action = getActionByName(DefaultEditorKit.upAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-n to go down one line
        action = getActionByName(DefaultEditorKit.downAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-c copy
        action = getActionByName(DefaultEditorKit.copyAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-x cut;  Ctrl-k  kill
        action = getActionByName(DefaultEditorKit.cutAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_K, Event.CTRL_MASK);  // emacs Kill-line
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-v paste; Ctrl-y  yank
        action = getActionByName(DefaultEditorKit.pasteAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);  // emacs Yank-line
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-a      CH05
        action = getActionByName(DefaultEditorKit.beginAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        //Ctrl-e      CH05
        action = getActionByName(DefaultEditorKit.endAction);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK);
        keymap.addActionForKeyStroke(key, action);

        textArea.setKeymap(keymap);
    }

    /**
     * Method to pad a string with specified character
     * @param inStr  - string to pad
     * @param maxLen - final length of string
     * @param pad    - character to pad with
     * @param left_f left_f - to the left or right?
     * @return String
     */
    private static String padIt(String inStr, int maxLen, String pad, boolean left_f) {
	int rem = 0;
	String outStr = "";
	if (inStr.length() < maxLen) {
	    rem = maxLen - inStr.length();
	    for (int i = 0; i < rem; i++) {
		outStr += pad;
	    }

	    if (left_f) {
		outStr = outStr + inStr;
	    } else {
		outStr = inStr + outStr;
	    }
	} else {
	    outStr = inStr;
	}
	return outStr;
    }

    /**
     * Method to pad a string with blanks on the left
     * @param inStr  - string to pad
     * @param maxLen - final length of string
     * @return String
     */
    public static String padString(String inStr, int maxLen) {
	String pad = " "; // CH82
	boolean left_f = true;
	return padIt(inStr, maxLen, pad, left_f);
    }

    /**
     * Method to pad a string with blanks
     * CH09
     * @param inStr  - string to pad
     * @param maxLen - final length of string
     * @param left_f left_f - to the left or right?
     * @return String
     */
    public static String padString(String inStr, int maxLen, boolean left_f) {
	String pad = " ";
	return padIt(inStr, maxLen, pad, left_f);
    }

    /**
     * Method to pad a string with specified character
     * @param inStr  - string to pad
     * @param maxLen - final length of string
     * @param pad    - character to pad with
     * @param left_f left_f - to the left or right?
     * @return String
     */
    public static String padString(String inStr, int maxLen, String pad, boolean left_f) {
	return padIt(inStr, maxLen, pad, left_f);
    }
}
