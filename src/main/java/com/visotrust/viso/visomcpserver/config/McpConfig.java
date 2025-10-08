/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    private static final Logger logger = LoggerFactory.getLogger(McpConfig.class);

    @Bean
    public List<ToolCallback> registerTools(ApplicationContext applicationContext)
            throws Exception {
        logger.info("Scanning for service beans with @Tool annotations...");
        List<ToolCallback> toolCallbacks = new ArrayList<>();

        // Get all beans from the application context that are in the service package
        Map<String, Object> allBeans = applicationContext.getBeansOfType(Object.class);
        Map<String, Object> beans = new java.util.HashMap<>();

        // Filter beans to only include those in the service package
        for (Map.Entry<String, Object> entry : allBeans.entrySet()) {
            Object beanInstance = entry.getValue();
            String className = beanInstance.getClass().getName();

            if (className.contains("com.visotrust.viso.visomcpserver.service")) {
                if (AopUtils.isAopProxy(beanInstance) && beanInstance instanceof Advised) {
                    beanInstance = ((Advised) beanInstance).getTargetSource().getTarget();
                }
                beans.put(entry.getKey(), beanInstance);
            }
        }

        logger.debug("Found {} service beans to scan", beans.size());

        int registeredBeans = 0;
        for (Object bean : beans.values()) {
            // Check if the bean has any methods annotated with @Tool
            boolean hasToolAnnotation = false;
            int toolMethodCount = 0;
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(org.springframework.ai.tool.annotation.Tool.class)) {
                    hasToolAnnotation = true;
                    toolMethodCount++;
                }
            }

            // If the bean has at least one method with @Tool annotation, register it
            if (hasToolAnnotation) {
                String beanClassName = bean.getClass().getName();
                logger.info(
                        "Registering bean {} with {} @Tool methods",
                        beanClassName,
                        toolMethodCount);
                toolCallbacks.addAll(List.of(ToolCallbacks.from(bean)));
                registeredBeans++;
            }
        }

        logger.info("Registered {} beans with @Tool annotations", registeredBeans);
        return toolCallbacks;
    }
}
