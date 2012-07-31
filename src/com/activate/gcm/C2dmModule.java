/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
 
package com.activate.gcm;

import org.appcelerator.kroll.KrollInvocation;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.util.Log;
import java.util.HashMap;
import org.appcelerator.kroll.KrollFunction;

import com.google.android.gcm.GCMRegistrar;

import android.app.AlertDialog;
import android.content.DialogInterface;

import static com.activate.gcm.CommonUtilities.SENDER_ID;

@Kroll.module(name="Gcm", id="com.activate.gcm")
public class C2dmModule extends KrollModule
{
	// Standard Debugging variables
	private static final String LCAT = "C2dmModule";
	
	private static C2dmModule _THIS;
	
	private KrollFunction successCallback;
	private KrollFunction errorCallback;
	private KrollFunction messageCallback;
	
	public C2dmModule() {
		super();
		_THIS = this;
	}

	// Methods
	@Kroll.method
	public void registerC2dm(String senderId, HashMap options) {
		Log.d(LCAT, "registerC2dm called");

		SENDER_ID = senderId;
		
		successCallback = (KrollFunction)options.get("success");
		errorCallback = (KrollFunction)options.get("error");
		messageCallback = (KrollFunction)options.get("callback");
		
		String registrationId = getRegistrationId();
		if(registrationId != null && registrationId.length() > 0) 
        {
			sendSuccess(registrationId);
		} else {
			GCMRegistrar.register(TiApplication.getInstance(), SENDER_ID);
		}


		/*GCMRegistrar.checkDevice(TiApplication.getInstance());
		GCMRegistrar.checkManifest(TiApplication.getInstance());
		final String regId = getRegistrationId();
		if(regId.equals(""))
		{
			GCMRegistrar.register(TiApplication.getInstance(), SENDER_ID);
		}
		else
		{
			sendSuccess(regId);
		}*/
	}
	
	// Methods
	@Kroll.method
	public void unregister() {
		Log.d(LCAT, "unregister called");
		GCMRegistrar.unregister(TiApplication.getInstance());	
	}
		
		
	// Properties
	@Kroll.method
	public String getRegistrationId() {
		Log.d(LCAT, "get registrationId property");
		return GCMRegistrar.getRegistrationId(TiApplication.getInstance());
	}

	@Kroll.method
	public String getSenderId()
	{
		return SENDER_ID;
	}

	
	public void sendSuccess(String registrationId) {
		if(successCallback != null) {
			HashMap data = new HashMap();
			data.put("registrationId", registrationId);
		
			successCallback.callAsync(getKrollObject(),data);
		}
	}
	
	public void sendError(String error) {
		if(errorCallback != null) {
			HashMap data = new HashMap();
			data.put("error", error);
		
			errorCallback.callAsync(getKrollObject(),data);
		}
	}
	
	public void sendMessage(HashMap messageData) {
		if(messageCallback != null) {
			HashMap data = new HashMap();
			data.put("data", messageData);
		
			messageCallback.call(getKrollObject(),data);
		}
	}
	
	
	public static C2dmModule getInstance() {
		return _THIS;
	}

}
