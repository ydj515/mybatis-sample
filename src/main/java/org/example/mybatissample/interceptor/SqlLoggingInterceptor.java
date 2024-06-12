package org.example.mybatissample.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlLoggingInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(SqlLoggingInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        sql = sql.replaceAll("\\n", " "); // 여러줄로 나오는 쿼리를 한줄로 처리하기 위함
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        String sqlWithParams = showSql(sql, parameterMappings, parameterObject, typeHandlerRegistry);

        logger.info("Executing SQL: {}", sqlWithParams);

        return invocation.proceed();
    }

    private String showSql(String sql, List<ParameterMapping> parameterMappings, Object parameterObject, TypeHandlerRegistry typeHandlerRegistry) {
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            return sql;
        }
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty();
            Object value;
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                value = parameterObject;
            } else {
                MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
                value = metaObject.getValue(propertyName);
            }
            sql = sql.replaceFirst("\\?", getParameterValue(value));
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        if (obj instanceof String) {
            return "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + formatter.format((Date) obj) + "'";
        } else if (obj instanceof LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return "'" + ((LocalDateTime) obj).format(formatter) + "'";
        } else {
            return obj == null ? "null" : obj.toString();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}