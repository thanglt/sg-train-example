/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.server;

import javax.servlet.http.HttpServletRequest;

//----------------------------------------------------------------------
//
// CartDMI implementation for the User-Specific Data example
//
// This example shows one way to insert your own logic into SmartGWT's
// normal client/server flow.  All operations of the cartItem DataSource
// are routed to this class's enforceUserAccess() method, which simply
// stamps the user's session ID onto the DSRequest and then executes it
// (which calls back into the normal SmartGWT flow).  This provides a
// robust way to ensure that all DataSource requests have the requesting
// session's ID passed to all operations; since cartItem is a SmartGWT
// SQL DataSource, the session ID will be used to filter fetch-type
// operations, and will be persisted in update- and add-type operations.
//
//----------------------------------------------------------------------

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;

public class CartDMI {

    public DSResponse enforceUserAccess(DSRequest dsRequest, HttpServletRequest servletRequest)
    throws Exception
    {
        String sessionId = servletRequest.getSession().getId();
        dsRequest.setFieldValue("sessionId", sessionId);
        return dsRequest.execute();
    }
}