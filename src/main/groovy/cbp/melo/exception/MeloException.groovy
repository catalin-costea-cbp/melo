package cbp.melo.exception

class MeloException extends RuntimeException{

	public MeloException() {
		super();
	}

	public MeloException(String message, Throwable cause) {
		super(message, cause);
	}

	public MeloException(String message) {
		super(message);
	}

	public MeloException(Throwable cause) {
		super(cause);
	}

}
