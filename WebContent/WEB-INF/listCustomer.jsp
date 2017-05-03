<c:import url="/inc/header.jsp"/>
		        
<c:choose>
	<c:when test="${ empty sessionScope.sessionCustomer }">
		<div class="bg-info">Aucun client n'a encore été créé</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Nom</th>
					<th>Prénom</th>
					<th>Adresse de livraison</th>
					<th>Téléphone</th>
					<th>Adresse mail</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessionScope.sessionCustomer }" var="customer">
				<tr>
	  				<td><c:out value="${customer.value.lastName }"/></td>
	  				<td><c:out value="${customer.value.firstName }"/></td>
	  				<td><c:out value="${customer.value.adress }"/></td>
	  				<td><c:out value="${customer.value.phone }"/></td>
	  				<td><c:out value="${customer.value.mail }"/></td>
	  				<td>
	  					<a href="<c:url value="suppression">
	  						<c:param name="suppr" value="${customer.value.lastName}"/>
	  						<c:param name="supprType" value="supprCustomer"/>
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