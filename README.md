# Application Android de géolocalisation avec OpenStreetMap

## Informations générales

* **Réalisé par :** Ibtissam EL BEKKALI
* **Projet :** MapApplication
* **Langage :** Java
* **Minimum SDK :** API 24
* **Target SDK :** API 34
* **Carte utilisée :** OpenStreetMap avec OSMDroid
* **Communication réseau :** Volley
* **Backend :** PHP et MySQL
* **Environnement :** Android Studio, Android Emulator et XAMPP/WAMP

## Objectif

L’objectif de ce laboratoire était de développer une application Android capable de :

* récupérer la position GPS de l’appareil ;
* envoyer la latitude, la longitude, la date et l’identifiant Android vers un serveur local ;
* enregistrer les positions dans une base de données MySQL ;
* récupérer les positions enregistrées ;
* les afficher sous forme de marqueurs sur une carte OpenStreetMap.

## Fonctionnement

L’application contient deux activités principales :

* `MainActivity`, responsable des permissions, de la récupération GPS et de l’envoi des positions ;
* `GoogleMapActivity`, responsable de l’affichage de la carte et des marqueurs.

Le bouton **Afficher La Map** permet d’ouvrir la carte.

L’adresse suivante est utilisée depuis l’émulateur pour communiquer avec le serveur local :

```text
10.0.2.2
```

Les données sont envoyées vers :

```text
http://10.0.2.2/map_project/createPosition.php
```

et récupérées depuis :

```text
http://10.0.2.2/map_project/getPosition.php
```

## Permissions utilisées

L’application demande les permissions suivantes :

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

Les permissions de localisation sont également demandées dynamiquement pendant l’exécution.

## Base de données

La base de données utilisée est :

```text
map_project
```

La table `positions` contient :

* un identifiant ;
* la latitude ;
* la longitude ;
* la date ;
* l’identifiant Android de l’appareil.

L’identifiant utilisé est `ANDROID_ID`, qui est plus adapté que l’IMEI pour ce projet pédagogique.

## Technologies utilisées

| Technologie | Utilisation             |
| ----------- | ----------------------- |
| Java        | Développement Android   |
| OSMDroid    | Affichage OpenStreetMap |
| Volley      | Requêtes HTTP           |
| PHP         | API backend             |
| MySQL       | Stockage des positions  |
| JSON        | Échange des données     |
| XAMPP/WAMP  | Serveur local           |

## Sécurité et limites

Le projet autorise temporairement le trafic HTTP vers `10.0.2.2` pour le développement local.

Dans une application de production, il faudrait :

* utiliser HTTPS ;
* ajouter une authentification ;
* valider strictement les données côté serveur ;
* limiter la collecte des positions ;
* informer l’utilisateur et demander son consentement ;
* permettre la suppression des données ;
* arrêter les mises à jour GPS lorsque l’application n’en a plus besoin.

## Résultat

L’application permet de récupérer une position GPS, de l’enregistrer dans MySQL, puis d’afficher les positions enregistrées sur une carte OpenStreetMap avec des marqueurs personnalisés.

## Conclusion

Ce laboratoire m’a permis de comprendre :

* la gestion des permissions Android ;
* l’utilisation du GPS ;
* les requêtes réseau avec Volley ;
* l’intégration d’OpenStreetMap ;
* la communication entre Android, PHP et MySQL ;
* la manipulation de réponses JSON.

**Projet réalisé par Ibtissam EL BEKKALI.**
