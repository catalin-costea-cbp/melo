package cbp.melo.supervision

import java.text.SimpleDateFormat

class SupervisionController {

    def index() {
        return [jalon        : new SimpleDateFormat("E dd MMMM yyyy à HH:mm:ss z").format(java.util.Calendar.getInstance().getTime()),
                environnement: System.getProperty("env"),
                verifications: supervisionService.verifications]
    }

    def disponibilite() {
        if (params.html) {
            return [jalon        : new SimpleDateFormat("E dd MMMM yyyy à HH:mm:ss z").format(java.util.Calendar.getInstance().getTime()),
                    environnement: System.getProperty("env"),
                    verifications: supervisionService.verificationsDisponibilite]
        } else {
            Map echec = supervisionService.verificationsDisponibilite.find {!it.etat}
            if(echec) {
                log.error(echec.libelle)
                render text: "KO", contentType: "text/plain"
            }else{
                render text: "OK", contentType: "text/plain"
            }
        }
    }

    def supervisionService
}
