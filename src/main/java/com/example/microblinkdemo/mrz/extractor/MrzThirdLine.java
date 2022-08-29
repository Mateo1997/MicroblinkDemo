package com.example.microblinkdemo.mrz.extractor;

import com.example.microblinkdemo.mrz.MrzConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MrzThirdLine implements MrzLineExtractor {

    private String primaryId;
    private String secondaryId;

    @Override
    public void extract(String mrzLine) {
        final String[] mrzThirdPartArray = mrzLine.split(MrzConstants.DOUBLE_FILLER);
        this.primaryId = getIdentifier(mrzThirdPartArray[0]);
        this.secondaryId = getIdentifier(mrzThirdPartArray[1]);
    }

    private String getIdentifier(String identifier) {
        return String.join(MrzConstants.SPACE, splitBySingleFiller(identifier));
    }

    private List<String> splitBySingleFiller(String identifier) {
        return Arrays.stream(identifier.split(MrzConstants.FILLER)).toList();
    }
}
