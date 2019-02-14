# Mise en place de la sécurité pour une application AngularJs servie par Spring Boot

Nous allons découvrir différentes manières de sécuriser une application web en utilisant spring boot.

Cette application sera du type **Livre d'or**, comportant une page de login et un écran sur lequel nous pourrons consulter les messages rédigés par les différents utilisateurs, mais aussi en ajouter un.
On pourra aussi imaginer un profil Admin permettant de supprimer un commentaire.

Afin de créer un projet Spring Boot vous pouvez utiliser [l'initializer de Spring Boot](https://start.spring.io).
Il vous faudra alors ajouter les dépendances vers web, jpa, et h2. Vous pourrez aussi utiliser lombok, un projet vous permettant d'éviter de produire du code répétitif qui n'a pas de valeur ajoutée (getter, setter, constructors ...).

Je vous propose ici un projet paramétré avec Maven, composé d'une application java et de sources AngularJs pour le front.
Nous utiliserons webpack-dev-server afin de faciliter le developpement. Celui-ci regénère un bundle lorsque les sources sont modifiées.
Vous trouverez dans :
* [src/main/java](./src/main/java) les sources Java
* [src/main/resources/app](./src/main/resources/app) l'application angular
* [src/main/resources/public](./src/main/resources/public) les resources public (html, image)

> Cloner/Télécharger le repo.

```bash
# cloner le repo
$ git clone https://github.com/Serli/spring-security-workshop.git spring-security-workshop

# Se placer dans le répertoire spring-security-workshop
$ cd spring-security-workshop

# Supprimer le versionning git
$ rm -rf .git
```

>Pré-requis
>* [Node](https://nodejs.org/en/)
>* [Intellij](https://www.jetbrains.com/shop/eform/students) (conseillé)
>* [Postman](https://www.getpostman.com) (conseillé) 
>* [Git](https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/) (optionnel) 

# Table des Matières


* [Création de l'application](./step1)
* [Sécuriser avec session et cookie de session](./step2)
* [Sécuriser via token sans session](./step3)
* [Sécuriser A good way](./step4)
