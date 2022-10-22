package com.seed.careerhub.exception;

import org.springframework.security.access.AccessDeniedException;

public class AccessDenied extends AccessDeniedException {

    /**
     * Constructs an <code>AccessDeniedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public AccessDenied(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>AccessDeniedException</code> with the specified message and
     * root cause.
     *
     * @param msg   the detail message
     * @param cause root cause
     */
    public AccessDenied(String msg, Throwable cause) {
        super(msg, cause);
    }
}
