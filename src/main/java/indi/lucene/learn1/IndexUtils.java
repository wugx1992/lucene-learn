package indi.lucene.learn1;

/**
 * Created by Credittone on 2018/4/19.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

public class IndexUtils {
    /**
     *  索引源，即源数据目录
     */
    public static String searchSource = "H:\\lucene\\searchsource";

    /**
     * 索引目标地址
     */
    public static String indexFolder = "H:\\lucene\\indexdata";

    /**
     * 从文件创建Document
     */
    public static List<Document> file2Document(String folderPath) throws IOException {
        List<Document> list = new ArrayList<Document>();
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return null;
        }
        // 获取目录 中的所有文件
        File[] files = folder.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                // 文件内容
                String fileContent = FileUtils.readFileToString(file);
                // 文件路径
                String filePath = file.getAbsolutePath();
                // 文件大小
                long fileSize = FileUtils.sizeOf(file);
                // 创建文档
                Document doc = new Document();
                // 创建各各Field域
                // 文件名
                Field field_fileName = new StringField("fileName", fileName, Store.YES);
                // 文件内容
                Field field_fileContent = new TextField("fileContent", fileContent, Store.YES);
                // 文件大小
                Field field_fileSize = new TextField("fileSize", String.valueOf(fileSize), Store.YES);
                // 文件路径
                Field field_filePath = new StoredField("filePath", filePath);

                // 自定义检索条件
                Field auto_field = new TextField("auto", "auto", Store.YES);
                // 将各各Field添加到文档中
                doc.add(field_fileName);
                doc.add(field_fileContent);
                doc.add(field_fileSize);
                doc.add(field_filePath);
                doc.add(auto_field);
                list.add(doc);
            }
        }
        return list;

    }


    /**
     * 更新索引： Lucene其实并未提供更新索引的方法,这里的更新操作内部是先删除再添加的方式
     */
    public static void updateIndex(IndexWriter indexWriter, Document doc, Term term) {
        try {
            indexWriter.updateDocument(term, doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引: 在执行完该方法后,再执行本类的TermQuery()方法,得到数据
     * 这说明此时删除的文档并没有被完全删除,而是存储在一个回收站中,它是可以恢复的 ，将回车站数据清空即可
     */
    public void deleteIndex(IndexWriter indexWriter,Term term) {
        try {
            indexWriter.deleteDocuments(term);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交索引内容的变更情况
     */
    public void commitIndex(IndexWriter indexWriter) {
        try {
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printDocumentOfFile(Document doc) {
        System.out.println("-----------");
        System.out.println("文件名称 => " + doc.get("fileName"));
        System.out.println("文件大小 => " + doc.get("fileSize"));
        System.out.println("文件内容 => " + doc.get("fileContent"));
    }

}
