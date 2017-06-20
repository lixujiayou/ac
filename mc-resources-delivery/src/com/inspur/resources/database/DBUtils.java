// 

// 

package com.inspur.resources.database;

import java.util.List;

import com.inspur.resources.database.databasebeans.SendInfoBean;
import com.inspur.resources.database.helper.MessageInfoHelper;

public class DBUtils
{
    public static void deleteOneMessage(final DBTool dbTool, final String s, final String s2) {
        dbTool.deleteSome(MessageInfoHelper.deleteOneMessage(s, s2));
    }
    
    public static void insertOneMessage(final DBTool dbTool, final SendInfoBean sendInfoBean) {
        dbTool.create("messageInfoTable", MessageInfoHelper.insertMessageByObj(sendInfoBean));
    }
    
    public static List<SendInfoBean> selectMessages(final DBTool dbTool, final String s, final String s2) {
        return MessageInfoHelper.PraseForMessage(dbTool.get(MessageInfoHelper.selectMessageAll(s, s2)));
    }
    
    public static List<SendInfoBean> selectMessagesToAll(final DBTool dbTool, final String s) {
        return MessageInfoHelper.PraseForMessage(dbTool.get(MessageInfoHelper.selectMessageAllTOO(s)));
    }
    
    public static void updateOneMessage(final DBTool dbTool, final String s, final String s2, final String s3) {
        dbTool.update(MessageInfoHelper.UpdateMessageByObj(s, s2, s3));
    }
}
