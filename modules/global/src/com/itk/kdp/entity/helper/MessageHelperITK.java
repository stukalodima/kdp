package com.itk.kdp.entity.helper;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

public abstract class MessageHelperITK {

    public static String getCaption(String captionUa, String captionEn, String captionRu) {
        UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.class);
        switch (userSessionSource.getUserSession().getLocale().getLanguage()){
            case "en": return captionEn;
            case "ru": return captionRu;
            default: return captionUa;
        }
    }

    public static String getCaptionByClassName(String className) {
        String strFirst = className.substring(0,1);
        String strFirstLow = strFirst.toLowerCase();
        return className.replaceFirst(strFirst, strFirstLow) + ".caption";
    }
}
