import cbp.melo.Calendrier
import cbp.melo.JNDIConfig
import cbp.melo.adhesion.Adhesion
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import org.springframework.beans.factory.annotation.Autowired

class SupervisionService {

    final Closure tableRequetable = { classe ->
        neplantepas("lecture table $classe") {
            classe.read(1) // on se fiche de l'existence de la ligne 1, on veut juste tester la table
            return true
        }
    }

    final Closure aujParametre = {
        ->
        neplantepas("récup date du jour en base") { Calendrier.auj != null }
    }

    final Closure urlAccessible = { String urlService ->
        _debrancheUrlAccessible ?: neplantepas("récup page html $urlService") {
            def http = new HTTPBuilder(urlService)
            http.handler.failure = { HttpResponseDecorator resp ->
                throw new RuntimeException("(${resp.status}) ${resp.statusLine}")
            }
            http.get(requestContentType: ContentType.HTML)
            return true
        }
    }

    final Closure neplantepas = { String descriptionTraitement, Closure retourneVrai ->
        try {
            return retourneVrai()
        } catch (Exception e) {
            log.error "échec $descriptionTraitement pour SUPERVISION: ${e.message}".toString()
            return false
        }
    }

    /**
     * @return les vérifications les plus critiques et plus rapides possibles conditionnant l'ouverture de l'appli
     * (appelé toutes les 15 s par la haute dispo)
     */
    List<Map<String, Object>> getVerificationsDisponibilite() {
        return [[libelle: "base de paramétrage requêtable", etat: tableRequetable(Calendrier)],
                [libelle: "présence paramétrage calendrier", etat: aujParametre()],
                [libelle: "base de gestion requêtable", etat: tableRequetable(Adhesion)],
                verifConfigUrlAccessible("urlServicesDistributeur"), // quasi toutes les fonctionnalité en ont besoin.
                verifConfigUrlAccessible("urlServicesInocs"), // quasi toutes les fonctionnalité en ont besoin.
                verifConfigUrlAccessible("urlServicesFare"), // quasi toutes les fonctionnalité en ont besoin.
                verifConfigUrlAccessible("urlServicesMoni"), // trop critique pour ouvrir sans.
                verifConfigUrlAccessible("urlServicesProduit") // indispensable même pour consulter.
        ]
        /**
         * Les autres services n'affectent pas la disponibilité immédiate de l'application mais de certaines
         * fonctionnalités seulement:
         * - organisme prêteur : sauvegarde des modalités de règlement en back office et front PDL.
         * - éditions : éditions front et back office.
         * - tahoré : édition front office PDL et services/batchs impayés.
         * - socle : suggestion de code postal/localité en saisieAdhesion backoffice.
         * - pièces : bannette backoffice, éditions sortantes front et back office.
         * - index Tahoré : l'index de recherche est en "retard", à reconstruire via l'administration.
         * - mandatSepa : éditions front office PDL.
         * - zurix : quittancement (1 fois par moi) et remboursement (1 fois par semaine).
         * Un message d'erreur informe l'utilisateur de l'indisponibilité de la fonctionnalité à son appel.
         */
    }

    /**
     * @return les vérifications les plus exhaustives possibles conditionnant l'alerte aux exploitants
     * (appelé toutes les 2 min par nagios, à la demande par les livreurs)
     */
    List<Map<String, Object>> getVerifications() {
        def verifications = [[libelle: "base de paramétrage requêtable", etat: tableRequetable(Calendrier)],
                             [libelle: "présence paramétrage calendrier", etat: aujParametre()]]
                             [libelle: "base de gestion requêtable", etat: tableRequetable(Adhesion)]

        meloJNDIConfig.getProperties().each {
            if (it.key.startsWith('urlServices') && it.key != "urlServicesClavisLogin") {
                verifications += verifConfigUrlAccessible(it.key)
            }
        }
        verifications
    }

    Map<String, Object> verifConfigUrlAccessible(cleConfig) {
        String url = meloJNDIConfig."$cleConfig"
        return [libelle: "$cleConfig=$url", etat: urlAccessible(url)]
    }

    @Autowired
    private JNDIConfig meloJNDIConfig

    //NOSONAR
    static boolean _debrancheUrlAccessible = false
}
