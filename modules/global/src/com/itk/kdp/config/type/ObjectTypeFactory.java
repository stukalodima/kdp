package com.itk.kdp.config.type;


import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.config.type.TypeFactory;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.EntityLoadInfo;
import com.haulmont.cuba.core.global.LoadContext;
import org.apache.commons.lang3.StringUtils;

// Ця хрень чомусь не працюэ
public class ObjectTypeFactory extends TypeFactory {

    @Override
    public Object build(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        DataService ds = AppBeans.get(DataService.class);

        EntityLoadInfo info = EntityLoadInfo.parse(value);
        if (info == null)
            throw new IllegalArgumentException("Invalid entity info: " + value);

        LoadContext ctx = new LoadContext(info.getMetaClass()).setId(info.getId());
        if (info.getViewName() != null)
            ctx.setView(info.getViewName());
        return ds.load(ctx);

    }
}
