import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JeuTest {

    int[][] premierPlateau =
            {       {2,0,0,0,0,0,0,0},
                    {0,1,0,0,0,0,0,2},
                    {0,0,1,0,0,0,1,0},
                    {0,0,0,1,0,1,0,0},
                    {2,1,1,1,0,1,1,2},
                    {0,0,0,1,0,1,0,0},
                    {0,0,1,0,0,0,1,0},
                    {0,2,0,0,0,0,0,2}
            };
    @Test
    public void coupLegalTest(){
        assertFalse(Jeu.coupLegal(premierPlateau, 0, 7, 2, 1), "cas où le pion posé n'est ni entouré par un pion adverse, ni encadré par lui même, test sur une des 4 extrémités du plateau");
        assertFalse(Jeu.coupLegal(premierPlateau, 7, 0, 1, 2), "cas où le pion posé est bien encadré au moins une fois par un pion advers mais n'est pas encadré par lui même, test sur une des 4 extrémités du plateau");
        assertFalse(Jeu.coupLegal(premierPlateau, 0, 1, 2, 1), "cas où le pion posé est bien entouré au moins une fois par un pion adverse mais n'encadre pas lui-même à l'horizontal, diagonal, vertical");
        assertTrue(Jeu.coupLegal(premierPlateau, 4, 4, 2, 1), "cas où le pion posé est entouré au moins une fois par un pion adverse et est en mêm temps encadré par lui même en horizontal droite et gauche");
    }

    @Test
    public void positionTest() {
        assertFalse(Jeu.position(premierPlateau, 0,0),  "cas où la case sélectionnée est déja prise");
        assertTrue(Jeu.position(premierPlateau, 0,1), "cas où la case sélectionner est vide: soit occupé par un 0");
    }

    @Test
    public void verifHorizontalDroiteTest() {
        assertFalse(Jeu.verifHorizontalDroite(premierPlateau, 0, 7, 1, 2), "cas où le pion n'encadre pas lui même en direction de l'horizontal droite et test sur une des 4 extrémités");
        assertFalse(Jeu.verifHorizontalDroite(premierPlateau, 0, 5, 1, 2), "cas où le pion n'encadre pas lui même en direction de l'horizontal droite et test sur un des bords du plateau");
        assertTrue(Jeu.verifHorizontalDroite(premierPlateau, 4, 4, 2, 1), "cas où le pion est encadré par lui même en direction de l'horizontal droite");
    }

    @Test
    public void verifHorizontalGaucheTest() {
        assertFalse(Jeu.verifHorizontalGauche(premierPlateau, 7, 0, 1, 2), "cas où le pion n'encadre pas lui même en direction de l'horizontal gauche et test sur une des 4 extrémités");
        assertFalse(Jeu.verifHorizontalGauche(premierPlateau, 5, 0, 1, 2), "cas où le pion n'encadre pas lui même en direction de l'horizontal gauche et test sur un des bords du plateau");
        assertTrue(Jeu.verifHorizontalGauche(premierPlateau, 4, 4, 2, 1), "cas où le pion est encadré par lui même en direction de l'horizontal gauche");
    }

    @Test
    public void partiPlusTest() {
        assertFalse(Jeu.finParti(premierPlateau));
        assertEquals(14, Jeu.nombrePionJoueur(premierPlateau, 1));
    }
        int[][] deuxiemePlateau =
                {       {0,2,2,2,0,0,0,0},
                        {0,1,0,0,0,0,0,0},
                        {0,0,1,0,0,0,1,0},
                        {0,0,0,1,2,1,0,0},
                        {0,0,0,1,2,2,2,2},
                        {0,0,0,1,0,1,0,0},
                        {0,0,1,0,0,0,1,0},
                        {0,0,0,0,0,0,0,0}
                };

    @Test
    public void mouvementTest() {
        assertFalse(Jeu.mouvementPossible(deuxiemePlateau, 1, 2));
    }

    @Test
    public void verifVerticalHautTest() {
        assertFalse(Jeu.verifVerticalHaut(deuxiemePlateau, 0, 0, 1, 2), "cas où le pion n'encadre pas lui même en direction de la vertical haut et test sur une des 4 extrémités");
        assertFalse(Jeu.verifVerticalHaut(deuxiemePlateau, 0, 4, 1, 2), "cas où le pion n'encadre pas lui même en direction de la vertical du haut et test sur un des bords du plateau");
        assertTrue(Jeu.verifVerticalHaut(deuxiemePlateau, 2, 1, 2, 1), "cas où le pion est encadré par lui même en direction de la vertical du haut");
    }

    @Test
    public void verifVerticalBasTest() {
        assertFalse(Jeu.verifVerticalBas(deuxiemePlateau, 7, 7, 2, 1), "cas où le pion n'encadre pas lui même en direction de la vertical du bas et test sur une des 4 extrémités plateau");
        assertFalse(Jeu.verifVerticalBas(deuxiemePlateau, 0, 4, 1, 2), "cas où le pion n'encadre pas lui même en direction de la vertical du bas et test sur un des bords du plateau");
        assertTrue(Jeu.verifVerticalBas(deuxiemePlateau, 2, 5, 2, 1), "cas où le pion est encadré par lui même en direction de la vertical du bas");
    }

    @Test
    public void verifDiagonalAscGaucheTest() {
        assertFalse(Jeu.verifDiagonalAscGauche(deuxiemePlateau, 0, 0, 2, 1), "cas où le pion n'encadre pas lui même en direction de la diagonal ascandante gauche du bas et test sur une des 4 extrémités plateau");
        assertFalse(Jeu.verifDiagonalAscGauche(deuxiemePlateau, 0, 5, 2, 1), "cas où  le pion n'encadre pas lui même en direction de la diagonal ascandante gauche et test sur un des bords du plateau");
        assertTrue(Jeu.verifDiagonalAscGauche(deuxiemePlateau, 7, 7, 2, 1), "cas où le pion est encadré par lui même en direction de la diagonal ascandante gauche");
    }

    @Test
    public void verifDiagonalAscDroiteTest() {
        assertFalse(Jeu.verifDiagonalAscDroite(deuxiemePlateau, 0, 7, 2, 1), "cas où le pion n'encadre pas lui même en direction de la diagonal ascandante droite et test sur une des 4 extrémités plateau");
        assertFalse(Jeu.verifDiagonalAscGauche(deuxiemePlateau, 0, 5, 2, 1), "cas où  le pion n'encadre pas lui même en direction de la diagonal ascandante droite et test sur un des bords du plateau");
        assertTrue(Jeu.verifDiagonalAscDroite(deuxiemePlateau, 7, 1, 2, 1), "cas où le pion est encadré par lui même en direction de la diagonal ascandante droite");
    }

    @Test
    public void verifDiagonalDescGaucheTest() {
        assertFalse(Jeu.verifDiagonalDescGauche(deuxiemePlateau, 0, 0, 1, 2), "cas où le pion n'encadre pas lui même en direction de la diagonal descandante gauche et test sur une des 4 extrémités");
        assertFalse(Jeu.verifDiagonalDescGauche(deuxiemePlateau, 3, 0, 2, 1), "cas où  le pion n'encadre pas lui même en direction de la diagonal descandante gauche et test sur un des bords du plateau");
        assertTrue(Jeu.verifDiagonalDescGauche(deuxiemePlateau, 1, 7, 2, 1), "cas où le pion est encadré par lui même en direction de la diagonal descandante gauche");
    }

    @Test
    public void verifDiagonalDescDroiteTest() {
        assertFalse(Jeu.verifDiagonalDescDroite(deuxiemePlateau, 2, 7, 1, 2), "cas où  le pion n'encadre pas lui même en direction de la diagonal descandante droite et test sur un des bords du plateau");
        assertTrue(Jeu.verifDiagonalDescDroite(deuxiemePlateau, 0, 0, 2, 1), "cas où le pion est encadré par lui même en direction de la diagonal descandante droite et test sur une des 4 extrémité");
    }

    @Test
    final void JeuFinPartiTest() {
        int[][] plateauRempli =
                {       {2,2,2,2,1,1,2,1},
                        {2,2,2,2,1,1,2,1},
                        {1,2,1,1,1,2,1,1},
                        {1,1,2,1,2,1,2,2},
                        {1,1,1,1,2,2,2,2},
                        {1,1,1,1,2,2,2,2},
                        {2,2,1,1,1,1,2,2},
                        {2,2,2,1,1,1,2,1}
                };

        assertTrue(Jeu.mouvementPossible(plateauRempli, 1,2));
        assertTrue(Jeu.mouvementPossible(plateauRempli,2,1));
        assertTrue(Jeu.finParti(plateauRempli), "cas où tout le plateau est repmli");
        assertEquals(-1, Jeu.gagnant(plateauRempli));
        assertEquals(32, Jeu.nombrePionJoueur(plateauRempli,2));

        int[][] plateauAucunMouvementPossible =
                {       {1,0,0,1,1,1,2,1},
                        {1,0,1,1,1,1,1,1},
                        {1,1,1,1,1,2,1,1},
                        {1,1,2,1,2,1,2,2},
                        {1,1,2,1,2,2,2,2},
                        {1,1,1,1,2,2,2,2},
                        {2,2,1,1,1,1,2,2},
                        {2,2,2,1,1,1,2,1}
                };

        assertTrue(Jeu.mouvementPossible(plateauAucunMouvementPossible, 1,2), "cas où c'est le tour du joueur 1 mais il n'y a plus de coup legal possible");
        assertEquals(1,Jeu.gagnant(plateauAucunMouvementPossible));
    }

}
