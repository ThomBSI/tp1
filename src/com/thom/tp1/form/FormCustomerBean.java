package com.thom.tp1.form;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.thom.tp1.beans.CustomerBean;

public class FormCustomerBean {

    public static final String  FIELD_FIRST_NAME               = "prenomClient";
    public static final String  FIELD_LAST_NAME                = "nomClient";
    public static final String  FIELD_ADRESS                   = "adresseClient";
    public static final String  FIELD_MAIL                     = "emailClient";
    public static final String  FIELD_PHONE                    = "telephoneClient";
    public static final String  FIELD_FILES                    = "image";

    public static final String  ATTR_PATH_FILE                 = "pathFiles";

    public static final int     BUFFER_LENGTH                  = 10240;

    private String              resultCustomer;
    private Map<String, String> errors                         = new HashMap<String, String>();
    private boolean             okCustomer                     = false;

    private static final String message_exception_firstName    = "Votre prénom doit contenir au moins 2 caractères.";
    private static final String message_exception_lastName     = "Votre nom doit contenir au moins 2 caractères.";
    private static final String message_exception_adress       = "Votre adresse doit contenir au moins 10 caractères.";
    private static final String message_exception_mail_valid   = "Merci de saisir une adresse email valide.";
    private static final String message_exception_mail_empty   = "Merci de saisir une adresse email.";
    private static final String message_exception_phone_valid  = "Merci de saisir un numèro de téléphone valide.";
    private static final String message_exception_phone_length = "Le numéro de téléphone doit contenir au moins 4 chiffres";
    private static final String message_exception_phone_empty  = "Merci d'entrer un numéro de téléphone.";
    private static final String message_create_success         = "Le compte a été crée avec succés.";
    private static final String message_create_fail            = "La création du compte a échouée.";
    private static final String message_file_tobig             = "Les données envoyées sont trop volumineuses.";
    private static final String message_file_config            = "Erreur de configuration du serveur.";
    private static final String message_file_request           = "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.";
    private static final String message_file_empty             = "Merci de sélectionner un fichier à envoyer";
    private static final String message_file_write             = "Erreur lors de l'écriture du fichier sur le disque.";

    /**
     * V�rifie la validit� des donn�es saisies par l'utilisateur Construit
     * l'objet 'utilisateur' Le renvoi � la servlet
     * 
     * @param request
     *            requete Http
     * @return objet CustomerBean avec tous les champs set�s
     */
    public CustomerBean initCustomer( HttpServletRequest request, String filesPath ) {

        String fileName = null;
        InputStream fileContent = null;

        try {
            Part part = request.getPart( FIELD_FILES );
            // Récupération du nom du fichier
            fileName = getFileName( part );
            if ( fileName != null && !fileName.isEmpty() ) {
                String fieldName = part.getName();
                // antibug pour IE :
                fileName = fileName.substring( fileName.lastIndexOf( "/" ) + 1 )
                        .substring( fileName.lastIndexOf( "\\" ) + 1 );
                // On récupère le contenu du fichier
                fileContent = part.getInputStream();
            }
        } catch ( IllegalStateException e ) {
            e.printStackTrace();
            setErrors( FIELD_FILES, message_file_tobig );
        } catch ( IOException e ) {
            e.printStackTrace();
            setErrors( FIELD_FILES, message_file_config );
        } catch ( ServletException e ) {
            e.printStackTrace();
            setErrors( FIELD_FILES, message_file_request );
        }

        // R�cup�ration du pr�nom
        String firstName = getFieldValue( request, FIELD_FIRST_NAME );
        // R�cup�ration du nom
        String lastName = getFieldValue( request, FIELD_LAST_NAME );
        // R�cup�ration de l'adresse
        String adress = getFieldValue( request, FIELD_ADRESS );
        // R�cup�ration de l'adresse mail
        String email = getFieldValue( request, FIELD_MAIL );
        // R�cup�ration du num�ro de t�l�phone
        String phone = getFieldValue( request, FIELD_PHONE );

        // On instancie un objet customer
        CustomerBean customer = new CustomerBean();

        // Si aucune erreur jusqu'à présent :
        if ( errors.isEmpty() ) {
            // v�rification et ajout du pr�nom au customer
            try {
                checkFirstName( firstName );
            } catch ( Exception e ) {
                setErrors( FIELD_FIRST_NAME, e.getMessage() );
            }
            customer.setFirstName( firstName );

            // v�rification et ajout du nom au customer
            try {
                checkLastName( lastName );
            } catch ( Exception e ) {
                setErrors( FIELD_LAST_NAME, e.getMessage() );
            }
            customer.setLastName( lastName );

            // v�rification et ajout de l'adresse au customer
            try {
                checkAdress( adress );
            } catch ( Exception e ) {
                setErrors( FIELD_ADRESS, e.getMessage() );
            }
            customer.setAdress( adress );

            // v�rification et ajout du mail au customer
            try {
                checkMail( email );
            } catch ( Exception e ) {
                setErrors( FIELD_MAIL, e.getMessage() );
            }
            customer.setMail( email );

            // v�rification et ajout du t�l�phone au customer
            try {
                checkPhone( phone );
            } catch ( Exception e ) {
                setErrors( FIELD_PHONE, e.getMessage() );
            }
            customer.setPhone( phone );

            // vérification de l'image
            try {
                checkFile( fileName, fileContent );
            } catch ( Exception e ) {
                setErrors( FIELD_FILES, e.getMessage() );
            }
            customer.setImageName( fileName );
        }

        // Si toujours pas d'erreur, on ecrit le fichier sur le disque :
        if ( errors.isEmpty() ) {
            try {
                writeFile( fileContent, fileName, filesPath );
            } catch ( Exception e ) {
                setErrors( FIELD_FILES, message_file_write );
            }
        }

        if ( errors.isEmpty() ) { // si aucune erreur n'a �t� soulev�e, on set
                                  // le message de success
            okCustomer = true;
            setResultCustomer( okCustomer );
        } else { // s'il y a eu des erreur, on set le message d'echec
            setResultCustomer( okCustomer );
        }

        return customer;
    }

