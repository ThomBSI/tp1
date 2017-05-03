<c:import url="/inc/header.jsp"/>
	
<!-- Affichage du message de succes de la création du client -->
<div class="bg-success">
	<c:out value="${ form.resultCustomer}"/>
</div>

<!-- Affichage des informations saisies -->
<fieldset>
   	<legend>Résumé du client</legend>
	<dl class="dl-horizontal">
	  <dt>Nom</dt>  <dd><c:out value="${ customer.lastName }"/></dd>
	  <dt>Prénom</dt> <dd><c:out value="${ customer.firstName }"/></dd>
	  <dt>Adresse</dt> <dd><c:out value="${ customer.adress }"/></dd>
	  <dt>Numéro de téléphone</dt> <dd><c:out value="${ customer.phone }"/></dd>
	  <dt>Email</dt> <dd><c:out value="${ customer.mail }"/></dd>
	</dl>
</fieldset>

<c:import url="/inc/footer.jsp"/>