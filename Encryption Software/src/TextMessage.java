import java.util.Arrays;
import java.util.NoSuchElementException;

public class TextMessage {
  /*
   * Data Fields: message - the message to be either encrypted or decrypted. Input by user
   * encryptedMsg - the encrypted form of the message decryptedMsg - the decrypted form of the
   * message
   */
  private String message;
  private String encryptedMsg;
  private String decryptedMsg;

  /**
   * Constructor for TextMessage. Initializes this message to input message, this encryptedMsg and
   * this decryptedMsg to empty strings
   * 
   * @param message
   * @throws IllegalArgumentException if input message is null or empty
   */
  public TextMessage(String message) {
    if (message == null || message == "") {
      throw new IllegalArgumentException();
    }

    this.message = message;
    this.encryptedMsg = "";
    this.decryptedMsg = "";
  }

  /**
   * Encrypts message by using an encoding matrix
   * 
   * @param matrix
   */
  public void encrypt(int[][] matrix) {
    String toEncrypt = this.message.replaceAll("\\s", "").toLowerCase();
    int[][] vectors;
    char[] ch = toEncrypt.toCharArray();
    int rows = toEncrypt.length() / 3;
    int iterator = 0;

    // Building the vectors for the message
    if (toEncrypt.length() % 3 == 0) {
      vectors = new int[rows][3];
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < 3; c++) {
          vectors[r][c] = getNumericPos(ch[iterator]);
          iterator++;
        }
      }
    } else {
      vectors = new int[rows + 1][3];
      for (int r = 0; r < rows + 1; r++) {
        for (int c = 0; c < 3; c++) {
          if (iterator < ch.length) {
            vectors[r][c] = getNumericPos(ch[iterator]);
            iterator++;
          } else {
            vectors[r][c] = iterator;
            iterator++;
          }
        }
      }
    }

    // Matrix multiplication
    int[][] encryptedVectors = new int[vectors.length][vectors[0].length];
    for (int evr = 0; evr < encryptedVectors.length; evr++) {
      for (int er = 0; er < matrix.length; er++) {
        encryptedVectors[evr][er] = (vectors[evr][0] * matrix[er][0])
            + (vectors[evr][1] * matrix[er][1]) + (vectors[evr][2] * matrix[er][2]);
      }
    }

    // Getting the encrypted message
    int multiple;
    for (int r = 0; r < encryptedVectors.length; r++) {
      for (int c = 0; c < encryptedVectors[0].length; c++) {
        multiple = encryptedVectors[r][c] / 26;
        this.encryptedMsg += getLetter(encryptedVectors[r][c] % 26);
        if (multiple != 0) {
          this.encryptedMsg += multiple;
        }
      }
    }
  }

  /**
   * Helper method that gets the numeric position of a letter
   * 
   * @param c letter
   * @return int numeric position of the letter
   */
  private static int getNumericPos(char c) {
    return (int) (c - 96);
  }

  /**
   * Helper method that gets the letter represented by a certain numeric position
   * 
   * @param i numeric position of the desired letter
   * @return char letter
   */
  private static char getLetter(int i) {
    return (char) (i + 96);
  }

  /**
   * Decrypts message by using an encoding matrix
   * 
   * @param matrix
   */
  public void decrypt(double[][] matrix) {
    String toDecrypt = this.message.replaceAll("\\s", "").toLowerCase();
    char[] ch = toDecrypt.toCharArray();
    int msgLength = 0;
    double[][] vectors;
    int rows;
    int iterator = 0;
    String mult;
    
    // iterate through str and fill vectors
    for (int i = 0; i < toDecrypt.length(); i++) {
      if (!Character.isDigit(toDecrypt.charAt(i))) {
        msgLength++;
      }
    }
    
    rows = msgLength / 3;
    
    if (msgLength % 3 == 0) {
      vectors = new double[rows][3];
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < 3; c++) {
          vectors[r][c] = getNumericPos(ch[iterator]);
          if (Character.isDigit(ch[iterator + 1])) {
            if ((iterator + 2) < ch.length && Character.isDigit(ch[iterator + 2])) {
              mult = String.valueOf(ch[iterator + 1]) + String.valueOf(ch[iterator + 2]);
              vectors[r][c] += 26 * Integer.parseInt(mult);
              iterator++;
            } else {
              mult = String.valueOf(ch[iterator + 1]);
              vectors[r][c] += 26 * Integer.parseInt(mult);
            }
            iterator++;
          }
          iterator++;
        }
      }
    } else {
      vectors = new double[rows + 1][3];
      for (int r = 0; r < rows + 1; r++) {
        for (int c = 0; c < 3; c++) {
          if (iterator < ch.length) {
            vectors[r][c] = getNumericPos(ch[iterator]);
            if (Character.isDigit(ch[iterator + 1])) {
              if ((iterator + 2) < ch.length && Character.isDigit(ch[iterator + 2])) {
                mult = String.valueOf(ch[iterator + 1]) + String.valueOf(ch[iterator + 2]);
                vectors[r][c] += 26 * Integer.parseInt(mult);
                iterator++;
              } else {
                mult = String.valueOf(ch[iterator + 1]);
                vectors[r][c] += 26 * Integer.parseInt(mult);
              }
              iterator++;
            }
            iterator++;
          } else {
            vectors[r][c] = iterator;
            iterator++;
          }
        }
      }
    }
    
    // do matrix multiplication with inverted matrix
    
    double[][] decryptedVectors = new double[vectors.length][vectors[0].length];
    for (int dvr = 0; dvr < decryptedVectors.length; dvr++) {
      for (int er = 0; er < matrix.length; er++) {
        decryptedVectors[dvr][er] = (vectors[dvr][0] * matrix[er][0])
            + (vectors[dvr][1] * matrix[er][1]) + (vectors[dvr][2] * matrix[er][2]);
      }
    }
    
    // Getting Decrypted Msg
    for (int r = 0; r < decryptedVectors.length; r++) {
      for (int c = 0; c < decryptedVectors[0].length; c++) {
        double toAdd = Math.round(decryptedVectors[r][c]);
        this.decryptedMsg += getLetter((int) toAdd);
      }
    }
  }

  /**
   * Mutator method that sets this TextMessage's message to the input message
   * 
   * @param message
   * @throws IllegalArgumentException if message is null or empty
   */
  public void setMessage(String message) {
    if (message == null || message == "") {
      throw new IllegalArgumentException();
    }

    this.message = message;
  }

  /**
   * Accessor method for this TextMessage's message
   * 
   * @return String in the form "Original Message: + this.message"
   */
  public String getMessage() {
    return "Original Message: " + this.message;
  }

  /**
   * Accessor method for this TextMessage's encryptedMsg
   * 
   * @return String in the form "Encrypted Message: + this.encryptedMsg"
   * @throws NoSuchElementException if this TextMessage's encryptedMsg is empty
   */
  public String getEncryptedMsg() {
    if (this.encryptedMsg == "") {
      throw new NoSuchElementException();
    }

    return "Encrypted Message: " + this.encryptedMsg;
  }

  /**
   * Accessor method for this TextMessage's decryptedMsg
   * 
   * @return String in the form "Decrypted Message: + this.decryptedMsg"
   * @throws NoSuchElementException if this TextMessage's decryptedMsg is empty
   */
  public String getDecryptedMsg() {
    if (this.decryptedMsg == "") {
      throw new NoSuchElementException();
    }

    return "Decrypted Message: " + this.decryptedMsg;
  }

  /**
   * Returns a formatted string containing this TextMessage's message and encryptedMsg
   * 
   * @return String in the form "this.getMessage() + \n + this.getEncryptedMsg()". Returns null with
   *         an error message if NoSuchElementException is caught
   */
  public String toString1() {
    try {
      return this.getMessage() + "\n" + this.getEncryptedMsg();
    } catch (NoSuchElementException e) {
      System.out.println("A NoSuchElementException was caught");
      return null;
    }
  }

  /**
   * Returns a formatted string containing this TextMessage's message and decryptedMsg
   * 
   * @return String in the form "this.getMessage() + \n + this.getDecryptedMsg()". Returns null with
   *         an error message if NoSuchElementException is caught
   */
  public String toString2() {
    try {
      return this.getMessage() + "\n" + this.getDecryptedMsg();
    } catch (NoSuchElementException e) {
      System.out.println("A NoSuchElementException was caught");
      return null;
    }
  }
}
