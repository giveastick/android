/**
 * 
 */
package com.iia.giveastick.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;

/**
 * @author maxime
 *
 */
@Entity
public class Stick  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"Stick";

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int id;
	
	@Column(type = Type.DATE, locale = true, hidden=true)
	private DateTime given;
	
	@Column(type = Type.DATE, locale = true, hidden=true)
	private DateTime pulledoff;
	
	@ManyToOne(inversedBy = "givenSticks")
	private User giver;
	
	@ManyToOne(inversedBy = "receivedSticks")
	private User receiver;
	
	@OneToMany
	private ArrayList<VoteStick> voteSticks;

	/**
	 * Default constructor.
	 */
	public Stick() {

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
	 * @return the given
	 */
	public DateTime getGiven() {
	     return this.given;
	}

	/**
	 * @param value the given to set
	 */
	public void setGiven(final DateTime value) {
	     this.given = value;
	}

	/**
	 * @return the pulledoff
	 */
	public DateTime getPulledoff() {
	     return this.pulledoff;
	}

	/**
	 * @param value the pulledoff to set
	 */
	public void setPulledoff(final DateTime value) {
	     this.pulledoff = value;
	}

	/**
	 * @return the giver
	 */
	public User getGiver() {
	     return this.giver;
	}

	/**
	 * @param value the giver to set
	 */
	public void setGiver(final User value) {
	     this.giver = value;
	}

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
	     return this.receiver;
	}

	/**
	 * @param value the receiver to set
	 */
	public void setReceiver(final User value) {
	     this.receiver = value;
	}

	/**
	 * @return the voteSticks
	 */
	public ArrayList<VoteStick> getVoteSticks() {
	     return this.voteSticks;
	}

	/**
	 * @param value the voteSticks to set
	 */
	public void setVoteSticks(final ArrayList<VoteStick> value) {
	     this.voteSticks = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		if (this.getGiven() != null) {
			dest.writeInt(1);
			dest.writeString(this.getGiven().toString());
		} else {
			dest.writeInt(0);
		}
		if (this.getPulledoff() != null) {
			dest.writeInt(1);
			dest.writeString(this.getPulledoff().toString());
		} else {
			dest.writeInt(0);
		}

		dest.writeParcelable(this.getGiver(), flags);

		dest.writeParcelable(this.getReceiver(), flags);

		if (this.getVoteSticks() != null) {
			dest.writeInt(this.getVoteSticks().size());
			for (VoteStick item : this.getVoteSticks()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}
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
			this.setGiven(new DateTime(parc.readString()));
		}
		if (parc.readInt() == 1) {
			this.setPulledoff(new DateTime(parc.readString()));
		}

		this.setGiver((User) parc.readParcelable(User.class.getClassLoader()));

		this.setReceiver((User) parc.readParcelable(User.class.getClassLoader()));

		int nbVoteSticks = parc.readInt();
		if (nbVoteSticks > -1) {
			ArrayList<VoteStick> items =
				new ArrayList<VoteStick>();
			for (int i = 0; i < nbVoteSticks; i++) {
				items.add((VoteStick) parc.readParcelable(
						VoteStick.class.getClassLoader()));
			}
			this.setVoteSticks(items);
		}
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public Stick(Parcel parc) {
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
	public static final Parcelable.Creator<Stick> CREATOR
	    = new Parcelable.Creator<Stick>() {
		public Stick createFromParcel(Parcel in) {
		    return new Stick(in);
		}
		
		public Stick[] newArray(int size) {
		    return new Stick[size];
		}
	};

}
