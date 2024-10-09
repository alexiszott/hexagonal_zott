package org.iut.mastermind.domain.partie;

import org.iut.mastermind.domain.Mastermind;
import org.iut.mastermind.domain.proposition.Lettre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test d'une partie:")
public class TestPartie {
    private static final Joueur JOUEUR = new Joueur("Alice");
    private static final String MOT_CORRECT = "SOLID";
    private static final String MOT_INCORRECT = "DXXXX";
    @Mock
    private PartieRepository partieRepository;
    @InjectMocks
    private Mastermind mastermind;

    @Test
    @DisplayName("retourne le résultat du mot proposé")
    void doitRenvoyerResultatPourLaProposition() {
        givenPartieEnregistree(Partie.create(JOUEUR, MOT_CORRECT));
        ResultatPartie res = mastermind.evaluation(JOUEUR, MOT_INCORRECT);
        Lettre premiereLettre = res.resultat().lettre(0);
        assertThat(premiereLettre).isEqualTo(Lettre.NON_PLACEE);
    }

    @Test
    @DisplayName("met à jour le nombre d'essais")
    void doitMettreAJourNombreEssais() {
        givenPartieEnregistree(Partie.create(JOUEUR, MOT_CORRECT));
        mastermind.evaluation(JOUEUR, MOT_INCORRECT);
        var partie = getPartieMAJDansRepository();
        assertThat(partie.getNbEssais()).isEqualTo(1);
    }

    @Test
    @DisplayName("termine la partie quand le nombre d'essais est dépassé")
    void doitTerminerLaPartieQuandNombreEssaisDepasse(){
        int nbEssaiMax = 5;
        givenPartieEnregistree(Partie.create(JOUEUR, MOT_CORRECT, nbEssaiMax-1));
        ResultatPartie result = mastermind.evaluation(JOUEUR, MOT_INCORRECT);
        assertThat(result.isTermine()).isTrue();
    }

    @Test
    @DisplayName("lève une erreur quand la partie est terminée")
    void doitLeverErreurQuandJeuTermine(){
        var partie = Partie.create(JOUEUR, MOT_CORRECT);
        partie.done();
        givenPartieEnregistree( partie );
        ResultatPartie result = mastermind.evaluation(JOUEUR, MOT_INCORRECT);
        assertThat(result.isError()).isTrue();
    }

    @Test
    @DisplayName("enregistre la partie quand elle est victorieuse")
    void doitEnregistrerPartieQuandVictoire(){
        givenPartieEnregistree(Partie.create(JOUEUR, MOT_CORRECT));
        mastermind.evaluation(JOUEUR, MOT_CORRECT);
        Partie partie = getPartieMAJDansRepository();
        assertThat(partie.isTerminee()).isTrue();
    }

    private Partie getPartieMAJDansRepository() {
        ArgumentCaptor<Partie> argument = ArgumentCaptor.forClass(Partie.class);
        verify(partieRepository).update(argument.capture());
        return argument.getValue();
    }

    private void givenPartieEnregistree(Partie partie) {
        when(partieRepository.getPartieEnregistree(eq(JOUEUR)))
                .thenReturn(Optional.of(partie));
    }
}
