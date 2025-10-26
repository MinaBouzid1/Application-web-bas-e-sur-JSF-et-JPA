# 🛒 E-Commerce Application - JSF & JPA

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue.svg)](https://jakarta.ee/)
[![JSF](https://img.shields.io/badge/JSF-4.0-green.svg)](https://jakarta.ee/specifications/faces/)
[![JPA](https://img.shields.io/badge/JPA-3.0-red.svg)](https://jakarta.ee/specifications/persistence/)
[![PrimeFaces](https://img.shields.io/badge/PrimeFaces-13.0-yellow.svg)](https://www.primefaces.org/)
[![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)](LICENSE)

> 💡 Application web e-commerce  développée avec JSF, JPA, Hibernate et PrimeFaces


## 🎯 À propos

** application web e-commerce moderne développée avec les technologies Jakarta EE. Elle permet aux utilisateurs de parcourir un catalogue de produits, gérer leur panier et passer des commandes en ligne.

### 🎓 Contexte du Projet
Ce projet a été réalisé dans le cadre d'un atelier pratique pour maîtriser :
- L'API JPA (Java Persistence API)
- Le Framework JSF (Jakarta Server Faces)
- L'architecture MVC (Model-View-Controller)
- La création d'interfaces riches avec PrimeFaces

---

## ✨ Fonctionnalités

### 👤 Gestion des Utilisateurs
- ✅ Inscription avec validation email unique
- ✅ Connexion sécurisée
- ✅ Gestion du profil utilisateur
- ✅ Déconnexion avec invalidation de session

### 🛍️ Catalogue & Produits
- ✅ Affichage des produits avec images
- ✅ Filtrage par catégorie dynamique
- ✅ Pagination automatique (12 produits/page)
- ✅ Badges de disponibilité en stock
- ✅ Descriptions détaillées des produits

### 🛒 Panier d'Achat
- ✅ Ajout/suppression de produits
- ✅ Modification des quantités avec spinner
- ✅ Validation du stock en temps réel
- ✅ Calcul automatique des sous-totaux et total
- ✅ Compteur d'articles dans la navigation

### 📦 Gestion des Commandes
- ✅ Validation du panier en commande
- ✅ Historique complet des commandes
- ✅ Affichage détaillé de chaque commande
- ✅ Annulation possible (statut EN_COURS)
- ✅ Suivi du statut : `EN_COURS` → `VALIDÉE` → `LIVRÉE` / `ANNULÉE`

### ⚙️ Administration
- ✅ CRUD complet des catégories
- ✅ CRUD complet des produits
- ✅ Interface intuitive avec DataTables
- ✅ Filtres et tri sur les colonnes
- ✅ Confirmations avant suppression
- ✅ Formulaires de saisie validés

---

## 🛠️ Technologies

### Backend
| Technologie | Version | Description |
|-------------|---------|-------------|
| **Java** | 17 | Langage de programmation |
| **Jakarta EE** | 10.0.0 | Plateforme d'entreprise Java |
| **JSF (Mojarra)** | 4.0.0 | Framework web MVC |
| **JPA** | 3.0 | API de persistance |
| **Hibernate** | 6.2.7 | Implémentation JPA/ORM |
| **CDI (Weld)** | 5.1.0 | Injection de dépendances |

### Frontend
| Technologie | Version | Description |
|-------------|---------|-------------|
| **PrimeFaces** | 13.0.0 | Composants UI riches |
| **XHTML** | - | Templates Facelets |
| **CSS3** | - | Styles personnalisés |
| **JavaScript** | ES6 | Interactions côté client |

### Base de Données
| Technologie | Version | Description |
|-------------|---------|-------------|
| **MySQL** | 8.0+ | SGBD relationnel |
| **MySQL Connector/J** | 8.0.33 | Driver JDBC |

### Serveur & Build
| Technologie | Version | Description |
|-------------|---------|-------------|
| **WildFly** | 27+ | Serveur d'application |
| **Maven** | 3.6+ | Gestionnaire de build |

