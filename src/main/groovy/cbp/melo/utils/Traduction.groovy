package cbp.melo.utils

class Traduction {

    static String getLibelleReferentielAvecLocale(objet, Locale locale) {
        if (objet != null) {
            getLibelleReferentiel("${objet.class.simpleName}.${objet.id}", objet.libelle, locale)
        }
    }

    static String getLibelleReferentiel(String code, String defaut, Locale locale) {
        try {
            cbpTag.traductionAvecLocale([code: code, locale: locale])
        } catch (IllegalStateException ise) {
            //pour les test d'intégration ou il n'y a pas de request
            return defaut
        }
    }

    static String getLibelleReferentiel(objet) {
        getLibelleReferentiel("${objet.class.simpleName}.${objet.id}", objet.libelle)
    }

    static String getLibelleReferentiel(String code, String defaut) {
        try {
            cbpTag.traduction([code: code])
        } catch (IllegalStateException ise) {
            //pour les test d'intégration ou il n'y a pas de request
            return defaut
        }
    }

    static String getMessageI18n(def code) {
        try {
            cbpTag.traduction([code: code])
        } catch (any) {
            return code
        }

    }

    static def getCbpTag(){
        new CbpTagLib()
    }
}
