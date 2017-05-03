<fieldset>
	<legend>Informations client</legend>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="nomClient">Nom <span class="requis">*</span></label>
		<div class="col-lg-9 col-xs-12">
			<input class="form-control" type="text" id="nomClient" name="nomClient" value="<c:out value="${infos.lastName }"/>" size="20" maxlength="20" />
    		<span class="bg-danger"><c:out value="${form.errors['nomClient'] }"/></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="prenomClient">Prénom </label>
		<div class="col-lg-9 col-xs-12">
			<input class="form-control" type="text" id="prenomClient" name="prenomClient" value="<c:out value="${infos.firstName }"/>" size="20" maxlength="20" />
    		<span class="bg-danger"><c:out value="${form.errors['prenomClient'] }"/></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="adresseClient">Adresse de livraison <span class="requis">*</span></label>
		<div class="col-lg-9 col-xs-12">
			<input class="form-control" type="text" id="adresseClient" name="adresseClient" value="<c:out value="${infos.adress }"/>" size="20" maxlength="20" />
    		<span class="bg-danger"><c:out value="${form.errors['adresseClient'] }"/></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
		<div class="col-lg-9 col-xs-12">
			<input class="form-control" type="text" id="telephoneClient" name="telephoneClient" value="<c:out value="${infos.phone }"/>" size="20" maxlength="20" />
    		<span class="bg-danger"><c:out value="${form.errors['telephoneClient'] }"/></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="emailClient">Adresse email</label>
		<div class="col-lg-9 col-xs-12">
			<input class="form-control" type="email" id="emailClient" name="emailClient" value="<c:out value="${infos.mail }"/>" size="20" maxlength="60" />
    		<span class="bg-danger"><c:out value="${form.errors['emailClient'] }"/></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 col-xs-12 control-label" for="image">Image</label>
		<input type="file" id="image" name="image"/>
		<span class="bg-danger"><c:out value="${form.errors['image'] }"/></span>
	</div>
</fieldset>