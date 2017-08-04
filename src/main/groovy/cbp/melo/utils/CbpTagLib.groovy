package cbp.melo.utils

import cbp.melo.societe.SocieteDeRattachement

/**
 * nos tags.
 */
class CbpTagLib {

    static CSS_CLASS_LECTURESEULE = "lectureseule"

    /**
     * Idem <g:message> + qui aide IT, SP, etc à traduire les messages.
     * Requiert que le message soit disponible en Français d'abord.
     * @attr code clé du message.
     * @attr fallbackCode clé du message de fallback.
     * @attr error objet erreur de validation clé du message.
     * @attr default n'est plus supporté; sera affiché [cle]version_française lorsque la version locale n'existe pas.
     * @attr args liste de paramètres du message, comme [val1, val2].
     * @attr encodeAs nom du codec à appliquer, c a d HTML, JavaScript, URL, etc.
     */
    def traduction = { attrs, body ->
        // pour aider les IT, SP... à repérer la clé et l'expression à traduire
        attrs.default = "[${attrs.code}]${message(code: attrs.code, locale: Locale.FRENCH)}"
        def result = message(attrs)
        if (attrs.fallbackCode && result == attrs.default) {
            attrs.code = attrs.fallbackCode
            out << message(attrs)
        } else {
            out << result
        }
    }

    /**
     * Idem <g:message> + qui aide IT, SP, etc à traduire les messages.
     * Requiert que le message soit disponible en Français d'abord.
     * @attr code clé du message.
     * @attr error objet erreur de validation clé du message.
     * @attr default n'est plus supporté; sera affiché [cle]version_française lorsque la version locale n'existe pas.
     * @attr args liste de paramètres du message, comme [val1, val2].
     * @attr encodeAs nom du codec à appliquer, c a d HTML, JavaScript, URL, etc.
     * @attr locale locale à utiliser pour la traduction
     */
    def traductionAvecLocale = { attrs, body ->
        // pour aider les IT, SP... à repérer la clé et l'expression à traduire
        attrs.default = "[${attrs.code}]${message(code: attrs.code, locale: attrs.locale)}"
        out << message(attrs)
    }

    /**
     * Champ de saisieAdhesion texte accompagné d'une checkbox pour assigner une valeur par défaut au champ
     * Permet de rendre certains champs d'une adhésion non obligatoires
     *   optionnel : peut ajouter un span de commentaire entre le champ et la checkbox
     * @attr name REQUIRED
     * @attr maxLength
     * @attr value REQUIRED
     * @attr valeurTemporaire REQUIRED
     * @attr labelCommentaire
     */
    def champTexteAvecInfoManquante = { attrs, body ->

        Map<String, String> attrsTextField = ['name'       : attrs.name,
                                              'maxlength'  : attrs.maxLength,
                                              'value'      : attrs.value,
                                              'class'      : attrs['class'],
                                              'pattern'    : attrs['pattern'],
                                              'readonly'   : attrs.readonly,
                                              'placeholder': attrs['placeholder'] ?: '']
        if (attrs.disabled) {
            attrsTextField.'disabled' = attrs.disabled
        }

        if ( // comparaison numerique
        attrs.value == attrs.valeurTemporaire ||
                // comparaison de chaine
                (attrs.value instanceof String && attrs.value?.equalsIgnoreCase(attrs.valeurTemporaire))) {
            attrsTextField.'class' += " ${CSS_CLASS_LECTURESEULE}"
            attrsTextField.putAt('readonly', 'readonly')
        } else {
            attrsTextField.putAt('required', 'required')
        }

        out << textField(attrsTextField)

        if (attrs.labelCommentaire) {
            out << """<span class="commentaire nontaille">${attrs['labelCommentaire']}</span>"""
        }

        out << """
			<input type="checkbox"
				class="nontaille"
				title="Information manquante"
				role="${attrs['role'] ?: 'adhesionMinimale'} infoManquante"
				${attrsTextField.readonly ? 'checked="checked"' : ''}
				onClick="
					var domObject = document.getElementById('${attrs.name}');
					if(this.checked) {
						domObject.setAttribute('readonly','readonly');
						domObject.classList.add('${CSS_CLASS_LECTURESEULE}');
						domObject.value = '${attrs.valeurTemporaire}';
                        domObject.removeAttribute('required');
					} else {
						domObject.removeAttribute('readonly');
						domObject.classList.remove('${CSS_CLASS_LECTURESEULE}');
						domObject.value = '';
						domObject.setAttribute('required','required');
					}"
					tabIndex="-1"
			/>
		"""
    }

