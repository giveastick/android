/**************************************************************************
 * VoteStickCriterias.java, giveastick Android
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

import com.iia.giveastick.entity.VoteStick;

/**
 * VoteStickCriterias.
 *
 * This class can help you forge requests for your VoteStick Entity.
 * For more details, see CriteriasBase.
 */
public class VoteStickCriterias extends CriteriasBase<VoteStick> {
	/** String to parcel voteStickCriteria. */
	public static final String PARCELABLE =
			"voteStickCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public VoteStickCriterias(final GroupType type) {
		super(type);
	}
}
