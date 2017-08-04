package cbp.melo.utils.doc

import grails.validation.AbstractConstraint
import grails.validation.ConstrainedProperty
import org.springframework.validation.Errors

/**
 * Created by jocal on 03/02/16. D'après http://stackoverflow.com/questions/14733844/custom-simple-constraint-grails
 *
 * De la documentation dans les contraintes de champ; permet de rédiger des règles
 * directement sous forme de Domain class avec contrainte de documentation; les règles sont alors directement
 * sous le nez du développeur pour implémenter et tester. Permet enfin de générer cette documentation de règles
 * théoriques dès l'entrée en développement et la conserver & maintenir après développement.
 *
 * A inscrire dans le système de validation Grails en statique des domain via
 * {@link org.codehaus.groovy.grails.validation.ConstrainedProperty#hasRegisteredConstraint(java.lang.String)} } et
 * {@link org.codehaus.groovy.grails.validation.ConstrainedProperty#registerNewConstraint(java.lang.String, java.lang.Class)}
 * pour un fonctionnement disponible en run dev et test.
 */
class DocumentationConstraint extends AbstractConstraint {
    @Override
    String getName() {
        return "doc"
    }

    /**
     * @return ok pour tous les types de champs
     */
    @Override
    boolean supports(Class type) {
        return type != null
    }

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        // toujours valide, aucun appel à rejectValue
    }

    /**
     * à appeler dans le bloc environments.test de config.groovy
     */
    static boolean registerIfNeeded() {
        if (!ConstrainedProperty.hasRegisteredConstraint("doc")) {
            ConstrainedProperty.registerNewConstraint("doc", DocumentationConstraint)
            return true
        } else {
            return false
        }
    }
}
