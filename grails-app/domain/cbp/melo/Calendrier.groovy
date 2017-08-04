package cbp.melo

import cbp.melo.utils.Utils
import groovy.time.TimeCategory
import org.hibernate.FlushMode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * calendrier en base; jour particulier "AUJ" à mettre à jour quotidiennement par batch en prod !
 * (interactivement par écran en dev)
 * @author jocal
 *
 */
class Calendrier {
	/**
	 * à utiliser dans le code métier dès qu'on veut baser une règle sur la date du jour
	 * @return aujourd'hui
	 */
	static Date getAuj() {
        if (_simulateurMaintenant) {
            return Utils.fixeHMS(_simulateurMaintenant, 0, 0, 0)
        }
		if (!cacheAujId) {
			// naturellement synchronisé par le bootstrapDistributeursCommercialisant qui est le premier appelant et qui est non concurrent
			cacheAujId = Calendrier.findByCodeJourParticulier(JoursParticuliers.AUJ.toString(), [flushMode:FlushMode.MANUAL])?.id
		}
		// on exploite le cache de session (cache de niveau 1) <=> la date n'est récupérée en base qu'1 fois par action.
		return cacheAujId ? Calendrier.get(cacheAujId)?.date : null
	}

	static Date getMaintenant() {
		if (_simulateurMaintenant) {
			return _simulateurMaintenant
		} else {
			use(TimeCategory){
				Date datePourHMS = new Date()
				return Utils.fixeHMS(auj, datePourHMS.hours, datePourHMS.minutes, datePourHMS.seconds)
			}
		}
	}

	/**
	 * à utiliser dans le batch quotidien de maj de la date d'aujourd'hui.
	 * Note: les testeurs passent par le contrôleur pour définir cette date.
	 * @param la date définie
	 */
	static void definitAuj(Date definie) {
		_simulateurMaintenant = null
		Calendrier aujourdhui = Calendrier.findOrCreateByCodeJourParticulier(JoursParticuliers.AUJ.toString())
		aujourdhui.date = definie
		if (!aujourdhui.save(true) || aujourdhui.hasErrors()) {
			throw new IllegalArgumentException("échec définition de ${JoursParticuliers.AUJ} en base: ${Utils.resume_erreur(aujourdhui)}" )
		}
		cacheAujId = aujourdhui.id
	}


	/**
	 * à utiliser dans le batch quotidien de maj de la date d'aujourd'hui.
	 * Note: les testeurs passent par le contrôleur pour définir cette date.
	 */
	static void definitAujAAujourdhui() {
		definitAuj(cbp.melo.utils.Utils.minuit(new Date()))
	}

	static constraints = {
		codeJourParticulier(nullable:false, unique:true)
		date(nullable:false)
	}

	static transients = ["auj", "maintenant"]

	String codeJourParticulier
	Date date
	Date dateCreated
	Date lastUpdated
	static Long cacheAujId // cache de l'id du code jour AUJ - stable au moins sur une version déployée de Tahoré
	static Date _simulateurMaintenant = null // pour les tests

	static mapping = {
		id sqlType:"decimal(11)"
		version column:"revision", sqlType:"decimal(5)"
		date column:"VALEUR_DE_DATE", sqlType:"date"
		codeJourParticulier sqlType:"varchar(5)"
		cache true
	}

	static Logger log = LoggerFactory.getLogger(Calendrier)

    static Date getAujPlusUnAn() {
		use(TimeCategory) {
			return auj + 1.year
		}
    }
}
