package com.easyfitness.licenses;

import android.content.Context;

import de.psdev.licensesdialog.licenses.License;

/**
 * Created by Swayam Arunav Khuntia on 10/06/23.
 */

public class CustomLicense extends License {

    private static final long serialVersionUID = 5165684351201813168L;
    private String mLicenseName = "";
    private String mLicenseURL = "";

    public CustomLicense(String pName, String pURL) {
        mLicenseName = pName;
        mLicenseURL = pURL;
    }

    @Override
    public String getName() {
        return mLicenseName;
    }

    public void setName(String pName) {
        mLicenseName = pName;
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return "";
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getUrl() {
        return mLicenseURL;
    }

    public void setUrl(String pURL) {
        mLicenseURL = pURL;
    }
}
