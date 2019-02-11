# Première étape

Le but de cette étape est de mettre en place une application de type Livre d'or, composée d'un écran de connexion, et d'une page de consultation, ajout de commentaire. 


> Initialiser le projet.
>* Démarrer IntelliJ, choisir le projet Step1 dans le répertoire télécharger précédemment. L'ouvrir en tant que projet Maven.
>* Ouvrir un terminal

```bash

# Se placer dans le répertoire step1
$ cd step1

# installer les dépendences avec npm
$ npm install


```

> Vous pourrez ensuite démarrer le serveur webpack

```
# démarrer le serveur
$ npm start
```

Rendez-vous [http://localhost:9000/](http://localhost:9000/) dans votre navigateur.

# Table des Matières


* [Démarrer le projet de services](#getting-started)
    * [Création du model](#model)
    * [Création du repository](#repository)
    * [Création du service](#service)

# Démarrer le projet de services

## Création du model

* Créer un package model
* Ajouter une classe **User**
* Peupler la classe avec les attributs qui vous semblent utiles

        @Data //Annotation lombok qui permet la génération des getter setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Entity
        public class User {
            ...
        }

* Ajouter de même une classe **Comment**

## Création des repository

* Créer un package repository
* Créer un repository par entité
    
    
        public interface UserRepository extends JpaRepository<User, String> {
            ...
        }
        
        
## Création du controller

    
        @RestController
        @RequestMapping("/api/user")
        class UserController {
            ...
        }
Le but est maintenant d'implémenter les points d'entrées qui seront utiles à votre application.
* Permettre à l'utilisateur de se connecter.
* Récupérer les messages postés.
* Ajouter un message.
* Supprimer un message si l'utilisateur est administrateur.

Vous pourrez utiliser Postman pour les tester.

## Définition des vues
 Spring nous permet de définir des points d'entrées à notre application sans définir de controller.
 Il suffit d'implémenter l'interface **WebMvcConfigurer** et d'overrider la méthode **addViewControllers**
 Ainsi on peut définir que lorsqu'on ira sur http://localhost:9000/ alors on affichera login.html.
 Spring boot par default expose les fichiers se situant dans le dossier src/main/resources/public
    
        registry.addViewController("/").setViewName("forward:login.html");
        registry.addViewController("/livredor").setViewName("forward:livredor.html");




Il ne reste qu'a modifié votre application angular afin de faire appel à vos services et de rendre l'application fonctionnel.
