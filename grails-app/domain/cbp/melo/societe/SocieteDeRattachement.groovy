package cbp.melo.societe

class SocieteDeRattachement {

    static final String SOCIETE_EN_COURS = "SOCIETE_EN_COURS"

    String nomProfil
    Long codeSociete

    static mapping = {
        table "P1PUSOPF"
        cache usage: 'read-only'
        id generator: 'assigned', name: "codeSociete"
        codeSociete column:"X_CODESOCIETE", sqlType:"decimal(5)"
        nomProfil column:"X_PROFIL", sqlType:"character(10)"
        version false
    }

    static constraints = {
        nomProfil nullable:false, blank:false
        codeSociete nullable:false
    }

    @Override
    public String toString() {
        "${nomProfil?.trim()} - $codeSociete"
    }
}
