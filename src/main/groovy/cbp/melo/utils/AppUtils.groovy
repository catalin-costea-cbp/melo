package cbp.melo.utils

import cbp.melo.JNDIConfig
import cbp.melo.societe.Societe
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by COSTEA on 10/05/2016.
 */
class AppUtils {

    /**
     * A partir du service (urlServicesInocs)
     * En mode dev, retourne l'url
     * Sinon, retourne le context
     * @return l'url ou le context du service
     */
    static String getBaseServiceInocs() {
        return Utils.getBaseUrl(meloJNDIConfig.urlServicesInocs)
    }

    /**
     * A partir du service (urlServicesInocs)
     * @return le context du service (/inocs)
     */
    static String getContextServiceInocs() {
        return Utils.getContextUrl(meloJNDIConfig.urlServicesInocs)
    }

    /**
     * A partir du service (urlServicesInocs)
     * @return le nom du service (inocs)
     */
    static String getUrlFare() {
        return meloJNDIConfig.urlServicesFare
    }

    static String getNomServiceInocs() {
        return StringUtils.remove(getContextServiceInocs(), "")
    }

    /**
     * NE PLUS UTILISER.
     *
     * Alternative recommandée = prendre la société du distributeur de l'adhésion:
     * <code> FuturDistributeur.findById(adhesion.distributeur.identifiant, [flushMode: FlushMode.MANUAL]).codeSociete
     * </code>
     *
     * {@link #societeDuDistributeurDe(cbp.tahore.adhesion.Adhesion)} n'est pas
     * recommandé, ne sert qu'à la réindexation ELS INTL en attendant le désendettement, il parcourt trop de relations.
     */
    @Deprecated
    static Long getIdSocieteSelonApplication() {
        if (onEstSurInocs()) {
            return Societe.Ids.CBP_ITALIA.valeur
        }
        return Societe.Ids.CBP_SOLUTION.valeur
    }

    @Autowired
    static private JNDIConfig meloJNDIConfig
}



