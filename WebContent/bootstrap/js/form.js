$(document).ready(function(){
	/* 1 - Au lancement de la page, on cache le bloc d'éléments du formulaire correspondant aux clients existants */
	$("div.choose-customer").hide();
	/* 2 - Au clic sur un des deux boutons radio "radio-show-customer", on affiche le bloc d'éléments correspondant (nouveau ou ancien client) */
    $('input[name=radio-show-customer]:radio').click(function(){
    	$("div.inport-customer-form").hide();
    	$("div.choose-customer").hide();
        var divClass = $(this).val();
        $("div."+divClass).show();
    });
});

