/**
 * 
 */
package com.iia.giveastick.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;

/**
 * @author maxime
 *
 */
@Entity
public class UserGroup  implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"UserGroup";


	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int id;
	
	@Column
	private String tag;
	
	@Column(nullable=true)
	@OneToMany
	private ArrayList<User> users;
	
	@Column(nullable=true)
	@OneToMany
	private ArrayList<VoteForfeit> voteForfeits;

	/**
	 * Default constructor.
	 */
	public UserGroup() {

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
	 * @return the tag
	 */
	public String getTag() {
	     return this.tag;
	}

	/**
	 * @param value the tag to set
	 */
	public void setTag(final String value) {
	     this.tag = value;
	}

	/**
	 * @return the users
	 */
	public ArrayList<User> getUsers() {
	     return this.users;
	}

	/**
	 * @param value the users to set
	 */
	public void setUsers(final ArrayList<User> value) {
	     this.users = value;
	}

	/**
	 * @return the voteForfeits
	 */
	public ArrayList<VoteForfeit> getVoteForfeits() {
	     return this.voteForfeits;
	}

	/**
	 * @param value the voteForfeits to set
	 */
	public void setVoteForfeits(final ArrayList<VoteForfeit> value) {
	     this.voteForfeits = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getTag());

		if (this.getUsers() != null) {
			dest.writeInt(this.getUsers().size());
			for (User item : this.getUsers()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}

		if (this.getVoteForfeits() != null) {
			dest.writeInt(this.getVoteForfeits().size());
			for (VoteForfeit item : this.getVoteForfeits()) {
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
		this.setTag(parc.readString());

		int nbUsers = parc.readInt();
		if (nbUsers > -1) {
			ArrayList<User> items =
				new ArrayList<User>();
			for (int i = 0; i < nbUsers; i++) {
				items.add((User) parc.readParcelable(
						User.class.getClassLoader()));
			}
			this.setUsers(items);
		}

		int nbVoteForfeits = parc.readInt();
		if (nbVoteForfeits > -1) {
			ArrayList<VoteForfeit> items =
				new ArrayList<VoteForfeit>();
			for (int i = 0; i < nbVoteForfeits; i++) {
				items.add((VoteForfeit) parc.readParcelable(
						VoteForfeit.class.getClassLoader()));
			}
			this.setVoteForfeits(items);
		}
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public UserGroup(Parcel parc) {
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
	public static final Parcelable.Creator<UserGroup> CREATOR
	    = new Parcelable.Creator<UserGroup>() {
		public UserGroup createFromParcel(Parcel in) {
		    return new UserGroup(in);
		}
		
		public UserGroup[] newArray(int size) {
		    return new UserGroup[size];
		}
	};

}
