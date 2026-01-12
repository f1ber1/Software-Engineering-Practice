package com.example.pos.infra.repo;

import com.example.pos.domain.model.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileOrderRepository implements OrderRepository {
    private final Path file;

    public FileOrderRepository(Path file) {
        this.file = file;
    }

    @Override
    public void save(Order order) {
        try {
            String block = "\n===== ORDER BEGIN =====\n" + order.toReceiptText() + "===== ORDER END =====\n";
            Files.writeString(file, block, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // 简化处理：真实项目可抛出基础设施异常
            throw new RuntimeException("Failed to save order to file: " + file, e);
        }
    }
}
