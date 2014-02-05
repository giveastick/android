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
public class User implements Serializable , Parcelable {

	/** Key Constant for parcelable/serialization. */
	public static final String PARCEL = 
			"User";

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int id;
	
	@Column(type = Type.LOGIN)
	private String email;
	
	@Column(type = Type.PASSWORD)
	private String password;
	
	@Column
	private String nickname;
	
	@Column(type = Type.DATE, locale = true, hidden=true)
	private DateTime created;
	
	@Column(nullable = true, hidden = true)
	private String androidGcmaToken;
	
	@ManyToOne
	private UserGroup userGroup;
	
	@OneToMany
	private ArrayList<VoteStick> voteSticks;
	
	@OneToMany(mappedBy = "giver")
	private ArrayList<Stick> givenSticks;
	
	@OneToMany(mappedBy = "receiver")
	private ArrayList<Stick> receivedSticks;


	/**
	 * Default constructor.
	 */
	public User() {

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
	 * @return the email
	 */
	public String getEmail() {
	     return this.email;
	}

	/**
	 * @param value the email to set
	 */
	public void setEmail(final String value) {
	     this.email = value;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
	     return this.password;
	}

	/**
	 * @param value the password to set
	 */
	public void setPassword(final String value) {
	     this.password = value;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
	     return this.nickname;
	}

	/**
	 * @param value the nickname to set
	 */
	public void setNickname(final String value) {
	     this.nickname = value;
	}

	/**
	 * @return the created
	 */
	public DateTime getCreated() {
	     return this.created;
	}

	/**
	 * @param value the created to set
	 */
	public void setCreated(final DateTime value) {
	     this.created = value;
	}

	/**
	 * @return the androidGcmaToken
	 */
	public String getAndroidGcmaToken() {
	     return this.androidGcmaToken;
	}

	/**
	 * @param value the androidGcmaToken to set
	 */
	public void setAndroidGcmaToken(final String value) {
	     this.androidGcmaToken = value;
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
	 * @return the givenSticks
	 */
	public ArrayList<Stick> getGivenSticks() {
	     return this.givenSticks;
	}

	/**
	 * @param value the givenSticks to set
	 */
	public void setGivenSticks(final ArrayList<Stick> value) {
	     this.givenSticks = value;
	}

	/**
	 * @return the receivedSticks
	 */
	public ArrayList<Stick> getReceivedSticks() {
	     return this.receivedSticks;
	}

	/**
	 * @param value the receivedSticks to set
	 */
	public void setReceivedSticks(final ArrayList<Stick> value) {
	     this.receivedSticks = value;
	}

	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getEmail());
		dest.writeString(this.getPassword());
		dest.writeString(this.getNickname());
		if (this.getCreated() != null) {
			dest.writeInt(1);
			dest.writeString(this.getCreated().toString());
		} else {
			dest.writeInt(0);
		}
		dest.writeString(this.getAndroidGcmaToken());

		dest.writeParcelable(this.getUserGroup(), flags);

		if (this.getVoteSticks() != null) {
			dest.writeInt(this.getVoteSticks().size());
			for (VoteStick item : this.getVoteSticks()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}

		if (this.getGivenSticks() != null) {
			dest.writeInt(this.getGivenSticks().size());
			for (Stick item : this.getGivenSticks()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}

		if (this.getReceivedSticks() != null) {
			dest.writeInt(this.getReceivedSticks().size());
			for (Stick item : this.getReceivedSticks()) {
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
		this.setEmail(parc.readString());
		this.setPassword(parc.readString());
		this.setNickname(parc.readString());
		if (parc.readInt() == 1) {
			this.setCreated(new DateTime(parc.readString()));
		}
		this.setAndroidGcmaToken(parc.readString());

		this.setUserGroup((UserGroup) parc.readParcelable(UserGroup.class.getClassLoader()));

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

		int nbGivenSticks = parc.readInt();
		if (nbGivenSticks > -1) {
			ArrayList<Stick> items =
				new ArrayList<Stick>();
			for (int i = 0; i < nbGivenSticks; i++) {
				items.add((Stick) parc.readParcelable(
						Stick.class.getClassLoader()));
			}
			this.setGivenSticks(items);
		}

		int nbReceivedSticks = parc.readInt();
		if (nbReceivedSticks > -1) {
			ArrayList<Stick> items =
				new ArrayList<Stick>();
			for (int i = 0; i < nbReceivedSticks; i++) {
				items.add((Stick) parc.readParcelable(
						Stick.class.getClassLoader()));
			}
			this.setReceivedSticks(items);
		}
	}

	/**
	 * Parcel Constructor.
	 *
	 * @param parc The parcel to read from
	 */
	public User(Parcel parc) {
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
	public static final Parcelable.Creator<User> CREATOR
	    = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
		    return new User(in);
		}
		
		public User[] newArray(int size) {
		    return new User[size];
		}
	};

}
