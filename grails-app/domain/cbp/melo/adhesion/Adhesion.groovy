package cbp.melo.adhesion

import groovy.transform.ToString
import groovy.util.logging.Slf4j

@ToString
@Slf4j
class Adhesion {


    static mapping = {

        id sqlType: "decimal(11)"
        version column: "revision", sqlType: "decimal(5)"


    }
}
