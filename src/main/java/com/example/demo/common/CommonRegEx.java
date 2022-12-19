package com.example.demo.common;

public class CommonRegEx {
    public static final String REG_EX_ID = "[0-9a-z]{5,20}";
    public static final String REG_EX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REG_EX_CELL_NO = "(01)\\d{1}-\\d{3,4}-\\d{4}";
    public static final String REG_EX_PASSWORD = "^(?:[a-zA-Z]()|[0-9]()|[!@#$%^*()_\\-+~=]())+$(?:\\1\\2|\\1\\3|\\2\\3)";
}
