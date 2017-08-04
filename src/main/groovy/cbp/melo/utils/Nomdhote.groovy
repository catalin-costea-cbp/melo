package cbp.melo.utils

/**
 * Created by jocal on 26/09/2014.
 */
class Nomdhote {

    /**
     * le minuscules sont appréciées par Elastic Search pour nommer un index
     */
    static String getMinuscule() {
        return java.net.InetAddress.getLocalHost().getHostName().toLowerCase()
    }
}
