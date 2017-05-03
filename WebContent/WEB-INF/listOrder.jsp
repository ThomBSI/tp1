<c:import url="/inc/header.jsp"/>
		        
<c:choose>
	<c:when test="${ empty sessionScope.sessionOrder }">
		<div class="bg-info">Aucune commande n'a encore été créée</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Client</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de livraison</th>
					<th>Statut de livraison</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessionScope.sessionOrder }" var="order">
					<tr>
		  				<td><c:out value="${order.value.customer.firstName } ${order.value.customer.lastName }"/></td>
		  				<td><c:out value="${order.value.date }"/></td>
		  				<td><c:out value="${order.value.amount }"/></td>
		  				<td><c:out value="${order.value.payMethod }"/></td>
		  				<td><c:out value="${order.value.payStatus }"/></td>
		  				<td><c:out value="${order.value.deliveryMod }"/></td>
		  				<td><c:out value="${order.value.deliveryStatus }"/></td>
		  				<td>
		  					<a href="<c:url value="suppression">
		  						<c:param name="suppr" value="${order.value.date}"/>
		  						<c:param name="supprType" value="supprOrder"/>
		  						</c:url>">
		  						<img width="25" alt="Supression" src="<c:url value="/inc/images/suppr.png"/>">
							</a>
						</td>
	 				</tr>
	 			</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>
		       	
<c:import url="/inc/footer.jsp"/>