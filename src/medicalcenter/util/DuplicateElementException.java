package medicalcenter.util;

public class DuplicateElementException extends IllegalArgumentException
{
	private static final long serialVersionUID = -6392225633051530996L;


	public DuplicateElementException(Object element) {
		this("duplicate element: " + element);
	}

	public DuplicateElementException(String message) {
		super(message);
	}

	public DuplicateElementException(Throwable cause) {
		super(cause);
	}

	public DuplicateElementException(String message, Throwable cause) {
		super(message, cause);
	}
}
