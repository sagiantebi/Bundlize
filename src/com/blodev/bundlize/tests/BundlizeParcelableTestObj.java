package com.blodev.bundlize.tests;

import android.os.Parcel;
import android.os.Parcelable;

public class BundlizeParcelableTestObj implements Parcelable {

	private String mString = null;
	
	public BundlizeParcelableTestObj(String str) {
		mString = str;
	}
	
	public BundlizeParcelableTestObj(Parcel in) {
		mString = in.readString();
	}
	
	public String getString() {
		return mString;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mString);
	}
	
	public static Parcelable.Creator<BundlizeParcelableTestObj> CREATOR = new Parcelable.Creator<BundlizeParcelableTestObj>() {

		@Override
		public BundlizeParcelableTestObj createFromParcel(Parcel source) {
			return new BundlizeParcelableTestObj(source);
		}

		@Override
		public BundlizeParcelableTestObj[] newArray(int size) {
			return new BundlizeParcelableTestObj[size];
		} 
		
	};

}
