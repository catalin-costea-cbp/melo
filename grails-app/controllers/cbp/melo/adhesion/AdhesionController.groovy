package cbp.melo.adhesion

import cbp.melo.JNDIConfig
import grails.rest.RestfulController
import org.springframework.beans.factory.annotation.Autowired

import static groovyx.net.http.ContentType.URLENC

class AdhesionController {

    static responseFormats = ['json']
    private static Integer DEFAULT_MAX_RESULT = 100
    public static final String ID_SEPARATOR = ";"

    def creationAdhesion() {

        if (request.method == "GET") {
            render view: '/adhesion/creationAdhesion'
            return
        }

        def form = [:]
        getParams()
        def reponse = communicationService.newRESTClientHandlingError(meloJNDIConfig.urlServicesFare + "/adhesion/creationAdhesion").post([body: form, requestContentType: URLENC])
        System.out.println(reponse)
    }

    def index() { }

    @Autowired
    private JNDIConfig meloJNDIConfig

    def communicationService


}
