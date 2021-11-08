package top.yang.oss.exception;

public class OSSClientException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private String errorCode = "Unknown";

  /**
   * Creates a new CosClientException with the specified message, and root cause.
   *
   * @param message An error message describing why this exception was thrown.
   * @param t       The underlying cause of this exception.
   */
  public OSSClientException(String message, Throwable t) {
    super(message, t);
  }

  public OSSClientException(String message, String errorCode, Throwable t) {
    super(message, t);
    this.errorCode = errorCode;
  }

  /**
   * Creates a new CosClientException with the specified message.
   *
   * @param message An error message describing why this exception was thrown.
   */
  public OSSClientException(String message) {
    super(message);
  }

  public OSSClientException(Throwable t) {
    super(t);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Returns a hint as to whether it makes sense to retry upon this exception. Default is true, but subclass may override.
   */
  public boolean isRetryable() {
    return true;
  }
}
