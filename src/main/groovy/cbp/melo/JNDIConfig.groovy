package cbp.melo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by COSTEA on 02/08/2017.
 */
@Component
@ConfigurationProperties("meloJNDIConfig")
public class JNDIConfig {
    String auth
    String type
    String factory
    String urlServicesZurix
    String urlServicesPieces
    String urlServicesEdition
    String urlServicesDistributeur
    String urlServicesOrganismePreteur
    String urlServicesMandatSepa
    String urlServicesSocle
    String urlServicesMoni
    String urlServicesProduit
    String urlServicesInocs
    String urlServicesFare
    String urlServicesClavisLogin
    String clavisCrowdAppName
    String clavisCrowdAppPassword
    String urlServicesIndexInocs
    String repertoireDocumentsStatiques

    String getAuth() {
        return auth
    }

    void setAuth(String auth) {
        this.auth = auth
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getFactory() {
        return factory
    }

    void setFactory(String factory) {
        this.factory = factory
    }

    String getUrlServicesZurix() {
        return urlServicesZurix
    }

    void setUrlServicesZurix(String urlServicesZurix) {
        this.urlServicesZurix = urlServicesZurix
    }

    String getUrlServicesPieces() {
        return urlServicesPieces
    }

    void setUrlServicesPieces(String urlServicesPieces) {
        this.urlServicesPieces = urlServicesPieces
    }

    String getUrlServicesEdition() {
        return urlServicesEdition
    }

    void setUrlServicesEdition(String urlServicesEdition) {
        this.urlServicesEdition = urlServicesEdition
    }

    String getUrlServicesDistributeur() {
        return urlServicesDistributeur
    }

    void setUrlServicesDistributeur(String urlServicesDistributeur) {
        this.urlServicesDistributeur = urlServicesDistributeur
    }

    String getUrlServicesOrganismePreteur() {
        return urlServicesOrganismePreteur
    }

    void setUrlServicesOrganismePreteur(String urlServicesOrganismePreteur) {
        this.urlServicesOrganismePreteur = urlServicesOrganismePreteur
    }

    String getUrlServicesMandatSepa() {
        return urlServicesMandatSepa
    }

    void setUrlServicesMandatSepa(String urlServicesMandatSepa) {
        this.urlServicesMandatSepa = urlServicesMandatSepa
    }

    String getUrlServicesSocle() {
        return urlServicesSocle
    }

    void setUrlServicesSocle(String urlServicesSocle) {
        this.urlServicesSocle = urlServicesSocle
    }

    String getUrlServicesMoni() {
        return urlServicesMoni
    }

    void setUrlServicesMoni(String urlServicesMoni) {
        this.urlServicesMoni = urlServicesMoni
    }

    String getUrlServicesProduit() {
        return urlServicesProduit
    }

    void setUrlServicesProduit(String urlServicesProduit) {
        this.urlServicesProduit = urlServicesProduit
    }

    String getUrlServicesInocs() {
        return urlServicesInocs
    }

    void setUrlServicesInocs(String urlServicesInocs) {
        this.urlServicesInocs = urlServicesInocs
    }

    String getUrlServicesFare() {
        return urlServicesFare
    }

    void setUrlServicesFare(String urlServicesFare) {
        this.urlServicesFare = urlServicesFare
    }

    String getUrlServicesClavisLogin() {
        return urlServicesClavisLogin
    }

    void setUrlServicesClavisLogin(String urlServicesClavisLogin) {
        this.urlServicesClavisLogin = urlServicesClavisLogin
    }

    String getClavisCrowdAppName() {
        return clavisCrowdAppName
    }

    void setClavisCrowdAppName(String clavisCrowdAppName) {
        this.clavisCrowdAppName = clavisCrowdAppName
    }

    String getClavisCrowdAppPassword() {
        return clavisCrowdAppPassword
    }

    void setClavisCrowdAppPassword(String clavisCrowdAppPassword) {
        this.clavisCrowdAppPassword = clavisCrowdAppPassword
    }

    String getUrlServicesIndexInocs() {
        return urlServicesIndexInocs
    }

    void setUrlServicesIndexInocs(String urlServicesIndexInocs) {
        this.urlServicesIndexInocs = urlServicesIndexInocs
    }

    String getRepertoireDocumentsStatiques() {
        return repertoireDocumentsStatiques
    }

    void setRepertoireDocumentsStatiques(String repertoireDocumentsStatiques) {
        this.repertoireDocumentsStatiques = repertoireDocumentsStatiques
    }
}
