package jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao{
    private static UporabnikDaoImpl instance;

    private static UporabnikDaoImpl getInstance(){
        if(instance == null) instance = new UporabnikDaoImpl(); return instance;
    }


    private Connection connection;
    private Logger log;
    @Override
    public Connection getConnection(){
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();
        } catch (NamingException e) {
            log.info("Povezava ni uspela: " + e.getMessage());
        } catch (SQLException throwables) {
            log.info("ds getConnection failed");
        }
        return null;
    }

    @Override
    public Entiteta vrni(int d){
        PreparedStatement ps = null;

        try {

            if (connection == null) {
                connection = getConnection();
            }
            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, d);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent){
        PreparedStatement ps = null;
        Uporabnik user = (Uporabnik) ent;

        try {
            if (connection == null) {
                connection = getConnection();
            }

            String sql = "INSERT INTO uporabniki (username, name, surname) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newid = rs.getInt(1);
                user.setId(newid);
            } else {
                log.info("Bug v try");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void odstrani(int d){
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }

            String sql = "DELETE FROM uporabniki WHERE id = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, d);

            ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent){
        PreparedStatement ps = null;
        Uporabnik user = (Uporabnik) ent;

        try {
            if (connection == null) {
                connection = getConnection();
            }

            String sql = "UPDATE uporabniki SET username = ?, name = ?, surname = ? WHERE id = ?";

            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public List<Entiteta> vrniVse(){
        List<Entiteta> entitete = new ArrayList<Entiteta>();
        PreparedStatement ps = null;

        try{
            if (connection == null) {
                connection = getConnection();
            }
            String sql = "SELECT * FROM uporabniki";
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while(rs.next()){
                entitete.add(getUporabnikFromRS(rs));
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return entitete;
    }
    private Entiteta getUporabnikFromRS(ResultSet rs) throws SQLException {

        String ime = rs.getString("name");//name surname username?
        String priimek = rs.getString("surname");
        String uporabniskoIme = rs.getString("username");
        return new Uporabnik(ime, priimek, uporabniskoIme);

    }
}
