# Quatrième étape

Dans cette étape nous allons mettre en place notre propre *session* utilisateur. Nous allons en fait simplement créer une table dans laquelle nous renseignerons un id de session, un id de User et une date d'expiration. Ainsi à chaque requête nous vérifierons via un cookie que l'id de session existe et est encore valide.

Nous allons aussi mettre en place une méthode afin de nous protéger contre les attaques CSRF.

## Mise en place

### Contexte de sécurité
* Le but ici est de mettre en place notre propre session d'authentification. 
    * Créer une entité qui sera composé d'un id généré alèatoirement (vous pourrez utiliser la classe UUID), d'un id de user, et d'une date d'expiration ou une durée...
    * Créer le repository associé.
    * Nous pouvons désormais modifié la méthode de login afin de peupler notre session utilisateur. Pour cette étape nous n'utiliserons pas le formLogin tel que nous l'avons vu dans les étapes précédentes, bien qu'il eu été possible de le faire.
        * Définir un endpoint login, vérifiant l'authentification
        
                 final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
                 );
                 
        * Si l'utilsateur est authentifié, le mettre dans le context de sécurité
        
                SecurityContextHolder.getContext().setAuthentication(authentication);
         
        * Créer ensuite un objet tel que définit ci-dessus et le sauvegarder. Vous pourrez ensuite récupérer l'id de cet objet et le fournir à votre application sous forme de cookie
        
                Cookie tokenCookie = new Cookie(authTokenCookieName, token.getToken());
                tokenCookie.setPath("/");
                tokenCookie.setHttpOnly(true);
                tokenCookie.setMaxAge(expiredTime);
                response.addCookie(tokenCookie);
                
    * Définir le point d'entrée logout, vous pourrez vous inspirer de l'étape 2. Il faudra  ajouter un handler afin de nettoyer notre "Session"
    
            .logoutSuccessHandler(getLogoutSuccessHandler())
            
        * Le success handler doit vider la base de données des sessions concernant l'utilisateur qui se deconnecte.
        * il faudra aussi penser à nettoyer les cookies.
        
    * Ajouter un intercepteur avant **UsernamePasswordAuthenticationFilter** de tel sorte à ce que le cookie soit vérifier.
         
    * Côté front il n'y a pas grand chose à faire dans la mesure ou le token est stocké dans les cookies, il sera transporté à chaque requête.
    
### Gestion CSRF
* Spring possède une fonctionnalité intégrée afin de gérer les CSRF, dans les étapes précédentes nous l'avons omise.
Associé à angularjs celle ci fonctionne assez intuitivement. Cependant elle est basée sur le fait que le cookies CSRF et lisible par le javascript. (httpOnly = false)
Ainsi l'application Web avant de poster une requête renseigne un Header portant la meme valeur que notre Cookie.
Et permet ainsi à Spring d'intercepter la requête et de vérifier que le header et le cookie sont identique. Et alors d'autoriser la requête.

        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            
* Il n'y a rien à faire côté front, vous pourrez juste constater que chaque requête sortante, envoie un header nommé **X-XSRF-TOKEN** avec comme valeur la même valeur que celle co cookie **XSRF-TOKEN**
