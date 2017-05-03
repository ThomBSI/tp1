package com.thom.tp1.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.thom.tp1.beans.CustomerBean;
import com.thom.tp1.beans.OrderBean;

public class FormOrderBean {

    public static final String  FIELD_DATE                              = "dateCommande";
    public static final String  DATE_PATTERN                            = "dd/MM/yyyy HH:mm:ss";
    public static final String  FIELD_AMOUNT                            = "montantCommande";
    public static final String  FIELD_PAY_METHOD                        = "modePaiementCommande";
    public static final String  FIELD_PAY_STATUS                        = "statutPaiementCommande";
    public static final String  FIELD_DELIVERY_MOD                      = "modeLivraisonCommande";
    public static final String  FIELD_DELIVERY_STATUS                   = "statutLivraisonCommande";

    public static final String  FIELD_RADIO_OLD_CUST                    = "radio-show-customer";
    public static final String  VALUE_OLD_CUST                          = "old-customer";
    public static final String  FIELD_SELECT_OLD_CUST                   = "existing-customer";
    public static final String  SESSION_CUST                            = "sessionCustomer";

    private String              resultOrder;
    private Map<String, String> errors                                  = new HashMap<String, String>();
    private boolean             okOrder                                 = false;

    private static final String message_exception_amount_negative       = "Le montant doit être un nombre positif.";
    private static final String message_exception_amount_format         = "Le montant doit être un nombre.";
    private static final String message_exception_amount_empty          = "Merci d'entrer un montant.";
    private static final String message_exception_deliveryMod_length    = "Le mode de livraison doit comporter au moins 2 caractères";
    private static final String message_exception_deliveryStatus_length = "Le status de livraison doit comporter au moins 2 caractères";
    private static final String message_exception_payMethod_length      = "Le mode de paiement doit comporter au moins 2 caractères";
    private static final String message_exception_payStatus_length      = "Le status de paiement doit comporter au moins 2 caractères";
    private static final String message_create_success                  = "La commande a ete créée avec succès.";
    private static final String message_create_fail                     = "La création de la commande a echouée.";

    public OrderBean initOrder( HttpServletRequest request ) {

        CustomerBean customer;

        if ( VALUE_OLD_CUST.equals( request.getParameter( FIELD_RADIO_OLD_CUST ) ) ) {
            String lastName = request.getParameter( FIELD_SELECT_OLD_CUST );
            HttpSession session = request.getSession();
            Map<String, Object> sessionMapCustomer = (HashMap<String, Object>) session
                    .getAttribute( SESSION_CUST );
            customer = (CustomerBean) sessionMapCustomer.get( lastName );

        } else {
            // on cr�e un objet customer � partir de son objet m�tier, ce qui
            // permet de setter tous les champs et de faire toutes les
            // v�rifications
            FormCustomerBean formCustomer = new FormCustomerBean();
            customer = formCustomer.initCustomer( request );

            // on r�cup�res les �ventuelles erreurs soulev�es dans la cr�ation
            // du customer
            errors = formCustomer.getErrors();
        }

        // r�cup�ration de la m�thode de paiement
        String payMethod = getFieldValue( request, FIELD_PAY_METHOD );
        // r�cup�ration du status du paiement
        String payStatus = getFieldValue( request, FIELD_PAY_STATUS );
        // r�cup�ration du mode de livraison
        String deliveryMod = getFieldValue( request, FIELD_DELIVERY_MOD );
        // r�cup�ration du status de livraison
        String deliveryStatus = getFieldValue( request, FIELD_DELIVERY_STATUS );
        // r�cup�ration du montant sous forme de String
        String amount = getFieldValue( request, FIELD_AMOUNT );

        // Cr�ation de la date du jour sous le bon format
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern( DATE_PATTERN );
        String date = dt.toString( formatter );

        // Instenciation d'un objet Order
        OrderBean order = new OrderBean();

        // Ajout de l'obet customer � l'order
        order.setCustomer( customer );

        // Ajout de la date � l'order
        order.setDate( date );
        // convertion du montant String en double et ajout � l'order
        double amountNum = -1;
        try {
            amountNum = checkAmount( amount );
        } catch ( Exception e ) {
            setErrors( FIELD_AMOUNT, e.getMessage() );
        }
        order.setAmount( amountNum );

        // V�rification du status de livraison et ajout � l'order
        try {
            checkDeliveryStatus( deliveryStatus );
        } catch ( Exception e ) {
            setErrors( FIELD_DELIVERY_STATUS, e.getMessage() );
        }
        order.setDeliveryStatus( deliveryStatus );

        // V�rification du mode de livraison et ajout � l'order
        try {
            checkDeliveryMod( deliveryMod );
        } catch ( Exception e ) {
            setErrors( FIELD_DELIVERY_MOD, e.getMessage() );
        }
        order.setDeliveryMod( deliveryMod );

        // V�rification du mode de paiement et ajout � l'order
        try {
            checkPayMethod( payMethod );
        } catch ( Exception e ) {
            setErrors( FIELD_PAY_METHOD, e.getMessage() );
        }
        order.setPayMethod( payMethod );

        // V�rification du status de paiement et ajout � l'order
        try {
            checkPayStatus( payStatus );
        } catch ( Exception e ) {
            setErrors( FIELD_PAY_STATUS, e.getMessage() );
        }
        order.setPayStatus( payStatus );

        if ( errors.isEmpty() ) {// si aucune erreur n'a �t� soulev�e, on
                                 // set le message de success
            setResultOrder( true );
        } else { // s'il y a eu des erreur, on set le message d'echec
            setResultOrder( false );
        }

        return order;
    }

