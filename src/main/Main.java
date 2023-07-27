package main;

import dao.ClasseImpl;
import dao.EtudiantImpl;
import dao.IClasse;
import dao.IEtudiant;
import entity.Classe;
import entity.Etudiant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IClasse classeDao = new ClasseImpl();
        IEtudiant etuDao = new EtudiantImpl();
        /*Scanner scanner = new Scanner(System.in);
        Classe cl = new Classe();
        System.out.println("Donner le nom de la classe");
        String nomClasse = scanner.nextLine();
        cl.setNom(nomClasse);
        int ok = classeDao.add(cl);
        if(ok == 1)
            System.out.println("Classe enregistree avec succés !");
        else
            System.out.println("Echec !");*/

        /*int ok = classeDao.delete(4);
        if(ok == 1)
            System.out.println("Classe supprimee avec succés !");
        else
            System.out.println("Echec !");

        System.out.println("Liste des classes");
        for (Classe cl : classeDao.list()) {
            System.out.println("ID = " + cl.getId());
            System.out.println("Nom = " + cl.getNom().toUpperCase());
            System.out.println("Effectif = " + cl.getEffectif());
        }*/

        /*Classe cl = classeDao.get(4);
        cl.setNom("DITI3");
        int ok = classeDao.update(cl);
        if(ok == 1)
            System.out.println("Classe modifiee avec succés !");
        else
            System.out.println("Echec !");*/

        /*Etudiant et = new Etudiant("FALL","Djibril",18.5, classeDao.get(1));
        int ok = etuDao.add(et);
        if(ok == 1)
            System.out.println("Etudiant enregistre avec succés !");
        else
            System.out.println("Echec !");*/

        int ok = etuDao.delete(6);
        if(ok == 1)
            System.out.println("Etudiant supprimé avec succés !");
        else
            System.out.println("Echec !");

        System.out.println("Liste des etudiants");
        for (Etudiant e : etuDao.list()) {
            System.out.println("ID = " + e.getId());
            System.out.println("Nom = " + e.getNom().toUpperCase());
            System.out.println("Prenom = " + e.getPrenom());
            System.out.println("Matricule = " + e.getMatricule());
            System.out.println("Moyenne = " + e.getMoyenne());
            System.out.println("Classe = " + e.getClasse().getNom());
        }
    }
}