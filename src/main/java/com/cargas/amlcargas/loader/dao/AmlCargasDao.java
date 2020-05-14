package com.cargas.amlcargas.loader.dao;

import com.cargas.amlcargas.loader.model.ProcessFile;

public interface AmlCargasDao {

	ProcessFile getMetaDataUploadFile(String baseName, String schema);
	Integer getSecIdProceso(String schema);
  //void insertBitacora(long procesoId, String claveProceso,String esquema, String estatus, String tipo_ejecucion) throws Exception;
  void insertBitacora(long procesoId, String claveProceso, String esquema) throws Exception;

  }
