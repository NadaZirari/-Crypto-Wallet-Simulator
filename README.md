# Wallet Crypto – Java 8 (Console) – PostgreSQL

📌 Contexte
Application console en Java 8 qui simule un wallet crypto avec gestion du mempool et calcul des frais (fees).

L’objectif est d’illustrer les concepts de transactions, priorités (ÉCONOMIQUE / STANDARD / RAPIDE), et leur positionnement dans le mempool, tout en persistant les données dans PostgreSQL.

✨ Fonctionnalités

 Création de wallet (BITCOIN ou ETHEREUM) avec adresse générée automatiquement et persistance en base.
 
 Création de transaction avec calcul des frais selon le type de crypto et la priorité (PENDING).
 
 Simulation du mempool : transactions aléatoires + calcul de la position par ordre décroissant des fees + estimation du temps (position × 10 min).
 
 Comparaison des frais : tableau console (fees, position, temps estimé) pour les 3 niveaux de priorité.
 
 Consultation DB : lister les wallets et lister les transactions par wallet.
 
# Architecture & principes

Java 8 (sans Maven)

Couches :

Présentation (console)
Services
Repositories 
Domaine
Utilitaires
Base de données (Singleton)

# Principes SOLID & Patterns :

📋 Prérequis
 JDK 8
 PostgreSQL (base crypto)
 Pilote JDBC PostgreSQL → lib/postgresql-<version>.jar
 Schéma de base de données
 Fichier : shema.sql
Tables : wallets, transactions
Index : wallet_id, status, priority

⚙️ Installation

Cloner/copier le projet

Créer la base et exécuter Wallet_Crypto.sql dans PostgreSQL (psql ou pgAdmin).

Placer le pilote JDBC dans lib/

lib/postgresql-42.7.4.jar

🏗️ Compilation (Windows, sans Maven)

bashcmd /c "if not exist out mkdir out && dir /s /b src\*.java > sources.txt && javac -source 1.8 -target 1.8 -encoding UTF-8 -cp lib\postgresql-42.7.4.jar -d out @sources.txt" bash

▶️ Exécution
PowerShell

$env:JDBC_URL = "jdbc:postgresql://localhost:5432/crypto_wallet" $env:JDBC_USER = "postgres" $env:JDBC_PASSWORD = "password" # ou "" si pas de mot de passe $env:JDBC_DRIVER = "org.postgresql.Driver"

java -cp "out;lib\postgresql-42.7.4.jar" com.crypto.app.Main

🖥️ Utilisation (Menu console)
1.Créer un wallet 2.Créer une nouvelle transaction 3.Voir les transactions d'un wallet 4.Comparer les niveaux de frais 5.Consulter l'état du mempool 6.Consulter Votre Position en meempol 7.Modifier Votre Balance 0.Quitter

📸 Aperçu
<img width="416" height="197" alt="Capture d'écran 2025-10-02 162229" src="https://github.com/user-attachments/assets/f81c77a7-1020-44ff-96db-f3529e92ec89" />
