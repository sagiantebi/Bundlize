
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


package com.blodev.bundlize.tests;

import com.blodev.bundlize.annotations.BundlizeObject;
import com.blodev.bundlize.annotations.BundlizeProperty;

/**
 * A Sample object for running the test case.
 * @author Sagi Antebi
 *
 */
@BundlizeObject
public class BundlizeTestObject {
	
	public static final int ARRAY_LENGTH = 10;
	
	@BundlizeProperty
	private int mInt = -1;

	@BundlizeProperty
	private String mString = null;

	@BundlizeProperty
	private long mLong = -1;
	
	@BundlizeProperty
	private String[] mStrings = null;
	
	@BundlizeProperty
	private int[] mInts = null;
	
	@BundlizeProperty
	private long[] mLongs = null;
	
	
	public BundlizeTestObject() {
		mInt = (int) (Math.random() * Integer.MAX_VALUE);
		mLong = (long) (Math.random() * Long.MAX_VALUE);
		mString = getRandomString();
		mStrings = new String[ARRAY_LENGTH];
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			mStrings[i] = getRandomString();
		}
		mInts = new int[ARRAY_LENGTH];
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			mInts[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}
		mLongs = new long[ARRAY_LENGTH];
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			mLongs[i] = (long) (Math.random() * Long.MAX_VALUE);
		}
	}
	
	public int getInt() {
		return mInt;
	}
	
	public String getString() {
		return mString;
	}
	
	public long getLong() {
		return mLong;
	}
	
	public String[] getStrings() {
		return mStrings;
	}
	
	public int[] getInts() {
		return mInts;
	}
	
	public long[] getLongs() {
		return mLongs;
	}
	
	private String getRandomString() {
		String retVal = "";
		
		for (int i = 0; i < 256; i++) {
			char c = (char) (Math.random()*255);
			retVal = retVal.concat(String.valueOf(c));
		}
		
		return retVal;
	}
	
	

}
