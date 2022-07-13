package com.santiago.proyecto.interceptors;

import com.santiago.proyecto.configs.MysqlConn;
import com.santiago.proyecto.services.ServiceJdbcException;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@TransactionalJdbc
@Interceptor
@Slf4j
public class TransactionalInterceptor {

    @Inject
    @MysqlConn
    private Connection conn;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception {
        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }

        try {
            Object resultado = invocation.proceed();
            conn.commit();
            return resultado;
        } catch (ServiceJdbcException e){
            conn.rollback();
            throw e;
        }
    }
}
