package com.software;

import java.util.*;

public class tfIdf {

    public static Map<String, Double> calculateTFIDF(List<String> words, List<List<String>> allWords) {

        Map<String, Double> tfidf = new HashMap<>();

        // 计算TF
        Map<String, Integer> tfMap = new HashMap<>();
        for (String word : words) {
            tfMap.put(word, tfMap.getOrDefault(word, 0) + 1);
        }

        // 计算IDF
        int totalDocs = allWords.size();
        Map<String, Double> idfMap = new HashMap<>();
        Map<String, Integer> docFrequency = new HashMap<>();
        for (List<String> wordList : allWords) {
            Set<String> uniqueWords = new HashSet<>(wordList);
            for (String word : uniqueWords) {
                docFrequency.put(word, docFrequency.getOrDefault(word, 0) + 1);
            }
        }
        for (String word : docFrequency.keySet()) {
            int df = docFrequency.get(word);
            double idf = Math.log((double) totalDocs / (1 + df));
            idfMap.put(word, idf);
        }

        // 计算TF-IDF
        for (String word : tfMap.keySet()) {
            double tfValue = tfMap.get(word) / (double) words.size();
            double idfValue = Math.log((double) totalDocs / (1 + idfMap.getOrDefault(word, 0.0)));
            tfidf.put(word, tfValue * idfValue);
        }

        return tfidf;
    }
}
