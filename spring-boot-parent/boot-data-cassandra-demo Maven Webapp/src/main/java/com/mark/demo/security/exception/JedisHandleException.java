package com.mark.demo.security.exception;


public class JedisHandleException extends RuntimeException
{
    //
    private static final long serialVersionUID = 8149861602527033152L;
    
    public JedisHandleException()
    {
        super();
    }
    
    public JedisHandleException(String s)
    {
        super(s);
    }
    
    public JedisHandleException(Throwable e)
    {
        super(e);
    }
}
