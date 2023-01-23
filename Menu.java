import java.util.Scanner;
public class Menu {
    public static void lancerMenu(){

        Scanner sc= new Scanner(System.in);
        int choix;

        do {
            System.out.println();
            System.out.println( "1. Découvrer les règles du jeu " + '\n' + "2. Lancer une partie" + '\n' + "3. Lancer une partie contre une ia" + '\n' + "4. Quitter le menu");
            System.out.println(" \nSaisir votre choix entre les 4 options, entrez le numéro de votre choix: ");
            choix = sc.nextInt();

            switch (choix) {

                case 1:

                    System.out.println("Othello est un jeu de plateau et se joue à deux. Un joueur avec les pions noirs, soit le pion numéro 1, et l'autre avec des pions blancs, soit le pion numéro 2. Le joueur ayant les pions 1 commence." + "\n" + "L'Othellier comporte 64 cases soit un plateau de 8 sur 8. Avant le début de la partie 4 pions sont déjà placés au centre du plateau." + "\n" + "Le but du jeu est d'avoir plus de pions que l'adversaire." + "\n" + "La capture de pions survient lorsqu'un joueur place un de ses pions à l'extrémité d'un alignement de pions adverses contigus et dont l'autre extrémité est déjà occupée par un de ses propres pions." + '\n' + "Les alignements considérés peuvent être une colonne, une ligne, ou une diagonale. Si le pion nouvellement placé vient fermer plusieurs alignements, il capture tous les pions adverses des lignes ainsi fermées." + "\n" + "La capture se traduit par le retournement des pions capturés. Ces retournements n'entraînent pas d'effet de capture en cascade: seul le pion nouvellement posé est pris en compte" + "\n" + "Les joueurs jouent à tour de rôle, chacun étant tenu de capturer des pions adverses lors de son mouvement. \nLe jeu s'arrête lorsque l'othellier ne compose plus de cases vide ou bien quand plus aucun coup n'est possible par le joueur courant.");
                    break;

                case 2:
                    Jeu.lancerJeu();
                    break;

                case 3:
                  IaJeu.lancerIaJeu();
                    break;

                default:
                    System.out.println("Merci d'avoir jouer !");
                    break;
            }
        } while (choix!=4);
    }
}