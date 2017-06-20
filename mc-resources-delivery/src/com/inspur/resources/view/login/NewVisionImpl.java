package com.inspur.resources.view.login;

import android.util.Log;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.inspur.resources.bean.NewVisionInfoBean;
import com.inspur.resources.http.WebInterface;
import com.inspur.resources.http.WebObjectResult;
import com.inspur.resources.utils.ApplicationValue;

public class NewVisionImpl extends WebInterface implements NewVision
{
    private static final String TAG = "NewVisionImpl";
    private static NewVisionImpl instance;
    
    static {
        NewVisionImpl.instance = null;
    }
    
    public static NewVisionImpl getInstance() {
        if (NewVisionImpl.instance == null) {
            return new NewVisionImpl();
        }
        return NewVisionImpl.instance;
    }
    
    protected void RegPhoneResult(final String s, final WebObjectResult webObjectResult) {
       /* final NodeList responseNode = this.getResponseNode(s, webObjectResult, "VersionResponse");
        if (responseNode != null) {
            webObjectResult.obj = new NewVisionInfoBean();
            for (int i = 0; i < responseNode.getLength(); ++i) {
                final Node item = responseNode.item(i);
                final String nodeName = item.getNodeName();
                if (nodeName.equals("VERSION")) {
                    ((NewVisionInfoBean)webObjectResult.obj).setVisionNum(item.getFirstChild().getNodeValue());
                }
                else if (nodeName.equals("UPDATELOG")) {
                    ((NewVisionInfoBean)webObjectResult.obj).setVisionNote(item.getFirstChild().getNodeValue());
                }
                else if (nodeName.equals("URL")) {
                    ((NewVisionInfoBean)webObjectResult.obj).setVisionurl(item.getFirstChild().getNodeValue());
                }
                else if (nodeName.equals("TIME")) {
                    ((NewVisionInfoBean)webObjectResult.obj).setVisionTime(item.getFirstChild().getNodeValue());
                }
                else if (nodeName.equals("SIZE")) {
                    ((NewVisionInfoBean)webObjectResult.obj).setVisionSize(item.getFirstChild().getNodeValue());
                }
            }
        }*/
    }
    
    @Override
    public WebObjectResult getVision() {
		return null;
    }
    
    protected String vision_composeXML() {
        final String substring = ApplicationValue.url.substring(7);
        final String sendXML = this.SendXML("<IP>" + substring.substring(0, substring.indexOf("/")) + "</IP>", null, null);
        Log.d("vision_composeXML", sendXML);
        return sendXML;
    }
}
