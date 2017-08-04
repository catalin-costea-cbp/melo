<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Disponibilité melo-SERVICES</title>
<style>
.o_k_pournosamisdelaprod {
	color: green;
	text-align: center;
}

.k_o_pournosamisdelaprod {
	color: red;
	font-weight: bold;
	text-align: center;
}

table thead tr th {
	background-color: lime;
}

table {
	margin: 0px;
	padding: 0px;
	border-collapse: collapse;
}

td {
	border-bottom: 1px solid black;
	padding-right: 3px;
	padding-left: 3px;
	padding-bottom: 3px;
	padding-top: 3px;
	margin: 0px;
}
</style>
</head>
<body>
	<h1>Disponibilité de l'application ** melo-SERVICES **</h1>
	<h4>${jalon }</h4>
	<h2>Vérifications (environnement = ${environnement})</h2>
	<center>
		<table>
			<thead>
				<tr>
					<th width="70%">Vérification</th>
					<th width="30%">Etat</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${verifications}" var="verification" status="i">
					<tr id="V${i+1}">
						<td>V${i+1}&nbsp;:&nbsp;${verification.libelle }</td>
                        <td class="${verification.etat ? 'o_k_pournosamisdelaprod' : 'k_o_pournosamisdelaprod' }">${verification.etat ? 'OK' : 'KO' }</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</center>
<a
        href="http://srv0052:7080/source/search?q=getVerificationsDisponibilite&project=hors-emprunteur&defs=&refs=&path=fare+services&hist=">
    documentation via G.r.o.k (projet hors-emprunteur, search getVerificationsDisponibilite)
        </a>
</body>
</html>
