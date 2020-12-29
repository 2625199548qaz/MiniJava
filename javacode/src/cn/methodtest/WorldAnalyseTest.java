package cn.methodtest;

import com.company.WorldAnalyse;
import org.junit.Test;

import java.io.IOException;

public class WorldAnalyseTest {

    @Test
    public void testLexicalAnalysis()throws IOException {
        WorldAnalyse wa = new WorldAnalyse();
        wa.lexicalAnalysis("D:\\true lexical\\java1.txt");
    }
}
