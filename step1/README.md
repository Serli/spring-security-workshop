# Première étape

Le but de cette étape est de mettre en place une application de type Livre d'or, composée d'un écran de connexion, et d'une page de consultation, ajout de commentaire. 

Afin de créer un projet Spring Boot vous pouvez utiliser [l'initializer de Spring Boot](https://start.spring.io).
Il vous faudra alors ajouter les dépendances vers web, jpa, et h2. Vous pourrez aussi utiliser lombok, un projet vous permettant d'éviter de produire du code répétitif qui n'a pas de valeur ajoutée (getter, setter, constructors ...).


> Cloner/Télécharger le repo.

```bash
# cloner le repo
$ git clone https://github.com/Serli/spring-security-workshop.git spring-security-workshop

# Se placer dans le répertoire spring-security-workshop
$ cd spring-security-workshop

# Supprimer le versionning git
$ rm -rf .git

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