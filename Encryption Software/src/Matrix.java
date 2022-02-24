import java.util.Random;
import java.util.Scanner;

public class Matrix {
  /*
   * Data Field: encryptionMatrix - the encryption matrix used to encrypt or decrypt messages
   */
  private int[][] encryptionMatrix;
  private double[][] invertedEncMatrix;

  /**
   * Constructor for Matrix. Initializes this encryptionMatrix with random numbers
   */
  public Matrix() {
    Random rand = new Random();
    this.encryptionMatrix = new int[3][3];
    this.invertedEncMatrix = new double[3][3];
    while (true) {
      for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
          this.encryptionMatrix[r][c] = rand.nextInt(10);
        }
      }
      if (getDet(this.encryptionMatrix) != 0) {
        break;
      }
    }
  }

  /**
   * Mutator method that sets this encryption matrix with the numbers that user inputs
   */
  public void setMatrix() {
    Scanner scan = new Scanner(System.in);
    System.out.println("Type each number of the encryption matrix in row major order:");
    while (true) {
      for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
          this.encryptionMatrix[r][c] = scan.nextInt();
        }
      }
      if (getDet(this.encryptionMatrix) != 0) {
        break;
      }
      System.out.println("The matrix must be invertible. Try again:");
    }
    
    scan.close();
  }

  /**
   * Accessor method that returns this encryptionMatrix
   * 
   * @return encryptionMatrix
   */
  public int[][] getMatrix() {
    return this.encryptionMatrix;
  }
  
  /**
   * Accessor method that returns this invertedEncMatrix
   * 
   * @return invertedEncMatrix
   */
  public double[][] getInvertedMatrix() {
    return this.invertedEncMatrix;
  }

  /**
   * Inverts the encryption matrix
   * 
   * @param
   * @return
   */
  public double[][] invert(int[][] matrix) {
    if (getDet(matrix) == 0) {
      System.out.println("The matrix cannot be inverted");
      return null;
    }
    
    int detMat = getDet(matrix);
    
    // get cofactor matrix
    double[][] cofMatrix = new double[matrix.length][matrix[0].length];
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        cofMatrix[row][col] = getCofactor(matrix, row, col);
      }
    }
    
    // get adjoint matrix
    double[][] adjMat = new double[matrix.length][matrix[0].length];
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        adjMat[row][col] = cofMatrix[col][row];
      }
    }
    
    double[][] inverse = new double[matrix.length][matrix[0].length];
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        inverse[row][col] = adjMat[row][col] / detMat;
      }
    }
    
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        this.invertedEncMatrix[row][col] = inverse[row][col];
      }
    }
    
    return null;
  }
  
  /**
   * Calculates cofactor values of the input matrix
   * 
   * @param matrix
   * @param r
   * @param c
   * @return
   */
  private int getCofactor(int[][] matrix, int r, int c) {
    int i = 0;
    int[] temp = new int[4];
    
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        if (row != r && col != c) {
          temp[i] = matrix[row][col];
          i++;
        }
      }
    }
    
    int cof = (temp[3] * temp[0]) - (temp[2] * temp[1]);
    
    if ((r + c) % 2 != 0) {
      return -cof;
    }
    
    return cof;
  }
  
  /**
   * Gets the determinant of the given matrix
   * 
   * @param matrix
   * @return int the determinant of the matrix
   */
  public static int getDet(int[][] matrix) {
    int x = (matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]));
    int y = (matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]));
    int z = (matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]));

    return x - y + z;
  }
}
