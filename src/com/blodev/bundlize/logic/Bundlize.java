
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.blodev.bundlize.annotations.BundlizeProperty;

public class Bundlize {

	private static final String LOGCAT_TAG = "bundelize";
	
	
	public static Bundle bundle(Object target) {
		Bundle retVal = new Bundle();
		
		if (target == null) {
			return retVal;
		}
		Field[] fields = getAnnotatedProperties(target);
		
		for (Field field : fields) {
			try {
				writeBundledFields(field, retVal, target);
			} catch (Exception e) {
				Log.e(LOGCAT_TAG, "Exception raised during field reflections",e);
			}
		}
		
		return retVal;
	}
	
	
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
	
	
	private static void writeBundledFields(Field field, Bundle b, Object origin) throws IllegalArgumentException, IllegalAccessException {

		boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
		}
		
		String fName = field.getName();
		Object fValue = field.get(origin);
		if (fValue instanceof Integer) {
			b.putInt(fName, (Integer) fValue);
		} else if (fValue instanceof String) {
			b.putString(fName, (String) fValue);
		} else if (fValue instanceof Long) {
			b.putLong(fName, (Long) fValue);
		}

		field.setAccessible(accessible);
		
	}
	
}
