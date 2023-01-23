import java.util.Scanner;

public class IaJeu {
    public static void lancerIaJeu() {

        int[][] plateau = new int[8][8];

        Scanner sc = new Scanner(System.in);
        int joueurCourant = 1, joueurAdverse = 2;
        int tour = 1;
        int ligne, colonne;

        System.out.println("Veuillez saisir un pseudo pour la partie : ");
        String pseudo = sc.nextLine();
        Jeu.initialPlateau(plateau);

        while (!Jeu.mouvementPossible(plateau, joueurCourant, joueurAdverse) && !Jeu.finParti(plateau)) {
            {
            System.out.println("Voici l'etat du plateau au tour numéro: " + (tour++) + '\n');
            Jeu.afficheplateau(plateau);

            if (joueurCourant == 1) {

                System.out.print("C'est à " + pseudo + " de jouer, vous êtes les pions 1 \n");
                System.out.println("Votre score actuel est de: " + Jeu.nombrePionJoueur(plateau, joueurCourant) + '\t' + "Le score de l'intelligence artificielle est de: " + Jeu.nombrePionJoueur(plateau, joueurAdverse) + '\n');

                do {
                    System.out.println("Veuillez saisir le numéro d'une ligne entre 1 et 8");
                    ligne = sc.nextInt() - 1;
                    System.out.println("Veuillez saisir le numéro d'une colonne entre 1 et 8");
                    colonne = sc.nextInt() - 1;

                    if (!Jeu.horsPlateau(ligne, colonne)) {
                        System.out.println("Les valeurs attendus sont entre 1 et 8");
                    } else if (!Jeu.position(plateau, ligne, colonne)) {
                        System.out.println("Il y a déjà un pion à cet emplacement");
                    } else if (!Jeu.coupLegal(plateau, ligne, colonne, joueurCourant, joueurAdverse)) {
                        System.out.println("Ce n'est pas un coup jouable");
                    }
                } while (!Jeu.horsPlateau(ligne, colonne) || !Jeu.position(plateau, ligne, colonne) || !Jeu.coupLegal(plateau, ligne, colonne, joueurCourant, joueurAdverse));
                {
                    plateau[ligne][colonne] = joueurCourant;

                    Jeu.changHorizontalDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                    Jeu.changHorizontalGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                    Jeu.changVerticalHaut(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                    Jeu.changVerticalBas(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                    Jeu.changDiagonalDescDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                    Jeu.changDiagonalDescGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                    Jeu.changDiagonalAscDroite(plateau, ligne, colonne, joueurCourant, joueurAdverse);
                    Jeu.changDiagonalAscGauche(plateau, ligne, colonne, joueurCourant, joueurAdverse);

                }
            }

            if (joueurCourant == 2) {

                int randomX, randomY;

                // tant que la position creer par l'oerdinateur avec les chiffres aléatoire ne donne pas un emplacement jouable et vide alors l'ordinateur choisit de nouveaux chiffres aleatoire
                do {
                    randomX = (int) (Math.random() * 8);
                    randomY = (int) (Math.random() * 8);

                } while (!Jeu.position(plateau, randomX, randomY) || !Jeu.coupLegal(plateau, randomX, randomY, joueurCourant, joueurAdverse));

                plateau[randomX][randomY] = 2;

                Jeu.changHorizontalDroite(plateau, randomX, randomY, joueurCourant, joueurAdverse);
                Jeu.changHorizontalGauche(plateau, randomX, randomY, joueurCourant, joueurAdverse);

                Jeu.changVerticalHaut(plateau, randomX, randomY, joueurCourant, joueurAdverse);
                Jeu.changVerticalBas(plateau, randomX, randomY, joueurCourant, joueurAdverse);

                Jeu.changDiagonalDescDroite(plateau, randomX, randomY, joueurCourant, joueurAdverse);
                Jeu.changDiagonalDescGauche(plateau, randomX, randomY, joueurCourant, joueurAdverse);

                Jeu.changDiagonalAscDroite(plateau, randomX, randomY, joueurCourant, joueurAdverse);
                Jeu.changDiagonalAscGauche(plateau, randomX, randomY, joueurCourant, joueurAdverse);

            }
        }
            int pivot = joueurCourant;
            joueurCourant = joueurAdverse;
            joueurAdverse = pivot;
        }


        Jeu.afficheplateau(plateau);
        System.out.println("C'est la fin du jeu !");


        if (Jeu.finParti(plateau)) {
            System.out.println("Il n'a plus de place sur l'othellier");
        }else {
            System.out.println("Plus aucun coup n'est réalisable");
        }

        System.out.println("Voici les scores finaux: \t " + pseudo + " possédez " + Jeu.nombrePionJoueur(plateau, 1) + " pions \t L'intelligence artificielle posséde " + Jeu.nombrePionJoueur(plateau, 2) + " pions ");

        if (Jeu.gagnant(plateau) == -1) {
            System.out.println("Il y a égalité ");
        } else if (Jeu.gagnant(plateau) == 1) {
            System.out.println( pseudo + " a gagné la partie ");
        } else if (Jeu.gagnant(plateau) == 2) {
            System.out.println("l'intelligence artificielle a gagné la partie ");
        }
    }

}
