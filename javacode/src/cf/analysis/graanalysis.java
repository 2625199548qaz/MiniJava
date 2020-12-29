package cf.analysis;

import com.company.Token;
import com.company.WorldAnalyse;

import java.io.IOException;
import java.util.List;

public class graanalysis {
    private int i;          //计数要取出第几个单词
    private WorldAnalyse wa;
    private String paraNumber;      //计数方法的参数数量
    private String paraType;        //记录方法的参数类型

    public graanalysis(String path) throws IOException {
        i = 0;
        wa = new WorldAnalyse();
        wa.lexicalAnalysis(path);
        paraNumber = "0";
        paraType = null;
    }

    //定义一个用于取单词的方法
    public Token getToken(List<Token> token) {
        if (i < token.size()) {
            Token t = token.get(i);
            i++;
            return t;
        } else
            return null;
    }

    //定义一个遍历符号表找相应标识符的方法
    /*public String[] searchSymbolList(String name, List<String[]> symboltable){
        for(int i = 0;i<symboltable.size();i++){
            String[] str = symboltable.get(i);
            if(str[1] == name)
                return str;
        }
        return null;
    }*/

    public void dealChar() {
        /*字符或者字符串的情况分为两种，
        1.字母字符或者字符串，这样的情况下，单引号内或者双引号内部整体会被当成一个标识符
          此时需要根据具体的产生式去具体分析，并且当分析得到是char或者string时，还要把
          这个标识符从符号表中移除，然后把这个值填入到相应的变量的值里面【好像不填也没关系，毕竟不涉及语义】
        2.中文字符或者字符串，那就肯定是char或者string，这个好处理，只要看种别码是字符还是字符串就行。
         */

    }

    //定义一个报错误的方法
    public void error() {
        System.out.println("语法存在错误");
    }

    //进行语法分析
    public void gramamerAnalysis() {
        goal();
    }

    //定义语法分析的方法
    public void goal() {
        mainClass();
        typeDeclaration();
    }

