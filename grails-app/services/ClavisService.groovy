import cbp.melo.JNDIConfig
import com.cbp.clavis.model.xml.PropagationRequest
import grails.converters.XML
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import org.springframework.beans.factory.annotation.Autowired

class ClavisService {

    /**
     * @return si le partenaire stipule un login/mdp correct - pour Devis Etendu en direct.
     */
    boolean isAuthentificationValide(def prefixe, def login, def mdp){
        def appName = meloJNDIConfig.clavisCrowdAppName
        def appPsw = meloJNDIConfig.clavisCrowdAppPassword
        def urlClavis = meloJNDIConfig.urlServicesClavisLogin + "/RESTServices/authentifieEtPropage"

        def http = communicationService.newHTTPBuilder(urlClavis)
        http.request(Method.POST, ContentType.XML) {
            body = new PropagationRequest(applicationName: appName,applicationPassword:appPsw,login:login,password:mdp,prefixePartenaire:prefixe ) as XML
            response.success = { resp ->
                true
            }
            response.failure = { resp ->
                false
            }
        }

    }

    @Autowired
    JNDIConfig meloJNDIConfig

    def communicationService
}
