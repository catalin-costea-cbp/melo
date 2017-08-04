<%@ page import="cbp.melo.Calendrier" %>
<html>
<head>
    <meta name="layout" content="main" />
    <asset:javascript src="application.js"/>
    <asset:stylesheet src="application.css"/>
    <title>Simulateur création d'adhésion internationale</title>
</head>

<body>
<style>
input, select {
    width: 400px
}

input.sel {
    background-color: green;
    padding: 5px;
    color: white;
    border: 1px solid darkgreen;
}

input[type="submit"] {
    width: 200px;
    background-color: #0275d8;
    padding: 5px;
    color: white;
    margin: 10px 300px;
    border: 1px solid #015dab;
}
</style>

<h3>Formulaire demo PreSale NRP</h3>
<g:form controller="adhesion" action="creationAdhesion" method="post">
    <div id="boutons_produits">
        Pays :
        <input type="button" value="IPU" style="width: 60px;" id="b-IPU" onclick="changerPour('IPU')" />
        <input type="button" value="IPT" style="width: 60px;" id="b-IPT" onclick="changerPour('IPT')" />
        <input type="button" value="AD" style="width: 60px;" id="b-AD" onclick="changerPour('AD')" />
        <input type="button" value="AP" style="width: 60px;" id="b-AP" onclick="changerPour('AP')" />
        <input type="button" value="GE" style="width: 60px;" id="b-GE" onclick="changerPour('GE')" />
        <g:submitButton name="Submit" id="Submit" />
    </div>
    <fieldset>
        <table id="formulaire_adhesion">
            <tr><td></td><td><h4>Assuré 1</h4></td><td></td></tr>
            <tr><td>X-Partenaire</td><td><g:textField name="X-Partenaire" value="${params.'X-Partenaire'}" placeholder="SANT, CBPG..." /></td></tr>
            <tr><td>product.id</td><td><g:textField name="product.id" value="" /></td><td>
                <span class="aide IPT IPU">IPT20 &rarr; employeeType.id = 2 / IPU20 &rarr; employeeType.id = 3</span>
            </td></tr>
            <tr><td>agentId</td><td><g:textField name="agentId" value="" /></td><td></td></tr>
            <tr><td>seller.distributor.identifier</td><td><g:textField name="seller.distributor.identifier" value="" /></td><td></td></tr>
            <tr><td>seller.entity.identifier</td><td><g:textField name="seller.entity.identifier" value="1" /></td><td></td></tr>
            <tr><td>seller.externalContractId</td><td><g:textField name="seller.externalContractId" value="" /></td><td></td></tr>
            <tr><td>campaignId</td><td><g:textField name="campaignId" value="" /></td><td>
                <span class="aide IPT IPU AD AP">16L : b33S1PG/dmZ9jkgjwrob0KRZFGdRR29StfxZ57ooi/A= <br> 16M :
                MzgXBQmTamSO6i0H4crnxvd7E9pEsMkhAxZB8nokoVs= <br> 16P : +S2ryrwMMJJIvCoqVeMxlZ9te4I6Odlm+hgyx1jUuPA= <br> 16R : Wf2yCCYe50tG6GzxI9gGB5ueVEPtWVAfF4pud3fXCis= <br> 16S : Vk57ejVw4anGPhP7bUVUdabpCKaHD6O1PjupjmHpUww= <br> 16T : Svcnw840fgruCAK9cgjBnJQz6djYw4CGWx3LK3OpDh8=
                </span></td></tr>
            <tr><td>persons.0.externalReference</td><td><g:textField name="persons.0.externalReference" value="" /></td><td></td></tr>
            <tr><td>persons.0.title.id</td><td><g:textField name="persons.0.title.id" value="" /></td><td></td></tr>
            <tr><td>persons.0.firstName</td><td><g:textField name="persons.0.firstName" value="" /></td><td></td></tr>
            <tr><td>persons.0.lastName</td><td><g:textField name="persons.0.lastName" value="" /></td><td></td></tr>
            <tr><td>persons.0.gender.id</td><td><g:textField name="persons.0.gender.id" value="" /></td><td></td></tr>
            <tr><td>persons.0.nationalId</td><td><g:textField name="persons.0.nationalId" value="" /></td><td></td></tr>
            <tr><td>persons.0.nationality.id</td><td><g:textField name="persons.0.nationality.id" value="" /></td><td></td></tr>
            <tr><td>persons.0.birthDate</td><td><g:textField name="persons.0.birthDate" value="" /></td><td></td></tr>
            <tr><td>persons.0.birthCity</td><td><g:textField name="persons.0.birthCity" value="" /></td><td></td></tr>
            <tr><td>persons.0.birthProvince</td><td><g:textField name="persons.0.birthProvince" value="" /></td><td></td></tr>
            <tr><td>persons.0.fixedLinePhone</td><td><g:textField name="persons.0.fixedLinePhone" value="" /></td><td></td></tr>
            <tr><td>persons.0.email</td><td><g:textField name="persons.0.email" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.line1FloorApartment</td><td><g:textField name="persons.0.address.line1FloorApartment" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.line2Building</td><td><g:textField name="persons.0.address.line2Building" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.line3StreetNameAndNumber</td><td><g:textField name="persons.0.address.line3StreetNameAndNumber" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.line4Province</td><td><g:textField name="persons.0.address.line4Province" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.zipcode</td><td><g:textField name="persons.0.address.zipcode" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.city</td><td><g:textField name="persons.0.address.city" value="" /></td><td></td></tr>
            <tr><td>persons.0.address.country.identifier</td><td><g:textField name="persons.0.address.country.identifier" value="" /></td><td></td></tr>
            <tr><td>persons.0.mobilePhone</td><td><g:textField name="persons.0.mobilePhone" value="" placeholder="NNNNN... o +NN NNNNN..." /></td><td></td>
            </tr>
            <tr><td>persons.0.preferredCommunicationMode</td><td><g:select name="persons.0.preferredCommunicationMode" from="${['email', 'postal']}" /></td><td></td></tr>
            <tr><td>persons.0.employeeType.id</td><td><g:textField name="persons.0.employeeType.id" value="" /></td><td>2 for self-employed, 3 for salaried employee</td></tr>
            %{--<tr><td></td><td><h4>Assuré 2</h4></td><td></td></tr>--}%
            <tr><td>persons.1.title.id</td><td><g:textField name="persons.1.title.id" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.firstName</td><td><g:textField name="persons.1.firstName" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.lastName</td><td><g:textField name="persons.1.lastName" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.nationalId</td><td><g:textField name="persons.1.nationalId" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.gender.id</td><td><g:textField name="persons.1.gender.id" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.birthDate</td><td><g:textField name="persons.1.birthDate" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.birthCity</td><td><g:textField name="persons.1.birthCity" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.birthProvince</td><td><g:textField name="persons.1.birthProvince" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td>persons.1.nationality.id</td><td><g:textField name="persons.1.nationality.id" value="" disabled="disabled" /></td><td></td></tr>
            <tr><td></td><td><h4>Adhésion</h4></td><td></td></tr>
            <tr><td>signatureDate</td><td><g:textField name="signatureDate" value="" /></td><td></td></tr>
            <tr><td>inceptionDate</td><td><g:textField name="inceptionDate" value="" /></td><td></td></tr>
            <tr><td>subscriptionDocument</td><td><g:textField name="subscriptionDocument" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.paymentMethod</td><td><g:textField name="paymentInformation.paymentMethod" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.periodicity</td><td><g:textField name="paymentInformation.periodicity" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.currency</td><td><g:textField name="paymentInformation.currency" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.account.iban</td><td><g:textField name="paymentInformation.account.iban" value="" /></td><td><span class="aide Italie">IT60X0542811101000000123456</span>
            </td></tr>
            <tr><td>paymentInformationSantander.account.encryptedIban</td><td><g:textField name="paymentInformationSantander.account.encryptedIban" value="" /></td><td></td></tr>
            <tr><td>paymentInformationSantander.account.bankName</td><td><g:textField name="paymentInformationSantander.account.bankName" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.account.owner</td><td><g:textField name="paymentInformation.account.owner" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.requestedCollectionDay</td><td><g:textField name="paymentInformation.requestedCollectionDay" value="" /></td><td></td></tr>
            <tr><td>paymentInformation.amountForPeriodicity</td><td><g:textField name="paymentInformation.amountForPeriodicity" value="" /></td><td></td></tr>
            <tr><td>language</td><td><g:textField name="language" value="" /></td><td></td></tr>
            <tr><td>subscriptionStep</td><td><g:select name="subscriptionStep" from="${['sale', 'presale']}" /></td><td></td></tr>
            <tr><td>contractInformationSantander.retention</td><td><g:checkBox name="contractInformationSantander.retention" /></td><td></td></tr>
            <tr><td>contractInformationSantander.updated</td><td><g:checkBox name="contractInformationSantander.updated" /></td><td></td></tr>
            <tr id="skipDuplicate"><td>skipDuplicate</td><td><g:checkBox name="skipDuplicate" value="true" /></td><td></td></tr>
        </table>
    </fieldset>
    <g:submitButton name="Submit" id="Submit" />
