package indi.lucene.learn2;
import org.ansj.lucene6.AnsjAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoSon on 2017/7/5.
 */
public class AnalyzerResult {

    /**
     * 获取指定分词器的分词结果
     * @param analyzeStr
     *            要分词的字符串
     * @param analyzer
     *            分词器
     * @return 分词结果
     */
    public List<String>getAnalyseResult(String analyzeStr,Analyzer analyzer) {
        List<String> response = new ArrayList<String>();
        TokenStream tokenStream = null;
        try {
            //返回适用于fieldName的TokenStream，标记读者的内容。
            tokenStream = analyzer.tokenStream("address", new StringReader(analyzeStr));
            // 语汇单元对应的文本
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            //消费者在使用incrementToken（）开始消费之前调用此方法。
            //将此流重置为干净状态。 有状态的实现必须实现这种方法，以便它们可以被重用，就像它们被创建的一样。
            tokenStream.reset();
            //Consumers（即IndexWriter）使用此方法将流推送到下一个令牌。
            while (tokenStream.incrementToken()) {
                response.add(attr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (tokenStream !=null) {
                try {
                    tokenStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    public static void main(String[] args) {
        String content = "茂名市信宜市丁堡镇片区丁堡街道181号301";
        content = "在应用程序中对象很少只是一个简单的键和值的列表。通常，它们拥有更复杂的数据结构，可能包括日期、地理信息、其他对象或者数组等。\n" +
                "\n" +
                "也许有一天你想把这些对象存储在数据库中。使用关系型数据库的行和列存储，这相当于是把一个表现力丰富的对象挤压到一个非常大的电子表格中：你必须将这个对象扁平化来适应表结构--通常一个字段>对应一列--而且又不得不在每次查询时重新构造对象。";


        System.out.println(content);
        System.out.println("------------------------");

        Analyzer analyzer = new IKAnalyzer();
        List<String> analyseResult = new AnalyzerResult().getAnalyseResult(content, analyzer);
        System.out.println("-------- IKAnalyzer -----------");
        for (String result : analyseResult){
            System.out.print(result+"|");
        }
        System.out.println();


        analyzer = new CJKAnalyzer();
        analyseResult = new AnalyzerResult().getAnalyseResult(content, analyzer);
        System.out.println("-------- CJKAnalyzer -----------");
        for (String result : analyseResult){
            System.out.print(result+"|");
        }
        System.out.println();


        analyzer = new StandardAnalyzer();
        analyseResult = new AnalyzerResult().getAnalyseResult(content, analyzer);
        System.out.println("-------- StandardAnalyzer -----------");
        for (String result : analyseResult){
            System.out.print(result+"|");
        }
        System.out.println();


        analyzer = new AnsjAnalyzer();
        analyseResult = new AnalyzerResult().getAnalyseResult(content, analyzer);
        System.out.println("-------- AnsjAnalyzer -----------");
        for (String result : analyseResult){
            System.out.print(result+"|");
        }
        System.out.println();
    }
}