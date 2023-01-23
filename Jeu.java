import java.util.Scanner;
public class Jeu {
    public static void lancerJeu() {

        int[][] plateau = new int[8][8];

        Scanner sc = new Scanner(System.in);
        int joueurCourant = 1, joueurAdverse = 2;
        int tour = 1;
        int  ligne, colonne;

        initialPlateau(plateau);
        System.out.println("Joueur 1, entrez votre pseudonyme pour la partie : ");
        String pseudo1= sc.nextLine();
        System.out.println("Joueur 2, entrez votre pseudonyme pour la partie : ");
        String pseudo2= sc.nextLine();

        // la boucle de jeu s'arrête lorsqu'il n'a plus de coups possible ou si le plateau de jeu est plein
        while (!mouvementPossible(plateau, joueurCourant, joueurAdverse) && !finParti(plateau)) {

            if (joueurCourant == 1) {
                System.out.println("Voici l'etat du plateau au tour numéro: " + (tour++) + '\t' + "C'est à " + pseudo1 + " de jouer, vous êtes les pions 1\n");
            } else {
                System.out.println("Voici l'etat du plateau au tour numéro: " + (tour++) + '\t' + "C'est à " + pseudo2 + " de jouer, vous êtes les pions 2\n");
            }
            afficheplateau(plateau);
            System.out.println("Votre score actuel est de: " + nombrePionJoueur(plateau, joueurCourant) + '\t' + "Le score du joueur adverse est de: " + nombrePionJoueur(plateau, joueurAdverse) + '\n');

            do {
                System.out.println("Veuillez saisir le numéro d'une ligne entre 1 et 8");
                ligne = sc.nextInt() - 1;
                System.out.println("Veuillez saisir le numéro d'une colonne entre 1 et 8");
                colonne = sc.nextInt() - 1;

                if (!horsPlateau(ligne, colonne)) {
                    System.out.println("Les valeurs attendus sont entre 1 et 8");
                } else if (!position(plateau, ligne, colonne)) {
                    System.out.println("Il y a déjà un pion à cet emplacement");
                } else if (!coupLegal(plateau, ligne, colonne, joueurCourant, joueurAdverse)) {
                    System.out.println("Ce n'est pas un coup jouable");
                }
            } while (!horsPlateau(ligne, colonne) || !position(plateau, ligne, colonne) || !coupLegal(plateau, ligne, colonne, joueurCourant, joueurAdverse));

                plateau[ligne][colonne] = joueurCourant;

                changHorizontalDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                changHorizontalGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                changVerticalHaut(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                changVerticalBas(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                changDiagonalDescDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                changDiagonalDescGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                changDiagonalAscDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                changDiagonalAscGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

            // changement du joueur courant et adverse après chaque tour avec une variable pivot
            int pivot = joueurCourant;
            joueurCourant = joueurAdverse;
            joueurAdverse = pivot;
        }

        afficheplateau(plateau);
        System.out.println("C'est la fin du jeu !");

        if (finParti(plateau)) {
            System.out.println("Il n'a plus de place sur l'othellier");
        }else {
            System.out.println("Plus aucun coup n'est réalisable");
        }

        System.out.println("Voici les scores finaux: \t " + pseudo1 + " posséde " + nombrePionJoueur(plateau, 1) + " pions \t"  + pseudo2 + " posséde " + nombrePionJoueur(plateau, 2) + " pions ");

            if (gagnant(plateau) == -1) {
                System.out.println("Il y a égalité ");
            } else if (gagnant(plateau) == 1) {
                System.out.println(pseudo1 + " a gagné la partie ");
            } else if (gagnant(plateau) == 2) {
                System.out.println(pseudo2 + " a gagné la partie ");
            }

    }

    /* Procédure qui remplit le tableau de 0 pour le vide, le 1 pour le joueur 1 et le 2 pour le joueur 2 */
    public static void initialPlateau(int[][] tab) {

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0;
            }
            tab[3][3] = 1;
            tab[4][4] = 1;
            tab[3][4] = 2;
            tab[4][3] = 2;
        }
    }
    public static void afficheplateau(int[][] tab) {

        int numeroColonne = 1, numeroLigne = 1;
        System.out.print(" ");
        for (int l = 0; l < tab.length; l++) {
            System.out.print("    " + 0 + numeroColonne);
            numeroColonne++;
        }

        System.out.println();
        for (int[] ints : tab) {
            System.out.print("0" + numeroLigne + '|');
            numeroLigne++;
            for (int anInt : ints) {
                System.out.print("   " + anInt + "  ");
            }
            System.out.println();
        }
    }

