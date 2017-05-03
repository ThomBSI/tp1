<c:import url="/inc/header.jsp"/>
   
<!-- Variable de transmission de données pour le formulaire client -->
<c:set var="infos" value="${customer }" scope="request"/>

<!-- Affichage du message d'echec de la création du client -->
<div class="bg-danger">
	<c:out value="${ form.resultCustomer}"/>
</div>

<!-- include du formulaire client -->
<div>
	<form class="form-horizontal" method="post" action="client" enctype="multipart/form-data">
   		<c:import url="/inc/inc_client_form.jsp"/>
   		<div class="form-group">
   			<div class="col-sm-offset-2 col-sm-10">
   				<input class="btn btn-default" type="submit" value="Valider"  />
				<input class="btn btn-default" type="reset" value="Remettre à zéro" />
   			</div>
   		</div>
	</form>
</div>
<c:import url="/inc/footer.jsp"/>