package com.rff.utilities;

import java.lang.reflect.*;
import java.util.*;

/**
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF">
 * <TD><B>PURPOSE</B></TD>
 * </TR>
 * </TABLE>
 * 
 * <p>
 *      Class extends Vector to allow sorted vectors
 * </p>
 *
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF">
 * <TD><B>MODIFICATIONS</B></TD>
 * </TR>
 * </TABLE>
 * 
 * <PRE>
 * </PRE>
 * @author JL Sowers 08/07/2003
 *
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF">
 * <TD><B>DESIGN NOTES:</B></TD>
 * </TR>
 * </TABLE>
 * 
 * <PRE>
 * </PRE>
 * @version $Revision: 1.2 $
 */
@SuppressWarnings("unchecked")
public class SortedVector<T> extends Vector<T> {
	private static final int	MAX_KEYS			= 3;
	private static final long	serialVersionUID	= 6555668632740853581L;
	protected String			alphaName;
	protected String			sortKey				= null;
	protected String[]			sortKeys			= null;

	/**
	 * Constructor creating vector pair and null symbolic names
	 */
	public SortedVector() {
		super();
		alphaName = null;
	}

	/**
	 * Constructor creating vector pair and null symbolic names
	 * @param capacity int value of vector(s) capacity
	 */
	public SortedVector(int capacity) {
		super(capacity);
		alphaName = null;
	}

	/**
	 * Constructor creating vector pair and symbolic names. The symbolic names are set from the parameters.
	 * @param capacity int value of vector(s) capacity
	 * @param aName primaryName
	 */
	public SortedVector(int capacity, String aName) {
		super(capacity);
		alphaName = aName;
	}

	/**
	 * Constructor creating vector pair and null symbolic names. Define a sort key field
	 */
    public SortedVector(String sortkey) {
	super();
	alphaName = null;
	sortKey = sortkey;
    }

    public final synchronized void addString(Object obj) {
	int i = 0;
	while ((i < size()) && (((String) obj).compareTo((String) elementAt(i)) > 0))
	    i++;
	insertElementAt((T) obj, i);
    }

