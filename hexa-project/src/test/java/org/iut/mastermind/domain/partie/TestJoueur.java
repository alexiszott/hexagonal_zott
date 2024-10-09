package org.iut.mastermind.domain.partie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test joueur pour:")
class TestJoueur {

    @Test
    @DisplayName("des joueurs de noms identiques")
    void joueursNomsIdentiques() {
        Joueur a = new Joueur("meme nom");
        Joueur b = new Joueur("meme nom");
        assertThat(a).isEqualTo(b);
    }

    @Test
    @DisplayName("des joueurs de noms différents")
    void joueursNomsDifferents() {
        Joueur a = new Joueur("meme nom");
        Joueur b = new Joueur("nom different");
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    @DisplayName("des joueurs avec un hashcode égal")
    void joueursHashcodeEgal() {
        Joueur a = new Joueur("meme nom");
        Joueur b = new Joueur("meme nom");
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }
}