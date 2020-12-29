package com.company;

import java.io.*;
import java.util.*;

public class WorldAnalyse {
    //存储词法分析得到的token
    public ArrayList<Token> token;
    //符号表，存储标识符（包括变量标识符,方法标识符和类标识符），每个元素增加一个属性用来标识是方法还是标识符还是类名
    public List<String[]> symboltable;
    //存储单词符号和种别编码
    public Map<String,String> map1;

    //构造函数初始化
    public WorldAnalyse() {
        token = new ArrayList<Token>();
        symboltable = new ArrayList<String[]>();
        map1=new HashMap<String,String>();
        map1.put("if","1");
        map1.put("else","2");
        map1.put("class","3");
        map1.put("boolean","4");
        map1.put("extends","5");
        map1.put("false","6");
        map1.put("int","7");
        map1.put("length","8");
        map1.put("main","9");
        map1.put("new","10");
        map1.put("public","11");
        map1.put("return","12");
        map1.put("static","13");
        map1.put("this","14");
        map1.put("true","15");
        map1.put(",","16");
        map1.put(";","17");
        map1.put("(","18");
        map1.put(")","19");
        map1.put("{","20");
        map1.put("}","21");
        map1.put("void","22");
        map1.put("while","23");
        map1.put("println","24");
        map1.put("=","25");
        map1.put("+","26");
        map1.put("-","27");
        map1.put("*","28");
        map1.put("/","29");
        map1.put("&","30");
        map1.put("|","31");
        map1.put("!","32");
        map1.put("==","33");
        map1.put("!=","34");
        map1.put("<","35");
        map1.put("<=","36");
        map1.put(">","37");
        map1.put(">=","38");
        map1.put(".","39");
        map1.put("[","40");
        map1.put("]","41");
        map1.put("标识符","42");
        map1.put("常数","43");
        map1.put("关键字","44");
        map1.put("运算符或界限符","45");
        map1.put("System","46");
        map1.put("out","47");
        map1.put("String","48");
        map1.put("char","49");
        map1.put("double","50");
        map1.put("for","51");
        //程序里的单词成分被分为六类，标识符，常数，关键字，运算符或界限符，字符，字符串。标识符单独用一张表，关键字用一个一维数组表，常数不用表存储，第二个直接
        //表示值
    }

