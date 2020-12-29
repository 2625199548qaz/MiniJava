package com.company;

public class Token {
    public String category;         //标识单词的种类
    public String location;         //标识单词本身的值或者是在符号表中的位置
    public int line;                //标识单词所在的行号

    public Token(String i , String str,int l){
        category = i;
        location = str;
        line = l;
    }
}
