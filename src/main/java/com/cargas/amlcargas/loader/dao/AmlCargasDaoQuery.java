package com.cargas.amlcargas.loader.dao;

public class AmlCargasDaoQuery {

    public static final String QUERY_GET_SEC_ID_PROCESO = "EXEC %s.PR_OBTEN_SECUENCIA ?,?";
    public static final String QUERY_GET_DATA_FILE_LOAD = "SELECT CLAVE_01,VALOR_ATRIBUTO_01,VALOR_ATRIBUTO_02,VALOR_ATRIBUTO_09,VALOR_ATRIBUTO_05\n" +
    		"					FROM %s.MTS_ANA_DCATALOGOS_CLAVES\n" +
    		"					WHERE CVE_TABLA = 'CAT_CARGAS'\n" +
    		"					AND VALOR_ATRIBUTO_01 = ?";

    public static final String QUERY_INSERT_BITACORA = "INSERT INTO %s.MTS_ANA_BITACORA_PROCESOS\n" +
      "\t\t\t\t\t(ID_PROCESO, CVE_PROCESO, FECHA_INI_PROCESO, CVE_USUARIO_PROCESO, CREADO_POR, CVE_ESTATUS_PROCESO, FEC_CREACION,CVE_TIPO_EJECUCION)\n" +
      "\t\t\t\t\tVALUES( ? ,? ,GETDATE() ,?, ?, 'I', GETDATE(),'M')";
}
