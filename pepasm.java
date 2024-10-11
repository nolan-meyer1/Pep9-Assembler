import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

import static java.util.Map.entry;

public class pepasm {

    //This hashmap contains the keys mapped to their values
    public static Map<String, String> assemblyCodes = assemblyCodes = Map.ofEntries(
            entry("LDWA", "C"),
            entry("LDBA", "D"),
            entry("STWA", "E"),
            entry("STBA", "F"),
            entry("STOP", "00"),
            entry("ASLA", "0A"),
            entry("ASRA", "0C"),
            entry("ADDA", "6"),
            entry("CPBA", "B"),
            entry("BRNE", "1A"),
            entry("i", "0"),
            entry("d", "1")
    );

    //This map will contain the label names as a key and their memory address will be the value
    public static HashMap<String, String> labels = new HashMap<String, String>();

    //Current memory address
    public static int memoryAddress = 0;

    /*
     * This method is used to generate the operand and
     * break it up into two parts. First it will check
     * to see if the length does not equal seven. If it
     * doesn't that means that we will need to add some
     * leading zeroes. We then look at the length and
     * subtract three which takes out the 0x and the ,
     * in the operand.Then it will insert leading zeroes
     * until count is equal to four. After it will
     * break it up into two parts and add spaces then return
     * the string. If it's the right length it will just break
     * it apart to begin with.
     *
     * */
    public static String operand(String operand) {

        StringBuilder sb = new StringBuilder(operand);

        if (operand.length() != 7) {

            int count = operand.length() - 3;

            while (count != 4) {
                sb.insert(2, "0");
                count++;
            }
        }

        String operand1 = sb.toString().substring(2, 4);
        String operand2 = sb.toString().substring(4, 6);

        memoryAddress = memoryAddress + 2;

        return operand1 + " " + operand2 + " ";

    }

    /*
     * This will take the line and split it at
     * the colon. Then it will put that label
     * in the hashmap with the current value
     * of the memory address that will be
     * computed.
     * */
    public static void createLabel(String line) {

        int i = 0;

        while (line.charAt(i) != ':') {
            i++;
        }

        line = line.substring(0, i) + ",";

        StringBuilder sb = new StringBuilder(Integer.toString(memoryAddress));

        while (sb.length() != 4) {
            sb.insert(0, "0");
        }

        sb.insert(2, " ");

        labels.put(line, sb.toString() + " ");

    }

    /*
     * This method is used to clean up the data. It will start
     * by looking for a comment and then split the array at the comment.
     * It will then get look at the first half and get rid of white space,
     * and split it at the space. Then we will loop through the array until
     * we find a mnemonic code then it will slice the array from that point
     * to the end and return the array.
     * */
    public static String[] cleanData(String line) {

        if (line.contains(":")) {
            createLabel(line);
        }

        String[] lineArray = line.split(";");
        lineArray = lineArray[0].trim().split(" ");



        return lineArray;
    }

    public static void main(String[] args) throws FileNotFoundException {

        //Opens the file
        File file = new File(args[0]);

        //Creates a String Builder object that will be used to generate the output
        StringBuilder output = new StringBuilder();

        //Creates a scanner object that will be used to parse through the file
        Scanner scnr = new Scanner(file);

        //Figure out a way for if they don't use spaces in the code

        while (scnr.hasNextLine()) {

            String[] line = cleanData(scnr.nextLine());


                for (int i = 0; i < line.length; i++) {

                    //If it equals a blank line or .END it will continue back to top and if it's stop it will append the stop code and continue
                    if (line[i].equals("") || line[i].equals(".END")) {
                        continue;
                    } else if (line[i].equals("STOP")) {
                        output.append(assemblyCodes.get(line[i]));
                        continue;
                    }

                    //Checks to see if it's a one part command like ASLA or ASRA
                    if(line.length != 1) {

                        //Checks to see if it's a label or not
                        if (!labels.containsKey(line[i])) {

                            //Checks if it is an assembly code and that we're not on the last item(memory adress)
                             if (assemblyCodes.containsKey(line[i]) && i != line.length - 1) {

                                //Checks to see if it's BRNE which needs a special configuration
                                if (!line[i].equals("BRNE")) {
                                    output.append(assemblyCodes.get(line[i]) + assemblyCodes.get(line[line.length - 1]) + " ");
                                } else {
                                    output.append(assemblyCodes.get(line[i]) + " ");
                                }
                                memoryAddress = memoryAddress + 1;

                            //By seeing if the string contains "0x" we know if we're dealing with an operand
                            } else if (line[i].contains("0x")) {
                                output.append(operand(line[i]));
                            }

                        //If it's a label instead of putting the memory address we put the label's memory address
                        } else {
                            output.append(labels.get(line[i]));
                        }

                    //If it's a one line command we can just put the machine code in directly
                    } else {
                        output.append(assemblyCodes.get(line[i]) + " ");
                    }
                }
        }
        System.out.println(output.toString());
    }
}
