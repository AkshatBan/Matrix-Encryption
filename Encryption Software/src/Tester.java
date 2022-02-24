import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Tester {
  /**
   * Checks correctness of Matrix class
   * 
   * @return true if all methods work as expected, false otherwise
   */
  public static boolean matrixTester() {
    // Test 1: checks if setMatrix() works as intended
    Matrix mat = new Matrix();
    System.out.println("Set the matrix as such: [1, 2, 3], [1, 1, 2], [0, 1, 2]");
    mat.setMatrix();
    if (!Arrays.deepToString(mat.getMatrix()).equals("[[1, 2, 3], [1, 1, 2], [0, 1, 2]]")) {
      System.out.println("The matrix was not set properly");
      System.out.println(Arrays.deepToString(mat.getMatrix()));
      return false;
    }
    
    // Test 2: checks if matrix is inverted properly
    mat.invert(mat.getMatrix());
    if (!Arrays.deepToString(mat.getInvertedMatrix()).equals("[[0.0, 1.0, -1.0], [2.0, -2.0, -1.0], [-1.0, 1.0, 1.0]]")) {
      System.out.println("The matrix was not inverted properly");
      System.out.println(Arrays.deepToString(mat.getInvertedMatrix()));
      return false;
    }

    return true;
  }

  /**
   * Checks correctness of TextMessage class
   * 
   * @return true if all methods work as expected, false otherwise
   */
  public static boolean textMessageTester() {
    // Test 1: checks if constructor throws exception
    try {
      TextMessage error = new TextMessage("");
      System.out.println("Conctructor should've thrown exception");
      return false;
    } catch (IllegalArgumentException e) {
      // Expected behavior
    }

    // Test 2: checks if getMessage() works as intended
    TextMessage msg = new TextMessage("Do or do not, there is no try");
    if (!msg.getMessage().equals("Original Message: Do or do not, there is no try")) {
      System.out.println("The message that was returned was not what was expected");
      return false;
    }

    /*
     * Test 3: checks if getEncryptedMsg(), getDecryptedMsg(), toString1(), and toString2() throw
     * exceptions when encryptedMsg and decryptedMsg are empty
     */
    try {
      msg.getEncryptedMsg();
      System.out.println("getEncryptedMsg() should've thrown exception");
      return false;
    } catch (NoSuchElementException e) {
      // Expected behavior
    }

    try {
      msg.getDecryptedMsg();
      System.out.println("getDecryptionMsg() should've thrown exception");
      return false;
    } catch (NoSuchElementException e) {
      // Expected behavior
    }

    if (msg.toString1() != null) {
      System.out.println("toString1() should've returned null");
      return false;
    }

    if (msg.toString2() != null) {
      System.out.println("toString2() should've returned null");
      return false;
    }
    
    // Test 4: checks if encryption works as intended
    msg.setMessage("MEET TOMORROW");
    Matrix key = new Matrix();
    System.out.println("Set the matrix as such: [1, 2, 3], [1, 1, 2], [0, 1, 2]");
    key.setMatrix();
    msg.encrypt(key.getMatrix());
    if (!msg.getEncryptedMsg().equals("Encrypted Message: l1b1oa4r2x1s3l2y1m4a3i2")) {
      System.out.println("Message was not encrypted properly");
      return false;
    }

    // Test 5: checks if desryption works as intended
    msg.setMessage("l1b1oa4r2x1s3l2y1m4a3i2");
    key.invert(key.getMatrix());
    msg.decrypt(key.getInvertedMatrix());
    if (!msg.getDecryptedMsg().equals("Decrypted Message: meettomorrow"));
    
    return true;
  }

  public static void main(String[] args) {
    //System.out.println(matrixTester());
    System.out.println(textMessageTester());
    
//    TextMessage msg = new TextMessage("l1b1oa4r2x1s3l2y1m4a3i2");
//    Matrix matrix = new Matrix();
//    matrix.setMatrix();
//    matrix.invert(matrix.getMatrix());
//    msg.decrypt(matrix.getInvertedMatrix());
//    System.out.println(msg.getDecryptedMsg());
  }
}
