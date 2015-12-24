package business.exception;

/**
 * @author mdelapenya
 */
public class NotFoundException extends APIException {

	public NotFoundException() {
		super("Not Found");
	}

}
