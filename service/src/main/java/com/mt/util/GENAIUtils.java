package com.mt.util;

import org.springframework.ai.document.Document;
import java.util.List;

public class GENAIUtils {

    public static String getContents(List<Document> documents) {
        StringBuilder sb = new StringBuilder();
        for (Document doc : documents)
        {
            sb.append(doc.getText());
            sb.append("\n");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
