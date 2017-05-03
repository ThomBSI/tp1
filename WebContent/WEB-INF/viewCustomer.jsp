<c:import url="/inc/header.jsp"/>
	
<!-- Affichage du message de succes de la cr�ation du client -->
<div class="bg-success">
	<c:out value="${ form.resultCustomer}"/>
</div>

<!-- Affichage des informations saisies -->
<fieldset>
   	<legend>R�sum� du client</legend>
	<dl class="dl-horizontal">
	  <dt>Nom</dt>  <dd><c:out value="${ customer.lastName }"/></dd>
	  <dt>Pr�nom</dt> <dd><c:out value="${ customer.firstName }"/></dd>
	  <dt>Adresse</dt> <dd><c:out value="${ customer.adress }"/></dd>
	  <dt>Num�ro de t�l�phone</dt> <dd><c:out value="${ customer.phone }"/></dd>
	  <dt>Email</dt> <dd><c:out value="${ customer.mail }"/></dd>
	</dl>
</fieldset>

<c:import url="/inc/footer.jsp"/>