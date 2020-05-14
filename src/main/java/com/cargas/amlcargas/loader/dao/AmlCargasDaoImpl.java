package com.cargas.amlcargas.loader.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import com.cargas.amlcargas.loader.model.ProcessFile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AmlCargasDaoImpl implements AmlCargasDao{

  private static final String UPDATE_TEXT_ENG = "A result set was generated for update.";
  private static final String UPDATE_TEXT_ESP = "Se ha generado un conjunto de resultados para actualización.";

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public ProcessFile getMetaDataUploadFile(String baseName, String schema) {
		ProcessFile res = new ProcessFile();
		res = jdbcTemplate.queryForObject(String.format(AmlCargasDaoQuery.QUERY_GET_DATA_FILE_LOAD,schema), new Object[] {baseName},new RowMapper<ProcessFile>(){

			public ProcessFile mapRow(ResultSet rs, int arg)
					throws SQLException {
				ProcessFile result = new ProcessFile();
				result.setCveProceso(rs.getString("CLAVE_01"));
				result.setIdLayout(rs.getString("VALOR_ATRIBUTO_02"));
				result.setSeparador(rs.getString("VALOR_ATRIBUTO_09"));
				result.setCveTipoArchivo(rs.getString("VALOR_ATRIBUTO_05"));
				return result;
			}
		});
		return res;
	}



    @Override
    public Integer getSecIdProceso(String schema) {

        SqlParameter fNameParam = new SqlParameter("Nombre_secuencia", Types.VARCHAR);
        SqlOutParameter outParameter = new SqlOutParameter("valor", Types.INTEGER);

        List<SqlParameter> paramList = new ArrayList<>();

        paramList.add(fNameParam);
        paramList.add(outParameter);

        Map<String, Object> integer = jdbcTemplate.call(con -> {
            CallableStatement callableStatement = con.prepareCall(String.format(AmlCargasDaoQuery.QUERY_GET_SEC_ID_PROCESO, schema));
            callableStatement.setString(1, "MTS_SEC_ANA_BITACORA");
            callableStatement.registerOutParameter(2, Types.INTEGER);
            return callableStatement;
        }, paramList);

        return Integer.parseInt(integer.get("valor").toString());
    }

  @Override
  public void insertBitacora(long procesoId, String claveProceso, String esquema) throws Exception {

    try {
      this.jdbcTemplate.update(String.format(AmlCargasDaoQuery.QUERY_INSERT_BITACORA, esquema), new Object[]{
        procesoId,
        claveProceso,
        esquema,
        esquema
      });
    }catch (Exception e) {

      //evalException(e.getCause().getMessage());
      if(!evalException(e.getCause().getMessage())){
        throw new Exception(e);
      }
    }
  }

  public boolean evalException(String message){
    return (UPDATE_TEXT_ENG.equals(message) || UPDATE_TEXT_ESP.equals(message));
  }
}
