package com.cd.zj.util;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
	/**
	 * @param heads 表头信息，这里设置的是单层表头
	 * @param property  属性信息，所要导出字段在list中map的key,例如name，age 为了排字段序
	 * @param list 导出的数据,其中是map组成的
	 * @param out  数据流
	 */
	@SuppressWarnings({ "rawtypes", "resource" })
	public static void writeFileOfMap(String[] heads, String[] property, List<Map<String, Object>> list, OutputStream out){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HSSFWorkbook hs = new HSSFWorkbook();
		HSSFCellStyle style = hs.createCellStyle();  //设置样式
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直
//		style.setAlignment(CellStyle.ALIGN_CENTER);  //水平
		//设置每个sheet显示1000条数据
		int count = 1000;  
		int sheetNum = list.size()/count;
		if(list.size() != sheetNum*count||list.size()==0){
			sheetNum++;
		}
		for (int i = 0; i < sheetNum; i++) {
			HSSFSheet sheet = hs.createSheet("第" + (i + 1) + "页");
			HSSFRow row = sheet.createRow(0);
			//设置表头
			for(int cells =0;cells<heads.length;cells++){
//				String[]  head = heads[i2];
				HSSFCell cell = row.createCell(cells);
				cell.setCellValue(heads[cells]); 
				cell.setCellStyle(style); // 样式
			}
			if(list!=null&&list.size()>0){
				int rows = list.size();
				for(int i2 = 0; i2<rows; i2++){
					HSSFRow rowNum = sheet.createRow(i2+1);
					for(int j=0;j<property.length;j++){
						sheet.setColumnWidth(j,32*120);//设置列宽(120像素)
						Object o = null;
						HSSFCell cell = rowNum.createCell(j);
						Map  map1 = list.get(i2);
						if(map1.containsKey(property[j])){
							o = map1.get(property[j]);
							if(o instanceof Long || o instanceof Integer){
								cell.setCellValue( Double.parseDouble(o.toString()));
							}else{
								String s;
								if(o==null||"".equals(o)){
									s ="";
									cell.setCellValue(s);
								}else if(o instanceof Date){
									s = sdf.format(o);
									cell.setCellValue(s);
								}else{
									s = String.valueOf(o);
									//正则表达式判断是否是有效的数字，如果是转换成Long型，导出excel时是数字而不是文本
									/*if(s.length()<=15 && s.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
										cell.setCellValue(Long.valueOf(s));
									}else{
										cell.setCellValue(s);
									}*/
									cell.setCellValue(s);
								}
							}
						}else{
							cell.setCellValue("");
						}
		
					}
				}
			}
			try {
				hs.write(out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	
	
	/**
		 * @param heads 表头信息，这里设置的是双层表头
		 * @param property  属性信息，所要导出字段在list中map的key,例如name，age 为了排字段序
		 * @param list 导出的数据,其中是map组成的
		 * @param out  数据流
		 */
		@SuppressWarnings({ "resource" })
		public static void writeFileOfMap(String[][] heads, String[] property, List<Map<String, Object>> list, OutputStream out){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			HSSFWorkbook hs = new HSSFWorkbook();
			HSSFCellStyle style = hs.createCellStyle();  //设置样式
//			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直
//			style.setAlignment(CellStyle.ALIGN_CENTER);  //水平
			//设置每个sheet显示1000条数据
			int count = 1000;  
			int sheetNum = list.size()/count;
			if(list.size() != sheetNum*count){
				sheetNum++;
			}
			for (int i = 0; i < sheetNum; i++) {
				HSSFSheet sheet = hs.createSheet("第" + (i + 1) + "页");
				HSSFRow row = sheet.createRow(0);
				HSSFRow row2 = sheet.createRow(1);
				int cols = 0;
				int cells = 0;
				//设置表头
				for(int i2 =0;i2<heads.length;i2++){
					String[]  head = heads[i2];
					if(head.length==1){
						sheet.addMergedRegion(new CellRangeAddress(0, 1, cols, cols));
						HSSFCell cell = row.createCell(cells);
						cell.setCellValue(head[0]); 
						cell.setCellStyle(style); // 样式
						cols++;
						cells++;
					}else if(head.length>1){
						//计算跨单元格数
						int colsMerged = head.length-1;
						for(int j=0;j<head.length;j++){
							if(j==0){
								sheet.addMergedRegion(new CellRangeAddress(0, 0, cols, cols+colsMerged-1));
								HSSFCell cell = row.createCell(cells);
								cell.setCellValue(head[0]); 
								cell.setCellStyle(style); // 样式
								cols = cols+colsMerged;
							}else{
								HSSFCell cell = row2.createCell(cells);
								cell.setCellValue(head[j]); 
								cell.setCellStyle(style); 
								cells++;
							}
						}
					}
				}
				//body
				if(list!=null&&list.size()>0){
					int rows = list.size();
					for(int i2 = 0; i2<rows; i2++){
						HSSFRow rowNum = sheet.createRow(i2+2);
						for(int j=0;j<property.length;j++){
							sheet.setColumnWidth(j,32*120);//设置列宽(120像素)
							Object o = null;
							HSSFCell cell = rowNum.createCell(j);
							Map<String, Object>  map1 = list.get(i2);
							o = map1.get(property[j]);
							if(o instanceof Long || o instanceof Integer){
								cell.setCellValue( Double.parseDouble(o.toString()));
							}else{
								String s;
								if(o==null||"".equals(o)){
									s ="";
									cell.setCellValue(s);
								}else if(o instanceof Date){
									s = sdf.format(o);
									cell.setCellValue(s);
								}else{
									s = String.valueOf(o);
									//正则表达式判断是否是有效的数字，如果是转换成Long型，导出excel时是数字而不是文本
									if(s.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
										cell.setCellValue(Long.valueOf(s));
									}else{
										cell.setCellValue(s);
									}
								}
							}
						}
					}
				}
				try {
					hs.write(out);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}

