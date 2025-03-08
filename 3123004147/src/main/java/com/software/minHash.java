package com.software;

import java.util.Arrays;
import java.util.Set;

public class minHash {

    // 假设有n个哈希函数
    private final int numHashFunctions;

    public minHash(int numHashFunctions) {
        this.numHashFunctions = numHashFunctions;
    }

    // 生成一个哈希值
    private int generateHash(String str, int seed) {
        int hashValue = str.hashCode();
        int prime = 101;
        return Math.abs((seed * hashValue) % prime);
    }

    // 计算集合的MinHash签名
    public int[] getMinHashSignature(Set<String> set) {
        int[] signature = new int[numHashFunctions];
        Arrays.fill(signature, Integer.MAX_VALUE);
        for (int i = 0; i < numHashFunctions; i++) {
            for (String element : set) {
                int hashValue = generateHash(element, i + 1);
                signature[i] = Math.min(signature[i], hashValue);
            }
        }
        return signature;
    }

    // 计算两个集合的Jaccard相似度估算
    public double jaccardSimilarity(Set<String> set1, Set<String> set2) {
        int[] signature1 = getMinHashSignature(set1);
        int[] signature2 = getMinHashSignature(set2);

        // 比较MinHash签名
        int identicalCount = 0;
        for (int i = 0; i < numHashFunctions; i++) {
            if (signature1[i] == signature2[i]) {
                identicalCount++;
            }
        }

        return (double) identicalCount / numHashFunctions;
    }
}
