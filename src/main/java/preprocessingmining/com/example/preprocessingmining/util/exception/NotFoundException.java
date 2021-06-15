package preprocessingmining.com.example.preprocessingmining.util.exception;

public class NotFoundException extends BasicRunTimeException {
    private static final int HTTP_STATUS_CODE = 404;
    private static final String ERROR_CODE = "not_found";
    public NotFoundException(String message) {
        super(HTTP_STATUS_CODE, ERROR_CODE, message);
    }
}
