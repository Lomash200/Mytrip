package com.lomash.mytrip.util;

import org.springframework.core.io.ClassPathResource;
import java.nio.file.Files;

public class EmailTemplateProcessor {

    public static String processTemplate(String fileName,
                                         java.util.Map<String, String> values) {

        try {
            String html = Files.readString(
                    new ClassPathResource("templates/emails/" + fileName).getFile().toPath()
            );

            for (var entry : values.entrySet()) {
                html = html.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return html;

        } catch (Exception e) {
            throw new RuntimeException("Error loading email template: " + e.getMessage());
        }
    }
}
