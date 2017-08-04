package cbp.melo.utils

import cbp.melo.Calendrier
import cbp.melo.exception.MeloException
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.lowagie.text.pdf.PdfCopyFields
import com.lowagie.text.pdf.PdfReader
import grails.util.Environment
import groovy.time.TimeCategory
import groovy.util.logging.Slf4j
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

import javax.persistence.OptimisticLockException
import java.math.RoundingMode
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.text.SimpleDateFormat

/**
 * utilitaires pour les règles de gestions, les jeux de tests, et les assertions de tests.
 * @author jocal
 *
 */
@Slf4j
class Utils {

    /**
     * Renvoie une date de même jour, mois, année que le paramètre mais avec des heures, minutes, secondes à 0.
     * @param date
     * @return
     */
    public static Date minuit(Date date) {
        return fixeHMS(date, 0, 0, 0)
    }

    public static Date fixeHMS(Date date, int heure0a23, int minute0a59, int seconde0a59) {
        if (date) {
            Calendar calendar = new GregorianCalendar()
            calendar.setTime(date)
            calendar.set(Calendar.HOUR_OF_DAY, heure0a23)
            calendar.set(Calendar.MINUTE, minute0a59)
            calendar.set(Calendar.SECOND, seconde0a59)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.getTime()
        } else {
            return null
        }
    }

    public static Date jma(int jour1a31, int mois1a12, int annee4chiffres) {
        return new GregorianCalendar(annee4chiffres, mois1a12 - 1, jour1a31).getTime()
    }

    public
    static Date hmsjma(int heure0a23, int minute0a59, int seconde0a59, int jour1a31, int mois1a12, int annee4chiffres) {
        return new GregorianCalendar(annee4chiffres, mois1a12 - 1, jour1a31, heure0a23, minute0a59, seconde0a59).getTime()
    }

    /**
     * la version String de la date jma fournie par Groovy.
     * Permet de faire des assertions plus facile à lire et comparer visuellement.
     */
    public static String jmastr(int jour1a31, int mois1a12, int annee4chiffres) {
        return jma(jour1a31, mois1a12, annee4chiffres).dateString
    }

    /**
     * une date dans l'année en cours, lorsque l'année n'est pas significative.
     * Permet de raccourcir la signature au plus significatif.
     */
    public static Date jm(int jour1a31, int mois1a12, int anneeenplus = 0) {
        if (mois1a12 == 2 && jour1a31 == 28) {
            log.warn "MEFIEZ VOUS vous manipulez le 28 fev de l'année en cours - dernier ou avant dernier j du mois " +
                    "selon l'année."
        }
        return jma(jour1a31, mois1a12, new GregorianCalendar().get(Calendar.YEAR) + anneeenplus)
    }

    /**
     * la version String de la date jm fournie par Groovy.
     * Permet de faire des assertions plus facile à lire et comparer visuellement.
     */
    public static String jmstr(int jour1a31, int mois1a12) {
        return jm(jour1a31, mois1a12).dateString
    }

