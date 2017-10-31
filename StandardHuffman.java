package sample;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class StandardHuffman {
    private final static int ASCII_SIZE = 256;
    static StringBuilder bits = null;
    static Node root = null;
    static Map<Character, String> lookUpTable = null;
    private static String DATA_FILE;

    public static Node compress(String filedata) {
        DATA_FILE = filedata;
        StringBuilder data = new StringBuilder();
        try {

            FileReader fileReader = new FileReader(filedata);
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
            fileReader.close(); // sa3at bydrb lma ast5dm cat lazm 22flo 34an ast5do f el mv w el cp
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int[] freq = buildFreqTable(data.toString());
        final Node root = buildHuffmanTree(freq);
        lookUpTable = buildTable(root);

        for (int i = 0; i < data.toString().length(); i++) {
            bits.append(lookUpTable.get(data.charAt(i)));

        }

        try (PrintWriter writer = new PrintWriter(filedata, "UTF-8")) {
            writer.println(bits);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static int[] buildFreqTable(String data) {
        int[] freq = new int[ASCII_SIZE];
        for (char character : data.toCharArray()) {
            freq[character]++;
        }
        return freq;
    }

    static class Node implements Comparable<Node> {
        final char character;
        final int _frequency;
        final Node leftChild;
        final Node rightChild;

        Node(char nodechar, int frequency, Node leftChild, Node rightChild) {
            character = nodechar;
            _frequency = frequency;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        boolean isLeaf() {
            return this.leftChild == null && this.rightChild == null;
        }

        @Override
        public int compareTo(Node o) {
            final int frequencyComparison =
                    Integer.compare(this._frequency, o._frequency);
            if (frequencyComparison != 0) {
                return frequencyComparison;
            }
            return Integer.compare(this.character, o.character);
        }
    }

    private static Node buildHuffmanTree(int[] freq) {
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < ASCII_SIZE; i++) {
            if (freq[i] > 0) {
                priorityQueue.add(new Node((char) i, freq[i], null, null));
            }
        }

        if (priorityQueue.size() == 1) {
            priorityQueue.add(new Node('\0', 1, null, null));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left._frequency + right._frequency, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    private static Map<Character, String> buildTable(Node root) {
        final Map<Character, String> lookUp = new HashMap<>();
        bits = new StringBuilder();
        buildLookUpTable(root, "", lookUp, bits);
        return lookUp;
    }

    private static void buildLookUpTable(Node root,
                                         String bit,
                                         Map<Character, String> lookUp, StringBuilder bits) {
        if (!root.isLeaf()) {
            buildLookUpTable(root.leftChild, bit + '0', lookUp, bits);
            buildLookUpTable(root.rightChild, bit + '1', lookUp, bits);
        } else {
            lookUp.put(root.character, bit);
        }
    }

    static String decompress(Node root) {
        StringBuilder input = new StringBuilder();
        try {

            FileReader fileReader = new FileReader(DATA_FILE);
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while ((line = in.readLine()) != null) {
                input.append(line);
            }
            fileReader.close(); // sa3at bydrb lma ast5dm cat lazm 22flo 34an ast5do f el mv w el cp
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder output = new StringBuilder();
        String inputInString = input.toString();

        Node base = root;
        while (!inputInString.isEmpty()) {
            if (inputInString.charAt(0) == '1') {
                base = base.rightChild;
                inputInString = inputInString.substring(1);
            } else {
                base = base.leftChild;
                inputInString = inputInString.substring(1);
            }
            if (base.leftChild == null && base.rightChild == null) {
                output.append(base.character);
                base = root;
            }

        }
        try (PrintWriter writer = new PrintWriter(DATA_FILE, "UTF-8")) {
            writer.println(output);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return (output.toString());
    }

}
