package com.reficul.Toolkit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xuzl on 16-2-19.
 */
public class Hash {
    private String input;
    private String output;

    public Hash(String input) {
        this.input = input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public Hash MD5() {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        output = hexString.toString();
        return this;
    }

    public Hash Sha1() {
//        Todo
        return this;
    }
}
