package com.itguai.models.base;

import java.io.Serializable;

public class Result<D>  implements Serializable {
    public boolean success;
    public String code;
    public String message;
    public D data;
}