/**************************************************************************
 * UserCriterias.java, giveastick Android
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

import com.iia.giveastick.entity.User;

/**
 * UserCriterias.
 *
 * This class can help you forge requests for your User Entity.
 * For more details, see CriteriasBase.
 */
public class UserCriterias extends CriteriasBase<User> {
	/** String to parcel userCriteria. */
	public static final String PARCELABLE =
			"userCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public UserCriterias(final GroupType type) {
		super(type);
	}
}
