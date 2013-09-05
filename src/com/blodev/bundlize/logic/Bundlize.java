
/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Sagi Antebi (sagiantebi@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.blodev.bundlize.logic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.blodev.bundlize.annotations.BundlizeObject;
import com.blodev.bundlize.annotations.BundlizeProperty;
/**
 * A Utility class for creating Bundle or writing to Bundles any object which has the {@link BundlizeObject} and {@link BundlizeObject} Annotations.
 * @author Sagi Antebi
 *
 */
public class Bundlize {

	private static final String LOGCAT_TAG = "bundelize";
	
	/**
	 * Flattens the object into in a bundle, returning the bundle created.<br>
	 * The Object itself must have the {@link BundlizeObject} Annotation.<br>
	 * Each field in the target object's class must also have the {@link BundlizeProperty} Annotation. 
	 * @param target The {@link BundlizeObject} which needs bundling.
	 * @return A bundle representation of the target object, or null if either - <br>a.the object is null<br>b.the object doesn't have the {@link BundlizeObject} Annotation.
	 */
	public static Bundle bundle(Object target) {
		Bundle retVal = null;
		
		if (target == null || (target != null && !target.getClass().isAnnotationPresent(BundlizeObject.class))) {
			return retVal;
		}
		
		Field[] fields = getAnnotatedProperties(target);
		retVal = new Bundle();
		for (Field field : fields) {
			try {
				writeFieldToBundle(field, retVal, target);
			} catch (Exception e) {
				Log.e(LOGCAT_TAG, "Exception raised during field reflections",e);
			}
		}
		
		return retVal;
	}
	
	/**
	 * Reads a bundle created using {@link Bundlize} assigning values to the declared annotated fields.
	 * @param bundle A bundle map object
	 * @param target The object in which the field values will be assigned to.
	 * @return True on success, false if <b>any</b> field assignment failed.
	 */
	public static boolean readFromBundle(Bundle bundle, Object target) {
		boolean retVal = true;
		
		if (target == null || (target != null && !target.getClass().isAnnotationPresent(BundlizeObject.class))) {
			return false;
		}
		
		Field[] fields = getAnnotatedProperties(target);
		for (Field field : fields) {
			try {
				readFieldFromBundle(field, bundle, target);
			} catch (Exception e) {
				Log.e(LOGCAT_TAG, "Exception raised during field reflections",e);
				retVal = false;
			}
		}
		return retVal;
	}
	
	/**
	 * Get all Fields ({@link Field}) which has the {@link BundlizeProperty} annotation
	 * @param target the object to read the Fields from
	 * @return an array of {@link Field} objects
	 */
	
	private static Field[] getAnnotatedProperties(Object target) {
		List<Field> retVal = new ArrayList<Field>();
		Field[] fields = target.getClass().getDeclaredFields();
				
		for (Field field : fields) {
			
			if (field.isAnnotationPresent(BundlizeProperty.class)) {
				retVal.add(field);
			}
		}
		return retVal.toArray(new Field[retVal.size()]);
	}
	
	/**
	 * Writes an annotated field value to the target bundle, using the object's declared type
	 * @param field The field
	 * @param b The bundle to write into
	 * @param origin The Object containing the field.
	 * @throws IllegalArgumentException in case reflection fails.
	 * @throws IllegalAccessException in case reflection fails.
	 */
	private static void writeFieldToBundle(Field field, Bundle b, Object origin) throws IllegalArgumentException, IllegalAccessException {

		boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
		}
		
		
		String fName = field.getName();
		Object fValue = field.get(origin);
		Class<?> fType = field.getType();
		if (fType.equals(boolean.class) || fType.equals(Boolean.class)) {
			b.putBoolean(fName, (Boolean)fValue);
		} else if (fType.equals(int.class) || fType.equals(Integer.class)) {
			b.putInt(fName, (Integer) fValue);
		} else if (fType.equals(String.class)) {
			b.putString(fName, (String) fValue);
		} else if (fType.equals(long.class) || fType.equals(Long.class)) {
			b.putLong(fName, (Long) fValue);
		} else if (fType.equals(double.class) || (fType.equals(Double.class))) {
			b.putDouble(fName, (Double)fValue);
		} else if (fType.equals(float.class) || fType.equals(Float.class)) {
			b.putFloat(fName, (Float)fValue);
		} else if (fType.equals(byte.class) || fType.equals(Byte.class)) {
			b.putByte(fName, (Byte)fValue);
		} else if (fType.equals(char.class) || fType.equals(Character.class)) {
			b.putChar(fName, (Character)fValue);
		} else if (fType.equals(short.class) || fType.equals(Short.class)) {
			b.putShort(fName, (Short)fValue);
		} else if (fType.isArray()) {
			Class<?> fArrayClass = fType.getComponentType();
			if (fArrayClass.equals(boolean.class) || fArrayClass.equals(Boolean.class)) {
				b.putBooleanArray(fName, (boolean[])fValue);
			} else if (fArrayClass.equals(String.class)) {
				b.putStringArray(fName, (String[]) fValue);
			} else if (fArrayClass.equals(int.class) || fArrayClass.equals(Integer.class)) {
				b.putIntArray(fName, (int[])fValue);
			} else if (fArrayClass.equals(long.class) || fArrayClass.equals(Long.class)) {
				b.putLongArray(fName, (long[])fValue);
			} else if (fArrayClass.equals(double.class) || fArrayClass.equals(Double.class)) {
				b.putDoubleArray(fName, (double[])fValue);
			} else if (fArrayClass.equals(float.class) || fArrayClass.equals(Float.class)) {
				b.putFloatArray(fName, (float[])fValue);
			} else if (fArrayClass.equals(byte.class) || fArrayClass.equals(Byte.class)) {
				b.putByteArray(fName, (byte[])fValue);
			} else if (fArrayClass.equals(char.class) || fArrayClass.equals(Character.class)) {
				b.putCharArray(fName, (char[])fValue);
			} else if (fArrayClass.equals(short.class) || fArrayClass.equals(Short.class)) {
				b.putShortArray(fName, (short[])fValue);
			}	
		} else {
			Type[] fInterfaces = fType.getGenericInterfaces();
			//FIXME untested stub.
			if (typeContains(Serializable.class, fInterfaces)) {
				Log.d(LOGCAT_TAG, "Under construction");
			}
		}
		field.setAccessible(accessible);
	}
	
	/**
	 * Looks up an interface class in a type array.
	 * @param tInterface The interface class to look for
	 * @param tArray The type array containing all interfaces
 	 * @return true if tInterface is sfound in tArray
	 */
	private static boolean typeContains(Class<?> tInterface, Type[] tArray) {
		
		for (Type t : tArray) {
			if (t.getClass().equals(tInterface)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Reads a field from a bundle, assigning the field's value to itself.
	 * @param field The field to write into
	 * @param b The bundle to read from
	 * @param origin The object to assign the field value to.
	 * @throws IllegalArgumentException in case reflection fails.
	 * @throws IllegalAccessException in case reflection fails.
	 */
	private static void readFieldFromBundle(Field field, Bundle b, Object origin) throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
		}
		String fName = field.getName();
		field.set(origin, b.get(fName));
		field.setAccessible(accessible);
	}
	
}
