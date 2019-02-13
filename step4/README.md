# Quatrième étape

Dans cette étape nous allons mettre en place notre propre *session* utilisateur. Nous allons en fait simplement créer une table dans laquelle nous renseignerons un id de session, un id de User et une date d'expiration. Ainsi à chaque requête nous vérifierons via un cookie que l'id de session existe et est encore valide.
