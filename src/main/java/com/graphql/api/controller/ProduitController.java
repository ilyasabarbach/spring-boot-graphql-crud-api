package com.graphql.api.controller;

import com.graphql.api.model.Categorie;
import com.graphql.api.model.Produit;
import com.graphql.api.repository.CategorieRepository;
import com.graphql.api.repository.ProduitRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProduitController {
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository; // (1) Besoin des deux

    // (2) Spring injecte les deux repositories
    public ProduitController(ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    @QueryMapping
    public List<Produit> produits() {
        return produitRepository.findAll();
    }

    @QueryMapping
    public Produit produit(@Argument Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Produit ajouterProduit(@Argument ProduitInput input) { // (3)
        // (4) On doit d'abord récupérer la Categorie via son ID
        Categorie categorie = categorieRepository.findById(input.categorieId()).orElse(null);
        Produit produit = new Produit(input.nom(), input.prix(), categorie);
        return produitRepository.save(produit);
    }

    @MutationMapping
    public Produit modifierProduit(@Argument Long id, @Argument ProduitInput input) {
        Produit produit = produitRepository.findById(id).orElseThrow(); // (5)
        produit.setNom(input.nom());
        produit.setPrix(input.prix());
        Categorie categorie = categorieRepository.findById(input.categorieId()).orElse(null);
        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }

    // ... supprimerProduit ...

    // (6) Le record pour l'Input Produit
    public record ProduitInput(String nom, double prix, Long categorieId) {}
}