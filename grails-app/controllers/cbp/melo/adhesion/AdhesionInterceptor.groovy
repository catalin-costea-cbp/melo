package cbp.melo.adhesion


class AdhesionInterceptor {

    AdhesionInterceptor() {
        match( controller: "adhesion2" )
    }

    boolean before() {
        authentifierEtLogger()
    }

    boolean after() {
        loggerSortie()
        true
    }

    void afterView() {
        // no-op
    }

    protected boolean authentifierEtLogger() {
        getWebRequest()
        params
        if (basicAuthenticationService.authentifier(getWebRequest(), request)) {
            loggerService.loggerEntree(actionName, params, this)
            return true
        } else {
            return false
        }
    }

    protected def loggerSortie() {
        // cet intercepteur n'est pas en mesure de récupérer la sortie exact, c'est pour cela qu'il y a des appels explicites en sortie de méthode
        loggerService.loggerSortie(actionName, this)
    }

    def basicAuthenticationService
    def loggerService
}
