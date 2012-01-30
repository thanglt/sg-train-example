package com.mycompany.webservice.sample.server.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-10-25
 * Time: 上午9:23
 * Description: email validate util
 */
public class MailUtils {

    /**
     * <p>check if it is a valid email address</p>
     *
     * @param email the email address need be checked.
     * @return <code>true</code> if it is a valid email address.
     */
    public static boolean isValidEmail(String email){
        return email.matches("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");
    }

     /**
     * <p>check if it is a unavailable email address</p>
     *
     * @param email the email address need be checked.
     * @return <code>true</code> if it is a unavailable email address.
     */
    public static boolean isNotValidEmail(String email){
        return !isValidEmail(email);
    }

    public static void main(String[] args){
        System.out.println(isValidEmail("asdfasdf"));
    }

}
