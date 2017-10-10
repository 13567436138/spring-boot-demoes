package com.mark.demo.security.mybatis;

import java.io.Serializable;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;

import com.mark.demo.security.base.Pagination;
import com.mark.demo.security.mybatis.dialect.Dialect;
import com.mark.demo.security.mybatis.dialect.MysqlDialect;
import com.mark.demo.security.mybatis.dialect.OracleDialect;
import com.mark.demo.security.mybatis.dialect.PostgreSQLDialect;
import com.mark.demo.security.mybatis.dialect.SQLServer2005Dialect;
import com.mark.demo.security.utils.Reflections;


public abstract class BaseInterceptor implements Interceptor, Serializable {

    private static final long serialVersionUID = 1L;

    protected static final String PAGE = "pagination";

    protected static final String DELEGATE = "delegate";

    protected static final String MAPPED_STATEMENT = "mappedStatement";

    protected Dialect DIALECT;


    /**
     * 对参数进行转换和检查
     *
     * @param parameterObject 参数对象
     * @return 分页对象
     * @throws NoSuchFieldException 无法找到参数
     */
    protected static Pagination convertParameter(Object parameterObject) {
        try {
            if (parameterObject instanceof Pagination) {
                return (Pagination) parameterObject;
            } else {
                return (Pagination) Reflections.getFieldValue(parameterObject, PAGE);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式
     * <code>dialectClass</code>,自定义方言类。可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlPattern</code> 需要拦截的SQL ID
     *
     * @param p 属性
     */
    protected void initProperties(Properties p) {
        Dialect dialect = null;
        String dbType = "mysql";
        if ("mysql".equals(dbType)) {
            dialect = new MysqlDialect();
        } else if ("oracle".equals(dbType)) {
            dialect = new OracleDialect();
        } else if ("postgre".equals(dbType)) {
            dialect = new PostgreSQLDialect();
        } else if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
            dialect = new SQLServer2005Dialect();
        }
        if (dialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        DIALECT = dialect;
    }
}