package com.company.project.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.rapid.common.util.DateConvertUtil;

public class EasyExcelUtil {
	
	public static <T> void  writeExcel2Response(HttpServletResponse response,List<T> list,Class<T> head)  {
		String date = DateConvertUtil.format(new Date(), "yyyyMMdd_HHmmss");
		String finalFileName = head.getSimpleName() + "_" + date + ".xls";
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + finalFileName);
		
		OutputStream outputStream = null;
		try {
	        outputStream = response.getOutputStream();
			writeExcel2OutputStream(outputStream,list,head);
		}catch(IOException e) {
			throw new RuntimeException("writeExcel2Response error,head:"+head,e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	
	public static <T> void writeExcel2OutputStream(OutputStream outputStream, List<T> itemList, Class<T> head) {
		EasyExcel.write(outputStream, head)
//        .excelType(ExcelTypeEnum.CSV)
		.inMemory(true)
		.autoTrim(true)
        .excelType(ExcelTypeEnum.XLS)
        .sheet("sheet1")
        .doWrite(itemList);
	}
}