    public static Date jourDuMois(Date date, int jour) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, jour);
        return c.getTime()
    }


    public static Date finDuMoisDe(Date date) {
        Date ret
        if (date) {
            /* Proposition de M Dominique Jocal de Nantes */
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            ret = c.getTime()
            /* Proposition de M Sébastien Errien de Nantes */
            //		int jour = c.get(Calendar.DAY_OF_MONTH)
            //		use(TimeCategory) {
            //			return date + 1.months - jour.days
            //		}
        }
        return ret
    }

    public static Date debutDuProchainMoisDe(Date date) {
        if (date == null) {
            return null
        }
        /* Proposition de M Dominique Jocal de Nantes */
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, 1)
        return c.getTime()
    }

    public static Date debutDuMoisDe(Date date) {
        if (date == null) {
            return null
        }
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime()
    }

    public static Date debutDuMoisMinuit(Date date) {
        return minuit(debutDuMoisDe(date))
    }

    public static Date finDuMois23H59m59s(Date date) {
        if (date == null) {
            return null
        }
        Calendar calendar = new GregorianCalendar()
        calendar.setTime(fixeHMS(finDuMoisDe(date), 23, 59, 59))
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.getTime()
    }

    public static Date finDuJour23H59m59s(Date date) {
        if (date == null) {
            return null
        }
        Calendar calendar = new GregorianCalendar()
        calendar.setTime(fixeHMS(date, 23, 59, 59))
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.getTime()
    }

    public static Date debutDuJour0H0m0s(Date date) {
        if (date == null) {
            return null
        }
        Calendar calendar = new GregorianCalendar()
        calendar.setTime(fixeHMS(date, 0, 0, 0))
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.getTime()
    }

    public static Date debutDeLAnneeCivileDe(Date date) {
        if (date == null) {
            return null
        }
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, 0);
        return minuit(c.getTime())
    }

    public static Date finDeLAnneeCivileDe(Date debut) {
        Date debutAnnee = debutDeLAnneeCivileDe(debut)
        Date finAnnee
        use(TimeCategory) {
            finAnnee = debutAnnee + 1.year
            finAnnee = finAnnee - 1.day
        }
        return finAnnee
    }

    public static int joursDansLeMoisDe(Date date) {
        Calendar c = new GregorianCalendar()
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    public static int joursDansLannee(Date date) {
        return cal(date).get(Calendar.DAY_OF_YEAR)
    }

    static Integer jour(Date date) {
        return cal(date)?.get(Calendar.DAY_OF_MONTH)
    }

    static Integer mois(Date date) {
        return date ? cal(date)?.get(Calendar.MONTH) + 1 : null
    }

    public static int age(Date dateNaissance) {
        Date today = Calendrier.auj
        //On clone la date de naissance
        Date naissance = dateNaissance.clone()
        //Le nombre d'année de différence
        int anneeDiff = today.getAt(Calendar.YEAR) - naissance.getAt(Calendar.YEAR);
        naissance.putAt(Calendar.YEAR, today.getAt(Calendar.YEAR));
        //Si l'anniversaire a déjà été celebré, on renvoit le nbr d'année de diff, -1 sinon
        return (!naissance.after(today)) ? anneeDiff : Math.max(0, anneeDiff - 1)
    }

    public static Calendar cal(Date date) {
        if (date) {

            Calendar cal = new GregorianCalendar()
            cal.setTime(date)
            return cal
        } else {
            return null
        }
    }

    /**
     * Retourne <b>TRUE</b> si l'environnement est <i>DEVELOPMENT</i> ou <i>TEST</i>
     * @return
     */
    public static boolean environmentForDevelopers() {
        (Environment.current == Environment.DEVELOPMENT
                || Environment.current == Environment.TEST)
    }

    /**
     * Transforme une exception en chaîne.
     * @param e
     * @return
     * 		"" si pas d'exception en paramètre.
     */
    public static String exceptionToString(Exception e) {
        String exceptionToString
        StringWriter sw
        PrintWriter pw
        try {
            sw = new StringWriter()
            pw = new PrintWriter(sw)
            e?.printStackTrace(pw)
            exceptionToString = sw.toString()
        } finally {
            IOUtils.closeQuietly(sw)
            IOUtils.closeQuietly(pw)
        }
        exceptionToString
    }

    /**
     * Pour le cascade d'erreur de validation composant/composé;
     * Pour les logs d'erreurs de validation dans le code;
     * Pour les assertions de validation dans les tests;
     *
     * Affiche un résumé de l'erreur de validation de l'objet validable (domaine ou commande).
     * @return "" si aucune erreur, "champ.contrainte.violation" ou "champ.violation" s'il y a une erreur.
     *
     * Usage en mode test:
     * 	risque.validate() // validation d'un Risque
     * 	assert !resume_erreur(risque) // devait être valide
     *
     * Assertion failed:
     *
     * 	assert !resume_erreur(risque)
     * 	       ||             |
     * 	       ||             Montant de 5 000,00€ - Option de 00,00€
     * 	       |montant.range.toobig
     * 	       false
     */
    static String resume_erreur(def validateable) {
        // en grails 2.3.11 hasErrors() bugge sur les mockCommandObject !! Cette alternative fonctionne.
        if (validateable?.errors?.errorCount) {
            FieldError fieldError = validateable?.errors?.fieldErrorCount ? validateable.errors.fieldErrors[0] : null
            if (fieldError) {
                return "${fieldError.field}.${fieldError.code}"
            } else {
                return "${validateable?.errors?.globalError}"
            }
        } else {
            return ""
        }
    }

    static String resume_toutes_erreurs(def validateable) {
        StringBuilder resume = new StringBuilder("\n")
        validateable.errors.allErrors.each { ObjectError err ->
            if (err instanceof FieldError) {
                FieldError ferr = (FieldError) err
                resume.append("${ferr.field}.${ferr.code}:$ferr\n")
            } else {
                resume.append("$err\n")
            }
        }
        return resume.toString()
    }

    static def listeToutesLesErreurs(def validateable) {
        List liste = []

        if (validateable.hasProperty('errors')) {
            if (validateable.hasErrors()) {
                validateable.errors.allErrors.each {
                    def champEnErreur = validateable."${it.field}"

                    if (champEnErreur instanceof List) {
                        champEnErreur.eachWithIndex { elem, index ->
                            if (champEnErreur.metaClass.properties.find { it.hasProperty('errors') }) {
                                liste << listeToutesLesErreurs(champEnErreur)
                            } else {
                                if (elem.hasErrors()) {
                                    elem.errors.allErrors.each {
                                        liste << "${it.objectName}.${index}.${it.field}.${it.code}"
                                    }
                                }
                            }
                        }
                    } else if (champEnErreur?.hasErrors()) {
                        if (champEnErreur.metaClass.properties.find { it.hasProperty('errors') }) {
                            liste << listeToutesLesErreurs(champEnErreur)
                        } else {
                            champEnErreur.errors.allErrors.each {
                                liste << "${it.objectName}.${it.field}.${it.code}"
                            }
                        }
                    } else {
                        liste << "${it.objectName}.${it.field}.${it.codes.last()}"
                    }
                }
            }
        }

        liste
    }

    static String valide_resume(def validateable) {
        validateable.validate()
        return resume_erreur(validateable)
    }

    /**
     * pour simplifier la double vérification de version imposée par Grails.
     *
     * Usage:
     * if (!machinInstance.saveWithCheckingVersion(Machin.get(params.id), params) {*           render(view: "edit", model: [machinInstance: machinInstance])
     *}* redirect(action: "show", id: machinInstance.id)
     *
     * @param existant_en_base - ex Machin.get(params.id)
     * @param nouveau_en_params - ex: params
     * @return false si échec verrou optimiste;
     * l'objet errors de l'instance est enrichi de l'erreur echec.verrou associée à un message générique.
     */
    static boolean saveWithCheckingVersion(existant_en_base, nouveau_en_params) {
        def instance = existant_en_base
        if (nouveau_en_params.version) {
            def version = nouveau_en_params.version.toLong()
            if (instance.version > version) {
                instance.errors.rejectValue("version", "echec.verrou")
                return false
            }
        }
        instance.properties = nouveau_en_params
        try {
            return instance.save(flush: true)
        } catch (OptimisticLockException e) {
            instance.errors.rejectValue("version", "echec.verrou")
            return false
        }
    }

    /**
     * Pour simplifier la double vérification de version imposée par Grails.
     *
     * @param existant_en_base - ex Machin.get(params.id)
     * @param nouvelleVersion - ex: la version qui provient de l'écran
     * @param instance_pour_ajouter_erreur - ex: adhesion
     * @return false si échec verrou optimiste;
     * l'objet errors de <i>instance_pour_ajouter_erreur</i> ou <i>existant_en_base</i> si null est enrichi de l'erreur echec.verrou associée à un message générique.
     */
    static boolean verifierVersion(existant_en_base, nouvelleVersion, instance_pour_ajouter_erreur) {
        def instance = existant_en_base
        if (instance_pour_ajouter_erreur == null) {
            instance_pour_ajouter_erreur = instance
        }
        if (nouvelleVersion) {
            def version = nouvelleVersion.toLong()
            if (instance.version > version) {
                instance_pour_ajouter_erreur.errors.rejectValue("version", "echec.verrou")
                return false
            }
        }
        return true
    }

    /**
     * @param montant exemple: 845.5G
     * @return exemple: "845.50"
     * @throws IllegalArgumentException si montant null - à vous de maitriser l'affichage d'un montant null.
     */
    static String formatAvecPointDeuxDecimales(BigDecimal montant) {
        DecimalFormat f = DecimalFormat.getInstance()
        DecimalFormatSymbols s = new DecimalFormatSymbols()
        s.setDecimalSeparator((char) '.')
        f.setDecimalFormatSymbols(s)
        f.setMinimumFractionDigits(2)
        return f.format(montant)
    }

    /**
     * Permet de vérifier qu'une date est valide i.e non Lenient.
     *
     * <br/>A note que le validateur ne prend pas en charge les chaines vides ou ne contenant que des caractères '/'.
     * C'est au domain object de prendre en charge les dates null, les années sur 2 chiffres =  2 chiffres après JC.
     * @param date
     * 		la chaine qui représente une date provenant d'une vue.
     * Exemple : "${params.dateFermeture_day}/${params.dateFermeture_month}/${params.dateFermeture_year}"
     * @return
     * 	<ul>
     * 		<li><b>format</b> si la transformation lance une exception et que la chaine contient le mot <i>undefined</i></li>
     * 		<li><b>lenient</b> si la transformation lance une exception </li>
     * 		<li><b>true</b> si la date est valide c a d conforme à dd/mm/yyyy, d/m/yy; attention yy signifie 00yy.</li>
     * </ul>
     */
    final static def VALIDATEUR_DE_DATE(String date) {
        if (DATE_IS_NOT_EMPTY(date)) {
            //Si la vue n'a pas réussie a parsser la date, elle renvoit undefined comme valeur.
            if (date.contains("undefined")) {
                return "formatUndefined"
            }
            SimpleDateFormat sdfJMA = new SimpleDateFormat(FORMAT_DATE_CBP)
            sdfJMA.setLenient(false)
            try {
                sdfJMA.parse(date)
            } catch (Exception e) {
                //Ce cas traite les date comme 35/04/2012
                return "format"
            }
        }
        return true
    }

    final static boolean DATE_IS_NOT_EMPTY(String date) {
        return StringUtils.isNotBlank(date) && date.replace("/", "").trim().length() > 0
    }


    final static String FORMAT_DATE_CBP = "d/M/yyyy"
    final static String PATTERN_DATE_CBP = "\\d+/\\d+/\\d\\d\\d\\d"

    final static Date depuisFormatCbp(String date) {
        if (DATE_IS_NOT_EMPTY(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_CBP)
            sdf.setLenient(false)
            return sdf.parse(date)
        } else {
            return null
        }
    }


    /**
     * pour bootstrapDistributeursCommercialisant des tests u
     */
    static Object assertSaveOk(Object domain) {
        assert domain.save(), resume_erreur(domain)
        return domain
    }

    /**
     * OBSOLETE ! Si
     * @param domain n'a pas d'id alors lui assigne comme id la valeur
     * @param id , et le sauvegarde, et
     * @return si la sauvegarde a fonctionné.
     * Sinon ne fait rien et retourne vrai.
     * @deprecated utiliser désormais {@link #sauveOuMaj(Class, Map)}
     */
    static boolean saveWithAssignedIdWhenCreated(def domain, Long id) {
        if ((!domain.id) || (domain.dirty)) {
            saveWithAssignedId(domain, id)
        } else {
            return true
        }
    }

    /**
     * pour les tests
     */
    static Exception souleveePar(Closure code) {
        try {
            code()
        } catch (Exception e) {
            return e
        }
        return null
    }

    /**
     * <p>Fonction qui encode la chaîne passée en paramètre en url-encoding UTF-8.</p>
     * <p>A utiliser pour les chaînes comportant des caractères spéciaux transmises par HTTP.</p>
     */
    static String urlEncodeUTF8(String content) {
        if (content) {
            URLEncoder.encode(content, "UTF-8")
        } else {
            return ""
        }
    }

    static Date prochainVendredi(Date jour) {
        Calendar vendredi = new GregorianCalendar()
        vendredi.setTime(jour)
        vendredi.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        if (vendredi.time < jour) { // cas samedi et dimanche
            vendredi.add(Calendar.DAY_OF_YEAR, 7)
        }
        return vendredi.time
    }

    static void nebloquepas(List<AssertionError> erreurs = [], Closure code) {
        try {
            code()
        } catch (AssertionError e) {
            LoggerFactory.getLogger("cbp.nebloquepas").info "$e"
            erreurs.add e
        }
    }

    /**
     * @param value
     *          null ou vide retourne null
     * @return
     *
     */
    @Deprecated
    static BigDecimal bindBigDecimal(value) {
        String tmp = value != null ? value.toString() : value
        if (tmp) {
            return new BigDecimal(tmp.replace(',', '.'))
        } else {
            return null
        }
    }

    /**
     * Convertir un numéro de téléphone en numéro de téléphone international en fonction du code pays
     * @param value numéro de téléphone saisie
     * @param codePays code du pays
     * @return le numéro de téléphone international (si possible, sinon retourne le numéro saisi sans les espaces)
     */
    static String convertirEnNumeroTelephoneInternational(String value, String codePays) {
        String tmpNumber = StringUtils.deleteWhitespace(value)
        if (StringUtils.isNotBlank(tmpNumber) && StringUtils.isNotBlank(codePays)) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance()
            try {
                PhoneNumber phoneNumber = phoneUtil.parse(tmpNumber, codePays.toUpperCase())
                if (phoneNumber != null && phoneUtil.isValidNumber(phoneNumber)) {
                    tmpNumber = phoneUtil.format(phoneNumber, PhoneNumberFormat.E164)
                }
            } catch (NumberParseException e) {
                LoggerFactory.getLogger(this).error(e.message)
            }
        }
        return tmpNumber
    }

    /**
     * réécriture d'une méthode isNumber, celle sur StringGroovyMethods gère pas les deux séparateurs
     */
    static boolean isNumber(value) {
        String chaine = value != null ? value.toString() : null
        if (chaine) {
            return value ==~ /\d*[\.|\,]*\d*/
        }
        return false
    }


    static List<String> getInformationsManquantes(def domain, Map infosObligatoires, String prefix) {
        List<String> infosManquantes = []
        infosObligatoires.each { k, v ->
            try {
                if (domain?.hasProperty(k)) {
                    if (domain."${k}" == ValeursTemporaires."$v") {
                        infosManquantes.add("${prefix}.${k}.nullable".toString())
                    }
                }
            } catch (Exception exc) {
                LoggerFactory.getLogger(this).error(exc.message)
            }
        }
        return infosManquantes
    }

    /**
     * @return renvoie le premier de la collection s'il existe sinon null
     */
    static Object premierOuPas(Collection<Object> collection) {
        return collection?.size() ? collection.first() : null
    }

    /**
     * @return sans accents ni caractère bizarre (ou avec, mais décomposés - voir tests)
     */
    static String normalise(String avecAccents, boolean gardeEtDecomposeAccents = true) {
        if (!avecAccents) {
            return avecAccents
        } else {
            String normalisee = Normalizer.normalize(avecAccents, Normalizer.Form.NFKD)
            return gardeEtDecomposeAccents ? normalisee.replaceAll("\\p{InCombiningDiacriticalMarks}", "") : normalisee
        }
    }

    /**
     * convertit ou laisse tel quel toute chaine Java; sert à renseigner les attributs d'un objet JSON par ex,
     * comme ceux qu'on envoie à Elasticsearch.
     */
    static String utf8(String texteToutEncoding) {
        return texteToutEncoding ? new String(texteToutEncoding.getBytes(Charset.forName("utf-8"))) : texteToutEncoding
    }

    static boolean isModeDev() {
        boolean devMode = Boolean.FALSE
        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                devMode = Boolean.TRUE
                break;
            case Environment.CUSTOM:
                devMode = "devI1" == Environment.current.name
                break;
        }
        return devMode
    }

    /** cf layout SRHE
     *
     * @param url
     * @return En mode dev retourne l'url
     * Sinon, retourne le path
     */
    static String getBaseUrl(String url) {
        String retour = url;
        if (!Utils.isModeDev()) {
            retour = getContextUrl(retour)
        }
        return retour;
    }

    static String getContextUrl(def url) {
        return new URL((String) url).path;
    }

    static Object nullAvecDebug(Logger logger, Object msg) {
        logger.debug msg
        return null
    }

    /*static List getListeMois() {
        def liste = []
        Locale locale = RequestContextUtils.getLocale(WebUtils.retrieveGrailsWebRequest()?.getCurrentRequest());
        (locale ? DateFormatSymbols.getInstance(locale) : DateFormatSymbols.getInstance()).getMonths().eachWithIndex { String month, int i ->
            if (month) {
                liste << [numero: i + 1, libelle: month.capitalize()]
            }
        }
        return liste.sort({ it.numero })
    }*/

    static BigDecimal arrondiHT(BigDecimal calcul) {
        return calcul?.setScale(2, RoundingMode.HALF_UP)
    }

    static formatteAvecPoint(BigDecimal valeur) {
        return arrondiHT(valeur)?.toString()?.replace(',', '.')
    }

    static String simplifie(String chaineXmlATester) {
        return chaineXmlATester
                .trim()
                .replaceAll("[\n\r]\\s+", " ")
                .replaceAll("[\n\r]", "")
                .replaceAll("\\s*<", "<")
    }

    static boolean isJsonContains(def json, def prop, def value) {
        json.contains("\"${prop}\": \"${value}\"") ||
                json.contains("\"${prop}\" : \"${value}\"") ||
                json.contains("\"${prop}\":\"${value}\"") ||
                json.contains("\"${prop}\": ${value}") ||
                json.contains("\"${prop}\" : ${value}") ||
                json.contains("\"${prop}\":${value}")
    }
}
