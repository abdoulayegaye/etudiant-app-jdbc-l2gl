package dao;

import entity.Classe;
import entity.Etudiant;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EtudiantImpl implements IEtudiant{

    private DB db = new DB();
    private ResultSet rs;
    private int ok;

    @Override
    public List<Etudiant> getEtudiantsByClasse(String classe) {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant e, classe c WHERE e.classe = c.id AND c.nom = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, classe);
            rs = db.executeSelect();
            while (rs.next()){
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("e.id"));
                e.setNom(rs.getString("e.nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setMatricule(rs.getString("matricule"));
                e.setMoyenne(rs.getDouble("moyenne"));
                etudiants.add(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return etudiants;
    }

    @Override
    public int add(Etudiant etudiant) {
        String sql = "INSERT INTO etudiant VALUES(null, ?, ?, ?, ?, ?)";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, etudiant.generateMatricule());
            db.getPstm().setString(2, etudiant.getNom());
            db.getPstm().setString(3, etudiant.getPrenom());
            db.getPstm().setDouble(4, etudiant.getMoyenne());
            db.getPstm().setInt(5, etudiant.getClasse().getId());
            ok = db.executeMaj();
            if(ok == 1){
                String req = "UPDATE classe SET effectif = effectif + 1 WHERE id = ?";
                try {
                    db.initPrepar(req);
                    db.getPstm().setInt(1, etudiant.getClasse().getId());
                    ok = db.executeMaj();
                    db.closeConnection();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int update(Etudiant etudiant) {
        String sql = "UPDATE etudiant SET nom = ?, prenom = ?, moyenne = ? WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, etudiant.getNom());
            db.getPstm().setString(2, etudiant.getPrenom());
            db.getPstm().setDouble(3, etudiant.getMoyenne());
            db.getPstm().setInt(4, etudiant.getId());
            ok = db.executeMaj();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM etudiant WHERE id = ?";
        int idClasse = get(id).getClasse().getId();
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            ok = db.executeMaj();
            if(ok == 1){
                String req = "UPDATE classe SET effectif = effectif - 1 WHERE id = ?";
                try {
                    db.initPrepar(req);
                    db.getPstm().setInt(1, idClasse);
                    ok = db.executeMaj();
                    db.closeConnection();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Etudiant> list() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant e, classe c WHERE e.classe = c.id";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()){
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("e.id"));
                e.setNom(rs.getString("e.nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setMatricule(rs.getString("matricule"));
                e.setMoyenne(rs.getDouble("moyenne"));
                IClasse classeDao = new ClasseImpl();
                Classe classe = classeDao.get(rs.getInt("c.id"));
                e.setClasse(classe);
                etudiants.add(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return etudiants;
    }

    @Override
    public Etudiant get(int id) {
        Etudiant e = null;
        String sql = "SELECT * FROM etudiant e, classe c WHERE e.classe = c.id AND e.id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            rs = db.executeSelect();
            if (rs.next()){
                e = new Etudiant();
                e.setId(rs.getInt("e.id"));
                e.setNom(rs.getString("e.nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setMatricule(rs.getString("matricule"));
                e.setMoyenne(rs.getDouble("moyenne"));
                IClasse classeDao = new ClasseImpl();
                Classe classe = classeDao.get(rs.getInt("c.id"));
                e.setClasse(classe);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return e;
    }
}