    @Override
    public boolean equals(Object o) {
	return super.equals(o);
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Adds an element according to internal sortKey (ascending order)
	 * @param a_obj Object controlling sort order
	 */
	public boolean add(Object a_obj) {
		add(a_obj, sortKey);
		return true;
	}

	/**
	 * Adds an element according to sortkey (ascending order)
	 * @param a_obj Object controlling sort order
	 * @param sortkey String field to sort on
	 */
	public void add(Object a_obj, String sortkey) {
		int i = 0;
		setSortKey(sortkey);
		while((i < size()) && (compare(a_obj, (Object) elementAt(i), sortKey) < 0)) {
			i++;
		}
		insertElementAt((T) a_obj, i);
	}

	/**
	 * Adds an element according to sortkeys (ascending order)
	 * @param a_obj Object controlling sort order
	 * @param sortkeys String[] fields to sort on
	 */
	public void add(Object a_obj, String[] sortkeys) {
		int i = 0;
		boolean done_f = false;
		int sortLevel = sortKeys.length;
		setSortKeys(sortkeys);

		while((i < size()) && !done_f) {
			int res0 = compare(a_obj, (Object) elementAt(i), sortKeys[0]);

			if(res0 > 0) {
				i++;
			} else {

				if(res0 == 0) {

					if(sortLevel == 1) {
						i++;
					} else {
						int res1 = compare(a_obj, (Object) elementAt(i), sortKeys[1]);

						if(res1 > 0) {
							i++;
						} else {

							if(res1 == 0) {

								if(sortLevel == 2) {
									i++;
								} else {
									int res2 = compare(a_obj, (Object) elementAt(i), sortKeys[2]);

									if(res2 >= 0) {
										i++;
									} else {
										done_f = true;
									}
								}
							} else {
								done_f = true;
							}
						}
					}
				} else {
					done_f = true;
				}
			}
		}
		insertElementAt((T) a_obj, i);
	}

	private int compare(Object a, Object b) {
		if((sortKey == null) && (sortKeys != null)) {
			sortKey = sortKeys[0];
		}
		return (sortKey == null) ? 0 : compare(a, b, sortKey);
	}

	/**
	 * Compares two objects based on an internal field.
	 * @param a Object
	 * @param b Object
	 * @param field name of field to use for comparison
	 * @return value showing result of compare: 0 = equal, -1 = a < b, +1 = a > b
	 */
	@SuppressWarnings("rawtypes")
	private int compare(Object a, Object b, String field) {
		int return_value = 0;

		// Check to make sure the objects are the same
		// Probably should invent an exception to throw if not.
		if(a.getClass() != b.getClass()) {

			return return_value;
		}

		// Determine if the field is primitive (int, short, float, etc)
		try {
			Field f_a = a.getClass().getDeclaredField(field);
			Field f_b = b.getClass().getDeclaredField(field);

			if(isFieldPrimitive(a, field)) {
				String type = f_a.getType().getName();
				if(type.equals("int")) {
					return_value = (f_a.getInt(a) > f_b.getInt(b)) ? 1 : ((f_a.getInt(a) < f_b.getInt(b)) ? -1 : 0);
				} else if(type.equals("short")) {
					return_value = (f_a.getShort(a) > f_b.getShort(b)) ? 1 : ((f_a.getShort(a) < f_b.getShort(b)) ? -1 : 0);
				} else if(type.equals("long")) {
					return_value = (f_a.getLong(a) > f_b.getLong(b)) ? 1 : ((f_a.getLong(a) < f_b.getLong(b)) ? -1 : 0);
				} else if(type.equals("char")) {
					return_value = (f_a.getChar(a) > f_b.getChar(b)) ? 1 : ((f_a.getChar(a) < f_b.getChar(b)) ? -1 : 0);
				} else if(type.equals("double")) {
					return_value = (f_a.getDouble(a) > f_b.getDouble(b)) ? 1 : ((f_a.getDouble(a) < f_b.getDouble(b)) ? -1 : 0);
				} else if(type.equals("float")) {
					return_value = (f_a.getFloat(a) > f_b.getFloat(b)) ? 1 : ((f_a.getFloat(a) > f_b.getFloat(b)) ? -1 : 0);
				} else if(type.equals("byte")) {
					return_value = (f_a.getByte(a) > f_b.getByte(b)) ? 1 : ((f_a.getByte(a) > f_b.getByte(b)) ? -1 : 0);
				}
			} else if(isCompareToAvailable(a, field)) {
				Class[] ptypes = { (new Object()).getClass() };

				try {
					Method m = f_a.getType().getMethod("compareTo", ptypes);
					Object[] args = { f_b.get(b) };
					return_value = ((Integer) m.invoke(f_a.get(a), args)).intValue();
				} catch(NoSuchMethodException nsme) {
					// Library.rffLogger.log(Level.SEVERE, "No such Method");
				} catch(SecurityException se) {
					// Library.rffLogger.log(Level.SEVERE, se.getMessage());
				}
			}
		} catch(IllegalArgumentException e1) {
			// Library.rffLogger.log(Level.FINE, e1.getMessage());
		} catch(InvocationTargetException e2) {
			// Library.rffLogger.log(Level.FINE, e2.getMessage());
		} catch(IllegalAccessException e3) {
			// Library.rffLogger.log(Level.FINE, e3.getMessage());
		} catch(NoSuchFieldException e4) {
			// Library.rffLogger.log(Level.FINE, e4.getMessage());
		}
		return return_value;
	}

	/**
	 * Determines if a compareTo method is available for the class for this field type.
	 * @param object Object to be checked
	 * @param field String name
	 * @return boolean true if a compareTo method is declared
	 */
	@SuppressWarnings("rawtypes")
	public boolean isCompareToAvailable(Object object, String field) {
		boolean result_f = false;
		Field f = null;
		if(!isFieldPrimitive(object, field)) {
			try {
				f = object.getClass().getDeclaredField(field);
			} catch(NoSuchFieldException nsfe) {
				System.out.println("Exception:" + object.getClass().getName() + " does not have a field named " + field);
			} catch(SecurityException se) {
				System.out.println(se);
			}
			if(f != null) {
				Class[] ptypes = { (new Object()).getClass() };
				try {
					f.getType().getMethod("compareTo", ptypes);
					result_f = true;
				} catch(NoSuchMethodException nsme) {
					// Library.rffLogger.log(Level.SEVERE, "No such Method");
				} catch(SecurityException se) {
					// Library.rffLogger.log(Level.SEVERE, "Security Problem");
					System.out.println(se);
				}
			}
		}
		return result_f;
	}

	/**
	 * Determines if the field is an int, byte, boolean, long, short, float or double.
	 * @param object Object to be checked
	 * @param field String name
	 * @return boolean true if field is a primitive type
	 */
	public boolean isFieldPrimitive(Object object, String field) {
		boolean result_f = false;

		try {
			Field f = object.getClass().getDeclaredField(field);
			result_f = f.getType().isPrimitive();
		} catch(NoSuchFieldException nsfe) {
			System.out.println("Exception:" + object.getClass().getName() + " does not have a field named " + field);
		} catch(SecurityException se) {
			System.out.println(se);
		}
		return result_f;
	}

	/**
	 * Utility method returning the numeric indicator corresponding to the symbolic name. Returns -1 if the name is not found.
	 * @param name symbolic name
	 * @return 0 if primary, -1 if null or not found
	 */
	protected int nameIX(String name) {
		if(name == null) return -1;
		if(name.equals(alphaName)) return 0;
		return -1;
	}

	/**
	 * Replaces an object. Uses an existing object in the vector indicated by the first parameter to find which pair will be
	 * replaced. Overrides replace method in VectorPair by checking sort key to see if the replacement will cause the vectors to
	 * be out of order. If so, then resort will be triggered. CH01
	 * @param old_obj Object existing object to be used as a key
	 * @param a_obj Object replacement primary object
	 */
	public void replace(Object old_obj, Object a_obj) {
		int cmpResult = -1;
		int ix = indexOf(old_obj);
		setElementAt((T) a_obj, ix);
		cmpResult = compare(old_obj, a_obj);
		if(cmpResult != 0) {
			resort(sortKeys);
		}
	}

	/**
	 * Reorder (resort) the array by a rather brute force method. Simply clone the original, clear the vector and add the
	 * elements back allowing the insertion sort to do the job
	 */
	public void resort(String sortkey) {
		Vector<T> alpha_clone = (Vector<T>) this.clone();
		setSortKey(sortkey);
		clear();

		for(int i = 0; i < alpha_clone.size(); i++) {
			add(alpha_clone.elementAt(i), sortKey);
		}
	}

	/**
	 * Resort using multiple keys.
	 */
	public void resort(String[] sortkeys) {
		Vector<T> alpha_clone = (Vector<T>) this.clone();
		setSortKeys(sortkeys);
		clear();
		for(int i = 0; i < alpha_clone.size(); i++) {
			add(alpha_clone.elementAt(i), sortKeys);
		}
	}

	/**
	 * Sets the value of the sort key field
	 * @param sortkey name of field to sort on.
	 */
	public void setSortKey(String sortkey) {
		this.sortKey = sortkey;
	}

	/**
	 * Sets the values of the sort key fields array entry 0 is primary, entry 1 is secondary
	 * @param sortkeys
	 */
	public void setSortKeys(String[] sortkeys) {
		if(sortkeys != null) {
			int len = (sortkeys.length > MAX_KEYS) ? MAX_KEYS : sortkeys.length;
			this.sortKeys = new String[len];

			for(int i = 0; i < len; i++) {
				this.sortKeys[i] = sortkeys[i];
			}
		}
	}

	public String toString() {
		return super.toString();
	}
	
	public static void main(String[] args) {
		int[] inpt = {0,8,7,6,5,4,3,2,1,2};
		SortedVector<Integer> test = new SortedVector<Integer>(10,"name");
		test.setSortKey("name");
		for(int i = 0; i < inpt.length; i++) {
			test.add(inpt[i],"name");
		}
		System.out.println(test.toString());
	}
	
}
