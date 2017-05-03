<c:import url="/inc/header.jsp"/>

<!-- Affichage du message de succes de la création de la commande -->
<div class="bg-success">
	<c:out value="${ form.resultOrder}"/>
</div>

<!-- Affichage des informations saisies -->
<fieldset >
   	<legend>Résumé du client</legend>
	<dl class="dl-horizontal">
	  <dt>Nom</dt>  <dd><c:out value="${ order.customer.lastName }"/></dd>
	  <dt>Prénom</dt> <dd><c:out value="${ order.customer.firstName }"/></dd>
	  <dt>Adresse</dt> <dd><c:out value="${ order.customer.adress }"/></dd>
	  <dt>Numéro de téléphone</dt> <dd><c:out value="${ order.customer.phone }"/></dd>
	  <dt>Email</dt> <dd><c:out value="${ order.customer.mail }"/></dd>
	</dl>
</fieldset>
<fieldset>
   	<legend>Résumé de la commande</legend>
	<dl class="dl-horizontal">
	  <dt>Date</dt> <dd><c:out value="${ order.date }"/></dd>
	  <dt>Montant</dt> <dd><c:out value="${ order.amount }"/></dd>
	  <dt>Mode de paiement</dt> <dd><c:out value="${ order.payMethod }"/></dd>
	  <dt>Statut du paiement</dt> <dd><c:out value="${ order.payStatus }"/></dd>
	  <dt>Mode de livraison</dt> <dd><c:out value="${ order.deliveryMod }"/></dd>
	  <dt>Statut de la livraison</dt> <dd><c:out value="${ order.deliveryStatus }"/></dd>
	</dl>
</fieldset>

<c:import url="/inc/footer.jsp"/>