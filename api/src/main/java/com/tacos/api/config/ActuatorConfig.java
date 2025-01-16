package com.tacos.api.config;

import com.tacos.api.repo.TacoRepositroy;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ActuatorConfig implements InfoContributor {

    private final TacoRepositroy tacoRepositroy;

    public ActuatorConfig(TacoRepositroy tacoRepositroy) {
        this.tacoRepositroy = tacoRepositroy;
    }

    @Override
    public void contribute(Info.Builder builder) {
        long tacoCount = tacoRepositroy.count();
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("taco-count", tacoCount);
        builder.withDetail("taco-api-stats", infoMap);
    }
}
