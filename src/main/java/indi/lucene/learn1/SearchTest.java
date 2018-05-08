package indi.lucene.learn1;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Created by Credittone on 2018/4/19.
 */

public class SearchTest {
    // 索引目录地址
    // 查询方法
    public static void testTermQuery() throws IOException {
        // 创建查询对象，根据文件名称域搜索匹配文件名称的文档
        //Query query = new TermQuery(new Term("fileName", "aa.txt"));
        Query query = new TermQuery(new Term("auto", "auto"));
        // 指定索引目录
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.indexFolder));
        // 定义IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 创建indexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 执行搜索
        TopDocs topDocs = indexSearcher.search(query, 100);
        // 提取搜索结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("共搜索到总记录数：" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 文档id
            int docID = scoreDoc.doc;
            // 得到文档
            Document doc = indexSearcher.doc(docID);
            // 输出 文件内容
            IndexUtils.printDocumentOfFile(doc);
        }

    }

    public static void main(String[] args) throws IOException {
        testTermQuery();
    }
}