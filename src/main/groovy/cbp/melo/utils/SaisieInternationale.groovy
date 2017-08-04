package cbp.melo.utils

import grails.util.Holders
import org.codehaus.groovy.runtime.metaclass.MultipleSetterProperty
import org.grails.web.databinding.DefaultASTDatabindingHelper

class SaisieInternationale {

    /**
     * Injecte des proprietes permettant de binder plusieurs nom de parametres http sur un même champ d'objet
     * Exemple:
     *
     * Sur l'objet Personne, on veut que le champ nomUsuel corresponde à la fois à "nomUsuel" et a "lastName"
     * quand on fait un binding de controller
     */
    static def initInternationalFields(obj) {
        //nom de la contrainte qui donne le nom traduit du champ
        def constraintName = new ChampInternationalConstraint().name

        def contraintes
        //premier cas, hack car le comportement des DomainClasses n'est pas le même à l'init de l'application qu'au runtime
        //à l'init, le constraints retourne une closure, au runtime elle retourne une liste
        //ce n'est pas le cas pour les objets validateable
        if(Holders.getGrailsApplication()?.domainClasses?.find{it.name == obj.getClass().getName()}) {
            contraintes = Holders.getGrailsApplication().getDomainClass(obj.getClass().name).constraints
        } else {
            contraintes = obj.constraints
        }

        if(contraintes instanceof Map) {
            def internationalProperties = contraintes.collect {
                def valeurDeLaContrainte = it.value.getAppliedConstraint(constraintName)?.parameter ?: it.value.metaConstraints[constraintName]
                //on se la pete un peu, on peut même gérer une liste de traduction d'API
                if(valeurDeLaContrainte instanceof List) {
                    def intermediaire = []
                    valeurDeLaContrainte.each { uneValeur ->
                        intermediaire << [propertyName: it.key, international: uneValeur, type: it.value.propertyType]
                    }
                    return intermediaire
                } else {
                    return [propertyName: it.key, international: valeurDeLaContrainte, type: it.value.propertyType]
                }
            }.flatten()
            .findAll {
                it.international != null
            }

            internationalProperties.each {
                // et pour chacun de ses champs, on ajoute une meta-propriété, qui n'a de différent que son nom (les getter, setter, type, field sont partagés (références identiques))
                MetaProperty propertyExistante = obj.metaClass.getMetaProperty(it.propertyName)

                def setterPourNouvelleProperty
                /* petite subtilié, les compositions de DomainClass (typiquement le Pays dans Adresse)
                    Grails génère des MultipleSetterProperty, ce qui fait appraitre au runtime plusieurs setters
                    dans l'exemple du Pays, sur Adresse, on a void setPays(Pays pays) et void setPaysId(Long paysId)
                   Pour ce cas particulier, on est obliger de récuperer le setter dans les metaMethods
                */
                if (propertyExistante instanceof MetaBeanProperty) {
                    setterPourNouvelleProperty = propertyExistante.setter
                } else if (propertyExistante instanceof MultipleSetterProperty) {
                    setterPourNouvelleProperty = obj.metaClass.metaMethods.find { method -> method.name == "set${it.propertyName.capitalize()}" && method.nativeParamTypes?.length }
                }

                //on instancie la nouvelle propriété nommé selon la contrainte champInternational
                //en faisant pointer sur les accesseurs de la propriété existante
                MetaProperty newOne = new MetaBeanProperty("${it.international}", (Class) it.type, propertyExistante.getter, setterPourNouvelleProperty)
                //et on la fait pointer sur le même Field
                newOne.field = propertyExistante.field
                //et on l'ajoute à l'objet
                obj.metaClass."${it.international}" = newOne
            }

            try {
                def clazz = obj.class
                def whiteList = clazz.getDeclaredField(DefaultASTDatabindingHelper.DEFAULT_DATABINDING_WHITELIST)
                if (whiteList) {
                    def whiteListValue = whiteList.get(clazz)
                    if (whiteListValue instanceof List) {
                        def asList = (List) whiteListValue
                        asList.addAll(internationalProperties*.international)
                    }
                }
            } catch (Exception e) {
                //pas de liste sur les vaildateable
            }
        }
    }

}
