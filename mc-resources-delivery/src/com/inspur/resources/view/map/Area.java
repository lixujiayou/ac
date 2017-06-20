// 

// 

package com.inspur.resources.view.map;

public class Area
{
    private String code;
    private String name;
    private String pcode;
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPcode() {
        return this.pcode;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setPcode(final String pcode) {
        this.pcode = pcode;
    }
    
    @Override
    public String toString() {
        return "Area [code=" + this.code + ", name=" + this.name + ", pcode=" + this.pcode + "]";
    }
}
