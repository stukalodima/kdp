package com.itk.kdp.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

import javax.inject.Inject;

@Source(type = SourceType.DATABASE)
public interface ConsultationService extends Config {

    @Property("ConsultationService.name")
    @Default("HR СБС")
    String getName();

    @Property("ConsultationService.telephone")
    @Default("067 376 83 93, 7159116")
    String getTelephone();
}