DROP DATABASE db_projectCiph;
CREATE DATABASE db_projectCiph;
use db_projectCiph;

CREATE TABLE IF NOT EXISTS tbl_users(id INTEGER PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS tbl_cipher(id INTEGER PRIMARY KEY AUTO_INCREMENT, encryptedTxt VARCHAR(255) NOT NULL, shiftNo INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS tbl_decipher(id INTEGER PRIMARY KEY AUTO_INCREMENT, decryptedTxt VARCHAR(255) NOT NULL, shiftNo INTEGER NOT NULL);