</g:form>

<g:hiddenField id="isP1" name="isP1" value="${System.getProperty("env")?.toString()?.toLowerCase()?.startsWith("p")}" />
<div style="display:none">
    <!-- pas utilisé pour le moment -->
    <h3>Enable or disable data which are posted to the server (to test single head product for example)</h3>
    <button id="disablePerson" type="button">Disable persons.1</button>
    <button id="enablePerson" type="button">Enable persons.1</button>
    <button id="disableAddress" type="button">Disable address</button>
    <button id="enableAddress" type="button">Enable address</button>
</div>

<script type='text/javascript'>
    var today = "${Calendrier.auj.format('dd/MM/yyyy')}"

    var valeursFormulaire = {
        "Commun": {
            "agentId": "10",
            "seller.externalContractId": "F545QS5D",
            "campaignId": "16MLNW",
            "persons.0.externalReference": "F5165321",
            "persons.0.title.id": "2",
            "persons.0.gender.id": "1",
            "persons.0.birthDate": "11/12/1968",
            "persons.0.fixedLinePhone": "0102030405",
            "persons.0.mobilePhone": "0606060606",
            "persons.0.employeeType.id": "2",
            "persons.1.title.id": "7",
            "persons.1.firstName": "Isabel",
            "persons.1.lastName": "Ravanelli",
            "persons.1.nationalId": "DF2F52F652F",
            "persons.1.gender.id": "2",
            "persons.1.birthDate": "01/05/1968",
            "persons.1.birthCity": "",
            "persons.1.birthProvince": "",
            "persons.1.nationality.id": "86",
            "paymentInformation.paymentMethod": "1",
            "paymentInformation.periodicity": "3",
            "paymentInformation.currency": "EUR",
            "subscriptionDocument": "MANAGED_BY_ENTITY",
            "paymentInformation.requestedCollectionDay": "15",
            "paymentInformation.amountForPeriodicity": "40"
        },
        "IPT": {
            "product.id": 'IPT20',
            "seller.distributor.identifier": 3191,
            "seller.entity.identifier": 1,
            "persons.0.firstName": "FabriZipt",
            "persons.0.lastName": "Ravanelli",
            "persons.0.nationalId": "DF2F52F673F",
            "persons.0.nationality.id": "86",
            "persons.0.address.line3StreetNameAndNumber": "Corso Gaetano Scirea, 28",
            "persons.0.address.line4Province": "TO",
            "persons.0.address.zipcode": "10151",
            "persons.0.address.city": "Torino",
            "persons.0.address.country.identifier": "IT",
            "inceptionDate": today,
            "signatureDate": today,
            "paymentInformation.account.iban": "",
            "paymentInformationSantander.account.encryptedIban": "MzgXBQmTamSO6i0H4crnxvd7E9pEsMkhAxZB8nokoVs=",
            "paymentInformationSantander.account.bankName": "Unicredit",
            "paymentInformation.account.owner": "Ravanelli",
            "language": "it",
            "persons.0.birthCity": "Torino",
            "persons.0.birthProvince": "Piemont",
            "persons.0.employeeType.id": 2,
            "paymentInformation.amountForPeriodicity": "40"
        },
        "IPU": {
            "product.id": 'IPT20',
            "seller.distributor.identifier": 3191,
            "seller.entity.identifier": 1,
            "persons.0.firstName": "FabriZipu",
            "persons.0.lastName": "Ravanelli",
            "persons.0.nationalId": "DF2F52F673F",
            "persons.0.nationality.id": "86",
            "persons.0.address.line3StreetNameAndNumber": "Corso Gaetano Scirea, 28",
            "persons.0.address.line4Province": "TO",
            "persons.0.address.zipcode": "10151",
            "persons.0.address.city": "Torino",
            "persons.0.address.country.identifier": "IT",
            "inceptionDate": today,
            "signatureDate": today,
            "paymentInformation.account.iban": "",
            "paymentInformationSantander.account.encryptedIban": "MzgXBQmTamSO6i0H4crnxvd7E9pEsMkhAxZB8nokoVs=",
            "paymentInformationSantander.account.bankName": "Unicredit",
            "paymentInformation.account.owner": "Ravanelli",
            "language": "it",
            "persons.0.birthCity": "Torino",
            "persons.0.birthProvince": "Piemont",
            "persons.0.employeeType.id": 2,
            "paymentInformation.amountForPeriodicity": "40"
        },
        "AD": {
            "product.id": 'AD121',
            "seller.distributor.identifier": 3191,
            "seller.entity.identifier": 1,
            "persons.0.firstName": "FabriZad",
            "persons.0.lastName": "Ravanelli",
            "persons.0.nationalId": "DF2F52F673F",
            "persons.0.nationality.id": "86",
            "persons.0.address.line3StreetNameAndNumber": "Corso Gaetano Scirea, 28",
            "persons.0.address.line4Province": "TO",
            "persons.0.address.zipcode": "10151",
            "persons.0.address.city": "Torino",
            "persons.0.address.country.identifier": "IT",
            "inceptionDate": today,
            "signatureDate": today,
            "paymentInformation.account.iban": "",
            "paymentInformationSantander.account.encryptedIban": "MzgXBQmTamSO6i0H4crnxvd7E9pEsMkhAxZB8nokoVs=",
            "paymentInformationSantander.account.bankName": "Unicredit",
            "paymentInformation.account.owner": "Ravanelli",
            "language": "it",
            "persons.0.birthCity": "Torino",
            "persons.0.birthProvince": "Piemont",
            "persons.0.employeeType.id": 2,
            "paymentInformation.amountForPeriodicity": "9.5"
        },
        "AP": {
            "product.id": 'AP121',
            "seller.distributor.identifier": 3191,
            "seller.entity.identifier": 1,
            "persons.0.firstName": "FabriZap",
            "persons.0.lastName": "Ravanelli",
            "persons.0.nationalId": "DF2F52F673F",
            "persons.0.nationality.id": "86",
            "persons.0.address.line3StreetNameAndNumber": "Corso Gaetano Scirea, 28",
            "persons.0.address.line4Province": "TO",
            "persons.0.address.zipcode": "10151",
            "persons.0.address.city": "Torino",
            "persons.0.address.country.identifier": "IT",
            "inceptionDate": today,
            "signatureDate": today,
            "paymentInformation.account.iban": "",
            "paymentInformationSantander.account.encryptedIban": "MzgXBQmTamSO6i0H4crnxvd7E9pEsMkhAxZB8nokoVs=",
            "paymentInformationSantander.account.bankName": "Unicredit",
            "paymentInformation.account.owner": "Ravanelli",
            "language": "it",
            "persons.0.birthCity": "Torino",
            "persons.0.birthProvince": "Piemont",
            "persons.0.employeeType.id": 2,
            "paymentInformation.amountForPeriodicity": "23.25"
        },
        "GE": {
            "product.id": 'GE120',
            "seller.distributor.identifier": 99944,
            "seller.entity.identifier": 10000,
            "persons.0.firstName": "Lucas",
            "persons.0.lastName": "Haas",
            "persons.0.nationalId": "",
            "persons.0.nationality.id": "5",
            "persons.0.address.line3StreetNameAndNumber": "Wacholderweg 24",
            "persons.0.address.line4Province": "",
            "persons.0.address.zipcode": "26133",
            "persons.0.address.city": "Oldenburg",
            "persons.0.address.country.identifier": "DE",
            "inceptionDate": today,
            "signatureDate": today,
            "paymentInformation.account.iban": "DE89370400440532013000",
            "paymentInformationSantander.account.encryptedIban": "",
            "paymentInformationSantander.account.bankName": "Unicredit",
            "paymentInformation.account.owner": "Haas",
            "language": "de",
            "persons.0.birthCity": "",
            "persons.0.birthProvince": "",
            "persons.0.employeeType.id": 3,
            "paymentInformation.amountForPeriodicity": "40"
        }
    };

    function changerPour(produit) {
        if (produit != "Commun") {
            changerPour("Commun");
        }
        var dico = valeursFormulaire[produit];
        for (var key in dico) {
            $("input[name^='" + key + "']").each(function () {
                this.value = dico[key];
            })
        }
        $(".aide").each(function () {
            if (this.className.indexOf(produit) != -1) {
                this.style.display = 'block';
            } else {
                this.style.display = 'none';
            }
        });
        $("[id^=b-]").each(function () {
            $(this).removeClass("sel");
        });
        $("#b-" + produit).addClass("sel");

    }

    function getUrlVars() {
        var vars = {};
        window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    }

    $(document).ready(function () {

        if (${System.getProperty("grails.env") in ["devI1", "devR1", "development"]} || getUrlVars()["env"] == "dev"
        )
        {
            var email = 'dsi-etudes-dev-international-inocs@cbp-prevoyance.net';
            $("input[name^='persons.0.email']").each(function () {
                this.value = email;
            });
            changerPour("AD");
        }
        else
        {
            $('#boutons_produits').remove();
            $("input[name^='skipDuplicate']").remove();
            $("#skipDuplicate").remove();
        }

        $('#disablePerson').on('click', function () {
            $("input[name^='persons\.1']").each(function () {
                $(this).attr('disabled', 'disabled');
                $(this).parent().parent().hide();
            })
        });

        $('#enablePerson').on('click', function () {
            $("input[name^='persons\.1']").each(function () {
                $(this).removeAttr('disabled');
                $(this).parent().parent().show();
            })
        });

        $('#disableAddress').on('click', function () {
            $("input[name*=address]").each(function () {
                $(this).attr('disabled', 'disabled');
                $(this).hide();
            })
        });

        $('#enableAddress').on('click', function () {
            $("input[name*=address").each(function () {
                $(this).removeAttr('disabled');
                $(this).show();
            });
        });

        $("#disablePerson").click();
    });
</script>

</body>
</html>