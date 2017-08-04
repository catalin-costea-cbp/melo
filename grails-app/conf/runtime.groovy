//Extrait doc grails 3
//A side effect of this change causes an exception when those commands are executed if the configuration relies on classes in the runtime.
//Ex : Error occurred running Grails CLI: startup failed:
//        script14738267015581837265078.groovy: 13: unable to resolve class com.foo.Bar
//The solution is to create a separate file called runtime.groovy in grails-app/conf. That file will not be parsed by the CLI and will only be included at runtime.
import cbp.CascadeValidationConstraint
import grails.validation.ConstrainedProperty
import cbp.melo.util.Nomdhote
import cbp.melo.util.doc.DocumentationConstraint