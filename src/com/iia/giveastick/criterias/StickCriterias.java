/**************************************************************************
 * StickCriterias.java, giveastick Android
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

import com.iia.giveastick.entity.Stick;

/**
 * StickCriterias.
 *
 * This class can help you forge requests for your Stick Entity.
 * For more details, see CriteriasBase.
 */
public class StickCriterias extends CriteriasBase<Stick> {
	/** String to parcel stickCriteria. */
	public static final String PARCELABLE =
			"stickCriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public StickCriterias(final GroupType type) {
		super(type);
	}
}
