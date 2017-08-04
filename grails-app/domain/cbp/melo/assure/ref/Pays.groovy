package cbp.melo.assure.ref

import cbp.melo.utils.SaisieInternationale
import cbp.melo.utils.Traduction

class Pays implements Serializable {

    {
        SaisieInternationale.initInternationalFields(this)
    }

    String libelle
    String codePays

    String toString() {
        return Traduction.getLibelleReferentiel(this)
    }

    static constraints = {
        libelle(nullable: false, blank: false)
        codePays champInternational: 'identifier'
    }

    static mapping = {

        cache usage: 'read-only'
        table 'P1PPAYPF'
        id column: 'P_IDPAYS', generator: "assigned", sqlType: "decimal(5)"
        libelle column: 'C_NOMPAYS', sqlType: "character(38)"
        codePays column: 'C_CODEPAYS', sqlType: "character(2)"
        version false
    }

    static marshalling = {
        json {
            privateOpen {
                shouldOutputClass false
            }
        }
    }

    static Pays getAUTRICHE() {
        Pays.get(Ids.AUTRICHE.valeur)
    }

    static Pays getFRANCE() {
        Pays.get(Ids.FRANCE.valeur)
    }

    static Pays getALLEMAGNE() {
        Pays.get(Ids.ALLEMAGNE.valeur)
    }

    static Pays getITALIE() {
        Pays.get(Ids.ITALIE.valeur)
    }

    static enum Ids {
        FRANCE(71L), ITALIE(104L), ALGERIE(59L), ALLEMAGNE(54L), AUTRICHE(13L)

        Ids(Long id) {
            valeur = id
        }

        Long valeur
    }


    static List<Map> valeursBootStrap() {
        [
                [id: Ids.FRANCE.valeur, libelle: "FRANCE", codePays: "FR"],
                [id: Ids.ALGERIE.valeur, libelle: "ALGERIE", codePays: "DZ"],
                [id: Ids.ITALIE.valeur, libelle: "ITALIE", codePays: "IT"],
                [id: Ids.ALLEMAGNE.valeur, libelle: "ALLEMAGNE", codePays: "DE"]
        ]
    }
}
