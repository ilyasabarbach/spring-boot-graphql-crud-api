# Spring Boot & GraphQL CRUD API (Produits & Catégories)

Ce projet est une API back-end complète développée avec **Spring Boot** et **GraphQL**.  
Il implémente des opérations CRUD (Create, Read, Update, Delete) pour la gestion de **Produits** et **Catégories**, 
offrant une alternative moderne et efficace aux API REST traditionnelles.

---

## Fonctionnalités

- **Endpoint unique `/graphql`** : toutes les opérations passent par un seul point d’entrée.
- **Schéma fortement typé** : contrat d’API clair et auto-documenté via `schema.graphqls`.
- **Opérations CRUD complètes** pour les entités `Produit` et `Categorie`.
- **Relations de données** : gestion de la relation `ManyToOne` entre `Produit` et `Categorie`.
- **Pas d’over-fetching / under-fetching** : le client récupère uniquement les données nécessaires.
- **Interface de test intégrée** : accès à GraphiQL pour exécuter et tester les requêtes en direct.

---

## Stack Technique

- **Java (Spring Boot)** — Framework principal du back-end.
  - `spring-boot-starter-graphql` : intégration de GraphQL.
  - `spring-boot-starter-data-jpa` : persistance et interaction avec la base de données.
- **GraphQL** — Langage de requête d’API.
- **MySQL** — Base de données relationnelle.
- **Maven** — Gestionnaire de dépendances.

---

## Démarrage Rapide

### 1. Prérequis

- JDK (Java Development Kit)
- Maven
- Serveur MySQL actif

### 2. Configuration du projet

#### a. Cloner le dépôt
```bash
git clone https://github.com/votre-nom/votre-repo.git
cd votre-repo
```

#### b. Créer la base de données
```sql
CREATE DATABASE graphql_db;
```

#### c. Configurer la connexion MySQL
Ouvrez le fichier `src/main/resources/application.properties` et ajustez les paramètres :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/graphql_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=votre_user
spring.datasource.password=votre_mot_de_passe

spring.jpa.hibernate.ddl-auto=update
```

### 3. Lancer l’application

Exécutez la commande :
```bash
mvn spring-boot:run
```

L’application sera disponible sur :  
 **http://localhost:8080**

### 4. Interface GraphiQL

Pour tester les requêtes :  
Accédez à **http://localhost:8080/graphiql**

---

## Exemples de Requêtes GraphQL

### Mutations (Écriture de données)

#### Ajouter une catégorie
```graphql
mutation {
  ajouterCategorie(input: { nom: "Informatique" }) {
    id
    nom
  }
}
```

#### Ajouter un produit
```graphql
mutation {
  ajouterProduit(input: { nom: "PC Dell", prix: 9500, categorieId: 1 }) {
    id
    nom
    prix
    categorie { nom }
  }
}
```

#### Modifier un produit
```graphql
mutation {
  modifierProduit(id: 1, input: { nom: "PC HP", prix: 8900, categorieId: 1 }) {
    id
    nom
    prix
  }
}
```

#### Supprimer un produit
```graphql
mutation {
  supprimerProduit(id: 1)
}
```

---

### Queries (Lecture de données)

#### Lister tous les produits et leurs catégories
```graphql
query {
  produits {
    id
    nom
    prix
    categorie { nom }
  }
}
```

#### Lister toutes les catégories et leurs produits
```graphql
query {
  categories {
    id
    nom
    produits {
      id
      nom
    }
  }
}
```

#### Récupérer un produit par ID
```graphql
query {
  produit(id: 1) {
    id
    nom
    categorie { nom }
  }
}
```
