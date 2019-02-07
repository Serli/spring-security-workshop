# Première étape

Le but de cette étape est de mettre en place une application de type Livre d'or, composée d'un écran de connexion, et d'une page de consultation, ajout de commentaire. 

Afin de créer le projet vous pouvez utiliser [l'initializer de Spring Boot](https://start.spring.io).
Il vous faudra alors ajouter les dépendances vers web, jpa, et h2. Vous pourrez aussi utiliser lombok, un projet vous permettant d'éviter de produire du code répétitif qui n'a pas de valeur ajoutée (getter, setter, constructors ...).

Une fois le projet récupéré, nous allons initialiser notre application web développée avec AngularJS. Pour ce faire nous utiliserons un quick starter.
Placez vous à la racine de votre projet.

> Cloner/Télécharger le repo.

```bash
# cloner le repo
$ git clone https://github.com/preboot/angularjs-webpack.git app

# Se placer dans le répertoire app
$ cd app

# Supprimer le versionning git
$ rm -rf .git

# installer les dépendences avec npm
$ npm install


```
Il faut ensuite modifier le fichier de configuration webpack, afin d'éviter les problèmes de Cross-Origin en mode développement lors de l'appel aux services.
On pourra aussi modifié le publicPath afin de spécifier un point d'entrée à notre application.

> Ouvrir le fichier webpack.config.js, puis modifier la partie devServer
    
    config.output = {
        ...
        publicPath: '/livredor/',
        ...
      };

    
    config.devServer = {
        contentBase: './src/public',    
        port:9000,
        proxy: {
          '/api': {
            target: 'http://localhost:8080',
            secure: false
          }
        }
      };

> Vous pourrez ensuite démarrer le serveur webpack

```
# démarrer le serveur
$ npm start
```

Rendez-vous [http://localhost:9000/livredor/](http://localhost:9000/livredor/) dans votre navigateur.

# Table des Matières


* [Démarrer le projet de services](#getting-started)
    * [Création du model](#model)
    * [Création du repository](#repository)
    * [Création du service](#service)
* [License](#license)

# Démarrer le projet de services

## Création du model

* Créer un package model
* Ajouter une classe **User**

        @Data //Annotation lombok qui permet la génération des getter setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Entity
        public class User {
            ...
        }

* Ajouter de même une classe **Comment**

## Création du repository

* Créer un package repository
* Créer un repository par entité
    
    
        public interface UserRepository extends JpaRepository<User, String> {
            ...
        }
## Création du service

* Créer un package service
* Créer un controller par domaine
        
    
        @RestController
        @RequestMapping("/api/user")
        class UserController {
            ...
        }

Le but est maintenat d'implémenter les points d'entrées qui seront utiles à votre application

# License

[Serli](/LICENSE)