    /**
     * R�cup�re la valeur d'un param�tre dans la requete Http.
     * 
     * @param request
     *            requete Http
     * @param name
     *            nom du param�tre � r�cup�rer
     * @return Valeur du param�tre sous forme de String
     */
    private static String getFieldValue( HttpServletRequest request, String name ) {
        String result = request.getParameter( name );
        if ( result == null || result.trim().length() == 0 ) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * V�rifie le format du montant saisi par l'utilisateur et renvoi sa valeur
     * en double.
     * 
     * @param amount
     *            montant en String
     * @return le montant en double
     * @throws Exception
     *             en cas de montant non num�rique ou n�gatif
     */
    private double checkAmount( String amount ) throws Exception {
        double temp;
        if ( amount != null ) { // si le montant saisi n'est pas nul, on le
                                // parse en double
            try {
                temp = Double.parseDouble( amount );
                if ( temp < 0 ) { // si le montant est n�gatif on renvoi une
                                  // erreur
                    throw new Exception( message_exception_amount_negative );
                }
            } catch ( NumberFormatException e ) {
                temp = -1;
                throw new Exception( message_exception_amount_format );
            }
        } else { // si le montant est nul, on d�fini sa valeur � -1
            temp = -1;
            throw new Exception( message_exception_amount_empty );
        }
        return temp;
    }

    /**
     * V�rifie la validit� du mode de livraison saisi.
     * 
     * @param deliveryMod
     *            valeur saisie par l'utilisateur
     * @throws Exception
     *             si c'est trop court
     */
    private void checkDeliveryMod( String deliveryMod ) throws Exception {
        checkStringLength( deliveryMod, message_exception_deliveryMod_length );
    }

    /**
     * V�rifie la validit� du statut de livraison saisi.
     * 
     * @param deliveryStatus
     *            valeur saisie par l'utilisateur
     * @throws Exception
     *             si c'est trop court
     */
    private void checkDeliveryStatus( String deliveryStatus ) throws Exception {
        checkStringLength( deliveryStatus, message_exception_deliveryStatus_length );
    }

    /**
     * V�rifie la validit� de la m�thode de paiement saisi.
     * 
     * @param payMethod
     *            valeur saisie par l'utilisateur
     * @throws Exception
     *             si c'est trop court
     */
    private void checkPayMethod( String payMethod ) throws Exception {
        checkStringLength( payMethod, message_exception_payMethod_length );
    }

    /**
     * V�rifie la validit� du statut de paiement saisi.
     * 
     * @param payStatus
     *            valeur saisie par l'utilisateur
     * @throws Exception
     *             si c'est trop court
     */
    private void checkPayStatus( String payStatus ) throws Exception {
        checkStringLength( payStatus, message_exception_payStatus_length );
    }

    /**
     * M�thode permetant de v�rifier la taille minimal d'une string.
     * 
     * @param str
     *            String � v�rifier
     * @param message
     *            en cas d'erreur
     * @throws Exception
     *             si la String est trop courte
     */
    private void checkStringLength( String str, String message ) throws Exception {
        if ( str.trim().length() < 3 ) {
            throw new Exception( message );
        }
    }

    public String getResultOrder() {
        return resultOrder;
    }

    /**
     * D�fini le message de r�sultat du traitement de la saisie utilisateur.
     * 
     * @param success
     *            true si le traitement n'a produit aucune erreur, false sinon
     */
    public void setResultOrder( boolean success ) {
        if ( success ) {
            this.resultOrder = message_create_success;
        } else {
            this.resultOrder = message_create_fail;
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Ajoute un �l�ment � la Map 'errors'.
     * 
     * @param fieldName
     *            nom du champ qui l�ve l'erreur
     * @param message
     *            message associ� � l'erreur
     */
    public void setErrors( String fieldName, String message ) {
        errors.put( fieldName, message );
    }

    public boolean isOkOrder() {
        return okOrder;
    }
}
