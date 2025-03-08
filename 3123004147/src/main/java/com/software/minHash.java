package com.software;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Set;

public class minHash {

    // 假设有n个哈希函数
    private final int numHashFunctions;

    public minHash(int numHashFunctions) {
        this.numHashFunctions = numHashFunctions;
    }

    private static final MessageDigest digestInstance = createMessageDigest();

    private static MessageDigest createMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 生成一个哈希值
    private int generateHash(String str, int seed) {

        byte[] hashBytes = digestInstance.digest(str.getBytes());

        // 将 SHA-256 的哈希值的前4字节转换为整数值, 只取 SHA-256 的前4字节
        int hashValue = ((hashBytes[0] & 0xFF) << 24) |
                ((hashBytes[1] & 0xFF) << 16) |
                ((hashBytes[2] & 0xFF) << 8) |
                (hashBytes[3] & 0xFF);


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
