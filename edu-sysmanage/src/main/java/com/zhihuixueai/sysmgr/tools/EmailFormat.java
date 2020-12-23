package com.zhihuixueai.sysmgr.tools;

public class EmailFormat {
    public static boolean isEmail(String email) {
        String  regex = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        return email.matches(regex);
    }

}
