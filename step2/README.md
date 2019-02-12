# Deuxième étape

Nous allons désormais intégrer Spring security à notre projet de service. Spring nous offre la possibilité de sécurisé notre application rapidement.
Pour ce faire nous étendrons la classe **WebSecurityConfigurerAdapter**. Celle-ci nous permettra de configurer les accès et les régles de sécurité.

## Mise en place de la sécurité

* Dans un premier temps nous allons dire à Spring comment lire un utilisateur depuis son login
    * Créer un Service Spring qui implémente **UserDetailsService**
    * Implémenter la méthode **loadUserByUsername**, celle-ci doit lire un utilisateur dans notre base de données.
    * Cette méthode renvoie un Objet de type UserDetails, il faudra donc que notre model implémente de cet Objet.
    
            public class User implements UserDetails {

    
    
* Il faut ensuite dire à Spring comment s'authentifier
    * Créer ensuite une classe héritant de **DaoAuthenticationProvider**
    * implémenter la méthode **authenticate**. Cette méthode devra vérifier que je jeton d'authentification est valide, et que celui correspond à notre utilisateur.
    En d'autres termes que les login et mot de passe sont correct.
    * Créer un **bean** associé
             
            /**
            * Création d'un bean injectable par Spring
            **/ 
            @Bean
            public AuthenticationProvider getAuthProvider() {
                //Le provider créé ci-dessus celui-ci prend en paramêtre un encoder
                //qui permettra de decoder le mot de passe.
                AppAuthProvider provider = new AppAuthProvider(bCryptPasswordEncoder());
                
                // On informe notre provider du seervice à utiliser pour lire un utilisateur
                // Le service créé juste au-dessus
                provider.setUserDetailsService(userDetailsService);
                return provider;
            }
    
* Nous pouvons maintenant configurer le projet.
    * Créer une classe héritant **WebSecurityConfigurerAdapter**. Il faudra alors implémenter la méthode **configure**.
    Le but de cette méthode est de définir les règles de sécurité à mettre en place
        
            http
                .authorizeRequests()
                    // on autorise l'accés à la suppression seulement aux utilisateur
                    // ayant l'autorité "WRITE_ACCESS"
                    .antMatchers(HttpMethod.DELETE, "/api/comments").hasAuthority("WRITE_ACCESS")
                    // on autorise l'accés au path "/" sans authentification
                    .antMatchers("/").permitAll() 
                    // pour tous les autres point d'entrée, l'authentification est requise
                    .anyRequest().authenticated() 
                .and()
                // On dit à Spring quel provider il devra utiliser
                // celui que l'on aura créé au dessus.
                .authenticationProvider(getAuthProvider())
                
                //On définit maintenant nos régle de login
                .formLogin()
                    // La page de login, vous devrez spécifier que "/" renvoie vers login.html cf Step 1
                    .loginPage("/")
                    // url de l'api à appeler pour ce logger (Method : Post, FormData (username, password))
                    .loginProcessingUrl("/login")
                    // url atteinte si la connexion à fonctionné
                    .defaultSuccessUrl("/livredor")
                    // url atteinte en cas de problème de connexion
                    .failureUrl("/#!/login/error")
                    
                //Puis les règle de déconnexion
                .logout()
                    // url de l'api à appeler pour se déconnecter
                    .logoutUrl("/api/user/logout")
                    // url atteinte en cas de déconnexion réussie
                    .logoutSuccessUrl("/")
                    // on invalide la session Http
                    .invalidateHttpSession(true)
                    // on supprime le cookie généré par Spring
                    .deleteCookies("JSESSIONID")
                    
    * Cela suffit Afin de faire une gestion de la sécurité par session. Ainsi Spring créé une map de Session dont l'id correspond à la valeur du JSESSIONID, et la valeur est un ensemble de données dont l'utilisateur courant,
que l'on peut récupérer.

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                    


* Coté front le cookie sera transféré à chaque requête, ainsi il vous sera possible de savoir à tout moment quel est l'utilisateur connecté. Il vous faudra pour cela créer un mapping supplémentaire vous permettant de récupérer l'utilisateur courant.