    /**
     * Valider le pr�nom saisie par l'utilisateur.
     * 
     * @param name
     *            la valeur du pr�nom
     * @throws Exception
     *             en cas de pr�nom trop court
     */
    private void checkFirstName( String name ) throws Exception {
        if ( !( name.trim().length() >= 2 ) ) { // si le pr�nom n'est pas assez
                                                // long
            throw new Exception( message_exception_firstName );
        }
    }

    /**
     * Valider le nom saisie par l'utilisateur.
     * 
     * @param name
     *            la valeur du nom
     * @throws Exception
     *             en cas de nom trop court
     */
    private void checkLastName( String name ) throws Exception {
        if ( !( name.trim().length() >= 2 ) ) { // si le nom n'est pas assez
                                                // long
            throw new Exception( message_exception_lastName );
        }
    }

    /**
     * Valider l'adresse saisie par l'utilisateur.
     * 
     * @param adress
     *            la valeur de l'adresse
     * @throws Exception
     *             en cas l'adresse trop courte
     */
    private void checkAdress( String adress ) throws Exception {
        if ( !( adress.trim().length() >= 10 ) ) { // Si l'adresse n'est pas
                                                   // assez longue
            throw new Exception( message_exception_adress );
        }
    }

    /**
     * Valider l'adresse mail saisie par l'utilisateur grace � des regex.
     * 
     * @param mail
     *            la valeur de l'adresse mail
     * @throws Exception
     *             en cas l'adresse mail invalide ou vide
     */
    private void checkMail( String mail ) throws Exception {
        if ( !mail.isEmpty() ) { // Si l'adresse mail saisie ne correspond pas
                                 // au motif de la regex et si elle n'est pas
                                 // vide
            if ( !mail.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( message_exception_mail_valid );
            }
        } else { // si aucune adresse mail n'a �t� saisie
            throw new Exception( message_exception_mail_empty );
        }
    }

    /**
     * Valider le num�ro de t�l�phone saisie par l'utilisateur grace � des
     * regex.
     * 
     * @param name
     *            la valeur du num�ro de t�l�phone
     * @throws Exception
     *             en cas de num�ro de t�l�phone invalide ou vide
     */
    private void checkPhone( String phone ) throws Exception {
        if ( phone != null ) { // si le num�ro saisi n'est pas nul
            if ( !phone.matches( "^\\d+$" ) ) { // si le num�ro saisi ne
                                                // respecte pas le motif de la
                                                // regex
                throw new Exception( message_exception_phone_valid );
            } else if ( phone.length() < 4 ) { // si le num�ro est trop court
                throw new Exception( message_exception_phone_length );
            }
        } else { // si l'utilisateur n'a saisi aucun num�ro
            throw new Exception( message_exception_phone_empty );
        }

    }

    /**
     * Vérifie que l'image à bien été envoyée et que c'est bien une image.
     * 
     * @param fileName
     * @param fileContent
     * @throws Exception
     */
    private void checkFile( String fileName, InputStream fileContent ) throws Exception {
        if ( fileName == null || fileContent == null ) {
            throw new Exception( message_file_empty );
        }
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
    private static String getFieldValue( HttpServletRequest request, String fieldName ) {
        String result = request.getParameter( fieldName );
        if ( result == null || result.trim().length() == 0 ) {
            return null;
        } else {
            return result;
        }
    }

    public String getResultCustomer() {
        return resultCustomer;
    }

    /**
     * D�fini le message de r�sultat du traitement de la saisie utilisateur.
     * 
     * @param success
     *            true si le traitement n'a produit aucune erreur, false sinon
     */
    public void setResultCustomer( boolean success ) {
        if ( success ) {
            this.resultCustomer = message_create_success;
        } else {
            this.resultCustomer = message_create_fail;
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Ajoute un �l�ment � la Map 'errors'.
     * 
     * @param nameField
     *            nom du champ qui l�ve l'erreur
     * @param message
     *            message associ� � l'erreur
     */
    public void setErrors( String nameField, String message ) {
        errors.put( nameField, message );
    }

    public boolean isOkCustomer() {
        return okCustomer;
    }

    /**
     * Méthode de récupération du nom d'un fichier.
     * 
     * @param part
     * @return le nom du fichier si le part contient bel et bien un fichier,
     *         null sinon.
     */
    private String getFileName( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( "=" ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }

    private void writeFile( InputStream contentFile, String fileName, String pathFile ) {
        // Préparation des flux :
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            // ouverture des flux :
            input = new BufferedInputStream( contentFile, BUFFER_LENGTH );
            output = new BufferedOutputStream( new FileOutputStream( new File( pathFile + fileName ) ), BUFFER_LENGTH );

            // lit le fichier et écrit son contenu sur un fichier sur le disque
            // :
            byte[] buffer = new byte[BUFFER_LENGTH];
            int length;
            while ( ( length = input.read( buffer ) ) > 0 ) {
                output.write( buffer, 0, length );
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch ( IOException ignore ) {
            }
            try {
                input.close();
            } catch ( Exception ignore ) {
            }
        }
    }

}
