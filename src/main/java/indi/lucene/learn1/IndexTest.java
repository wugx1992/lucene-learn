package indi.lucene.learn1;

/**
 * Created by Credittone on 2018/4/19.
 */
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexTest {

    public static void testCreateIndex() {
        try {
            // 从目录中读取文件内容并创建Document文档
            List<Document> docs = IndexUtils.file2Document(IndexUtils.searchSource);
            // 创建分析器，standardAnalyzer标准分析器
            Analyzer standardAnalyzer = new StandardAnalyzer();
            // 指定索引存储目录
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.indexFolder));
            // 创建索引操作配置对象
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
            // 定义索引操作对象indexWriter
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            // 遍历目录 下的文件生成的文档，调用indexWriter方法创建索引
            for (Document document : docs) {
                indexWriter.addDocument(document);
            }
            // 索引操作流关闭
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("索引创建完成");

    }

    public static void main(String[] args) {
        testCreateIndex();
    }

}
