package com.example.microblinkdemo.mrz.extractor;

public abstract class AbstractMrzLineExtractor implements MrzLineExtractor {

    public String extractPart(MrzExtractor extractor, Integer elementSize) {
        final String extractPart = extractor.getMrzString().substring(0, elementSize);
        extractor.setMrzString(extractor.getMrzString().substring(elementSize));
        return extractPart;
    }
}
