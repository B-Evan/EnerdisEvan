/*
 * Created on 27 august 2006
 * Update on 7 october 2024
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.btsciel;

/**
 * @author MOULAI Philippe <br>
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Cette classe recupere les touches saisies au clavier Chaque methode retourne
 * les informations saisies au clavier dans le format desire<br>.
 * <hr>
 * Pour afficher les chiffres decimaux 2 chiffres apres la virgule vous pouvez
 * utiliser <br>
 * DecimalFormat df = new DecimalFormat("##00.00");
 * <br> puis afficher<br>
 * System.out.println(df.format(maValeurEnDecimal));
 */
public class In {

    /*
     * @param in
     * c'est une reference sur le flux du clavier
     */
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme d'entier
     * @return un entier qui correspond à la valeur tapee sur le clavier
     * @exception NumberFormatException si la valeur saisie n'est pas un entier
     */
    public static int readInteger() {
        /*
         * mot stocke les touches tapees au clavier
         * jusqu'a ce que l'utilisateur appuie sur
         * la touche entree du clavier
         */
        String mot = null;
        int valeur = -1;
        do {
            try {
                mot = in.readLine();
                /* Si des touches ont ete appuyees */
                if (!mot.isEmpty()) {
                    valeur = Integer.valueOf(mot);
                }
            } catch (IOException e) {
                System.out.println("Erreur de lecture du clavier " + e);
            } catch (NumberFormatException e) {
                mot = "";
                System.out.println("Veuillez entrer un entier");
            }
        } while (mot.isEmpty());
        /* La valeur entiere est retournee  */
        return valeur;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme d'entier
     *
     * @return un entier qui correspond a la valeur tapee sur le clavier

     * @exception NumberFormatException si la valeur saisie n'est pas un entier
     */
    public static long readLong() {
        /*
         * mot stocke les touches tapees au clavier
         * jusqu'a ce que l'utilisateur appuie sur
         * la touche entree du clavier
         */
        String mot = "";
        do {
            try {
                mot = in.readLine();
                /* Si des touches ont ete appuyees */
                if (!mot.isEmpty()) {
                    /* La valeur entiere est retournee */
                    return Long.valueOf(mot);
                }
            } catch (IOException e) {
                System.out.println("Erreur de lecture du clavier " + e);
            } catch (NumberFormatException e) {
                mot = "";
                System.out.println("Veuillez entrer un Long");
            }
        } while (mot.isEmpty());
        return 0L;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme de double
     *
     * @return un double qui correspond a la valeur tapee sur le clavier
     * @exception IOException : probleme avec le clavier, declaration
     * obligatoire en java
     * @exception NumberFormatException si la valeur saisie n'est pas un double
     */
    public static double readDouble() //throws Exception
    {
        String mot = "";
        double valeur = -1;
        do {
            try {
                mot = in.readLine();
                int val = 0;
                /*
                 * Si des touches ont ete appuyees
                 */
                if (!mot.isEmpty()) {
                    //  val = Integer.valueOf(mot).intValue();
                    /*
                     * La valeur entiere est retournee
                     */
                    valeur = Double.valueOf(mot).doubleValue();
                }

            } catch (IOException e) {
                System.out.println("Erreur de lecture du clavier " + e);
            } catch (NumberFormatException e) {
                mot = "";
                System.out.println("Veuillez entrer un double");
            }
        } while (mot.isEmpty());
        return valeur;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme de flottant
     *
     * @return un flottant qui correspond a la valeur tapee sur le clavier
     * obligatoire en java
     * @exception NumberFormatException si la valeur saisie n'est pas un
     * flottant
     */
    public static float readFloat() {

        String mot = "";
        float valeur = -1;
        do {
            try {
                mot = in.readLine();
                /* Si des touches ont ete appuyees  */
                if (!mot.isEmpty()) {
                    //  val = Integer.valueOf(mot).intValue();
                    /* La valeur entiere est retournee */
                    valeur = Float.valueOf(mot).floatValue();
                }
            } catch (IOException e) {
                System.out.println("Erreur de lecture du clavier " + e);
            } catch (NumberFormatException e) {
                mot = "";
                System.out.println("Veuillez entrer un double");
            }
        } while (mot.isEmpty());
        return valeur;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme d'une chaine de caratere
     *
     * @return une chaine de caratere qui correspond a la valeur tapee sur le
     * clavier
     * @exception IOException : probleme avec le clavier, declaration
     */
    public static String readString() //throws Exception
    {
        int valeur = 0;
        char c = 0;
        StringBuilder sb = new StringBuilder();
        try {
            /*
             * Retourne directemet les touches tapees au clavier
             * lorsque l'utilisateur a appuiye sur
             * la touche entree du clavier
             */
            do {
                c = (char) System.in.read();
                valeur = c;
                sb.append(c);
            } while (valeur != 10);
            return sb.substring(0, sb.length() - 1);
        } catch (IOException e) {
            System.out.println("Erreur de lecture de clavier " + e);
        }
        return "";
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme de caratere
     *
     * @return un caratere qui correspond a la valeur tapee sur le clavier
     * @exception IOException : probleme avec le clavier, declaration
     * @exception NumberFormatException si la valeur saisie n'est pas un
     * caratere
     */
    public static char readChar() //throws Exception
    {
        String mot = null;
        try {
            /*
             * mot stocke les touches tapees au clavier
             * jusqu'a ce que l'utilisateur appuie sur
             * la touche entree du clavier
             */
            do {
                mot = in.readLine();
                /*
                 * Si des touches ont ete appuyees
                 */
                if (mot.length() > 0) {
                    /*
                     * La valeur de tpe char est retournee
                     */
                    return mot.charAt(0);
                }
            } while (mot.isEmpty());
        } catch (IOException e) {
            System.out.println("Erreur de lecture du clavier " + e);
        } catch (NumberFormatException e1) {
            /*
             * Si la valeur saisie au clavier n'est pas un char,
             * je passe dans ce code
             */
            System.out.println("Vous n'avez pas saisi un char ");
        }
        return 0;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme de byte
     *
     * @return une chaine de caratere qui correspond a la valeur tapee sur le
     * clavier
     * @exception IOException : probleme avec le clavier, declaration
     * @exception NumberFormatException si la valeur saisie n'est pas un byte
     */
    public static byte readByte() //throws Exception
    {
        String mot = "";
        byte valeur = -1;
        do {
            try {
                mot = in.readLine();
                int val = 0;
                /*
                 * Si des touches ont ete appuyees
                 */
                if (!mot.isEmpty()) {
                    //  val = Integer.valueOf(mot).intValue();
                    /*
                     * La valeur entiere est retournee
                     */
                    valeur = Byte.valueOf(mot).byteValue();
                }

            } catch (IOException e) {
                System.out.println("Erreur de lecture du clavier " + e);
            } catch (NumberFormatException e) {
                mot = "";
                System.out.println("Veuillez entrer un byte");
            }
        } while (mot.isEmpty());
        return valeur;
    }

    /**
     * Cette methode permet de retourner les touches tapees par l'utilisateur
     * sous forme d'un tableau de caractere
     *
     * @return une chaine de caratere qui correspond a la valeur tapee sur le
     * clavier
     * @exception IOException : probleme avec le clavier, declaration
     * @exception NumberFormatException si la valeur saisie n'est pas un tableau
     * de caractere
     */
    public static char[] readArrayChar()// throws Exception
    {
        String mot;
        try {
            /*
             * mot stocke les touches tapees au clavier
             * jusqu'a ce que l'utilisateur appuie sur
             * la touche entree du clavier
             */
            mot = in.readLine();
            /* Si des touches ont ete appuyees  */
            if (!mot.isEmpty()) {
                /* La valeur est de type char[] est retournee  */
                return mot.toCharArray();
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du clavier " + e);
        } catch (NumberFormatException e1) {
            /* Si la valeur saisie au clavier n'est pas un byte, je passe dans ce code  */
            System.out.println("Vous n'avez pas saisi un entier ");
        }
        return null;
    }
}
