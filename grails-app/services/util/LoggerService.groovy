package util

/**
 * Service tracant tout ce qui entre dans un controleur dans un fichier de log dédié, cf cookbook log4j.xml
 */
class LoggerService {

    /**
     * @param controller
     */
    def loggerEntree(def actionName, def params, def controller) {
        StringBuilder sb = new StringBuilder()
        sb << ">" << actionName << '[' << controller.request.method << "] : entete ["
        controller.request.headerNames.each { key ->
            sb << "${key}=${controller.request.getHeader(key)}" << ", "
        }
        sb << "]\n\t params : ["
        params.each {
            if (it.value instanceof String) {
                sb << it << ","
            }
        }
        sb << "]"
        log.info sb.toString()
    }

    def loggerSortie(def actionName, def controller) {
        log.info "< ${actionName}[${controller.response.status}]"
    }

    def loggerSortieValeurs(def actionName, def controller, def params) {
        log.info "< ${actionName}[${controller.response.status}] : ${params}"
    }
}
