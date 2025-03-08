package com.software;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class utils {
    public static String readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static List<String> segmentText(String text) {
        text = text.replaceAll("[\\p{P}\\p{S}]", "");
        JiebaSegmenter segmenter = new JiebaSegmenter();
        return segmenter.sentenceProcess(text);
    }
}