    // Fonction qui prend en paramètre la ligne et la colonne entré par l'utilisateur. Elle retourne vrai seulement si le ligne et la colonne est comprise entre 0 et 7 inclut
    public static boolean horsPlateau(int ligne, int colonne) {

        return ligne >= 0 && ligne <= 7 && colonne >= 0 && colonne <= 7;
    }

    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur. Elle vérifie que cet emplacement est bien occupé par un 0 pour pouvoir placer un pion par dessus.
    // Retourne vrai si elle est occupé par un 0.
    public static boolean position(int[][] tab, int ligne, int colonne) {

        return tab[ligne][colonne] == 0;
    }

    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que la fonction verif Position Adverse retourne vrai et qu’au moins une des fonctions chang retourne vrai.
    // Si c’est le cas alors l’emplacement que le joueur veut jouer est possible, elle retourne vrai.
    public static boolean coupLegal(int[][] tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        return verifPositionAdverse(tab, ligne, colonne, joueurAdverse) && (verifVerticalBas(tab, ligne, colonne, joueurCourant, joueurAdverse)|| verifVerticalHaut(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifDiagonalDescGauche(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifDiagonalAscGauche(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifHorizontalGauche(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifDiagonalAscDroite(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifDiagonalDescDroite(tab, ligne, colonne, joueurCourant, joueurAdverse) || verifHorizontalDroite(tab, ligne, colonne, joueurCourant, joueurAdverse));
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire
    // Et en fonction des différentes positions si la ligne et la colonne ne dépasse pas du tableau alors on vérifie qu’il y a bien un joueur adverse à ces positions : c’est à dire l’emplacement à droite, gauche,
    // bas, haut, la diagonal bas gauche/droite et enfin la diagonal haut gauche et droite depuis l’emplacement initial.
    // Pour que cette fonction retourne vrai il faut qu’au moins une de ces 8 positions contiennent un joueur adverse.
    public static boolean verifPositionAdverse(int[][] tab, int ligne, int colonne, int joueurAdverse) {

        boolean legal = false;

        //diagonal bas droite
        if (ligne<7 && colonne<7 && tab[ligne+1][colonne+1]==joueurAdverse) {
            legal = true;
        }
        //bas
        else if (ligne < 7 && tab[ligne + 1][colonne] == joueurAdverse) {
            legal = true;
            //droite
        } else if (colonne < 7 && tab[ligne][colonne + 1] == joueurAdverse) {
            legal = true ;
            //gauche
        } else if (colonne > 0 && tab[ligne][colonne - 1] == joueurAdverse ) {
            legal = true;
            //haut diagonal gauche
        } else if (ligne>0 && colonne>0 && tab[ligne-1][colonne-1]==joueurAdverse ) {
            legal = true;
            //haut
        } else if (ligne>0 && tab[ligne-1][colonne]==joueurAdverse) {
            legal = true;
            //diagonal haut droite
        } else if(ligne>0 && colonne<7 && tab[ligne-1][colonne+1]==joueurAdverse) {
            legal = true;
            //diagonal bas gauche
        }else if (ligne<7 && colonne>0 && tab[ligne+1][colonne-1]==joueurAdverse) {
            legal = true;
        }
        return legal;
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de l’horizontal droite.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifHorizontalDroite(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean horizontal = false;
        int compteur=0;

        while (colonne<7 && tab[ligne][colonne+ 1 ] ==joueurAdverse ){

            if (tab[ligne][colonne + 1] == joueurAdverse) {
                colonne++;
                compteur++;
            }
        }
        if (colonne<7) {
            if (tab[ligne][colonne + 1] == joueurCourant && compteur > 0) {
                horizontal = true;
            }
        }
        return horizontal;
    }


    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifHorizontalDroite est vrai alors on change les cases en direction de l’horizontal droite par celle du numéro du joueur Courant.
    public static void changHorizontalDroite(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne < 7 && verifHorizontalDroite(tab, ligne, colonne, joueurCourant, joueurAdverse)) {
            tab[ligne][colonne + 1] = joueurCourant;
            colonne++;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de l’horizontal gauche.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifHorizontalGauche(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean horizontalG = false;
        int compteur=0;

        while (colonne > 0 && tab[ligne][colonne - 1 ] ==joueurAdverse ){

            if (tab[ligne][colonne - 1] == joueurAdverse) {
                colonne--;
                compteur++;
            }
        }
        if (colonne > 0) {
            if (tab[ligne][colonne - 1] == joueurCourant && compteur > 0) {
                horizontalG = true;
            }
        }
        return horizontalG;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifHorizontalGauche est vrai alors on change les cases en direction de l’horizontal gauche par celle du numéro du joueur Courant.
    public static void changHorizontalGauche(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne > 0 && verifHorizontalGauche(tab, ligne, colonne, joueurCourant,joueurAdverse) ) {
            tab[ligne][colonne - 1] = joueurCourant;
            colonne--;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la vertical haut.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifVerticalHaut(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean vertical = false;
        int compteur=0;

        while (ligne > 0 && tab[ligne-1][colonne] ==joueurAdverse ){

            if (tab[ligne-1][colonne] == joueurAdverse) {
                ligne--;
                compteur++;
            }
        }
        if (ligne > 0) {
            if (tab[ligne - 1][colonne] == joueurCourant && compteur > 0) {
                vertical = true;
            }
        }
        return vertical;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifVerticalHaut est vrai alors on change les cases en direction de la vertical haut par celle du numéro du joueur Courant.
    public static void changVerticalHaut(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (ligne > 0 && verifVerticalHaut(tab, ligne, colonne, joueurCourant,joueurAdverse) ) {
            tab[ligne-1][colonne] = joueurCourant;
            ligne--;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la vertical bas.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifVerticalBas(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean vertical = false;
        int compteur=0;

        while (ligne < 7 && tab[ligne+1][colonne] ==joueurAdverse ){

            if (tab[ligne+1][colonne] == joueurAdverse) {
                ligne++;
                compteur++;
            }
        }
        if (ligne<7) {
            if (tab[ligne+1][colonne] == joueurCourant && compteur>0) {
                vertical = true;
            }
        }
        return vertical;
    }


    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifVerticalBas est vrai alors on change les cases en direction de la vertical bas par celle du numéro du joueur Courant.
    public static void changVerticalBas(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (ligne < 7 && verifVerticalBas(tab, ligne, colonne, joueurCourant,joueurAdverse) ) {
            tab[ligne+1][colonne] = joueurCourant;
            ligne++;
        }
    }

    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la diagonale descendante droite.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifDiagonalDescDroite(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean diagoDesDroite = false;
        int compteur=0;

        while (colonne < 7 && ligne < 7 && tab[ligne+1][colonne+1] ==joueurAdverse ){

            if (tab[ligne+1][colonne+1] == joueurAdverse) {
                ligne++;
                colonne++;
                compteur++;
            }
        }
        if (ligne<7 && colonne < 7) {
            if (tab[ligne+1][colonne+1] == joueurCourant && compteur>0) {
                diagoDesDroite = true;
            }
        }
        return diagoDesDroite;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifDiagonalDescDroite est vrai alors on change les cases en direction de la diagonale descendante droite par celle du numéro du joueur Courant.
    public static void changDiagonalDescDroite(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne < 7 && ligne< 7 && verifDiagonalDescDroite(tab, ligne, colonne, joueurCourant,joueurAdverse) ) {
            tab[ligne+1][colonne+1] = joueurCourant;
            colonne++;
            ligne++;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la diagonale descendante gauche.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifDiagonalDescGauche(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean diagoDesGauche = false;
        int compteur=0;

        while (colonne > 0 && ligne < 7 && tab[ligne+1][colonne-1] ==joueurAdverse ){

            if (tab[ligne+1][colonne-1] == joueurAdverse) {
                ligne++;
                colonne--;
                compteur++;
            }
        }
        if (colonne > 0 && ligne < 7) {
            if (tab[ligne+1][colonne-1] == joueurCourant && compteur>0) {
                diagoDesGauche = true;
            }
        }
        return diagoDesGauche;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifDiagonalDescGauche est vrai alors on change les cases en direction de la diagonale descendante gauche par celle du numéro du joueur Courant.
    public static void changDiagonalDescGauche(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne > 0 && ligne< 7 && verifDiagonalDescGauche(tab, ligne, colonne, joueurCourant,joueurAdverse)) {
            tab[ligne+1][colonne-1] = joueurCourant;
            colonne--;
            ligne++;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la diagonale ascendante droite.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifDiagonalAscDroite(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean diagoAscDroite = false;
        int compteur=0;

        while (colonne < 7 && ligne > 0 && tab[ligne-1][colonne+1] ==joueurAdverse ){

            if (tab[ligne-1][colonne+1] == joueurAdverse) {
                ligne--;
                colonne++;
                compteur++;
            }
        }
        if (colonne < 7 && ligne > 0) {
            if (tab[ligne-1][colonne+1] == joueurCourant && compteur>0) {
                diagoAscDroite = true;
            }
        }
        return diagoAscDroite;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifDiagonalAscDroite est vrai alors on change les cases en direction de la diagonale ascendante droite par celle du numéro du joueur Courant.
    public static void changDiagonalAscDroite(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne < 7 && ligne > 0 && verifDiagonalAscDroite(tab, ligne, colonne, joueurCourant,joueurAdverse)) {
            tab[ligne-1][colonne+1] = joueurCourant;
            ligne--;
            colonne++;
        }
    }


    // Fonction qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Elle vérifie que le pion qu’on veut nouvellement posé est bien l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est
    // déjà occupée par un de ses propres pions en direction de la diagonale ascendante gauche.
    // La fonction retourne vrai si c’est le cas.
    public static boolean verifDiagonalAscGauche(int[][] tab, int ligne, int colonne, int joueurCourant,int joueurAdverse) {

        boolean diagoAscGauche = false;
        int compteur=0;

        while (colonne > 0 && ligne > 0 && tab[ligne-1][colonne-1] ==joueurAdverse ){

            if (tab[ligne-1][colonne-1] == joueurAdverse) {
                ligne--;
                colonne--;
                compteur++;
            }
        }
        if (colonne > 0 && ligne > 0) {
            if (tab[ligne - 1][colonne - 1] == joueurCourant && compteur > 0) {
                diagoAscGauche = true;
            }
        }
        return diagoAscGauche;
    }

    // Procédure qui prend en paramètre le tableau, la ligne, la colonne entré par l’utilisateur, le numéro du joueur courant et celui de l'adversaire.
    // Et tant que la fonction verifDiagonalAscGauche est vrai alors on change les cases en direction de la diagonale ascendante gauche par celle du numéro du joueur Courant.
    public static void changDiagonalAscGauche(int [][]tab, int ligne, int colonne, int joueurCourant, int joueurAdverse) {

        while (colonne > 0 && ligne > 0 && verifDiagonalAscGauche(tab, ligne, colonne, joueurCourant,joueurAdverse) ) {
            tab[ligne-1][colonne-1] = joueurCourant;
            ligne--;
            colonne--;
        }
    }


    // fonction qui prend en paramètre le tableau et le numéro du joueur et retourne le nombre de pions d'un joueur
    public static int nombrePionJoueur(int[][]tab, int joueur) {

        int compteur=0;
        for (int i=0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (tab[i][j] == joueur)
                    compteur++;
            }
        }
        return compteur;
    }


    // Fonction qui prend en paramètre le tableau et compte le nombre de cases occupées par un 1 ou un 2.
    // un compteur permet de compter ces cases occupées et si on atteint 64 avec le compteur alors le tableau est rempli et on retourne vrai.
    public static boolean finParti(int [][]tab) {

        int compteur=0;
        for (int i=0; i < 8; i++) {
            for (int j=0; j< 8; j++) {
                if (tab[i][j] == 1 || tab[i][j] == 2)
                    compteur++;

            }
        }
        return compteur == 64;
    }

    // Fonction qui prend en paramètre le tableau, le numéro du joueur courant et celui de l'adversaire.
    //  Elle vérifie pour chaque case du tableau si le joueur Courant peut poser un pion à au moins un emplacement sur le tableau grâce à la fonction coupLegal
    //  Si c’est le cas alors elle retourne vrai.
    public static boolean mouvementPossible(int [][]tab, int joueurCouant, int joueurAdverse) {

        int movePoss = 0;

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {

                if (tab[i][j] == 0) {
                    if (coupLegal(tab, i, j, joueurCouant, joueurAdverse))
                        movePoss++;
                }
            }
        }
        return movePoss == 0;
    }

    // prend en paramètre le tableau et compte le nombre de 1 et de 2.
    // Si le nombre de 1 est supérieur alors elle retourne 1, si le nombre de 2 est supérieur alors elle retourne 2
    // Sinon la fonction retourne -1.
    public static int gagnant(int[][] tab) {

        int compteurDe1 = 0;
        int compteurDe2 = 0;

        for (int[] ints : tab) {
            for (int anInt : ints) {

                if (anInt == 1)
                    compteurDe1++;
                else if (anInt == 2)
                    compteurDe2++;
            }
        }
        if (compteurDe1 > compteurDe2)
            return 1;
        else if (compteurDe2 > compteurDe1)
            return 2;
        else
            return -1;
    }
}
