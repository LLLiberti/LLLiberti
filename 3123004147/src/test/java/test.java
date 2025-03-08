import com.software.tfIdf;
import com.software.utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        System.out.println(doc1Tokens);
        System.out.println(doc2Tokens);
        System.out.println(doc1Map);
        System.out.println(doc2Map);
        System.out.println(doc1Sset);
        System.out.println(doc2Sset);
    }
}
