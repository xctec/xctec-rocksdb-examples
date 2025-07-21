package io.github.xctec.demo.controller;

import io.github.xctec.demo.pojo.Gwyzw;
import io.github.xctec.rocksdb.core.RocksdbTemplate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RocksdbTemplate<String, Gwyzw> rocksdbTemplate;


    @RequestMapping("/imp")
    public String imp() throws Exception {
        File file = new File("rocksdb-gwyzw/gkzw.xls");
        if (!file.exists()) {
            return String.format("file[%s] not exists!", file.getAbsolutePath());
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = new HSSFWorkbook(inputStream);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                logger.info("开始处理sheet, index: {}, name: {}, firstRowNum: {}, lastRowNum: {}",
                        i, sheetName, sheet.getFirstRowNum(), sheet.getLastRowNum());
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = 2; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    Gwyzw gwyzw = new Gwyzw();
                    gwyzw.setId((i + 1) * 1000000 + rowNum);
                    gwyzw.setBgxh(i);
                    gwyzw.setBgmc(sheetName);
                    setStringValue(row, 0, gwyzw, Gwyzw::setBmdm);
                    setStringValue(row, 1, gwyzw, Gwyzw::setBmmc);
                    setStringValue(row, 2, gwyzw, Gwyzw::setYrsj);
                    setStringValue(row, 3, gwyzw, Gwyzw::setJgxz);
                    setStringValue(row, 4, gwyzw, Gwyzw::setZkzw);
                    setStringValue(row, 5, gwyzw, Gwyzw::setZwsz);
                    setStringValue(row, 6, gwyzw, Gwyzw::setZwfb);
                    setStringValue(row, 7, gwyzw, Gwyzw::setZwjj);
                    setStringValue(row, 8, gwyzw, Gwyzw::setZwdm);
                    setStringValue(row, 9, gwyzw, Gwyzw::setJgcj);
                    setStringValue(row, 10, gwyzw, Gwyzw::setKslb);
                    setIntValue(row, 11, gwyzw, Gwyzw::setZkrs);
                    setStringValue(row, 12, gwyzw, Gwyzw::setZy);
                    setStringValue(row, 13, gwyzw, Gwyzw::setXl);
                    setStringValue(row, 14, gwyzw, Gwyzw::setXw);
                    setStringValue(row, 15, gwyzw, Gwyzw::setZzmm);
                    setStringValue(row, 16, gwyzw, Gwyzw::setJcgzzdnx);
                    setStringValue(row, 17, gwyzw, Gwyzw::setFwjcxmgzjl);
                    setStringValue(row, 18, gwyzw, Gwyzw::setSfmsjdzzzynlcs);
                    setStringValue(row, 19, gwyzw, Gwyzw::setMszybl);
                    setStringValue(row, 20, gwyzw, Gwyzw::setGzdd);
                    setStringValue(row, 21, gwyzw, Gwyzw::setLhdd);
                    setStringValue(row, 22, gwyzw, Gwyzw::setBz);
                    setStringValue(row, 23, gwyzw, Gwyzw::setBmwz);
                    setStringValue(row, 24, gwyzw, Gwyzw::setZxdh1);
                    setStringValue(row, 25, gwyzw, Gwyzw::setZxdh2);
                    setStringValue(row, 26, gwyzw, Gwyzw::setZxdh3);
                    rocksdbTemplate.put(String.valueOf(gwyzw.getId()), gwyzw);
                }
            }
        }
        return "success";
    }

    @RequestMapping("/get")
    public Gwyzw get(@RequestParam("id") String id) {
        return rocksdbTemplate.get(id);
    }

    @RequestMapping("/del")
    public String del(@RequestParam("id") String id) {
        rocksdbTemplate.delete(id);
        return "success";
    }

    @RequestMapping("/iterator")
    public List<Gwyzw> iterator(
            @RequestParam(value = "startType", required = false, defaultValue = "key") String startType,
            @RequestParam(value = "seekKey", required = false) String seekKey,
            @RequestParam(value = "order", required = false, defaultValue = "next") String order,
            @RequestParam(value = "offset", required = false, defaultValue = "1") Long offset,
            @RequestParam(value = "count", required = false, defaultValue = "2000000") Long count) {
        List<Gwyzw> result = new ArrayList<>();
        /*
        try (ReadOptions readOptions = new ReadOptions()) {
            try (RocksIterator iterator = rocksdbTemplate.newIterator(readOptions)) {
                if ("first".equals(startType)) {
                    iterator.seekToFirst();
                } else if ("last".equals(startType)) {
                    iterator.seekToLast();
                } else if ("key".equals(startType) && StringUtils.hasText(seekKey)) {
                    iterator.seek(seekKey.getBytes());
                } else {

                }
                while (true) {
                    Gwyzw value = (Gwyzw) RocksdbSerializer.json().deserialize(iterator.value());
                    if (value != null) {
                        result.add(value);
                    }
                    if (iterator.isValid()) {
                        if ("next".equals(order)) {
                            iterator.next();
                        } else {
                            iterator.prev();
                        }
                    } else {
                        break;
                    }
                }
                try {
                    iterator.status();
                } catch (RocksDBException e) {
                    logger.info("seek出现异常", e);
                }
            }

        }*/
        rocksdbTemplate.iterator(startType, seekKey, order, offset, count, (key, value, index) -> {
            result.add(value);
        });
        logger.info("seekSize: {}", result.size());
        return result;
    }


    private void setStringValue(Row row, int cellIndex, Gwyzw gwyzw, BiConsumer<Gwyzw, String> setter) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return;
        }
        String cellValue = cell.getStringCellValue();
        if (StringUtils.hasText(cellValue)) {
            setter.accept(gwyzw, cellValue);
        }
    }

    private void setIntValue(Row row, int cellIndex, Gwyzw gwyzw, BiConsumer<Gwyzw, Integer> setter) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return;
        }
        String strCellValue = cell.getStringCellValue();
        Integer cellValue = 0;
        if (StringUtils.hasText(strCellValue)) {
            try {
                cellValue = Integer.valueOf(strCellValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setter.accept(gwyzw, cellValue);
    }

}
