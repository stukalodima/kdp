package com.itk.kdp.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

@Source(type = SourceType.DATABASE)
public interface ServiceDeskConfig extends Config {
    @Property("SERVICE-DESK.mail")
    @Default("service.desk@it-capital.com.ua")
    String getServiceDeskMail();
}