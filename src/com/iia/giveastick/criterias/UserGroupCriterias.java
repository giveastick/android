/**************************************************************************
 * UserGroupCriterias.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 5, 2014
 *
 **************************************************************************/
package com.iia.giveastick.criterias;

import com.iia.giveastick.criterias.base.CriteriasBase;
import com.iia.giveastick.criterias.base.Criteria;

import com.iia.giveastick.entity.UserGroup;

/**
 * UserGroupCriterias.
 *
 * This class can help you forge requests for your UserGroup Entity.
 * For more details, see CriteriasBase.
 */
public class UserGroupCriterias extends CriteriasBase<UserGroup> {
	/** String to parcel userGroupCriteria. */
	public static final String PARCELABLE =
			"userGroupCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public UserGroupCriterias(final GroupType type) {
		super(type);
	}
}
