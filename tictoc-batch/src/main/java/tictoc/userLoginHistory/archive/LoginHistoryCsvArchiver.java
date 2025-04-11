package tictoc.userLoginHistory.archive;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import tictoc.error.ErrorCode;
import tictoc.error.exception.CsvFailException;
import tictoc.userLoginHistory.dto.UserLoginHistoryDTO;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;

@Component
public class LoginHistoryCsvArchiver {
    private static final Path LOG_DIR = Paths.get("logs");
    private static final String FILE_PREFIX = "login-archive-";
    private static final String FILE_EXTENSION = ".csv";

    public Path archive(List<? extends UserLoginHistoryDTO> items) {
        checkLogDirectoryExists();
        Path filePath = buildArchiveFilePath();
        writeHistoriesToCsv(filePath, items);
        return filePath;
    }

    private void checkLogDirectoryExists() {
        try {
            if (Files.notExists(LOG_DIR)) {
                Files.createDirectories(LOG_DIR);
            }
        } catch (IOException e) {
            throw new CsvFailException(e, ErrorCode.CSV_FAIL);
        }
    }

    private Path buildArchiveFilePath() {
        String fileName = FILE_PREFIX + LocalDate.now() + FILE_EXTENSION;
        return LOG_DIR.resolve(fileName);
    }

    private void writeHistoriesToCsv(Path filePath, List<? extends UserLoginHistoryDTO> items) {
        try (
                Writer writer = Files.newBufferedWriter(filePath,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "user_id", "login_at", "ip_address", "device"))
        ) {
            for (UserLoginHistoryDTO dto : items) {
                csvPrinter.printRecord(
                        dto.id(),
                        dto.userId(),
                        dto.loginAt(),
                        dto.ipAddress(),
                        dto.device()
                );
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new CsvFailException(e, ErrorCode.CSV_FAIL);
        }
    }
}