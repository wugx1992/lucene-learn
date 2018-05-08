package indi.lucene.learn2;

import org.ansj.lucene6.AnsjAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.tinygroup.chineseanalyzer.ChineseAnalyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.apache.lucene.document.TextField.TYPE_STORED;

/**
 * Created by MoSon on 2017/6/30.
 */
public class CreateIndex {

    public static void main(String[] args) throws IOException {
        //定义IndexWriter
        //index是一个相对路径，当前工程
        String pathString = "H:\\lucene\\learn2";
        Path path = FileSystems.getDefault().getPath(pathString, "index");
        Directory directory = FSDirectory.open(path);
        //Directory directory = FSDirectory.open(Paths.get(pathString));
        //将索引创建到内存中
        //Directory directory = new RAMDirectory();


        //定义分词器
        //Analyzer analyzer = new StandardAnalyzer();
        //Analyzer analyzer = new AnsjAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //Analyzer analyzer = new CJKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        //定义文档
        Document document = new Document();
        //定义文档字段
        document.add(new StoredField("id",5499));
        document.add(new Field("title", "小米6", TYPE_STORED));
        document.add(new Field("sellPoint", "骁龙835，6G内存，双摄像头！", TYPE_STORED));
        //写入数据
        indexWriter.addDocument(document);


        //添加新的数据
        document = new Document();
        document.add(new StoredField("id", 8324));
        document.add(new Field("title", "OnePlus5", TYPE_STORED));
        document.add(new Field("sellPoint", "8核，8G运行内存", TYPE_STORED));
        indexWriter.addDocument(document);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();

    }


}