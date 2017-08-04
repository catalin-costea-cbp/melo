package secutity

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.StringUtils
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus

/**
 * authentifie un partenaire clavis en basic auth;
 * voir messages dans {@link fluxDevisEtendu_fr.properties}
 */
class BasicAuthenticationService {

    /**
     * @param controller
     * @return VRAI si l'authent est satisfaite, FAUX + rendu d'erreur sinon.
     */
    boolean authentifier(def controller, def request) {
        Authentification auth = extraireDe(request)
        if (!auth) {
            demanderBasicAuthentication(controller, "clavis.authentAbsente")
            log.info "<authentifier auth absente"
            return false
        }

        final String partenaire = controller.request.getHeader("X-Partenaire") ?: controller.params."X-Partenaire"
        if (!clavisService.isAuthentificationValide(partenaire, auth.login, auth.mdp)) {
            demanderBasicAuthentication(controller, "clavis.echecAuthent")
            log.info "<authentifier clavis KO partenaire $partenaire login ${auth.login}"
            return false
        }
        log.info "<authentifier OK partenaire $partenaire login ${auth.login}"
        return true
    }

    private void demanderBasicAuthentication(def controller, String codeMessage) {
        demanderDans(controller.response)
        //controller.render controller.message(code: codeMessage)
    }

    static class Authentification {
        String login
        String mdp
    }

    /**
     * @return null si la requête n'est pas en Basic Authent,
     * sinon l'{@link BasicAuthenticationService.Authentification} soumise.
     */
    Authentification extraireDe(def request) {
        // mince, request.authType retourne null en mode dev !
        // obligé de le recoder ?!!
        String enteteAuthorization = request?.getHeader(HttpHeaders.AUTHORIZATION)
        if (!org.apache.commons.lang.StringUtils.equalsIgnoreCase("BASIC", enteteAuthorization?.substring(0, 5))) {
            log.debug "entête $enteteAuthorization"
            return null
        }

        String loginDeuxPointMdpEncode = request.getHeader(HttpHeaders.AUTHORIZATION).substring(6);
        String loginDeuxPointMdp = StringUtils.newStringUtf8(Base64.decodeBase64(loginDeuxPointMdpEncode))
        String[] listeLoginMdp = loginDeuxPointMdp.split(":", 2)
        return new Authentification(login: listeLoginMdp.size() > 0 ? listeLoginMdp[0] : "",
                mdp: listeLoginMdp.size() > 1 ? listeLoginMdp[1] : "")
    }

    void demanderDans(def response) {
        response.status = HttpStatus.SC_UNAUTHORIZED
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"CLAVIS CBP Solutions\"")
    }

    def clavisService
}
