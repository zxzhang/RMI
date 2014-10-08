package remote;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 *
 */
public class RemoteException extends java.io.IOException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public Throwable detail;

  public RemoteException(String s, Throwable cause) {
    super(s);
    initCause(null);
    detail = cause;
  }

  public String getMessage() {
    if (detail == null) {
      return super.getMessage();
    } else {
      return super.getMessage() + "; nested exception is: \n\t" + detail.toString();
    }
  }

  public Throwable getCause() {
    return detail;
  }
}
