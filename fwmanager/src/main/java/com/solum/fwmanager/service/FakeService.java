package com.solum.fwmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.solum.fwmanager.vo.GatewayVO;

@Service
public class FakeService {

	public List<GatewayVO>	getTargetGWList(){
		List<GatewayVO>	targetList = new ArrayList<GatewayVO>();
		
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_1").ip("192.168.100.1").mac("AA:00:01:02:03:04").status("Reserved").reserveTime("2019-05-31 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_2").ip("192.168.100.2").mac("BB:00:01:02:F3:04").status("Reserved").reserveTime("2019-05-31 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_3").ip("192.168.100.3").mac("CC:00:01:02:D3:04").status("Reserved").reserveTime("2019-05-31 03:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_1").ip("10.168.15.1").mac("DD:00:01:02:23:04").status("Auto").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_2").ip("10.168.15.1").mac("DF:00:01:02:34:04").status("Auto").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_1").ip("32.100.15.63").mac("99:FF:01:02:23:04").status("Configure").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_2").ip("32.100.15.64").mac("99:FF:01:02:34:04").status("Configure").reserveTime("--:--").build());
		
		return targetList;
	}
	

	public List<GatewayVO>	getTargetGWList2(){
		List<GatewayVO>	targetList = new ArrayList<GatewayVO>();
		
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_1").ip("192.168.100.1").mac("AA:00:01:02:03:04").status("Reserved").reserveTime("2019-05-31 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_2").ip("192.168.100.2").mac("BB:00:01:02:F3:04").status("Reserved").reserveTime("2019-05-31 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_3").ip("192.168.100.3").mac("CC:00:01:02:D3:04").status("Reserved").reserveTime("2019-05-31 03:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_1").ip("10.168.15.1").mac("DD:00:01:02:23:04").status("Reserved").reserveTime("2019-06-01 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_2").ip("10.168.15.1").mac("DF:00:01:02:34:04").status("Reserved").reserveTime("2019-06-01 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_1").ip("32.100.15.63").mac("99:FF:01:02:23:04").status("Configure").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_2").ip("32.100.15.64").mac("99:FF:01:02:34:04").status("Configure").reserveTime("--:--").build());
		
		return targetList;
	}
	
	public List<GatewayVO>	getTargetGWList3(){
		List<GatewayVO>	targetList = new ArrayList<GatewayVO>();
		
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_1").ip("192.168.100.1").mac("AA:00:01:02:03:04").status("Reserved").reserveTime("2019-05-31 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_2").ip("192.168.100.2").mac("BB:00:01:02:F3:04").status("Reserved").reserveTime("2019-05-31 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_3").ip("192.168.100.3").mac("CC:00:01:02:D3:04").status("Reserved").reserveTime("2019-05-31 03:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_1").ip("10.168.15.1").mac("DD:00:01:02:23:04").status("Reserved").reserveTime("2019-06-01 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_2").ip("10.168.15.1").mac("DF:00:01:02:34:04").status("Reserved").reserveTime("2019-06-01 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_1").ip("32.100.15.63").mac("99:FF:01:02:23:04").status("Reserved").reserveTime("2019-06-06 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_2").ip("32.100.15.64").mac("99:FF:01:02:34:04").status("Reserved").reserveTime("2019-06-06 04:00:00").build());
		
		return targetList;
	}
	
	public List<GatewayVO>	getTargetGWList4(){
		List<GatewayVO>	targetList = new ArrayList<GatewayVO>();
		
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_1").ip("192.168.100.1").mac("AA:00:01:02:03:04").status("Configure").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_2").ip("192.168.100.2").mac("BB:00:01:02:F3:04").status("Configure").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("ABC").name("GW_3").ip("192.168.100.3").mac("CC:00:01:02:D3:04").status("Configure").reserveTime("--:--").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_1").ip("10.168.15.1").mac("DD:00:01:02:23:04").status("Reserved").reserveTime("2019-06-01 01:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("XYZ").name("GW_2").ip("10.168.15.1").mac("DF:00:01:02:34:04").status("Reserved").reserveTime("2019-06-01 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_1").ip("32.100.15.63").mac("99:FF:01:02:23:04").status("Reserved").reserveTime("2019-06-06 02:00:00").build());
		targetList.add(GatewayVO.builder().stationCode("Suwon").name("GW_2").ip("32.100.15.64").mac("99:FF:01:02:34:04").status("Reserved").reserveTime("2019-06-06 04:00:00").build());
		
		return targetList;
	}
}
