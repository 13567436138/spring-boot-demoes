package com.mark.demo.security.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.utils.Reflections;
import com.mark.demo.security.utils.SQLHelper;


@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})
})
public class PreparePaginationInterceptor extends BaseInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget().getClass().isAssignableFrom(RoutingStatementHandler.class)) {
            final RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            final BaseStatementHandler delegate = (BaseStatementHandler) Reflections.getFieldValue(statementHandler, DELEGATE);
            final MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate, MAPPED_STATEMENT);
            BoundSql boundSql = delegate.getBoundSql();
            //分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                return ivk.proceed();
            } else {
                final Connection connection = (Connection) ivk.getArgs()[0];
                final String sql = boundSql.getSql();
                //记录统计
                Pagination page  = convertParameter(parameterObject);
                if(null != page) page.setTotalCount(SQLHelper.getCount(sql, connection, mappedStatement, parameterObject, boundSql));
                String pagingSql = SQLHelper.generatePageSql(sql, page, DIALECT);
                //将分页sql语句反射回BoundSql.
                Reflections.setFieldValue(boundSql, "sql", pagingSql);
            }

            if (boundSql.getSql() == null || "".equals(boundSql.getSql())) {
                return null;
            }

        }
        return ivk.proceed();
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        initProperties(properties);
    }
}