    public void lexicalAnalysis(String pathname) throws IOException {
        //识别token串的过程用一个成员方法封装起来，方便语法分析程序调用
        int i =0,j=0;
        String strToken ="";//存储从strTest分离出来的关键字、标识符、常数、运算符、界符
        String strTest ="";//存储从文件里面读来的一行代码
        char ch ;//用来存储从strTest中分离出来的单个字符
        File file = new File(pathname);//创建要访问的文件
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int line = 1;           ///标识文件的行数
        while ((strTest = bufferedReader.readLine()) != null)//读取文件的一行
        {
            System.out.println("第"+(++j)+"行分析结果：");
            strToken="";
            ch=' ';
            //找到该行中第一个不是空的字符的位置
            for (i = 0; i < strTest.length(); i++) {
                if (strTest.charAt(i) != ' ') {
                    ch = strTest.charAt(i);
                    break;
                }
            }
            //标识符和关键字的判断
            while (i < strTest.length()) {
                //判断该字符是否为字母
                if (Character.isLetter(ch))
                {
                    //该循环用于分离第一个字符为字母后续若干字母或者数字的字符串
                    while (Character.isLetter(ch) || Character.isDigit(ch)) {
                        strToken += Character.toString(ch);
                        //判断该行是否还有字符
                        if ((++i) < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {
                            //该行没有下一个字符的话终止循环进行下一行的分析
                            i = strTest.length();
                            break;
                        }
                    }
                    //当ch既不是字母也不是数字而是界限符或者空格时，这个标识符肯定是合法的，进行关键字的判断
                    if(map1.containsKey(Character.toString(ch)) || ch==' '){
                        //判断为关键字
                        if (map1.containsKey(strToken)) {
                            Token t = new Token("关键字",strToken,line);
                            token.add(t);
                            System.out.println("<"+t.category+","+t.location+">");
                        }
                        else//判断为标识符
                        {
                            String[] identifier = new String[5];  //5个元素，第二个为name，第一个表示标识符的种类，是变量标识符还是类名还是方法名
                            identifier[1] = strToken;
                            identifier[0] = null;
                            identifier[2] = null;
                            identifier[3] = null;
                            identifier[4] = null;
                            symboltable.add(identifier);
                            int location = symboltable.size()-1;
                            Token t = new Token("标识符",String.valueOf(location),line);
                            token.add(t);
                            System.out.println("<"+t.category+","+t.location+">");
                        }
                        ch = ' ';
                        strToken = "";
                    }else{          //当标识符后面跟着非法符号时，报错误
                        while (!map1.containsKey(Character.toString(ch)) && ch!=' '){
                            strToken = strToken + ch;
                            if (++i < strTest.length()) {
                                ch = strTest.charAt(i);
                            } else {
                                //该行结束的话终止循环进行下一行的分析
                                i = strTest.length();
                                break;
                            }
                        }
                        System.out.println("第"+line+"行中的"+strToken+"中含有非法符号，不是合法的标识符，错误");
                        strToken = "";
                        ch = ' ';
                    }

                }
                //常数的判断,常数的token的第二个数表示它自身的值，常数不填表
                else if (Character.isDigit(ch)) {//判断该字符是否为数字
                    int first_number = Character.getNumericValue(ch);
                    while (Character.isDigit(ch)) {//连续读取若干数字
                        strToken += Character.toString(ch);
                        //判断该行是否结束
                        if (++i < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {
                            //该行结束的话终止循环进行下一行的分析
                            i = strTest.length();
                            break;
                        }
                    }
                    int digit = strToken.length();      //记录小数点前面的位数
                    //当数字后面是运算符或者界限符或者空格时
                    if((map1.containsKey(Character.toString(ch)) || ch==' ')&&ch!='.'){
                        if(digit>1) {       //当为多位数时，此时常数的最高位不能为0
                            if (first_number != 0) {       //常数的最高位不为0
                                Token t = new Token("整数", strToken, line);
                                token.add(t);
                                System.out.println("<" + t.category + "," + t.location + ">");
                                strToken = "";
                                ch = ' ';
                            } else {    //常数的最高位为0，报错
                                System.out.println("第" + line + "行中的" + strToken + "最高位为0，错误");
                                strToken = "";
                                ch = ' ';
                            }
                        }else {         //只有一位，可为0
                            Token t = new Token("整数", strToken, line);
                            token.add(t);
                            System.out.println("<" + t.category + "," + t.location + ">");
                            strToken = "";
                            ch = ' ';
                        }
                    }else if(ch == '.'){    //数字后面跟.，那么就可能是小数
                        boolean bool = true;
                        strToken += Character.toString(ch);
                        if (++i < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {
                            //该行结束的话终止循环进行下一行的分析
                            i = strTest.length();
                            bool = false;
                            System.out.println("第" + line + "行中的" + strToken + "中小数点后无数字，不是合法的数");
                        }
                        if(Character.isDigit(ch)) {   //小数点后面跟的是数字
                            while (Character.isDigit(ch) && bool) {//连续读取若干数字
                                strToken += Character.toString(ch);
                                //判断该行是否结束
                                if (++i < strTest.length()) {
                                    ch = strTest.charAt(i);
                                } else {
                                    //该行结束的话终止循环进行下一行的分析
                                    i = strTest.length();
                                    break;
                                }
                            }
                            //当浮点数后面跟的是运算符或者界限符或者空格时
                            if(map1.containsKey(Character.toString(ch)) || ch==' '){
                                if(digit<=1) {          //小数点前面只有一位，可为0
                                    Token t = new Token("浮点数", strToken, line);
                                    token.add(t);
                                    System.out.println("<" + t.category + "," + t.location + ">");
                                    strToken = "";
                                    ch = ' ';
                                }else {         //小数点前面有多位，需要判断最高位是否为0
                                    if (first_number != 0) {       //常数的最高位不为0
                                        Token t = new Token("浮点数", strToken, line);
                                        token.add(t);
                                        System.out.println("<" + t.category + "," + t.location + ">");
                                        strToken = "";
                                        ch = ' ';
                                    } else {    //常数的最高位为0，报错
                                        System.out.println("第" + line + "行中的" + strToken + "最高位为0，错误");
                                        strToken = "";
                                        ch = ' ';
                                    }
                                }
                            }else {     //当浮点数后面直接跟着字母或者其他非法符号时
                                while (!map1.containsKey(Character.toString(ch)) && ch!=' '){
                                    strToken = strToken + ch;
                                    if (++i < strTest.length()) {
                                        ch = strTest.charAt(i);
                                    } else {
                                        //该行结束的话终止循环进行下一行的分析
                                        i = strTest.length();
                                        break;
                                    }
                                }
                                System.out.println("第" + line + "行中的" + strToken + "中含有字母或者非法符号，不是合法的浮点数，错误");
                                strToken = "";
                                ch = ' ';
                            }
                        }else if(bool){     //小数点后面直接跟着除数字之外的其他字符时
                            while (!map1.containsKey(Character.toString(ch)) && ch!=' '){
                                strToken = strToken +ch;
                                if (++i < strTest.length()) {
                                    ch = strTest.charAt(i);
                                } else {
                                    //该行结束的话终止循环进行下一行的分析
                                    i = strTest.length();
                                    break;
                                }
                            }
                            System.out.println("第" + line + "行中的" + strToken + "不是合法的浮点数，错误");
                            strToken = "";
                            ch = ' ';
                        }
                    } else{          //当数字后面为字母或者非法符号时，就违背了常数的规定，错误！
                        while (!map1.containsKey(Character.toString(ch)) && ch!=' '){
                            strToken = strToken + ch;
                            if (++i < strTest.length()) {
                                ch = strTest.charAt(i);
                            } else {
                                //该行结束的话终止循环进行下一行的分析
                                i = strTest.length();
                                break;
                            }
                        }
                        System.out.println("第" + line + "行中的" + strToken + "中含有字母或者非法符号，不是合法常数，错误");
                        strToken = "";
                        ch = ' ';
                    }
                }
                //运算符和界限符的判断，它的token的第二个就表示它们本身
                else if (map1.containsKey(Character.toString(ch))) {
                    int k = i;
                    if(ch=='<'||ch=='>'){
                        strToken=Character.toString(ch);
                        if (++i < strTest.length()) {
                            ch = strTest.charAt(i);
                            if(ch=='='){
                                strToken = strToken+"=";
                                if (++i < strTest.length()) {
                                    ch = strTest.charAt(i);
                                } else {
                                    //该行结束的话终止循环进行下一行的分析
                                    i = strTest.length();
                                    break;
                                }
                            }else {

                            }
                            Token t = new Token("运算符或界限符",strToken,line);
                            token.add(t);
                            System.out.println("<"+t.category+","+t.location+">");
                            strToken = "";
                            ch = ' ';
                        } else {
                            //该行结束的话终止循环进行下一行的分析
                            Token t = new Token("运算符或界限符",strToken,line);
                            token.add(t);
                            System.out.println("<"+t.category+","+t.location+">");
                            strToken = "";
                            ch = ' ';
                            i = strTest.length();
                            break;
                        }
                    }else {
                        strToken=Character.toString(ch);
                        Token t = new Token("运算符或界限符",strToken,line);
                        token.add(t);
                        System.out.println("<"+t.category+","+t.location+">");
                        strToken = "";
                        ch = ' ';
                        //判断该行是否结束
                        if (++i < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {
                            //该行结束的话终止循环进行下一行的分析
                            i = strTest.length();
                            break;
                        }
                    }
                }
                //字符串的判断
                else if(ch=='"'){
                    strToken += Character.toString(ch);
                    if ((++i) < strTest.length()) {
                        ch = strTest.charAt(i);
                    } else {
                        //该行没有下一个字符的话终止循环进行下一行的分析
                        i = strTest.length();
                        System.out.println("第"+line+"行"+strToken+"少一个双引号，不是合法的字符串");
                        break;
                    }
                    while (ch!='"') {
                        strToken += Character.toString(ch);
                        //判断该行是否还有字符
                        if ((++i) < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {        //如果到行末也没有"与最初"进行匹配，那就错误了
                            //该行没有下一个字符的话终止循环进行下一行的分析
                            i = strTest.length();
                            System.out.println("第"+line+"行"+strToken+"少一个双引号，不是合法的字符串");
                            break;
                        }
                    }
                    strToken += Character.toString(ch);
                    Token t = new Token("字符串",strToken,line);
                    token.add(t);
                    System.out.println("<"+t.category+","+t.location+">");
                    strToken = "";
                    ch = ' ';
                }
                //字符的判断
                else if(ch=='\''){
                    strToken += Character.toString(ch);
                    if ((++i) < strTest.length()) {
                        ch = strTest.charAt(i);
                    } else {            //说明只有一个'这一行就结束了，错误
                        //该行没有下一个字符的话终止循环进行下一行的分析
                        i = strTest.length();
                        System.out.println("第"+line+"行"+strToken+"少一个单引号，不是合法的字符");
                        break;
                    }
                    while (ch!='\'') {
                        strToken += Character.toString(ch);
                        //判断该行是否还有字符
                        if ((++i) < strTest.length()) {
                            ch = strTest.charAt(i);
                        } else {        //如果到行末也没有'与最初'进行匹配，那就错误了
                            //该行没有下一个字符的话终止循环进行下一行的分析
                            i = strTest.length();
                            System.out.println("第"+line+"行"+strToken+"少一个单引号，不是合法的字符");
                            break;
                        }
                    }
                    strToken += Character.toString(ch);
                    if(strToken.length()==3){
                        Token t = new Token("字符",strToken,line);
                        token.add(t);
                        System.out.println("<"+t.category+","+t.location+">");
                        strToken = "";
                        ch = ' ';
                    }else {
                        System.out.println("第"+line+"行"+strToken+"单引号里不止一个字符或者无字符，不是合法的字符");
                    }
                }
                //不合法输入
                else {
                    System.out.println("<"+"不合法符号"+","+ch+">");
                    strToken="";
                    ch=' ';
                    //判断该行是否结束
                    if (++i < strTest.length()) {
                        ch = strTest.charAt(i);
                    } else {
                        //该行结束的话终止循环进行下一行的分析
                        i = strTest.length();
                        break;
                    }
                }
                //找到下一个不为空的字符的位置
                for (; i < strTest.length(); i++) {
                    if (strTest.charAt(i) != ' ') {
                        ch = strTest.charAt(i);
                        break;
                    }
                }
            }
            line++;
        }
        //关闭文件读取
        bufferedReader.close();
    }

    public static void main(String[] args) throws Exception {
        WorldAnalyse worldAnalyse = new WorldAnalyse();
        worldAnalyse.lexicalAnalysis("D:\\true lexical\\java1.txt");
    }
}
