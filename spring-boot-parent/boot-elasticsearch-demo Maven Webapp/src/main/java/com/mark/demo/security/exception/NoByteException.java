package com.mark.demo.security.exception;

public class NoByteException extends Throwable
{
    private static final long serialVersionUID = -8601145084304417269L;
    
    public NoByteException()
    {
        super();
    }
    
    public NoByteException(String msg)
    {
        super(msg);
    }
    
    public NoByteException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
    
    public NoByteException(Throwable cause)
    {
        super(cause);
    }
}
