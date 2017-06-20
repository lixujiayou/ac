// 

// 

package com.inspur.resources.http;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.NameValuePair;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.IOException;
import org.xml.sax.SAXException;

import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.ConstantValue;
import com.inspur.resources.utils.ValueUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import android.util.Log;
import org.w3c.dom.NodeList;

public class WebInterface
{
    private static final String TAG = "WebInterface";
    private static final int TIMEOUT_3000MS = 3000;
    private static final int TIMEOUT_8000MS = 8000;
    
    protected String SendXML(final String s) {
        String s2;
        if (ApplicationValue.mUser != null) {
            s2 = String.valueOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><WEBSERVICESYNC><HEADER></HEADER><BODY><SERIALNO>11</SERIALNO><VERSION>V1.0</VERSION><USER>") + ApplicationValue.mUser;
        }
        else {
            s2 = String.valueOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><WEBSERVICESYNC><HEADER></HEADER><BODY><SERIALNO>11</SERIALNO><VERSION>V1.0</VERSION><USER>") + "123";
        }
        final String string = String.valueOf(s2) + "</USER><PASSWORD>";
        String s3;
        if (ApplicationValue.mPassword != null) {
            s3 = String.valueOf(string) + ValueUtil.MD5EncodeToHex(ApplicationValue.mPassword).substring(8, 24);
        }
        else {
            s3 = String.valueOf(string) + ValueUtil.MD5EncodeToHex("123").substring(8, 24);
        }
        return String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(s3)).append("</PASSWORD>").toString())).append(s).toString()) + "</BODY></WEBSERVICESYNC>";
    }
    
    protected String SendXML(final String s, String string, final String s2) {
        String string2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><WEBSERVICESYNC><HEADER></HEADER><BODY><SERIALNO>11</SERIALNO><VERSION>V1.0</VERSION>";
        if (string != null) {
            string2 = String.valueOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><WEBSERVICESYNC><HEADER></HEADER><BODY><SERIALNO>11</SERIALNO><VERSION>V1.0</VERSION>") + "<USER>" + string + "</USER>";
        }
        string = string2;
        if (s2 != null) {
            string = String.valueOf(string2) + "<PASSWORD>" + s2 + "</PASSWORD>";
        }
        return String.valueOf(new StringBuilder(String.valueOf(string)).append(s).toString()) + "</BODY></WEBSERVICESYNC>";
    }
    
   /* protected NodeList getResponseNode(final String s, final WebObjectResult webObjectResult, final String s2) {
            Log.d("WebInterface", s);
            while (true) {
                int n = 0;
                Label_0244: {
                    try {
                        final Element documentElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(s))).getDocumentElement();
                        webObjectResult.SERIALNO = documentElement.getElementsByTagName("SERIALNO").item(0).getFirstChild().getNodeValue();
                        final Node item = documentElement.getElementsByTagName("BODY").item(0);
                        if (item != null) {
                            final NodeList childNodes = item.getChildNodes();
                            n = 0;
                            if (n < childNodes.getLength()) {
                                final Node item2 = childNodes.item(n);
                                final String nodeName = item2.getNodeName();
                                if (nodeName.equals("RESULT")) {
                                    webObjectResult.RESULT = Integer.parseInt(item2.getFirstChild().getNodeValue());
                                    break Label_0244;
                                }
                                if (nodeName.equals("DESCRIPTION")) {
                                    webObjectResult.ERRORDESCRIPTION = item2.getFirstChild().getNodeValue();
                                    if (webObjectResult.RESULT != 0) {
                                        return null;
                                    }
                                    break Label_0244;
                                }
                                else {
                                    if (nodeName.equals(s2)) {
                                        return item2.getChildNodes();
                                    }
                                    break Label_0244;
                                }
                            }
                        }
                    }
                    catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    }
                    catch (SAXException ex2) {
                        ex2.printStackTrace();
                    }
                    catch (IOException ex3) {
                        ex3.printStackTrace();
                    }
                    break;
                }
                ++n;
                continue;
            }
    }*/
    
    protected void getResponseNode(final String s, final WebObjectResult webObjectResult) {
        while (true) {
            Log.d("WebInterface", s);
            while (true) {
                int n = 0;
                Label_0206: {
                    try {
                        final Element documentElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(s))).getDocumentElement();
                        webObjectResult.SERIALNO = documentElement.getElementsByTagName("SERIALNO").item(0).getFirstChild().getNodeValue();
                        final Node item = documentElement.getElementsByTagName("BODY").item(0);
                        if (item != null) {
                            final NodeList childNodes = item.getChildNodes();
                            n = 0;
                            if (n >= childNodes.getLength()) {
                                return;
                            }
                            final Node item2 = childNodes.item(n);
                            final String nodeName = item2.getNodeName();
                            if (nodeName.equals("RESULT")) {
                                webObjectResult.RESULT = Integer.parseInt(item2.getFirstChild().getNodeValue());
                                break Label_0206;
                            }
                            if (nodeName.equals("DESCRIPTION")) {
                                webObjectResult.ERRORDESCRIPTION = item2.getFirstChild().getNodeValue();
                            }
                            break Label_0206;
                        }
                    }
                    catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                        return;
                    }
                    catch (SAXException ex2) {
                        ex2.printStackTrace();
                        return;
                    }
                    catch (IOException ex3) {
                        ex3.printStackTrace();
                    }
                    break;
                }
                ++n;
                continue;
            }
        }
    }
    
    protected void jiexiError(final WebObjectResult webObjectResult) {
        webObjectResult.RESULT = ConstantValue.RESOLVE_XML_ERROR_VALUE;
        webObjectResult.ERRORDESCRIPTION = "\u89e3\u6790XML\u5931\u8d25";
    }
    
    protected String webImplService(final List<NameValuePair> list, final String s) {
        try {
            Log.d("WebInterface", "webServer=" + ApplicationValue.url);
            final HttpPost httpPost = new HttpPost(URIUtils.createURI("http", ApplicationValue.url, -1, s, (String)null, (String)null));
            httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((List)list, "utf-8"));
            final DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            defaultHttpClient.getParams().setParameter("http.connection.timeout", (Object)8000);
            defaultHttpClient.getParams().setParameter("http.socket.timeout", (Object)8000);
            final HttpResponse execute = defaultHttpClient.execute((HttpUriRequest)httpPost);
            if (execute.getStatusLine().getStatusCode() != 200) {
                Log.d("WebInterface", "Web response fail!");
                Log.d("WebInterface", "\u670d\u52a1\u5668\u54cd\u5e94\u9519\u8bef: " + execute.getStatusLine().toString());
                return null;
            }
            return EntityUtils.toString(execute.getEntity());
        }
        catch (URISyntaxException ex) {
            return null;
        }
        catch (UnsupportedEncodingException ex2) {
            return null;
        }
        catch (ClientProtocolException ex3) {
            return null;
        }
        catch (IOException ex4) {
            return null;
        }
    }
    
    protected String webImplService(final List<NameValuePair> list, final String s, final int n) {
        synchronized (this) {
            try {
                Log.d("WebInterface", "webServer=" + ApplicationValue.url);
                final HttpPost httpPost = new HttpPost(URIUtils.createURI("http", ApplicationValue.url, -1, s, (String)null, (String)null));
                httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((List)list, "utf-8"));
                final DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                defaultHttpClient.getParams().setParameter("http.connection.timeout", (Object)n);
                defaultHttpClient.getParams().setParameter("http.socket.timeout", (Object)n);
                final HttpResponse execute = defaultHttpClient.execute((HttpUriRequest)httpPost);
                String string;
                if (execute.getStatusLine().getStatusCode() != 200) {
                    Log.d("WebInterface", "Web response fail!");
                    Log.d("WebInterface", "\u670d\u52a1\u5668\u54cd\u5e94\u9519\u8bef: " + execute.getStatusLine().toString());
                    string = null;
                }
                else {
                    string = EntityUtils.toString(execute.getEntity());
                }
                return string;
            }
            catch (URISyntaxException ex) {
                return null;
            }
            catch (UnsupportedEncodingException ex2) {
                return null;
            }
            catch (ClientProtocolException ex3) {
                return null;
            }
            catch (IOException ex4) {
                return null;
            }
        }
    }
    
    protected String webImplService(final List<NameValuePair> list, final URI uri) {
        try {
            Log.d("WebInterface", "webServer=" + ApplicationValue.url);
            final HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((List)list, "utf-8"));
            final DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            defaultHttpClient.getParams().setParameter("http.connection.timeout", (Object)8000);
            defaultHttpClient.getParams().setParameter("http.socket.timeout", (Object)8000);
            final HttpResponse execute = defaultHttpClient.execute((HttpUriRequest)httpPost);
            if (execute.getStatusLine().getStatusCode() != 200) {
                Log.d("WebInterface", "Web response fail!");
                Log.d("WebInterface", "\u670d\u52a1\u5668\u54cd\u5e94\u9519\u8bef: " + execute.getStatusLine().toString());
                return null;
            }
            return EntityUtils.toString(execute.getEntity());
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
        catch (ClientProtocolException ex2) {
            return null;
        }
        catch (IOException ex3) {
            return null;
        }
    }
    
    protected String webImplService(final List<NameValuePair> list, final URI uri, final int n) {
        try {
            Log.d("WebInterface", "webServer=" + ApplicationValue.url);
            final HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((List)list, "utf-8"));
            final DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            defaultHttpClient.getParams().setParameter("http.connection.timeout", (Object)n);
            defaultHttpClient.getParams().setParameter("http.socket.timeout", (Object)n);
            final HttpResponse execute = defaultHttpClient.execute((HttpUriRequest)httpPost);
            if (execute.getStatusLine().getStatusCode() != 200) {
                Log.d("WebInterface", "Web response fail!");
                Log.d("WebInterface", "\u670d\u52a1\u5668\u54cd\u5e94\u9519\u8bef: " + execute.getStatusLine().toString());
                return null;
            }
            return EntityUtils.toString(execute.getEntity());
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
        catch (ClientProtocolException ex2) {
            return null;
        }
        catch (IOException ex3) {
            return null;
        }
    }
}
