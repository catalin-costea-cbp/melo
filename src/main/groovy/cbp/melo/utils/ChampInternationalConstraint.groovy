package cbp.melo.utils

import grails.validation.AbstractConstraint
import grails.validation.ConstrainedProperty
import org.springframework.validation.Errors

class ChampInternationalConstraint extends AbstractConstraint {

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        //toujours valide, ce n'est que descriptif
    }

    @Override
    boolean supports(Class type) {
        true
    }

    @Override
    String getName() {
        return 'champInternational'
    }

    static register() {
        if (!ConstrainedProperty.hasRegisteredConstraint('champInternational')) {
            ConstrainedProperty.registerNewConstraint('champInternational', ChampInternationalConstraint)
        }
    }
}
