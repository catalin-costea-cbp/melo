package cbp.melo.security

class Requestmap {

	String url
	String configAttribute

	static constraints = {
		url size:1..500, unique: true
		configAttribute size:1..500
	}

	static mapping = {
		cache true
		id sqlType:"decimal(5)"
		version column:"revision", sqlType:"decimal(5)"
	}
}
