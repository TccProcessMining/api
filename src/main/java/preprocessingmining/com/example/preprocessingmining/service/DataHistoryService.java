package preprocessingmining.com.example.preprocessingmining.service;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.DataHistory;
import preprocessingmining.com.example.preprocessingmining.repository.DataHistoryRepository;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class DataHistoryService {
    @Autowired
    private DataHistoryRepository dataHistoryRepository;


    @SneakyThrows
    public DataHistory store(@NotNull String data_id, @NotNull ByteBuffer file) throws IOException {
        var dataHistory = new DataHistory(UUID.randomUUID().toString(), data_id, file);
        return dataHistoryRepository.save(dataHistory);
    }
}
