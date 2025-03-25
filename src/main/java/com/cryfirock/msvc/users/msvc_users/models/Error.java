package com.cryfirock.msvc.users.msvc_users.models;

import java.util.Date;

import lombok.Data;

@Data
public class Error {

    /**
     * Attributes
     */
    private String message;
    private String error;
    private int status;
    private Date date;

}
