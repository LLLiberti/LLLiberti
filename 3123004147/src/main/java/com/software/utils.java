package com.software;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class utils {

    //读取文件
    public static String readFile(String filePath)  {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    //jieba分词预处理文章
    public static List<String> segmentText(String text) {
        //去除所有标点符号
        text = text.replaceAll("[\\p{P}\\p{S}]", "");

        JiebaSegmenter segment = new JiebaSegmenter();
        return segment.sentenceProcess(text);
    }
}
