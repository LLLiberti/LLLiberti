package com.software;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class checkPlagiarism {
    public static void checkPlagiarism(String origPath, String plagiarizedPath, String outputPath) throws IOException {
        // 读取文件内容
        String origText = utils.readFile(origPath);
        String plagText = utils.readFile(plagiarizedPath);

        // 使用jieba分词
        List<String> origToken = utils.segmentText(origText);
        List<String> plagiarizedToken = utils.segmentText(plagText);

        List<List<String>> allTokens = new ArrayList<>();
        allTokens.add(origToken);
        allTokens.add(plagiarizedToken);

        // 计算TF-IDF值
        Map<String, Double> origTFIDF = tfIdf.calculateTFIDF(origToken, allTokens);
        Map<String, Double> plagTFIDF = tfIdf.calculateTFIDF(plagiarizedToken, allTokens);

        //筛选前100个具有代表性的词汇，用于进一步计算重复率
        Set<String> origSet = origTFIDF.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Set<String> plagSet = plagTFIDF.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        // 计算筛选过后的MinHash相似度
        minHash minHash = new minHash(10000);
        double similarity = minHash.jaccardSimilarity(origSet, plagSet);

        // 输出相似度结果到文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(String.format("查重完成，相似度：%.2f", similarity));
        writer.close();

        System.out.println("查重完成，相似度：" + similarity * 100 + "%");
    }
}
