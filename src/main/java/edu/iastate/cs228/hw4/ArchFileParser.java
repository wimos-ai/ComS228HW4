package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author William Jacob Mosier
 */
public class ArchFileParser {

    static class ArchFileData {
        public String tree;
        public String encodedBytes;
    }

    public static ArchFileData parse(Path pth) {
        String data = null;

        try {
            data = Files.readString(pth);
        } catch (IOException e) {
            System.out.println("File could not be read and raised an IOException.\n" + e + "\nStack Trace: " + Arrays.toString(e.getStackTrace()));
            System.exit(-1);
        }

        return ArchFileParser.parse(data);
    }

    public static ArchFileData parse(String fileContents) {
        Scanner sc = new Scanner(fileContents);

        String[] lines = null;
        while (sc.hasNextLine()) {
            if (lines == null) {
                lines = new String[1];
            } else {
                String[] tmp = new String[lines.length + 1];
                System.arraycopy(lines, 0, tmp, 0, lines.length);
                lines = tmp;
            }
            lines[lines.length - 1] = sc.nextLine();
        }

        assert (lines != null);

        final ArchFileData fd = new ArchFileData();

        fd.encodedBytes = lines[lines.length - 1];

        {
            String[] arr = new String[lines.length-1];
            System.arraycopy(lines,0,arr,0,arr.length);
            lines = arr;
        }

        if (lines.length == 1){
            fd.tree = lines[0];
        }else if(lines.length == 2){
            fd.tree = lines[0] + '\n' + lines[1];
        }else{
            throw new AssertionError("Possibly invalid .arch format");
        }

        return fd;
    }

}
