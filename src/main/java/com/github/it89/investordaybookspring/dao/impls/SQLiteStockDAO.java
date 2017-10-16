package com.github.it89.investordaybookspring.dao.impls;

import com.github.it89.investordaybookspring.dao.interfaces.StockDAO;
import com.github.it89.investordaybookspring.daybook.stockmarket.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("sqliteStockDAO")
public class SQLiteStockDAO implements StockDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void register(Stock stock) {

        if(!exists(stock)) {
            String sql = "INSERT INTO Stock (code, caption) VALUES (:code, :caption)";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("code", stock.getCode());
            params.addValue("caption", stock.getCaption());

            jdbcTemplate.update(sql, params);
        } else {
            Integer id = getIdByCode(stock.getCode());
            String sql = "UPDATE Stock SET code = :code, caption = :caption where id = :id";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("code", stock.getCode());
            params.addValue("caption", stock.getCaption());
            params.addValue("id", id);

            jdbcTemplate.update(sql, params);
        }
    }

    @Override
    public void remove(Stock stock) {
        String sql = "DELETE FROM Stock WHERE upper(code) = upper(:code)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", stock.getCode());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public boolean exists(Stock stock) {
        if (getIdByCode(stock.getCode()) == null)
            return false;
        else
            return true;
    }

    @Override
    public Stock getStockByCode(String code) {
        return getStockByID(getIdByCode(code));
    }

    @Override
    public Stock getStockByCaption(String caption) {
        return getStockByID(getIdByCaption(caption));
    }

    private Integer getIdByCode(String code) {
        String sql = "SELECT id FROM Stock WHERE UPPER(code) = UPPER(:code)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("code", code);
        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    private Integer getIdByCaption(String caption) {
        String sql = "SELECT id FROM Stock WHERE UPPER(caption) = UPPER(:caption)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("caption", caption);
        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Stock getStockByID(int id) {
        String sql = "select * from mp3 where id=:id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.queryForObject(sql, params, new MP3RowMapper());
    }

    private static final class MP3RowMapper implements RowMapper<Stock> {

        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            Stock stock = new Stock(rs.getString("code"), rs.getString("caption"));
            return stock;
        }

    }
}
