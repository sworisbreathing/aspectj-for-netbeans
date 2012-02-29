/* *******************************************************************
 * Copyright (c) 1999-2001 Xerox Corporation, 
 *               2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 *  
 * Contributors: 
 *     Xerox/PARC     initial implementation 
 * ******************************************************************/

 
 
package org.aspectj.tools.ajde.netbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.ui.UserPreferencesAdapter;
import org.aspectj.util.LangUtil;

public class UserPreferencesStore implements UserPreferencesAdapter {
    public static final String FILE_NAME = "/.ajbrowser";
    private final String VALUE_SEP = ";";
    private Properties properties = new Properties();
    private boolean persist = true;
	
	public UserPreferencesStore() {
        this(true);
	}

    public UserPreferencesStore(boolean loadDefault) {
    	persist = loadDefault;
        if (persist) {
            loadProperties(getPropertiesFilePath());
        }
    }
	
    public String getProjectPreference(String name) {
//        System.out.println("UserPreferencesStore#get project pref: "+name);
        return properties.getProperty(name);
    }

    public List getProjectMultivalPreference(String name) {
//        System.out.println("UserPreferencesStore#getProjectMultivalPreference: "+name);
        List values = new ArrayList();
        String valuesString = properties.getProperty(name);
        if (valuesString != null && !valuesString.trim().equals("")) {
            StringTokenizer st = new StringTokenizer(valuesString, VALUE_SEP);
            while (st.hasMoreTokens()) {
                values.add(st.nextToken());
            }
        }
        return values;
    }

    public void setProjectPreference(String name, String value) {
//        System.out.println("UserPreferencesStore#setProjectPreference: "+name+" "+value);
        properties.setProperty(name, value); 
        saveProperties();
    }

    public void setProjectMultivalPreference(String name, List values) {
//        System.out.println("UserPreferencesStore#setProjectMultivalPreference: "+name+" "+values);
        String valuesString = "";
        for (Iterator it = values.iterator(); it.hasNext(); ) {
            valuesString += (String)it.next() + ';';
        }
        properties.setProperty(name, valuesString);
        saveProperties();
    }

    public static String getPropertiesFilePath() {
//        System.out.println("UserPreferencesStore#getPropertiesFilePath");
        String path = System.getProperty("user.home");
        if (path == null) {
            path = ".";
        }
        return path + FILE_NAME;
    }
    
	public String getGlobalPreference(String name) {
//            System.out.println("UserPreferencesStore#getGlobalPreference "+name);
		return getProjectPreference(name);
	}
	
	public List getGlobalMultivalPreference(String name) {
//            System.out.println("UserPreferencesStore#getGlobalMultivalPreference "+name);
		return getProjectMultivalPreference(name);
	}
	
	public void setGlobalPreference(String name, String value) {
//            System.out.println("UserPreferencesStore#setGlobalPreference "+name+" "+value);
		setProjectPreference(name, value);
	}
	
	public void setGlobalMultivalPreference(String name, List values) {
//            System.out.println("UserPreferencesStore#setGlobalMultivalPreference "+name+" "+values);
		setProjectMultivalPreference(name, values);
	}
	private void loadProperties(String path) {
        if (LangUtil.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (!file.canRead()) {
            return;
        }
        FileInputStream in = null;
        try {
            path = getPropertiesFilePath();
            in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException ioe) {
            Ajde.getDefault().getErrorHandler().handleError("Error reading properties from " + path, ioe);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
	}
    public void saveProperties() {
//        System.out.println("UserPreferencesStore#saveProperties");
    	if (!persist) return;
    	
        FileOutputStream out = null;
        String path = null;
        try {
            path = getPropertiesFilePath();
            out = new FileOutputStream(path);
            properties.store(out, "AJDE Settings");
        } catch (IOException ioe) {
            Ajde.getDefault().getErrorHandler().handleError("Error writing properties to " + path, ioe);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
