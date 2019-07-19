package com.solum.fwmanager.external;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.solum.fwmanager.dto.TagTypeInfoDTO;
import com.solum.fwmanager.dto.TargetStationDTO;
import com.solum.fwmanager.external.entity.OTATargetGateway;

@Repository
public class CoreDao {

	@Autowired
	@Qualifier("aimsCoreEntityManagerFactory")
	private	EntityManagerFactory emFactory;
	
	public	long getTest() {
		EntityManager entityManager = emFactory.createEntityManager();
	    String sql = "SELECT COUNT(*) FROM accesspoint";
	 
	    Query nativeQuery = entityManager.createNativeQuery(sql);
	    BigInteger ret = (BigInteger)nativeQuery.getSingleResult();
	    
	    return ret.longValue();
	}
	
	// TODO : Support MS_SQL
	// TODO : Add 'stationCode' parameter to query
	public List<TagTypeInfoDTO> getInstalledTagTypeListByStation(String stationCode){
		EntityManager entityManager = emFactory.createEntityManager();
		String sql = "SELECT B.slabel_type, A.display_width AS width, A.is_nfc, B.is_ti, "
				+ "SUBSTRING(A.code FROM 9 FOR 3) AS attribute FROM enddevice A "
				+ "INNER JOIN (SELECT DISTINCT ON (slabel_type) id, slabel_type, is_ti FROM solum_enddevice) B "
				+ "ON A.id=B.id";
		
		
		Query nativeQuery = entityManager.createNativeQuery(sql);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nativeQuery.getResultList();
	    List<TagTypeInfoDTO> tagTypeList = resultList.stream().map(tagTypeInfo -> {
	    	TagTypeInfoDTO singleDTO = new TagTypeInfoDTO();
	    	
	    	singleDTO.setSlabelType((String)tagTypeInfo[0]);
	    	singleDTO.setWidth((Integer)tagTypeInfo[1]);
	    	singleDTO.setNfc((Boolean)tagTypeInfo[2]);
	    	singleDTO.setTi((Boolean)tagTypeInfo[3]);
	    	singleDTO.setAttribute((String)tagTypeInfo[4]);
	    	
	    	singleDTO.setPropertiesFromLowData();
    		
            return singleDTO;
	    }).collect(Collectors.toList());
	    
	    return tagTypeList;
	}
	
	// TODO : Support MS_SQL
	public List<TargetStationDTO> getTargetStationList(String tagAttribute){
		EntityManager entityManager = emFactory.createEntityManager();
		String sql = "SELECT ST.id, ST.code, ST.name, ST.description, COUNT(SUB.gw_id) AS gwCount, SUM(SUB.tag_count) AS tagCount " + 
				"FROM station ST " + 
				"INNER JOIN ( " + 
				"	SELECT AC.station_id, AC.id AS gw_id, COUNT(ED.*) AS tag_count FROM accesspoint AC " + 
				"	INNER JOIN enddevice ED " + 
				"	ON AC.id=ED.accesspoint_id " + 
				"	WHERE SUBSTRING(ED.code FROM 9 FOR 3) = ? " + 
				"	GROUP BY AC.station_id, AC.id " + 
				") SUB " + 
				"ON ST.id=SUB.station_id " + 
				"GROUP BY ST.id, ST.code, ST.name, ST.description"; 
		
		Query nativeQuery = entityManager.createNativeQuery(sql);
		nativeQuery.setParameter(1, tagAttribute);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nativeQuery.getResultList();
	    List<TargetStationDTO> targetStationList = resultList.stream().map(stationInfo -> {
	    	TargetStationDTO singleDTO = new TargetStationDTO();
	    	
	    	singleDTO.setCode((String)stationInfo[1]);
	    	singleDTO.setName((String)stationInfo[2]);
	    	singleDTO.setDescription((String)stationInfo[3]);
	    	singleDTO.setGwCount(((BigInteger)stationInfo[4]).intValue());
	    	singleDTO.setTagCount(((BigDecimal)stationInfo[5]).longValue());

    		
            return singleDTO;
	    }).collect(Collectors.toList());
	    
	    return targetStationList;
	}
	
	//TODO : Support MS-SQL
	public List<OTATargetGateway> getTargetGWList(String stationCode){
		EntityManager entityManager = emFactory.createEntityManager();
		String sql = "SELECT AC.id, AC.mac_address AS macAddress, AC.ip_address AS ipAddress " + 
				"FROM accesspoint AC " + 
				"WHERE AC.station_id IN (SELECT id FROM station WHERE code=?) " +
				"AND AC.state='CONNECTED'";
		
		Query nativeQuery = entityManager.createNativeQuery(sql);
		nativeQuery.setParameter(1, stationCode);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nativeQuery.getResultList();
	    List<OTATargetGateway> targetGWList = resultList.stream().map(gwInfo -> {
	    	OTATargetGateway singleDTO = new OTATargetGateway();
	    	
	    	singleDTO.setId(((BigInteger)gwInfo[0]).longValue());
	    	singleDTO.setMacAddress((String)gwInfo[1]);
	    	singleDTO.setIpAddress((String)gwInfo[2]);
    		
            return singleDTO;
	    }).collect(Collectors.toList());
	    
	    return targetGWList;
	}	
}
