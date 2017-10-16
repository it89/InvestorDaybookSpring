package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.interfaces.BondDAO;
import com.github.it89.investordaybookspring.daybook.stockmarket.Bond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("sqliteBondDAO")
public class SQLiteBondDAO implements BondDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void register(Bond bond) {

        if(!exists(bond)) {
            String sql = "INSERT INTO Bond (code, caption) VALUES (:code, :caption)";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("code", bond.getCode());
            params.addValue("caption", bond.getCaption());

            jdbcTemplate.update(sql, params);
        } else {
            Integer id = getIdByCode(bond.getCode());
            String sql = "UPDATE Bond SET code = :code, caption = :caption where id = :id";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("code", bond.getCode());
            params.addValue("caption", bond.getCaption());
            params.addValue("id", id);

            jdbcTemplate.update(sql, params);
        }
    }

    @Override
    public void remove(Bond bond) {
        String sql = "DELETE FROM Bond WHERE upper(code) = upper(:code)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", bond.getCode());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public boolean exists(Bond bond) {
        if (getIdByCode(bond.getCode()) == null)
            return false;
        else
            return true;
    }

    @Override
    public Bond getBondByCode(String code) {
        return getBondByID(getIdByCode(code));
    }

    @Override
    public Bond getBondByCaption(String caption) {
        return getBondByID(getIdByCaption(caption));
    }

    private Integer getIdByCode(String code) {
        String sql = "SELECT id FROM Bond WHERE UPPER(code) = UPPER(:code)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("code", code);
        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    private Integer getIdByCaption(String caption) {
        String sql = "SELECT id FROM Bond WHERE UPPER(caption) = UPPER(:caption)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("caption", caption);
        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Bond getBondByID(int id) {
        String sql = "select * from mp3 where id=:id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.queryForObject(sql, params, new MP3RowMapper());
    }

    private static final class MP3RowMapper implements RowMapper<Bond> {

        @Override
        public Bond mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bond bond = new Bond(rs.getString("code"), rs.getString("caption"));
            return bond;
        }

    }
}
