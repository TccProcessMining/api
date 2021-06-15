package preprocessingmining.com.example.preprocessingmining.util.exception;

public class BasicRunTimeException extends RuntimeException{
    private final int httpStatusCode;
    private final String errorCode;
    private final String errorDescription;

    public BasicRunTimeException(int httpStatusCode, String errorCode, String errorDescription) {
        super(getErrorDescriptionForSuper(httpStatusCode, errorCode, errorDescription));
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    private static String getErrorDescriptionForSuper(int httpStatusCode, String errorCode, String errorDescription) {
        return "httpStatusCode |" + httpStatusCode + "|, errorCode |"+ errorCode+ "|, description -> " + errorDescription;
    }

}
