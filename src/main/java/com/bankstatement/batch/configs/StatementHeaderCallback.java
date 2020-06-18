package com.bankstatement.batch.configs;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class StatementHeaderCallback implements FlatFileHeaderCallback {
  public void writeHeader(Writer writer) throws IOException {
    writer.write(String.format("%120s\n", "Created by Jobin George"));
    writer.write(String.format("%120s\n", "9497079052"));
    writer.write(String.format("%120s\n", "Available 24/7"));
    writer.write("\n");
  }
}
