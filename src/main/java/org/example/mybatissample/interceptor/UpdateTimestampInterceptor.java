package org.example.mybatissample.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;

@Component
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class UpdateTimestampInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];

        if (parameter != null) {
            try {
                Field updatedAtField = parameter.getClass().getDeclaredField("updatedAt");
                updatedAtField.setAccessible(true);
                updatedAtField.set(parameter, LocalDateTime.now());
            } catch (NoSuchFieldException e) {
                log.error(e.toString());
            } catch (IllegalAccessException e) {
                log.error(e.toString());
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
