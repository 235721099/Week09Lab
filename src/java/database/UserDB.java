package database;

import models.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDB {

    public int insert(Users user) throws NotesDBException {
        EntityManager  em = DBUtil.getEmFactory().createEntityManager();  
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            em.merge(user);
            trans.commit();
            return 1;
        } catch(Exception ex){
            trans.rollback();
            return 0;
        }finally {
            em.close();
        }
    }

    public int update(Users user) throws NotesDBException {
        EntityManager  em = DBUtil.getEmFactory().createEntityManager();  
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return 1;
        } catch(Exception ex){
            trans.rollback();
            return 0;
        }finally {
            em.close();
        }
    }

    public ArrayList<Users> getAll() throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();       
        try {
            //get all notes from all users
            //List<Note> notes = em.createNamedQuery("Note.findAll", Note.class).getResultList();
            
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            ArrayList<Users> uList = new ArrayList<>();
            for(int i = 0; i < users.size(); i++){
                uList.add(users.get(i));
            }
            return uList;
        } finally {
            em.close();
        }
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws NotesDBException
     */
    public Users getUser(String username) throws NotesDBException {
        EntityManager  em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Users user = em.find(Users.class, username);
            //get the first name of the owner of this note
            //System.out.println("first name:" + note.getOwner().getFirstName());
            //get all notes from same user 
            //List<Note> notes = note.getOwner().getNoteList();
            return user;
        } finally {
            em.close();
        }
    }

    public int delete(Users user) throws NotesDBException {
        EntityManager  em = DBUtil.getEmFactory().createEntityManager();  
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
            return 1;
        } catch(Exception ex){
            trans.rollback();
            return 0;
        }finally {
            em.close();
        }
    }
}
