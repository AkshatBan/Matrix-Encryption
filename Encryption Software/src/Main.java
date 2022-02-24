import java.util.Arrays;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    String userInput = "";
    Scanner scan = new Scanner(System.in);
    
    // Display welcome msg and ask user for input
    System.out.println("===============Welcome to the Crypt===============");
    
    do {
      System.out.println("Enter the key corresponding to the action you want to take" 
                          + "\n1 - Encrypt"
                          + "\n2 - Decrypt"
                          + "\nx - Exit");
      
      do {
        userInput = scan.nextLine().toLowerCase().trim();
        
        if (checkIncorrectInputBase(userInput)) {
          System.out.println("Invalid input\nPress any key and try again");
          scan.nextLine();
        }
      } while (checkIncorrectInputBase(userInput));
      
      switch(userInput) {
        case "x":
          // Exit application
          return;
        case "1":
          // Run encryption
          encryption();
          return;
        case "2":
          // Run decryption
          decryption();
          return;
      }
    } while (!userInput.equalsIgnoreCase("x"));
    
    scan.close();
  }
  
  private static void encryption() {
    Scanner scan = new Scanner(System.in);
    String msgToEnc = "";
    System.out.println("Type the message you want to encrypt: (NOTE: just letters, no numbers)");
    msgToEnc = scan.nextLine();
    TextMessage message = new TextMessage(msgToEnc);
    Matrix encMatrix = new Matrix();
    message.encrypt(encMatrix.getMatrix());
    System.out.println("\n" + message.getEncryptedMsg());
    System.out.println("\nThe encryption matrix is:");
    int[][] encMat = encMatrix.getMatrix();
    for (int[] row : encMat) {
      System.out.println("\n" + Arrays.toString(row));
    }
    scan.close();
  }
  
  private static void decryption() {
    Scanner scan = new Scanner(System.in);
    String msgToDec = "";
    System.out.println("Type the message you wish to decrypt:");
    msgToDec = scan.nextLine();
    TextMessage message = new TextMessage(msgToDec);
    Matrix decMatrix = new Matrix();
    System.out.println("Enter the encryption matrix you were given:");
    decMatrix.setMatrix();
    
    decMatrix.invert(decMatrix.getMatrix());
    
    message.decrypt(decMatrix.getInvertedMatrix());
    System.out.println("\n" + message.getDecryptedMsg());
    scan.close();
  }
  
  private static boolean checkIncorrectInputBase(String str) {
    return !str.equalsIgnoreCase("x") && !str.equals("1") && !str.equals("2");
  }

}
