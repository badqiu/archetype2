package com.company.project.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.excel.EasyExcel;

public class EasyExcelUtil {
	
	public static <T> void  writeExcel2Response(HttpServletResponse response,List<T> newItemList,Class<T> head)  {
		String finalFileName = head.getSimpleName() + ".xlsx";
		
		OutputStream outputStream = null;
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + finalFileName);
	
	        outputStream = response.getOutputStream();
			EasyExcel.write(outputStream, head)
	//        .excelType(ExcelTypeEnum.CSV)
	//        .inMemory(true)
	        .sheet("sheet1")
	        .doWrite(newItemList);
		}catch(IOException e) {
			throw new RuntimeException("writeExcel2Response error,head:"+head,e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
	
}
