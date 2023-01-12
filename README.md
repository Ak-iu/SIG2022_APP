# SIG2022_APP

Membres du groupe : PETRAGALLO Tom, MULUMBA Evan  
Explication de l'architecture du projet :
- base de données postGIS sous docker remplie grâce à qgis par les données provenant du site data.orleans-metropole.fr.
- Geoserver connecté à postGIS qui met à disposition 2 couches : les points d'apports volontaires (pav) et les mobiliers et aménagements.
- une API réalisée grâce à SpringBoot qui permet de gérer les dégradations et les suggestions.
- Cette application Android. La partie service est accessible dans cette version. Pour les utilisateurs ordinaires il suffit de supprimer le fragment service.
- Les pages webs avec les cartes utilisent openlayers (version 7.1.0). Elles sont situées dans app/src/main/assets.  

Pour faire fonctionner l'application, il faut modifier l'addresse de l'api écrite dans la classe Api dans le dossier app/src/main/java/com/example/sig2022_app/tasks.
Il faut aussi définir dans map_view.html et map_view_degradations.html l'addesse permettant de se connecter à Geoserver.  
