package be.tomjo.advent.day8;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class LicenseFileParser {

    private final int[] licenseFile;
    private int licenseFilePointer;

    public LicenseFileParser(int[] licenseFile) {
        this.licenseFile = licenseFile;
        this.licenseFilePointer = 0;
    }

    public LicenseFileNode parseLicenseFileNode(){
        Header header = parseHeader();
        List<LicenseFileNode> childNodes = parseChildNodes(header.getChildNodeCount());
        List<Integer> metadataEntries = parseMetadataEntries(header.getMetadataCount());
        return new LicenseFileNode(header, childNodes, metadataEntries);
    }

    private Header parseHeader(){
        int childNodeCount = licenseFile[licenseFilePointer++];
        int metadataCount = licenseFile[licenseFilePointer++];
        return new Header(childNodeCount, metadataCount);
    }

    private List<Integer> parseMetadataEntries(int metadataCount){
        return range(0, metadataCount)
                .mapToObj(metadataEntry -> licenseFile[licenseFilePointer++])
                .collect(toList());
    }

    private List<LicenseFileNode> parseChildNodes(int childNodeCount) {
        return range(0, childNodeCount)
                .mapToObj(i -> parseLicenseFileNode())
                .collect(toList());
    }
}
