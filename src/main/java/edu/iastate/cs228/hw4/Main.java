package edu.iastate.cs228.hw4;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author William Jacob Mosier
 */
public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) {

        final String fileName;

        System.out.print("Please enter filename to decode: ");
        Scanner sc = new Scanner(System.in);
        fileName = sc.nextLine();


        var data = ArchFileParser.parse(Path.of(fileName));

        MsgTree tree = new MsgTree(data.tree);



        MsgTree.printCodes(tree, null);

        System.out.println("\nMESSAGE:");
        tree.decode(tree, data.encodedBytes);
    }

    /*public static void main(String[] args) {

        final String fileName;

        if (DEBUG){
            String[] possibleFiles = {"src/main/resources/constitution.arch", "src/main/resources/example.arch","src/main/resources/monalisa.arch","src/main/resources/twocities.arch"};
            fileName = possibleFiles[new Random().nextInt(possibleFiles.length)];
        }else {
            System.out.print("Please enter filename to decode: ");
            Scanner sc = new Scanner(System.in);
            fileName = sc.nextLine();
        }

        var data = ArchFileParser.parse(Path.of(fileName));

        MsgTree tree = new MsgTree(data.tree);

        if (DEBUG) {

            System.out.println(tree.preOrderTraversal());

            System.out.println(data.encodedBytes);
        }

        String result = MsgTree.decode(tree, data.encodedBytes);

        MsgTree.printCodes(tree, null);

        System.out.println("MESSAGE:");
        System.out.println(result);
    }*/
}
