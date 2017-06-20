// 

// 

package com.inspur.resources.utils;

import com.inspur.resources.view.login.NewVision;
import com.inspur.resources.view.login.NewVisionImpl;

public class BizFactory
{
    public static NewVision getNewVision() {
        return NewVisionImpl.getInstance();
    }
    
    /*public static RegPhone getRegPhone() {
        return RegPhoneImpl.getInstance();
    }*/
}
