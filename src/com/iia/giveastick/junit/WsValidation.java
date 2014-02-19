package com.iia.giveastick.junit;

import junit.framework.TestCase;

import com.iia.giveastick.util.GetWsJson;
import com.iia.giveastick.util.RestClient.Verb;

public class WsValidation extends TestCase {

	public WsValidation(String name) {
		super(name);
	}
	
	/**
	 * Tests the connectivity
	 * 
	 * @author Maxime Lebastard
	 */
	public void connectivityTest(){
		try{                                                                                                                                                   			
			String stringResult = new GetWsJson("/ping", null, Verb.GET).getString();
			assertEquals("{\"android\":{\"success\":true,\"data\":{\"pong\":\"pong\"}}}", stringResult);
		}
		catch(Exception e)
		{}
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

}
