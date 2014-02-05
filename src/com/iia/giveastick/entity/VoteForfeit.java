package com.iia.giveastick.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class VoteForfeit  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"VoteForfeit";


	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int id;
	
	@Column(type = Type.DATE, locale = true, hidden=true)
	private DateTime datetime;
	
	@ManyToOne
	private UserGroup userGroup;

	/**
	 * Default constructor.
	 */
	public VoteForfeit() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
	     return this.id;
	}

	/**
	 * @param value the id to set
	 */
	public void setId(final int value) {
	     this.id = value;
	}

	/**
	 * @return the datetime
	 */
	public DateTime getDatetime() {
	     return this.datetime;
	}

	/**
	 * @param value the datetime to set
	 */
	public void setDatetime(final DateTime value) {
	     this.datetime = value;
	}

	/**
	 * @return the userGroup
	 */
	public UserGroup getUserGroup() {
	     return this.userGroup;
	}

	/**
	 * @param value the userGroup to set
	 */
	public void setUserGroup(final UserGroup value) {
	     this.userGroup = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		if (this.getDatetime() != null) {
			dest.writeInt(1);
			dest.writeString(this.getDatetime().toString());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getUserGroup(), flags);
	}

	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		this.setId(parc.readInt());
		if (parc.readInt() == 1) {
			this.setDatetime(new DateTime(parc.readString()));
		}

		this.setUserGroup((UserGroup) parc.readParcelable(UserGroup.class.getClassLoader()));
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public VoteForfeit(Parcel parc) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.readFromParcel(parc);

		// You can  implement your own parcel mechanics here.

	}

	/* This method is not regenerated. You can implement your own parcel mechanics here. */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// You can chose not to use harmony's generated parcel.
		// To do this, remove this line.
		this.writeToParcelRegen(dest, flags);

		// You can  implement your own parcel mechanics here.
	}

	@Override
	public int describeContents() {
		// This should return 0 
		// or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
		return 0;
	}

	/**
	 * Parcelable creator.
	 */
	public static final Parcelable.Creator<VoteForfeit> CREATOR
	    = new Parcelable.Creator<VoteForfeit>() {
		public VoteForfeit createFromParcel(Parcel in) {
		    return new VoteForfeit(in);
		}
		
		public VoteForfeit[] newArray(int size) {
		    return new VoteForfeit[size];
		}
	};

}
