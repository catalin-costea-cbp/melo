package cbp.melo;

/**
 * jours particulier du @link {@link Calendrier} en base.
 * 
 * @author jocal
 * 
 */
public enum JoursParticuliers {
	/*
	 * aujourd'hui
	 */
	AUJ("AUJ"), DERNIER_TRANSFERT_ENCAISSE("ENCAI");

	@Override
	public String toString() {
		return code;
	}

	JoursParticuliers(String code) {
		this.code = code;
	}

	private String code;

}
