package cbp.melo.security

import cbp.melo.societe.SocieteDeRattachement

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username size:1..30, unique: true
		password size:1..70
	}

	static mapping = {
		id sqlType:"decimal(5)"
		version column:"revision", sqlType:"decimal(5)"
		password column: "mot_de_passe"
		table "utilisateur"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsertManuel() {
		encodePassword()
		toLowerCase()
	}

	def beforeUpdateManuel() {
		if (springSecurityService && isDirty('password')) {
			encodePassword()
		}
		toLowerCase()
	}
	
	void toLowerCase(){
		username = username?.toLowerCase()
	}

	protected void encodePassword() {
		if (springSecurityService) {
			password = springSecurityService?.encodePassword(password)
		}
	}

	def getSocieteDeRattachement() {
		SocieteDeRattachement.findAllByNomProfil(username.toUpperCase())?.codeSociete as Object[]
	}

	boolean estRattacheASociete(Long codeSociete) {
		def societes = getSocieteDeRattachement()
		return !societes || societes.contains(codeSociete)
	}

	@Override
	public String toString() {
		username
	}
}
