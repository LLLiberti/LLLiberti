import com.software.checkPlagiarism;
import com.software.minHash;
import com.software.tfIdf;
import com.software.utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class test {

    @Test
    public void tdIdfTest() {

        String doc1 = "今天是星期天，天气晴，今天晚上我要去看电影。";
        String doc2 = "今天是周天，天气晴朗，我晚上要去看电影。";

        List<String> doc1Tokens = utils.segmentText(doc1);
        List<String> doc2Tokens = utils.segmentText(doc2);

        List<List<String>> documents = new ArrayList<>();
        documents.add(doc1Tokens);
        documents.add(doc2Tokens);

        Map<String, Double> doc1Map = tfIdf.calculateTFIDF(doc1Tokens, documents);
        Map<String, Double> doc2Map = tfIdf.calculateTFIDF(doc2Tokens, documents);

        Set<String> doc1Sset = doc1Map.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Set<String> doc2Sset = doc2Map.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        System.out.println(doc1Map);
        System.out.println(doc2Map);
        System.out.println(doc1Sset);
        System.out.println(doc2Sset);
    }

    @Test
    public void testMinHash() {
        Set<String> set1 = new HashSet<>();
        set1.add("番茄");
        set1.add("banana");
        set1.add("orange");

        Set<String> set2 = new HashSet<>();
        set2.add("grape");
        set2.add("西红柿");

        // 设定MinHash哈希函数的数量
        minHash minHash = new minHash(100);

        // 估算Jaccard相似度
        double jaccardSimilarity = minHash.jaccardSimilarity(set1, set2);
        System.out.println(String.format("%.2f", jaccardSimilarity));
    }

    @Test
    public void testcheckPlag() throws IOException {
        String origPath = "C:\\Users\\lbq\\Desktop\\orig.txt";
        String plagiarizedPath = "C:\\Users\\lbq\\Desktop\\orig_0.8_add.txt";
        String outputPath = "C:\\Users\\lbq\\Desktop\\ans.txt";

        checkPlagiarism.checkPlagiarism(origPath,plagiarizedPath,outputPath);
    }

    @Test
    public void testcheckPlagEx() throws IOException {

        //路径错误
        String origPath1 = "C:\\Users\\lbq\\Desktop\\orig1.txt";
        String plagiarizedPath1 = "C:\\Users\\lbq\\Desktop\\orig_0.8_add.txt";
        String outputPath1 = "C:\\Users\\lbq\\Desktop\\ans.txt";

        //空路径
        String origPath2 = "";
        String plagiarizedPath2 = "C:\\Users\\lbq\\Desktop\\orig_0.8_add.txt";
        String outputPath2 = "C:\\Users\\lbq\\Desktop\\ans.txt";

        String origPath3 = "C:\\Users\\lbq\\Desktop\\orig.txt";
        //空文章
        String plagiarizedPath3 = "C:\\Users\\lbq\\Desktop\\1.txt";
        String outputPath3 = "C:\\Users\\lbq\\Desktop\\ans.txt";

        checkPlagiarism.checkPlagiarism(origPath1,plagiarizedPath1,outputPath1);

        checkPlagiarism.checkPlagiarism(origPath2,plagiarizedPath2,outputPath2);

        checkPlagiarism.checkPlagiarism(origPath3,plagiarizedPath3,outputPath3);
    }
}
