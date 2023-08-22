package edu.iastate.cs228.hw4;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @author William Jacob Mosier
 */
public class MsgTree {
    static final char nullChar = '^';
    final char charecter;
    MsgTree leftChild = null;
    MsgTree rightChild = null;

    public MsgTree(char c) {
        charecter = c;
    }

    public MsgTree(String s) {
        this(s.charAt(0));
        MsgTree currentNode = this;

        assert s.charAt(0) == '^';

        Stack<MsgTree> parents = new Stack<>();

        for (int i = 1; i < s.length(); i++) {
            final char nextChar = s.charAt(i);

            final MsgTree newNode = new MsgTree(nextChar);

            while (true) {

                if (currentNode.leftChild == null) {
                    currentNode.leftChild = newNode;
                    parents.push(currentNode);
                    currentNode = newNode;
                    break;
                } else if (currentNode.rightChild == null) {
                    currentNode.rightChild = newNode;
                    parents.push(currentNode);
                    currentNode = newNode;
                    break;
                } else {
                    currentNode = parents.pop();
                }
            }

            if (newNode.charecter != MsgTree.nullChar) {
                currentNode = parents.pop();
            }
        }
    }


    public String preOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        preOrderTraversalHelper(sb, this);
        return sb.toString();
    }


    private void preOrderTraversalHelper(StringBuilder sb, MsgTree n) {
        if (n == null) {
            return;
        }
        sb.append(n.charecter);
        preOrderTraversalHelper(sb, n.leftChild);
        preOrderTraversalHelper(sb, n.rightChild);

    }

    public static String class_decode(MsgTree tree, String encoded) {
        StringBuilder sb = new StringBuilder();
        MsgTree node = tree;
        for (int i = 0; i < encoded.length();i++) {
            char c = encoded.charAt(i);
            if (c == '1') {
                node = node.rightChild;
            } else if (c == '0'){
                node = node.leftChild;
            }else {
                throw new AssertionError("The .arch file may be invalid");
            }

            if (node != null && node.charecter != nullChar) {
                sb.append(node.charecter);
                node = tree;
            }

            if (node == null) {
                node = tree;
            }

        }
        return sb.toString();
    }


    public void decode(MsgTree codes, String msg){
        String decoded = MsgTree.class_decode(codes,msg);

        System.out.print(decoded);

    }


    //method to print characters and their binary codes
    public static void printCodes(MsgTree root, String code) {
        Map<Character, boolean[]> characterCodeMap = new TreeMap<>();
        printCodesHelper(root, characterCodeMap, null);

        System.out.println("character\tcode\n" +
                "-------------------------");

        for (Character key : characterCodeMap.keySet()) {
            System.out.print('\t');

            if (key != '\n') {
                System.out.print(key);
            }else {
                System.out.print("\\n");
            }
            System.out.print('\t');
            System.out.print('\t');
            for (boolean bool : characterCodeMap.get(key)) {
                if (bool){
                    System.out.print('1');
                }else {
                    System.out.print('0');
                }
            }
            System.out.print('\n');
        }

    }

    private static void printCodesHelper(MsgTree tree, Map<Character, boolean[]> characterCodeMap, boolean[] path) {
        if (tree == null) {
            return;
        }

        //Using pre-order traversal :)
        if (tree.charecter != nullChar) {
            characterCodeMap.put(tree.charecter, path);
            return;
        }

        boolean[] lPath;
        boolean[] rPath;
        if (path != null) {
            lPath = new boolean[path.length + 1];
            lPath[path.length] = false;
            rPath = new boolean[path.length + 1];
            rPath[path.length] = true;

            System.arraycopy(path,0,lPath,0,path.length);
            System.arraycopy(path,0,rPath,0,path.length);

        } else {
            lPath = new boolean[1];
            lPath[0] = false;
            rPath = new boolean[1];
            rPath[0] = true;
        }

        printCodesHelper(tree.leftChild, characterCodeMap, lPath);
        printCodesHelper(tree.rightChild, characterCodeMap, rPath);

    }

}
