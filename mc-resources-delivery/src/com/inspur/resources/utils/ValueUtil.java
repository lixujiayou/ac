// 

// 

package com.inspur.resources.utils;

import java.nio.ByteBuffer;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class ValueUtil
{
    public static byte[] MD5Encode(final String s) {
        return MD5Encode(s.getBytes());
    }
    
    public static byte[] MD5Encode(byte[] digest) {
        try {
            digest = MessageDigest.getInstance("MD5").digest(digest);
            return digest;
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new byte[0];
        }
    }
    
    public static String MD5EncodeToHex(final String s) {
        return bytesToHexString(MD5Encode(s));
    }
    
    public static String[] StrForArray(final String s) {
        final int length = s.length();
        final String[] array = new String[length];
        for (int i = 0; i < length; ++i) {
            array[i] = s.substring(i, i + 1);
        }
        return array;
    }
    
    public static String StrToPass(final String s) {
        if (s.length() >= 3) {
            return String.valueOf(s.substring(0, 3)) + "****";
        }
        return s;
    }
    
    public static String bcd2Str(final byte[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            sb.append((byte)((array[i] & 0xF0) >>> 4));
            sb.append((byte)(array[i] & 0xF));
        }
        if (sb.toString().substring(0, 1).equalsIgnoreCase("0")) {
            return sb.toString().substring(1);
        }
        return sb.toString();
    }
    
    public static final String bytesToHexString(final byte[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length);
        for (int i = 0; i < array.length; ++i) {
            final String hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }
    
    public static final Object bytesToObject(final byte[] array) throws IOException, ClassNotFoundException {
        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(array));
        final Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }
    
    public static String change(final String s) {
        if (s != null) {
            final StringBuffer sb = new StringBuffer(s);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }
        return null;
    }
    
    /*public static void copyValueToBean(final Object o, final Object o2) {
        if (o != null && o2 != null) {
            final Class<?> class1 = o.getClass();
            final Class<?> class2 = o2.getClass();
            final Field[] declaredFields = class1.getDeclaredFields();
            for (int length = declaredFields.length, i = 0; i < length; ++i) {
                while (true) {
                    final Field field = declaredFields[i];
                    String name;
                    Type genericType;
                    Field[] declaredFields2;
                    int length2;
                    int n = 0;
                    String name2;
                    Object invoke;
                    Field field2;
                    Type genericType2;
                    Block_10_Outer:Label_0078_Outer:
                    while (true) {
                        Label_0494: {
                            while (true) {
                                try {
                                    name = field.getName();
                                    genericType = field.getGenericType();
                                    declaredFields2 = class2.getDeclaredFields();
                                    length2 = declaredFields2.length;
                                    n = 0;
                                    break Label_0481;
                                    // iftrue(Label_0227:, !genericType.toString().equals((Object)"class java.util.Date"))
                                Block_8_Outer:
                                    while (true) {
                                        while (true) {
                                            while (true) {
                                                class2.getMethod("set" + change(name2), Date.class).invoke(o2, invoke);
                                                break Label_0494;
                                                class2.getMethod("set" + change(name2), Integer.TYPE).invoke(o2, invoke);
                                                break Label_0494;
                                                invoke = class1.getMethod("get" + change(name), (Class<?>[])new Class[0]).invoke(o, new Object[0]);
                                                continue Block_10_Outer;
                                            }
                                            field2 = declaredFields2[n];
                                            name2 = field2.getName();
                                            genericType2 = field2.getGenericType();
                                            continue Label_0078_Outer;
                                        }
                                        Label_0227: {
                                            continue Block_8_Outer;
                                        }
                                    }
                                }
                                // iftrue(Label_0494:, !name.equals((Object)name2) || !genericType.toString().equals((Object)genericType2.toString()))
                                // iftrue(Label_0302:, !genericType.toString().equals((Object)"int"))
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                    break;
                                }
                                Label_0302: {
                                    if (genericType.toString().equals("class java.util.List")) {
                                        class2.getMethod("set" + change(name2), List.class).invoke(o2, invoke);
                                        break Label_0494;
                                    }
                                }
                                if (genericType.toString().equals("class java.util.ArrayList")) {
                                    class2.getMethod("set" + change(name2), ArrayList.class).invoke(o2, invoke);
                                    break Label_0494;
                                }
                                class2.getMethod("set" + change(name2), String.class).invoke(o2, invoke);
                                break Label_0494;
                                if (n >= length2) {
                                    break;
                                }
                                continue;
                            }
                        }
                        ++n;
                        continue;
                    }
                }
            }
        }
    }*/
    
    public static byte[] getIntGgetD(int i) {
        final byte[] array = { (byte)(i & 0xFF), (byte)((0xFF00 & i) >> 8), (byte)((0xFF0000 & i) >> 16), (byte)((0xFF000000 & i) >> 24) };
        final StringBuffer sb = new StringBuffer(array.length);
        String hexString;
        for (i = 0; i < array.length; ++i) {
            hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString.toUpperCase());
        }
        System.out.println(sb);
        return array;
    }
    
    public static String getStr16(final int n) {
        switch (n) {
            default: {
                return new StringBuilder(String.valueOf(n)).toString();
            }
            case 10: {
                return "A";
            }
            case 11: {
                return "B";
            }
            case 12: {
                return "C";
            }
        }
    }
    
    public static String hexString2binaryString(final String s) {
        String s2;
        if (s == null || s.length() % 2 != 0) {
            s2 = null;
        }
        else {
            String string = "";
            int n = 0;
            while (true) {
                s2 = string;
                if (n >= s.length()) {
                    break;
                }
                final String string2 = "0000" + Integer.toBinaryString(Integer.parseInt(s.substring(n, n + 1), 16));
                string = String.valueOf(string) + string2.substring(string2.length() - 4);
                ++n;
            }
        }
        return s2;
    }
    
    public static byte[] hexStringToByte(final String s) {
        final int n = s.length() / 2;
        final byte[] array = new byte[n];
        final char[] charArray = s.toUpperCase().toCharArray();
        for (int i = 0; i < n; ++i) {
            final int n2 = i * 2;
            array[i] = (byte)(toByte(charArray[n2]) << 4 | toByte(charArray[n2 + 1]));
        }
        return array;
    }
    
    public static final Object hexStringToObject(final String s) throws IOException, ClassNotFoundException {
        return bytesToObject(hexStringToByte(s));
    }
    
    public static final byte[] objectToBytes(final Serializable s) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(s);
        objectOutputStream.flush();
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public static final String objectToHexString(final Serializable s) throws IOException {
        return bytesToHexString(objectToBytes(s));
    }
    
    public static final String shortToHexString(final short n) {
        final ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.asShortBuffer().put(n);
        return bytesToHexString(allocate.array());
    }
    
    public static byte[] str2Bcd(final String s) {
        int n2;
        final int n = n2 = s.length();
        String string = s;
        if (n % 2 != 0) {
            string = "0" + s;
            n2 = string.length();
        }
        final byte[] array = new byte[n2];
        int n3;
        if ((n3 = n2) >= 2) {
            n3 = n2 / 2;
        }
        final byte[] array2 = new byte[n3];
        final byte[] bytes = string.getBytes();
        for (int i = 0; i < string.length() / 2; ++i) {
            byte b;
            if (bytes[i * 2] >= 48 && bytes[i * 2] <= 57) {
                b = (byte)(bytes[i * 2] - 48);
            }
            else if (bytes[i * 2] >= 97 && bytes[i * 2] <= 122) {
                b = (byte)(bytes[i * 2] - 97 + 10);
            }
            else {
                b = (byte)(bytes[i * 2] - 65 + 10);
            }
            byte b2;
            if (bytes[i * 2 + 1] >= 48 && bytes[i * 2 + 1] <= 57) {
                b2 = (byte)(bytes[i * 2 + 1] - 48);
            }
            else if (bytes[i * 2 + 1] >= 97 && bytes[i * 2 + 1] <= 122) {
                b2 = (byte)(bytes[i * 2 + 1] - 97 + 10);
            }
            else {
                b2 = (byte)(bytes[i * 2 + 1] - 65 + 10);
            }
            array2[i] = (byte)((b << 4) + b2);
        }
        return array2;
    }
    
    public static String switchS(final int n) {
        switch (n) {
            default: {
                return new StringBuilder().append(n).toString();
            }
            case 10: {
                return "a";
            }
            case 11: {
                return "b";
            }
            case 12: {
                return "c";
            }
            case 13: {
                return "d";
            }
            case 14: {
                return "e";
            }
            case 15: {
                return "f";
            }
        }
    }
    
    private static byte toByte(final char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    public static int trans1(final char[] array) {
        int n = 0;
        int n3;
        for (int i = 0; i < 4; ++i, n = n3) {
            int n2 = 8;
            n3 = n;
            if (array[i] == '1') {
                for (int j = 1; j <= i; ++j) {
                    n2 /= 2;
                }
                n3 = n + n2;
            }
        }
        return n;
    }
    
    public static String transBinToHex(final String s) {
        final int n = s.length() / 4;
        String string = "";
        for (int i = 0; i < n; ++i) {
            final char[] array = new char[4];
            s.getChars(i * 4, i * 4 + 4, array, 0);
            string = String.valueOf(string) + switchS(trans1(array));
        }
        return string;
    }
}