    public void mainClass() {
        //取15个单词进行匹配
        Token[] tokens = new Token[15];
        for (int t = 0; t < 15; t++) {
            tokens[t] = getToken(wa.token);
        }
        //第15个单词存在，那么前面的都存在
        if (tokens[14] != null) {
            if (tokens[0].location == "public") {

            } else {
                System.out.println("错误,第一个单词应是public");
                error();
                return;
            }
            if (tokens[1].location == "class") {

            } else {
                System.out.println("错误,第二个单词应是class");
                error();
                return;
            }
            if (tokens[2].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[2].location));
                if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "类名";
                    for (int i = 0; i < wa.symboltable.size(); i++) {
                        String[] sym = wa.symboltable.get(i);
                        if (sym[1] == str[1]) {
                            sym[0] = str[0];
                        }
                    }
                } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    System.out.println(str[1] + "标识符重名，错误");
                    return;
                }
            } else {
                System.out.println("错误，第三个单词应为标识符");
                error();
                return;
            }
            if (tokens[3].location == "{") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[4].location == "public") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[5].location == "static") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[6].location == "void") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[7].location == "main") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[8].location == "(") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[9].location == "String") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[10].location == "[") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[11].location == "]") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[12].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[12].location));
                if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "变量名";
                    str[2] = "String[]";
                    for (int i = 0; i < wa.symboltable.size(); i++) {
                        String[] sym = wa.symboltable.get(i);
                        if (sym[1] == str[1]) {
                            sym[0] = str[0];
                            sym[2] = str[2];
                        }
                    }
                } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                    System.out.println(str[1] + "标识符重名，错误");
                    return;
                }
            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[13].location == ")") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[14].location == "{") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            printStatement();
            if (getToken(wa.token).location == "}") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (getToken(wa.token).location == "}") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
        } else {             //连15个单词都不满足，肯定不满足mainClass的语法要求
            System.out.println("错误");
            error();
            return;
        }
    }

    public void typeDeclaration() {
        if (getToken(wa.token) == null) {
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            classDeclaration();
            classExtendsDeclaration();
            typeDeclaration();
        }
    }

    public void classDeclaration() {
        //声明一个变量用于计数取出的单词个数，方便回退
        int count = 0;
        Token[] tokens = new Token[3];
        for (int t = 0; t < 3; t++) {
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if (tokens[2] != null) {
            if (tokens[0].location == "class") {

            } else {
                System.out.println("错误,第一个单词应是class");
                i = i - count;
                error();
                return;
            }
            if (tokens[1].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[1].location));
                if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "类名";
                    for (int i = 0; i < wa.symboltable.size(); i++) {
                        String[] sym = wa.symboltable.get(i);
                        if (sym[1] == str[1]) {
                            sym[0] = str[0];
                        }
                    }
                } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    System.out.println(str[1] + "标识符重名，错误");
                    return;
                }
            } else {
                System.out.println("错误,第二个单词应是标识符");
                i = i - count;
                error();
                return;
            }
            if (tokens[2].location == "{") {

            } else {
                System.out.println("错误,第三个单词应是{");
                i = i - count;
                error();
                return;
            }
            varDeclaration();
            methodDeclaration();
            Token t = getToken(wa.token);
            if (t != null) {
                count++;
                if (t.location == "}") {

                } else {
                    System.out.println("错误");
                    i = i - count;
                    error();
                    return;
                }
            } else {
                System.out.println("错误");
                i = i - count;
                error();
                return;
            }
        } else {
            System.out.println("错误");
            i = i - count;
            error();
            return;
        }
    }

    //跟上面那个不同，这个匹配失败就报错，不需要回退了
    public void classExtendsDeclaration() {
        Token[] tokens = new Token[5];
        for (int t = 0; t < 5; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[4] != null) {
            if (tokens[0].location == "class") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[1].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[1].location));
                if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "类名";
                    for (int i = 0; i < wa.symboltable.size(); i++) {
                        String[] sym = wa.symboltable.get(i);
                        if (sym[1] == str[1]) {
                            sym[0] = str[0];
                        }
                    }
                } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    System.out.println(str[1] + "标识符重名，错误");
                    return;
                }
            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[2].location == "extends") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            //extends后面的标识符必须是已经声明了的类名
            if (tokens[3].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[3].location));
                if (str[0] == "类名") {         //检查标识符是否为类名

                } else {                 //不是类名就要报错
                    System.out.println(str[1] + "这个标识符不是类名，不能用于类的继承");
                    return;
                }
            } else {
                System.out.println("错误");
                error();
                return;
            }
            if (tokens[4].location == "{") {

            } else {
                System.out.println("错误");
                error();
                return;
            }
            varDeclaration();
            methodDeclaration();
            Token t = getToken(wa.token);
            if (t != null) {
                if (t.location == "}") {

                } else {
                    System.out.println("错误");
                    error();
                    return;
                }
            } else {
                System.out.println("错误");
                error();
                return;
            }

        } else {
            System.out.println("错误");
            error();
            return;
        }
    }

    public void varDeclaration() {
        if (getToken(wa.token) == null) {     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            String ty = typemethod();
            //声明一个变量用于计数取出的单词个数，方便回退
            int count = 0;
            //取两个单词用于匹配
            Token[] tokens = new Token[2];
            for (int t = 0; t < 2; t++) {
                tokens[t] = getToken(wa.token);
                if (tokens[t] != null)
                    count++;
            }
            if (tokens[1] != null) {
                if (tokens[0].category == "标识符") {
                    String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                    if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                        str[0] = "变量名";
                        str[2] = ty;
                        for (int i = 0; i < wa.symboltable.size(); i++) {
                            String[] sym = wa.symboltable.get(i);
                            if (sym[1] == str[1]) {
                                sym[0] = str[0];
                                sym[2] = str[2];
                            }
                        }
                    } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                        //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                        System.out.println(str[1] + "标识符重名，错误");
                        return;
                    }
                } else {
                    i = i - count;
                    return;
                }
                if (tokens[1].location == ";") {

                } else {
                    i = i - count;
                    return;
                }
                eVarDeclaration();
            } else {
                i = i - count;
                return;
            }
        }
    }

    public void eVarDeclaration() {
        if (getToken(wa.token) == null) {     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            String ty = typemethod();
            //声明一个变量用于计数取出的单词个数，方便回退
            int count = 0;
            //取两个单词用于匹配
            Token[] tokens = new Token[2];
            for (int t = 0; t < 2; t++) {
                tokens[t] = getToken(wa.token);
                if (tokens[t] != null)
                    count++;
            }
            if (tokens[1] != null) {
                if (tokens[0].category == "标识符") {
                    String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                    if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                        str[0] = "变量名";
                        str[2] = ty;
                        for (int i = 0; i < wa.symboltable.size(); i++) {
                            String[] sym = wa.symboltable.get(i);
                            if (sym[1] == str[1]) {
                                sym[0] = str[0];
                                sym[2] = str[2];

                            }
                        }
                    } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                        //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                        System.out.println(str[1] + "标识符重名，错误");
                        return;
                    }
                } else {
                    i = i - count;
                    return;
                }
                if (tokens[1].location == ";") {

                } else {
                    i = i - count;
                    return;
                }
                eVarDeclaration();
            } else {
                i = i - count;
                return;
            }
        }
    }

    public String typemethod() {
        int count = 0;
        Token[] tokens = new Token[3];
        for (int t = 0; t < 3; t++) {
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if (tokens[0] != null) {
            if (tokens[0].location == "int") {
                if (tokens[2] != null) {
                    if (tokens[2].location == "]" && tokens[1].location == "[")
                        return "int[]";
                    else if (tokens[2].location == "]" && tokens[1].location != "[") {
                        System.out.println("类型错误，没有int" + tokens[1].location + "]这样的类型");
                        return null;            //返回null表示原代码中的用户写的变量类型是错误的或者没有写类型
                    } else if (tokens[1].location == "[" && tokens[2].location != "]") {
                        i--;
                        System.out.println("类型错误，没有int[这样的类型");
                        return null;
                    } else {
                        i = i - 2;        //后面两个单词既不是[又不是]时，要回退两个单词
                        return "int";
                    }
                } else {
                    if (tokens[1] != null) {
                        if (tokens[1].location == "[") {
                            System.out.println("类型错误，没有int[这样的类型");
                            return null;
                        } else {
                            i--;
                            return "int";
                        }
                    } else {
                        return "int";
                    }
                }
            } else if (tokens[0].location == "boolean") {
                i = i - count + 1;          //回退
                return "boolean";
            } else if (tokens[0].location == "char") {
                i = i - count + 1;          //回退
                return "char";
            } else if (tokens[0].location == "double") {
                i = i - count + 1;          //回退
                return "double";
            } else if (tokens[0].location == "String") {
                i = i - count + 1;          //回退
                return "String";
            } else {          //用户写了规定的基本类型之外的类型，此时要判断是否合法
                if (tokens[0].category == "标识符") {          //此时要判断这个标识符是否是之前已经声明过的类名
                    String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                    if (str[0] == "类名") {         //检查标识符是否为类名
                        i = i - count + 1;
                        return str[1];
                    } else {                 //不是类名就要报错
                        i = i - count + 1;
                        System.out.println(str[1] + "这个标识符不是已经声明过的类名，不是变量的一个合法类型");
                        return null;
                    }
                } else {
                    i = i - count + 1;
                    System.out.println(tokens[0].location + "这个名字不是已经声明过的类名，不是变量的一个合法类型");
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public void methodDeclaration() {
        if (getToken(wa.token) == null) {
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            //匹配第一个单词
            Token t1 = getToken(wa.token);
            if (t1.location == "}") {        //后面直接就是}说明methodDeclaration为空
                i--;
                return;
            }else {
                if (getToken(wa.token).location == "public") {

                } else {
                    System.out.println("错误");
                    error();
                    return;
                }
                String ty = typemethod();
                //取两个单词用于匹配
                Token[] tokens = new Token[2];
                for (int t = 0; t < 2; t++) {
                    tokens[t] = getToken(wa.token);
                }
                if (tokens[1] != null) {
                    if (tokens[0].category == "标识符") {
                        String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                        if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                            str[0] = "方法名";
                            str[2] = ty;
                            for (int i = 0; i < wa.symboltable.size(); i++) {
                                String[] sym = wa.symboltable.get(i);
                                if (sym[1] == str[1]) {
                                    sym[0] = str[0];
                                    sym[2] = str[2];

                                }
                            }
                        } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                            //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                            System.out.println(str[1] + "方法名重名，错误");
                            return;
                        }
                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (tokens[1].location == "(") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    formalParameterList();
                    //将这个方法对应的参数的信息填入到符号表中相应的位置
                    String[] meth = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                    meth[3] = paraType;
                    meth[4] = paraNumber;
                    if (getToken(wa.token).location == ")") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (getToken(wa.token).location == "{") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    varDeclaration();
                    statement();
                    Token t2 = getToken(wa.token);
                    if (t2!=null && t2.location == "return") {      //会报异常吗？得测试一下

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    expression();
                    if (getToken(wa.token).location == ";") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (getToken(wa.token).location == "}") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    eMethodDeclaration();
                } else {
                    System.out.println("错误");
                    error();
                    return;
                }
            }
        }

    }

    public void eMethodDeclaration() {
        if (getToken(wa.token) == null) {     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            Token t1 = getToken(wa.token);
            if(t1.location==")"){       //说明为空
                i--;
                return;
            }else {
                if (getToken(wa.token).location == "public") {

                } else {
                    System.out.println("错误");
                    error();
                    return;
                }
                String ty = typemethod();
                //取两个单词用于匹配
                Token[] tokens = new Token[2];
                for (int t = 0; t < 2; t++) {
                    tokens[t] = getToken(wa.token);
                }
                if (tokens[1] != null) {
                    if (tokens[0].category == "标识符") {
                        String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                        if (str[0] == null) {       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                            str[0] = "方法名";
                            str[2] = ty;
                            for (int i = 0; i < wa.symboltable.size(); i++) {
                                String[] sym = wa.symboltable.get(i);
                                if (sym[1] == str[1]) {
                                    sym[0] = str[0];
                                    sym[2] = str[2];

                                }
                            }
                        } else {      //不为null说明之前就有语句声明了这个标识符，此时重名了
                            //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                            System.out.println(str[1] + "方法名重名，错误");
                            return;
                        }
                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (tokens[1].location == "(") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    formalParameterList();
                    //将这个方法对应的参数的信息填入到符号表中相应的位置
                    String[] meth = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                    meth[3] = paraType;
                    meth[4] = paraNumber;
                    if (getToken(wa.token).location == ")") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (getToken(wa.token).location == "{") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    varDeclaration();
                    statement();
                    if (getToken(wa.token).location == "return") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    expression();
                    if (getToken(wa.token).location == ";") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    if (getToken(wa.token).location == "}") {

                    } else {
                        System.out.println("错误");
                        error();
                        return;
                    }
                    eMethodDeclaration();
                } else {
                    System.out.println("错误");
                    error();
                    return;
                }
            }
        }
    }

    public void formalParameterList() {
        //每一次进行方法的参数判断之前先将记录参数的变量初始化
        paraNumber = "0";
        paraType = null;
        if (getToken(wa.token) == null) {     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        } else {
            //取出来了一个单词，需要回退
            i--;
            Token t = getToken(wa.token);
            if(t.location == ")"){          //空参数
                i--;
                return;
            }else {                 //不是空参数
                formalParameter();
                formalParameterRest();
            }
        }
    }


    public void formalParameter(){
        String ty = typemethod();
        //取一个单词用于匹配
        Token[] tokens = new Token[1];
        for(int t = 0;t<1;t++){
            tokens[t] = getToken(wa.token);
        }
        if(tokens[0]!=null){
            if(tokens[0].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]==null){       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "变量名";
                    str[2] = ty;
                    int pa = Integer.parseInt(paraNumber);
                    paraNumber = String.valueOf(++pa);
                    //分割paraType时，先把开头的,给去掉，再根据,进行分割
                    paraType = paraType+","+ty;
                    for(int i =0;i<wa.symboltable.size();i++){
                        String[] sym = wa.symboltable.get(i);
                        if(sym[1] == str[1]){
                            sym[0] = str[0];
                            sym[2] = str[2];
                        }
                    }
                }else{      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                    System.out.println(str[1]+"标识符重名，错误");
                    i--;
                    return;
                }
            }else {
                System.out.println("错误");
                i--;
                return;
            }

        }else {
            System.out.println("错误");
            i--;
            return;
        }

    }

    public void formalParameterRest(){
        //取一个单词用于匹配
        Token[] tokens = new Token[1];
        for(int t = 0;t<1;t++){
            tokens[t] = getToken(wa.token);
        }
        if(tokens[0]!=null){
            if(tokens[0].location == "，"){

            }else {
                System.out.println("错误");
                error();
                return;
            }
            formalParameter();

        }else {
            System.out.println("错误");
            error();
            return;
        }
    }

    public void statement(){
        Token t1 = getToken(wa.token);
        if(t1==null){
            return;
        }else {
            if(t1.location=="{"){
                i--;
                block();
            }else if(t1.category=="标识符"){
                Token t2 = getToken(wa.token);
                if(t2==null){
                    System.out.println("第"+t1.line+"行的语句不完整，不是一个合法的语句");
                    return;
                }else {
                    if(t2.location=="="){
                        i = i-2;
                        assignmentStatement();
                    }else {
                        i = i-2;
                        arrayAssignmentStatement();
                    }
                }
            }else if(t1.location=="if"){
                i--;
                ifStatement();
            }else if(t1.location=="while"){
                i--;
                whileStatement();
            }else if(t1.location=="System"){
                i--;
                printStatement();
            }else{
                declareAssignmentStatement();
                eStatement();
                statement();
            }
        }

    }

    public void eStatement(){
        if(getToken(wa.token) == null){     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        }else {
            //取出来了一个单词，需要回退
            i--;
            block();
            assignmentStatement();
            arrayAssignmentStatement();
            ifStatement();
            whileStatement();
            printStatement();
        }
    }

    public void block(){
        if(getToken(wa.token) == null){     //因为允许为空串，所以如果没有单词可以匹配了，直接return
            return;
        }else {
            //取出来了一个单词，需要回退
            i--;
            Token t1 = getToken(wa.token);
            if(t1.location=="return"){      //说明为空
                i--;
                return;
            }else {                 //不为空，如果进入这个，那么不匹配就真的不匹配，不需要回退了
                //取一个单词用于匹配
                Token[] tokens = new Token[1];
                for(int t = 0;t<1;t++){
                    tokens[t] = getToken(wa.token);
                }
                if(tokens[0]!=null){
                    if(tokens[0].location == "{"){

                    }else {
                       System.out.println("第"+tokens[0].line+"行有错误");
                        return;
                    }
                    statement();
                    block();
                    Token t2 = getToken(wa.token);
                    if(t2!=null&&t2.location=="}"){

                    }else {
                        System.out.println("错误");
                        return;
                    }
                }else {
                    System.out.println("错误");
                    return;
                }
            }
        }
    }

    public void assignmentStatement() {
        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[1] != null) {
            if (tokens[0].category == "标识符") {      //赋值语句，标识符要已经被声明过，存在了
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if (str[0] == "变量名"&&str[2]!="int[]"&&str[2]!=null) {      //变量已经被声明过，则合法

                }else {         //变量未被声明过或者标识符被声明过，但不是变量而是方法，类名
                    System.out.println("第"+tokens[0].line+"行,标识符"+str[1]+"未被声明过或者是方法名、类名、数组名，不符合赋值语句的要求");
                }

            } else {
                System.out.println("第"+tokens[0].line+"行,"+tokens[0].location+"不是标识符，不符合赋值语句的要求");
            }
            if(tokens[1].location == "="){

            }else {
                System.out.println("第"+tokens[1].line+"行,"+tokens[1].location+"不是=，不符合赋值语句的要求");
            }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null && t2.location ==";"){

            }else {         //注意：可能会报异常！
                System.out.println("第"+t2.line+"行,"+t2.location+"不是;，不符合赋值语句的要求");
            }
        } else {
            System.out.println("第"+tokens[0].line+"行少了一些单词，不符合赋值语句的要求");
        }
    }

    public void arrayAssignmentStatement() {
        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[1] != null) {
            if (tokens[0].category == "标识符") {
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if (str[0] == "变量名" && str[2]=="int[]") {      //变量已经被声明过，则合法

                }else {
                    System.out.println("第"+tokens[0].line+"行,标识符"+str[1]+"未被声明过或者不是数组名，不符合数组赋值语句的要求");
                }

            } else {
                System.out.println("第"+tokens[0].line+"行,"+tokens[0].location+"不是标识符，不符合数组赋值语句的要求");
            }
            if(tokens[1].location == "["){

            }else {
                System.out.println("第"+tokens[1].line+"行"+tokens[1].location+"不是[，不符合数组赋值语句的要求");
           }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location =="]"){

            }else {         //可能会有异常
                System.out.println("第"+t2.line+"行"+t2.location+"不是]，不符合数组赋值语句的要求");
            }
            Token t3 = getToken(wa.token);
            if(t3!=null&&t3.location =="="){

            }else {     //可能会有异常
                System.out.println("第"+t3.line+"行"+t3.location+"不是=，不符合数组赋值语句的要求");
            }
            expression();
            Token t4 = getToken(wa.token);
            if(t4!=null&&t4.location ==";"){

            }else {     //可能会有异常，得想办法消除
                System.out.println("第"+t4.line+"行"+t4.location+"不是;，不符合数组赋值语句的要求");
            }
        } else {
            System.out.println("错误");
        }
    }

    public void ifStatement() {
        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[1] != null) {
            if(tokens[0].location == "if"){

            }else {
                System.out.println("第"+tokens[0].line+"行"+tokens[0].location+"不是if，不符合if语句的要求");
            }
            if(tokens[1].location == "("){

            }else {
                System.out.println("第"+tokens[1].line+"行"+tokens[1].location+"不是(，不符合if语句的要求");
            }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location ==")"){

            }else {
                System.out.println("第"+t2.line+"行"+t2.location+"不是)，不符合if语句的要求");
            }
            statement();
            Token t3 = getToken(wa.token);
            if(t3!=null&&t3.location =="else"){

            }else {
                System.out.println("第"+t3.line+"行"+t3.location+"不是else，不符合if语句的要求");
            }
            statement();

        } else {
            System.out.println("错误");
        }
    }

    public void whileStatement() {
        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[1] != null) {
            if(tokens[0].location == "while"){

            }else {
                System.out.println("第"+tokens[0].line+"行"+tokens[0].location+"不是while，不符合while语句的要求");
            }
            if(tokens[1].location == "("){

            }else {
                System.out.println("第"+tokens[1].line+"行"+tokens[1].location+"不是(，不符合while语句的要求");
            }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location ==")"){

            }else {
                System.out.println("第"+t2.line+"行"+t2.location+"不是)，不符合while语句的要求");
            }
            statement();
        } else {
            System.out.println("错误");
        }
    }

    public void printStatement() {
        //取六个单词用于匹配
        Token[] tokens = new Token[6];
        for (int t = 0; t < 6; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[5] != null) {
            if(tokens[0].location == "System"){

            }else {
                System.out.println("第"+tokens[0].line+"行"+tokens[0].location+"不是System，不符合输出语句的要求");
            }
            if(tokens[1].location == "."){

            }else {
                System.out.println("第"+tokens[1].line+"行"+tokens[1].location+"不是.，不符合输出语句的要求");
            }
            if(tokens[2].location == "out"){

            }else {
                System.out.println("第"+tokens[2].line+"行"+tokens[2].location+"不是out，不符合输出语句的要求");
            }
            if(tokens[3].location == "."){

            }else {
                System.out.println("第"+tokens[3].line+"行"+tokens[3].location+"不是.，不符合输出语句的要求");
            }
            if(tokens[4].location == "println"){

            }else {
                System.out.println("第"+tokens[4].line+"行"+tokens[4].location+"不是println，不符合输出语句的要求");
            }
            if(tokens[5].location == "("){

            }else {
                System.out.println("第"+tokens[5].line+"行"+tokens[5].location+"不是(，不符合输出语句的要求");
            }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location ==")"){

            }else {         //可能有异常
                System.out.println("第"+t2.line+"行"+t2.location+"不是)，不符合输出语句的要求");
            }
            Token t3 = getToken(wa.token);
            if(t3!=null&&t3.location ==";"){

            }else {         //可能有异常
                System.out.println("第"+t3.line+"行"+t3.location+"不是;，不符合输出语句的要求");
            }

        } else {
            System.out.println("错误");
        }
    }

    public void declareAssignmentStatement() {
        int j = i;      //记录i的原值，方便回溯
        boolean b = false;      //记录是否需要回溯
        String ty = typemethod();       //typemethod方法里面如果返回null，需要报错的

        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if(tokens[1]!=null){
            if(tokens[0].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]==null){       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "变量名";
                    str[2] = ty;
                    for(int i =0;i<wa.symboltable.size();i++){
                        String[] sym = wa.symboltable.get(i);
                        if(sym[1] == str[1]){
                            sym[0] = str[0];
                            sym[2] = str[2];

                        }
                    }
                }else{      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                    System.out.println(str[1]+"标识符重名，错误");
                }
            }else {
                b = true;       //不匹配，需要回溯
            }
            if(tokens[1].location == "="){

            }else {
                b = true;
            }
            expression();
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location ==";"){

            }else {
                b = true;
            }
        }else {
            b = true;
        }
        if(b)           //需要回溯
            i = j;
    }

    public String expression() {                //表达式返回一个预计的类型
        int j = i;
        if(getToken(wa.token)==null){
            System.out.println("错误");
            return null;
        }else {
            i--;
            Token t1 = primaryExpressionS();
            if(t1!=null){
                Token t2 = getToken(wa.token);
                if(t2.location=="&"){
                    i = j;
                    return AndExpression();
                }else if(t2.location=="|"){
                    i=j;
                    return orExpression();
                }else{
                }
            }
            i = j;              //回溯
            Token t3 = primaryExpressionF();
            if(t3!=null){
                Token t4 = getToken(wa.token);
                if(t4.location=="<"){
                    i = j;
                    return compareExpression();
                }else if(t4.location=="+"){
                    i = j;
                    return plusExpression();
                }else if(t4.location=="-"){
                    i = j;
                    return minusExpression();
                }else if(t4.location=="*"){
                    i = j;
                    return TimesExpression();
                }else if(t4.location=="/"){
                    i = j;
                    return divideExpression();
                }else if(t4.location==">"){
                    i = j;
                    return maxsExpression();
                }else if(t4.location=="<="){
                    i = j;
                    return minusEqExpression();
                }else if(t4.location==">="){
                    i = j;
                    return maxsEqExpression();
                }else {
                    i = j;
                }
            }
            Token[] tokens = new Token[3];      //取三个单词
            for(int k = 0;k<3;k++){
                tokens[k] = getToken(wa.token);
            }
            if(tokens[2]!=null){
                if(tokens[0].category=="标识符"){
                    if(tokens[1].location=="["){
                        i = j;
                        return arrayLookup();
                    }else if(tokens[1].location=="."){
                        if(tokens[2].location=="length"){
                            i = j;
                            return arraylength();
                        }else {
                            i = j;
                            return messageSend();
                        }
                    }else if(tokens[1].location=="("){
                        i = j;
                        return directMessageSend();
                    }else {
                        i = j;
                    }
                }else {
                    i = j;
                }
            }else {
                System.out.println("第"+tokens[0].line+"行语句不完整，错误");
                return null;
            }
            i = j;
            return primaryExpression();
        }

    }

    public String AndExpression() {
        boolean erro = false;       //记录这个表达式匹配的语句是否有错误
        Token t1 = primaryExpressionS();
        if(t1.category == "标识符"){       //还需要补充
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&str[2]=="boolean"){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足&表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="&"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足&表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionS();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&str[2]=="boolean"){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足&表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足&表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public String compareExpression() {
        boolean erro = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足<表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="<"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足<表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足<表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足<表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public String plusExpression() {
        boolean erro = false;
        boolean isdou = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                if(str[2]=="double"){
                    isdou = true;
                }
            }else {
                System.out.println("第"+t1.line+"行有错误，不满足加法表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="+"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足加法表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                    if(str[2]=="double"){
                        isdou = true;
                    }
                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足加法表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足加法表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            if(isdou)
                return "double";
            else
                return "int";
        }
    }

    public String minusExpression() {
        boolean erro = false;
        boolean isdou = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                if(str[2]=="double"){
                    isdou = true;
                }
            }else {
                System.out.println("第"+t1.line+"行有错误，不满足减法表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="-"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足减法表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                    if(str[2]=="double"){
                        isdou = true;
                    }
                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足减法表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足减法表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            if(isdou)
                return "double";
            else
                return "int";
        }
    }

    public String TimesExpression() {
        boolean erro = false;
        boolean isdou = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                if(str[2]=="double"){
                    isdou = true;
                }
            }else {
                System.out.println("第"+t1.line+"行有错误，不满足乘法表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="*"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足乘法表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                    if(str[2]=="double"){
                        isdou = true;
                    }
                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足乘法表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足乘法表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            if(isdou)
                return "double";
            else
                return "int";
        }
    }

    public String arrayLookup() {
        //取两个单词用于匹配
        boolean erro = false;
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if(tokens[1]!=null){
            if(tokens[0].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]=="变量名"&&str[2]=="int[]"){       //正确

                }else{
                        System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
                        erro = true;
                }
            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
                }
                erro = true;
            }
            if(tokens[1].location == "["){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
                }
                erro = true;
            }
            Token t1 = primaryExpressionF();
            if(t1!=null){
                if(t1.category=="浮点数"){
                    if(!erro){
                        System.out.println("第"+t1.line+"行有错误，不满足访问数组元素表达式的要求");
                    }
                    erro = true;
                }
                if(t1.category == "标识符"){       //还需要补充
                    String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
                    if(str[0]=="变量名"&&str[2]=="int"){       //满足才是正确的

                    }else {
                        if(!erro){
                            System.out.println("第"+t1.line+"行有错误，不满足访问数组元素表达式的要求");
                        }
                        erro = true;
                    }
                }
            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
                }
                erro = true;
            }
            Token t2 = getToken(wa.token);
            if(t2!=null&&t2.location =="]"){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
                }
                erro = true;
            }
        }else {
            if(!erro){
                System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组元素表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "int";
        }
    }

    public String arraylength() {
        boolean erro = false;
        //取三个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 3; t++) {
            tokens[t] = getToken(wa.token);
        }
        if(tokens[2]!=null){
            if(tokens[0].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]=="变量名"&&str[2]=="int[]"){       //正确

                }else{
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组长度表达式的要求");
                    erro = true;
                }
            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组长度表达式的要求");
                }
                erro = true;
            }
            if(tokens[1].location == "."){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组长度表达式的要求");
                }
                erro = true;
            }
            if(tokens[2].location == "length"){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组长度表达式的要求");
                }
                erro = true;
            }
        }else {
            if(!erro){
                System.out.println("第"+tokens[0].line+"行有错误，不满足访问数组长度表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "int";
        }
    }

    public String messageSend() {
        String re = null;       //记录返回值类型
        String paralist = null;  //记录参数列表，方便检测
        boolean erro = false;
        //取四个单词用于匹配
        Token[] tokens = new Token[4];
        for (int t = 0; t < 4; t++) {
            tokens[t] = getToken(wa.token);
        }
        if(tokens[3]!=null){
            if(tokens[0].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]=="变量名"&&str[2]!=null&&str[2]!="int[]"&&str[2]!="int"&&str[2]!="boolean"&&str[2]!="double"&&str[2]!="char"&&str[2]!="String"){
                    //这个变量是自定义类型
                }else {
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                    erro = true;
                }
            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }
            if(tokens[1].location == "."){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }
            if(tokens[2].category == "标识符"){           //必须是方法名
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[2].location));
                if(str[0]=="方法名"){      //正确
                    re = str[2];
                    paralist = str[3];
                }else {
                    if(!erro){
                        System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                    }
                    erro = true;
                }
            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }
            if(tokens[3].location == "("){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }
            if(erro)
                Para(paralist,erro);
            else
                erro = Para(paralist,erro);
            Token t3 = getToken(wa.token);
            if(t3!=null&&t3.location ==")"){

            }else {
                if(!erro){
                    System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }

        }else {
            if(!erro){
                System.out.println("第"+tokens[0].line+"行有错误，不满足方法调用表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return re;
        }
    }

    public String directMessageSend() {
        String re = null;       //记录返回值类型
        String paralist = null;  //记录参数列表，方便检测
        boolean erro = false;
        //取两个单词用于匹配
        Token[] tokens = new Token[2];
        for (int t = 0; t < 2; t++) {
            tokens[t] = getToken(wa.token);
        }
        if (tokens[0].category == "标识符") {           //必须是方法名
            String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
            if (str[0] == "方法名") {      //正确
                re = str[2];
                paralist = str[3];
            } else {
                if (!erro) {
                    System.out.println("第" + tokens[0].line + "行有错误，不满足方法调用表达式的要求");
                }
                erro = true;
            }
        } else {
            if (!erro) {
                System.out.println("第" + tokens[0].line + "行有错误，不满足方法调用表达式的要求");
            }
            erro = true;
        }
        if (tokens[1].location == "(") {

        } else {
            if (!erro) {
                System.out.println("第" + tokens[0].line + "行有错误，不满足方法调用表达式的要求");
            }
            erro = true;
        }
        if(erro)
            Para(paralist,erro);         //建议给Para函数也加一个参数，erro参数
        else
            erro = Para(paralist,erro);
        Token t3 = getToken(wa.token);
        if (t3 != null && t3.location == ")") {

        } else {
            if (!erro) {
                System.out.println("第" + tokens[0].line + "行有错误，不满足方法调用表达式的要求");
            }
            erro = true;
        }
        if (erro) {           //有错误
            return null;
        } else {         //正确
            return re;
        }
    }

    public String divideExpression() {
        boolean erro = false;
        boolean isdou = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                if(str[2]=="double"){
                    isdou = true;
                }
            }else {
                System.out.println("第"+t1.line+"行有错误，不满足除法表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="/"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足除法表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的
                    if(str[2]=="double"){
                        isdou = true;
                    }
                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足除法表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足除法表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            if(isdou)
                return "double";
            else
                return "int";
        }
    }

    public String maxsExpression() {
        boolean erro = false;       //记录这个表达式匹配的语句是否有错误
        Token t1 = primaryExpressionS();
        if(t1.category == "标识符"){       //还需要补充
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足>表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location ==">"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足>表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionS();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足>表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足>表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public String orExpression() {
        boolean erro = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&str[2]=="boolean"){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足|表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="|"){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足|表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&str[2]=="boolean"){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足|表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足|表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public String maxsEqExpression() {
        boolean erro = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足>=表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location ==">="){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足>=表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足>=表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足>=表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public String minusEqExpression() {
        boolean erro = false;
        Token t1 = primaryExpressionF();
        if(t1.category == "标识符"){       //还需要补充，括号的情况没考虑
            String[] str = wa.symboltable.get(Integer.parseInt(t1.location));
            if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

            }else {
                System.out.println("第"+t1.line+"行有错误，不满足<=表达式的要求");
                erro = true;
            }
        }
        Token t2 = getToken(wa.token);
        if(t2.location =="<="){

        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足<=表达式的要求");
            }
            erro = true;
        }
        Token t3 = primaryExpressionF();
        if(t3!=null){
            if(t3.category == "标识符"){       //还需要补充
                String[] str = wa.symboltable.get(Integer.parseInt(t3.location));
                if(str[0]=="变量名"&&(str[2]=="int"||str[2]=="double")){       //满足才是正确的

                }else {
                    if(!erro){
                        System.out.println("第"+t2.line+"行有错误，不满足<=表达式的要求");
                    }
                    erro = true;
                }
            }
        }else {
            if(!erro){
                System.out.println("第"+t2.line+"行有错误，不满足<=表达式的要求");
            }
            erro = true;
        }
        if(erro){           //有错误
            return null;
        }else {         //正确
            return "boolean";
        }
    }

    public void uFNExpression() {
        AndExpression();
        primaryExpressionS();
        orExpression();

    }

    public void uFBExpression() {
        AndExpression();
        compareExpression();
        plusExpression();
        minusExpression();
        primaryExpressionS();
        divideExpression();
        maxsExpression();
        orExpression();
        maxsEqExpression();
        minusEqExpression();

    }

    public String primaryExpression() {
        primaryExpressionF();
        primaryExpressionS();
        arrayAllocationExpression();
        allocationExpression();
    }

    public Token primaryExpressionF() {
        int j =i;               //记录刚进来时i的状态
        Token t1 = getToken(wa.token);
        if(t1!=null){
            if(t1.category=="标识符" || t1.category=="整数" || t1.category=="浮点数"){
                return t1;
            }else if(t1.location=="("){
                i = j;
                bracketExpression();        //也要返回一个有价值的信息才行

            }else {     //说明和primaryExpressionF不匹配
                i = j;      //回溯
                return null;
            }
        }else {     //可以提前在之前的方法里看是否为null，这里应该进入不了，可以去掉
            return null;
        }
    }

    public Token primaryExpressionS() {
        int j =i;               //记录刚进来时i的状态
        Token t1 = getToken(wa.token);
        if(t1!=null){
            if(t1.category=="标识符"||t1.location=="true"||t1.location=="false"){
                return t1;
            }else if(t1.location=="!"){
                i=j;
                notExpression();
            }else if(t1.location=="("){
                i = j;
                bracketExpression();
            }else {         //不匹配，回退
                i = j;
                return null;
            }
        }else {
            return null;
        }
    }


    public void arrayAllocationExpression(){
        //取三个单词用于匹配
        int count = 0;
        Token[] tokens = new Token[2];
        for (int t = 0; t < 3; t++) {
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if (tokens[2] != null) {
            if(tokens[0].location == "new"){

            }else {
                i = i-count;
                return;
            }
            if(tokens[1].location == "int"){

            }else {
                i = i-count;
                return;
            }
            if(tokens[2].location =="["){

            }else {
                i = i-count;
                return;
            }
            if(getToken(wa.token).location =="]"){

            }else {
                i = i-count-1;
                return;
            }

        } else {
            System.out.println("错误");
            i = i-count;
            return;
        }
    }

    public void allocationExpression(){
        //取四个单词用于匹配
        int count = 0;
        Token[] tokens = new Token[2];
        for (int t = 0; t < 4; t++) {
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if (tokens[3] != null) {
            if(tokens[0].location == "new"){

            }else {
                i = i-count;
                return;
            }
            if(tokens[1].category == "标识符"){
                String[] str = wa.symboltable.get(Integer.parseInt(tokens[0].location));
                if(str[0]==null){       //遍历符号表，将与这个标识符同名的其他标识符的类别设置成与其一样
                    str[0] = "变量名";
                    for(int i =0;i<wa.symboltable.size();i++){
                        String[] sym = wa.symboltable.get(i);
                        if(sym[1] == str[1]){
                            sym[0] = str[0];
                            sym[2] = str[2];

                        }
                    }
                }else{      //不为null说明之前就有语句声明了这个标识符，此时重名了
                    //这里还有不小的问题，涉及到了对变量的作用域范围的探讨
                    System.out.println(str[1]+"标识符重名，错误");
                    return;
                }
            }else {
                System.out.println("错误");
                i = i - count;
                error();
                return;
            }
            if(tokens[2].location =="("){

            }else {
                i = i-count;
                return;
            }
            if(tokens[3].location ==")"){

            }else {
                i = i-count;
                return;
            }


        } else {
            System.out.println("错误");
            i = i-count;
            return;
        }
    }

    public void notExpression(){
        //取一个单词用于匹配
        int count = 0;
        Token[] tokens = new Token[1];
        for(int t = 0;t<1;t++){
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if(tokens[0]!=null){
            if(tokens[0].location == "！"){

            }else {
                System.out.println("错误");
                error();
                return;
            }
            uFNExpression();

        }else {
            System.out.println("错误");
            error();
            return;
        }
    }
    public void bracketExpression(){
        //取一个单词用于匹配
        int count = 0;
        Token[] tokens = new Token[1];
        for(int t = 0;t<1;t++){
            tokens[t] = getToken(wa.token);
            if (tokens[t] != null)
                count++;
        }
        if(tokens[0]!=null){
            if(tokens[0].location == "("){

            }else {
                System.out.println("错误");
                error();
                return;
            }
            uFBExpression();
            if(getToken(wa.token).location ==")"){

            }else {
                i = i-count-1;
                return;
            }

        }else {
            System.out.println("错误");
            error();
            return;
        }
    }

    public boolean Para(String paralist,boolean erro){
        if(getToken(wa.token) == null){
            return true;
        }else{
            //取出来了一个单词，需要回退
            i--;
            Token t = getToken(wa.token);
            if(t.location==")"){        //如果左括号后面就是右括号，那么是空参数，是正确的
                if(paralist==null){
                    //声明的函数也是空参数才对
                    i--;
                    return false;
                }else {
                    if(!erro){
                        System.out.println("第"+t.line+"行有错误");
                        erro = true;
                    }
                    return true;
                }
            }else {                     //用户写了参数的情况，要考虑原本函数不需要参数
                if(paralist!=null){
                    String str = paralist.substring(1);
                    String[] para = str.split(",");
                    if(t.category=="标识符"){
                        String[] s = wa.symboltable.get(Integer.parseInt(t.location));
                        if(s[0]=="变量名"&&s[2]==para[0]){

                        }else {
                            if(!erro){
                                System.out.println("第"+t.line+"行有错误");
                                erro = true;
                            }
                        }
                    }else if(t.category=="整数"){
                        if(para[0]=="int"){

                        }else {
                            if(!erro){
                                System.out.println("第"+t.line+"行有错误");
                                erro = true;
                            }
                        }
                    }else if(t.category=="浮点数"){
                        if(para[0]=="double"){

                        }else {
                            if(!erro){
                                System.out.println("第"+t.line+"行有错误");
                                erro = true;
                            }
                        }
                    }else {
                        if(!erro){
                            System.out.println("第"+t.line+"行有错误");
                            erro = true;
                        }
                    }
                    Token t2 = getToken(wa.token);
                    if(t2==null){
                        System.out.println("第"+t.line+"行不完整，错误");
                        erro = true;
                        return true;
                    }else {
                        i--;
                        return y(para,1,erro);
                    }
                }else {             //声明的函数的参数列表为空
                    if(!erro){
                        System.out.println("第"+t.line+"行有错误");
                        erro = true;
                    }
                    Token t2 = getToken(wa.token);
                    while(t2!=null&&t2.location!=")"){
                        t2 = getToken(wa.token);
                    }
                    i--;
                    return true;
                }
            }
        }
    }

    public boolean y(String[] para , int j, boolean erro){
        Token t = getToken(wa.token);
        if(t.location==")"){
            if(j<para.length){      //用户写的函数的参数与声明的不符，错误
                if(!erro){
                    System.out.println("第"+t.line+"行有错误");
                    erro = true;
                }
                i--;
                return true;
            }else {
                i--;
                return false;
            }
        }else {
           if(t.location==","){

           }else {
               if(!erro){
                   System.out.println("第"+t.line+"行有错误");
                   erro = true;
               }
           }
           if(j<para.length){       //说明将要匹配的参数是声明的函数的参数数量范围内的，没有超过
               Token t1 = getToken(wa.token);
               if(t1!=null&&t1.category=="标识符"){
                   String[] s = wa.symboltable.get(Integer.parseInt(t1.location));
                   if(s[0]=="变量名"&&s[2]==para[j]){

                   }else {
                       if(!erro){
                           System.out.println("第"+t.line+"行有错误");
                           erro = true;
                       }
                   }
               }else if(t1!=null&&t1.category=="整数"){
                   if(para[j]=="int"){

                   }else {
                       if(!erro){
                           System.out.println("第"+t.line+"行有错误");
                           erro = true;
                       }
                   }
               }else if(t1!=null&&t1.category=="浮点数"){
                   if(para[j]=="double"){

                   }else {
                       if(!erro){
                           System.out.println("第"+t.line+"行有错误");
                           erro = true;
                       }
                   }
               }else {
                   if(!erro){
                       System.out.println("第"+t.line+"行有错误");
                       erro = true;
                   }
               }
               Token t2 = getToken(wa.token);
               if(t2==null){
                   if(!erro){
                       System.out.println("第"+t.line+"行有错误");
                       erro = true;
                   }
                   return true;
               }else {
                   i--;
                   j++;
                   return y(para,j,erro);
               }

           }else {
               if(!erro){
                   System.out.println("第"+t.line+"行有错误");
                   erro = true;
               }
               Token t2 = getToken(wa.token);
               while(t2!=null&&t2.location!=")"){
                   t2 = getToken(wa.token);
               }
               i--;
               return true;
           }
        }
    }
    public static void main(String[] args){
    }
}

