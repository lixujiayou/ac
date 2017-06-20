// 

// 

package com.inspur.resources.http;

public class WebObjectResult
{
    private static WebObjectResult instance;
    public String ERRORDESCRIPTION;
    public Integer RESULT;
    public String SERIALNO;
    public Object obj;
    
    static {
        WebObjectResult.instance = null;
    }
    
    public WebObjectResult() {
        this.SERIALNO = null;
        this.RESULT = null;
        this.ERRORDESCRIPTION = null;
        this.obj = null;
    }
    
    public static WebObjectResult getInstance() {
        if (WebObjectResult.instance == null) {
            return new WebObjectResult();
        }
        return WebObjectResult.instance;
    }
}
