package com.baizhi;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestHex {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123", "abcd", 1024);
        System.out.println(md5Hash.toHex());


    }


}
