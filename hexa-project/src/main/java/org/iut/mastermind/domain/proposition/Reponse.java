package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private ArrayList<Character> motSecretLettres = new ArrayList<Character>();

    public Reponse(String mot) {
        this.motSecret = mot;
        for (char c : motSecret.toCharArray()) {
            motSecretLettres.add(c);
        }
    }

    // on récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        for (char c : essai.toCharArray()) {
            resultat.add(evaluationCaractere(c));
        }
    }

    // si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        boolean toutesPlacees = true;
        for (Lettre l : resultat) {
            if (!l.equals(Lettre.PLACEE)) {
                toutesPlacees = false;
                break;
            }
        }
        return toutesPlacees;
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // renvoie le statut du caractère
    private Lettre evaluationCaractere(char carCourant) {
        if (estPresent(carCourant)) {
            if (estPlace(carCourant)) {
                return Lettre.PLACEE;
            } else {
                return Lettre.NON_PLACEE;
            }
        } else {
            return Lettre.INCORRECTE;
        }
    }

    // le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        boolean estPresent = false;
        for (char c : motSecret.toCharArray()) {
            if (carCourant == c) {
                estPresent = true;
                break;
            }
        }
        return estPresent;
    }

    // le caractère est placé dans le mot secret
    private boolean estPlace(char carCourant) {
        boolean estPlacee = carCourant == motSecretLettres.getFirst();
        motSecretLettres.removeFirst();
        return estPlacee;
    }
}
