package cbp.melo.utils

import groovy.util.logging.Log4j
import groovyx.net.http.RESTClient
import org.apache.http.client.ClientProtocolException
import org.springframework.web.client.RestClientException
/**
 * Created by membre on 26/09/16.
 */
@Log4j
class RESTClientHandlingErrors {

    @Delegate
    RESTClient client

    RESTClientHandlingErrors(String uri) {
        client = new RESTClient(uri)
    }

    public Object get(Map<String, ?> arguments) throws ClientProtocolException,
            IOException, URISyntaxException {
        try {
            return client.get(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    public Object post(Map<String, ?> arguments)
            throws URISyntaxException, ClientProtocolException, IOException {
        try {
            return client.post(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    public Object put(Map<String, ?> arguments) throws URISyntaxException,
            ClientProtocolException, IOException {
        try {
            return client.put(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    public Object head(Map<String, ?> arguments) throws URISyntaxException,
            ClientProtocolException, IOException {
        try {
            return client.head(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    public Object delete(Map<String, ?> arguments) throws URISyntaxException,
            ClientProtocolException, IOException {
        try {
            return client.delete(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    public Object options(Map<String, ?> arguments) throws ClientProtocolException,
            IOException, URISyntaxException {
        try {
            return client.options(arguments)
        } catch (ConnectException ce) {
            traiterException(ce)
        }
    }

    def traiterException(ConnectException ce) {
        // si la cause est ajoutée, le gestionnaire d'exception du conteneur va chercher SYSTEMATIQUEMENT la cause mère de l'exception
        // cf GrailsExceptionResolver.getRootCause()
        // dc même si j'encapsule l'exception, ma contextualisation n'apparait ni dans les logs, ni à l'écran
        // throw new IllegalStateException("Echec de connexion à l'url ${uri}".toString(), ce)
        throw new RestClientException("Echec de connexion à  l'url ${uri} : ${ce.message}".toString()) //NOSONAR
    }
}