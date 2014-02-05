/**************************************************************************
 * VoteForfeitCriterias.java, giveastick Android
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

import com.iia.giveastick.entity.VoteForfeit;

/**
 * VoteForfeitCriterias.
 *
 * This class can help you forge requests for your VoteForfeit Entity.
 * For more details, see CriteriasBase.
 */
public class VoteForfeitCriterias extends CriteriasBase<VoteForfeit> {
	/** String to parcel voteForfeitCriteria. */
	public static final String PARCELABLE =
			"voteForfeitCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public VoteForfeitCriterias(final GroupType type) {
		super(type);
	}
}
