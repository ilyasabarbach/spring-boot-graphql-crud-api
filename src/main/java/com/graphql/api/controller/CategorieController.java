package com.graphql.api.controller;

import com.graphql.api.model.Categorie;
import com.graphql.api.repository.CategorieRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller // (2)
public class CategorieController {
    private final CategorieRepository categorieRepository; // (3)

    // (4) Injection de dépendance
    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @QueryMapping // (5)
    public List<Categorie> categories() {
        return categorieRepository.findAll(); // (6)
    }

    @QueryMapping // (7)
    public Categorie categorie(@Argument Long id) { // (8)
        return categorieRepository.findById(id).orElse(null);
    }

    @MutationMapping // (9)
    public Categorie ajouterCategorie(@Argument CategorieInput input) { // (10)
        Categorie categorie = new Categorie(input.nom());
        return categorieRepository.save(categorie);
    }

    @MutationMapping
    public Boolean supprimerCategorie(@Argument Long id) {
        categorieRepository.deleteById(id);
        return true;
    }

    // (11) "Record" Java pour l'Input
    public record CategorieInput(String nom) {}
}