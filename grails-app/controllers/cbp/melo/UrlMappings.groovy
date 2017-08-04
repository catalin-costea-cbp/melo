package cbp.melo

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "adhesion", action: "creationAdhesion")
        //"/"(controller: "adhesion")
        //nouveau service de création d'adhésion - SANTANDERCB/WAVE/CNPSI/NRP
        '/public/secure/adhesion'(controller: 'adhesion', action: 'creationAdhesion')

        '500'(controller: 'errors')
        '/private/open/dispo'(controller: 'supervision', action: 'disponibilite')
        '/private/open/supervision'(controller: 'supervision', action: 'index')
        '/errors'(controller: 'errors')
        "404"(view:'/notFound')
    }
}
