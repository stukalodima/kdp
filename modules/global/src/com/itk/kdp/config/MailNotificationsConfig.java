package com.itk.kdp.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.type.Factory;
import com.haulmont.cuba.core.config.type.Stringify;
import com.itk.kdp.config.type.ObjectStringify;
import com.itk.kdp.config.type.ObjectTypeFactory;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Organizations;

@Source(type = SourceType.DATABASE)
public interface MailNotificationsConfig extends Config {

    @Property("Mail_Notifications.Vacation.Organization")
    @Factory(factory = ObjectTypeFactory.class)
    @Stringify(stringify = ObjectStringify.class)
    @Default("kdp_Organizations-6e58d1c8-e3a5-11e4-8ec4-3085a9a0f9fe")
    Organizations getOrganizationForMailNotifications();

    void setOrganizationForMailNotifications(Organizations value);

    @Property("Mail_Notifications.Vacation.Department")
    @Factory(factory = ObjectTypeFactory.class)
    @Stringify(stringify = ObjectStringify.class)
    @Default("kdp_Departments-6d335725-e084-11eb-a96a-000d3a43dc70")
    Departments getDepartmentForMailNotifications();

    void setDepartmentForMailNotifications(Departments value);

}