# Pep9-Assembler

This is a very simple assembler for Pep9 written in Java. This assembler supports the simple assembly commands that include LDWA, LDBA, STWA, STBA, ASLA, ASRA, ADDA, CPBA, BRNE, and STOP. It also supports two types of adressing modes immediate and direct. This tool is meant to be used with the command line interface as shown: 

![Screenshot 2024-10-11 at 12 18 54 PM](https://github.com/user-attachments/assets/fe0f67ab-58b1-4934-a053-4d9b357dc435)

To run the file you're going to want to make sure you're in the right directory then use the following format java pepasm (file name). Once you do this you as long as the operations are supported you will be able to trasnalte the assembly code into the correct machine code. You also want to make sure that you have spaces in between your different parts for example you would want to do LDBA 0x0014, i instead of LDBA 0x0014,i because we use split with spaces so it will cause an error. 

NOTE: In the code folder there are example test files that can be used to test the assembler! 

## Instance Variables
assemblyCodes: HashMap that contains the mnemonic code as a key and the machine code equivalnt as the value. <br><br>
labels: HashMap that is used to store labels. The key is the label name and the value is the memory adress associated with that label. <br><br>
memoryAddress: This is used to keep track of the current memory adress of the program. Each time something is added to the output we increment the memory adress so we know what adress we're at. 

## operand() method
This method is one of the most important methods of the entire program.  This method is used to generate the operand and break it up into two parts. First it will check to see if the length does not equal seven. If it doesn't that means that we will need to add some leading zeroes. We then look at the length an subtract three which takes out the 0x and the ,in the operand.Then it will insert leading zeroes until count is equal to four. After it willbreak it up into two parts and add spaces then return the string. If it's the right length it will just break
it apart to begin with. Lastly, we want to add two to the static variable memory adress since we're always going to add two parts to the output. 

## createLabel() method
This will take the line and split it at the colon. Then it will put that label in the HashMap with the current value of the memory address that will be computed. If the length of the memory adress isn't the proper length we will add leading zeroes to make it correct.

## cleanData() method
This method is used to clean up the data. It will startby looking for a comment and then split the array at the comment. It will then get look at the first half and get rid of white space   and split it at the space. Then we will loop through the array until we find a mnemonic code then it will slice the array from that point to the end and return the array.

## main() method
This method will start by opening the file and use scanner to parse it. We will first grab a line and then call the clean data function to get the array of the values. Then we will loop through that array and check different conditions. These conditions will decide which functions we need to call and what we need to do in order to form the correct result. (More detailed explanation in the comments of the code)

