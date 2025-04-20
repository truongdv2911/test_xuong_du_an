package com.example.demo.Config;

import com.example.demo.Entity.Staff;
import com.example.demo.Entity.StaffExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static ByteArrayInputStream exportStaff(List<StaffExcel> staffs) throws IOException {
        String[] HEADERS = {"Mã Nhân Viên", "Họ và tên", "Email fpt", "Email fe","Bo mon chuyen nghanh"};
        String SHEET = "Staff";

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            int rowIdx = 1;
            for (StaffExcel nv : staffs) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(nv.getStaffCode());
                row.createCell(1).setCellValue(nv.getName());
                row.createCell(2).setCellValue(nv.getAccountFpt());
                row.createCell(3).setCellValue(nv.getAccountFe());
                row.createCell(4).setCellValue(nv.getMajorFacilityDepart());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public static List<StaffExcel> excelToNhanViens(InputStream is) {
        List<StaffExcel> staffs = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber++ == 0) {
                    continue; // Bỏ qua hàng tiêu đề
                }

                StaffExcel staff = new StaffExcel();
                try {
                    staff.setStaffCode(getStringCellValue(currentRow.getCell(0)));
                    staff.setName(getStringCellValue(currentRow.getCell(1)));
                    staff.setAccountFpt(getStringCellValue(currentRow.getCell(2)));
                    staff.setAccountFe(getStringCellValue(currentRow.getCell(3)));
                    staff.setMajorFacilityDepart(getStringCellValue(currentRow.getCell(4)));

                    // Chỉ thêm nhân viên nếu StaffCode không rỗng sau khi đã xử lý null và whitespace
                    if (!staff.getStaffCode().trim().isEmpty()) {
                        staffs.add(staff);
                    }
                } catch (Exception e) {
                    // Ghi log hoặc xử lý lỗi khi đọc một hàng cụ thể
                    System.err.println("Lỗi khi đọc hàng " + rowNumber + ": " + e.getMessage());
                    // Bạn có thể chọn bỏ qua hàng này hoặc xử lý khác tùy theo yêu cầu
                }
            }
            return staffs;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file Excel: " + e.getMessage());
        }
    }
    private static String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cellType == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue()).trim();
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cellType == CellType.BLANK) {
            return "";
        } else {
            return "";
        }
    }
}
