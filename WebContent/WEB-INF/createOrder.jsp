<c:import url="/inc/header.jsp"/>
	
<!-- Affichage du message d'echec de la création du client -->
<!-- première ligne entière : affichage des erreurs -->
<div class="row col-lg-12">
	<div class="bg-danger">
		<c:out value="${ form.resultOrder}"/>
	</div>
</div>

<!-- Formulaires de saisies -->
<div>
    <form class="form-horizontal order-form" method="post" action="<c:url value='/commande'/>">
    
    	<!-- deuxième ligne entière : choix nvx client / ancien client -->
    	<div class="row">
	    	<div class="radio col-lg-12">
	    		<c:if test="${!empty sessionScope.sessionCustomer}">
		    		<label>
		    			<input type="radio" id="radio-show-customer" name="radio-show-customer" value="old-customer"/>
		    			Client existant
		    		</label>
		    		<label>
		    			<input checked="checked" type="radio" id="radio-show-customer" name="radio-show-customer" value="inport-customer-form"/>
		    			Nouveau client
		    		</label>
	    		</c:if>
	    	</div>
    	</div>
    	
    	<!-- troisième ligne entière : choix ancien client -->
    	<div class="row">
	    	<div class="choose-customer col-lg-12">
	    		<c:if test="${!empty sessionScope.sessionCustomer}">
		    		<select class="form-control" name="existing-customer">
		    			<c:forEach items="${sessionScope.sessionCustomer }" var="customer">
		    				<option value="${customer.value.lastName }"><c:out value="${customer.value.lastName } ${customer.value.firstName }" /></option>
		    				<c:set var="infos" value="${customer }"/>
		    			</c:forEach>
		    		</select>
	    		</c:if>
	    	</div>
    	</div>
    	
    	<!-- quatrième ligne entière : les 2 formulaires -->
    	<div class="row">
    		<!-- formulaire commandes -->
	    	<fieldset class="col-lg-6">
		     	<legend>Informations commande</legend>
		     	<div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="dateCommande">Date <span class="requis">*</span></label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="dateCommande" name="dateCommande" value="<c:out value="${order.date }"/>" size="20" maxlength="20" disabled/>
		             	<span class="bg-danger"><c:out value="${form.errors['dateCommande'] }"/></span>
		     		</div>
		     	</div>
		        	<div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="montantCommande">Montant <span class="requis">*</span></label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="montantCommande" name="montantCommande" value="<c:out value="${order.amount }"/>" size="20" maxlength="20"/>
		            		<span class="bg-danger"><c:out value="${form.errors['montantCommande'] }"/></span>
		     		</div>
		     	</div>
		     	<div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${order.payMethod }"/>" size="20" maxlength="20"/>
		            		<span class="bg-danger"><c:out value="${form.errors['modePaiementCommande'] }"/></span>
		     		</div>
		     	</div>
		     	<div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="statutPaiementCommande">Statut du paiement</label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${order.payStatus }"/>" size="20" maxlength="20"/>
		            		<span class="bg-danger"><c:out value="${form.errors['statutPaiementCommande'] }"/></span>
		     		</div>
		     	</div>
		     	<div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${order.deliveryMod }"/>" size="20" maxlength="20"/>
		            		<span class="bg-danger"><c:out value="${form.errors['modeLivraisonCommande'] }"/></span>
		     		</div>
		     	</div>
		            <div class="form-group">
		     		<label class="col-lg-3 col-xs-12 control-label" for="statutLivraisonCommande">Statut de la livraison</label>
		     		<div class="col-lg-9 col-xs-12">
		     			<input class="form-control" type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${order.deliveryStatus }"/>" size="20" maxlength="20"/>
		            		<span class="bg-danger"><c:out value="${form.errors['statutLivraisonCommande'] }"/></span>
		     		</div>
		     	</div>
	        </fieldset>
	        <!-- formulaire clients -->
	        <div class="inport-customer-form col-lg-6">
	        	<!-- Variable de transmission de données pour le formulaire client -->
				<c:set var="infos" value="${order.customer }" scope="request"/>
	    		<c:import url="/inc/inc_client_form.jsp"/>
	    	</div>
        </div>
        
        <!-- dernière ligne : bttns de validation -->
        <div class="row">
	        <div class="form-group">
	        	<div class="col-lg-offset-3">
	        		<input class="btn btn-default" type="submit" value="Valider"  />
	        		<input class="btn btn-default" type="reset" value="Remettre à zéro" />
	        	</div>
	        </div>
        </div>
    </form>
</div>

<c:import url="/inc/footer.jsp"/>
