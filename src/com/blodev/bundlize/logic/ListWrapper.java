package com.blodev.bundlize.logic;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/*package*/ class ListWrapper implements Parcelable {

	private List<?> mForiegnList;
	
	/*package*/ ListWrapper(List<?> list) {
		mForiegnList = list;
	}
	
	protected ListWrapper(Parcel in) {
		in.readList(mForiegnList, Bundlize.getClassLoader());
	}
	
	public List<?> getValue() {
		return mForiegnList;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(mForiegnList);
	}

	
	protected Parcelable.Creator<ListWrapper> CREATOR = new Parcelable.Creator<ListWrapper>() {

		@Override
		public ListWrapper createFromParcel(Parcel source) {
			return new ListWrapper(source);
		}

		@Override
		public ListWrapper[] newArray(int size) {
			return new ListWrapper[size];
		}
	};
}
