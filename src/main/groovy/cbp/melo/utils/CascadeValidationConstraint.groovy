package cbp.melo.utils

import grails.validation.AbstractVetoingConstraint
import org.springframework.validation.Errors
import org.springframework.validation.FieldError

/**
 * Permet de cascader les contraintes du les commandes objects.
 * Inspiré de http://asoftwareguy.com/2013/07/01/grails-cascade-validation-for-pogos/
 */
class CascadeValidationConstraint extends AbstractVetoingConstraint{
    public static final String NAME = "cascadeValidation"

    @Override
    String getName() {
        NAME
    }

    @Override
    boolean supports(Class type) {
        true
    }

    @Override
    public void setParameter(Object constraintParameter) {
        if (!(constraintParameter instanceof Boolean)) {
            throw new IllegalArgumentException(
                    """Parameter for constraint [$name] of
                   property [$constraintPropertyName]
                   of class [$constraintOwningClass]
                   must be a Boolean
                """
            )
        }
        super.setParameter(constraintParameter)
    }

    @Override
    protected boolean skipNullValues() {
        return true
    }

    @Override
    public boolean processValidateWithVetoing(
            Object target, Object propertyValue,
            Errors errors) {
        if (!propertyValue.validate()) {
            propertyValue.errors.fieldErrors.each { FieldError fe ->
                String field = "${propertyName}.${fe.field}"
                String[] codes = fe.codes?.collect {it.replace(fe.field, field) }
                def fieldError = new FieldError(target.errors.objectName, field, fe.rejectedValue,
                        fe.bindingFailure, codes, fe.arguments,fe.defaultMessage)
                errors.addError(fieldError)
            }
            return false
        }
        return true
    }
}
