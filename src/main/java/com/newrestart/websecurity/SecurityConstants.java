package com.newrestart.websecurity;

import com.newrestart.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; //10days
    public static final String TOKEN_PREFIX = "Bearer "; //this is required in the Header stream we will be sending in the Http request, the Token generated comes after the Bearer in the Header
    public static final String HEADER_STRING = "Authorization"; // This is also included in the Header
    public static final String SIGN_UP_URL = "/users"; // This is used to configure sign up url. it gives public access in the configure(HttpSecurity http) method

    public static String getTokenSecret(){
        AppProperties appProperties =(AppProperties)SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
