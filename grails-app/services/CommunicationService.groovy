import cbp.melo.utils.RESTClientHandlingErrors
import groovy.sql.Sql
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.client.ClientProtocolException
import org.hibernate.StatelessSession
import org.springframework.web.client.RestClientException

class CommunicationService {

    /**
     * essentiellement pour pouvoir mocker un httpbuilder
     * @param uri
     * @return un nouvel httpbuilder pour l'uri
     * @Deprecated utiliser {@link #newHTTPBuilderHandlingError(java.lang.String)}
     */
    @Deprecated
    HTTPBuilder newHTTPBuilder(String uri) {
        new HTTPBuilder(uri)
    }

    /**
     * retourne un HTTPBuilder enrichi pour afficher l'url de connexion en cas d'erreur de type ConnectException
     * @param uri
     * @return un nouvel httpbuilder pour l'uri
     */
    HTTPBuilder newHTTPBuilderHandlingError(String uri) {
        // note d'implémentation :
        //  - HTTPBuilder est écrit en java, dc pas de metaclass
        //  - HTTPBuilder n'implémente pas d'interface, dc pas de proxy dynamic
        //  - alors on fait de l'héritage simple avec surcharge de méthode
        new HTTPBuilderHandlingError(uri)
    }

    /**
     * HTTPBuilder enrichi pour afficher l'url de connexion en cas d'erreur de type ConnectException
     */
    class HTTPBuilderHandlingError extends HTTPBuilder {

        HTTPBuilderHandlingError(String uri) {
            super(uri)
        }

        @Override
        Object doRequest(final HTTPBuilder.RequestConfigDelegate rcd)
                throws ClientProtocolException, IOException {
            try {
                super.doRequest(rcd)
            } catch (ConnectException ce) {
                // si la cause est ajoutée, le gestionnaire d'exception du conteneur va chercher SYSTEMATIQUEMENT la cause mère de l'exception
                // cf GrailsExceptionResolver.getRootCause()
                // dc même si j'encapsule l'exception, ma contextualisation n'apparait ni dans les logs, ni à l'écran
//                throw new IllegalStateException("Echec de connexion à l'url ${uri}".toString(), ce)
                throw new RestClientException("Echec de connexion à  l'url ${uri} : ${ce.message}".toString()) //NOSONAR
            }
        }
    }

    /**
     * Idem {@link #newHTTPBuilder(String)}
     */
    @Deprecated
    RESTClient newRESTClient(String uri) {
        return new RESTClient(uri)
    }

    /**
     * Retourne un client REST prenant en charge la gestion des erreurs techniques
     * Idem {@link #newHTTPBuilder(String)}
     */
    def newRESTClientHandlingError(String uri) {
        def client = new RESTClientHandlingErrors(uri)
        client.handler.failure = { HttpResponseDecorator resp ->
            log.error("Erreur de récupération de l'appel ${client.uri}. Code html ${resp.status}. Page html retournée : " + resp.getEntity()?.content?.getText())
            throw new RestClientException("Erreur d'appel de service.")
        }
        client
    }

    /**
     * pour mocker plus facilement
     * @return facade SQL de Groovy connectée via la datasource principale
     */
    Sql newSql(def dataSource = this.dataSource) {
        return new Sql(dataSource)
    }


    def statelessSession(Closure codeAvecSession) {
        StatelessSession session = sessionFactory.openStatelessSession()
        try {
            codeAvecSession.call(session)
        } finally {
            try {
                session.close()
            }
            catch (RuntimeException closeE) {//NOSONAR
                // ignore
            }
        }
    }

    /**
     * @return url des services ajax dans le contexte mashup (pour le navigateur, l'adresse principale
     * est celle du mashup; le composant mashup ne peut faire ses interactions ajax avec des urls
     * relatives)
     */
    String getUrlModeMashup(request) {
        String httpsReverseProxy = request.getHeader("X-Forwarded-Proto")
        String scheme = httpsReverseProxy ?: request.scheme
        String port = httpsReverseProxy ? "" : request.serverPort
        String contextPath = grailsLinkGenerator.contextPath
        return "$scheme://${request.serverName}:$port$contextPath"
    }


    def grailsLinkGenerator
    def sessionFactory
    def dataSource
    static transactional = false
}
