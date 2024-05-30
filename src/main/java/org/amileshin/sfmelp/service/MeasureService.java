package org.amileshin.sfmelp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.manager.ClickHouseManager;
import org.amileshin.sfmelp.manager.FileManager;
import org.amileshin.sfmelp.model.dto.MeasureInfo;
import org.amileshin.sfmelp.model.dto.ResultInfo;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MeasureService {
    private final ClickHouseManager dbManager;
    private final FileManager fileManager;

    public ResultInfo getMeasure(MeasureInfo measureInfo) {
        try {
            log.info("Start Measure");
            ResultInfo resultInfo = new ResultInfo();

            double compressionTime = dbManager.getTimeForLoadLogsToDatabase(
                    fileManager.getStringFromFile(measureInfo.getFile()),
                    measureInfo.getTable(), measureInfo.getDb());

            double decompressionTime = dbManager.getTimeForLoadLogsFromDatabase(
                    measureInfo.getTable(), measureInfo.getDb());

            double compressionSize = dbManager.getDatabaseByteSize(measureInfo.getTable(), measureInfo.getDb());
            double fileSize = fileManager.getFileSize(measureInfo.getFile());

            resultInfo.setCompressionTime(fileSize * 1000000000 / (compressionTime * 1048576));
            resultInfo.setDecompressionTime(fileSize * 1000000000 / (decompressionTime * 1048576));
            resultInfo.setRatio(compressionSize / fileSize * 100.0);

            log.info("Finish Measure");
            return resultInfo;
        } catch (Exception e) {
            log.error("Measure failed: {}", e.getMessage());
            throw e;
        }
    }
}
