/**
 * 
 */
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

/**
 * @author maxime
 *
 */
@Entity
public class VoteStick  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"VoteStick";


	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int id;
	
	@Column(type = Type.BOOLEAN)
	private boolean answer;
	
	@Column(type = Type.DATE, locale = true, hidden=true)
	private DateTime datetime;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Stick stick;

	/**
	 * Default constructor.
	 */
	public VoteStick() {

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
	 * @return the answer
	 */
	public boolean isAnswer() {
	     return this.answer;
	}

	/**
	 * @param value the answer to set
	 */
	public void setAnswer(final boolean value) {
	     this.answer = value;
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
	 * @return the user
	 */
	public User getUser() {
	     return this.user;
	}

	/**
	 * @param value the user to set
	 */
	public void setUser(final User value) {
	     this.user = value;
	}

	/**
	 * @return the stick
	 */
	public Stick getStick() {
	     return this.stick;
	}

	/**
	 * @param value the stick to set
	 */
	public void setStick(final Stick value) {
	     this.stick = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());

		if (this.isAnswer()) {
			dest.writeInt(1);
		} else {
			dest.writeInt(0);
		}
		if (this.getDatetime() != null) {
			dest.writeInt(1);
			dest.writeString(this.getDatetime().toString());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getUser(), flags);

		dest.writeParcelable(this.getStick(), flags);
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
		this.setAnswer(parc.readInt() == 1);
		if (parc.readInt() == 1) {
			this.setDatetime(new DateTime(parc.readString()));
		}

		this.setUser((User) parc.readParcelable(User.class.getClassLoader()));

		this.setStick((Stick) parc.readParcelable(Stick.class.getClassLoader()));
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public VoteStick(Parcel parc) {
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
	public static final Parcelable.Creator<VoteStick> CREATOR
	    = new Parcelable.Creator<VoteStick>() {
		public VoteStick createFromParcel(Parcel in) {
		    return new VoteStick(in);
		}
		
		public VoteStick[] newArray(int size) {
		    return new VoteStick[size];
		}
	};

}
