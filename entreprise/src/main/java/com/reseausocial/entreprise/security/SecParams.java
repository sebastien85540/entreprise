package com.reseausocial.entreprise.security;

public interface SecParams {
    public static final long EXP_TIME = 10*24*60*60*1000;
    public static final String SECRET = "admin@admin.com";
    public static final String PREFIX = "Bearer ";
}
