package com.eazyBytes.eazySchool.audit;


import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EazyschoolInfoContributor implements InfoContributor {
    /**
     * @param builder the builder to use
     */
    @Override
    public void contribute(Info.Builder builder) {

        Map<String, String> eazyMap = new HashMap<>();
        eazyMap.put("App Name", "EazySchool");
        eazyMap.put("App description", "Eazyschool application for student and admins");
        eazyMap.put("App Version", "1.0.0");
        eazyMap.put("Contact Email", "info@eazyschool.com");
        builder.withDetail("eazyschool-info",eazyMap);
    }
}
