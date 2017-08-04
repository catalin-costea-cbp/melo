package cbp.melo.security

class Role {

	String authority

	static constraints = {
		authority size:1..50, unique: true
	}
	
	static mapping = {
		id sqlType:"decimal(5)"
		version column:"revision", sqlType:"decimal(5)"
		cache true
	}

	@Override
	public String toString() {
		authority
	}
}
