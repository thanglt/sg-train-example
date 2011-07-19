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

package com.smartgwt.sample.showcase.server.customDataSource;


import java.util.Iterator;
import java.util.Map;

import com.isomorphic.hibernate.HibernateDataSource;
import com.isomorphic.util.ErrorReport;

/**
 * This class is an extension of HibernateDataSource that does some additional (rather
 * trivial) validation.  Its purpose is to demonstrate an implementation of a custom
 * DataSource that can be used with the client-side property DataSource.serverConstructor
 */
public class ExampleCustomDataSource extends HibernateDataSource {


    public ErrorReport validate(Map data, boolean reportMissingRequiredFields) throws Exception {
        ErrorReport errorReport = super.validate(data, reportMissingRequiredFields);
        if (errorReport == null) errorReport = new ErrorReport();

        // This custom implementation forbids nulls, empty strings and missing fields, regardless
        // of the setting of reportMissingRequiredFields
        for (Iterator i = getFieldNames().iterator(); i.hasNext(); ) {
            String key = (String)i.next();
            Object obj = data.get(key);
            if (obj == null || "".equals(obj)) {
                errorReport.addError(key, "ExampleCustomDataSource does not allow blank or null values");
            }
        }

        return errorReport;
    }
}