    /**
     * champ select accompagné d'une checkbox pour le bloquer et lui assigner une valeur temporaire
     * @attr name REQUIRED
     * @attr from REQUIRED
     * @attr optionKey REQUIRED
     * @attr optionValue
     * @attr value REQUIRED
     * @attr noSelection REQUIRED
     */
    def listeDeroulanteAvecInfoManquante = { attrs, body ->
        def cochee = ''
        def attrsForSelect = attrs.clone()

        //la map d'attrs est détruite apres l'appel du select, d'ou ce clonage
        if (attrs.nullSignifiantManquant && attrs.value == null) {
            cochee = 'checked="checked"'
        } else {
            attrsForSelect.putAt('required', 'required')
        }

        attrsForSelect.putAt('onChange', "if(document.getElementById('${attrs.name}.cb').checked) {this.selectedIndex=0;}")


        out << select(attrsForSelect)
        out << """
			<input type="checkbox"
				id='""" + attrs.name + """.cb'
				class="nontaille"
				title="Information manquante"
				role="${attrs['role'] ?: 'adhesionMinimale'} infoManquante"
				${cochee}
                    onClick="
					var domObject = document.getElementById('""" + attrs.name + """');
					if(this.checked) {
						domObject.selectedIndex = 0;
						domObject.removeAttribute('required');
					}else{
					    domObject.setAttribute('required','required');
					}"
					tabIndex="-1"
			/>
		"""
    }

    /**
     * Champ select proposant les valeurs Oui, Non, null pour forcer la saisieAdhesion d'une valeur Boolean
     * @attr name REQUIRED
     * @attr value REQUIRED
     */
    Closure booleanSelect = { attrs, body ->
        out << select([from       : [[key: true, value: "${traduction(code: 'oui.capitalize')}"], [key: false, value: "${traduction(code: 'non.capitalize')}"]],
                       optionValue: "value", optionKey: "key", noSelection: ['null': ''],
                       name       : attrs.name, value: attrs.value])
    }

    /**
     * Idem <g:message> + qui aide IT, SP, etc à traduire les messages.
     * la clé recherchée sera de la forme "${base}.${code normalisé}"
     * @attr base base de la clé à rechercher
     * @attr code clé du message à normaliser.
     * @attr error objet erreur de validation clé du message.
     * @attr default n'est plus supporté; sera affiché [cle]version_française lorsque la version locale n'existe pas.
     * @attr args liste de paramètres du message, comme [val1, val2].
     * @attr encodeAs nom du codec à appliquer, c a d HTML, JavaScript, URL, etc.
     */
    def traductionNormalisee = { attrs, body ->
        // pour aider les IT, SP... à repérer la clé et l'expression à traduire
        if (attrs.code) {
            if (attrs.base) {
                attrs.code = "${attrs.base}.${normaliser(attrs.code)}"
            }
            attrs.default = "[${attrs.code}]${message(code: attrs.code, locale: Locale.FRENCH)}"
            out << message(attrs)
        }
    }

    def societeDeTravailDuUser = { attrs, body ->

        // affiché uniquement pour l'international
        if (session[SocieteDeRattachement.SOCIETE_EN_COURS]) {
            def code = session[SocieteDeRattachement.SOCIETE_EN_COURS]?.id
            out << link(action: 'index', controller: 'choixSociete', style: 'color: white; vertical-align: middle;') {
                "<div class=\"c-flag c-flag_${code}\" title=\"${message(code: "Societe.${code}")}\"></div>"
            }
        } else if (controllerName != "choixSociete") {
            out << link(action: 'index', controller: 'choixSociete', style: 'color:red') { "(<i class=\"fa fa-warning\"></i>${message(code: "choixSociete.obligatoire")})" }
        }

    }

    private static final String PLAIN_ASCII = "aeu" +  // grave
            "aeu" + // acute
            "c"     // cedilla

    private static final String UNICODE = "\u00E0\u00E8\u00F9" +
            "\u00E1\u00E9\u00FA" +
            "\u00E7"

    /**
     * retourne une chaine supprimée de charactère accentuée et espace.
     *
     * @param string
     * @return
     */
    static def normaliser(String string) {
        // détails d'implémentation : ~40x plus rapide qu'un appel qu'au Normalizer du JDK
        if (string) {
            char[] out = new char[string.length()];
            def n = string.length()
            def j = 0

            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                int ci = c
                int pos = UNICODE.indexOf(ci);
                if (c != ' ') {
                    if (pos > -1) {
                        out[j++] = PLAIN_ASCII.charAt(pos)
                    } else {
                        out[j++] = c
                    }
                }
            }

            return new String(out, 0, j);
        }
    }


}
