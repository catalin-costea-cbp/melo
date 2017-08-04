package cbp.melo.societe

import cbp.melo.assure.ref.Pays
import cbp.melo.utils.AppUtils
import groovy.transform.ToString
import org.hibernate.FlushMode

@ToString(includeNames = true)
class Societe {

    String nom
    Pays pays

    static Societe getEnCours() {
        Long societeId = AppUtils.idSocieteSelonApplication
        Societe.findById(societeId)
    }

    static transients = ['enCours', 'isCBPSolution', 'isInternationale']

    static constraints = {
    }

    static mapping = {
        table "A0ISTEPF"
        id column: "P_CODESOCIETE", sqlType: "decimal(5)", generator: "assigned"
        pays column: "F_IDPAYS", sqlType: "decimal(5)"
        nom column: "C_NOMSOCIETE", sqlType: "character(35)"
        cache usage: 'read-only'
        version false
    }



    boolean isCBPSolution() {
        id == Societe.Ids.CBP_SOLUTION.valeur
    }

    boolean isInternationale() {
        return !isCBPSolution()
    }

    static Societe getSansFlush(Long id) {
        Societe.findById(id, [flushMode: FlushMode.MANUAL])
    }

    static enum Ids {
        CBP_DEUTSCHLAND(13L), CBP_SOLUTION(4L), CBP_ITALIA(11L), CBP_PDP(12L), CBP_AUSTRIA(15L)

        Ids(Long id) {
            valeur = id
        }

        Long valeur
    }

    static List<Map> valeursBootStrap() {
        [
                [id: Ids.CBP_DEUTSCHLAND.valeur, nom: "CBP Deutschland", pays: Pays.ALLEMAGNE],
                [id: Ids.CBP_SOLUTION.valeur, nom: "CBP Solutions", pays: Pays.FRANCE],
                [id: Ids.CBP_ITALIA.valeur, nom: "CBP Italia", pays: Pays.ITALIE]
        ]
    }
}